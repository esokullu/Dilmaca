package dilbaz.en2tr;


public class DatabaseVars {


    private boolean exists; // bu veritabani sorgusu var mi yok mu ?
    private String stack; // veritabanindan ele gecen stack
    private int jump; // atlanacak kelime sayisi
    private int type; // kelime turu
    private int property; // kelime ozelligi

    /**
     * DatabaseVars sinifi olusturuyor.
     * @param type tur
     * @param property ozellik
     * @param stack kelime haznesi
     * @param jump atlanacak kelime sayisi
     */
    public DatabaseVars(int type,int property,String stack,int jump) {
        this.exists=true;
        this.type=type;
        this.property=property;
        this.stack=stack;
        this.jump=jump;
    }

    /**
     * Veritabaninin basarisiz bir sorgu yaptigini ortaya koyan
     * parametresiz kurucu yordami
     */
    public DatabaseVars() {
        exists = false;
    }

    /**
     * Basarili bir sorgu u yoksa degil mi ?
     * @return boolean >> Basarili bir sorgu u yoksa degil mi ?
     */
    public boolean exists() {
        return exists;
    }

    /**
     * Kelimeyi donduruyor.
     * @return stack
     */
    public String getStack() {
        return stack;
    }

    /**
     * Turu donduruyor.
     * @return type
     */
    public int getType() {
        return type;
    }

    /**
     * Ozelligi donduruyor
     * @return property
     */
    public int getProperty() {
        return property;
    }

    /**
     * Atlanacak kelime sayisini donduruyor.
     * @return atlanacak kelime sayisi
     */
    public int getJump() {
        return jump;
    }
}
