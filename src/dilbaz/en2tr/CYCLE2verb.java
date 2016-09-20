package dilbaz.en2tr;

/**
* Fiilerle ilgili sinif.
* findVerb, Body'den ayri otonom bir sinif altinda toplandi, bir çok alt yordamdan olusuyor.
* teml sinifi VerbBase i extend ediyor.
* @author Emre Sokullu
*/

class CYCLE2verb {

    /**
     * lookForAdverbs() yordami icinde adverb e rastlanirsa adverb un
     * uzunlugu bu field ( alan ) icinde saklaniyor.
     */
    private static int adverbJump=0;

    /**
     * Tipki adverbJump gibi, lookForAdverbs() yordaminda adverb e rastlanirsa,
     * adverb un icerigini saklamak uzere var bu string.
     */
    private static String adverbStack=new String();

    private static int p,n; // sirasiyle cumle no ; kelime no.


    /**
     * Tum zamanlari arastirip, bulunup bulunmamasina gore
     * boolean sonuc donduruyor. p ve n static alanlarina
     * deger veriyor.
     * @param phrase cumle no
     * @param num kelime no
     * @return boolean / verb var mi yok mu ?
     */
    protected static boolean go(int phrase,int num) {

        p=phrase;
        n=num;

        if ( findBe() || findHave() || findHad() || findWas() || findModal() || findFuture() || findCan() || findDo() || findDid() )
            return true;
        else
            return false;

    }




    /**
    * Fiilleri organize edip OBJECT degiskenleri içinde sakliyor.
    * @param verb türkçe fiil
    * @param jump kaç kelime atlanacagi
    * @param object fiilin nesnesi
    * @param time fiil zaman
    * @param person fiilin sahsi
    * @param negative fiil negatif mi degil mi
    * @param query fiil soru fiili mi degil mi
    * @param passive fiil pasif mi aktif mi
    * @param mode fiilin modu
    * @return VOID
    */
    private static void _verbOrganize(String verb,int jump,int object,String time,int person,boolean negative,boolean query,boolean passive,int mode) {
        
        GlobalVars.OBJECT.add(new ConjugatedVerb(verb,object,time,person,negative,query,passive,mode));
        GlobalVars.WORDno += jump;
    }






    /**
     * Fiillere adverb bulmak icin, DATABASE sinifindan queryAdverbDB yordamini
     * calistiriyor ve bulgularini, adverbJump ve adverbStack field'larinda sakliyarak
     * fiillerin zarf almalar?na yardimci oluyor.
     * @param j yordam icinde o ana kadar ki ziplama nosu.
     * @return void
     */
    private static void _lookForAdverbs (int j) {

        int jump=0;

        if ( GlobalVars.WORD[p][n+j].equals("very") || GlobalVars.WORD[p][n+j].equals("too") )
            jump=1;

        DatabaseVars dbv = DATABASE.queryAdverbDB( GlobalVars.WORD[p][n+j+jump] , p ,n );


        if (dbv.exists()) {

            adverbJump = j + jump + dbv.getJump();
            adverbStack = dbv.getStack()+' '; // sonradan bir bosluk birakiyor ki fillerden ayrilsin.

        }

        else { // eger bulunamazda zarf bas degerlere donuluyor.

            adverbJump = 0 ;
            adverbStack = "";
        }
    }





    /**
    * ! ! ! <b> DUZELTILECEK : </b>xxm# ile soru baslamaz ! <br> 
    * Be ile baslayan fiilleri bulmak üzere tasarlandi.
    * zarflari içine katiyor.
    * fiilin sahsini bulmaya çalisiyor.
    * bazen BE# çiktisi veriyor, bazen de type18 çiktisi veriyor.
    * @param p cumle no
    * @param n kelime no
    * @return boolean
    */ 

