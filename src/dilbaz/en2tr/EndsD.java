package dilbaz.en2tr;

class EndsD extends dilbaz.en2tr.util.Suffixes implements ConjugatableVerbalOBJECT {



    private String stack;

    private int type;




    public EndsD(String s,int i) {

        this.stack=s;

        // %10 cunku
        // eger infinitive olurs type kullanilmayacak zaten yoksa
        // fiilin artik withVerbING gibi bir hali kalmayacak.
       this.type = i%10;

    }

    public String getStack() {
        return this.getInfinitive();
    }

    public int getType() {
        return this.type;
    }




    /**
    * Stacki degistiriyor.
    * @param s Yeni stack
    * @return void
    */
    public void setStack(String s) {
        stack = s ;
    }










    /**
    * Stack "sev" ise "sevilmis" gibi birsey donduruyor.
    * @return fiilin sifat hali
    */
    public String getInfinitive() {
        if(stack.endsWith("a") || stack.endsWith("e") || stack.endsWith("\u0131") || stack.endsWith("i") || stack.endsWith("u") || stack.endsWith("o") || stack.endsWith("\u00FC") || stack.endsWith("\u00F6") )
            return stack.concat("nm"+lastVowelQuattro(stack)+'\u015F');
        else if ( stack.endsWith("l") )
            return stack+lastVowelQuattro(stack)+"nm"+lastVowelQuattro(stack)+'\u015F';
        else
            return stack+lastVowelQuattro(stack)+"lm"+lastVowelQuattro(stack)+'\u015F';
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
     * @return false
     */
    public boolean isPlural() {
        return false;
    }


    /**
     * Bu sýnýfý Noun sýnýfýna dönüþtüryor
     * @return Elimizdeki sýnýfýn Noun hali
     */
    public Noun toNoun() {
        return new Noun(this.getInfinitive(),2);
    }

    /**
     * ConjugatedVerb class casting iþlemini yerine getiriyor.
     * Buna göre buradan oluþturulan fiil simple past ( kod adý 11) zamanýnda çekiliyor.
     * <br> Bu metod CYCLE3 ayaðýnda çaðrýlýyor.
     * @return (ConjugatedVerb)this
     */
    public ConjugatedVerb toConjugatedVerb() {
        return new ConjugatedVerb(this.stack,this.type,"11",0,false,false,false,0);
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
