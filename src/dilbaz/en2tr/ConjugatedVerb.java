package dilbaz.en2tr;

/**
* En genis sinif
* Cekimli fiilleri icinde barindiriyor.
* @author Emre Sokullu
*/

class ConjugatedVerb extends dilbaz.en2tr.util.Suffixes implements OBJECT {

    /**
    * Alttaki özellikler fiilin yapisal propertysinden kendi buldugumuz ozellikler.
    */
    private int verbObject;
    private boolean withVerbING=false;
    private boolean withVerbTo=false;
    private boolean withThat=false;
    private int objectivePronoun=-1;
    /**
    * Fiil "ihtiyac ol" ise ; ozneye bakip onu
    * "ihtiyacim ol" gibi cekebiliyor.
    */ 
    private boolean isPossessiveVerb=false;

    /**
    * daha onceden finishVerb() yordaminin
    * agrilmis olup olmadigini tutyor.
    */
    private boolean hasAlreadyBeenConjugated=false;


    /**
    * Constructor'da veriliyor zaten
    */

    private String stack;


    /**
    * findVerb() ayaginda falan bulunan ozellikler
    */
    private String time;
    private int person;
    private boolean negative;
    private boolean query;
    private boolean passive;

    /*
    * mode 0
    * mode 1
    * mode 2
    * mode 3 if
    */
    private int mode;



    /**
    * kurucu sinif
    * @param s fiil
    * @param i property ; property 9 : isim-fiil
    * @param vTime zaman degiskeni
    * @param vPerson sahis degiskeni
    * @param vNegative olumlu olumsuz fiil 
    * @param vQuery soru fiili
    * @param vPassive pasif yuklem
    * @param vMode yuklem modu
    * @return void
    */ 

    public ConjugatedVerb(String s,int i,String vTime, int vPerson, boolean vNegative, boolean vQuery, boolean vPassive, int vMode) {

        this.stack=s;
        this.verbObject = i%10;
        this.time=vTime;
        this.person=vPerson;
        this.negative=vNegative;
        this.query=vQuery;
        this.passive=vPassive;
        this.mode=vMode;

        if ( i%100 >= 10 )
            this.withVerbING=true;

        if ( i%1000 >= 100 )
            this.withVerbTo=true;

        if ( i%10000 >= 1000 )
            this.withThat=true;

        if ( i%100000 >= 10000 )
            this.objectivePronoun = i/100000-1;


        if ( i >= 100000 )
            this.isPossessiveVerb=true;
    }




    /**
     * Direkt çekimli bir fiil olu?turuyor.
     * Daha çok veritaban?ndan gelen ConjugatedVerbler için (17.6)
     * hasAlreadyBeen true, di?erleri varsay?lan de?erler
     * @param s Çekimlenmi? haliyle Türkçe fiil
     */
    public ConjugatedVerb(String s) {

        this.stack=s;
        this.verbObject = 0;
        this.hasAlreadyBeenConjugated=true;
    }

    
    /**
     * Bo? kurucu yordam.
     * CYCLE3 de fiilleri birle?tiren yordam da temprary ConjugatedVeb instance ?
     * olu?turulurken kullan?l?yor.
     * <br>Maksat ConjugatedVErb instance ? olu?turmak.
     */
    public ConjugatedVerb() {}


    /**
    * Fiilin verbObject ini degistiriyor, diger withING gibi
    * ozelliklerini de false yapiyor.
    * @param i tip no
    * @return void
    */
    public void setProperties(int i) {
        this.verbObject = i%10;
        this.withVerbING=false;
        this.withVerbTo=false;
        this.withThat=false;
        this.objectivePronoun=-1;
        this.isPossessiveVerb=false;
    }


    /**
    * @return fiil en saf halinde
    */
    public String getStack() {
        return stack;
    }


    /**
    * Yeni stack atiyor.
    * @param s yeni stack
    * @return void
    */
    public void setStack(String s) {
        stack=s;
    }


    /**
    * Protokol yordami
    * @return -1
    */
    public int getType() { return -1; }


