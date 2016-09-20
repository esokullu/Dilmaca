package dilbaz.en2tr;

/**
* Noun sinifi
* Isimler,sifatlar,zarflar,insan isimleri, çogul, tamlama halinde bulunma ozellikleriyle beraber bu sinif icinde bulunuyor, ve isleniyorlar.
* @author Emre SOKULLU
*/

class Noun extends dilbaz.en2tr.util.Suffixes implements NounOBJECT,ConjugatableVerbalOBJECT {

    private String stack;
    private int type;

    private boolean isPlural=false;
    private boolean isConjunctionNoun=false;
    private boolean withArticle=false;

    /**
    * -1 false
    * diger pozitif degerler fiilin propertysi
    */
    private int mayBeVerb=-1;



    /**
    * Noun yapilandirici yordami
    * withArticle illa ki false.
    * @param s kelime
    * @param i ozelligi
    * @return void
    */
    public Noun ( String s , int i ) {

        this.stack=s.toLowerCase();

        if ( i%100 >= 10 )
            this.isPlural = true;


        if ( i >= 100 ) 
            this.mayBeVerb = (i/100)-1 ;

        this.type = i%10;

        if(type==1)
            this.isConjunctionNoun=true;


        // withArticle illaki false
    }



    /**
    * Noun yapilandirici yordami
    * withArticle = true ;
    * @param s kelime
    * @param p cogul mu ?
    * @param c tamlama ekli 
    * @return void
    */
    public Noun ( String s , boolean p , boolean c  ) {

        this.stack=s.toLowerCase();
        this.type = -1;
        this.mayBeVerb=-1;
        this.isPlural=p;
        this.isConjunctionNoun = c;
        this.withArticle=true;
    }



    /**
    * Cogul mu degil mi ?
    * @return boolean
    */
    public boolean isPlural() {
        return isPlural;
    }

    /**
    * Fiil olabilir mi ?
    * @return boolean
    */
    public boolean mayBeVerb() {
        if ( mayBeVerb == -1 )
            return false;
        else
            return true;
    }


    /**
    * Sifat mi ?
    * @return boolean
    */
    public boolean isAdjective() {
        if (type==2)
            return true;
        else
            return false;
    }

    /**
    * Zarf mi ?
    * @return boolean
    */
    public boolean isAdverb() {
        if(type==3)
            return true;
        else
            return false;
    }

    /**
    * Isim mi ( tamlama da olabilir ) ?
    * @return boolean
    */ 
    public boolean isNoun() {
        if( type==0 || type==1 )
            return true;
        else return false;
    }

    /**
    * Insan ismi mi ?
    * @return boolean
    */
    public boolean isHumanName() {

        if(type==7)
            return true;
        else return false;
    }


    /**
    * Tamlama ismi mi ?
    * @return boolean
    */

    public boolean isConjunctionNoun() {

        if (isConjunctionNoun)
            return true;
        else
            return false;
    }




    public boolean withArticle() {
        return withArticle;
    }

    /**
    * Kelimeyi döndür ( toString() fonksiyonu gibi )
    * mayBeVerb ayrimi yapiliyor. Yani mayBeVerb'se sadece isim kismi dönüyor.
    * @return Hafizadaki kelime
    */	
    public String getNoun() {
        try {
            // burada mayBeVerb e deðl de artý iþaretine bakýyoruz
            // çünkü bir isim mayBeVerb olup .. CYCLE3 e isim hali çaðrýlabilir de ..
            if (stack.indexOf('+')!=-1)
                return stack.substring(0,stack.indexOf('+'));
            else
                return stack;
        } catch ( StringIndexOutOfBoundsException e ) {
            return stack;
        }
    }


    /**
     * VerbalOBJECT i implement etmenin verdiði bir zorunluluk olarak bu 
     * metodu override (iptal) ediyor.
     * getNoun() metoduyla ayný i yapýyor. Nitekim içinden de onu çaðýrýyor sadece.
     * @return Isim
     */
    public String getInfinitive() {
        return getNoun();
    }


    public Noun toNoun() {
        this.cantBeVerb();
        return this;
    }