    private static boolean findBe() {

        /* person degiskenini (sahsi) bulma kismi */
        int person;

        if( GlobalVars.WORD[p][n].toLowerCase().equals("am") || GlobalVars.WORD[p][n].toLowerCase().equals("xxm#") )
            person=1;

        else if ( GlobalVars.WORD[p][n].toLowerCase().equals("is") || GlobalVars.WORD[p][n].toLowerCase().equals("xxs#") )
            person=3;

        else if ( GlobalVars.WORD[p][n].toLowerCase().equals("are") || GlobalVars.WORD[p][n].toLowerCase().equals("xxre#") || GlobalVars.WORD[p][n].toLowerCase().equals("r") )
            person=0;

        else
            return false;


        /* not var mi ?  negative mi ? */
        int jump = 1 ;
        boolean negative = false ;

        if( GlobalVars.WORD[p][n+jump].toLowerCase().equals("not") ) {
            negative=true;
            jump=2;
        }

        /* findBe()'ye özel bir durum,am den sonra "i" geliyorsa, soru cümlesi */
        boolean query=false;
        if( GlobalVars.WORD[p][n+jump].equalsIgnoreCase("i") ) {
            query=true;
            jump++;
        }



        /* Zarf bulma kismi */
        _lookForAdverbs(jump);
        jump += adverbJump;



        /* Asil kisim */
        if ( GlobalVars.WORD[p][n+jump].toLowerCase().equals("going") && ( GlobalVars.WORD[p][n+jump+1].toLowerCase().equals("to") || GlobalVars.WORD[p][n+jump+1].toLowerCase().equals("2") )  ) {


            if ( GlobalVars.WORD[p][n+jump+2].toLowerCase().equals("be") || GlobalVars.WORD[p][n+jump+2].toLowerCase().equals("b") ) {
                DatabaseVars dbv = DATABASE.queryVerbPPDB( GlobalVars.WORD[p][n+jump+3],p,n+jump+3);
                if (dbv.exists())

                    _verbOrganize(adverbStack+dbv.getStack(),jump+3+dbv.getJump(),dbv.getProperty(),"31",person,negative,query,true,0);


                else

                    _verbOrganize("be#",jump+3,6,"31",person,negative,query,false,0);
                
                return true;

            }
            else {

                DatabaseVars dbv = DATABASE.queryVerbDB( GlobalVars.WORD[p][n+jump+2],p,n+jump+2);

                if (dbv.exists()) {

                    _verbOrganize(adverbStack+dbv.getStack(),jump+2+dbv.getJump(),dbv.getProperty(),"31",person,negative,query,false,0);
                    return true;
                }

                //else

                  //  _verbOrganize(adverbStack+"git",jump+2,6,"31",person,negative,query,false,0);
            }

        }
        
        
        
        
        if ( GlobalVars.WORD[p][n+jump].toLowerCase().equals("being") ) {


            DatabaseVars dbv = DATABASE.queryVerbPPDB( GlobalVars.WORD[p][n+jump+1],p,n+jump+1);

            if (dbv.exists())

                _verbOrganize(adverbStack+dbv.getStack(),jump+1+dbv.getJump(),dbv.getProperty(),"22",person,negative,query,true,0);


            else

                _verbOrganize("be#",jump+1,0,"21",person,negative,query,false,0);


        }
        else if ( GlobalVars.WORD[p][n+jump].toLowerCase().equals("to") || GlobalVars.WORD[p][n+jump].toLowerCase().equals("2") ) {

            if ( GlobalVars.WORD[p][n+jump+1].toLowerCase().equals("be") || GlobalVars.WORD[p][n+jump+1].toLowerCase().equals("b") ) {

                DatabaseVars dbv = DATABASE.queryVerbPPDB( GlobalVars.WORD[p][n+jump+2],p,n+jump+2 );

                if (dbv.exists())

                    _verbOrganize(adverbStack+dbv.getStack(),jump+2+dbv.getJump(),dbv.getProperty(),"must",person,negative,query,true,0);


                else

                    _verbOrganize("be#",jump+2,0,"must",person,negative,query,false,0);


            }
            else {

                DatabaseVars dbv = DATABASE.queryVerbDB( GlobalVars.WORD[p][n+jump+1],p,n+jump+1 );

                if ( dbv.exists() )

                    _verbOrganize(adverbStack+dbv.getStack(),jump+1+dbv.getJump(),dbv.getProperty(),"must",person,negative,query,false,0);

                else
                    _verbOrganize(adverbStack+"yap",jump+1,6,"must",person,negative,query,false,0);

            }

        }

        else if ( GlobalVars.WORD[p][n+jump].toLowerCase().equals("supposed") && ( GlobalVars.WORD[p][n+jump+1].toLowerCase().equals("to") || GlobalVars.WORD[p][n+jump+1].toLowerCase().equals("2") ) ) {

            if ( GlobalVars.WORD[p][n+jump+2].toLowerCase().equals("be") || GlobalVars.WORD[p][n+jump+2].toLowerCase().equals("b") ) {
                DatabaseVars dbv = DATABASE.queryVerbPPDB( GlobalVars.WORD[p][n+jump+3],p,n+jump+3 );
                if ( dbv.exists() )

                    _verbOrganize(adverbStack+dbv.getStack(),jump+2+dbv.getJump(),dbv.getProperty(),"must",person,negative,query,true,0);

                else
                    _verbOrganize("be#",jump+2,0,"must",person,negative,query,false,0);

            }



            else {




                DatabaseVars dbv = DATABASE.queryVerbDB( GlobalVars.WORD[p][n+jump+2],p,n+jump+2 );

                if ( dbv.exists() )

                    _verbOrganize(adverbStack+dbv.getStack(),jump+2+dbv.getJump(),dbv.getProperty(),"must",person,negative,query,false,0);


                else
                    _verbOrganize(adverbStack+"yap",jump+2,6,"must",person,negative,query,false,0);
            }

        }



        else if ( GlobalVars.WORD[p][n+jump].toLowerCase().equals("able") && ( GlobalVars.WORD[p][n+jump+1].toLowerCase().equals("to") || GlobalVars.WORD[p][n+jump+1].toLowerCase().equals("2") ) ) {

            if ( GlobalVars.WORD[p][n+jump+2].toLowerCase().equals("be") || GlobalVars.WORD[p][n+jump+2].toLowerCase().equals("b") ) {

                DatabaseVars dbv = DATABASE.queryVerbPPDB( GlobalVars.WORD[p][n+jump+3],p,n+jump+3 );
                if ( dbv.exists() )

                    _verbOrganize(adverbStack+dbv.getStack(),jump+3+dbv.getJump(),dbv.getProperty(),"can",person,negative,query,true,0);


                else
                    _verbOrganize("be#",jump+3,0,"can",person,negative,query,false,0);

            }
            else {

                DatabaseVars dbv = DATABASE.queryVerbDB( GlobalVars.WORD[p][n+jump+2],p,n+jump+2 );
                if ( dbv.exists() )

                    _verbOrganize(adverbStack+dbv.getStack(),jump+2+dbv.getJump(),dbv.getProperty(),"can",person,negative,query,false,0);


                else
                    _verbOrganize(adverbStack+"yap",jump+2,6,"can",person,negative,query,false,0);
            }

        }

        else if ( GlobalVars.WORD[p][n+jump].toLowerCase().equals("bound") && ( GlobalVars.WORD[p][n+jump+1].toLowerCase().equals("to") || GlobalVars.WORD[p][n+jump+1].toLowerCase().equals("2") ) ) {

            if ( GlobalVars.WORD[p][n+jump].toLowerCase().equals("be") || GlobalVars.WORD[p][n+jump].toLowerCase().equals("b") ) {

                DatabaseVars dbv = DATABASE.queryVerbPPDB( GlobalVars.WORD[p][n+jump+3],p,n+jump+3 );
                if ( dbv.exists() )

                    _verbOrganize(adverbStack+dbv.getStack(),jump+3+dbv.getJump(),dbv.getProperty(),"bound",person,negative,query,true,0);


                else
                    _verbOrganize("be#",jump+3,0,"bound",person,negative,query,false,0);
            }
            else {
                DatabaseVars dbv = DATABASE.queryVerbDB( GlobalVars.WORD[p][n+jump+2],p,n+jump+2 );
                if ( dbv.exists() )

                    _verbOrganize(adverbStack+dbv.getStack(),jump+2+dbv.getJump(),dbv.getProperty(),"bound",person,negative,query,false,0);


                else
                    _verbOrganize(adverbStack+"yap",jump+2,6,"bound",person,negative,query,false,0);
            }

        }


        else {

            DatabaseVars dbv = DATABASE.queryVerbINGDB( GlobalVars.WORD[p][n+jump] , p, n+jump);

            if ( dbv.exists() )

                _verbOrganize(adverbStack+dbv.getStack(),jump+dbv.getJump(),dbv.getProperty(),"22",person,negative,query,false,0);

            else {

                DatabaseVars dbv2 = 	DATABASE.queryVerbPPDB( GlobalVars.WORD[p][n+jump] , p, n+jump);

                if (dbv.exists() )

                    _verbOrganize(adverbStack+dbv.getStack(),jump+dbv.getJump(),dbv.getProperty(),"21",person,negative,query,true,0);



                else if (!query) {


                    if(person==0 && negative) { //are
                        GlobalVars.OBJECT.add(new LeftVerb(101));
                        GlobalVars.WORDno += jump-adverbJump;
                    }
                    else if (person==0) {
                        GlobalVars.OBJECT.add(new LeftVerb(1));
                        GlobalVars.WORDno += jump-adverbJump;
                    }
                    else if (person==1) //am
                        _verbOrganize("be#",jump,0,"21",1,negative,true,false,0);

                    else if (negative) {
                        GlobalVars.OBJECT.add(new LeftVerb(102));
                        GlobalVars.WORDno += jump-adverbJump;
                    }
                    else {
                        GlobalVars.OBJECT.add(new LeftVerb(2));
                        GlobalVars.WORDno += jump-adverbJump;
                    }

                }

                else
                    _verbOrganize("be#",jump,0,"21",1,negative,true,false,0);

            }
        }

        return true;

    }




