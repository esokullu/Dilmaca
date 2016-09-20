package dilbaz.en2tr;

class Unchangeable implements OBJECT {

    private String stack;

    /**
    * Kurucu yordam
    */
    public Unchangeable	(String s) {
        stack=s;
    }

    /**
    * Unchangeable yiginina yeni bir kelime ekliyor.
    * @param s eklenecek kelime
    * @return vosd
    */
    public void append(String s) {
        try {
            if ( s.charAt(0)== ',' )
                stack = stack.concat(s);
            else
                stack = stack.concat(' '+s);
        } catch (StringIndexOutOfBoundsException e) {}  // bir sey yapmiyoruz cunku eklenmesi istenen seyin 0 harften olustugunu anladik.
    }


    /**
    * Unchangeable türkçe soz obegini donduruyor.
    * @return turkce unchangeable soz obegi
    */
    public String getStack() {
        return stack;
    }

    /**
    * Protokol yordami
    * @return -1
    */
    public int getType() { return -1; }

    /**
    * stack degisiyor.
    * @param s yeni stack
    * @return void
    */
    public void setStack(String s) {
        stack=s;
    }


}
