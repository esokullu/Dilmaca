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
    
    public int untouchableTagSplitLength=0; // split_SCRIPT_and_STYLE_tags metodundan sonra olu�an HTMLArray dizisinin boyu
    public String [] untouchableTagSplit; // split_SCRIPT_and_STYLE_tags metodundan sonra olu�an dizi
    
    public int tagSplitLength=0;
    
    private StringBuffer pureHTML=new StringBuffer();
    
    
  
  
  

  public Jetch( URL url ){

/** https i�in
java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
System.setProperty("java.protocol.handler.pkgs", "javax.net.ssl");
 https i�indi **/

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
   * Bu metod <script> ve <style> taglerini normal HTML den ay�r�yor.
   * ��nk� bu tagler i�indeki yorumlar normal yorumlarla ayn� de�il. Bu yorumlar�n
   * i�eri�i silinmemeli.
   * <br>Ayr�ca bunlar �zel tagler oldu�undan ay�rmakta fayda var.
   * <p>Bu metod �yle bir de�er g�nderiyor ki :
   * <ul><li>Tek say�l�lar (1,3,5...) bu �zel tak�lar�</li>
   * <li>�ift say�l�lar (0,2,4...) di�er tak�lar�</li>/ul>
   * g�stermekte.</p>
   * @param html HTML girdisi
   * @return String [] .. tek => SCRIPT ve STYLE ; �ift => di�er tak�lar
   */
  
  private void  splitUntouchableTags() {

      // </?(\w+)(\s+\w+=(\w+|"[^"]*"|'[^']*'))*>  // tag and comment
      
  try {
      
      
      this.pureHTML = this.pureHTML.append(' ').insert(0,' '); // tagle ba�lamas�n� isemiyoruz.

      
      RE re1 = new RE("<(script|style)(>|( [^>]*?>))(.|\n)*?</\\1>");
      
      REMatch [] re1Matches = re1.getAllMatches(this.pureHTML.toString());
      
      this.untouchableTagSplitLength = re1Matches.length*2+1;
      
      this.untouchableTagSplit = new String [this.untouchableTagSplitLength];
      
      int i;
      int startPoint=0;
      int endPoint;
      
      
      
      for (i=0;i<this.untouchableTagSplitLength;i++) {
          
          if(i%2==1) { // tekliler Script veya STYLE tak�lar�
              
              endPoint=re1Matches[(i-1)/2].getEndIndex();
              
              this.untouchableTagSplit[i]=this.pureHTML.toString().substring(startPoint,endPoint);
              
              startPoint=endPoint;
              
          }
          
          else { // di�erleri di�er tak�lar
          
              if(i==re1Matches.length*2)
                  endPoint=this.pureHTML.toString().length();
              else
                  endPoint=re1Matches[(i/2)].getStartIndex();
          
              this.untouchableTagSplit[i]=this.pureHTML.toString().substring(startPoint,endPoint);
              //System.out.println(">>"+stringArray[i].replaceAll("<!\\-\\-(.|\n)*?\\-\\->","")); // yorumlardan kurtarma k�sm�
          
              startPoint=endPoint;
          
              
          }
          

      }

  }
  catch (Exception e) {
      System.err.println("A problem has occured while splitting \"style\" and \"script\" tags");
  }
     
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
    /**
   * Bu metod tagleri d�z metinden ay�r�yor.
   * ��nk� bu tagler i�indeki yorumlar normal yorumlarla ayn� de�il. Bu yorumlar�n
   * i�eri�i silinmemeli.
   * <br>Ayr�ca bunlar �zel tagler oldu�undan ay�rmakta fayda var.
   * <p>Bu metod �yle bir de�er g�nderiyor ki :
   * <ul><li>Tek say�l�lar (1,3,5...) tak�lar</li>
   * <li>�ift say�l�lar (0,2,4...) d�z metinler</li>/ul>
   * g�stermekte.</p>
   * @return String [] .. tek => tak�lar ; �ift => d�z metinler
   */
  
  public String [] splitTags () {
      
      
      String html = this.pureHTML.toString();
      
      // ADIM 1 : Yokedilmesi gereken tagler
      
      // ��nk� bunlar kendilerinden �nceki kelimelere yap���k anlamlar i�eriyor.
      // var'da version 7.<var>x</var>.<var>x</var> gibi bir�ey.
      html = html.replaceAll("</?(sub|sup|var)(>|( [^>]*?>))","");

      
    
     
      
      
      // ADIM 2 : �saretlenmesi gereken tagler
      
      /*******************************************
       **
       ** Bu b�l�m ileride kullan?lacak.
       ** �?u anda taglerle Dilmaca i�in nas�l u�ra�aca��m�z�
       ** bilmiyoruz.
       ** Bundan dolay� ge�ici bir ��z�m bulaca��z.
       ********************************************
     
       html = html.replaceAll("<(/)?(a|abbr|acronym|b|big|cite|del|dfn|em|font|i|img|ins|kbd|s|small|span|strike|strong|u)(>|( [^>]*?>))"," xxtag_separator# $1$2$3 ");
    
       //// YUKARIDA OLMAYAN INLINE VE PHRASE TAKILAR
       ////
       //// TAKI ; NEDEN LISTEDE DE??L
       ////
       // address ; ��nk� genelde adress k?sm?nda yeni giri?ler yer al?yor. Inline olarak kullan?lm?yor.
       // basefont ; temelli bir font de?i?kli?i oluyorsa, blok de?i?ikli?i de oluyor genelde
       // bdo ; ne oldu?u belli de?il
       // code ; burada genelde kod bloklar? yer al?yor.
       // input ; formlar aras?  inline l?k �zelli?i pek kalm?yor
       // label ; a�?klamaya yapma tak?s?.  Genelde inline de?il (buraya dfn da eklenebilir)
       // q ; yeni bir quote a�?yor (blockquote'?n inline ?). Ama genelde yeni bir c�mle
       // samp ; code ile ayn?
       // select ; input ile ayn?
       // textarea ; input ile an?
       // tt ; code, sample ile ayn?
    
       */
    
    
    
    
      /*** Ge�ici ��z�m �u :
      >> Gerekti�inde bu sat�r� kald�r >> */
    
    
      html = html.replaceAll("<(/)?(abbr|acronym|b|big|cite|del|dfn|em|i|ins|kbd|s|small|span|strike|strong|u)(>|( [^>]*?>))","");
    
    
    
    
      /* << Gerekti?ine bu sat�r� kald�r <<
       */
    
    
      
      
      
      
      
      //ADIM 3 : Tak�lar� ay�r
      
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
          
              if(i%2==1) { // Tak�lar
              
                  endPoint=reMatches[(i-1)/2].getEndIndex();
              
                  stringArray[i]=html.substring(startPoint,endPoint);
              
                  startPoint=endPoint;
              
              }
          
              else { // di�erleri metin
          
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




    