    /**
    * Have ile baslayan fiilleri bulmak üzere tasarlandi.
    * zarflari içine katiyor.
    * fiilin sahsini bulmaya çalisiyor.
    * bazen Have# çiktisi veriyor, bazen de type18 çiktisi veriyor.
    * @param p cumle no
    * @param n kelime no
    * @return boolean
    */    


    private static boolean findHave() {
        int person;
        /* Sahsi bulma kismi */
        if ( GlobalVars.WORD[p][n].toLowerCase().equals("have") || GlobalVars.WORD[p][n].toLowerCase().equals("xxve#") )
            person=0;
        else if ( GlobalVars.WORD[p][n].toLowerCase().equals("has") || GlobalVars.WORD[p][n].toLowerCase().equals("xxs#") )
            person=3;
        else return false;


        int jump = 1 ;
        boolean negative = false ;

        /* Negative bulma kismi */
        if( GlobalVars.WORD[p][n+jump].toLowerCase().equals("not") ) {
            negative=true;
            jump=2;
        }


        /* Asil gövde */
        if( GlobalVars.WORD[p][n+jump].toLowerCase().equals("been") ) {

            DatabaseVars dbv = DATABASE.queryVerbINGDB(GlobalVars.WORD[p][n+jump+1],p,n+jump+1);

            if(dbv.exists())

                _verbOrganize(dbv.getStack(),jump+1+dbv.getJump(),dbv.getProperty(),"12",person,negative,false,false,0);
            else {
                DatabaseVars dbv2 = DATABASE.queryVerbINGDB(GlobalVars.WORD[p][n+jump+1],p,n+jump+1);
                if(dbv2.exists())
                    _verbOrganize(dbv2.getStack(),jump+1+dbv2.getJump(),dbv2.getProperty(),"11",person,negative,false,true,0);
                else
                    _verbOrganize("be#",jump+1,0,"11",person,negative,false,false,0);
            }


        }

        else if ( GlobalVars.WORD[p][n+jump].toLowerCase().equals("to") || GlobalVars.WORD[p][n+jump].toLowerCase().equals("2") ) {
            if ( GlobalVars.WORD[p][n+jump+1].toLowerCase().equals("be") || GlobalVars.WORD[p][n+jump+1].toLowerCase().equals("b") ) {

                DatabaseVars dbv = DATABASE.queryVerbPPDB(GlobalVars.WORD[p][n+jump+2],p,n+jump+2);

                if(dbv.exists())

                    _verbOrganize(dbv.getStack(),jump+2+dbv.getJump(),dbv.getProperty(),"must",person,negative,false,true,0);

                else

                    _verbOrganize("be#",jump+2,0,"must",person,negative,false,false,0);

            }
            else {
                DatabaseVars dbv = DATABASE.queryVerbDB(GlobalVars.WORD[p][n+jump+1],p,n+jump+1);

                if(dbv.exists())

                    _verbOrganize(dbv.getStack(),jump+1+dbv.getJump(),dbv.getProperty(),"must",person,negative,false,false,0);

                else

                    _verbOrganize("yap",jump+1,6,"must",person,negative,false,false,0);

            }
        }

        else if ( GlobalVars.WORD[p][n+jump].toLowerCase().equals("got") )

            _verbOrganize("have#",jump+1,0,"21",person,negative,false,false,0);


        else {
            /* Adverb bulma kismi */
            _lookForAdverbs(jump);
            jump += adverbJump;
            /* Adverb bulma kismi bitti */



            DatabaseVars dbv = DATABASE.queryVerbPPDB(GlobalVars.WORD[p][n+jump],p,n+jump);

            if( dbv.exists() )

                _verbOrganize(adverbStack+dbv.getStack(),jump+dbv.getJump(),dbv.getProperty(),"11",person,negative,false,false,0);

            else {

                int finalProperty;

                if(  person==3 && negative )
                    finalProperty = 104;
                else if ( person==3 )
                    finalProperty =4;
                else if ( negative)
                    finalProperty=103;
                else
                    finalProperty=3;

                GlobalVars.OBJECT.add(new LeftVerb(finalProperty));
                GlobalVars.WORDno += jump-adverbJump;

            }
        }
        return true;

    }


