package dilbaz.en2tr;
/**
* Article sinifi
* @author Emre SOKULLU
*/
class Article implements OBJECT {

    /**
    * true  definite the
    * false indefinite a/an
    */
    private boolean isDefinite;


    /**
    * Article yapilandirici
    * @param b Definite (Belirli) article mi ?
    */
    public Article(boolean b) {
        isDefinite = b;
    }




    /**
    * Protokol yordami
    * @return -1
    */
    public int getType() { return -1; }



    /**
    * Protokol yordami
    * @return Kelimenin kendisi
    */
    public String getStack() {
        return "";
    }


    /**
    * Protokol yordami
    * @param s Yeni stack
    */
    public void setStack(String s) {}



    /**
    * Article in definite olup olmadigini belirtiyor. Yani the ..
    * @return boolean
    */
    public boolean isDefinite() {
        return isDefinite;
    }



}
