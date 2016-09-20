package dilbaz.en2tr;


/**
* Zamirlerle ilgili sinif
* Daha da gelistirilebilir.
* @author emre Sokullu
*/

class Pronoun implements NounOBJECT {

    private int type;
    private String pronoun;
    private String stack = new String();

    /**
    * Pronoun yapilandirici yordami 
    * @param s pronoun un kendisi ( ingilizce ); herhangi bir toLowerCase() islemi gerceklestirilmiyor. Cunku zaten CYCLE2.findPronouns dan gelen bu veri sekillenmis olarak elimize ulasiyor.
    * @param i hangi tur pronoun oldugu
    * @return void
    */
    public Pronoun(String s,int i) {
        this.type=i;
        this.pronoun=s;
    }


    /**
    * Pronoun'un property sini döndürüyor.
    * @return pronoun property
    */
    public int getType() {
        return type;
    }

    /**
    * Pronoun un kendisini döndürüyor
    * @return Pronoun in kendisi
    */
    public String getPronoun() {
        return pronoun;
    }


    /**
    * Pronoun un standart tükçesini döndürüyor.
    * "That" gibi her durumda geçerli degil.
    * Daha onceden stack e bir deger verilmisse o zaman once bunu donduruyor.
    * @return pronoun un standart türkçe karsiligi
    */
    public String getStack() {

        if (stack.equals("")) {

            if (pronoun.equals("i")||pronoun.equals("me"))
                return "ben";
            else if (pronoun.equals("you")||pronoun.equals("u"))
                return "siz";
            else if (pronoun.equals("thou"))
                return "sen";
            else if (pronoun.equals("he")||pronoun.equals("she")||pronoun.equals("it")||pronoun.equals("him"))
                return "o";
            else if (pronoun.equals("they")||pronoun.equals("them"))
                return "onlar";
            else if (pronoun.equals("we")||pronoun.equals("us"))
                return "biz";


            if (pronoun.equals("i one"))
                return "ben tek ba\u015F\u0131ma";
            else if (pronoun.equals("you one") || pronoun.equals("u one"))
                return "siz tek ba\u015F\u0131n\u0131za";
            else if (pronoun.equals("thou one"))
                return "sen tek ba\u015F\u0131na";
            else if (pronoun.equals("he one")||pronoun.equals("she one")||pronoun.equals("it one"))
                return "o tek ba\u015F\u0131na";
            else if (pronoun.equals("they one"))
                return "onlar tek ba\u015Flar\u0131na";
            else if (pronoun.equals("we one"))
                return "biz tek ba\u015F\u0131m\u0131za";


            else if (pronoun.equals("any"))
                return "herhangi";
            else if (pronoun.equals("some"))
                return "baz\u0131";
            else if (pronoun.equals("every"))
                return "her";
            else if (pronoun.equals("each"))
                return "herbir";
            else if (pronoun.equals("all")||pronoun.equals("whole"))
                return "t\u00FCm";
            else if (pronoun.equals("this") || pronoun.equals("these"))
                return "bu";
            else if (pronoun.equals("those"))
                return "\u015Fu";
            else if (pronoun.equals("my"))
                return "benim";
            else if (pronoun.equals("your")||pronoun.equals("ur"))
                return "sizin";
            else if (pronoun.equals("his") || pronoun.equals("her") || pronoun.equals("its") )
                return "onun";
            else if (pronoun.equals("our"))
                return "bizim";
            else if (pronoun.equals("their"))
                return "onlar\u0131n";
            else if (pronoun.equals("my own"))
                return "benim kendi";
            else if (pronoun.equals("your own")||pronoun.equals("ur own"))
                return "sizin kendi";
            else if (pronoun.equals("his own") || pronoun.equals("her own") || pronoun.equals("its own") )
                return "onun kendi";
            else if (pronoun.equals("our own"))
                return "bizim kendi";
            else if (pronoun.equals("their own"))
                return "onlar\u0131n kendi";
            else if (pronoun.equals("mine"))
                return "benimki";
            else if (pronoun.equals("yours")||pronoun.equals("urs"))
                return "sizinki";
            else if (pronoun.equals("thine"))
                return "seninki";
            else if (pronoun.equals("hers"))
                return "onunki";
            else if (pronoun.equals("ours"))
                return "bizimki";
            else if (pronoun.equals("theirs"))
                return "onlar\u0131nki";
            else if (pronoun.equals("myself"))
                return "kendi kendime";
            else if (pronoun.equals("yourself")||pronoun.equals("urself")||pronoun.equals("himself")||pronoun.equals("herself")||pronoun.equals("itself"))
                return "kendi kendine";
            else if (pronoun.equals("ourselves"))
                return "kendi kendimize";
            else if (pronoun.equals("yourselves")||pronoun.equals("urselves"))
                return "kendi kendinize";
            else if (pronoun.equals("themselves"))
                return "kendi kendilerine";
            else if (pronoun.equals("somebody")||pronoun.equals("someone"))
                return "biri";
            else if (pronoun.equals("something"))
                return "bir\u015Fey";
            else if (pronoun.equals("everybody"))
                return "herkes";
            else if (pronoun.equals("everything"))
                return "her\u015Fey";


            else if ( pronoun.equals("more"))
                return "daha fazla";
            else if ( pronoun.equals("less") )
                return "daha az";
            else if ( pronoun.equals("much more"))
                return "\u00E7ok daha fazla";
            else if ( pronoun.equals("much less") )
                return "\u00E7ok daha az";

            else if ( pronoun.equals("most") )
                return "pek \u00E7ok";
            else if ( pronoun.equals("least") )
                return "pek az";

            else if ( pronoun.equals("very"))
                return "\u00E7ok"; //çok

            // most people -> birçok insan :: most beatiful -> en güzel (bir de most of)
            // least people -> pek az insan :: least beatiful ->  en az güzel  ( bir de least of)


            else if ( this.pronoun.equals("who") )
                return "kim";
            else if (this.pronoun.equals("what") )
                return "ne";
            else if (this.pronoun.equals("how") )
                return "nas\u0131l";
            else if (this.pronoun.equals("how long") ) // ayný zamanda how much how many
                return "ne kadar";
            else if (this.pronoun.equals("how long ago") )
                return "ne kadar zaman \u00F6nce";
            else if (this.pronoun.equals("how far") ) 
                return "ne kadar uzak";
            else if (this.pronoun.equals("how often") )
                return "ne s\u0131kl\u0131kta";
            else if (this.pronoun.equals("how well") )
                return "ne kadar iyi";
            else if (this.pronoun.equals("how old") )
                return "ka\u00E7 ya\u015F\u0131nda";
            else if (this.pronoun.equals("where") )
                return "nereye";
            else if (this.pronoun.equals("why") || this.pronoun.equals("how come") ) 
                return "ni\u00E7in";
            else if (this.pronoun.equals("when"))
                return "ne zaman";
            else return pronoun;
        }
        else return stack;
    }




