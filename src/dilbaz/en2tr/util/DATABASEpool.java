package dilbaz.en2tr.util;

import java.util.*;
import java.lang.*;
import java.sql.*;

import dilbaz.en2tr.DatabaseVars;
import dilbaz.en2tr.GlobalVars;

public class DATABASEpool  {


    static Connection con=null;
    static Statement stmt=null;
    /**
    * Baglanma yordami.<br>
    * Daha onceden bir baglanti gerceklesmisse, gene ayni islemleri tekrarlamaz.
    * @return boolean
    */
    private static boolean connect() {

        if(GlobalVars.isConnected)
            return true;
        else {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            }
            catch ( Exception e ) {
                System.exit(-1);
            }

            try {
                con=DriverManager.getConnection("jdbc:mysql://localhost/dilmaca?user=root&password=");
                stmt=con.createStatement();
            }
            catch ( SQLException se ) {
                System.exit(-1);
            }


            GlobalVars.isConnected=true;
            return true;
        }
    }


    /**
    * Kendisine verilen kelimenin cogul olup olmadigina bakar.
    * @param model Veritabanindan cekilen, elimizdeki kelimenin tekil halinin ayni olmasi gereken model kelime.
    * @param s Elimizdeki kelime ( cogul haliyle veya alakasiz bir kelime de olabilir )
    * @return boolean
    */
    private static boolean _isPlural(String model,String s) {

        s = new String(s);

        if(s.endsWith("s")) {

            if( model.equalsIgnoreCase(s.substring(0,s.length()-1) ) )
                return true;



            else if ( model.equalsIgnoreCase(s.substring(0,s.length()-2)) && ( s.endsWith("ses") || s.endsWith("xes") || s.endsWith("ses") || s.endsWith("oes") || s.endsWith("ches") || s.endsWith("shes") ) )

                return true;


            else if ( model.equalsIgnoreCase(s.substring(0,s.length()-3)) && ( s.endsWith("aies") || s.endsWith("oies") || s.endsWith("iies") || s.endsWith("eies") || s.endsWith("uies") ) )
                return true;

            else
                return false;

        }

        else
            return false;




    }





    /**
    * Veritabani kayitlarini kontrol eder.<br>
    * Veritabanindan o an cekilen girisin, cumle icindeki kelimelerle uyumlu olup olmadigin ortaya cikartir. ( kelime grubu da olabilir )
    * Bunu yaparken, isimse tekil cogul ayrimin da gozetler.
    * @param en Veritabanindaki model kelime
    * @param type Bu kelimenin tipi. Isimse tekillik aratirmasi yapilacagi icin gerekli.
    * @param p cumle no
    * @param n kelime no
    * @return int ; kac kelime atlanacagini dondurur.<br> Eger cogul isimse buna 100 eklenir.
    */
    private static int _checkDBQueries(String en,String type,int p,int n) {

        if(en.indexOf(' ')==-1) {
            return 1;
        }

        else {

            try {
                int o = n+1; // object
                StringTokenizer st = new StringTokenizer(en);
                String stn = new String();
                int l=st.countTokens(); // limit
                st.nextToken();




                while(st.hasMoreTokens()) {

                    stn = st.nextToken(); // st next



                    if( stn.equalsIgnoreCase( GlobalVars.WORD[p][o] ) ) {
                        o++;
                    }

                    else if ( o==l+n-1 && type.equals("1") && _isPlural( stn , GlobalVars.WORD[p][o] ) ) { // (o-n)==l

                        o+=2;

                    }

                    else
                        break;
                }

                if (o==l+n)
                    return l;
                else if (o==l+n+1)
                    return l+100;  // plural anlami var
                else
                    return -1;

            } catch (ArrayIndexOutOfBoundsException AE ) { return -1; }

        }
    }


    /**
    * Bu yordam, kendisine verilen String arrayini, icinde bulundurduklari kelime
    * sayisina gore buyukten kucuge dogru siraliyor.
    * @param s Duzenlenmesi istenen string arrayi
    * @return String arrayini kelime sayisina gore buyukten kucuge siralamis bir bicimde geri dondurur.
    */
    private static String [] _sort ( String [] a ) {


        Object [] st = new Object [ a.length ];
        Object temp;
        StringBuffer tempBuf;
        int i,j;

        for (i=0;i<a.length;i++) {
            st [i]= (Object)(new StringTokenizer(a[i]));

        }

        for (i=0;i<a.length-1;i++) {
            for (j=0;j<a.length-1;j++) {

                if( ((StringTokenizer)st[j+1]).countTokens() > ((StringTokenizer)st[j]).countTokens() ) {
                    temp =  st[j];
                    st[j]=  st[j+1];
                    st[j+1]=  temp;

                }
            }
        }

        for (i=0;i<a.length;i++) {

            tempBuf=new StringBuffer();
            while(((StringTokenizer)st[i]).hasMoreTokens())
                tempBuf.append(((StringTokenizer)st[i]).nextToken()+" ");
            a[i]=tempBuf.toString().trim();
        }

        return a;

    }







    /**
    *  Veritabanindan veri çekmek için gereken temel fonksiyon.
    * @param s arastirilacak kelime ( bu kelime ve bu kelimeyle baslayan kelimelere bakiliyor.
    * @param p bu kelimenin hangi cumle elemenai oldugu
    * @param n bu kelimenin p nolu cumlenin hangi kelimesi oldugu
    * @param sql extra SQL kodu.
    * @return String []  boolean-type-property-stackTR-jump
    */

    protected static DatabaseVars queryDB(String s,int p,int n,String sql) {

        int fs=0;
        try {
            connect();

            ResultSet rs= stmt.executeQuery("SELECT * FROM en2tr WHERE "+sql+" ( en=\""+s.toLowerCase()+"\" OR en like \""+s.toLowerCase()+" %\" )");


            while(rs.next())
                fs++;

            if(fs==0)
                return new DatabaseVars();

            else if(fs==1) {
                rs.first();

                int check = _checkDBQueries(rs.getString("en"),rs.getString("type"),p,n);

                if(check==-1)
                    return  new DatabaseVars();
                else if (check>100)
                    return new DatabaseVars(1,rs.getInt("prop")+10,rs.getString("tr"),check-100);
                else
                    return new DatabaseVars(rs.getInt("type"),rs.getInt("prop"),rs.getString("tr"),check);

            }


            else {

                String [] toExamineDisordered= new String [fs];
                String [] toExamine = new String [fs];
                int c=0;

                Hashtable toStore = new Hashtable(fs);
                Hashtable toStoreType = new Hashtable(fs);
                Hashtable toStoreProperty = new Hashtable(fs);

                rs.beforeFirst();
                while(rs.next()) {
                    toExamineDisordered[c]=rs.getString("en");
                    toStore.put(toExamineDisordered[c],rs.getString("tr"));
                    toStoreType.put(toExamineDisordered[c],rs.getString("type"));
                    toStoreProperty.put(toExamineDisordered[c],rs.getString("prop"));
                    c++;
                }

                toExamine=_sort(toExamineDisordered);

                for(c=0;c<fs;c++) {

                    int check = _checkDBQueries( toExamine[c],toStoreType.get(toExamine[c]).toString(), p, n );

                    if(check==-1)
                        continue;
                    else if (check>100)
                        return new DatabaseVars(1,10+(new Integer(toStoreType.get(toExamine[c]).toString())).intValue(),toStore.get(toExamine[c]).toString(),check-100);
                    else
                        return new DatabaseVars( (new Integer(toStoreType.get(toExamine[c]).toString())).intValue() , (new Integer(toStoreProperty.get(toExamine[c]).toString())).intValue() , toStore.get(toExamine[c]).toString() , check );

                }

                return new DatabaseVars();

            }
        } catch (SQLException SE) { return new DatabaseVars(); }

    }
}
