package dilbaz;

import java.io.*;
import java.net.*;
import gnu.regexp.*;


/**
* Fetches the HTML content of a web page as a String.
*/
public final class Jetch {


    
    
  
  
  
  private URL url;

  private final String HTTPProtocol = "http";

  private final String NEWLINE = System.getProperty("line.separator");
    
    public int untouchableTagSplitLength=0; // split_SCRIPT_and_STYLE_tags metodundan sonra oluþan HTMLArray dizisinin boyu
    public String [] untouchableTagSplit; // split_SCRIPT_and_STYLE_tags metodundan sonra oluþan dizi
    
    public int tagSplitLength=0;
    
    private StringBuffer pureHTML=new StringBuffer();
    
    
  
  
  

  public Jetch( URL url ){

/** https için
java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
System.setProperty("java.protocol.handler.pkgs", "javax.net.ssl");
 https içindi **/

    if ( !url.getProtocol().equals(HTTPProtocol) ) {
      
        new IllegalArgumentException("URL is not for HTTP Protocol: " + url );
    }
    
   this.url = url ;
    this.getPageContent();
    this.splitUntouchableTags();
    
    
  }

  
  
  
  public Jetch( String urlName ) throws MalformedURLException {
    this ( new URL(urlName) );
  }

  
  
  
  /**
  * Fetch the HTML content of the page as simple text.
  */
  private void getPageContent() {
      
      BufferedReader reader = null;

        try { 
            
            

            
            reader = new BufferedReader( new InputStreamReader(this.url.openStream()) );
            String line = null;
            while ( (line = reader.readLine()) != null) {
                this.pureHTML.append(line);
                this.pureHTML.append(this.NEWLINE);
            }
            
        }
        catch ( Exception ex ) {
            System.err.println("Cannot retrieve contents of: " + this.url );
        } 
        
        finally {
            this.shutdown( reader );
        }

  }

  
  
  
  
  
  private void isTrueContentType() throws NotValidContentTypeException {
      
      try {
          URLConnection conn = this.url.openConnection();
          
          if(!conn.getContentType().startsWith("text/html"))
 
              throw new NotValidContentTypeException();
      
      }
      
      catch (IOException e) {
          
          throw new NotValidContentTypeException();
      }

  }
      


  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  /**
   * Bu metod <script> ve <style> taglerini normal HTML den ayýrýyor.
   * Çünkü bu tagler içindeki yorumlar normal yorumlarla ayný deðil. Bu yorumlarýn
   * içeriði silinmemeli.
   * <br>Ayrýca bunlar özel tagler olduðundan ayýrmakta fayda var.
   * <p>Bu metod öyle bir deðer gönderiyor ki :
   * <ul><li>Tek sayýlýlar (1,3,5...) bu özel takýlarý</li>
   * <li>Çift sayýlýlar (0,2,4...) diðer takýlarý</li>/ul>
   * göstermekte.</p>
   * @param html HTML girdisi
   * @return String [] .. tek => SCRIPT ve STYLE ; çift => diðer takýlar
   */
  