    /**
    * Protokol.
    * @param s Yeni stack
    * @return void
    */
public void setStack(String s) {}




    /**
    * Özne zamiri mi ?? ( i - he -she - it - we - you - u - thou- they ) + one lýlar
    * ( i one - you one gibi )
    * <br>Tum ozne zamirleri burada
    * @return boolean
    */
    public boolean isSubjectPronoun() {

        if( type==1 )
            return true;
        else
            return false;

    }

    /**
    * Nesne zamiri mi ( me - you - him - her - it - us - them )
    * you her it özne zamiri de olabilir.
    * @return boolean
    */
    public boolean isObjectPronoun() {

        if ( type==5 )
            return true;
        else if ( type==1 && ( pronoun.equals("you") || pronoun.equals("u") || pronoun.equals("her") || pronoun.equals("it") ) )
            return true;
        else
            return false;

    }

    /**
    * ( my - your - his - her - its - our - their )
    * tum possessive pronouns burada.
    * @return boolean
    */
    public boolean isPossessivePronoun() {

        if(type==2)
            return true;
        else
            return false;

    }

    /**
    * ( this - that - these - those - whole - all - each - some - every - any )
    * that disinda hepsi var.
    * Ama that donmeyecek o istisna bir durum ..
    * @return boolean
    */
    public boolean isDemonstrativePronoun() {
        if(type==0)
            return true;
        else
            return false;

    }





