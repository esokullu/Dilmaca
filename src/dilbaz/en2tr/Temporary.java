package dilbaz.en2tr;

/**
 * Bu sýnýf Cycle3 te oluþturuluyor ve hemen CYCLE3 içnde tüketilip sonlandýrýlýyor.
 * <br> Kýsacasý bu sýnýfta geçici veriler yer alýyor.
 * @author Emre Sokullu
 */
class Temporary implements OBJECT {


    private String stack=new String(); // türkçe

    /**
     * Türler aþaðýdakilere karþýlýk gelmektedir. ( örnekle .. )
     * 0 -> ANY OF / SOME OF / MOST OF /ALL OF ... noun
     */
    private int type;


    /**
     * Temporary nesnesini oluþturur.
     * @param stack Türkçe hafýzada saklanacak kelime
     * @param type Temporary türü
     */    
    public Temporary(String stack,int type) {
        this.stack=stack;
        this.type=type;
    }

    /**
     * Türü geri döndürüyor.
     * @return type
     */
    public int getType() {
        return this.type;
    }

    /**
     * Türkçe kelimeyi döndürüyor
     * @return Türkçe yýðýn
     */
    public String getStack() {
        return this.stack;
    }

    /**
     * Yýðýný deðiþtiriyor.
     * @return void
     */
    public void setStack(String newStack) {
        this.stack = newStack;
    }


}

