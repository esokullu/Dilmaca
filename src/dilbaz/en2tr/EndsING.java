package dilbaz.en2tr;

class EndsING extends dilbaz.en2tr.util.Suffixes implements VerbalOBJECT {


    private String stack;

    private int type;
    
    /**
     * "going to go" gibibloklar için
     * LEFTVERBS s?n?f? için
     */
    private boolean isGoingToVerb=false;


    /**
     * isBe() yordamý için
     */
    private boolean isBe=false;


    /**
     * Her fiil için kullan?lan yordam.
     * isGoingToGo boolean ? false kal?yor.
     * Ona dokunulmuyor.
     * @param s Türkçe EndsING
     * @param i EndsING nini türü
     */
    public EndsING(String s,int i) {

        if(s.equals("be#")) {
            this.stack="ol";
            this.isBe=true;
        }
        else
            this.stack=s;

        // %10 cunku
        // eger infinitive olurs type kullanilmayacak zaten yoksa
        // fiilin artik withVerbING gibi bir hali kalmayacak.
        this.type = i%10;


    }
    
    
    /**
     * "am Goingto Go" bloklar? için var.
     * Parametresiz bir kurucu yordam.
     * Varsay?lan olarak isGoingToVerb alan?n? true i?aretliyor.
     */
    public EndsING() {
        this.stack="git";
        this.type=2;
        this.isGoingToVerb=true;
    }
            

    /**
     * EndsING fiilinin Türkçe kar??l???n? döndürüyor.
     * @return Türkçe kar??l???yla EndsING
     */
    public String getStack() {
        return this.getInfinitive();
    }

    /**
     * EndsING nin türünü çekiyor.
     * @return EndsING nini türü
     */
    public int getType() {
        return this.type;
    }

    /**
     * "am going to go" gibi bloklar için var.
     * Ozellikle LEFTVERBS nesnesinde kullan?lan bir yordam bu.
     * @return boolean 
     */
    public boolean isGoingToVerb() {
        return this.isGoingToVerb;
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
     * Cogul olabilir mi
     * @return false
     */
    public boolean isPlural() {
        return false;
    }

    
    /**
     * be# fiil mi
     * @retun boolean
     */
    public boolean isBe() {
        return this.isBe;
    }
    
    /**
     * Mastar halini döndürüyor.
     * @return Mastar hali
     */
    public String getInfinitive() {
        return this.stack.concat("m"+lastVowelBinary(stack));
    }

  
    /**
     * VerbING sýnýfýnýn saf fiil halini döndürüyor.
     * @return VerbING sýnýfýnýn saf fiil hali
     */
    public String getVerb() {
        return this.stack;
    }
    
    
    
    /**
     * Fiili Noun haline dönü?türüyor.
     * @return Nesnenin Noun hali
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
