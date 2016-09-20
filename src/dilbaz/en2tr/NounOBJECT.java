package dilbaz.en2tr;

/**
 * Noun ve Pronoun �n implement etti�i interface.
 * �sim soylular iin var.
 * Implement edildi�i s�n�flara ek eklenmesini m�mk�n k�l�yor.
 * @author Emre SOKULLU
 */
interface NounOBJECT extends OBJECT {

    /**
     * Parametre olarak girilen de�ere bak�p, ona g�re
     * nesnesinin hangi eki alaca��na karar veren mekanizma
     * @param type ek t�r�
     * @return void
     */
    public void commentSuffixe(int type);

    /**
     * tamlama eki
     */
    public void makeDefiner();

    /**
     * tamlanan eki
     */
    public void makeDefined();

    /**
     * -i hal eki
     */
    public void makeObjective();

    /**
     *-e hal eki
     */
    public void makeDative();

    /**
     * -de hal eki
     */
    public void makeLocative();

    /**
     * -den hal eki
     */
    public void makeAblative();



}
