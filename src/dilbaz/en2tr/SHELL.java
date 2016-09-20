package dilbaz.en2tr;

import gnu.regexp.*;
import java.lang.*;
import java.util.*;







public class SHELL { 


    static String t =  new String();
    static String te =  new String();


    public static void main(String [] args) throws REException, java.sql.SQLException {

        for(int i=0;i<args.length;i++)
            t+=args[i]+" ";
        t.trim();
System.out.println(t);
        te = translate(t);
        System.out.println(te);

    }






    /**
    * Islenmesi istenen metni, noktalama isaretlerine gore cumleler ayiriyor.
    * Bu islemin sonunda <b>PHRASEen</b> ve <b>SIGN</b> global degiskenleri deger kazaniyor.
    * @param t islenmesi istenen metin
    * @return void
    */


    public static String translate(String t)  {


        GlobalVars.WORD = new String[0][0];
        GlobalVars.TRANSLATED = new String [0];
        GlobalVars.SIGN = new String [0];
        GlobalVars.WORDno=0;
        GlobalVars.OBJECT.removeAll();

        StringBuffer translated = new StringBuffer();

        t = new String(firstTouch(t));
        explodeText(t);

        int i,j;

        for(i=0;i<GlobalVars.WORD.length;i++) {
            try {
                GlobalVars.WORDno=0;
                GlobalVars.OBJECT.removeAll();
                GlobalVars.WORDlength=GlobalVars.WORD[i].length;

                while( GlobalVars.WORDno < GlobalVars.WORDlength )
                    new CYCLE2(i,GlobalVars.WORDno);
                new CYCLE3(i);

                //translated.append(' '+ startWithCapitalLetter(GlobalVars.TRANSLATED[i])+GlobalVars.SIGN[i]);
                translated.append(' '+ GlobalVars.TRANSLATED[i]+GlobalVars.SIGN[i]);
            } catch (IndexOutOfBoundsException e) {

                translated.append(' '+ GlobalVars.TRANSLATED[i]+'.');
            }
        }

        try {
            return translated.toString().substring(1);
        } catch(StringIndexOutOfBoundsException e) {
            return translated.toString();
        }


    }





    /*
        private static String startWithCapitalLetter(String s) {
            
            char [] c = s.toCharArray();
            
            switch (c[0]) {
                case '\u00E7' : // ç
                    return '\u00C7'+s.substring(1);
                case '\u011F' : // ?
                    return '
            
            
            
            
            
            
        }
        
        */  



    public static void explodeText(String t) {



        try {
            Vector temp = new Vector();

            Vector sign = new Vector();
            Vector phrase=new Vector();

            RE r0=new RE("[^ ]+");
            RE r1=new RE("^[.:?!;]*$");
            RE r2=new RE("[.:?!;]+");
            REMatch rMatch ;


            REMatch [] r0Matches = r0.getAllMatches(t);


            String [] st = new String [r0Matches.length];
            int i,stNo;

            for ( i=0 ; i < r0Matches.length ; i++ )
                st[i] = r0Matches[i].toString();



            for(stNo=0;stNo<st.length;stNo++)
            {
                rMatch=r2.getMatch(st[stNo]);

                if(r1.isMatch(st[stNo])) {
                    sign.addElement(st[stNo]);
                    phrase.addElement(temp);
                    temp=new Vector();

                }
                else if ( rMatch!=null && rMatch.getEndIndex()==st[stNo].length() ) {
                    sign.addElement(st[stNo].substring(rMatch.getStartIndex()));
                    temp.addElement(st[stNo].substring(0,rMatch.getStartIndex()));
                    phrase.addElement(temp);
                    temp=new Vector();

                }
                else if( st[stNo].charAt(0)!='#' && rMatch!=null ) {



                    i=0;
                    while(rMatch!=null) {

                        sign.addElement(st[stNo].substring(rMatch.getStartIndex(),rMatch.getEndIndex()));
                        if(i==0) {
                            if(rMatch.getStartIndex()==0) {

                                phrase.addElement(temp);
                                temp=new Vector();
                            }
                            else {
                                temp.addElement(st[stNo].substring(0,rMatch.getStartIndex()));
                                phrase.addElement(temp);
                                temp=new Vector();
                            }
                        }
                        else {
                            temp.addElement(st[stNo].substring(0,rMatch.getStartIndex()));
                            phrase.addElement(temp);
                            temp=new Vector();

                        }
                        st[stNo]=new String(st[stNo].substring(rMatch.getEndIndex()));
                        rMatch=r2.getMatch(st[stNo]);

                        i++;
                    }
                    if(!st[stNo].equals(""))
                        temp.addElement(st[stNo]);

                }

                else
                    temp.addElement(st[stNo]);

            }

            //if( !temp.isEmpty() || !temp.get(0).toString().trim().equals("")) gives Exception
            if( !temp.isEmpty() )
                phrase.addElement(temp);


            GlobalVars.WORD = new String[phrase.size()][];
            GlobalVars.TRANSLATED = new String[phrase.size()];
            GlobalVars.SIGN = new String[sign.size()];



            for(i=0;i<phrase.size();i++) {
                GlobalVars.WORD[i] = new String[((Vector)phrase.elementAt(i)).size()];
                for(int j=0;j<((Vector)phrase.elementAt(i)).size();j++)
                    GlobalVars.WORD[i][j] = (String)((Vector)phrase.elementAt(i)).elementAt(j);
            }
            for(i=0;i<sign.size();i++)
                GlobalVars.SIGN[i]= sign.elementAt(i).toString();
        } catch(REException E) {}
    }