    public boolean mayTakeVerbObject() {

        if(this.verbObject!=7)
            return true;
        else
            return false;
    }





    /**
    * @return fiilin nesnesi
    */
    public int getVerbObject() {
        return this.verbObject;
    }


    
     /**
     * Fiilimiz geçi?li fiil mi geçi?siz mi onu belirtiyor.
     * @return boolean (true -> geçi?li ; false -> geçi?siz )
     */
    public boolean isIntransitive() {
        if(this.verbObject==7)
            return true;
        else
            return false;
    }
    
    
    
    
    
    /**
    * fiili negatif yapiyor.
    * @return void
    */
    public void makeNegative() {

        this.negative=true;
    }


    /**
    * fiile zarf ekliyor
    * @return void
    */
    public void appendAdverb(String s) {

        this.stack=s.concat(' '+stack);

    }


    /**
    * fiilden sonra <b>that</b> ( he is handsome ) gelir mi ? "Say" gibi fiiller için.
    */
    public boolean withThat() {
        return this.withThat;
    }


    /**
    * verb + objective pronoun durumu yasanabilir mi ?
    */
    public int getObjectivePronoun() {
        return this.objectivePronoun;
    }



    
    /**
    * fiilden sonra -ing ile biten fiil gelir mi
    */
    public boolean withVerbING() {
        return this.withVerbING;
    }


    /**
    * fiilden sonra <b>to verb</b> kalibi gelir mi ?
    */
    public boolean withVerbTo() {
        return this.withVerbTo;
    }


    /**
    * Saf fiilin ilktakisi degisken mi.
    * Ornek : ihtiyac var --> ihtiyacim var
    */
    public boolean isPossessiveVerb() {
        return this.isPossessiveVerb;
    }
    
    
    
    /**
     * Soru yüklemi mi de?i mi onu buluyor
     * @return Soru yüklemi mi ?
     */
    public boolean isQueryVerb() {
        return this.query;
    }
    
    
    /**
     * Yüklemimiz soru yüklemi olsun olmas?n ;
     * onu soru yüklemi olmaktan ç?kar?yor.
     * @return void
     */
    public void isntQueryVerb() {
        this.query=false;
    }
    

    /**
    * Sahisi degistiriyor.
    * Eger daha onceden dege verilmissse herhangi bir degisiklik yer almiyor.
    * @param p sahis
    * @return void
    */

    public void setPerson(int p) {
        if( person == 0 )
            person = p;
    }


    /**
    * Yuklem nesnesini degistiriyor.
    * @param o fiil nesnesi
    * @return void
    */    
    public void setVerbObject(int o) {

        verbObject = o;
    }



    /**
    * Modu degistiriyor.
    * <br>1. mode 1
    * <br>200. mode 2 / %100 person
    * @param m mod
    * @return void
    */    
    public void setMode(int m) {
        mode=m;
    }


    /**
    * conjugate() <br>
    * Fiili cekip, kullanima hazir hale getiriyor.<br>
    * Guvenlik onlemi olarak hasAlreadyBeenConjugated boolean ini kullaniyor ;
    * Bu ayni fiilin birden fazla kere cekilmsini onluyor.
    * @return void
    */

