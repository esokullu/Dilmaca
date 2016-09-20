package dilbaz.en2tr;

/**
 * VerbalOBJECT + toConjugatedVerb //( + getConjugatedStack() )
 * <br> Noun(mayBeVerb), Verb ve EndsD bu arabirimi (interface i)
 * implement ediyor.
 */
interface ConjugatableVerbalOBJECT extends VerbalOBJECT {

    /**
     * Nesnemizin ConjugatedVerb halini d�nd�r�yor.
     * @return Nesnemizin ConjugatedVerb e d�n�?m�? hali
     */
    public ConjugatedVerb toConjugatedVerb();
    
    //public String getConjugatedStack();


}
