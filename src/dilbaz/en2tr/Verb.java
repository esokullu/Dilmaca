package dilbaz.en2tr;

class Verb extends dilbaz.en2tr.util.Suffixes implements ConjugatableVerbalOBJECT {

    private String stack;
    private int type;
    private boolean endsWithS;

    /**
    * Verb kurucu yordami.<br>
    * 1000000 den buyuk olmasi endsWithS anlamina geliyor.
    * s stack
    * i property
    */
    public Verb(String s,int i) {

        this.stack=s;

        if(type>=1000000) {
            this.type = i-1000000;
            this.endsWithS=true;
        }
        else {
            this.type=i;
            this.endsWithS=false;
        }

    }


    /**
    * Kelimeyi donduruyor
    * @return Turkce kelime
    */ 
    public String getStack() {
        return this.getInfinitive();
    }




    /**
    * stack in icerigini degistiriyor.
    * @param s Yeni stack icerigi
    * @return void
    */
    public void setStack(String s) {
        this.stack = s;
    }


    /**
    @return tur
    */
    public int getType() {
        return this.type;
    }



    /**
    * mastar halini donduruyor
    * @retun mastar hali
    */
    public String getInfinitive() {
        return this.stack.concat("m"+lastVowelBinary(stack));
    }
    
    /**
     * Verb sýnýfýnýn saf fiil halini döndürüyor.
     * @return Verb sýnýfýnýn saf fiil hali
     */
    public String getVerb() {
        return this.stack;
    }

    /**
     * Cogul olabilir mi
     * s ile mi bitiyor ?
     * @return s ile mi bitiyor boolean ?
     */
    public boolean isPlural() {
        return this.endsWithS;
    }

    /**
     * Isim halini döndürüyor.
     * @return Noun ..
     */
    public Noun toNoun() {
        return new Noun(this.getInfinitive(),this.endsWithS,false);
    }

    /**
    * ConjugatedVerb class casting i?lemini yerine getiriyor.
    * Buna göre buradan olu?turulan fiil geni? zaman ( kod ad? 21) zaman?nda çekiliyor.
    * E?er endsWithS de?i?keni  true ise bu ConjugatedVerb ün ?ahs? 3. tekil ?ah?s "3" oluyor.
    * <br> Bu metod CYCLE3 aya??nda ça?r?l?yor.
    * @return (ConjugatedVerb)this
    */
    public ConjugatedVerb toConjugatedVerb() {
        if(this.endsWithS)
            return new ConjugatedVerb(this.stack,this.type,"21",3,false,false,false,0); // 3. tekil ?ah?s
        else
            return new ConjugatedVerb(this.stack,this.type,"21",0,false,false,false,0); // bilinmeyen ?ah?s
    }

    
    
    /**
     * VerbalOBJECT'e zarf atar
     * @param s Zarf
     * @return void
     */
    public void appendAdverb(String s) {
        this.stack = s.concat(" "+this.stack); 
    }

    
}