    /**
    * Had ile baslayan fiilleri bulmak üzere tasarlandi.
    * zarflari içine katiyor.
    * bazen Had# çiktisi veriyor, bazen de type18 çiktisi veriyor.
    * @param p cumle no
    * @param n kelime no
    * @return boolean
    */    


    private static boolean findHad() {


        if ( GlobalVars.WORD[p][n].toLowerCase().equals("had") || GlobalVars.WORD[p][n].toLowerCase().equals("xxd#") ) {

            int jump = 1 ;
            boolean negative = false ;

            /* Negative bulma kismi */
            if( GlobalVars.WORD[p][n+jump].toLowerCase().equals("not") ) {
                negative=true;
                jump=2;
            }


            /* Asil gövde */
            if( GlobalVars.WORD[p][n+jump].toLowerCase().equals("been") ) {

                DatabaseVars dbv = DATABASE.queryVerbINGDB(GlobalVars.WORD[p][n+jump+1],p,n+jump+1);

                if(dbv.exists())

                    _verbOrganize(dbv.getStack(),jump+1+dbv.getJump(),dbv.getProperty(),"14",0,negative,false,false,0);

                else {
                    DatabaseVars dbv2 = DATABASE.queryVerbPPDB(GlobalVars.WORD[p][n+jump+1],p,n+jump+1);
                    if(dbv2.exists())

                        _verbOrganize(dbv2.getStack(),jump+1+dbv2.getJump(),dbv2.getProperty(),"13",0,negative,false,true,0);

                    else

                        _verbOrganize("be#",jump+1,0,"13",0,negative,false,false,0);
                }

            }

            else if ( GlobalVars.WORD[p][n+jump].toLowerCase().equals("to") || GlobalVars.WORD[p][n+jump].toLowerCase().equals("2") ) {

                if ( GlobalVars.WORD[p][n+jump].toLowerCase().equals("be") || GlobalVars.WORD[p][n+jump].toLowerCase().equals("b") ) {
                    DatabaseVars dbv = DATABASE.queryVerbPPDB(GlobalVars.WORD[p][n+jump+2],p,n+jump+2);

                    if(dbv.exists())

                        _verbOrganize(dbv.getStack(),jump+2+dbv.getJump(),dbv.getProperty(),"mustPast",0,negative,false,true,0);

                    else

                        _verbOrganize("be#",jump+2,0,"mustPast",0,negative,false,false,0);
                }
                else {
                    DatabaseVars dbv = DATABASE.queryVerbDB(GlobalVars.WORD[p][n+jump+1],p,n+jump+1);

                    if(dbv.exists())

                        _verbOrganize(dbv.getStack(),jump+1+dbv.getJump(),dbv.getProperty(),"mustPast",0,negative,false,false,0);

                    else

                        _verbOrganize("yap",jump+1,6,"mustPast",0,negative,false,false,0);

                }
            }

            else if ( GlobalVars.WORD[p][n+jump].toLowerCase().equals("better") && ( GlobalVars.WORD[p][n+jump+1].toLowerCase().equals("to") || GlobalVars.WORD[p][n+jump+1].toLowerCase().equals("2") ) ) {

                if ( GlobalVars.WORD[p][n+jump].toLowerCase().equals("be") || GlobalVars.WORD[p][n+jump].toLowerCase().equals("b") ) {
                    DatabaseVars dbv = DATABASE.queryVerbPPDB(GlobalVars.WORD[p][n+jump+3],p,n+jump+3);

                    if(dbv.exists())
                        _verbOrganize(dbv.getStack(),jump+3+dbv.getJump(),dbv.getProperty(),"must",0,negative,false,true,0);
                    else
                        _verbOrganize("be#",jump+3,6,"must",0,negative,false,false,0);

                }
                else {
                    DatabaseVars dbv = DATABASE.queryVerbDB(GlobalVars.WORD[p][n+jump+2],p,n+jump+2);

                    if(dbv.exists())
                        _verbOrganize(dbv.getStack(),jump+2+dbv.getJump(),dbv.getProperty(),"must",0,negative,false,false,0);
                    else
                        _verbOrganize("yap",jump+2,6,"must",0,negative,false,false,0);
                }
            }



            else {
                /* Adverb bulma kismi */
                _lookForAdverbs(jump);

                jump += adverbJump ;
                /* Adverb bulma kismi bitti */


                DatabaseVars dbv = DATABASE.queryVerbPPDB(GlobalVars.WORD[p][n+jump],p,n+jump);

                if( dbv.exists() )

                    _verbOrganize(adverbStack+dbv.getStack(),jump+dbv.getJump(),dbv.getProperty(),"13",0,negative,false,false,0);

                else

                    _verbOrganize("have#",jump,0,"11",0,negative,false,false,0);

            }
            return true;
        }
        else
            return false;

    }