    /**
     * Eger DemonstrativePronoun laran sonra isim gelmiyorsa, sýfat olmuyorlar
     * ve zamir olarak kullanýlýyorlar.
     * Zamir halleri de aþaðýda belirtildiði gibi..
     * @return Pronoun un zamir hali
     */
    public String getDemonstrativePronounInNounForm() {

        if(pronoun.equals("this"))
            return "bunu";
        else if (pronoun.equals("that"))
            return "\u015Funu"; // þunu
        else if (pronoun.equals("these"))
            return "bunlar\u0131"; // bunu
        else if (pronoun.equals("those"))
            return "\u015Funlar\u0131"; // þunlarý
        else if (pronoun.equals("whole")||pronoun.equals("all"))
            return "t\u00FCm\u00FCn\u00FC"; //tümü
        else if (pronoun.equals("each"))
            return "herbirini";
        else if (pronoun.equals("some"))
            return "baz\u0131s\u0131n\u0131";
        else if (pronoun.equals("every"))
            return "hepsini";
        else if (pronoun.equals("any"))
            return "herhangi birini";
        else
            return getStack();

    }

    /**
    * demonstrative + possessive pronouns + comparative pronouns + superlative pronouns = adjective pronouns
    * that yok, o CYCLE 3 de sýfat olup çýkýyor olsa olsa
    * @return boolean
    */
    public boolean isAdjectivePronoun() {
        if(type==0 || type==2 || type==10 || type==11 )
            return true;
        else
            return false;
    }



    /**
    * ( myself ..)
    * hepsi dahil
    * @return boolean
    */
    public boolean isReflexivePronoun() {
        if(type==4)
            return true;
        else
            return false;
    }

    /**
    * that so gibi karisiklik cikartan pronounlar
    * @return boolean
     */

    public boolean isUndefinedPronoun() {
        if(type==6)
            return true;
        else
            return false;
    }


    public boolean isNounPronoun() {

        if ( type==8 )
            return true;
        else
            return false;
    }


    public boolean isYesNoPronoun() {

        if ( type==9 )
            return true;
        else
            return false;

    }

    public boolean isNounPossessivePronoun() {

        if ( type==3 )
            return true;
        else
            return false;
    }



    /**
     * more ve less ve much more ve much less
     * @return boolean
     */
    public boolean isComparativePronoun() {
        if(this.type==10)
            return true;
        else
            return false;
    }


    /**
     * most ve least
     * @return boolean
     */
    public boolean isSuperlativePronoun() {
        if(this.type==11)
            return true;
        else
            return false;
    }



    /**
    * who where gibi soru pronounlari
    * @return boolean
    */

    public boolean isQueryPronoun() {

        if (type==7)
            return true ;
        else
            return false ;

    }

    public void enrichPossessivePronoun() {

        if (type==2) // possessiive pronoun
            pronoun = pronoun.concat(" own");


    }




    /*
    *
    *
    * Bundan sonra ekler
    *
    *
    */




