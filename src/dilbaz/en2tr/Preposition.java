package dilbaz.en2tr;

/**
* Kilitlenebiliyor.
* Bu s?n?f sonPHRASE s?n?f?na kadar var.
* @author Emre Sokullu
*/

class Preposition implements OBJECT {

    private String stack;
    private int type;
    private boolean isLocked=false; // kilitli arkas?ndaki kelimeyi ald???n? g�steriyor.

    /**
     * Veritaban?ndan gelen normal type5 prepositionlar i�in
     * Her preposition bir de t�r� var ki sonra gelen kelimelerin
     * eki bu t�re g�re belirlenebilsin.
     * Ancak buradaki preposition hen�z locked de?il, ilerideki
     * basamaklarda locked olmay? bekliyorlar.
     * @param s T�rk�e preposition
     * @param i Peposition t�r� .. Prepositiondan sonra gelecek kelimenin alaca?? ek kodu
     */
    public Preposition ( String s, int i) {
        this.stack=s;
        this.type=i;
    }


    /**
     * Bu ?ekilde kurulan prepositionlar locked.
     * Bu kurulum sadece CYCLE2 da ge�erli type17 prepositionlar i�in ge�erli
     * @param s preposition kal?b?n?n kendisi
     */
    public Preposition ( String s) {
        this.stack=s;
        this.type=-1;
        this.isLocked=true;

    }

    /**
     * Preposition ?n tipini d�nd�r�yor.
     * Bu tip arkas?na alaca?? kelimenin ekini belirtiyor.
     * @return Type
     */
    public int getType() {
        return type;
    }

    /**
     * Preposition ?n kendisini d�nd�r�yor
     * @return Preposition ?n t�rk�e kar??l???
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
     * arkas?ndaki kelimeyi ald???n? g�steriyor, bundan sonra
     * arkas?na kelime eklenemiyor
     * T�m bunlar CYCLE3 te ger�ekle?iyor.
     * @return void
     */
    public void lock() {
        this.isLocked=true;
    }


    /**
     * Preposition ?n kilitli olup olmad???n? d�nd�r�yor.
     * Kilitliyse kelime alam?yor.
     * Bu metod CYCLE3 te kullan?l?yor.
     */
    public boolean isLocked() {
        return this.isLocked;
    }

}
