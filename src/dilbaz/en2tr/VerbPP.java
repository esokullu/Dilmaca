package dilbaz.en2tr;

class VerbPP implements VerbalOBJECT {

    private String stack;
    private int type;



    /**
     * VerbPP kurucu yordami.
     * @param s verbpp kelimesi (fiil++kelime) yapisinda gelecek
     * @param i type .. fiil olursa verbObject i ne olur ?
     */
    public VerbPP(String s,int i) {

        stack=s;
        type = i;

    }

    /**
     * VerbPP nin kelime haliini donduruyor.
     * @return verbpp nin kendisi
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
     * Turu donduruyor.
     * Yalniz bu tip, fiilin tipi, noun olarak cekilirse
     * (getInfinitive()) yordami ile, o zaman tip otomatikman 1 oluyor.
     * @return tip numarasi
     */
    public int getType() {
       return this.type;
    }


    /**
     * Yordam her ne kadar mastari cek gibi adlandirilmassa da
     * bu nesne içinde çagrilan tur sifat .. sifat veritabaninda ++
     * ibaresinden sonra yer aliyor.
     * @return Fiilin sifat hali
     */
    public String getInfinitive() {
        return this.stack.substring(this.stack.indexOf('+')+2);
    }

    /**
     * Fiili donduruyor.
     *@return verbpp stack'indeki fiil kismi.
     */
    public String getVerb() {
        return this.stack.substring(0,this.stack.indexOf('+'));
    }

    /**
    * Cogul olabilir mi
    * @return false
    */
    public boolean isPlural() {
        return false;
    }


    /**
    * Bu s?n?f? Noun s?n?f?na dönü?türyor
    * @return Elimizdeki s?n?f?n Noun hali
    */
    public Noun toNoun() {
        return new Noun(this.getInfinitive(),2);
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