    /**
    * ConjugatedVerb class casting iþlemini yerine getiriyor.
    * Buna göre buradan oluþturulan fiil geniþ zaman ( kod adý 21) zaman?nda çekiliyor.
    * Eðer isPlural deðiþkeni  true ise bu ConjugatedVerb ün þahsý 3. tekil þahýs "3" oluyor.
    * <br> Bu metod CYCLE3 ayaðýnda çaðrýlýyor.
    * <br> Noun un mayBeVerb olma durumu olup olmadýðý kontrol edilmiyor, çünkü aksi halde döndürülecek
    * null nesnesi herþeyi bozabilir. 
    * Ondan dolayý belki ileride buraya bir <b>throw</b> mekanizmasý yerleþtirilebilir.
    * @return (ConjugatedVerb)this
    */ 
    public ConjugatedVerb toConjugatedVerb() {
        if(this.isPlural)
            return new ConjugatedVerb(this.getVerb(),this.mayBeVerb,"21",3,false,false,false,0); // 3. tekil þahýs
        else
            return new ConjugatedVerb(this.getVerb(),this.mayBeVerb,"21",0,false,false,false,0); // bilinmeyen þahýs

    }



    /**
    * Fiili döndürür. mayBeVerb dikkate alinarak.
    * @return fiil
    */
    public String getVerb() {
        try {
            if (mayBeVerb!=-1)
                return stack.substring(stack.indexOf('+')+2);
            else
                return stack;
        } catch ( StringIndexOutOfBoundsException e ) {
            return stack;
        }

    }


    /**
    * getNoun() la ayni. Fakat getStack() cok genel bir yourdam oldugu icin ve
    * Cycle3 sinifinda dealWithUnchangeablesAtTheBeginningOfTheSentence() yordaminda
    * bu yordama ihtiyac duyuldugundan, bu yordam turedi.
    * @return Hafizadaki kelime
    */	
    public String getStack() {

        return this.getNoun();
    }


    public int getType() {

        int toReturn = (mayBeVerb+1)*100 + type;
        if(isPlural)
            toReturn+=10;
        return toReturn;

    }


    /**
    * Fiilin properttsini dondurur.
    * @return Fiilin property'si
    */
    public int getVerbProperty() {
        return mayBeVerb;
    }

    /**
    * Kelimeyi genisletir
    * @param s Hafizadaki kelimeye eklenecek. Aradaki bosluk otomatik olarak birakiliyor. eger s virgülse, o zaman bosluk yok.
    * @return void
    */

    public void append(String s) {
        try {
            if ( s.charAt(0)== ',' )
                stack = stack.concat(s);
            else
                stack = stack.concat(' '+s);
        } catch (StringIndexOutOfBoundsException e) {}  // bir sey yapmiyoruz cunku eklenmesi istenen seyin 0 harften olustugunu anladik.
    }


    /**
     * VerbalOBJECT'e zarf atar
     * @param s Zarf
     * @return void
     */
    public void appendAdverb(String s) {
        this.stack = s.concat(" "+this.stack); 
    }
    
    
    
    
    /**
     * Türü yeniden atar
     * @param t Yeni tür
     * @return void
     */
    public void setType(int t) {
        this.type=t;
    }




    /**
    * Yeni kelime atar
    * @param s Yeni kelime
    * @return void
    */
    public void setStack(String s) {
        stack = new String(s);
    }




    /**
    * Fiil olma olasiligi tasiyan Nounlarin bu olasiliklarini ortadan kaldirir.
    * @return void
    */
    public void cantBeVerb() {
        mayBeVerb=-1;
        stack=getNoun();
    }




    /**
     * Insan ismi olabilir mi olamaz mi sorguluyor. Type 9 CYCLE 2 da sahipsiz kelimelere veriliyor.
     * Type 9 CYCLE 3 ün ilk yordamlarýnda harcanan bir tür.
     * @return boolean
     */
    public boolean mayBeHumanName() {
        if(this.type==9)
            return true;
        else
            return false;
    }


    /**
     * Type 9 u 0 a çeviriyor. Yani bu HumanName olamaz.
     * @return void
     */
    public void isntHumanName() {
        this.type=0;
    }  
    
    
    
    /**
     * CYCLE3 te baslaraki bir yordam tarafindan cagiriliyor.
     * mayBeHumanName i gerçek HumanName e dönüþtürüyor.
     * @return void
     */
    public void setHumanName() {
        this.type=7;
    }