    public void conjugate() {
        System.out.println("conjugat?ng...");
        if(!hasAlreadyBeenConjugated) {
            
            
            // STEP 0
            // xxconjugateverb_behind tak?s? ile ilgileniliyor.
            String replacementVerb=new String(); // Yeni doludurlacak kelime
            ConjugatedVerb tempConjugatedVerb = new ConjugatedVerb(); // yeni ConjugatedVerb nesnesi ; arkada tak? varsa diye ..
            int n_f=-1; // doldurulacak yerin son index nosu
            int n_i=-1; // doldurulacak yerin ilk index nosu

            while ( ( n_f = this.stack.indexOf(" xxconjugatedverb_behind#") ) !=-1 ) {
                n_i = this.stack.lastIndexOf(' ',n_f-1); 
                if(n_i != -1 ) { // ilk kelime de?il.
                    
                    tempConjugatedVerb= new ConjugatedVerb( this.stack.substring(n_i+1,n_f) , 0 , this.time, this.person , this.negative, this.query , this.passive , this.mode);
                    tempConjugatedVerb.conjugate();
                    replacementVerb = tempConjugatedVerb.getStack();
                    // bo?lu?u da içine almak için n_i+1
                    this.stack = this.stack.substring(0,n_i+1) + replacementVerb + this.stack.substring(n_f+25);
                }
                else { // ilk kelime
                    
                    tempConjugatedVerb= new ConjugatedVerb( this.stack.substring(0,n_f) , 0 ,  this.time, this.person , this.negative, this.query , this.passive , this.mode);
                    tempConjugatedVerb.conjugate();
                    replacementVerb = tempConjugatedVerb.getStack();
                    // bo?lu?u da içine almak için n_i+1
                    this.stack = replacementVerb + this.stack.substring(n_f+25);
                }
                
               
            }
            
            

            // STEP 1
            // oncelikle eger hala deger atanmamissa,
            // person a deger atayalim.
            if ( person == 0 )
                person = 3 ;



            // STEP 1.25
            // have# ilgilenme ayagi
            if(stack.equals("have#"))
                stack= "sahip be#";


            // STEP 1.5
            // isim fiillerle ilgilenme ayagi
            if(stack.endsWith(" be#")) {

                if(time.equals("21")) {
                    if(negative)
                        stack=stack.substring(0,stack.length()-3)+"de\u011Fil";
                    else
                        stack = stack.substring(0,stack.length()-4);

                    hasAlreadyBeenConjugated=true;
                    return;
                }
                else if ( time.equals("11") ) {

                    if(negative)
                        stack=stack.substring(0,stack.length()-3)+"de\u011Fil";
                    else
                        stack = stack.substring(0,stack.length()-4);

                }

                else
                    stack = stack.substring(0,stack.length()-3)+"ol";


            }



            // STEP 2 : PASSIVES
            if(passive)
                makeVerbPassive();

            // STEP 3 : MODES
            if(this.mode==1) {
                makeMode1();
                return;
            }
            else if (this.mode>=200) {
                makeMode2(this.mode%100);
                return;
            }

            // STEP 4 : IKI KELIMEDEN OLUSAN FIILLERLE ILGILENME AYAGI
            if ( time.equals("32") ) {
                stack = addVerb__yor();
                stack = stack.concat(" ol");
            }
            else if ( time.equals("33") || time.equals("canPastPast") || time.equals("mustPast") || time.equals("wouldPast") ) {
                stack = addVerb__mis();
                stack = stack.concat(" ol");
            }
            else if ( time.equals("34") ) {
                stack = addVerb__mekte();
                stack = stack.concat(" ol");
            }
            // bir de dare eklenebilir!


            // STEP 5 : ZAMAN CAN ISE, OLUMSUZ ONCESI -E EKI
            if( time.equals("can") && negative )
                this.makeNegativeCan();

            // STEP 6 : NEGATIVE
            if(negative)
                this.makeVerbNegative();

            // STEP 7 : ZAMANLAR
            boolean t2 = false; // time2 ikinci -di eki ; yor - du

            if(time.equals("11"))
                stack = addVerb__di();
            else if(time.equals("12")) {
                stack = addVerb__yor();
                t2=true;
            }
            else if(time.equals("13")) {
                stack = addVerb__mis();
                t2 = true;
            }
            else if(time.equals("14")) {
                stack = addVerb__mekte();
                t2 = true ;
            }
            else if ( time.equals("21") && !negative )
                stack = addVerb_21();
            else if (time.equals("22"))
                stack = addVerb__yor();
            else if ( time.equals("31") || time.equals("32") || time.equals("33") || time.equals("34") || time.equals("bound") )
                stack = addVerb__ecek();
            else if ( time.equals("must") )
                stack = addVerb__meli();
            else if ( time.equals("can") && !negative )
                stack = addVerb__ebilir();
            else if ( time.equals("canPast") || time.equals("canPastPast") ) {
                stack = addVerb__ebilir();
                t2=true;
            }
            else if ( time.equals("would") || time.equals("woulPast") ) {
                stack = addVerb_21();
                t2=true;
            }
            else if ( time.equals("must") || time.equals("mustPast") ) {
                stack = addVerb__meli();
                t2=true;
            }

            // STEP 8 : -DI EKI ALACAK 3P LER
            if ( t2 && person == 13 )
                makePlural();


            // STEP 9 : IF
            if(mode==3)
                this.makeVerbIf();

             /*yeni**/
            // STEP 11 : QUERY
            if ( query && !time.equals("11") ) {
                this.makeVerbQuery();
                if(!t2)
                    this.makeVerbPersonFor_query();   
                else {
                    this.stack = this.addVerb__di(query);
                    this.makeVerbPersonFor__di();
                }
            }
            else {
            
            if(t2)
                this.stack = this.addVerb__di();
            
            // STEP 10 : SAHIS EKLERI

            if (time.equals("22"))
                makeVerbPersonFor__yor();
            else if(time.equals("21"))
                makeVerbPersonFor_21();
            else if( time.equals("can") && !negative )
                makeVerbPersonFor__ebilir();
            else if(time.equals("can") )
                makeVerbPersonFor_21(); // genis zamandaki cekimle ayni
            else if(time.equals("must"))
                makeVerbPersonFor__meli();
            else if( t2 || time.equals("11") )
                makeVerbPersonFor__di();
            else if( time.equals("31") || time.equals("32") || time.equals("33") || time.equals("34") || time.equals("bound") )
                makeVerbPersonFor__ecek();
            }
            // STEP 11 : QUERY
            if(query && time.equals("11"))
                makeVerbQuery();
/*
            // STEP 12 : BOUND ICIN GEREKLI AYAK
            if( time.equals("bound") )
                suffixeBound();
                */
            


                // STEP 15 : Sinif ici hasAlreadyBeenConjugated boolean i true hal getiriliyor ki, daha guvenli bir kullanim olsun
                this.hasAlreadyBeenConjugated = true ;


            
        }

    }