    /**
    * Was/Were ile baslayan fiilleri bulmak üzere tasarlandi.
    * zarflari içine katiyor.
    * fiilin sahsini bulmaya çalisiyor.
    * bazen BE# çiktisi veriyor, bazen de type18 çiktisi veriyor.
    * @param p cumle no
    * @param n kelime no
    * @return boolean
    */ 

    private static boolean findWas() {

        if( GlobalVars.WORD[p][n].toLowerCase().equals("was") || GlobalVars.WORD[p][n].toLowerCase().equals("were") ) {



            /* not var mi ?  negative mi ? */
            int jump = 1 ;
            boolean negative = false ;

            if( GlobalVars.WORD[p][n+jump].toLowerCase().equals("not") ) {
                negative=true;
                jump=2;
            }


            /* Zarf bulma kismi */
            _lookForAdverbs(jump);
            jump += adverbJump;



            /* Asil kisim */
            if ( GlobalVars.WORD[p][n+jump].toLowerCase().equals("being") ) {


                DatabaseVars dbv = DATABASE.queryVerbPPDB( GlobalVars.WORD[p][n+jump+1],p,n+jump+1);

                if (dbv.exists())

                    _verbOrganize(adverbStack+dbv.getStack(),jump+1+dbv.getJump(),dbv.getProperty(),"12",0,negative,false,true,0);


                else

                    _verbOrganize("be#",jump+1,0,"11",0,negative,false,false,0);


            }


            else if ( GlobalVars.WORD[p][n+jump].toLowerCase().equals("able") && ( GlobalVars.WORD[p][n+jump+1].toLowerCase().equals("to") || GlobalVars.WORD[p][n+jump+1].toLowerCase().equals("2") ) ) {
                if ( GlobalVars.WORD[p][n+jump].toLowerCase().equals("be") || GlobalVars.WORD[p][n+jump].toLowerCase().equals("b") ) {

                    DatabaseVars dbv = DATABASE.queryVerbPPDB( GlobalVars.WORD[p][n+jump+3],p,n+jump+3 );
                    if ( dbv.exists() )

                        _verbOrganize(adverbStack+dbv.getStack(),jump+3+dbv.getJump(),dbv.getProperty(),"canPast",0,negative,false,true,0);


                    else
                        _verbOrganize("be#",jump+3,6,"canPast",0,negative,false,false,0);


                }
                else {

                    DatabaseVars dbv = DATABASE.queryVerbDB( GlobalVars.WORD[p][n+jump+2],p,n+jump+2 );
                    if ( dbv.exists() )

                        _verbOrganize(adverbStack+dbv.getStack(),jump+2+dbv.getJump(),dbv.getProperty(),"canPast",0,negative,false,false,0);


                    else
                        _verbOrganize(adverbStack+"yap",jump+2,6,"canPast",0,negative,false,false,0);

                }
            }



            else {

                DatabaseVars dbv = DATABASE.queryVerbINGDB( GlobalVars.WORD[p][n+jump] , p, n+jump);

                if ( dbv.exists() )

                    _verbOrganize(adverbStack+dbv.getStack(),jump+dbv.getJump(),dbv.getProperty(),"12",0,negative,false,false,0);

                else {

                    DatabaseVars dbv2 = 	DATABASE.queryVerbPPDB( GlobalVars.WORD[p][n+jump] , p, n+jump);

                    if (dbv.exists() )

                        _verbOrganize(adverbStack+dbv.getStack(),jump+dbv.getJump(),dbv.getProperty(),"11",0,negative,false,true,0);



                    else {

                        if(negative) //are
                            GlobalVars.OBJECT.add(new LeftVerb(105));
                        else
                            GlobalVars.OBJECT.add(new LeftVerb(5));
                        GlobalVars.WORDno+=jump-adverbJump;


                    }

                }
            }

            return true;
        }
        else
            return false;

    }