    /**
     * Fiilin icinde hic Comma bulunup bulunmadýðýný sorguluyor.
     *  Ozellikle PHRASE sýnýfýnda kullanýlan bir yordam.
     * @return boolean
     */
    public boolean hasComma() {

        if (stack.indexOf('&')!=-1 || stack.indexOf(',')!=-1 || stack.indexOf(" ve ")!=-1 )
            return true;
        else
            return false;
    }

    /**
     * En son Comma'dan itibaren stack'i ikiye bolup
     * uc elemanli bir String [] dizisi halinde donduruyor.
     * Ilk eleman, Comma'dan onceki bolum<br>
     * Ikýnci elemean, Comma'dan sonraki bolum<br>
     * Son eleman, ayrac ( , & ve .. gibi )<br>
     * Gene ozellikle PHRASE sinifi tarafindsan kullanilan bir yordam bu.
     * @return boolean
     */
    public String [] divideByComma() {

        if(this.hasComma()) {
            int i1 = stack.lastIndexOf('&');
            int i2 = stack.lastIndexOf(',');
            int i3 = stack.lastIndexOf(" ve ");

            int i = Math.max(i1,Math.max(i2,i3));
            if(i==i3) // ve
                return new String [] {stack.substring(0,i).trim(),stack.substring(i+4).trim()," ve"};
            else if (i==i1) // &
                return new String [] {stack.substring(0,i).trim(),stack.substring(i+1).trim()," "+stack.charAt(i)};
            else  // ,
                return new String [] {stack.substring(0,i).trim(),stack.substring(i+1).trim(),stack.substring(i,i+1)};
        }
        else
            return new String [] {stack,"",""};



    }







    /*
    *
    *
    * Bundan sonraki kisimda hep ekler olacak
    *
    *
    */

    /**
    * Kelimeyi cogul yapar
    * @return void
    */
    public void makePlural()
    {

        stack = stack.concat("l"+lastVowelBinary(stack)+'r');

    }


    /**
    * 1. Tekil Kisi possessive eki ekler
    * @return possessive eki eklenmis hali ile kelime
    */
    public void makePossessiveMy() {
        String n = fallVowel(stack);

        if ( n.matches(".*[aeiou\u0131\u00F6\u00FC]$") )
            stack = n+'m';
        else
            stack = n+lastVowelQuattro(n)+'m';
    }


    /**
    * 2. Tekil Kisi possessive eki ekler
    * @return possessive eki eklenmis hali ile kelime
    */    
    public void makePossessiveYour() {
        String n = fallVowel(stack);

        if ( n.matches(".*[ae\u0131io\u00F6u\u00FC]$") )
            stack = n+'n'+lastVowelQuattro(n)+'z';
        else
            stack = n+lastVowelQuattro(n)+'n'+lastVowelQuattro(n)+'z';
    }




    /**
    * 3. Tekil Kisi possessive eki ekler
    * @return possessive eki eklenmis hali ile kelime
    */
    public void makePossessiveHis() {
        String n = fallVowel(stack);

        if ( n.matches(".*[ae\u0131io\u00F6u\u00FC]$") )
            stack = n+'s'+lastVowelQuattro(n);
        else
            stack = n+lastVowelQuattro(n);

    }





    /**
    * 1. Cogul Kisi possessive eki ekler
    * @return possessive eki eklenmis hali ile kelime
    */
    public void makePossessiveOur() {
        String n = fallVowel(stack);

        if ( n.matches(".*[ae\u0131io\u00F6u\u00FC]$") )
            stack = n+'m'+lastVowelQuattro(n)+'z';
        else
            stack = n+lastVowelQuattro(n)+'m'+lastVowelQuattro(n)+'z';
    }


    public void makePossessiveTheir() {

        this.makePlural();
        this.stack = this.stack+lastVowelQuattro(this.stack);

    }



