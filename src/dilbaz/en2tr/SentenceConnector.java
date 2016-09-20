package dilbaz.en2tr;

class SentenceConnector implements OBJECT {

    private String stack;

    /**
    * SentenceConnector kurucu yordami.<br>
    * Diger OBJECTlerde oldugu gibi Type yok bu sinifta.
    * @param s turkce kelime
    */
    public SentenceConnector (String s) {
        stack=s;
    }

    /**
    * Kelimeyi geri donduruyor
    * @return turkce kelime
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
     * Stack e yeni kelimeler ekliyor.
     * Böylece SentenceConnector dan sonra gelen Unchangeable lar
     * ve zarflar SentenceConnector'a baðlanýyor.
     * @return void
     */
    public void append(String s) {
        stack= stack.concat(' '+s);
    }


    /**
    * Aslinda tur yok. Her zaman false -1 donduruyor
    * @return -1 ( false )
    */
    public int getType() { return -1; }

}
