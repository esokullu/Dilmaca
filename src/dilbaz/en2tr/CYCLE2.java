package dilbaz.en2tr;

import gnu.regexp.*;
import java.sql.SQLException;

class CYCLE2 extends dilbaz.en2tr.CYCLE2verb {

    /**
    * p cumle no <br>
    * n kelime no
     */
    private int p,n;




    public CYCLE2(int phraseNo,int wordNo) {

        this.p = phraseNo;
        this.n = wordNo;

        if (

            findUnchangeables() ||
            dealWithSpecialCases() ||
            findSpecialWordsAndNums() ||
            findCommas() ||
            findArticles() ||
            
            findVerb() ||
            findGENERAL() ||
            findMoreGeneralPrepositions() ||
            findPronouns() ||
            findEndsS() ||
            findEndsD() ||
            findEndsING() ||
            findConjunctions() ||
            //findVerb() ||
            lastAction()
        );
        this.finalize();

    }

    /**
    * Veritabanindan hangi tipin gelecegi belli degilse .. bu tip bu yordamda yorumlanip SHELL.OBJECT ArrayListine ekleniyor.
    * <br><b> NOT <b> VerbPS ler direkt olarak ConjugatedVerb sinifina geciriliyor.<br>
    * <br> Bu istisna durum disinda, bu sinif sadece direkt veritabaninda bulunan turleri iceriyor.
    * @param type kelime turu
    * @param property kelime ozelligi
    * @param stack kelimenin turkce karsiligi
    * @param jump kelimenin uzunlugu
    * @return void
    */
    private void setOBJECTvars(int type,int property,String stack,int jump) {

        switch (type) {
        case 1 :
            GlobalVars.OBJECT.add(new Noun(stack,property));
            break;
        case 2 :
            GlobalVars.OBJECT.add(new Verb(stack,property));
            break;
        case 3 :
            if(property>=100)
                GlobalVars.OBJECT.add(new ConjugatedVerb(stack.substring(0,stack.indexOf('+')),property-100,"11",0,false,false,false,0));
            else
                GlobalVars.OBJECT.add(new ConjugatedVerb(stack,property,"11",0,false,false,false,0));
            break; // normalde VerbPS olmaliydi ama VerbPS lerin hepsi gecmis zaman conjugated verb ü.
        case 4 :
            GlobalVars.OBJECT.add(new VerbPP(stack,property));
            break;
        case 5 :
            GlobalVars.OBJECT.add(new Preposition(stack,property));
            break;
        case 6 :
            GlobalVars.OBJECT.add(new VerbalPreposition(stack,property));
            break;
        case 7 :
            GlobalVars.OBJECT.add(new SentenceConnector(stack));
            break;
        case 8 :
            GlobalVars.OBJECT.add(new AdjectivePattern(stack,property));
            break;
        case 17 :
            GlobalVars.OBJECT.add(new Unchangeable(stack));
            break;
        }

        GlobalVars.WORDno += jump ;

    }



    /**
    * Ilk basamak bu .. cunku bu basamakta klasik soz obeklerini bulup
    * cikartiyoruz.
    * Buradan Noun, Unchangeable veya Preposition nesneleri olu?abiliyor.
    * @return Shell de base method unda kullanilmak üzere true veya false
    */
    private boolean findUnchangeables() {

        DatabaseVars dbv = DATABASE.queryUnchangeableDB(GlobalVars.WORD[p][n],p,n);

        if(dbv.exists()) {
            switch(dbv.getProperty()) {
            case 0 :
                GlobalVars.OBJECT.add( new Unchangeable(dbv.getStack()) );
                GlobalVars.WORDno+=dbv.getJump();
                return true;
            case 1 :
                GlobalVars.OBJECT.add( new Noun(dbv.getStack(),false,false) ); // ço?ul olmayan ve tamlama olmayan bir articled noun
                GlobalVars.WORDno+=dbv.getJump();
                return true;
            case 2 :
                GlobalVars.OBJECT.add( new Noun(dbv.getStack(),2 ) ); // s?fat
                GlobalVars.WORDno+=dbv.getJump();
                return true;
            case 3 :
                GlobalVars.OBJECT.add( new Noun(dbv.getStack(),3 ) ); // zarf
                GlobalVars.WORDno+=dbv.getJump();
                return true;
            case 4 : //çekimli preposition
                GlobalVars.OBJECT.add( new Preposition(dbv.getStack()) );
                GlobalVars.WORDno+=dbv.getJump();
                return true;
            case 5 : //normal noun
                GlobalVars.OBJECT.add( new Noun(dbv.getStack(),0) );
                GlobalVars.WORDno+=dbv.getJump();
                return true;
            case 6 :
                GlobalVars.OBJECT.add(new Noun("",false,false)); //özne olacak.
                GlobalVars.OBJECT.add(new ConjugatedVerb(dbv.getStack()));
                GlobalVars.WORDno += dbv.getJump();
                return true;
            case 7 :
                GlobalVars.OBJECT.add(new SentenceConnector(dbv.getStack()));
                GlobalVars.WORDno += dbv.getJump();
                return true;
            default :
                GlobalVars.OBJECT.add( new Unchangeable(dbv.getStack()) );
                GlobalVars.WORDno+=dbv.getJump();
                return true;
            }

        }
        else
            return false;
    }


    /**
    * Bu siniftaki diger yordamlardan farklii bir yordam bu ..
    * Bu yordam ile .. ingilizce de cok kullanilan kelimelerdeki
    * muhtemel anlam karmasalarini engelliyoruz.  Bir nevii sinif ici
    * yorumlama yordami bu.
    * @return Shell de base method unda kullanilmak üzere true veya false
    */

