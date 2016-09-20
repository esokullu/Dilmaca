package dilbaz.en2tr;

/**
* Comma sinifi
* And , & gibi cümle icindeki ozdes gorevlileri veya cumlecikleri ayiran ogeleri barindiriyor.
* @author Emre SOKULLU
*/

class Comma implements OBJECT {

    /**
    * 0 and
    * 1 ,
    * 2 &
    */ 
    private int type;

    /**
    * Comma yapilandiricisi
    * @param i tip kodu : <br> 0 ve <br> 1 , <br> 2 &
    * @return void
    */
    public Comma(int i) {
        type=i;
    }

    /**
    * Virgul mu ?
    * @return boolean
    */ 
    public boolean isComma() {
        if(type==1)
            return true;
        else return false;
    }

    /**
    * Ve baglaci mi ?
    * @return boolean
    */
    public boolean isAnd() {
        if(type==0)
            return true;
        else return false;
    }

    /**
    * & ayraci mi ?
    * @return boolean
    */
    public boolean isSeparator() {
        if (type==2)
            return true;
        else return false;
    }

    /**
    * eldeki ayraci geri donduruyor
    * @return Ayrac ( ve , & )
    */
    public String getStack() {
        if(isComma())
            return ",";
        else if (isAnd())
            return "ve";
        else
            return "&";
    }

    /**
    * Turu donduruyor.
    * @return tur
    */
public int getType() { return type; }

    /**
    * Protokol yordami.
    * @param s Yeni stack
    * @return void
    */
    public void setStack(String s) {}


}