    /**
     * Tamlama eki ekler
     * @return Yeni hali ile pronoun Türkçesi
     */
    public void makeDefiner() {

        if ( this.pronoun.equals("i") || this.pronoun.equals("me") || this.pronoun.equals("mine") )
            stack = "benim";
        else if ( this.pronoun.equals("you") || this.pronoun.equals("u") || this.pronoun.equals("yours") || this.pronoun.equals("urs") )
            stack = "sizin";
        else if (this.pronoun.equals("thou")||this.pronoun.equals("thine"))
            stack = "senin";
        else if (this.pronoun.equals("he")||this.pronoun.equals("she")||this.pronoun.equals("it")||this.pronoun.equals("him")||this.pronoun.equals("hers"))
            stack = "onun";
        else if (this.pronoun.equals("they")||this.pronoun.equals("them")||this.pronoun.equals("theirs"))
            stack = "onlar\u0131n";
        else if (this.pronoun.equals("we")||this.pronoun.equals("us")||this.pronoun.equals("ours"))
            stack = "bizim";
        
        // reflexive lere baþlýyoruz.
        else if ( this.pronoun.equals("myself") )
            this.stack = "kendi kendimin";
        
        // sen ve o bu noktada ayný hale dönüþüyorlar
        else if ( this.pronoun.equals("yourself") || this.pronoun.equals("urself") || this.pronoun.equals("himself") || this.pronoun.equals("herself") || this.pronoun.equals("itself")  )
            this.stack="kendi kendinin";
        else if ( this.pronoun.equals("ourselves") )
            this.stack="kendi kendimizin";
        else if ( this.pronoun.equals("yourselves") || this.pronoun.equals("urselves") )
            this.stack="kendi kendinizin";
        else if ( this.pronoun.equals("themselves") )
            this.stack="kendi kendilerinin";
        
        else
            stack = getStack();
    }


    public void makeDefined() {
        stack = getStack();
    }






    /**
    * Eki yorumluyor
    * @param fiil nesnesi ; ona gore kelimemiz ek aliyor.
    * @return void
    */    
    public void commentSuffixe(int type) {

        switch(type) {
        case 0 : stack = getStack();break;
        case 1 : makeObjective();break;
        case 2 : makeDative();break;
        case 3 : makeLocative(); break;
        case 4 : makeAblative();break;
        case 5 : makeDefiner(); break;
        case 6 : stack = getStack() + " ile";break;
        default : stack = getStack();
        }
    }

    /**
    * -i hal eki ekliyor
    * @return Yeni hali ile pronoun Türkçesi
    */
    public void makeObjective() {

        if ( this.pronoun.equals("i") || this.pronoun.equals("me") )
            this.stack =  "beni";
        else if ( this.pronoun.equals("you") || this.pronoun.equals("u") )
            this.stack = "sizi";
        else if ( this.pronoun.equals("thou") )
            this.stack = "seni";
        else if ( this.pronoun.equals("he") || this.pronoun.equals("she") || this.pronoun.equals("it") || this.pronoun.equals("him") )
            this.stack = "onu";
        else if ( this.pronoun.equals("they") || this.pronoun.equals("them") )
            this.stack = "onlar\u0131";
        else if ( this.pronoun.equals("we") || this.pronoun.equals("us") )
            this.stack = "bizi";
        
        
        // reflexive lere baþlýyoruz.
        else if ( this.pronoun.equals("myself") )
            this.stack = "kendi kendimi";
        
        // sen ve o bu noktada ayný hale dönüþüyorlar
        else if ( this.pronoun.equals("yourself") || this.pronoun.equals("urself") || this.pronoun.equals("himself") || this.pronoun.equals("herself") || this.pronoun.equals("itself")  )
            this.stack="kendi kendini";
        else if ( this.pronoun.equals("ourselves") )
            this.stack="kendi kendimizi";
        else if ( this.pronoun.equals("yourselves") || this.pronoun.equals("urselves") )
            this.stack="kendi kendinizi";
        else if ( this.pronoun.equals("themselves") )
            this.stack="kendi kendilerini";
        
       
        else
            this.stack = getStack();
    }

    /**
    * -e hal eki ekliyor
    * @return Yeni hali ile pronoun in Türkçe hali
    */