    /**
    * Islenmesi gereken metni islemeden once son hazirliklari yapiyor.
    * Metni tek ve belirli bir duzene sokuyor.
    * @param t islenmesi istenen metin
    * @return metni islenmis bir halde geri donduruyor.
    */

    public static String firstTouch(String t) {

        try {
            /* ain't isn't */
            RE r = new RE("\\bain\\'t\\b");
            t = new String( r.substituteAll(t," is not "));


            /* wanna -> want to */
            r = new RE("\\bwanna\\b");
            t = new String( r.substituteAll(t," want to "));


            /* gonna -> going to */
            r = new RE("\\bgonna\\b");
            t = new String( r.substituteAll(t," going to "));


            /* negatiflik eki ..n't bosluk not olarak degistiriliyor */
            r = new RE("n\\\'t\\b");
            t = new String( r.substituteAll(t," not ") );


            /* & ve , daha kolay incelenmek üzere boslukla cumleden ayriliyor */
            r = new RE("(\\&|\\,)");
            t = new String( r.substituteAll(t," $1 ") );


            /*
            * 1. içinde sayi # ve @ bulunduranlar fisleniyor ( ön taki eki # ) alarak.
            * 2. network adresleri fisleniyor (ön taki eklerine bakilarak).
            * 3. network adresleri fisleniyor (son taki eklerine bakilarak).
            * 4. dosya isimleri fisleniyor.
            * 5. kisaltmalar fisleniyor
            * 6. smileyler fisleniyor.
            */
            r = new RE("(\\b|([^ #]\\S*[.:?!;]))((([^ #]\\S*(#|\\d|@)\\S*)|((\\b|@)\\d\\S*))|((ftp\\:|http\\:|https\\:|gopher\\:|rsync\\:|www\\.|ftp\\.)\\S*)|([^ #]\\S*\\.(com|net|biz|edu|gov|org|fr|de|to|us|ca|it|sk)(((\\/|\\.|\\:)\\S*)|\\b))|([^ #]\\S*\\.(txt|mov|gif|jpeg|swf|fla|jpg|bmp|wav|mp3|exe|zip|tar|gz|bz|bz2|rpm|asp|asa|php|php3|jsp|java|class|py|cfm|html|xhtml|xml|htm|shtml|phtml|c|h|xsl|mdb|sql|dll|so|pdf)((\\.\\S*)|\\b))|([^ #]\\.([a-zA-Z]\\.)*[a-zA-Z]\\.?)|((\\:|;|8|\\=|%)+\\s*\\-*\\s*(\\\\|/|\\(|\\)|\\||<|>|x|X|p|P|s|S|q|Q|l|L|o|O)+))(\\b|(\\S*[.:?!;]))");
            t = new String( r.substituteAll(t,"#$3") );


            /* He's You're daki apostrof sonrasi ekler ayriliyor daha kolay incelenebilmek amaciyla. */
            r = new RE("\\\'(s|d|m|(ve)|(re)|(ll))\\b");
            t = new String( r.substituteAll(t," xx$1# ") );

            /* Bu da Marks' gibi durumlar icin, ama degistirilebilir. */
            r = new RE("\\Bs\\\'\\b");
            t = new String( r.substituteAll(t,"s xxs# ") );


            return t;
        } catch(REException re) {return null;}
    }
}
