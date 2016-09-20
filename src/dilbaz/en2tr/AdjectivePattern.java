package dilbaz.en2tr;

/**
* Kilitlendi mi Unchangeable oluyor.
* @author Emre Sokullu
*/

class AdjectivePattern implements OBJECT{

    private String stack;
    private int type;

    public AdjectivePattern ( String s, int i) {
        stack=s;
        type=i;
    }

    public int getType() {
        return type;
    }

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

}
