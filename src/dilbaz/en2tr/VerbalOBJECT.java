package dilbaz.en2tr;

/**
 * Fiil olma ihtimali ta??yan OBJECTleri temsil ediyor.
 * <br>Bunlar aras?nda Noun(mayBeVerb), Verb, EndsD, EndsING, VerbPP,ToVerb var.
 * <br> Yaln?z Noun(mayBeVerb), Verb ve EndsD ConjugatableVerbalOBJECT isimli ba?ka bir
 * interface ?n alt?ndalar.
 * Fakat bu interface de gene  VerbalOBJECT i extend ediyor.
 */
interface VerbalOBJECT extends OBJECT {

    /**
     * Mastar? d�nd�r�r, bu s?fatta olabilir tabii.
     * Asl?nda getStack() ile ayn? i? yap?yor.
     * @return Noun hali
     */
    public String getInfinitive();
    
    /**
     * Fiil halini d�nd�recek.
     * @return Fiil hali
     */
    public String getVerb();

    /**
     * -s ile mi bitiyor.
     * Sadece Vebrb ve noun i�in ge�erli.
     * ��nk� isme d�n�?t�rld�klerinde plural m?, singular m? olacaklar?n? belliyor.
     * @return boolean
     */
    public boolean isPlural();

    /**
     * VerbalOBJECT nesnesini Noun nesnesine d�n�?t�r�yor.
     * @return VerbalOBJECT'inNoun nesnesi hali
     */
    public Noun toNoun();
    
    /**
     * VerbalOBJECT e adverb tak?yor
     * @param s Zarf
     * @return void
     */
    public void appendAdverb(String s);


}
