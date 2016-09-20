package dilbaz.en2tr;

/**
 * ToVerb .. to verb kal?b? için var.
 * Bu kal?par mastar olarak sonlanabilecekleri gibi ConjugatedVerblerin bir parças? da
 * olabilirler.
 */
class ToVerb extends dilbaz.en2tr.util.Suffixes implements VerbalOBJECT {

    private boolean isBe;
    private String stack;
    private int type;

    /**
     * To .. Verb kal?plar? için kurucu yordam.
     * Fakat be# ve have# ile nas?l hareket edece?i henüz belli de?il.
     * @param s Fiilin Türkçe kar??l???
     * @param t Fiilin türü
     */
    public ToVerb(String s,int t) {

        if(s.equals("be#"))
            this.isBe=true;
        this.stack=s;
        this.type=t%10; // sadece verbalObject propertysine ihtiyacimiz var cunku
        

    }

    /**
     * Fiilin Türkçe kar??l???n? döndürüyor.
     * @return Fiilin Türkçe kar??l???
     */
    public String getStack() {
        return this.getInfinitive();
    }


    /**
    * Stacki degistiriyor.
    * @param s Yeni stack
    * @return void
    */
    public void setStack(String s) {
        this.stack = s ;
    }



    /**
     * Fiilin mastar halini döndürüyor.
     * @return Filin mastar hali
     */
    public String getInfinitive() {
        return stack+'m'+lastVowelBinary(stack);
    }

    /**
     * ToVerb s?n?f?n?n saf fiil halini döndürüyor.
     * @return Verb s?n?f?n?n saf fiil hali
     */
    public String getVerb() {
        return this.stack;
    }

    /**
     * Fiilin türü
     * @return Fiilin türü
     */
    public int getType() {
        return type;
    }



    /**
     * be# fiil mi ?
     * @return boolean
     */
    public boolean isBe() {
        return isBe;
    }


    /**
     * Protokol yordam?.
     * ToVerb ün sonunaa s almas? gibi bir durum öz konusu olamaz.
     * @return boolean
     */
    public boolean isPlural() {
        return false;
    }


    /**
     * Bu fiilin Noun hali, isim olarak mastar haliyle
     * @return (Noun)this --> bu nesnenin Noun'a casted hali
     */
    public Noun toNoun() {
        return new Noun(this.getInfinitive(),false,false);
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
