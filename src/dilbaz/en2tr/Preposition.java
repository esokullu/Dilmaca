package dilbaz.en2tr;

/**
* Kilitlenebiliyor.
* Bu s?n?f sonPHRASE s?n?f?na kadar var.
* @author Emre Sokullu
*/

class Preposition implements OBJECT {

    private String stack;
    private int type;
    private boolean isLocked=false; // kilitli arkas?ndaki kelimeyi ald???n? gösteriyor.

    /**
     * Veritaban?ndan gelen normal type5 prepositionlar için
     * Her preposition bir de türü var ki sonra gelen kelimelerin
     * eki bu türe göre belirlenebilsin.
     * Ancak buradaki preposition henüz locked de?il, ilerideki
     * basamaklarda locked olmay? bekliyorlar.
     * @param s Türkçe preposition
     * @param i Peposition türü .. Prepositiondan sonra gelecek kelimenin alaca?? ek kodu
     */
    public Preposition ( String s, int i) {
        this.stack=s;
        this.type=i;
    }


    /**
     * Bu ?ekilde kurulan prepositionlar locked.
     * Bu kurulum sadece CYCLE2 da geçerli type17 prepositionlar için geçerli
     * @param s preposition kal?b?n?n kendisi
     */
    public Preposition ( String s) {
        this.stack=s;
        this.type=-1;
        this.isLocked=true;

    }

    /**
     * Preposition ?n tipini döndürüyor.
     * Bu tip arkas?na alaca?? kelimenin ekini belirtiyor.
     * @return Type
     */
    public int getType() {
        return type;
    }

    /**
     * Preposition ?n kendisini döndürüyor
     * @return Preposition ?n türkçe kar??l???
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
     * arkas?ndaki kelimeyi ald???n? gösteriyor, bundan sonra
     * arkas?na kelime eklenemiyor
     * Tüm bunlar CYCLE3 te gerçekle?iyor.
     * @return void
     */
    public void lock() {
        this.isLocked=true;
    }


    /**
     * Preposition ?n kilitli olup olmad???n? döndürüyor.
     * Kilitliyse kelime alam?yor.
     * Bu metod CYCLE3 te kullan?l?yor.
     */
    public boolean isLocked() {
        return this.isLocked;
    }

}