    public void makeDative() {
        if (pronoun.equals("i")||pronoun.equals("me"))
            stack = "bana";
        else if (pronoun.equals("you")||pronoun.equals("u"))
            stack = "size";
        else if (pronoun.equals("thou"))
            stack = "sana";
        else if (pronoun.equals("he")||pronoun.equals("she")||pronoun.equals("it")||pronoun.equals("him"))
            stack = "ona";
        else if (pronoun.equals("they")||pronoun.equals("them"))
            stack = "onlara";
        else if (pronoun.equals("we")||pronoun.equals("us"))
            stack = "bize";
        
        // reflexive lere baþlýyoruz.
        else if ( this.pronoun.equals("myself") )
            this.stack = "kendi kendime";
        
        // sen ve o bu noktada ayný hale dönüþüyorlar
        else if ( this.pronoun.equals("yourself") || this.pronoun.equals("urself") || this.pronoun.equals("himself") || this.pronoun.equals("herself") || this.pronoun.equals("itself")  )
            this.stack="kendi kendine";
        else if ( this.pronoun.equals("ourselves") )
            this.stack="kendi kendimize";
        else if ( this.pronoun.equals("yourselves") || this.pronoun.equals("urselves") )
            this.stack="kendi kendinize";
        else if ( this.pronoun.equals("themselves") )
            this.stack="kendi kendilerine";
        
        
        
        else
            stack = getStack();

    }


    /**
    * -de hal eki ekliyor
    * @return Yeni hali ile pronoun ?n Türkçe hali
    */

    public void makeLocative() {
        if (pronoun.equals("i")||pronoun.equals("me"))
            stack = "bende";
        else if (pronoun.equals("you")||pronoun.equals("u"))
            stack = "sizde";
        else if (pronoun.equals("thou"))
            stack = "sende";
        else if (pronoun.equals("he")||pronoun.equals("she")||pronoun.equals("it")||pronoun.equals("him"))
            stack = "onda";
        else if (pronoun.equals("they")||pronoun.equals("them"))
            stack = "onlarda";
        else if (pronoun.equals("we")||pronoun.equals("us"))
            stack = "bizde";
       
        // reflexive lere baþlýyoruz.
        else if ( this.pronoun.equals("myself") )
            this.stack = "kendi kendimde";
        
        // sen ve o bu noktada ayný hale dönüþüyorlar
        else if ( this.pronoun.equals("yourself") || this.pronoun.equals("urself") || this.pronoun.equals("himself") || this.pronoun.equals("herself") || this.pronoun.equals("itself")  )
            this.stack="kendi kendinde";
        else if ( this.pronoun.equals("ourselves") )
            this.stack="kendi kendimizde";
        else if ( this.pronoun.equals("yourselves") || this.pronoun.equals("urselves") )
            this.stack="kendi kendinizde";
        else if ( this.pronoun.equals("themselves") )
            this.stack="kendi kendilerinde";
        
        
        
        
        
        
        else
            stack = getStack();
    }

    /**
    * -den hal eki
    * @return Yeni hali ile pronoun in Türkçe hali
    */
    public void makeAblative() {
        if (pronoun.equals("i")||pronoun.equals("me"))
            stack = "benden";
        else if (pronoun.equals("you")||pronoun.equals("u"))
            stack = "sizden";
        else if (pronoun.equals("thou"))
            stack = "senden";
        else if (pronoun.equals("he")||pronoun.equals("she")||pronoun.equals("it")||pronoun.equals("him"))
            stack = "ondan";
        else if (pronoun.equals("they")||pronoun.equals("them"))
            stack = "onlardan";
        else if (pronoun.equals("we")||pronoun.equals("us"))
            stack = "bizden";

        
        // reflexive lere baþlýyoruz.
        else if ( this.pronoun.equals("myself") )
            this.stack = "kendi kendimin";
        
        // sen ve o bu noktada ayný hale dönüþüyorlar
        else if ( this.pronoun.equals("yourself") || this.pronoun.equals("urself") || this.pronoun.equals("himself") || this.pronoun.equals("herself") || this.pronoun.equals("itself")  )
            this.stack="kendi kendinden";
        else if ( this.pronoun.equals("ourselves") )
            this.stack="kendi kendimizden";
        else if ( this.pronoun.equals("yourselves") || this.pronoun.equals("urselves") )
            this.stack="kendi kendinizden";
        else if ( this.pronoun.equals("themselves") )
            this.stack="kendi kendilerinden";
        
        
        else
            stack = getStack();
    }








}