    /**
    * Fiili pasif hale getiriyor.
    * @return void
    */
    private void makeVerbPassive() {

        if ( stack.matches(".*[aeiou\u0131\u00FC\u00F6]$") )
            stack = stack.concat("n");
        else if ( stack.endsWith("l") )
            stack = stack.concat(lastVowelQuattro(stack)+"n");
        else
            stack = stack.concat(lastVowelQuattro(stack)+"l");

    }

    /**
    * yap - an
    * @return void
    */
    private void makeMode1() {

        if ( stack.matches(".*[aeiou\u0131\u00F6\u00FC]$") )
            stack = stack.concat("y"+lastVowelBinary(stack)+'n');
        else
            stack = stack.concat(lastVowelBinary(stack)+"n");

    }

    /**
    * yap - tigi
    * @return void
    */
    private void makeMode2(int p) {
        if(p==1)
            stack = stack.concat("d"+lastVowelQuattro(stack)+'\u011F'+lastVowelQuattro(stack)+'m');
        else if (p==2)
            stack = stack.concat("d"+lastVowelQuattro(stack)+'\u011F'+lastVowelQuattro(stack)+'n'+lastVowelQuattro(stack)+'z');
        else if (p==11)
            stack = stack.concat("d"+lastVowelQuattro(stack)+'\u011F'+lastVowelQuattro(stack)+'m'+lastVowelQuattro(stack)+'z');
        else
            stack = stack.concat("d"+lastVowelQuattro(stack)+'\u011F'+lastVowelQuattro(stack));
    }


