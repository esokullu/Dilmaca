package dilbaz.en2tr;

/**
 * ToVerb .. to verb kal?b? i�in var.
 * Bu kal?par mastar olarak sonlanabilecekleri gibi ConjugatedVerblerin bir par�as? da
 * olabilirler.
 */
class ToVerb extends dilbaz.en2tr.util.Suffixes implements VerbalOBJECT {

    private boolean isBe;
    private String stack;
    private int type;

    /**
     * To .. Verb kal?plar? i�in kurucu yordam.
     * Fakat be# ve have# ile nas?l hareket edece?i hen�z belli de?il.
     * @param s Fiilin T�rk�e kar??l???
     * @param t Fiilin t�r�
     */
    public ToVerb(String s,int t) {

        if(s.equals("be#"))
            this.isBe=true;
        this.stack=s;
        this.type=t%10; // sadece verbalObject propertysine ihtiyacimiz var cunku
        

    }

    /**
     * Fiilin T�rk�e kar??l???n? d�nd�r�yor.
     * @return Fiilin T�rk�e kar??l???
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
     * Fiilin mastar halini d�nd�r�yor.
     * @return Filin mastar hali
     */
    public String getInfinitive() {
        return stack+'m'+lastVowelBinary(stack);
    }

    /**
     * ToVerb s?n?f?n?n saf fiil halini d�nd�r�yor.
     * @return Verb s?n?f?n?n saf fiil hali
     */
    public String getVerb() {
        return this.stack;
    }

    /**
     * Fiilin t�r�
     * @return Fiilin t�r�
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
     * ToVerb �n sonunaa s almas? gibi bir durum �z konusu olamaz.
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
