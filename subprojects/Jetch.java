import java.io.*;
import java.net.*;

/**
* Fetches the HTML content of a web page as a String.
*/
public final class Jetch {

  public static void main(String[] aArguments) throws MalformedURLException {
    Jetch fetcher = new  Jetch(aArguments[0]);
    if ( aArguments[1].equalsIgnoreCase("header") ) {
      System.out.println( fetcher.getPageHeader() );
    }
    else if ( aArguments[1].equalsIgnoreCase("content") ) {
      System.out.println( fetcher.getPageContent() );
    }
    else {
      System.err.println("Unknown option.");
    }
  }
    
    
  // PRIVATE //
  private URL url;

  private final String HTTPProtocol = "http";

  private final String NEWLINE = System.getProperty("line.separator");
    
    

  public Jetch( URL url ){
    if ( !url.getProtocol().equals(fHTTP) ) {
      throw new IllegalArgumentException("URL is not for HTTP Protocol: " + url );
    }
    
   this.url = url ;
  }

  public Jetch( String urlName ) throws MalformedURLException {
    this ( new URL(urlName) );
  }

  /**
  * Fetch the HTML content of the page as simple text.
  */
  public String getFullHTML() {
    StringBuffer result = new StringBuffer();
    BufferedReader reader = null;
    try {
      reader = new BufferedReader( new InputStreamReader(this.url.openStream()) );
      String line = null;
      while ( (line = reader.readLine()) != null) {
        result.append(line);
        result.append(this.NEWLINE);
      }
    }
    catch ( IOException ex ) {
      System.err.println("Cannot retrieve contents of: " + this.url );
    }
    finally {
      shutdown( reader );
    }
    return result.toString();
  }

  /**
  * Fetch the HTML headers as simple text.
  */
  public String getPageHeader(){
    StringBuffer result = new StringBuffer();

    URLConnection connection = null;
    try {
      connection = this.url.openConnection();
    }
    catch (IOException ex) {
      System.err.println("Cannot open connection to URL: " + this.url );
    }

    //not all headers come in key-value pairs - sometimes the key is
    //null or an empty String
    int headerIdx = 0;
    String headerKey = null;
    String headerValue = null;
    while ( (headerValue = connection.getHeaderField(headerIdx)) != null ) {
      headerKey = connection.getHeaderFieldKey(headerIdx);
      if ( headerKey != null && headerKey.length()>0 ) {
        result.append( headerKey );
        result.append(" : ");
      }
      result.append( headerValue );
      result.append(this.NEWLINE);
      headerIdx++;
    }
    return result.toString();
  }



  private void shutdown( Reader reader ){
    try {
      if (aReader != null) reader.close();
    }
    catch (IOException ex){
      System.err.println("Cannot close reader: " +reader);
    }
  }
  
  
} 