    /**
    * Can negatifte ; yap - a - genis zaman ekleri
    * @return void
    */
    private void makeNegativeCan() {

        String [] s = new String[2];
        s = prepareForSuffixing(stack);

        s[1] = solidify(s[1]) ;

        if( s[1].matches("[aeiou\u0131\u00FC\u00F6]$") )
            stack = s[0]+s[1]+'y'+lastVowelBinary(s[1]);
        else
            stack = s[0]+s[1]+lastVowelBinary(s[1]);
    }


    /**
    * Soru yuklemi yapiyor
    * @return void
    */
    private void makeVerbQuery() {
        this.stack = this.stack + " m" + this.lastVowelQuattro(stack);
    }

    /**
    * fiile yor eki ekliyor
    * @return fiilin -yor eki eklenmis hali
    */
    private String addVerb__yor() {
        /*exceptions
         */
        String [] vp = prepareForSuffixing(stack);



        String vpn=solidify(vp[1]);

        if( vpn.matches(".*[aeiou\u00FC\u0131\u00F6]$") )
            vpn=vpn.substring(0,vpn.length()-1);

        return vp[0]+vpn+lastVowelQuattro(vpn)+"yor";
    }

    /**
    * fiile -mis eki ekliyor.
    * @return fiilin -mis eki eklenmis hali
    */
    private String addVerb__mis() {
        return stack.concat("m"+lastVowelQuattro(stack)+'\u015F');
    }




    /**
    * @return Fiilin -mekte eki eklenmis hali
    */
    private String addVerb__mekte() {
        return stack.concat("m"+lastVowelBinary(stack)+"kt"+lastVowelBinary(stack)+"y");
    }



    /**
    * @return Fiilin di eki eklenmis hali
    */
    private String addVerb__di() {

        if ( stack.matches(".*[\u00E7fhkps\u015Ft]$") )
            return stack.concat("t"+lastVowelQuattro(stack));
        /*
        else if ( stack.matches(".*[aeiou\u0131\u00FC\u00F6]$") )
            return stack.concat("yd"+lastVowelQuattro(stack));
            */
        else
            return stack.concat("d"+lastVowelQuattro(stack));
    }

    
    /**
     * addVerb__di nin ayn?s? fakat ;
     * bu overloaded ( a??r? yüklenmi? hali ) bunda parametre olarak
     * bunun bir soru yüklemi olup olmad???na bak?l?yor.
     * Soru yüklemi de?ilse addVerb__di() metoduyla ayn? islem görülüyor.
     * Yoksa önlerine bir -y koyulup alamalar? geeken hali al?yorlar.
     * @return Fiilin di eki eklenmis hali
     */
    private String addVerb__di( boolean isQuery ) {

        if(!isQuery)
            return addVerb__di();
        else 
            return this.stack+"yd"+lastVowelQuattro(this.stack);
    }
    
    
    
    
    
    
    
    
    /**
    * @return Fiilin -meli -mali ile cekilmis hali
    */
    private String addVerb__meli() {
        if ( lastVowelBinary(stack)=='a' )
            return stack + "mal\u0131" ;
        else
            return stack + "meli" ;
    }


    /**
    * @return Fiilin -ebilir ile cekilmis hali
    */
    private String addVerb__ebilir() {

        String s = solidify(stack);

        if( s.matches(".*[aeiou\u0131\u00FC\u00F6]$") )
            return s + 'y' + lastVowelBinary(s) + "bilir" ;
        else
            return s  + lastVowelBinary(s) + "bilir" ;
    }


    /**
    * @return Fiilin -ecek gelecek zaman eki eklenmis hali
    */
    private String addVerb__ecek() {
        String [] s = prepareForSuffixing(stack);
        s[1] = solidify(s[1]);

        if ( s[1].matches(".*[aeiou\u0131\u00FC\u00F6]$") )
            return s[0]+' '+s[1]+'y' +lastVowelBinary(s[1]) + 'c' + lastVowelBinary(s[1]) + 'k';
        else
            return s[0]+' '+s[1] + lastVowelBinary(s[1]) + 'c' + lastVowelBinary(s[1]) + 'k' ;
    }


