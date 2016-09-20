package dilbaz.en2tr;

/**
 * VerbalOBJECT + toConjugatedVerb //( + getConjugatedStack() )
 * <br> Noun(mayBeVerb), Verb ve EndsD bu arabirimi (interface i)
 * implement ediyor.
 */
interface ConjugatableVerbalOBJECT extends VerbalOBJECT {

    /**
     * Nesnemizin ConjugatedVerb halini döndürüyor.
     * @return Nesnemizin ConjugatedVerb e dönü?mü? hali
     */
    public ConjugatedVerb toConjugatedVerb();
    
    //public String getConjugatedStack();


}
