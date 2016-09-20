package dilbaz.en2tr.util;

public class Suffixes {


    protected char lastVowel(String s)
    {

        int i;
        char [] ch=s.toCharArray();

        for ( i=(ch.length-1) ; i>=-1 ; i-- ) {

            if(i==-1)
                return 'a';
            else if(ch[i]=='a' || ch[i]=='e' || ch[i]=='\u0131' || ch[i]=='i' || ch[i]=='o' || ch[i]=='\u00F6' || ch[i]=='u' || ch[i]=='\u00FC' )
                break;

        }
        
        return ch[i];

        
    }


    protected char lastVowelBinary(String s)
    {

        char ch = lastVowel(s);

        if( ch=='a' || ch=='\u0131' || ch=='o' || ch=='u' ) // \u0131 i
            return 'a';
        else
            return'e';
    }


    protected char lastVowelQuattro(String s)
    {

        char ch=lastVowel(s);
        if ( ch=='a' || ch=='\u0131' )
            return '\u0131';
        else if( ch=='e' || ch=='i' )
            return 'i';
        else if ( ch=='o' || ch=='u' )
            return 'u';
        else
            return '\u00FC';

    }


    protected String softify(String n) {

        if( n.endsWith("nk") )
            return n.substring(0,n.length()-1) + 'g' ;
        else if ( n.equals("bisiklet") )
            return n;

        else {
            char [] ch=n.toCharArray();
            int j=0;
            for(int i=0;i<ch.length;i++)
                if( ch[i]=='a' || ch[i]=='e' || ch[i]=='\u0131' || ch[i]=='i' || ch[i]=='o' || ch[i]=='\u00F6' || ch[i]=='u' || ch[i]=='\u00FC' )
                    j++;
            if(j<2)
                return n;
            else {
                switch(ch[ch.length-1]) {

                case 'k' : return n.substring(0,n.length()-1) + '\u011F' ;
                case '\u00E7' : return n.substring(0,n.length()-1) + 'c' ;
                case 't' : return n.substring(0,n.length()-1) + 'd' ;
                case 'p' : return n.substring(0,n.length()-1) + 'b' ;
                default : return n;
                }
            }

        }

    }
    protected String fallVowel(String n) {
        if(n.equals("ak\u0131l"))
            return "akl";
        else if(n.equals("fikir"))
            return "fikr";
        else if (n.equals("bahis")) return "bahs";
        else if (n.equals("kibir")) return "kibr";   // kibrim degil
        else if (n.equals("\u00F6m\u00FCr")) return "\u00F6mr"; //omur
        else if (n.equals("emir")) return "emr";
        else if (n.equals("h\u00FCk\u00FCm")) return "h\u00FCkm"; //hukum
        else if (n.equals("kah\u0131r")) return "kahr"; //kahir
        else if (n.equals("kay\u0131p")) return "kayb"; //kayip
        //      	else if (n.equals("zehir")) return "zehr";
        else if (n.equals("seyir")) return "seyr";
        else if (n.equals("bahis")) return "bahs";
        else if (n.equals("nakil")) return "nakl";
        else if (n.equals("keyif")) return "keyf";
        else if (n.equals("ke\u015Fif")) return "ke\u015Ff"; //kesif
        else if (n.equals("sab\u0131r")) return "sabr"; //sabir
        else if (n.equals("hapis")) return "haps";
        else if (n.equals("\u015F\u00FCk\u00FCr")) return "\u015F\u00FCkr"; //sükür
        else if (n.equals("devir")) return "devr";
        else if (n.equals("nesir")) return "nesr";
        else if (n.equals("akis")) return "aks";
        else if (n.equals("vak\u0131f")) return "vakf"; //vakif
        //		else if (n.equals("nak\u0131\u015F")) return "nak\u015F";
        else if (n.equals("kas\u0131t")) return "kasd"; //kasit
        //		else if (n.equals("katil")) return "katl";



        //webten çektiklerim ve emin oldugumu söyleyebileceklerim
        else if (n.equals("a\u011F\u0131z")) return "a\u011Fz"; //agiz
        else if (n.equals("al\u0131n")) return "aln"; //alin
        else if (n.equals("ba\u011F\u0131r")) return "ba\u011Fr"; //bagir
        else if (n.equals("beniz")) return "benz";
        else if (n.equals("beyin")) return "beyn";
        else if (n.equals("boyun")) return "boyn";
        else if (n.equals("burun")) return "burn";
        else if (n.equals("geniz")) return "genz";
        else if (n.equals("go\u011Fus")) return "go\u011Fs"; //gogus
        else if (n.equals("gonul")) return "gonl";
        else if (n.equals("karin")) return "karn";

        // kitapta yukardakilerle beraber islenenler
        else if (n.equals("o\u011Ful")) return "o\u011Fl"; //ogul
        else if (n.equals("omuz")) return "omz";
        else if (n.equals("kay\u0131n")) return "kayn"; //kayin
        else return n;

    }


    protected String [] prepareForSuffixing(String s) {
        if( s.indexOf(' ')== -1 )
            return new String [] {"",s};
        else
            return new String [] { s.substring( 0 , s.lastIndexOf(' ') ) , s.substring( s.lastIndexOf(' ')+1 ) };
    }


    protected String solidify(String s) {

        if(s.equals("git") || s.equals("et") || s.equals("hisset") || s.equals("hazmet") || s.equals("hallet") )
            return s.substring(0,s.length()-1)+"d";
        else
            return s;
    }

}