    /**
    * @return Fiilin genis zamanli cekimi
    */
    private String addVerb_21() {
        if(negative)
            return stack;

        String [] s = prepareForSuffixing(stack);

        if (s[1].equals("ol") || s[1].equals("bul") || s[1].equals("dur") || s[1].equals("vur") )
            return s[0]+' '+s[1]+"ur";
        else if ( s[1].equals("al") ) return s[0]+' '+s[1]+"al\u0131r";
        else if ( s[1].equals("ver") || s[1].equals("gel") ) return s[0]+' '+s[1]+"ir";
        else if ( s[1].equals("g\u00F6r") ) return s[0]+' '+"g\u00F6r\u00FCr";
        else if ( s[1].equals("git") || s[1].equals("et") || s[1].equals("hisset") || s[1].equals("hallet") || s[1].equals("hazmet") )
            return s[0]+' '+s[1].substring(0,s[1].length()-1)+"der";
        else {

            if ( s[1].matches(".*[aeiou\u0131\u00FC\u00F6]$") )
                return s[0]+' '+s[1] + 'r' ;
            else {
                char lastVowel = lastVowel(stack);

                if( s[1].matches(".*[aeiou\u0131\u00FC\u00F6].*[aeiou\u0131\u00FC\u00F6].*") )
                    return s[0]+' '+s[1]+lastVowelQuattro(stack)+'r';
                else
                    return s[0]+' '+s[1]+lastVowelBinary(s[1])+'r';
            }
        }
    }




    /**
    * Genis zaman icin cekim yapiyor
    * @return void
    */
    private void makeVerbPersonFor_21() {

        char joker;

        if(!negative) {

            switch(person) {
            case 1 : stack = stack + lastVowelQuattro(stack) + 'm' ; break;
            case 2 : stack = stack + 's' + lastVowelQuattro(stack) + 'n' ; break;
            case 11 : stack = stack + lastVowelQuattro(stack) + 'z' ; break;
            case 12 : stack = stack +'s' + lastVowelQuattro(stack) + 'n' + lastVowelQuattro(stack) + 'z' ; break;
                //case 13 : makePlural(); break;
            }
        }

        else {
            if (stack.substring(stack.length()-1).equals("a"))
                joker = '\u0131';
            else
                joker = 'i';

            switch(person) {
            case 1 : stack = stack + 'm'; break;
            case 2 :stack = stack + "zs" + joker + 'n'; break;
            case 3 : stack = stack + 'z'; break;
            case 11 : stack = stack + 'y' + joker + 'z';break;
            case 12 : stack = stack + "zs" + joker + 'n' + joker + 'z'; break;
            case 13 : stack = stack + "zl" + stack.substring(stack.length()-1) + 'r'; break;
            }
        }

    }




    /**
    * Gelecek zaman eki -ecek icin cekim yapiyor.
    * @return void
    */
    private void makeVerbPersonFor__ecek() {

        char c = lastVowel(stack) == 'a' ? '\u0131' : 'i' ;

        switch(this.person) {
        case 1 : stack = stack.substring(0,stack.length()-1) + "\u011F\u0131m";break;
        case 2 : stack = stack + "s\u0131n" ;break;
        case 11 : stack = stack.substring(0,stack.length()-1) + "\u011F\u0131z";break;
        case 12 : stack = stack + "s\u0131n\u0131z" ;break;
        case 13 : makePlural();
        }
    }




    /**
    * -di eki almis fiillerin kisi cekimini hallediyor.
    * @return void
    */
    private void makeVerbPersonFor__di() {

        switch(this.person) {
        case 1 : stack = stack.concat("m");break;
        case 2 : stack = stack.concat("n");break;
        case 11 : stack = stack.concat("k");break;
        case 12 : stack = stack + 'n' + lastVowelQuattro(stack) + 'z';
        }

    }

    
    /**
     * Soru eki alm?? oalnlar için ?ah?s eklerini ekliyor.
     * @return void
     */
    private void makeVerbPersonFor_query() {
        switch(this.person) {
        case 1 : this.stack = this.stack.concat("y"+this.stack.charAt(this.stack.length()-1)+'m');break;
        case 2 : this.stack = this.stack.concat("s"+this.stack.charAt(this.stack.length()-1)+'n');break;
        case 11 : this.stack = this.stack.concat("y"+this.stack.charAt(this.stack.length()-1)+'m');break;
        case 12 : this.stack = this.stack.concat("s"+this.stack.charAt(this.stack.length()-1)+'n'+this.stack.charAt(this.stack.length()-1)+'z');
        }

    }

