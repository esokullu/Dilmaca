package dilbaz.en2tr;
/**
* Conjunction sinifi
* of ve xxs# gibi conjunctionlari icinde barindiriyor.
* @author Emre SOKULLU
*/
class Conjunction implements OBJECT {

    /**
    * type=0 => OF connector
    * type=1 => XXS# connector
          */	

    private boolean isConjunctionOF ;

    /**
    * Conjunction yapilandiricisi
    * @param b elimizdeki conjunction <b>"of"</b> mu degil mi ? Degilse conjunction imiz xxs# dir.
    * @return void
    */
    public Conjunction(boolean b) {
        isConjunctionOF=b;
    }

    /**
    * Hangi conjunction oldugunu donduruyor.
    * @return boolean
    */
    public boolean isConjunctionOF() {
        return isConjunctionOF;
    }

    /**
    * Asagidaki yordam sadece , gerekli yerlerda karisiklik ortaya
    * cikmasin diye yapildi.
    * @return String bos string nesnesi
    */
    public String getStack() { return ""; }

    /**
    * Protokol yordami.
    * @return -1
    */
    public int getType() { return -1; }

    /**
    * Protokol.
    * @param s Yeni stack
    * @return void
    */
    public void setStack(String s) {}

}
