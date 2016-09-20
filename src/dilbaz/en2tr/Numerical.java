package dilbaz.en2tr;

class Numerical implements OBJECT {

    private String stack;
    private boolean isOne;

    /**
    * Numerical kurucu method u
    * @param s turkce kelime
    */
    public Numerical (String s) {

        if(s.equals("1")||s.equals("bir"))
            isOne=true;
        else
            isOne=false;
        stack=s;
    }


    /**
    * Elimizdeki sayi 1 mi ?
    * @return boolean
    */
    public boolean isOne() {
        return isOne;
    }


    /**
    * Sayiyi donduruyor.
    * @return Sayi
    */
    public String getStack() {
        return stack;
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
    * Protokol metodu
    * @return -1
    */
    public int getType() { return -1; }

    /**
     * Say?y? s?fata çeviriyor.
     * CYCLE3 da önünde EndsD EndsING VerbPP gibi ileride s?fat olma ihtimali olan
     * VerbalOBJECT ler oldu?u zaman bu metod kullan?l?yor.
     * @return bu nesnenin s?fat hali (type casting)
     */
    public Noun toAdjective() {
        return new Noun(stack,2);
    }


    /**
     * Say?y? isme çeviriyor. Önündeki isim olmas? mümkün olmayan bir?eyse CYCLE3 te
     * bu metoda ba?vuruluyor.
     * @return noun nesnesi .. articled halde ..
     */
    public Noun toNoun() {
        return new Noun(stack,false,false);
    }

}