  private void  splitUntouchableTags() {

      // </?(\w+)(\s+\w+=(\w+|"[^"]*"|'[^']*'))*>  // tag and comment
      
  try {
      
      
      this.pureHTML = this.pureHTML.append(' ').insert(0,' '); // tagle baþlamasýný isemiyoruz.

      
      RE re1 = new RE("<(script|style)(>|( [^>]*?>))(.|\n)*?</\\1>");
      
      REMatch [] re1Matches = re1.getAllMatches(this.pureHTML.toString());
      
      this.untouchableTagSplitLength = re1Matches.length*2+1;
      
      this.untouchableTagSplit = new String [this.untouchableTagSplitLength];
      
      int i;
      int startPoint=0;
      int endPoint;
      
      
      
      for (i=0;i<this.untouchableTagSplitLength;i++) {
          
          if(i%2==1) { // tekliler Script veya STYLE takýlarý
              
              endPoint=re1Matches[(i-1)/2].getEndIndex();
              
              this.untouchableTagSplit[i]=this.pureHTML.toString().substring(startPoint,endPoint);
              
              startPoint=endPoint;
              
          }
          
          else { // diðerleri diðer takýlar
          
              if(i==re1Matches.length*2)
                  endPoint=this.pureHTML.toString().length();
              else
                  endPoint=re1Matches[(i/2)].getStartIndex();
          
              this.untouchableTagSplit[i]=this.pureHTML.toString().substring(startPoint,endPoint);
              //System.out.println(">>"+stringArray[i].replaceAll("<!\\-\\-(.|\n)*?\\-\\->","")); // yorumlardan kurtarma kýsmý
          
              startPoint=endPoint;
          
              
          }
          

      }

  }
  catch (Exception e) {
      System.err.println("A problem has occured while splitting \"style\" and \"script\" tags");
  }
     
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
    /**
   * Bu metod tagleri düz metinden ayýrýyor.
   * Çünkü bu tagler içindeki yorumlar normal yorumlarla ayný deðil. Bu yorumlarýn
   * içeriði silinmemeli.
   * <br>Ayrýca bunlar özel tagler olduðundan ayýrmakta fayda var.
   * <p>Bu metod öyle bir deðer gönderiyor ki :
   * <ul><li>Tek sayýlýlar (1,3,5...) takýlar</li>
   * <li>Çift sayýlýlar (0,2,4...) düz metinler</li>/ul>
   * göstermekte.</p>
   * @return String [] .. tek => takýlar ; çift => düz metinler
   */
  
  public String [] splitTags () {
      
      
      String html = this.pureHTML.toString();
      
      // ADIM 1 : Yokedilmesi gereken tagler
      
      // çünkü bunlar kendilerinden önceki kelimelere yapýþýk anlamlar içeriyor.
      // var'da version 7.<var>x</var>.<var>x</var> gibi birþey.
      html = html.replaceAll("</?(sub|sup|var)(>|( [^>]*?>))","");

      
    
     
      
      
      // ADIM 2 : Ýsaretlenmesi gereken tagler
      
      /*******************************************
       **
       ** Bu bölüm ileride kullan?lacak.
       ** þ?u anda taglerle Dilmaca için nasýl uðraþacaðýmýzý
       ** bilmiyoruz.
       ** Bundan dolayý geçici bir çözüm bulacaðýz.
       ********************************************
     
       html = html.replaceAll("<(/)?(a|abbr|acronym|b|big|cite|del|dfn|em|font|i|img|ins|kbd|s|small|span|strike|strong|u)(>|( [^>]*?>))"," xxtag_separator# $1$2$3 ");
    
       //// YUKARIDA OLMAYAN INLINE VE PHRASE TAKILAR
       ////
       //// TAKI ; NEDEN LISTEDE DE??L
       ////
       // address ; çünkü genelde adress k?sm?nda yeni giri?ler yer al?yor. Inline olarak kullan?lm?yor.
       // basefont ; temelli bir font de?i?kli?i oluyorsa, blok de?i?ikli?i de oluyor genelde
       // bdo ; ne oldu?u belli de?il
       // code ; burada genelde kod bloklar? yer al?yor.
       // input ; formlar aras?  inline l?k özelli?i pek kalm?yor
       // label ; aç?klamaya yapma tak?s?.  Genelde inline de?il (buraya dfn da eklenebilir)
       // q ; yeni bir quote aç?yor (blockquote'?n inline ?). Ama genelde yeni bir cümle
       // samp ; code ile ayn?
       // select ; input ile ayn?
       // textarea ; input ile an?
       // tt ; code, sample ile ayn?
    
       */
    
    
    
    
      /*** Geçici çözüm þu :
      >> Gerektiðinde bu satýrý kaldýr >> */
    
    
      html = html.replaceAll("<(/)?(abbr|acronym|b|big|cite|del|dfn|em|i|ins|kbd|s|small|span|strike|strong|u)(>|( [^>]*?>))","");
    
    
    
    
      /* << Gerekti?ine bu satýrý kaldýr <<
       */
    
    
      
      
      
      
      
      //ADIM 3 : Takýlarý ayýr
      
      html=" "+html+" ";
      try {
          RE re = new RE("</?(\\w+)(>|( [^>]*?>))");
          REMatch [] reMatches = re.getAllMatches(html);
          String [] stringArray = new String [reMatches.length*2+1];
      
          int i;
          int startPoint=0;
          int endPoint;
      
          this.tagSplitLength = reMatches.length*2+1;
          for (i=0;i<this.tagSplitLength;i++) {
          
              if(i%2==1) { // Takýlar
              
                  endPoint=reMatches[(i-1)/2].getEndIndex();
              
                  stringArray[i]=html.substring(startPoint,endPoint);
              
                  startPoint=endPoint;
              
              }
          
              else { // diðerleri metin
          
                  if(i==reMatches.length*2)
                      endPoint=html.length();
                  else
                      endPoint=reMatches[(i/2)].getStartIndex();
          
                  stringArray[i]=html.substring(startPoint,endPoint);
                  
          
                  startPoint=endPoint;
          
              
              }
          }
    
    
          return stringArray;
          
      } catch(REException e) { 
          System.err.println("There was an error in stripTags method");
          return null;
      }
      
      
      
  }




 
  
  

  private void shutdown( Reader reader ){
    try {
      if (reader != null) reader.close();
    }
    catch (IOException ex){
      System.err.println("Cannot close reader: " +reader);
    }
  }
  
  
  
  
  
} 




    
