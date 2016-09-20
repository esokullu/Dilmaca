package dilbaz.en2tr;

/**
* Kilitlendi mi Unchangeable oluyor.
* @author Emre Sokullu
*/

class VerbalPreposition implements OBJECT {

    private String stack;
    private int type;

    public VerbalPreposition ( String s, int i) {
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
    * stack in icerigini degistiriyor.
    * @param s Yeni stack icerigi
    * @return void
    */
    public void setStack(String s) {
        stack = s;
    }
}
