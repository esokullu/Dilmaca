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
     * Verb s�n�f�n�n saf fiil halini d�nd�r�yor.
     * @return Verb s�n�f�n�n saf fiil hali
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
     * Bu s�n�f� Noun s�n�f�na d�n��t�ryor
     * @return Elimizdeki s�n�f�n Noun hali
     */
    public Noun toNoun() {
        return new Noun(this.getInfinitive(),2);
    }

    /**
     * ConjugatedVerb class casting i�lemini yerine getiriyor.
     * Buna g�re buradan olu�turulan fiil simple past ( kod ad� 11) zaman�nda �ekiliyor.
     * <br> Bu metod CYCLE3 aya��nda �a�r�l�yor.
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