    /**
    * -yor cekimini almis fiillerin kisi cekimleri icin
    * @return void
    */
    private void makeVerbPersonFor__yor() {

        switch(this.person) {
        case 1 : stack =  stack + "um" ;break;
        case 2 : stack = stack + "sun" ;break;
        case 11 : stack = stack +"uz" ;break;
        case 12 : stack = stack + "sunuz" ;break;
        case 13 : stack = stack + "lar" ;
        }
    }


    /**
    * -ebilir eki icin kisi cekimi yapiyor.
    * @return void
    */
    private void makeVerbPersonFor__ebilir() {

        switch(this.person) {
        case 1 : stack =  stack + "im" ;break;
        case 2 : stack = stack + "sin" ;break;
        case 11 : stack = stack +"iz" ;break;
        case 12 : stack = stack + "siniz" ;break;
        case 13 : stack = stack + "ler" ;
        }


    }


    /**
    * -meli eki ile bitenler icin kisi cekimi yapiyor.
    * @return void
    */
    private void makeVerbPersonFor__meli() {
        char c = stack.charAt(stack.length()-1);
        switch(this.person) {
        case 1 : stack =  stack + 'y' + c + 'm' ; break ;
        case 2 : stack = stack + 's' + c + 'n' ; break ;
        case 11 : stack = stack + 'y' + c + 'z' ;break;
        case 12 : stack = stack + 's' + c + 'n' + c + 'z' ;break;
        case 13 : makePlural() ;
        }

    }




    /**
    * cogul yapiyor.
    * @return void
    */
    private void makePlural() {
        this.stack = this.stack + 'l' + lastVowelBinary(this.stack) + 'r' ;
    }



    /**
    * dolcak
    */
    private void suffixeBound() {}






    /**
    * eger yuklemi yapiyor.
    * @return void
    */
    private void makeVerbIf() {
        if( this.stack.matches(".*[aeiou\u0131\u00FC\u00F6]$") )
            this.stack = this.stack + "ys" + lastVowelBinary(stack);
        else
            this.stack = this.stack + 's' + lastVowelBinary(stack);
        /**
        * Yuklemi olumsuz hale cekiyor
        */                                                   }
    private void makeVerbNegative() {
        this.stack = this.stack + 'm' + lastVowelBinary(stack) ;
    }







    private void makeVerbalNoun1S() {

        String n = fallVowel(stack);

        if ( n.matches("[ae\u0131io\u00F6u\u00FC]$") )
            this.stack = n+'y'+lastVowelQuattro(n)+'m';
        else
            this.stack = n+lastVowelQuattro(n)+'m';
    }

    private void makeVerbalNoun2S() {
        this.stack = this.stack.concat("s"+lastVowelQuattro(stack)+"n");

    }
    private void makeVerbalNoun3S() {

        this.stack = this.addVerb__di()+'r';
    }
    private void makeVerbalNoun1P() {
        String n = fallVowel(stack);

        if ( n.matches("[ae\u0131io\u00F6u\u00FC]$") )
            stack = n+'y'+lastVowelQuattro(n)+'z';
        else
            stack = n+lastVowelQuattro(n)+'z';

    }
    private void makeVerbalNoun2P() {

        stack = stack+'s'+lastVowelQuattro(stack)+'n'+lastVowelQuattro(stack)+'z';

    }
    private void makeVerbalNoun3P() {
        this.makeVerbalNoun3S();
        this.makePlural();

    }







}
