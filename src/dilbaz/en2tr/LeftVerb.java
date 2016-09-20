package dilbaz.en2tr;

/**
* LeftVerb <br>
* am - is - did gibi acikt5a kalan fiil parcaciklarini bulunduruyor.<br>
* Bunlar ilkeride soru veya emir yuklemleri olabiliyor.
* @author Emre Sokullu
*/
class LeftVerb implements OBJECT {

    /**
    * tip kodlamasi <br>
    * 1 are <br>
    * 2 is <br>
    * 3 have <br>
    * 4 has <br>
    * 5 was / were <br>
    * 6 modal <br>
    * 7 will <br>
    * 8 shall <br>
    * 9 can <br>
    * 10 do <br>
    * 11 does <br>
    * 12 did
    */
    private int type;
    
    /**
     * Bu LeftVerb olumlu mu olumsuz m
     */
    private boolean isNegative;

    /**
    * yapilandiririci LeftVerb yordami
    * @param t type
    */
    public LeftVerb(int t) {
        if(type>=100)
            this.isNegative=true;
        this.type = t%100 ;
    }


    /**
    * hangi tur oldugunu geri donduren yordam
    * @return tip
    */
    public int getType() {
        return this.type;
    }

    /**
    * bos yordam
    */
    public String getStack() { return "";  };

    /**
    * Protokol.
    * @param s Yeni stack
    * @return void
    */
    public void setStack(String s) {}

    /**
     * LeftVerb ün olumlu mu olumsuz mu oldu?unu döndürüyor.
     * @return boolean -> olumlu mu (true) olumsuz mu ?
     */
    public boolean isNegative() {
        return this.isNegative;
    }

    


}
