package dilbaz.en2tr;

/**
 * Noun ve Pronoun ýn implement ettiði interface.
 * Ýsim soylular iin var.
 * Implement edildiði sýnýflara ek eklenmesini mümkün kýlýyor.
 * @author Emre SOKULLU
 */
interface NounOBJECT extends OBJECT {

    /**
     * Parametre olarak girilen deðere bakýp, ona göre
     * nesnesinin hangi eki alacaðýna karar veren mekanizma
     * @param type ek türü
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