    /**
    * modal verbler siralanacak.
    * klasik Verb sinii kurallari burada da geçerli olacak.
    * <b>Not</b> : Should ve Must ayni zamanlar olmamalarina karsin cevrimleri ayni.
    * <b>Not</b> : May/might cekimi aynen Can cekimi gibi..
    * @param p cumle no
    * @param n kelime no
    * @return boolean
    */

    private static boolean findModal() {

        /* hangi modal verb oldugunu belirleme govdesi */
        String time;
        if ( GlobalVars.WORD[p][n].toLowerCase().equals("must") || GlobalVars.WORD[p][n].toLowerCase().equals("should") ) // ayni zamanlar degil ama cevrimleri turkcede ayni zaman oluyor.
            time = "must";
        else if ( GlobalVars.WORD[p][n].toLowerCase().equals("may") || GlobalVars.WORD[p][n].toLowerCase().equals("might") )
            time = "can";
        else if ( GlobalVars.WORD[p][n].toLowerCase().equals("could") )
            time = "canPast";
        else if ( GlobalVars.WORD[p][n].toLowerCase().equals("would") )
            time = "would";
        else
            return false;

        /* negative bulma kismi */
        int jump = 1 ;
        boolean negative = false ;

        if( GlobalVars.WORD[p][n+jump].toLowerCase().equals("not") ) {
            negative=true;
            jump=2;
        }

        if ( GlobalVars.WORD[p][n+jump].toLowerCase().equals("have") ) {

            DatabaseVars dbv = DATABASE.queryVerbPPDB(GlobalVars.WORD[p][n+jump+1],p,n+jump+1);

            if(dbv.exists())
                _verbOrganize(dbv.getStack(),jump+dbv.getJump()+1,dbv.getProperty(),time+"Past",0,negative,false,false,0);
            else
                _verbOrganize("have#",jump+1,0,time,0,negative,false,false,0);
        }





        else {
            /* Zarf bulma kismi */
            _lookForAdverbs(jump);

            jump += adverbJump;
            /* Zarf bulma kismi bitti */

            if (GlobalVars.WORD[p][n+jump].toLowerCase().equals("b") || GlobalVars.WORD[p][n+jump].toLowerCase().equals("b") ) {
                DatabaseVars dbv = DATABASE.queryVerbPPDB(GlobalVars.WORD[p][n+jump+1],p,n+jump+1);

                if(dbv.exists())
                    _verbOrganize(adverbStack+dbv.getStack(),jump+1+dbv.getJump(),dbv.getProperty(),time,0,negative,false,true,0);
                else
                    _verbOrganize("be#",jump+1,0,time,0,negative,false,false,0);
            }
            else {
                DatabaseVars dbv = DATABASE.queryVerbDB(GlobalVars.WORD[p][n+jump],p,n+jump);

                if ( dbv.exists() )
                    _verbOrganize(adverbStack+dbv.getStack(),jump+dbv.getJump(),dbv.getProperty(),time,0,negative,false,false,0);
                else {

                    if(negative)
                        GlobalVars.OBJECT.add(new LeftVerb(106));
                    else
                        GlobalVars.OBJECT.add(new LeftVerb(6));
                    GlobalVars.WORDno += jump-adverbJump;


                }
            }
        }
        return true;
    }


    /**
    * <b>! ! ! DIKKAT : </b>I will probably have been a killer. >> Bu cumleyi cevirirken "probably" ignore ediliyor !
    * future verbler siralanacak.
    * @param p cumle no
    * @param n kelime no
    * @return boolean
    */