    private boolean dealWithSpecialCases() {

        String [] res= new String[2];


        if ( GlobalVars.WORD[p][n].equalsIgnoreCase("can") ) {

            return false;

        }
        else if ( GlobalVars.WORD[p][n].equalsIgnoreCase("xxs#") ) {

            return false;
        }
        else if ( GlobalVars.WORD[p][n].equalsIgnoreCase("will") ) {
            return false;

        }
        else if ( GlobalVars.WORD[p][n].equalsIgnoreCase("may") ) {

            return false;
        }
        else if ( GlobalVars.WORD[p][n].equalsIgnoreCase("xxd#") ) {
            return false;
        }
        else if ( GlobalVars.WORD[p][n].equalsIgnoreCase("us") ) {
            return false;
        }

        //saatler
        else if(GlobalVars.WORD[p][n].equalsIgnoreCase("it") || GlobalVars.WORD[p][n].equalsIgnoreCase("at"))
            return _dealWithHours();










        // bu da tamam
        // veritaban?na a ler eklenecek.
        else if ( GlobalVars.WORD[p][n].matches("^#[0-9]+$") ) {

            // ago durumu
            if( GlobalVars.WORD[p][n+2].equalsIgnoreCase("ago")) {
                if( GlobalVars.WORD[p][n+1].equalsIgnoreCase("centuries") || GlobalVars.WORD[p][n+1].equals("century") )
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+" y\u00FCzy\u0131l \u00F6nce"));
                else if ( GlobalVars.WORD[p][n+1].equals("years") || GlobalVars.WORD[p][n+1].equals("year") )

                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+" sene \u00F6nce"));


                else if ( GlobalVars.WORD[p][n+1].equals("seasons") || GlobalVars.WORD[p][n+1].equals("season") )
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+" sezon \u00F6nce"));
                else if ( GlobalVars.WORD[p][n+1].equals("months") || GlobalVars.WORD[p][n+1].equals("month") )
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+" ay \u00F6nce"));


                else if ( GlobalVars.WORD[p][n+1].equals("weeks") || GlobalVars.WORD[p][n+1].equals("week") )
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+" hafta \u00F6nce"));
                else if ( GlobalVars.WORD[p][n+1].equals("days") || GlobalVars.WORD[p][n+1].equals("day") )
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+" g\u00FCn \u00F6nce"));
                else if ( GlobalVars.WORD[p][n+1].equals("hours") || GlobalVars.WORD[p][n+1].equals("hour") )
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+" saat \u00F6nce"));
                else if ( GlobalVars.WORD[p][n+1].equals("minutes") || GlobalVars.WORD[p][n+1].equals("minute") )
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+" dakika \u00F6nce"));
                else if ( GlobalVars.WORD[p][n+1].equals("seconds") || GlobalVars.WORD[p][n+1].equals("second") )
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+" saniye \u00F6nce"));
                else {
                    DatabaseVars dbv = DATABASE.queryNounDB(GlobalVars.WORD[p][n+1],p,n+1);
                    if(dbv.exists() && dbv.getJump()==1 )
                        GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+' '+dbv.getStack()+" \u00F6nce"));
                    else if (GlobalVars.WORD[p][n+1].endsWith("s")) {
                        DatabaseVars dbv2= DATABASE.queryNounDB(GlobalVars.WORD[p][n+1].substring(0,GlobalVars.WORD[p][n+1].length()-1),p,n+1);
                        if( dbv2.exists() && dbv2.getJump()==1)
                            GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+' '+dbv2.getStack()+" \u00F6nce"));
                        else
                            GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+' '+GlobalVars.WORD[p][n+1]+" \u00F6nce"));
                    }
                    else
                        GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+' '+GlobalVars.WORD[p][n+1]+" \u00F6nce"));

                }
                GlobalVars.WORDno+=3;
                return true;
            }
            else  {

                GlobalVars.OBJECT.add(new Numerical(GlobalVars.WORD[p][n].substring(1)));
                GlobalVars.WORDno++;
                return true;
            }


        }

        else if ( (res=_getNumberSequence(n))!=null ) { //performans? önemli ölçüde etkiliyor bu metod !!


            int jump = Integer.parseInt(res[1]);

            // ago durumu
            if( GlobalVars.WORD[p][n+jump+1].equals("ago")) {

                if( GlobalVars.WORD[p][n+jump].equals("centuries") || GlobalVars.WORD[p][n+jump].equals("century") )
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" y\u00FCzy\u0131l \u00F6nce"));
                else if ( GlobalVars.WORD[p][n+jump].equals("years") || GlobalVars.WORD[p][n+jump].equals("year") )

                    GlobalVars.OBJECT.add(new Preposition(res[0]+" sene \u00F6nce"));


                else if ( GlobalVars.WORD[p][n+jump].equals("seasons") || GlobalVars.WORD[p][n+jump].equals("season") )
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" sezon \u00F6nce"));
                else if ( GlobalVars.WORD[p][n+jump].equals("months") || GlobalVars.WORD[p][n+jump].equals("month") )
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" ay \u00F6nce"));


                else if ( GlobalVars.WORD[p][n+jump].equals("weeks") || GlobalVars.WORD[p][n+jump].equals("week") )
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" hafta \u00F6nce"));
                else if ( GlobalVars.WORD[p][n+jump].equals("days") || GlobalVars.WORD[p][n+jump].equals("day") )
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" g\u00FCn \u00F6nce"));
                else if ( GlobalVars.WORD[p][n+jump].equals("hours") || GlobalVars.WORD[p][n+jump].equals("hour") )
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" saat \u00F6nce"));
                else if ( GlobalVars.WORD[p][n+jump].equals("minutes") || GlobalVars.WORD[p][+jump].equals("minute") )
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" dakika \u00F6nce"));
                else if ( GlobalVars.WORD[p][n+jump].equals("seconds") || GlobalVars.WORD[p][n+jump].equals("second") )
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" saniye \u00F6nce"));
                else {
                    DatabaseVars dbv = DATABASE.queryNounDB(GlobalVars.WORD[p][n+jump],p,n+1);
                    if(dbv.exists() && dbv.getJump()==1 )
                        GlobalVars.OBJECT.add(new Preposition(res[0]+' '+dbv.getStack()+" \u00F6nce"));
                    else if (GlobalVars.WORD[p][n+1].endsWith("s")) {
                        DatabaseVars dbv2= DATABASE.queryNounDB(GlobalVars.WORD[p][n+1].substring(0,GlobalVars.WORD[p][n+1].length()-1),p,n+1);
                        if( dbv2.exists() && dbv2.getJump()==1)
                            GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+' '+dbv2.getStack()+" \u00F6nce"));
                        else
                            GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+' '+GlobalVars.WORD[p][n+1]+" \u00F6nce"));
                    }
                    else
                        GlobalVars.OBJECT.add(new Preposition(res[0]+' '+GlobalVars.WORD[p][n+jump]+" \u00F6nce"));

                }
                GlobalVars.WORDno+=2+jump;
                return true;
            }
            else  {

                GlobalVars.OBJECT.add(new Numerical(res[0]));
                GlobalVars.WORDno+=jump;
                return true;
            }












        }



        else if ( GlobalVars.WORD[p][n].equalsIgnoreCase("much") ) {

            /*
             * much much .. durumu
             */

            int extra=0;
            String much=new String();

            if(GlobalVars.WORD[p][n+1].equalsIgnoreCase("much")) {
                extra=1;
                much=" \u00E7ok";
            }


            if( GlobalVars.WORD[p][n+1].equalsIgnoreCase("more") || GlobalVars.WORD[p][n+1].equalsIgnoreCase("less") ) {
                GlobalVars.OBJECT.add(new Pronoun("much "+GlobalVars.WORD[p][n+1].toLowerCase(),10));
                GlobalVars.WORDno+=2+extra;
            }
            else {
                GlobalVars.OBJECT.add(new Noun("\u00E7ok"+much,2));
                GlobalVars.WORDno+=1+extra;
            }

            return true;

        }
        else
            return false;
    }





    /**
     * Bu s?n?ftakidi?er yordamlardançok farkl?bir yordam.
     * Bu yordam dealWithSpecialCases() yordam?içnden ça?r?l?yor.
     * Saati çok farkl? permütasyonlara göre bulma olana?? tan?yor.
     * !!! PROBLEM yordam?n minutes k?sm?ndan öncesine bi bak
     * @return void
     */
    private boolean _dealWithHours() {

        boolean was=false; // it was siye mi ba?l?yor --> buna göre Türkçe de sonuna "idi" ekinialacak.
        boolean at=false; // at ile miba?l?yor. Buangöre Türkçe'de sonuna "'de" ekini alacak.
        boolean to=false; // 4 e 3 kala ..
        String adverb=new String(); // Türkçe zarf. Already almost durumlar?nda.
        int jump=1; //Atlanacak kelime say?s?.
        String hour = new String(); // saat (Türkçe)
        String min = new String(); //dakika (Türkçe)
        String [] res = new String[2]; // _getNumberSequence nesneleri için

        //son ekler
        if ( GlobalVars.WORD[p][n].equalsIgnoreCase("at") )
            at=true;
        else if( n+1<GlobalVars.WORDlength && ( GlobalVars.WORD[p][n+1].equalsIgnoreCase("is") || GlobalVars.WORD[p][n+1].equalsIgnoreCase("xxs#") ) )
            jump=2;
        else if( n+1<GlobalVars.WORDlength && ( GlobalVars.WORD[p][n+1].equalsIgnoreCase("was") || GlobalVars.WORD[p][n+1].equalsIgnoreCase("were") ) ) {
            jump=2;
            was=true;
        }
        else
            return false; // devam etsin ve normalde nas?l bulunacaksa öyle bulunsun.

        // almost - about - already
        if( n+jump<GlobalVars.WORDlength && GlobalVars.WORD[p][n+jump].equalsIgnoreCase("about") || GlobalVars.WORD[p][n+jump].equalsIgnoreCase("almost") ) {
            adverb="yakla\u015F\u0131k "; //yaklasik
            jump++;
        }
        else if( n+jump<GlobalVars.WORDlength && GlobalVars.WORD[p][n+jump].equalsIgnoreCase("already") ) {
            adverb="hen\u00FCz "; //henüz
            jump++;
        }


        //half quarter özeldurumlar?

        //half
        if( n+jump+2<GlobalVars.WORDlength && GlobalVars.WORD[p][n+jump].equalsIgnoreCase("half") && GlobalVars.WORD[p][n+jump+1].equalsIgnoreCase("past") && (res=_getNumberSequence(n+jump+2))!=null ) {

            jump += 2 + Integer.parseInt(res[1]);

            if( n+jump+2<GlobalVars.WORDlength && GlobalVars.WORD[p][n+jump].equalsIgnoreCase("in") && GlobalVars.WORD[p][n+jump+1].equalsIgnoreCase("the") ) {
                if( GlobalVars.WORD[p][n+jump+2].equalsIgnoreCase("morning") ) {
                    adverb="\u00F6\u011Fleden \u00F6nce "+adverb;// ogleden once
                    jump+=3;
                }
                else if( GlobalVars.WORD[p][n+jump+2].equalsIgnoreCase("afternoon")) {
                    adverb="\u00F6\u011Fleden sonra "+adverb;// ogleden sonra
                    jump+=3;
                }
            }

            if(at)
                GlobalVars.OBJECT.add(new Preposition("saat "+adverb+res[0]+" bu\u00E7ukta"));
            else if(was)
                GlobalVars.OBJECT.add(new Unchangeable("saat "+adverb+res[0]+" bu\u00E7uktu"));
            else
                GlobalVars.OBJECT.add(new Unchangeable("saat "+adverb+res[0]+" bu\u00E7uk"));

            GlobalVars.WORDno += jump;
            return true;

        }

        // a quarter
        else if( n+jump+3<GlobalVars.WORDlength && GlobalVars.WORD[p][n+jump].equalsIgnoreCase("a") && GlobalVars.WORD[p][n+jump+1].equalsIgnoreCase("quarter") && (GlobalVars.WORD[p][n+jump+2].equalsIgnoreCase("past")||GlobalVars.WORD[p][n+jump+2].equalsIgnoreCase("to") ) && (res=_getNumberSequence(n+jump+3))!=null ) {

            if( GlobalVars.WORD[p][n+jump+2].equalsIgnoreCase("to") )
                to=true;

            jump += 3 + Integer.parseInt(res[1]);

            if( n+jump+2<GlobalVars.WORDlength && GlobalVars.WORD[p][n+jump].equalsIgnoreCase("in") && GlobalVars.WORD[p][n+jump+1].equalsIgnoreCase("the") ) {
                if(GlobalVars.WORD[p][n+jump+2].equalsIgnoreCase("morning")) {
                    adverb="\u00F6\u011Fleden \u00F6nce "+adverb;// ogleden once
                    jump+=3;
                }
                else if(GlobalVars.WORD[p][n+jump+2].equalsIgnoreCase("afternoon")) {
                    adverb="\u00F6\u011Fleden sonra "+adverb;// ogleden sonra
                    jump+=3;
                }
            }
            System.out.println("eeeeeeeeeeeeeeeeeeeee");
            if(at&&to)
                GlobalVars.OBJECT.add(new Preposition("saat "+adverb+res[0]+"\'e \u00E7eyrek kala"));
            else if(at)
                GlobalVars.OBJECT.add(new Preposition("saat "+adverb+res[0]+"\'i \u00E7eyrek ge\u00E7e"));
            else if(was&&to)
                GlobalVars.OBJECT.add(new Unchangeable("saat "+adverb+res[0]+"\'e \u00E7eyrek vard\u0131"));
            else if(was)
                GlobalVars.OBJECT.add(new Unchangeable("saat "+adverb+res[0]+"\'i \u00E7eyrek ge\u00E7iyordu"));
            else if(to)
                GlobalVars.OBJECT.add(new Unchangeable("saat "+adverb+res[0]+"\'e \u00E7eyrek var"));
            else
                GlobalVars.OBJECT.add(new Unchangeable("saat "+adverb+res[0]+"\'i \u00E7eyrek ge\u00E7iyor"));

            GlobalVars.WORDno += jump;
            return true;

        }

        // quarter
        else if( n+jump+2<GlobalVars.WORD.length &&  GlobalVars.WORD[p][n+jump].equalsIgnoreCase("quarter") && (GlobalVars.WORD[p][n+jump+1].equalsIgnoreCase("past")||GlobalVars.WORD[p][n+jump+1].equalsIgnoreCase("to") ) && (res=_getNumberSequence(n+jump+2))!=null ) {

            if( GlobalVars.WORD[p][n+jump+1].equalsIgnoreCase("to") )
                to=true;

            jump += 2 + Integer.parseInt(res[1]);

            if( n+jump+2<GlobalVars.WORD.length && GlobalVars.WORD[p][n+jump].equalsIgnoreCase("in") && GlobalVars.WORD[p][n+jump+1].equalsIgnoreCase("the") ) {
                if(GlobalVars.WORD[p][n+jump+2].equalsIgnoreCase("morning")) {
                    adverb="\u00F6\u011Fleden \u00F6nce "+adverb;// ogleden once
                    jump+=3;
                }
                else if(GlobalVars.WORD[p][n+jump+2].equalsIgnoreCase("afternoon")) {
                    adverb="\u00F6\u011Fleden sonra "+adverb;// ogleden sonra
                    jump+=3;
                }
            }

            if(at&&to)
                GlobalVars.OBJECT.add(new Preposition("saat "+adverb+res[0]+"\'e \u00E7eyrek kala"));
            else if(at)
                GlobalVars.OBJECT.add(new Preposition("saat "+adverb+res[0]+"\'i \u00E7eyrek ge\u00E7e"));
            else if(was&&to)
                GlobalVars.OBJECT.add(new Unchangeable("saat "+adverb+res[0]+"\'e \u00E7eyrek vard\u0131"));
            else if(was)
                GlobalVars.OBJECT.add(new Unchangeable("saat "+adverb+res[0]+"\'i \u00E7eyrek ge\u00E7iyordu"));
            else if(to)
                GlobalVars.OBJECT.add(new Unchangeable("saat "+adverb+res[0]+"\'e \u00E7eyrek var"));
            else
                GlobalVars.OBJECT.add(new Unchangeable("saat "+adverb+res[0]+"\'i \u00E7eyrek ge\u00E7iyor"));

            GlobalVars.WORDno += jump;
            return true;

        }




        // saati bulma
        if( n+jump<GlobalVars.WORDlength && GlobalVars.WORD[p][n+jump].matches("^#[0-9]+[.:]?[0-9]*$") ) { //nümerik de?erler
            hour = GlobalVars.WORD[p][n+jump].substring(1); //bundan sonra illakibirsaat de?eri döndürülecek.
            jump++;
        }
        else if ( n+jump<GlobalVars.WORDlength && (res=_getNumberSequence(n+jump))!=null ) {
            hour = res[0]; //bundan sonra illa ki bir saat de?eri döndürülecek.
            jump += Integer.parseInt(res[1]);
        }
        else
            return false; //devam etsin normalde nas?l bulunacaksa öyle bulsun.




        // saatten sonra gelebilecek kelimeler.
        if( n+jump<GlobalVars.WORDlength && GlobalVars.WORD[p][n+jump].equalsIgnoreCase("am") ) {
            if(at)
                GlobalVars.OBJECT.add(new Preposition("saat \u00F6\u011Fleden \u00F6nce "+adverb+hour+"\'de"));
            else if(was)
                GlobalVars.OBJECT.add(new Preposition("saat \u00F6\u011Fleden \u00F6nce "+adverb+hour+" idi"));
            else
                GlobalVars.OBJECT.add(new Preposition("saat \u00F6\u011Fleden \u00F6nce "+adverb+hour));

            GlobalVars.WORDno += jump+1;
            return true; //ç?kar
        }

        else if ( n+jump<GlobalVars.WORDlength && GlobalVars.WORD[p][n+jump].equalsIgnoreCase("pm") ) {
            if(at)
                GlobalVars.OBJECT.add(new Preposition("saat \u00F6\u011Fleden sonra "+adverb+hour+"\'de"));
            else if(was)
                GlobalVars.OBJECT.add(new Preposition("saat \u00F6\u011Fleden sonra "+adverb+hour+" idi"));
            else
                GlobalVars.OBJECT.add(new Preposition("saat \u00F6\u011Fleden sonra "+adverb+hour));

            GlobalVars.WORDno += jump+1;
            return true; //ç?kar
        }

        else if ( n+jump<GlobalVars.WORDlength && GlobalVars.WORD[p][n+jump].equalsIgnoreCase("o'clock") ) {
            if( n+jump+3<GlobalVars.WORDlength && GlobalVars.WORD[p][n+jump+1].equalsIgnoreCase("in") && GlobalVars.WORD[p][n+jump+2].equalsIgnoreCase("the") ) {
                if(GlobalVars.WORD[p][n+jump+3].equalsIgnoreCase("morning")) {
                    adverb="\u00F6\u011Fleden \u00F6nce "+adverb;// ogleden once
                    jump+=3;
                }
                else if(GlobalVars.WORD[p][n+jump+3].equalsIgnoreCase("afternoon")) {
                    adverb="\u00F6\u011Fleden sonra "+adverb;// ogleden sonra
                    jump+=3;
                }
            }

            if(at)
                GlobalVars.OBJECT.add(new Preposition("saat "+adverb+hour+"\'de"));
            else if(was)
                GlobalVars.OBJECT.add(new Preposition("saat "+adverb+hour+" idi"));
            else
                GlobalVars.OBJECT.add(new Preposition("saat "+adverb+hour));

            GlobalVars.WORDno += jump+1;
            return true; //ç?kar
        }

        else if( n+jump+2<GlobalVars.WORDlength && GlobalVars.WORD[p][n+jump].equalsIgnoreCase("in") && GlobalVars.WORD[p][n+jump+1].equalsIgnoreCase("the") && ( GlobalVars.WORD[p][n+jump+2].equalsIgnoreCase("afternoon") || GlobalVars.WORD[p][n+jump+2].equalsIgnoreCase("morning") ) ) {
            if(GlobalVars.WORD[p][n+jump+2].equalsIgnoreCase("morning")) {
                adverb="\u00F6\u011Fleden \u00F6nce "+adverb;// ogleden once
                jump+=3;
            }
            else if(GlobalVars.WORD[p][n+jump+2].equalsIgnoreCase("afternoon")) {
                adverb="\u00F6\u011Fleden sonra "+adverb;// ogleden sonra
                jump+=3;
            }

            if(at)
                GlobalVars.OBJECT.add(new Preposition("saat "+adverb+hour+"\'de"));
            else if(was)
                GlobalVars.OBJECT.add(new Preposition("saat "+adverb+hour+" idi"));
            else
                GlobalVars.OBJECT.add(new Preposition("saat "+adverb+hour));

            GlobalVars.WORDno += jump;
            return true; //ç?kar

        }

        else if( n+jump<GlobalVars.WORDlength && !at && GlobalVars.WORD[p][n+jump].equals("now") ) {

            if(was)
                GlobalVars.OBJECT.add(new Preposition("\u015Fu anda saat "+adverb+hour+" idi"));
            else
                GlobalVars.OBJECT.add(new Preposition("\u015Fu anda saat "+adverb+hour));

            GlobalVars.WORDno+=jump+1;
            return true;
        }

        else if( n+jump+2<GlobalVars.WORDlength && !at && GlobalVars.WORD[p][n+jump].equals("at") && ( GlobalVars.WORD[p][n+jump].equals("the") ||  GlobalVars.WORD[p][n+jump].equals("that") ||  GlobalVars.WORD[p][n+jump].equals("this") ) && ( GlobalVars.WORD[p][n+jump+2].equals("moment") ||  GlobalVars.WORD[p][n+jump+2].equals("instant") ) ) {

            if(was)
                GlobalVars.OBJECT.add(new Preposition("\u015Fu anda saat "+adverb+hour+" idi"));
            else
                GlobalVars.OBJECT.add(new Preposition("\u015Fu anda saat "+adverb+hour));

            GlobalVars.WORDno+=jump+3;
            return true;
        }


        else if ( n+jump<GlobalVars.WORDlength && GlobalVars.WORD[p][n+jump].equalsIgnoreCase("minutes") ) {
            min=hour;
            if( n+jump+1<GlobalVars.WORDlength && GlobalVars.WORD[p][n+jump+1].equalsIgnoreCase("before")) {
                to=true;
                jump+=2;
            }
            else if( n+jump+1<GlobalVars.WORDlength && GlobalVars.WORD[p][n+jump+1].equalsIgnoreCase("after"))
                jump+=2;
            else
                jump++;

        }

        else if ( n+jump<GlobalVars.WORDlength && GlobalVars.WORD[p][n+jump].equalsIgnoreCase("past") ) {
            min=hour;
            jump++;
        }

        else if ( n+jump<GlobalVars.WORDlength && GlobalVars.WORD[p][n+jump].equalsIgnoreCase("to") ) {
            min=hour;
            jump++;
            to=true;

        }





        // dakikayi bulma zamani
        if( n+jump<GlobalVars.WORDlength && GlobalVars.WORD[p][n+jump].matches("^#[0-9]+$") ) { //nümerik de?erler
            if(!min.equals(""))
                hour = GlobalVars.WORD[p][n+jump].substring(1); //bundan sonra illaki bir saat de?eri döndürülecek.
            else
                min = GlobalVars.WORD[p][n+jump].substring(1);
            jump++;
        }
        else if ( n+jump<GlobalVars.WORDlength && (res=_getNumberSequence(n+jump))!=null ) {
            if(!min.equals(""))
                hour = res[0]; //bundan sonra illa ki bir saat de?eri döndürülecek.
            else
                min = res[0];
            jump += Integer.parseInt(res[1]);
        }
        else {

            // ne yapaca??m?z? bilemiyoruz..
            // ya it's twelve months ise ?
            // ondan ?imdilik
            return false;

        }

        //son ekler inthe morning / am / now
        if( !at && !was && n+jump<GlobalVars.WORDlength && GlobalVars.WORD[p][n+jump].equalsIgnoreCase("now") ) {
            //geçmi? zamanda now olmaz.
            if(to)
                GlobalVars.OBJECT.add(new Preposition("\u015Fu anda saat "+adverb+hour+"\'e "+min+" var"));
            else
                GlobalVars.OBJECT.add(new Preposition("\u015Fu anda saat "+adverb+hour+' '+min));

            GlobalVars.WORDno+=jump+1;
            return true;

        }
        else if ( n+jump+2<GlobalVars.WORDlength && GlobalVars.WORD[p][n+jump].equalsIgnoreCase("in") &&  GlobalVars.WORD[p][n+jump+1].equals("the") ) {

            if(GlobalVars.WORD[p][n+jump+2].equalsIgnoreCase("morning")) {
                adverb="\u00F6\u011Fleden \u00F6nce "+adverb;// ogleden once
                jump+=3;
            }
            else if(GlobalVars.WORD[p][n+jump+2].equalsIgnoreCase("afternoon")) {
                adverb="\u00F6\u011Fleden sonra "+adverb;// ogleden sonra
                jump+=3;
            }

            if(at&&to)
                GlobalVars.OBJECT.add(new Preposition("saat "+adverb+hour+"\'e "+min+" kala"));
            else if(at)
                GlobalVars.OBJECT.add(new Preposition("saat "+adverb+hour+' '+min+"\'de"));
            else if(was&&to)
                GlobalVars.OBJECT.add(new Preposition("saat "+adverb+hour+"\'e "+min+" vard\u0131"));
            else if(was)
                GlobalVars.OBJECT.add(new Preposition("saat "+adverb+hour+' '+min+" idi"));
            else if(to)
                GlobalVars.OBJECT.add(new Preposition("saat "+adverb+hour+"\'e "+min+" var"));
            else
                GlobalVars.OBJECT.add(new Preposition("saat "+adverb+hour+' '+min));

            GlobalVars.WORDno += jump;
            return true; //ç?kar

        }

        else if(  !at && !was && n+jump+2<GlobalVars.WORDlength && GlobalVars.WORD[p][n+jump].equals("at") && ( GlobalVars.WORD[p][n+jump].equals("the") ||  GlobalVars.WORD[p][n+jump].equals("that") ||  GlobalVars.WORD[p][n+jump].equals("this") ) && ( GlobalVars.WORD[p][n+jump+2].equals("moment") ||  GlobalVars.WORD[p][n+jump+2].equals("instant") ) ) {

            if(to)
                GlobalVars.OBJECT.add(new Preposition("\u015Fu anda saat "+adverb+hour+"\'e "+min+" var"));
            else
                GlobalVars.OBJECT.add(new Preposition("\u015Fu anda saat "+adverb+hour+' '+min));

            GlobalVars.WORDno+=jump+3;
            return true;

        }
        else {

            if(at&&to)
                GlobalVars.OBJECT.add(new Preposition("saat "+adverb+hour+"\'e "+min+" kala"));
            else if(at)
                GlobalVars.OBJECT.add(new Preposition("saat "+adverb+hour+' '+min+"\'de"));
            else if(was&&to)
                GlobalVars.OBJECT.add(new Preposition("saat "+adverb+hour+"\'e "+min+" vard\u0131i"));
            else if(was)
                GlobalVars.OBJECT.add(new Preposition("saat "+adverb+hour+' '+min+" idi"));
            else if(to)
                GlobalVars.OBJECT.add(new Preposition("saat "+adverb+hour+"\'e "+min+" var"));
            else
                GlobalVars.OBJECT.add(new Preposition("saat "+adverb+hour+' '+min));


            GlobalVars.WORDno += jump;
            return true; //ç?kar

        }
    }













    /**
    * # ile baslayan kelimeleri buluyor.
    * Sayiysa bunlar, bunlari 12 sinifina dahil ediyor ((propertyCode) 1->0; digerleri -> 1 )
    * 1st 2nd 3rd gibilerse bunlari sifat olarak algiliyor.
    * yoksa normal isim olarak g,hic veritabani sorgulamasi yapmadan turkcey4 ceviriyor.
    * @return Shell de base method unda kullanilmak üzere true veya false
    */

    private boolean findSpecialWordsAndNums()  {

        if( GlobalVars.WORD[p][n].charAt(0)=='#' ) {





            if( GlobalVars.WORD[p][n].toLowerCase().matches("^\\#[0-9]+(st|nd|th)$") ) {
                GlobalVars.OBJECT.add(new Noun(GlobalVars.WORD[p][n].substring(1,GlobalVars.WORD[p][n].length()-2)+'.',2));
                GlobalVars.WORDno++;
                return true;
            }

            else {
                GlobalVars.OBJECT.add(new Noun(GlobalVars.WORD[p][n].substring(1),0));
                GlobalVars.WORDno++;
                return true;
            }
        }

        else
            return false;
    }

    /**
    * , & and gibi commalari (ayrac) buluyor.
    * Commalarin typeCode u 11
    * @return Shell de base method unda kullanilmak üzere true veya false
    */

    private boolean findCommas () {


        if ( GlobalVars.WORD[p][n].equalsIgnoreCase("and") ) {

            GlobalVars.OBJECT.add(new Comma(0));
            GlobalVars.WORDno++;
            return true;
        }

        else if(GlobalVars.WORD[p][n].equals(",")) {

            GlobalVars.OBJECT.add(new Comma(1));
            GlobalVars.WORDno++;
            return true;
        }

        else if(GlobalVars.WORD[p][n].equals("&")) {
            GlobalVars.OBJECT.add(new Comma(2));
            GlobalVars.WORDno++;
            return true;
        }

        else
            return false;
    }


    /**
    * the a an gibi articlelari buluyor.
    * Articlelarin typeCode u 10
    * @return Shell de base method unda kullanilmak üzere true veya false
    */
    private boolean findArticles() {

        if(GlobalVars.WORD[p][n].equalsIgnoreCase("the")) {
            GlobalVars.OBJECT.add(new Article(true));
            GlobalVars.WORDno++;
            return true;
        }

        else if ( GlobalVars.WORD[p][n].equals("a") || GlobalVars.WORD[p][n].equals("an") ) {
            GlobalVars.OBJECT.add(new Article(false));
            GlobalVars.WORDno++;
            return true;
        }

        else
            return false;
    }



    /**
    * of ve 's gibi tamlayan tamlanan eklerini buluyor.
    * Bunlarin typeCode ' u 16
    * @return Shell de base method unda kullanilmak üzere true veya false
    */

    private boolean findConjunctions() {

        if(GlobalVars.WORD[p][n].equalsIgnoreCase("of")) {
            GlobalVars.OBJECT.add(new Conjunction(true));
            GlobalVars.WORDno++;
            return true;
        }
        else if (GlobalVars.WORD[p][n].equalsIgnoreCase("xxs#")) {
            GlobalVars.OBJECT.add(new Conjunction(false));
            GlobalVars.WORDno++;
            return false;
        }
        else
            return false;
    }


    /**
    * Verilen kelimenin karsiliginin bulunamamasi halinde devreye giriyor.
    * O kelimeyi normal bir isim gibi algiliyor.<br>
    * Property olarak 9 veriyor noun a bu da CYCLE3 ün ilk a?amalar?nda elimizdeki
    * kelimenin HumanName de olabilecegini isaret ediyor. HumanName
    * olmazsa hemen normal Noun a (type0) dönü?üyor.<br>
    * Gelecekte buraya bir spell check aspell gibi eklenebilir.
    * @return Shell de base method unda kullanilmak üzere true veya false
    */

    private boolean lastAction() {

        String [] res = new String [2];

        if((res=_getNumberSequence(n))!=null) {
            GlobalVars.OBJECT.add(new Numerical(res[0]));
            GlobalVars.WORDno += Integer.parseInt(res[1]);
            return true;
        }
        else {
            GlobalVars.OBJECT.add(new Noun(GlobalVars.WORD[p][n],9)); // property 9 .. isim olabilir
            GlobalVars.WORDno++;
            return true;
        }
    }



    /**
    * Pronounlari buluyor.
    * @return Shell de base method unda kullanilmak üzere true veya false
    */

    private boolean findPronouns() {

        String isPronoun =  GlobalVars.WORD[p][n].toLowerCase();

        // SubjectPronoun
        if(isPronoun.equals("i")||isPronoun.equals("you")||isPronoun.equals("u")||isPronoun.equals("thou")||isPronoun.equals("he")||isPronoun.equals("she")||isPronoun.equals("it")||isPronoun.equals("they")||isPronoun.equals("we")) {
            if( GlobalVars.WORDlength>n+1 && GlobalVars.WORD[p][n+1].equals("one") ) {
                GlobalVars.OBJECT.add(new Pronoun(isPronoun+" one",1));
                GlobalVars.WORDno+=2;
            }
            else {
                GlobalVars.OBJECT.add(new Pronoun(isPronoun,1));
                GlobalVars.WORDno++;
            }
            return true;
        }

        // Demonstrative Pronoun
        else if (isPronoun.equals("any")||isPronoun.equals("some")||isPronoun.equals("every")||isPronoun.equals("each")||isPronoun.equals("all")||isPronoun.equals("whole")||isPronoun.equals("this")||isPronoun.equals("these")||isPronoun.equals("those")||isPronoun.equals("most")||isPronoun.equals("more")||isPronoun.equals("less")||isPronoun.equals("least")) {
            GlobalVars.OBJECT.add(new Pronoun(isPronoun,0));
            GlobalVars.WORDno++;
            return true;
        }


        // PossessivePronoun
        else if (isPronoun.equals("my")||isPronoun.equals("your")||isPronoun.equals("ur")||isPronoun.equals("his")||isPronoun.equals("her")||isPronoun.equals("its")||isPronoun.equals("our")||isPronoun.equals("their")) {

            if( GlobalVars.WORDlength > n+1 && GlobalVars.WORD[p][n+1].equalsIgnoreCase("own")) {
                GlobalVars.OBJECT.add(new Pronoun(isPronoun+" own",2));
                GlobalVars.WORDno+=2;
            }
            else {
                GlobalVars.OBJECT.add(new Pronoun(isPronoun,2));
                GlobalVars.WORDno++;
            }
            return true;
        }

        // NounPossessivePronoun
        else if(isPronoun.equals("mine")||isPronoun.equals("yours")||isPronoun.equals("urs")||isPronoun.equals("thine")||isPronoun.equals("hers")||isPronoun.equals("ours")||isPronoun.equals("theirs")) {
            GlobalVars.OBJECT.add(new Pronoun(isPronoun,3));
            GlobalVars.WORDno++;
            return true;
        }

        // ReflexivePronoun
        else if (isPronoun.equals("myself")||isPronoun.equals("yourself")||isPronoun.equals("urself")||isPronoun.equals("herself")||isPronoun.equals("himself")||isPronoun.equals("itself")||isPronoun.equals("ourselves")||isPronoun.equals("yourselves")||isPronoun.equals("urselves")||isPronoun.equals("theirselves")) {
            GlobalVars.OBJECT.add(new Pronoun(isPronoun,4));
            GlobalVars.WORDno++;
            return true;
        }


        // ObjectPronoun
        else if (isPronoun.equals("me")||isPronoun.equals("him")||isPronoun.equals("us")||isPronoun.equals("them")) {
            GlobalVars.OBJECT.add(new Pronoun(isPronoun,5));
            GlobalVars.WORDno++;
            return true;
        }

        // UndeterminedPronoun
        else if (isPronoun.equals("that")||isPronoun.equals("so")||isPronoun.equals("too")||isPronoun.equals("as")||isPronoun.equals("very")) {
            GlobalVars.OBJECT.add(new Pronoun(isPronoun,6));
            GlobalVars.WORDno++;
            return true;
        }

        
        // QueryPronoun ?n ilk k?sm?
        else if ( GlobalVars.WORDlength > n+1 && isPronoun.equals("how") ) {
            if ( GlobalVars.WORD[p][n+1].equalsIgnoreCase("long") || GlobalVars.WORD[p][n+1].equalsIgnoreCase("much") || GlobalVars.WORD[p][n+1].equalsIgnoreCase("many")  )  {
                GlobalVars.OBJECT.add(new Pronoun(isPronoun+" long",7));
                GlobalVars.WORDno+=2;
                return true;
            }
            else if ( GlobalVars.WORD[p][n+1].equalsIgnoreCase("come") ) {
                GlobalVars.OBJECT.add(new Pronoun("how come",7));
                GlobalVars.WORDno+=2;
                return true;
            }
            
            else if ( GlobalVars.WORD[p][n+1].equalsIgnoreCase("far") ) {
                GlobalVars.OBJECT.add(new Pronoun("how far",7));
                GlobalVars.WORDno+=2;
                return true;
            }
            else if ( GlobalVars.WORD[p][n+1].equalsIgnoreCase("old") ) {
                GlobalVars.OBJECT.add(new Pronoun("how old",7));
                GlobalVars.WORDno+=2;
                return true;
            }
            
            else if ( GlobalVars.WORD[p][n+1].equalsIgnoreCase("often") ) {
                GlobalVars.OBJECT.add(new Pronoun("how often",7));
                GlobalVars.WORDno+=2;
                return true;
            }
            else if ( GlobalVars.WORD[p][n+1].equalsIgnoreCase("well") ) {
                GlobalVars.OBJECT.add(new Pronoun("how well",7));
                GlobalVars.WORDno+=2;
                return true;
            }
            else if ( GlobalVars.WORDlength > n+2 && GlobalVars.WORD[p][n+1].equalsIgnoreCase("long")  && GlobalVars.WORD[p][n+1].equalsIgnoreCase("ago")) {
                GlobalVars.OBJECT.add(new Pronoun("how long ago",7));
                GlobalVars.WORDno+=3;
                return true;
            }
            else {
                GlobalVars.OBJECT.add(new Pronoun(isPronoun,7));
                GlobalVars.WORDno++;
                return true;
            }
                
        }
        
        // QueryPronouns
        else if (isPronoun.equals("how")||isPronoun.equals("why")||isPronoun.equals("who")||isPronoun.equals("whom")||isPronoun.equals("whose")||isPronoun.equals("where")||isPronoun.equals("which")||isPronoun.equals("whoever")||isPronoun.equals("what")||isPronoun.equals("whatever")) {
            GlobalVars.OBJECT.add(new Pronoun(isPronoun,7));
            GlobalVars.WORDno++;
            return true;
        }

        // NounPronoun
        else if (isPronoun.equals("anybody")||isPronoun.equals("anyone")||isPronoun.equals("anything")||isPronoun.equals("somebody")||isPronoun.equals("someone")||isPronoun.equals("something")||isPronoun.equals("everyone")||isPronoun.equals("everybody")||isPronoun.equals("everything")||isPronoun.equals("none")||isPronoun.equals("nobody")||isPronoun.equals("nothing")||isPronoun.equals("neither")||isPronoun.equals("either")||isPronoun.equals("nor")) {
            GlobalVars.OBJECT.add(new Pronoun(isPronoun,8));
            GlobalVars.WORDno++;
            return true;
        }

        // YesNoPronoun
        else if (isPronoun.equals("no")){
            GlobalVars.OBJECT.add(new Pronoun(isPronoun,9));
            GlobalVars.WORDno++;
            return true;
        }

        else if  (isPronoun.equals("less") || isPronoun.equals("more") ) {
            GlobalVars.OBJECT.add(new Pronoun(isPronoun,10));
            GlobalVars.WORDno++;
            return true;
        }

        else if  (isPronoun.equals("least") || isPronoun.equals("most")) {
            GlobalVars.OBJECT.add(new Pronoun(isPronoun,11));
            GlobalVars.WORDno++;
            return true;
        }

        else
            return false;
    }









    private boolean findMoreGeneralPrepositions() {


        /*
         * "to"
         * to VERB TOVerb olabilir
         * ok
         */     
        if( GlobalVars.WORD[p][n].equalsIgnoreCase("to")) {

            DatabaseVars dbv = DATABASE.queryVerbDB(GlobalVars.WORD[p][n+1],p,n+1);

            if(dbv.exists()) {
                GlobalVars.OBJECT.add(new ToVerb(dbv.getStack(),dbv.getProperty()));
                GlobalVars.WORDno+=2;
            }
            else {
                GlobalVars.OBJECT.add(new Preposition("",2));
                GlobalVars.WORDno++;
            }

            return true;
        }


        /*
         * "on"
         * on DAY olabilir ( günle ayla falan )
         * ok
         */
        else if ( GlobalVars.WORD[p][n].equalsIgnoreCase("on") ) {
            String month = new String();

            if ( GlobalVars.WORDlength > n+2 && GlobalVars.WORD[p][n+1].matches("^#[0-9]+$") && (month=this._getMonth(n+2))!=null  ) {
                if( GlobalVars.WORDlength > n+3 && GlobalVars.WORD[p][n+3].matches("^#[0-9]+$")) {
                    GlobalVars.OBJECT.add( new Preposition( GlobalVars.WORD[p][n+1].substring(1) + ' ' + month + ' ' + GlobalVars.WORD[p][n+3].substring(1) + " g\u00FCn\u00FC" ) );
                    GlobalVars.WORDno +=  4 ;
                }
                else {
                    GlobalVars.OBJECT.add( new Preposition( GlobalVars.WORD[p][n+1].substring(1) + ' ' + month +  " g\u00FCn\u00FC" ) );
                    GlobalVars.WORDno +=  3 ;
                }
                return true;
            }
            else if ( GlobalVars.WORDlength > n+1 && (month=this._getMonth(n+1))!=null ) {
                if ( GlobalVars.WORDlength > n+2 && GlobalVars.WORD[p][n+2].matches("^#[0-9]+$") ) {
                    GlobalVars.OBJECT.add( new Preposition( GlobalVars.WORD[p][n+2].substring(1) + ' ' + month +  " g\u00FCn\u00FC" ) );
                    GlobalVars.WORDno +=  3 ;
                }
                else {
                    GlobalVars.OBJECT.add( new Preposition(  month +  " ay\u0131nda" ) );
                    GlobalVars.WORDno +=  2 ;
                }
                return true;
            }
            else {
                GlobalVars.OBJECT.add( new Preposition( "\u00FCzerinde" , 5 ) );
                GlobalVars.WORDno ++ ;
                return true;
            }





        }



        /*
         * in YEAR ve in ... TIMEINTERVAL
         * OK
         */
        else if ( GlobalVars.WORD[p][n].equalsIgnoreCase("in") ) {

            String [] res = new String [2];
            int jump=0;

            if( GlobalVars.WORDlength > n+1 && (res=this._getNumberSequence(n+1) )!=null ) {

                jump=Integer.parseInt(res[1]);


                if ( GlobalVars.WORD[p][n+1+jump].equalsIgnoreCase("centuries") || GlobalVars.WORD[p][n+1+jump].equalsIgnoreCase("century") )
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" y\u00FCzy\u0131lda"));
                else if ( GlobalVars.WORD[p][n+1+jump].equalsIgnoreCase("years") || GlobalVars.WORD[p][n+1+jump].equalsIgnoreCase("year") )
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" sene i\u00E7inde"));
                else if ( GlobalVars.WORD[p][n+1+jump].equalsIgnoreCase("months") || GlobalVars.WORD[p][n+1+jump].equalsIgnoreCase("month") )
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" ay i\u00E7inde"));
                else if (GlobalVars.WORD[p][n+1+jump].equalsIgnoreCase("seasons")||GlobalVars.WORD[p][n+1+jump].equalsIgnoreCase("season"))
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" sezon i\u00E7inde"));
                else if (GlobalVars.WORD[p][n+1+jump].equalsIgnoreCase("weeks")||GlobalVars.WORD[p][n+1+jump].equalsIgnoreCase("week"))
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" hafta i\u00E7inde"));
                else if ( GlobalVars.WORD[p][n+1+jump].equalsIgnoreCase("days")||GlobalVars.WORD[p][n+1+jump].equalsIgnoreCase("day"))
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" g\u00FCn i\u00E7inde"));
                else if (GlobalVars.WORD[p][n+1+jump].equalsIgnoreCase("hours")||GlobalVars.WORD[p][n+1+jump].equalsIgnoreCase("hour"))
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" saat i\u00E7inde"));
                else if (GlobalVars.WORD[p][n+1+jump].equalsIgnoreCase("minutes")||GlobalVars.WORD[p][n+1+jump].equalsIgnoreCase("minutes"))
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" dakika i\u00E7inde"));
                else if ( GlobalVars.WORD[p][n+1+jump].equalsIgnoreCase("seconds")||GlobalVars.WORD[p][n+1+jump].equalsIgnoreCase("second"))
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" saniye i\u00E7inde"));
                else {
                    GlobalVars.OBJECT.add(new Preposition("i\u00E7inde",5));
                    GlobalVars.OBJECT.add(new Numerical(res[0]));
                    GlobalVars.WORDno+=1 + jump;
                    return  true;
                }

                GlobalVars.WORDno+= 2 + jump ;
                return true;
            }


            /*
             * Sene ile ilgili olanlar, sadece nümerik olarak say? alabilr
             */
            if( GlobalVars.WORDlength > n+1 && GlobalVars.WORD[p][n+1].equalsIgnoreCase("the") )
                jump=1;

            if( GlobalVars.WORDlength > n+1+jump && GlobalVars.WORD[p][n+1+jump].matches("^#[0-9]+s?$") ) { // y?l oluyor.

                if( GlobalVars.WORD[p][n+1+jump].endsWith("s") ) {
                    GlobalVars.OBJECT.add(new Preposition( GlobalVars.WORD[p][n+1].substring(1,GlobalVars.WORD[p][n+1].length()-1)+"\'lerde" ) );
                    GlobalVars.WORDno+=2+jump;
                }
                else if ( GlobalVars.WORDlength > n+2+jump && GlobalVars.WORD[p][n+2+jump].equals("xxs#") ) {
                    GlobalVars.OBJECT.add(new Preposition( GlobalVars.WORD[p][n+1].substring(1)+"\'lerde" ) );
                    GlobalVars.WORDno+=3+jump;
                }

                else if ( jump==0 && GlobalVars.WORD[p][n+2].equalsIgnoreCase("centuries") || GlobalVars.WORD[p][n+2].equalsIgnoreCase("century") ) {
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" y\u00FCzy\u0131lda"));
                    GlobalVars.WORDno+=3;
                }


                else if ( jump==0 && GlobalVars.WORDlength > n+2 && ( GlobalVars.WORD[p][n+2].equalsIgnoreCase("years") || GlobalVars.WORD[p][n+2].equalsIgnoreCase("year") ) ) {
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n+1].substring(1)+" sene i\u00E7inde")); // yilinda
                    GlobalVars.WORDno+=3;

                }

                else if ( jump==0 && GlobalVars.WORDlength > n+2 && ( GlobalVars.WORD[p][n+2].equalsIgnoreCase("months") || GlobalVars.WORD[p][n+2].equalsIgnoreCase("month") ) ) {
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n+1].substring(1)+" ay i\u00E7inde"));
                    GlobalVars.WORDno+=3;
                }
                else if ( jump==0 && GlobalVars.WORDlength > n+2 && ( GlobalVars.WORD[p][n+2].equalsIgnoreCase("seasons")||GlobalVars.WORD[p][n+2].equalsIgnoreCase("season"))) {
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n+1].substring(1)+" sezon i\u00E7inde"));
                    GlobalVars.WORDno+=3;
                }
                else if ( jump==0 && GlobalVars.WORDlength > n+2 && ( GlobalVars.WORD[p][n+2].equalsIgnoreCase("weeks")||GlobalVars.WORD[p][n+2].equalsIgnoreCase("week"))) {
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n+1].substring(1)+" hafta i\u00E7inde"));
                    GlobalVars.WORDno+=3;
                }
                else if ( jump==0 && GlobalVars.WORDlength > n+2 && ( GlobalVars.WORD[p][n+2].equalsIgnoreCase("days")||GlobalVars.WORD[p][n+2].equalsIgnoreCase("day") ) ) {
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n+1].substring(1)+" g\u00FCn i\u00E7inde"));
                    GlobalVars.WORDno+=3;
                }



                else if ( jump==0 && GlobalVars.WORDlength > n+2 && ( GlobalVars.WORD[p][n+2].equalsIgnoreCase("hours")||GlobalVars.WORD[p][n+2].equalsIgnoreCase("hour"))) {
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n+1].substring(1)+" saat i\u00E7inde"));
                    GlobalVars.WORDno+=3;
                }
                else if ( jump==0 && GlobalVars.WORDlength > n+2 && ( GlobalVars.WORD[p][n+2].equalsIgnoreCase("minutes")||GlobalVars.WORD[p][n+2].equalsIgnoreCase("minute"))) {
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n+1].substring(1)+" dakika i\u00E7inde"));
                    GlobalVars.WORDno+=3;
                }
                else if ( jump==0 && GlobalVars.WORDlength > n+2 &&  (GlobalVars.WORD[p][n+2].equalsIgnoreCase("seconds")||GlobalVars.WORD[p][n+2].equalsIgnoreCase("second"))) {
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n+1].substring(1)+" saniye i\u00E7inde"));
                    GlobalVars.WORDno+=3;
                }



                else if (jump==0)  {


                    if ( GlobalVars.WORD[p][n+2].equalsIgnoreCase("centuries") || GlobalVars.WORD[p][n+2].equalsIgnoreCase("century") )
                        GlobalVars.OBJECT.add(new Preposition( GlobalVars.WORD[p][n+1] +" y\u00FCzy\u0131lda"));
                    else if ( GlobalVars.WORD[p][n+2].equalsIgnoreCase("years") || GlobalVars.WORD[p][n+2].equalsIgnoreCase("year") )
                        GlobalVars.OBJECT.add(new Preposition( GlobalVars.WORD[p][n+1] +" sene i\u00E7inde"));
                    else if ( GlobalVars.WORD[p][n+2].equalsIgnoreCase("months") || GlobalVars.WORD[p][n+2].equalsIgnoreCase("month") )
                        GlobalVars.OBJECT.add(new Preposition( GlobalVars.WORD[p][n+1] +" ay i\u00E7inde"));
                    else if (GlobalVars.WORD[p][n+2].equalsIgnoreCase("seasons")||GlobalVars.WORD[p][n+2].equalsIgnoreCase("season"))
                        GlobalVars.OBJECT.add(new Preposition( GlobalVars.WORD[p][n+1] +" sezon i\u00E7inde"));
                    else if (GlobalVars.WORD[p][n+2].equalsIgnoreCase("weeks")||GlobalVars.WORD[p][n+2].equalsIgnoreCase("week"))
                        GlobalVars.OBJECT.add(new Preposition( GlobalVars.WORD[p][n+1] +" hafta i\u00E7inde"));
                    else if ( GlobalVars.WORD[p][n+2].equalsIgnoreCase("days")||GlobalVars.WORD[p][n+2].equalsIgnoreCase("day"))
                        GlobalVars.OBJECT.add(new Preposition( GlobalVars.WORD[p][n+1] +" g\u00FCn i\u00E7inde"));
                    else if (GlobalVars.WORD[p][n+2].equalsIgnoreCase("hours")||GlobalVars.WORD[p][n+2].equalsIgnoreCase("hour"))
                        GlobalVars.OBJECT.add(new Preposition( GlobalVars.WORD[p][n+1] +" saat i\u00E7inde"));
                    else if (GlobalVars.WORD[p][n+2].equalsIgnoreCase("minutes")||GlobalVars.WORD[p][n+2].equalsIgnoreCase("minutes"))
                        GlobalVars.OBJECT.add(new Preposition( GlobalVars.WORD[p][n+1] +" dakika i\u00E7inde"));
                    else if ( GlobalVars.WORD[p][n+2].equalsIgnoreCase("seconds")||GlobalVars.WORD[p][n+2].equalsIgnoreCase("second"))
                        GlobalVars.OBJECT.add(new Preposition( GlobalVars.WORD[p][n+1] +" saniye i\u00E7inde"));
                    else {
                        GlobalVars.OBJECT.add(new Preposition( GlobalVars.WORD[p][n+1].substring(1)+" y\u0131l\u0131nda")); // yilinda
                        GlobalVars.WORDno+=2;
                        return true;
                    }

                    GlobalVars.WORDno+= 3 ;
                    return true;
                }




                else { // in the 200 .. durumu ..
                    GlobalVars.OBJECT.add(new Preposition("i\u00E7inde",5));
                    GlobalVars.WORDno++;
                }

                return true;
            }


            /*
             *di?erleri
             */
            else {
                GlobalVars.OBJECT.add(new Preposition("i\u00E7inde",5));
                GlobalVars.WORDno++;
                return  true;
            }
        }










        /* "For"
        * ok
        * veritaban?nda for a hour .. ve for half an hour eklensin 17.4olarak.
        */
        else if ( GlobalVars.WORD[p][n].equalsIgnoreCase("for") ) {



            String [] res = new String [2];

            if ( GlobalVars.WORD[p][n+1].matches("^#[0-9]+$") ) {


                if( GlobalVars.WORD[p][n+2].equals("centuries") || GlobalVars.WORD[p][n+2].equals("century") )
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+" y\u00FCzy\u0131l boyuncana"));
                else if ( GlobalVars.WORD[p][n+2].equals("years") || GlobalVars.WORD[p][n+2].equals("year") )

                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+" sene boyuncana"));


                else if ( GlobalVars.WORD[p][n+2].equals("seasons") || GlobalVars.WORD[p][n+2].equals("season") )
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+" sezon boyuncana"));
                else if ( GlobalVars.WORD[p][n+2].equals("months") || GlobalVars.WORD[p][n+2].equals("month") )
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+" ay boyuncana"));


                else if ( GlobalVars.WORD[p][n+2].equals("weeks") || GlobalVars.WORD[p][n+2].equals("week") )
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+" hafta boyuncana"));
                else if ( GlobalVars.WORD[p][n+2].equals("days") || GlobalVars.WORD[p][n+2].equals("day") )
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+" g\u00FCn boyuncana"));
                else if ( GlobalVars.WORD[p][n+2].equals("hours") || GlobalVars.WORD[p][n+2].equals("hour") )
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+" saat boyuncana"));
                else if ( GlobalVars.WORD[p][n+2].equals("minutes") || GlobalVars.WORD[p][n+2].equals("minute") )
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+" dakika boyuncana"));
                else if ( GlobalVars.WORD[p][n+2].equals("seconds") || GlobalVars.WORD[p][n+2].equals("second") )
                    GlobalVars.OBJECT.add(new Preposition(GlobalVars.WORD[p][n].substring(1)+" saniye boyuncana"));
                else {

                    GlobalVars.OBJECT.add(new Preposition("i\u00E7in",0));
                    GlobalVars.OBJECT.add( new Numerical( GlobalVars.WORD[p][n+1].substring(1) ) );
                    GlobalVars.WORDno += 2 ;
                    return true;

                }
                GlobalVars.WORDno+=3;
                return true;



            }

            else if ( (res=_getNumberSequence(n+1))!=null ) { //performans? önemli ölçüde etkiliyor bu metod !!


                int jump = Integer.parseInt(res[1]);



                if( GlobalVars.WORD[p][n+jump+1].equals("centuries") || GlobalVars.WORD[p][n+jump+1].equals("century") )
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" y\u00FCzy\u0131l boyuncana"));
                else if ( GlobalVars.WORD[p][n+jump+1].equals("years") || GlobalVars.WORD[p][n+jump+1].equals("year") )

                    GlobalVars.OBJECT.add(new Preposition(res[0]+" sene boyuncana"));


                else if ( GlobalVars.WORD[p][n+jump+1].equals("seasons") || GlobalVars.WORD[p][n+jump+1].equals("season") )
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" sezon boyuncana"));
                else if ( GlobalVars.WORD[p][n+jump+1].equals("months") || GlobalVars.WORD[p][n+jump+1].equals("month") )
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" ay boyuncana"));


                else if ( GlobalVars.WORD[p][n+jump+1].equals("weeks") || GlobalVars.WORD[p][n+jump+1].equals("week") )
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" hafta boyuncana"));
                else if ( GlobalVars.WORD[p][n+jump+1].equals("days") || GlobalVars.WORD[p][n+jump+1].equals("day") )
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" g\u00FCn boyuncana"));
                else if ( GlobalVars.WORD[p][n+jump+1].equals("hours") || GlobalVars.WORD[p][n+jump+1].equals("hour") )
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" saat boyuncana"));
                else if ( GlobalVars.WORD[p][n+jump+1].equals("minutes") || GlobalVars.WORD[p][+jump+1].equals("minute") )
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" dakika boyuncana"));
                else if ( GlobalVars.WORD[p][n+jump+1].equals("seconds") || GlobalVars.WORD[p][n+jump+1].equals("second") )
                    GlobalVars.OBJECT.add(new Preposition(res[0]+" saniye boyuncana"));
                else {

                    GlobalVars.OBJECT.add(new Preposition("i\u00E7in",0));
                    GlobalVars.OBJECT.add( new Numerical( res[0] ) );
                    GlobalVars.WORDno += 1+jump ;
                    return true;

                }
                GlobalVars.WORDno+=2+jump;
                return true;
            }



            else {

                GlobalVars.OBJECT.add(new Preposition("i\u00E7in",0));
                GlobalVars.WORDno++ ;
                return true;

            }









        }
















        else
            return false;




    }


    /**
     * Arka arkaya gidiyorsa say? dizisi ( twenty four )
     * bunu döndürüyor.
     * Döndürülen dizinin ilk eleman? say?n?n Türkçe kar??l??? ;
     * ikinci eleman? kaç kelime atland???n?n String formudur.
     * @param nn Hangi nolu kelimeden ba?l?yor
     * @return String [] Tükçe kar??l?k + z?pla
     */
    private String [] _getNumberSequence(int nn) {

        int start = nn;
        StringBuffer toReturn=new StringBuffer();
        String temp = new String();

        do {

            if((temp=_getNumber(nn))!=null) {
                toReturn.append(" "+temp);
                nn++;
            }


        }
        while( nn < GlobalVars.WORDlength && temp!=null);

        if(nn==start)
            return null;
        else
            return new String [] { toReturn.toString().substring(1), Integer.toString(nn-start) };

    }


    /**
     * Istenilen spesifik kelimeden say? var m? yok mu diye bak?yor.
     * Varsa, bu say?n?n Türkçesini ç?kartp döndürüyor.
     * @param nn Kaç nolu kelime
     * @return Say?n?n Türkçe kar??l???
     */
    private String _getNumber(int nn) {

        String toReturn=   GlobalVars.WORD[p][nn].toLowerCase();

        // içinde - bar?nd?r?yorda onu siliyor.
        if( toReturn.indexOf('-')!=-1 )
            toReturn=toReturn.replaceAll("-","");

        if( toReturn.matches("^((trillionand)|(billionand)|(millionand)|(thousandand)|(hundredand)|(one)|(two)|(three)|(four)|(five)|(six)|(seven)|(eight)|(nine)|(ten)|(eleven)|(twelve)|(thirteen)|(fourteen)|(fifteen)|(sixteen)|(seventeen)|(eighteen)|(nineteen)|(twenty)|(thirty)|(forty)|(fifty)|(sixty)|(seventy)|(eighty)|(ninety)|(hundred)|(thousand)|(million)|(billion)|(trillion))+$") ) {

            toReturn = toReturn.replaceAll("trillionand"," trilyon");
            toReturn = toReturn.replaceAll("billionand"," milyar");
            toReturn = toReturn.replaceAll("millionand"," milyon");
            toReturn = toReturn.replaceAll("thousandand"," bin");
            toReturn = toReturn.replaceAll("hundredand"," y\u00FCz");
            toReturn = toReturn .replaceAll("thirteen"," on \u00FC\u00E7");
            toReturn = toReturn .replaceAll("fourteen"," on d\u00F6rt");
            toReturn = toReturn .replaceAll("fifteen"," on be\u015F");
            toReturn = toReturn .replaceAll("sixteen"," on alt\u0131");
            toReturn = toReturn .replaceAll("seventeen"," on yedi");
            toReturn = toReturn .replaceAll("eighteen"," on sekiz");
            toReturn = toReturn .replaceAll("nineteen"," on dokuz");
            toReturn = toReturn .replaceAll("twenty"," yirmi");
            toReturn = toReturn .replaceAll("thirty"," otuz");
            toReturn = toReturn .replaceAll("forty"," k\u0131rk");
            toReturn = toReturn .replaceAll("fifty"," elli");
            toReturn = toReturn .replaceAll("sixty"," altm\u0131\u015F");
            toReturn = toReturn .replaceAll("seventy"," yetmi\u015f");
            toReturn = toReturn .replaceAll("eighty"," seksen");
            toReturn = toReturn .replaceAll("ninety"," doksan");
            toReturn = toReturn .replaceAll("one"," bir");
            toReturn = toReturn .replaceAll("two"," iki");
            toReturn = toReturn .replaceAll("three"," \u00FC\u00E7"); //uc
            toReturn = toReturn .replaceAll("four"," d\u00F6rt");
            toReturn = toReturn .replaceAll("five"," be\u015F");
            toReturn = toReturn .replaceAll("six"," alt\u0131");
            toReturn = toReturn .replaceAll("seven"," yedi");
            toReturn = toReturn .replaceAll("eight"," sekiz");
            toReturn = toReturn .replaceAll("nine"," dokuz");
            toReturn = toReturn .replaceAll("ten"," on");
            toReturn = toReturn .replaceAll("eleven"," on bir");
            toReturn = toReturn .replaceAll("twelve"," on iki");

            toReturn = toReturn .replaceAll("hundred"," y\u00FCz");
            toReturn = toReturn .replaceAll("thousand"," bin");
            toReturn = toReturn .replaceAll("million"," milyon");
            toReturn = toReturn .replaceAll("billion"," milyar");
            toReturn = toReturn .replaceAll("trillion"," trilyon");

            toReturn = toReturn .replaceAll("bir bin","bin");
            toReturn = toReturn .replaceAll("bir y\u00FCz","y\u00FCz");

            return toReturn.substring(1);
        }
        else
            return null;
    }




    /**
     * Parametre olarak girilen nodaki kelimenin bir ay ismi olu olmad???n?
     * (null veya de?il) ve, ay ismiyse türkçe kar??l???n?n en oldu?unu
     * döndürüyor.
     * @param nn Kelime no
     * @return Türkçe kar??l???
     */
    private String _getMonth(int nn) {

        String isMonth=GlobalVars.WORD[p][nn].toLowerCase();

        if(isMonth.equals("january"))
            return "Ocak";
        else if(isMonth.equals("february"))
            return "\u015Eubat";
        else if(isMonth.equals("march"))
            return "Mart";
        else if(isMonth.equals("april"))
            return "Nisan";
        else if(isMonth.equals("may"))
            return "May\u0131s";
        else if(isMonth.equals("june"))
            return "Haziran";
        else if(isMonth.equals("july"))
            return "Temmuz";
        else if(isMonth.equals("august"))
            return "A\u011Fustos";
        else if(isMonth.equals("september"))
            return "Eyl\u00FCl";
        else if(isMonth.equals("october"))
            return  "Ekim";
        else if(isMonth.equals("november"))
            return "Kas\u0131m";
        else if(isMonth.equals("december"))
            return "Aral\u0131k";
        else
            return null;



    }















    /**
    * Veritabanindan eslesik kelimeyi cekiyor.
    * Eslesik kelime bircok kelimeden olusuyorsa son kelimenin plural olma olasiligi DATABASE.queryDB methodu tarafindan degerlendiriliyor.
    * @return Shell de base method unda kullanilmak üzere true veya false
    */

    private boolean findGENERAL() {



        DatabaseVars dbv = DATABASE.queryWholeDB(GlobalVars.WORD[p][n],p,n);

        if( dbv.exists() ) {
            setOBJECTvars(dbv.getType(),dbv.getProperty(),dbv.getStack(),dbv.getJump());
            return true;
        }

        else
            return false;

    }




    /**
    * Sonu S ile biten kelimeleri belirliyor.
    * Sonu s ile bitenler cogul olabilecekleri gibi, simple present da 3s te olabilirler.
    * @return Shell de base method unda kullanilmak üzere true veya false
    */
    private boolean findEndsS() {

        String s = GlobalVars.WORD[p][n].toLowerCase();

        if( s.endsWith("s") ) {


            DatabaseVars dbv = DATABASE.queryVerbPPAndNounAndVerbDB(s.substring(0,(s.length()-1)),p,n);

            if(dbv.exists()) {

                if( dbv.getType()==1 )
                    GlobalVars.OBJECT.add(new Noun(dbv.getStack(),dbv.getProperty()+10));
                else if ( dbv.getType()==2 )
                    GlobalVars.OBJECT.add(new Verb(dbv.getStack(),dbv.getProperty()+1000000));
                else
                    GlobalVars.OBJECT.add(new Noun(dbv.getStack().substring(dbv.getStack().indexOf('+')+2),10));

                GlobalVars.WORDno += dbv.getJump();
                return true;
            }

            else if ( s.endsWith("ses") || s.endsWith("xes") || s.endsWith("oes") || s.endsWith("zes") || s.endsWith("ches") || s.endsWith("shes") ) {

                DatabaseVars dbv2 = DATABASE.queryVerbPPAndNounAndVerbDB(s.substring(0,(s.length()-2)),p,n);

                if(dbv2.exists()) {
                    if(dbv2.getType()==1)
                        GlobalVars.OBJECT.add(new Noun(dbv2.getStack(),dbv2.getProperty()+10));
                    else if (dbv2.getType()==2)
                        GlobalVars.OBJECT.add(new Verb(dbv2.getStack(),dbv2.getProperty()+1000000));
                    else
                        GlobalVars.OBJECT.add(new Noun(dbv.getStack().substring(dbv.getStack().indexOf('+')+2),10));

                    GlobalVars.WORDno += dbv2.getJump();
                    return true;
                }

                else return false;
            }

            else if ( s.endsWith("aies") || s.endsWith("eies") || s.endsWith("oies") || s.endsWith("uies") || s.endsWith("iies") ) {

                DatabaseVars dbv2= DATABASE.queryVerbPPAndNounAndVerbDB(s.substring(0,(s.length()-3))+"y",p,n);

                if(dbv2.exists()) {
                    if(dbv2.getType()==1)
                        GlobalVars.OBJECT.add(new Noun(dbv2.getStack(),dbv2.getProperty()+10));
                    else if (dbv2.getType()==2)
                        GlobalVars.OBJECT.add(new Verb(dbv2.getStack(),dbv2.getProperty()+1000000));
                    else
                        GlobalVars.OBJECT.add(new Noun(dbv2.getStack().substring(dbv2.getStack().indexOf('+')+2),10));
                    GlobalVars.WORDno += dbv2.getJump();
                    return true;
                }

                else return false;
            }

            else return false;
        }
        else return false;

    }




    /**
    * Sonu D ile biten kelimeleri belirliyor(past simple past participle).
    * @return Shell de base method unda kullanilmak üzere true veya false
    */

    private boolean findEndsD() {


        String s = GlobalVars.WORD[p][n].toLowerCase();
        
        /* Her zamanki gibi önce istisnalar : */
        if(s.equals("supposed")) {
            GlobalVars.OBJECT.add(new EndsD("^^varsay",21001));
            GlobalVars.WORDno++;
            return true;
        }

        
        
        /* Ana k?sma geçtik */
        if( s.endsWith("id") ) {

            DatabaseVars dbv = DATABASE.queryVerbDB( s.substring(0,s.length()-2)+'y' ,p, n );

            if( dbv.exists() ) {

                GlobalVars.OBJECT.add(new EndsD(dbv.getStack(),dbv.getProperty()));
                GlobalVars.WORDno+=dbv.getJump();
                return true;
            }

            else
                return false;

        }

        else if ( s.endsWith("ed") ) {

            DatabaseVars dbv = DATABASE.queryVerbDB( s.substring(0,(s.length()-2)),p , n );

            if( dbv.exists() ) {

                GlobalVars.OBJECT.add(new EndsD(dbv.getStack(),dbv.getProperty()));
                GlobalVars.WORDno+=dbv.getJump();
                return true;
            }

            else if ( s.endsWith("ied") ) {

                DatabaseVars dbv2 = DATABASE.queryVerbDB( s.substring(0,(s.length()-3))+"y",p , n );

                if ( dbv2.exists() ) {

                    GlobalVars.OBJECT.add(new EndsD(dbv2.getStack(),dbv2.getProperty()));
                    GlobalVars.WORDno+=dbv2.getJump();
                    return true;
                }

                else return false;

            }

            else if ( s.endsWith("cked") || ( s.length()>5 && s.charAt(s.length()-3)==s.charAt(s.length()-4) ) ) {

                DatabaseVars dbv2 = DATABASE.queryVerbDB( s.substring(0,(s.length()-3)),p,n);

                if( dbv2.exists() ) {

                    GlobalVars.OBJECT.add(new EndsD(dbv2.getStack(),dbv2.getProperty()));
                    GlobalVars.WORDno+=dbv2.getJump();
                    return true;
                }

                else return false;
            }

            else
                return false;

        }

        else
            return false;
    }


    /**
    * Sonu ING ile biten kelimeleri belirliyor.
    * "Going To Verb" blogu icin yeni bir EndsINGs?n?f? ollusturuluyor.
    * Parameresiz bu nesne eldeki durumu LEFTVERBS s?n?f? icin sakliyor.
    * @return Shell de base method unda kullanilmak üzere true veya false
    */


    private boolean findEndsING() {


        String s = GlobalVars.WORD[p][n].toLowerCase();
        
        /*
         * Builk a?aman?n amac? going to fiillerini bulmak çünkü bunlar
         * özellikle CYCLE3 içndeLEFTVERBS s?n?f?nda bol bol kullan?l?yor.
         */
        if( GlobalVars.WORDlength > n+1 && s.equals("going") && ( GlobalVars.WORD[p][n+1].equalsIgnoreCase("to") || GlobalVars.WORD[p][n+1].equals("#2") ) ) {
            DatabaseVars dbv = DATABASE.queryVerbDB(GlobalVars.WORD[p][n+2].toLowerCase(),p,n+2);            
            if(dbv.exists()) {
                GlobalVars.OBJECT.add(new EndsING());
                GlobalVars.OBJECT.add(new ToVerb(dbv.getStack(),dbv.getProperty()));
                GlobalVars.WORDno+=2+dbv.getJump();
            }
            else {
                GlobalVars.OBJECT.add(new EndsING("git",2));
                GlobalVars.WORDno+=2;
            }
            
            return true;
        }
        
        else if ( s.equals("being") ) {
            GlobalVars.OBJECT.add(new EndsING("be#",0));
            GlobalVars.WORDno++;
            return true;
        }

        
        /*
         * Ana k?s?m burada ba?l?yor.
         */
        if( s.endsWith("ing") ) {

            DatabaseVars dbv = DATABASE.queryVerbDB(s.substring(0,s.length()-3),p,n);

            if( dbv.exists() ) {

                GlobalVars.OBJECT.add( new EndsING( dbv.getStack() , dbv.getProperty() ) ) ;
                GlobalVars.WORDno += dbv.getJump();
                return true;
            }

            else if( s.endsWith("ying") ) {

                DatabaseVars dbv2 = DATABASE.queryVerbDB(s.substring(0,(s.length()-4))+"ie",p,n);

                if( dbv2.exists() ) {

                    GlobalVars.OBJECT.add(new EndsING(dbv2.getStack(),dbv2.getProperty()));
                    GlobalVars.WORDno+=dbv2.getJump();
                    return true;
                }

                else
                    return false;
            }

            else if ( s.endsWith("icking") || ( s.length()>6 && s.charAt(s.length()-4)==s.charAt(s.length()-5) ) ) {

                DatabaseVars dbv2 = DATABASE.queryVerbDB(s.substring(0,s.length()-4)+"ie",p,n);
                if( dbv2.exists() ) {
                    GlobalVars.OBJECT.add(new EndsING(dbv2.getStack(),dbv2.getProperty()));
                    GlobalVars.WORDno+=dbv2.getJump();
                    return true;
                }

                else return false;


            }
            else return false;
        }

        else
            return false;
    }


    private boolean findVerb() {



        if( go(p,n) ) {
            System.out.println(GlobalVars.WORD[p][n]);
            return true;
        }
        else
            return false;

    }


    /**
     * Haf?zay? daha verimli kullanmak için s?n?f? sonland?ran metod.
     */
public void finalize() {}

}