    /**
    * Tamlama eki ekler
    * @return tamlama eki eklenmis hali ile kelime
    */
    public void makeDefiner() {


        if( stack.matches("^[A-Z].*") )
        {
            // daha da eklenecek
            if( stack.endsWith("a") || stack.endsWith("e") || stack.endsWith("\u0131") || stack.endsWith("i") || stack.endsWith("o") || stack.endsWith("u") || stack.endsWith("\u00FC") || stack.endsWith("\u00F6") )
                // apostrophe konacak
                stack = stack + "\'" + 'n' + lastVowelQuattro(stack) + 'n' ;

            else

                stack = softify(stack) + "\'" + lastVowelQuattro(stack) + 'n' ;

        }
        else

        {
            if( stack.endsWith("a") || stack.endsWith("e") || stack.endsWith("\u0131") || stack.endsWith("i") || stack.endsWith("o") || stack.endsWith("u") || stack.endsWith("\u00FC") || stack.endsWith("\u00F6") )

                stack = stack + 'n' + lastVowelQuattro(stack) + 'n' ;

            else

                stack = softify(stack) + lastVowelQuattro(stack) + 'n' ;

        }
    }



    /**
    * Tamlanan eki ekler
    * @return tamlanan eki eklenmis hali ile kelime
    */
    public void makeDefined() {

        isConjunctionNoun = true;

        if( stack.matches("^[A-Z].*") )
        {
            if( stack.endsWith("a") || stack.endsWith("e") || stack.endsWith("\u0131") || stack.endsWith("i") || stack.endsWith("o") || stack.endsWith("u") || stack.endsWith("\u00FC") || stack.endsWith("\u00F6") )
                stack = stack + "\'" + 's' + lastVowelQuattro(stack) ;

            else

                stack = softify(fallVowel(stack)) + "\'" + lastVowelQuattro(stack) ;

        }
        else

        {
            if( stack.endsWith("a") || stack.endsWith("e") || stack.endsWith("\u0131") || stack.endsWith("i") || stack.endsWith("o") || stack.endsWith("u") || stack.endsWith("\u00FC") || stack.endsWith("\u00F6") )

                stack = stack + 's' + lastVowelQuattro(stack) ;

            else

                stack = softify(fallVowel(stack)) + lastVowelQuattro(stack)  ;

        }
    }





    /**
    * Eki yorumluyor
    * @param fiil nesnesi ; ona gore kelimemiz ek aliyor.
    * @return void
    */    
    public void commentSuffixe(int type) {

        switch(type) {
        case 0 : break;
        case 1 : makeObjective(); break;
        case 2 : makeDative(); break;
        case 3 : makeLocative(); break;
        case 4 : makeAblative(); break;
        case 5 : makeDefiner(); break;
        case 6 : stack = stack.concat(" ile");
        }
    }

    /**
    * -i hal eki ekliyor
    * @return void
    */
    public void makeObjective() {

        String temp = softify(fallVowel(stack));

        if ( stack.matches(".*[aeiou\u0131\u00FC\u00F6]$") ) {

            if ( type==1 || isConjunctionNoun )
                stack = temp.concat("n"+lastVowelQuattro(temp));
            else
                stack = temp.concat("y"+lastVowelQuattro(temp));
        }
        else
            stack =  temp + lastVowelQuattro(stack) ;
    }

    /**
    * -e hal eki ekliyor
    * @return void
    */

    public void makeDative() {



        String temp = softify(fallVowel(this.stack));

        if ( this.stack.matches(".*[aeiou\u0131\u00FC\u00F6]$") ) {

            if ( type==1 || isConjunctionNoun)
                this.stack = temp.concat("n"+lastVowelBinary(temp));
            else
                this.stack = temp.concat("y"+lastVowelBinary(temp));
        }
        else
            this.stack =  temp + lastVowelBinary(temp) ;

    }


    /**
    * -de hal eki ekliyor
    * @return void
    */

    public void makeLocative() {

        if ( type == 1 || isConjunctionNoun ) // tamlama mi
            stack = stack + "nd" + lastVowelBinary(stack) + 'n';

        else {

            if ( stack.matches(".*[\u00E7fhkps\u015Ft]$") )
                stack = stack + 't' + lastVowelBinary(stack);
            else
                stack = stack + 'd' + lastVowelBinary(stack);
        }
    }

    /**
    * -den hal eki
    * @return void
    */
    public void makeAblative() {
        makeLocative();
        stack = stack + 'n' ;
    }

}