    private static boolean findFuture() {

        int jump=0;
        boolean negative=false;

        if( GlobalVars.WORD[p][n].toLowerCase().equals("will") || GlobalVars.WORD[p][n].toLowerCase().equals("xxll#") || GlobalVars.WORD[p][n].toLowerCase().equals("shall") ) {



            if( GlobalVars.WORD[p][n+jump].toLowerCase().equals("not") ) {
                negative=true;
                jump=2;
            }

            else
                /* negative bulma kismi */
                jump = 1 ;


        }
        else if ( ( GlobalVars.WORD[p][n].toLowerCase().equals("sha") || GlobalVars.WORD[p][n].toLowerCase().equals("wo") ) && GlobalVars.WORD[p][n+1].toLowerCase().equals("not") ) {

            negative = true;
            jump = 2 ;

        }
        else
            return false;

        /* Zarf bulma kismi */
        _lookForAdverbs(jump);
        jump+=adverbJump;



        /* Asil kisim */
        if ( GlobalVars.WORD[p][n+jump].toLowerCase().equals("be") || GlobalVars.WORD[p][n+jump].toLowerCase().equals("b") ) {

            DatabaseVars dbv = DATABASE.queryVerbINGDB( GlobalVars.WORD[p][n+jump+1],p,jump+1);
            if(dbv.exists())
                _verbOrganize(adverbStack+dbv.getStack(),jump+1+dbv.getJump(),dbv.getProperty(),"32",0,negative,false,false,0);
            else {
                DatabaseVars dbv2 = DATABASE.queryVerbPPDB( GlobalVars.WORD[p][n+jump+1],p,jump+1);
                if( dbv2.exists() )
                    _verbOrganize(adverbStack+dbv.getStack(),jump+1+dbv2.getJump(),dbv2.getProperty(),"31",0,negative,false,true,0);
                else
                    _verbOrganize("be#",jump+1,0,"31",0,negative,false,false,0);


            }
        }
        else if ( GlobalVars.WORD[p][n+jump].toLowerCase().equals("have") && GlobalVars.WORD[p][n+jump+1].toLowerCase().equals("been") ) {

            DatabaseVars dbv = DATABASE.queryVerbINGDB( GlobalVars.WORD[p][n+jump+2],p,jump+2);
            if(dbv.exists())
                _verbOrganize(adverbStack+dbv.getStack(),jump+2+dbv.getJump(),dbv.getProperty(),"34",0,negative,false,false,0);
            else {
                DatabaseVars dbv2 = DATABASE.queryVerbPPDB( GlobalVars.WORD[p][n+jump+2],p,jump+2);
                if(dbv2.exists())
                    _verbOrganize(adverbStack+dbv2.getStack(),jump+2+dbv2.getJump(),dbv2.getProperty(),"33",0,negative,false,true,0);
                else
                    _verbOrganize("be#",jump+2,0,"33",0,negative,false,false,0);
            }
        }
        else if ( GlobalVars.WORD[p][n].toLowerCase().equals("have") ) {

            DatabaseVars dbv = DATABASE.queryVerbPPDB( GlobalVars.WORD[p][n+jump+1],p,jump+1);
            if(dbv.exists())
                _verbOrganize(adverbStack+dbv.getStack(),jump+1+dbv.getJump(),dbv.getProperty(),"33",0,negative,false,false,0);
            else
                _verbOrganize("have#",jump+1,0,"31",0,negative,false,false,0);

        }
        else {

            DatabaseVars dbv = DATABASE.queryVerbDB( GlobalVars.WORD[p][n+jump],p,jump);
            if(dbv.exists())
                _verbOrganize(adverbStack+dbv.getStack(),jump+dbv.getJump(),dbv.getProperty(),"31",0,negative,false,false,0);
            else {

                if ( negative ) {
                    GlobalVars.OBJECT.add(new LeftVerb(107));
                    GlobalVars.WORDno += jump-adverbJump;
                }
                else if ( GlobalVars.WORD[p][n].toLowerCase().equals("will") ) {
                    GlobalVars.OBJECT.add(new LeftVerb(7));
                    GlobalVars.WORDno += jump-adverbJump;
                }
                else if ( GlobalVars.WORD[p][n].toLowerCase().equals("shall") ) {
                    GlobalVars.OBJECT.add(new LeftVerb(8));
                    GlobalVars.WORDno += jump-adverbJump;
                }
                else
                    _verbOrganize(adverbStack+"yap",jump,6,"31",0,negative,false,false,0);

            }
        }

        return true;

    }



