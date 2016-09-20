package dilbaz.en2tr;

/**
 * Bu s�n�f Cycle3 te olu�turuluyor ve hemen CYCLE3 i�nde t�ketilip sonland�r�l�yor.
 * <br> K�sacas� bu s�n�fta ge�ici veriler yer al�yor.
 * @author Emre Sokullu
 */
class Temporary implements OBJECT {


    private String stack=new String(); // t�rk�e

    /**
     * T�rler a�a��dakilere kar��l�k gelmektedir. ( �rnekle .. )
     * 0 -> ANY OF / SOME OF / MOST OF /ALL OF ... noun
     */
    private int type;


    /**
     * Temporary nesnesini olu�turur.
     * @param stack T�rk�e haf�zada saklanacak kelime
     * @param type Temporary t�r�
     */    
    public Temporary(String stack,int type) {
        this.stack=stack;
        this.type=type;
    }

    /**
     * T�r� geri d�nd�r�yor.
     * @return type
     */
    public int getType() {
        return this.type;
    }

    /**
     * T�rk�e kelimeyi d�nd�r�yor
     * @return T�rk�e y���n
     */
    public String getStack() {
        return this.stack;
    }

    /**
     * Y���n� de�i�tiriyor.
     * @return void
     */
    public void setStack(String newStack) {
        this.stack = newStack;
    }


}

