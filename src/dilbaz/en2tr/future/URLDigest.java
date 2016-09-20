
import java.net.*;
import java.io.*;
import java.security.*;
import java.math.*;

public class URLDigest {
    
    public static void main(String [] args) {
        
        for(int i=0 ; i<args.length ; i++ ) {
            try {
                URL u = new URL(args[i]);
                printDigest(u.openStream());
            }
            catch(MalformedURLException e) {
                System.err.println(args[i]+ " is not a URL");
            }
            catch (Exception e) {
                System.err.println(e);
            }
        }
    }
    
    public static void printDigest(InputStream in)
    throws IOException, NoSuchAlgorithmException {
        
        MessageDigest md5 = MessageDigest.getInstance("md5");
        byte [] data = new byte[128];
        while(true) {
            int bytesRead = in.read(data);
            if(bytesRead<0)
                break;
            md5.update(data,0,bytesRead);
        }
        byte [] result = md5.digest();
        for( int i=0 ; i<result.length ; i++ )
            System.out.print(result[i]+" ");
        System.out.println("\n");
        System.out.println(new BigInteger(result));
    }
}