    private static boolean findCan () {

        int jump=0;
        boolean negative=false;

        if ( GlobalVars.WORD[p][n].toLowerCase().equals("can") ) {
            if ( GlobalVars.WORD[p][n+1].toLowerCase().equals("not") ) {
                negative=true;
                jump = 2 ;
            }
            else
                jump = 1 ;

        }
        else if ( GlobalVars.WORD[p][n].toLowerCase().equals("cannot") ) {
            negative=true;
            jump = 1;
        }

        else
            return false;

        /* Zarf bulma kismi */
        _lookForAdverbs(jump);

        jump+=adverbJump;

        /* Asil govde */

        if( GlobalVars.WORD[p][n+jump].toLowerCase().equals("be") || GlobalVars.WORD[p][n+jump].toLowerCase().equals("b") ) {

            DatabaseVars dbv = DATABASE.queryVerbPPDB(GlobalVars.WORD[p][n+jump+1],p,n+jump+1);

            if ( dbv.exists() )
                _verbOrganize(adverbStack+dbv.getStack(),jump+1+dbv.getJump(),dbv.getProperty(),"can",0,negative,false,true,0);
            else
                _verbOrganize("be#",jump+1,dbv.getProperty(),"can",0,negative,false,false,0);
        }
        else {

            DatabaseVars dbv = DATABASE.queryVerbDB(GlobalVars.WORD[p][n+jump],p,n+jump);

            if ( dbv.exists() ) {
                _verbOrganize(adverbStack+dbv.getStack(),jump+dbv.getJump(),dbv.getProperty(),"can",0,negative,false,false,0);

            }
            else if (negative) {
                GlobalVars.OBJECT.add(new LeftVerb(109));
                GlobalVars.WORDno += jump-adverbJump;
            }
            else {
                GlobalVars.OBJECT.add(new LeftVerb(9));
                GlobalVars.WORDno += jump-adverbJump;
            }
        }
        return true;
    }





    private static boolean findDo() {
        
        int person=0;

        if ( GlobalVars.WORD[p][n].toLowerCase().equals("do") )
            person=0;
        else if ( GlobalVars.WORD[p][n].toLowerCase().equals("does") )
            person = 3 ;
        else
            return false ;

        int jump = 1 ;
        boolean negative = false ;

        if ( GlobalVars.WORD[p][n+jump].toLowerCase().equals("not") ) {
            negative = true ;
            jump = 2;
        }

        /* Asil govde */
        DatabaseVars dbv = DATABASE.queryVerbDB(GlobalVars.WORD[p][n+jump],p,n+jump);

        if ( dbv.exists() )

            _verbOrganize(dbv.getStack(),jump+dbv.getJump(),dbv.getProperty(),"21",person,negative,false,false,0);

        else if ( GlobalVars.WORD[p][n].toLowerCase().equals("do") && negative ) {

            GlobalVars.OBJECT.add(new LeftVerb(110));
            GlobalVars.WORDno += jump-adverbJump;
        }

        else if ( GlobalVars.WORD[p][n].toLowerCase().equals("do") ) {
            GlobalVars.OBJECT.add(new LeftVerb(10));
            GlobalVars.WORDno += jump-adverbJump;
        }
        else if ( negative ) {
            GlobalVars.OBJECT.add(new LeftVerb(111));
            GlobalVars.WORDno += jump-adverbJump;
        }
        else {
            GlobalVars.OBJECT.add(new LeftVerb(11));
            GlobalVars.WORDno += jump-adverbJump;
        }


        return true;

    }


    private static boolean findDid () {

        int jump=0;
        boolean negative=false;

        if ( GlobalVars.WORD[p][n].toLowerCase().equals("did") ) {

            if ( GlobalVars.WORD[p][n+1].toLowerCase().equals("not") ) {
                negative = true ;
                jump = 2 ;
            }
            else
                jump = 1 ;


            DatabaseVars dbv = DATABASE.queryVerbDB( GlobalVars.WORD[p][n+jump],p,n+jump );

            if ( dbv.exists() )
                _verbOrganize(dbv.getStack(),jump+dbv.getJump(),dbv.getProperty(),"11",0,negative,false,false,0);
            else if ( negative ) {
                GlobalVars.OBJECT.add(new LeftVerb(112));
                GlobalVars.WORDno += jump-adverbJump;
            }
            else {
                GlobalVars.OBJECT.add(new LeftVerb(12));
                GlobalVars.WORDno += jump-adverbJump;
            }
            return true;

        }
        else
            return false;

    }

    /*
    private static boolean findNeed( int p , int n ) {
    	
    	if ( GlobalVars.WORD[p][n].toLowerCase().equals("need") ) {
    		int jump = 1 ;
    		String past = "";
    	else if ( GlobalVars.WORD[p][n].toLowerCase().equals("needed") ) {
    		int jump = 1 ;
    		String past = "Past"
    	}
    	else
    		return false;
    	
    	/* Negatifligi arastirma bolumu */
    /*
    boolean negative = false ;

    if ( GlobalVars.WORD[p][n+jump].toLowerCase().equals("not") )
    	negative = true;
    	jump = 2;
    }
       
      /* Zarf bulma kismi */ 
    /*
    String [] adverb = _lookForAdverbs(p,n+jump);
    if(!adverb[0].toLowerCase().equals(""))
        String adverbStack= adverb[0]+" ";
    else
        String adverbStack="";
    jump+=(new Integer(adverb[1])).intValue();

    /* Asil govde */
    /*

    if ( GlobalVars.WORD[p][n+jump].toLowerCase().equals("to") || GlobalVars.WORD[p][n+jump].toLowerCase().equals("2") ) [
    	
    	DatabaseVars dbv = DATABASE.queryVerbDB( GlobalVars.WORD[p][n+jump+1] , p , n+jump+1 ) ;
    	
    	if ( dbv.exists() ) {
    		
    			
    	}
    	else {
    		
    		
    	}
    } 	


    }

    */

}
