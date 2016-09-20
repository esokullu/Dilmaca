package dilbaz.en2tr;

/*
 * it's .. o'clock falan veritaban�na ge�ecek ..
 * anything you like falan da ge�ecek ama yeni bir t�r m� _?
 * the cake of my father and his red .. denem bakal�m
 */





/**
* ���nc� cycle da son verileri d�zene sokmaya yariyor.<br>
* Shell sinifi tarafindan kontrol ediliyor.
* @author Emre Sokullu
*/
class CYCLE3{



    /**
     * Bu s?n?f?n son yordam? finalize() da TRANSLATED ?n ka�?nc?
     * dizisine ula??ca??m?z? belirlemkte g�revli.
     */
    private int phraseNo=0;


    /**
     * CYCLE 2 da ortaya �?kan OBJECT OBJECTList ini yorumlayip
     * turk�e tam kar??l??? bulmaya �al???yor.<br>
     * S?n?f kurucu yordam?n?n en sonunda finalize() yordam?n?
     * icerdi?i icin kendi kendini yok ediyor.
     * @param i TRANSLATED ta kullan?lmak �zere cumle s?ras?.
     */

    public CYCLE3(int i) {
        this.phraseNo = i;


        for(int n=0;n<GlobalVars.OBJECT.size();n++)
            System.out.println(" --> "+GlobalVars.OBJECT.get(n).getClass().getName());




        /* Bu fonksionla c�mlenin ba��ndaki Unchangeablelar�, zarflar� (firstly falan gibi), Commalar�
         * SentenceConnector lar�, unchangeable olarak sabitliyoruz.
         */
        dealWithUnchangeablesAtTheBeginningOfTheSentence();
        /* Bir�ok gereksiz zarf SentenceConnector, Comma elendi. Art�k emir kiplerini ve
         * soru c�mlelerini bulmak kolayla�t�.
         */


        /* Bu fonksionla da sentece connector lardan sonra gelen zarf,Comma,Unchangeable cinsi
         * kelimeler birle�tiriliyor.
         */
        dealWithUnchangeablesInTheSentence();
        /* C�mle ortas�ndaki, bir�ok gereksiz zarf SentenceConnector, Comma elendi. Art�k emir kiplerini ve
         * soru c�mlelerini bulmak kolayla�t�.
         */        


        /* Bir sonraki ad�mda insan isimlerini birle�tiricez. Bunun i�in veritaban�ndan �ekilen
         * insan isimleriyle, anlam verilemeyen kelimeleri kulllan�caz.
         */
        mergeHumanNames();
        /* B�ylece insan isim soyadlar� tek bir nesneyle temsil edildi�inden di�er basamaklardaki
         * i�lerimiz daha kolayla�t�.
         * Bir de art�k type=9 isim yani mayBeHumanName kalmad�, hepsinin type lar� 0lan�p
         * isim oldular
         */


        /*
         * Say�sallardan sonra gelen Nonlar birle�tiriliyor ve onlar�n �o�ul olmas� engelleniyor.
         * Ayn� �ekilde tekil olan bir de �yle ..
         * Di�er Numericallar da isim oluyor.
         * Bir de buran�n sonunda takille�tirlmeyen �o�ullara art�k �o�ul ekleri tak�l�yor.
         */
        dealWithNumericsAndPlurals(); // plurallar artik �ekimli
        /*
         * Art�k plurallar�m�z �ekimli. Ve elimizde hi� Numerical kalmad� ..
         * Noun olan Numerical lar article l� Noun lar gibi oldu !!
         */


        /*
        * Prononun Article Conjunction durumlarina gore verbpp verb noun endsing endsd
        * kelimeleri sifat veya isim yapabiliyor.
        */
        clarifyVerbsAndNounsWRTPositionOfDefiners();
        /*
         * Baz� mayVerb Nounlar Verbler VerbPPler EndsINGler ve EndsD ler elendi
         * yerlerine Nounlar geldi ( baz�lar� s�fat olmak �zere )
         */





        /*
        * Verb, EndsD, mayBeVerb Noun gibi fiil olma olasiligi olan t�rlerin who/where
        * gibi query pronounlardan sonra
        * gelme durumlarina g�re onlari conjugated verb sinifina sokuyor.
        */
        findSomeConjugatedVerbs();
        /*
         * baz� verb endsD nounlar elenip yerlerine ConjugatedVerb geliyor.
         */




        /*
         * Virg�lle ayr�lm�� ConjugatedVerbleri birle�tiriyor.
         */
        connectVerbsSeparatedByCommas();
        /*
         * ConjugatedVerbleri azalt�yor, toparl�yor.
         */




        ////dealWithPronoun6();




        /*
         * G�revi normalde ne �ekilde kullan�laca�� belli olmayan "that"in g�revine
         * a��kl�k getirmek
         */
        clarifyTheSituationOfThePronounThat();
        /*
         * artik adjectivePronoun olan that ler s�fat olarak kayda ge�ti�inden bir 
         * problem veya kar���kl�la kar��la��lmayacak.
         */







        /*
         * S�fat ve zarflar� birle�tiriyor, kendi aralar�nda . ��yleki ..
         * Yinelenen s�fat zarf dizileri, dizinin en sonundaki kelimenin t�r�n� al�yor.
         * Dizi so very too gibi bir�eyle kar��la��rsa otomatikman duruyor.
         * EndsD EndsING ve VerbPPlerle ilgilenmiyoruz.. Neden ?
         * EndsD fil olma ihtimali y�ksek, leftverblerle ileti�ime ge�ebilir.
         * EndsING zaten �ok kullan�lanlar� veritaban�nda, geri kalanlarda isim de olu�turabilir ..
         * bu ihtimali de�erlendirmek daha mant�kl� ��nk� bir de �st�ne �stl�k
         * LeftVeblerle ili�ikte olma durumu var.
         * VerbPPler de LeftVerblerle ili�ikte olabilir .. ama olmazlarsa zaten daha sonra s�fata �evrilecekler
         * Dolay��yls bu basamak daha sonradan bir ke daha �a�r�labilir.
         */
        mergeAdjectivesAndAdverbs();
        /*
         * Art�k elimizde daha derli toplu s�fat ve zarflar var.
         */






        /*
         * Fiilden sonra gelen "no" lar .. fiile negatiflik anlam� kat�yor.
         * Onlar bulunup bu negatiflik anlam� kat�l�yor.
         * Bir de adverbler bulunup fiile ekleniyorlar.
         */
        dealWithAdverbsAndNONearVerbs();
        /*
         * Art�k adverb konusunda daha rahat�z ve "No" lardan i�imiz rahat ..
         * Bundan sonra no sugar veya no, olabilir
         */


        /*
         * Isimlerle sifatlar birlesitiriliyor. araya xxMayTakeIndefiniteArticle# koyuluyor ki
         * arkadan bir a/an gelirse bir oraya konabilsin.
         * bir de somebody very preciou falan gibi durumlarla ilgileniliyo.
         */
        attachAdjectivesAndNouns(); // bi de somebody very precious for me
        /*
         * isimlerle sifatlar birle�ti.
         * dealWithNumerics'ten itibaren ilk defa article l� Noun ��kaartma olay�
         * someone very special gibi ..
         * baz� kelimeler xx_may_take_indefinite_Article i�areti var.
         */



        /*
         * bir�ok kelimeye article kopyuluyor ve art�k article kilidi oluyor.
         * bu i�lem esnas�nda xxmay_take_indfinite_article i�aretine dikkat ediliyor.
         */
        putArticles();
        /*
         * bir �ok kelime article l� yani dokunulmazl�klar� var ..
         * hi� xxmay_take_indefinite_article i�areti kalmad� ..
         */


        /*
         * adjective pronounlar� da koyup onla� articled Noun yap�yor
         */
        putAdjectivePronouns(); // onlar kalmiyor, Noun ya da Article
        /*
         * belirsiz noun iyice azald�, adjective pronoun da kalm�yor
         */



        /*
         * virg�llerle ayr� isimler birle�tirilecek
         */
        connectNounsSeparatedByCommas();
        /*
         * isimler art�k daha derli toplu
         */

        /*
         * isimlerde son i�lem .. conjunctionlar bitiyor.
         */
        organizeConjunctions();
        /*
         * isimlerle i�miz bitti
         * isim tamlamalar� aer�k ok
         */


        /*
         * kullan�lacak endsIng endsD verbpp  verb ve noun(mayBeVerb) ler kullan�l�yor
         * soru c�mleleri bulunmu� olunuyor.
         */
        dealWithLeftVerbs();
        /*
         * daha �ok ConjugatedVerb, daha az bulan�k..
         */






        /*
         * baz� "that" ve "endsIng"ler eleniyor
         */
        dealWithConjugatedVerbProperties();
        /*
         * that ler bitmi� oluyor.
         * 
         */        












        /*
         * endsIng ler t�ketiliyor, �nlerindeki nounlarla birle�tirliyor veya birle�tirlmeksizin
         * Noun a d�n���yor
         */
        findOutGerunds();
        /*
         * no more endsIng, more Noun
         */


        /*
         * Art�k kelimeler ortaya ��kt���na g�re Preposition ve AdjectivePronounlar�
         * �nlerindeki Nounlarla birle�tirebiliriz.
         * �nde endsD noun(mayBeVerb) verbpp  verb varsa onlarda noun oluyor.
         * e�er bunlar endsD ve verbpp ise Temporary oluyolar sonra bu s�n�f bi daha �a�r�l�yor.
         * adjPattern in e�i yoksa o zaman Noun oluyor.
         */
        finishPrepositionsAndAdjectivePatterns();
        /*
         * bulan�klar daha az
         * preposition ve adj patternler daha fazla
         */



        /*
         * BE#leri ConjugatedVerbe yap��t�r ya da Noun(Adjective) yap
         */
        dealWith_BE();
        /* daha �ok Noun, AdjectivePAtern de kalmad�
         */


        /*
         * Bulan�klar� endsD verbpp verb noun(maybeverb) bitiriyor
         */
        finishFuzzies();
        /*
         * 4 kelime t�r� daha elendi
         */



        theWhoseTheDo();


        dealWith_Whose();


        theWhoDo();

        theThatTheDo();
        finalize();

    }


    /**
    * dealWithUnchangeablesAtTheBeginningOfTheSentence()<br>
    * Cumlenin basindaki sentence connectorlari (7) , zarflari (1.3) , ve unchangeablelari (17) belirleyip, birlesitiriyor. Aralardaki commalari ( 11 ) lari da unutmadan.
    * <br>
    * <u>Kullanilan T�rler</u> 1.3 ,7 , 11 ,17 <br>
    * <u>Elenen T�rler</u> : 1.3 , 7 <br>
    * <u>Eklenen T�rler</u> :  17
    * @return void
    */


    private void dealWithUnchangeablesAtTheBeginningOfTheSentence() {

        int i;

        for ( i = 0 ; i < GlobalVars.OBJECT.size() ; i++ ) {
            if ( !( GlobalVars.OBJECT.get(i) instanceof Comma || GlobalVars.OBJECT.get(i) instanceof Unchangeable || GlobalVars.OBJECT.get(i) instanceof SentenceConnector || ( GlobalVars.OBJECT.get(i) instanceof Noun && GlobalVars.OBJECT.getNoun(i).isAdverb() ) || ( GlobalVars.OBJECT.get(i) instanceof Preposition && GlobalVars.OBJECT.getPreposition(i).isLocked() ) ) )
                break;

            else {
                if( i!=0 )
                    GlobalVars.OBJECT.getUnchangeable(0).append( GlobalVars.OBJECT.getOBJECT(i).getStack() );

                else if ( !( GlobalVars.OBJECT.get(i) instanceof Unchangeable ) )
                    GlobalVars.OBJECT.set( 0 , new Unchangeable( GlobalVars.OBJECT.getOBJECT(0).getStack() ) );


            }
        }

        for ( i = i-1 ; i >= 1 ; i-- ) {
            GlobalVars.OBJECT.remove(i);
        }
    }









    private void dealWithUnchangeablesInTheSentence() {

        int i,j;

        // c�mlenin i�de olaca�� i�in d�ng� rakamlar� biraz de�i�ik.
        for ( i= GlobalVars.OBJECT.size()-3 ; i>=1 ; i-- ) {

            if ( GlobalVars.OBJECT.get(i) instanceof SentenceConnector )   {






                for ( j = i+1 ; j < GlobalVars.OBJECT.size() ; j++ ) {
                    if ( !( GlobalVars.OBJECT.get(j) instanceof Comma || GlobalVars.OBJECT.get(j) instanceof Unchangeable || GlobalVars.OBJECT.get(j) instanceof SentenceConnector || ( GlobalVars.OBJECT.get(j) instanceof Noun && GlobalVars.OBJECT.getNoun(j).isAdverb() ) || ( GlobalVars.OBJECT.get(i) instanceof Preposition && GlobalVars.OBJECT.getPreposition(i).isLocked() ) ) )
                        break;

                    else

                        GlobalVars.OBJECT.getSentenceConnector(i).append( GlobalVars.OBJECT.getOBJECT(j).getStack() );
                }

                for ( j = j-1 ; j >= i+1 ; j-- )
                    GlobalVars.OBJECT.remove(j);














            }



        }

    }
















    /**
    * mergeHumanNames()
    * Amaci arka arkaya gelen insan isimlerini birlstirerek ad soyad kombinasyonlarini ger�eklestirebilmek.
    * Ileride kullanicilar # disinda bir seyle de baslayabilicek ** gibi.. Bu da insan ismi yerine ge�ecek.
    * <br>
    * <u>Kullanilan t�rler</u> : 1.7
    * ! Elenen  veya eklenen bir t�r yok.
    * @return void
    */

    private void mergeHumanNames() {

        int i;


        for ( i = GlobalVars.OBJECT.size()-1 ; i>=1 ; i-- ) {

            if ( GlobalVars.OBJECT.get(i) instanceof Noun && GlobalVars.OBJECT.get(i-1) instanceof Noun &&  ( GlobalVars.OBJECT.getNoun(i).isHumanName() || GlobalVars.OBJECT.getNoun(i-1).isHumanName() ) ) {

                if ( ( GlobalVars.OBJECT.getNoun(i).isHumanName() || GlobalVars.OBJECT.getNoun(i).mayBeHumanName() ) && GlobalVars.OBJECT.getNoun(i-1).isHumanName() ) {
                    GlobalVars.OBJECT.getNoun(i-1).append(GlobalVars.OBJECT.getNoun(i).getNoun());
                    GlobalVars.OBJECT.remove(i);
                }
                else if ( ( GlobalVars.OBJECT.getNoun(i-1).mayBeHumanName() || GlobalVars.OBJECT.getNoun(i-1).isHumanName() ) && GlobalVars.OBJECT.getNoun(i).isHumanName() ) {

                    GlobalVars.OBJECT.getNoun(i-1).append(GlobalVars.OBJECT.getNoun(i).getNoun());
                    GlobalVars.OBJECT.getNoun(i-1).setHumanName();
                    GlobalVars.OBJECT.remove(i);
                }

            }
            
            else if ( GlobalVars.OBJECT.get(i) instanceof Noun && GlobalVars.OBJECT.getNoun(i).mayBeHumanName() ) {
                GlobalVars.OBJECT.getNoun(i).isntHumanName();
                
                
            }
        }
    }


    /**
    * dealWithNumericsAndPlurals()
    * Ingilizce'de 1 den b�y�k sayilarin arkasindaki isimler �ogul oluyor.
    * Ama bunlar T�rk�e'ye �evrilince tekile �evriliyor. Bu basamakta bu tip durumlari inceliyoruz.
    * Ayni zamanda diger hem  Noun hem cogul olanlara cogul ekleri verilecek.
    * !! amalamalar� illaki false olarak ge�iiyor burada bir hata olabilir.
    * <br>
    * <u>Kullanilan t�rler</u> : Numerical Noun Plural<br>
    * <u>Elenen t�rler</u> : Bir kisim Verb ve Numerical<br>
    * <u>Eklenen t�rler</u> : Noun<br>
    * @return void
    */

    private void dealWithNumericsAndPlurals()  {

        for ( int i = GlobalVars.OBJECT.size()-1 ; i >=0 ; i-- ) {


            // burada sadece fiillerin ve isimlerin  bu kategoriye alinmalarinin sebebi, digerlerinin alinmamasinin sebebi, onlar isim disinda sifat da olabili VERBPP ENDSING ENDSD
            if ( i>=1 && GlobalVars.OBJECT.get(i-1) instanceof Numerical ) {
                if ( GlobalVars.OBJECT.get(i) instanceof Noun || GlobalVars.OBJECT.get(i) instanceof Verb ) {
                    if(!GlobalVars.OBJECT.getNumerical(i-1).isOne())

                        GlobalVars.OBJECT.set(i-1,new Noun(GlobalVars.OBJECT.getNumerical(i-1).getStack()+' '+GlobalVars.OBJECT.getOBJECT(i).getStack(),true,false)); // cogul ve article li


                    else

                        GlobalVars.OBJECT.set(i-1,new Noun(GlobalVars.OBJECT.getNumerical(i-1).getStack()+GlobalVars.OBJECT.getOBJECT(i).getStack(),true,false)); // cogul ve article li

                    GlobalVars.OBJECT.remove(i);
                    i--;
                }
                else if ( GlobalVars.OBJECT.get(i) instanceof EndsD || GlobalVars.OBJECT.get(i) instanceof EndsING || GlobalVars.OBJECT.get(i) instanceof VerbPP ) { // yani EndsD EndsING VerbPP
                    GlobalVars.OBJECT.set(i-1, GlobalVars.OBJECT.getNumerical(i-1).toAdjective() );
                    i--;
                }

                else {  // hem de article alm�� oluyor.
                    GlobalVars.OBJECT.set(i-1,GlobalVars.OBJECT.getNumerical(i-1).toNoun() );
                    i--;
                }
            }
            else if ( GlobalVars.OBJECT.get(i) instanceof Noun && GlobalVars.OBJECT.getNoun(i).isPlural() && !GlobalVars.OBJECT.getNoun(i).mayBeVerb() ) {
                GlobalVars.OBJECT.getNoun(i).makePlural();
            }

        }
    }












    /**
    * clarifyVerbsAndNounsWRTPositionOfDefiners()
    * prononun article conjunction durumlarina gore verbpp verb noun endsing endsd
    * kelimeleri sifat veya isim yapabiliyor.
    * <br>
    * <u>Kullanilan t�rler</u> : conjunction , pronoun ,article , endsing ,endsd ,noun ,verb ,verbpp<br>
    * <u>Elenen t�rler</u> : endsing , endsd, verb , verbpp ,(noun with mayBeVerb=true)   ( bi kisim)
    * <u>Eklenen t�rler</u> : noun (with adjective bazen)
    * @return void
    */
    private void clarifyVerbsAndNounsWRTPositionOfDefiners() {

        int i;

        for ( i = GlobalVars.OBJECT.size()-1 ; i>=0 ; i-- ) {

            //bir sonrasi of olmali veya bir �ncesi xxs# the a/an my-your.. olmali
            if ( ( i < GlobalVars.OBJECT.size()-2 && GlobalVars.OBJECT.get(i+1) instanceof Conjunction && GlobalVars.OBJECT.getConjunction(i+1).isConjunctionOF() ) || ( i>=1 && ( ( i>=2 && GlobalVars.OBJECT.get(i-1) instanceof Conjunction && !GlobalVars.OBJECT.getConjunction(i-1).isConjunctionOF() ) || GlobalVars.OBJECT.get(i-1) instanceof Article || ( GlobalVars.OBJECT.get(i-1) instanceof Pronoun && GlobalVars.OBJECT.getPronoun(i-1).isAdjectivePronoun() ) ) ) ) {

                // eger fiilse ve sonu S ile bitiyors ( =>cogul isim )
                if ( GlobalVars.OBJECT.get(i) instanceof Verb && GlobalVars.OBJECT.getVerb(i).isPlural() )

                    GlobalVars.OBJECT.set(i,new Noun(GlobalVars.OBJECT.getVerb(i).getInfinitive(),10));

                // eger isimVeyaFiil se .. isim
                else if ( GlobalVars.OBJECT.get(i) instanceof Noun && GlobalVars.OBJECT.getNoun(i).mayBeVerb() )
                    GlobalVars.OBJECT.getNoun(i).cantBeVerb();

                // normal fiilse veya endsIng o zama da mastar

                else if (  GlobalVars.OBJECT.get(i) instanceof EndsING || GlobalVars.OBJECT.get(i) instanceof Verb  )
                    GlobalVars.OBJECT.set(i,new Noun(GlobalVars.OBJECT.getVerbalOBJECT(i).getInfinitive(),0));

                // past pasriticple veya endsD ise adjective ..
                else if (  GlobalVars.OBJECT.get(i) instanceof VerbPP || GlobalVars.OBJECT.get(i) instanceof EndsD )
                    GlobalVars.OBJECT.set(i,GlobalVars.OBJECT.getVerbalOBJECT(i).toNoun());

            }



        }
    }






    /**
    * FindSomeConjugatedVerbs()
    * Fiile olma olasiligi olan t�rlerin who/where gibi query pronounlardan sonra
    * gelme durumlarina g�re onlari conjugated verb sinifina sokuyor.
    * <br>
    * <u>Kullanilan t�rler</u> : Query Pronouns , endsD , verb , noun , conjugated verb<br>
    * <u>Elenen t�rler</u> : Bir kisim endsd verb noun.mayBeVerb<br>
    * <u>Eklenen t�rler</u> : conjugated verb
    * @return void
    */
    private void findSomeConjugatedVerbs() {

        for ( int i = GlobalVars.OBJECT.size()-2 ; i>=0 ; i-- ) {

            if ( ( GlobalVars.OBJECT.get(i) instanceof Pronoun && ( GlobalVars.OBJECT.getPronoun(i).isQueryPronoun() || GlobalVars.OBJECT.getPronoun(i).isSubjectPronoun() ) ) || ( GlobalVars.OBJECT.get(i) instanceof Noun && GlobalVars.OBJECT.getNoun(i).isHumanName() ) ) {

                if ( GlobalVars.OBJECT.get(i+1) instanceof EndsD )
                    GlobalVars.OBJECT.set(i+1,GlobalVars.OBJECT.getEndsD(i+1).toConjugatedVerb());
                else if ( GlobalVars.OBJECT.get(i+1) instanceof Verb )
                    GlobalVars.OBJECT.set(i+1,GlobalVars.OBJECT.getVerb(i+1).toConjugatedVerb());

                else if ( GlobalVars.OBJECT.get(i+1) instanceof Noun && GlobalVars.OBJECT.getNoun(i+1).mayBeVerb() )

                    GlobalVars.OBJECT.set( i+1, GlobalVars.OBJECT.getNoun(i+1).toConjugatedVerb() );

            }

        }
    }









    /**
    * connectVerbsSeparatedByCommas
    * virg�lle birbirinden ayri noun ve commlari birlestiriyor
    * <br>
    * <u>Kullanilan Turler</u> : comma verb<br>
    * <u>Elenen Turler</u> : bi kisim verb comma<br>
    * <u>Eklenen Turler</u> : verb
    * @return void
    */
    private void connectVerbsSeparatedByCommas() {

        for ( int i = GlobalVars.OBJECT.size()-2 ; i >= 1 ; i-- ) {

            if ( GlobalVars.OBJECT.get(i) instanceof Comma && ( GlobalVars.OBJECT.get(i-1) instanceof ConjugatedVerb || GlobalVars.OBJECT.get(i+1) instanceof ConjugatedVerb ) ) {

                if ( GlobalVars.OBJECT.get(i-1) instanceof ConjugatedVerb && GlobalVars.OBJECT.get(i+1) instanceof ConjugatedVerb ) {
                    if ( GlobalVars.OBJECT.getComma(i).isComma() )
                        GlobalVars.OBJECT.getConjugatedVerb(i+1).setStack( GlobalVars.OBJECT.getConjugatedVerb(i-1).getStack()+", "+GlobalVars.OBJECT.getConjugatedVerb(i+1).getStack() ) ;
                    else
                        GlobalVars.OBJECT.getConjugatedVerb(i+1).setStack( GlobalVars.OBJECT.getConjugatedVerb(i-1).getStack()+' '+GlobalVars.OBJECT.getComma(i).getStack()+' '+GlobalVars.OBJECT.getConjugatedVerb(i+1).getStack() ) ;
                    GlobalVars.OBJECT.remove(i);
                    GlobalVars.OBJECT.remove(i-1);
                    i--;

                }
                else if ( GlobalVars.OBJECT.get(i-1) instanceof Verb || GlobalVars.OBJECT.get(i-1) instanceof EndsD ) {

                    if ( GlobalVars.OBJECT.getComma(i).isComma() )
                        GlobalVars.OBJECT.getConjugatedVerb(i+1).setStack( GlobalVars.OBJECT.getOBJECT(i-1).getStack()+", "+GlobalVars.OBJECT.getConjugatedVerb(i+1).getStack() ) ;
                    else
                        GlobalVars.OBJECT.getConjugatedVerb(i+1).setStack( GlobalVars.OBJECT.getOBJECT(i-1).getStack()+' '+GlobalVars.OBJECT.getComma(i).getStack()+' '+GlobalVars.OBJECT.getConjugatedVerb(i+1).getStack() ) ;
                    GlobalVars.OBJECT.remove(i);
                    GlobalVars.OBJECT.remove(i-1);
                    i--;
                }
                else if ( GlobalVars.OBJECT.get(i-1) instanceof Noun && GlobalVars.OBJECT.getNoun(i-1).mayBeVerb() ) {

                    if ( GlobalVars.OBJECT.getComma(i).isComma() )
                        GlobalVars.OBJECT.getConjugatedVerb(i+1).setStack( GlobalVars.OBJECT.getNoun(i-1).getVerb()+", "+GlobalVars.OBJECT.getConjugatedVerb(i+1).getStack() ) ;
                    else
                        GlobalVars.OBJECT.getConjugatedVerb(i+1).setStack( GlobalVars.OBJECT.getNoun(i-1).getVerb()+' '+GlobalVars.OBJECT.getComma(i).getStack()+' '+GlobalVars.OBJECT.getConjugatedVerb(i+1).getStack() ) ;
                    GlobalVars.OBJECT.remove(i);
                    GlobalVars.OBJECT.remove(i-1);
                    i--;
                }
                else if ( GlobalVars.OBJECT.get(i+1) instanceof Verb || GlobalVars.OBJECT.get(i+1) instanceof EndsD ) {

                    if ( GlobalVars.OBJECT.getComma(i).isComma() )
                        GlobalVars.OBJECT.getConjugatedVerb(i-1).setStack( GlobalVars.OBJECT.getConjugatedVerb(i-1).getStack()+", "+GlobalVars.OBJECT.getOBJECT(i+1).getStack() ) ;
                    else
                        GlobalVars.OBJECT.getConjugatedVerb(i-1).setStack( GlobalVars.OBJECT.getConjugatedVerb(i-1).getStack()+' '+GlobalVars.OBJECT.getComma(i).getStack()+' '+GlobalVars.OBJECT.getOBJECT(i+1).getStack() ) ;
                    GlobalVars.OBJECT.remove(i+1);
                    GlobalVars.OBJECT.remove(i);
                    i--;
                }
                else if ( GlobalVars.OBJECT.get(i+1) instanceof Noun && GlobalVars.OBJECT.getNoun(i+1).mayBeVerb() ) {

                    if ( GlobalVars.OBJECT.getComma(i).isComma() )
                        GlobalVars.OBJECT.getConjugatedVerb(i-1).setStack( GlobalVars.OBJECT.getConjugatedVerb(i-1).getStack()+", "+GlobalVars.OBJECT.getNoun(i+1).getVerb() ) ;
                    else
                        GlobalVars.OBJECT.getConjugatedVerb(i-1).setStack( GlobalVars.OBJECT.getConjugatedVerb(i-1).getStack()+' '+GlobalVars.OBJECT.getComma(i).getStack()+' '+GlobalVars.OBJECT.getNoun(i+1).getVerb() ) ;
                    GlobalVars.OBJECT.remove(i);
                    GlobalVars.OBJECT.remove(i-1);
                    i--;
                }
            }

        }

    }








    /**
     * That undefined pronoun unun durumunu a���a kavu�turuyor. �nceden
     * so ve too dan sonra gelen that bir anlama kavu�mu�tu. �imdide
     * baz� fiillerden sonra glen that ignore edilip .. s�fat oln that belirleniyor.
     * @return void
     */
    private void clarifyTheSituationOfThePronounThat() {

        int verbalThat=0;


        for(int i=0;i<GlobalVars.OBJECT.size();i++) {
            if( i<GlobalVars.OBJECT.size()-1 && GlobalVars.OBJECT.get(i) instanceof ConjugatedVerb && GlobalVars.OBJECT.getConjugatedVerb(i).withThat() ) {
                if( GlobalVars.OBJECT.get(i+1) instanceof Pronoun && GlobalVars.OBJECT.getPronoun(i+1).getPronoun().equals("that") )
                    i++;
                // arada objectivePronoun var m� diye bak�yor
                else if ( i<GlobalVars.OBJECT.size()-2 && GlobalVars.OBJECT.get(i+2) instanceof Pronoun && GlobalVars.OBJECT.getPronoun(i+2).getPronoun().equals("that") && GlobalVars.OBJECT.getConjugatedVerb(i).getObjectivePronoun() != -1 && ( ( GlobalVars.OBJECT.get(i+1) instanceof Pronoun && GlobalVars.OBJECT.getPronoun(i+1).isObjectPronoun() ) || ( GlobalVars.OBJECT.get(i+1) instanceof Noun && ( GlobalVars.OBJECT.getNoun(i+1).withArticle() || GlobalVars.OBJECT.getNoun(i+1).isHumanName() ) ) ) )
                    i+=2;
            }
            else if ( GlobalVars.OBJECT.get(i) instanceof Pronoun && GlobalVars.OBJECT.getPronoun(i).getPronoun().equals("that") )
                GlobalVars.OBJECT.set(i,new Noun("\u015Fu",2)); //su
        }



    }









    /* daha sonradan �a�r�labilir durumda */



    /**
    * mergeAdjectivesAndAdverbs()
    * Sifatlari sifatlarla, zarflari da zarflarla birlestirici fonksiyon
    * <br>
    * <u>Kullanilan t�rler</u> : noun , pronoun , comma
    * <u>Elenen t�rler</u> : Comma , Pronoun."very"  (bir kismi)	
    * @return void
    */

    private void mergeAdjectivesAndAdverbs() throws IndexOutOfBoundsException,ClassCastException {

        for ( int i = GlobalVars.OBJECT.size()-1 ; i >= 1 ; i-- ) {

            if ( GlobalVars.OBJECT.get(i) instanceof Noun && ( GlobalVars.OBJECT.getNoun(i).isAdjective() || GlobalVars.OBJECT.getNoun(i).isAdverb() ) ) {

                if ( GlobalVars.OBJECT.get(i-1) instanceof Noun && ( GlobalVars.OBJECT.getNoun(i-1).isAdjective() || GlobalVars.OBJECT.getNoun(i-1).isAdverb() ) ) {

                    GlobalVars.OBJECT.getNoun(i-1).append( GlobalVars.OBJECT.getNoun(i).getNoun() );
                    GlobalVars.OBJECT.getNoun(i-1).setType( GlobalVars.OBJECT.getNoun(i).getType() );
                    GlobalVars.OBJECT.remove(i);
                }

                else if ( GlobalVars.OBJECT.get(i-1) instanceof Comma && ( GlobalVars.OBJECT.get(i-2) instanceof Noun && ( GlobalVars.OBJECT.getNoun(i-2).isAdjective() || GlobalVars.OBJECT.getNoun(i-2).isAdverb() ) ) ) {

                    GlobalVars.OBJECT.getNoun(i-2).append(GlobalVars.OBJECT.getComma(i-1).getStack() + " " + GlobalVars.OBJECT.getNoun(i).getNoun()  );
                    GlobalVars.OBJECT.getNoun(i-2).setType( GlobalVars.OBJECT.getNoun(i).getType() );
                    GlobalVars.OBJECT.remove(i);
                    GlobalVars.OBJECT.remove(i-1);
                    i--;
                }

                // very(�ok) - most(en) -least (en az) -more(daha)  - less(daha az)  ( so too az �nceki kademede hallediliyor)
                else if ( GlobalVars.OBJECT.get(i-1) instanceof Pronoun ) {
                    if(GlobalVars.OBJECT.getPronoun(i-1).getStack().equals("very")) {
                        GlobalVars.OBJECT.set(i-1,new Noun("\u00E7ok " + GlobalVars.OBJECT.getNoun(i).getNoun(), GlobalVars.OBJECT.getNoun(i).getType() ) );
                        GlobalVars.OBJECT.remove(i);

                    }
                    else if (GlobalVars.OBJECT.getPronoun(i-1).getStack().equals("most")) {
                        GlobalVars.OBJECT.set(i-1,new Noun("en " + GlobalVars.OBJECT.getNoun(i).getNoun(), GlobalVars.OBJECT.getNoun(i).getType() ) );
                        GlobalVars.OBJECT.remove(i);

                    }
                    else if (GlobalVars.OBJECT.getPronoun(i-1).getStack().equals("least")) {
                        GlobalVars.OBJECT.set(i-1,new Noun("en az " + GlobalVars.OBJECT.getNoun(i).getNoun(), GlobalVars.OBJECT.getNoun(i).getType() ) );
                        GlobalVars.OBJECT.remove(i);

                    }
                    else if (GlobalVars.OBJECT.getPronoun(i-1).getStack().equals("more")) {
                        GlobalVars.OBJECT.set(i-1,new Noun("daha " + GlobalVars.OBJECT.getNoun(i).getNoun(), GlobalVars.OBJECT.getNoun(i).getType() ) );
                        GlobalVars.OBJECT.remove(i);

                    }
                    else if (GlobalVars.OBJECT.getPronoun(i-1).getStack().equals("less")) {
                        GlobalVars.OBJECT.set(i-1,new Noun("daha az " + GlobalVars.OBJECT.getNoun(i).getNoun(), GlobalVars.OBJECT.getNoun(i).getType() ) );
                        GlobalVars.OBJECT.remove(i);

                    }
                    i--;
                }
            }

        }
    }








    /* Daha sonradan �a�r�labilir durumda */


    /**
    * dealWithAdverbsAndNONearVerbs()
    * Fiilin bitisiindeki adverbleri ( onundeyse veya arkasindaysa ( baska bir kelimenin niteleyicisi olmamasi
    * sartiyla )) fiille birlestiriyor.
    * <br>
    * <u>Kullanilan t�rler</u> : conjugatedVerb pronoun noun article
    * <u>Elenen t�rler</u> : bir kisim pronoun ve noun.adverb
    * @return void
    */



    private void dealWithAdverbsAndNONearVerbs() {

        for ( int i = GlobalVars.OBJECT.size()-1 ; i>=0 ; i-- ) {

            if ( GlobalVars.OBJECT.get(i) instanceof ConjugatedVerb ) {

                if( i<GlobalVars.OBJECT.size()-1 && GlobalVars.OBJECT.get(i+1) instanceof Pronoun && GlobalVars.OBJECT.getPronoun(i+1).getPronoun().equals("no") ) {

                    GlobalVars.OBJECT.getConjugatedVerb(i).makeNegative();
                    GlobalVars.OBJECT.remove(i+1);
                }

                // arkas�ndan zarf geliyorsa ( o zarftan sonra da isim gelmiyorsa veya elimizdeki zarf son kelimesisyse c�mlenin ) veya reflexive pronoun geliyorsa onlar zarf olur.
                else if ( i<GlobalVars.OBJECT.size()-1 && ( ( GlobalVars.OBJECT.get(i+1) instanceof Pronoun && GlobalVars.OBJECT.getPronoun(i+1).isReflexivePronoun() ) || ( GlobalVars.OBJECT.get(i+1) instanceof Noun && GlobalVars.OBJECT.getNoun(i+1).isAdverb() && ( i==GlobalVars.OBJECT.size()-2 || GlobalVars.OBJECT.getConjugatedVerb(i).isIntransitive() || ( i<GlobalVars.OBJECT.size()-2 && ( !(GlobalVars.OBJECT.get(i+2) instanceof Noun) ) ) ) ) ) ) { // iki sonras�nda c�mle bitmeli veya isim olmamal�

                    GlobalVars.OBJECT.getConjugatedVerb(i).appendAdverb(GlobalVars.OBJECT.getOBJECT(i+1).getStack());
                    GlobalVars.OBJECT.remove(i+1);

                }

                if ( i>=1 && ( ( GlobalVars.OBJECT.get(i-1) instanceof Noun && GlobalVars.OBJECT.getNoun(i-1).isAdverb() ) || ( GlobalVars.OBJECT.get(i-1) instanceof Pronoun && GlobalVars.OBJECT.getPronoun(i-1).isReflexivePronoun() ) ) ) {

                    GlobalVars.OBJECT.getConjugatedVerb(i).appendAdverb(GlobalVars.OBJECT.getOBJECT(i-1).getStack());
                    GlobalVars.OBJECT.remove(i-1);
                    i--;
                }

            }
            
            // VerbalOBJECTlerle ilgileniyor. VerbalOBJECTler hem ConjugatedVerb olabilir hem de Noun
            // her ne olurlarsa olsunlar, arkalar�na bu adverblere ihtiya� var.
            else if ( i>=1 && GlobalVars.OBJECT.get(i-1) instanceof Noun && GlobalVars.OBJECT.getNoun(i-1).isAdverb()  && GlobalVars.OBJECT.get(i) instanceof VerbalOBJECT ) {
                GlobalVars.OBJECT.getVerbalOBJECT(i).appendAdverb(GlobalVars.OBJECT.getNoun(i-1).getNoun());
                GlobalVars.OBJECT.remove(i-1);
                i--;
            }
                
        }
    }






    /* Daha sonradan �a�r�labilir durumda */


    /**
    * attachAdjectivesAndNouns()
    * Eldeki sifatlari isimlerle birlestiriyor.<br>
    * Bunu yaparken isim �nlerine isaret ( xxMayIndefiniteTakeArticle# )
    * koyuyoruz ki indefinite article la birlesimde problem yasanmasin.
    * <br>
    * <u>Kullanilan t�rler</u> : noun<br>
    * <u>Elenen t�rler</u> : bir kisim sifat olabilecek isim<br>
    * <u>Eklenen t�rler</u> : tam isim
    * @return void
    */
    private void attachAdjectivesAndNouns() {

        boolean signPut=false; // xxMayTakeIndefiniteArticle# isareti icin

        for ( int i = GlobalVars.OBJECT.size()-1 ; i>=0 ; i-- ) {


            if ( i>=1 && GlobalVars.OBJECT.get(i) instanceof Noun && GlobalVars.OBJECT.getNoun(i).isNoun() && !GlobalVars.OBJECT.getNoun(i).withArticle() && GlobalVars.OBJECT.get(i-1) instanceof Noun && GlobalVars.OBJECT.getNoun(i-1).isAdjective() ) {

                if (signPut)
                    GlobalVars.OBJECT.set(i-1,new Noun(GlobalVars.OBJECT.getNoun(i-1).getNoun()+' '+GlobalVars.OBJECT.getNoun(i).getNoun(),GlobalVars.OBJECT.getNoun(i).getType() ) );
                else

                    GlobalVars.OBJECT.set(i-1,new Noun(GlobalVars.OBJECT.getNoun(i-1).getNoun()+" xxmay_take_indefinite_article# "+GlobalVars.OBJECT.getNoun(i).getNoun(),GlobalVars.OBJECT.getNoun(i).getType() ) );

                GlobalVars.OBJECT.remove(i);
                signPut=true;


            }
            // somebody very special
            else if ( i<GlobalVars.OBJECT.size()-2 && GlobalVars.OBJECT.get(i) instanceof Pronoun &&  GlobalVars.OBJECT.getPronoun(i).isNounPronoun() && GlobalVars.OBJECT.get(i+1) instanceof Noun && GlobalVars.OBJECT.getNoun(i+1).isAdjective() ) {
                if( GlobalVars.OBJECT.getPronoun(i).getPronoun().equals("someone") || GlobalVars.OBJECT.getPronoun(i).getPronoun().equals("somebody") )
                    GlobalVars.OBJECT.set(i,new Noun(GlobalVars.OBJECT.getNoun(i+1).getStack()+" biri",false,false));
                else if ( GlobalVars.OBJECT.getPronoun(i).getPronoun().equals("something") )
                    GlobalVars.OBJECT.set(i,new Noun(GlobalVars.OBJECT.getNoun(i+1).getStack()+" bir\u015Fey",false,false));

                signPut=false;
            }

            else
                signPut=false;

        }

    }






    /* sonradan �a�r�lamaz ��nk� zaten t�m olanaklar kapan�yor bununla.


    //bundan sonrada oftan sonra gele fiillerle lgilenecez ..
    //tirnak isareti ve parantezlerin ici yeni bi cumle sonra da parantezler kendilerinden once gelen ismin bi parcasi, tirnak ta yepyeni bi kelime

    /**
    * putArticles()
    * Articlelari yerlestiriyor.
    * <br>
    * <u>Kullanilan t�rler</u> : noun article<br>
    * <u>Elenen t�rler</u> : bir kisim noun ve article<br>
    * <u>Eklenen t�rler</u> : noun ve article
    */

    private void putArticles() {
        String stack;
        boolean isPlural,isConjunctionNoun;

        for( int i=GlobalVars.OBJECT.size()-1 ; i >= 1  ; i-- ) {

            if ( GlobalVars.OBJECT.get(i-1) instanceof Article ) {

                if ( GlobalVars.OBJECT.get(i) instanceof Noun ) {
                    stack = GlobalVars.OBJECT.getNoun(i).getNoun();
                    isPlural = GlobalVars.OBJECT.getNoun(i).isPlural();
                    isConjunctionNoun = GlobalVars.OBJECT.getNoun(i).isConjunctionNoun();
                }
                else {
                    stack = GlobalVars.OBJECT.getOBJECT(i).getStack();
                    isPlural = false;
                    isConjunctionNoun = false;
                }


                if ( GlobalVars.OBJECT.getArticle(i-1).isDefinite() )
                    stack = stack.replaceAll("xxmay_take_indefinite_article# ","");


                else {

                    if ( stack.indexOf("xxmay_take_indefinite_article#") != -1 )
                        stack = stack.replaceFirst("xxmay_take_indefinite_article# ","bir ");
                    else
                        stack = "bir "+stack;

                }

                GlobalVars.OBJECT.set(i-1, new Noun( stack,isPlural,isConjunctionNoun) );
                GlobalVars.OBJECT.remove(i);
                i--;
            }

            else if ( GlobalVars.OBJECT.getOBJECT(i).getStack().indexOf("xxmay_take_indefinite_article#") != -1 )
                GlobalVars.OBJECT.getOBJECT(i).setStack(GlobalVars.OBJECT.getOBJECT(i).getStack().replaceAll("xxmay_take_indefinite_article# ",""));
        }
        /* Sifirinci uye icin .. */
        if ( GlobalVars.OBJECT.getOBJECT(0).getStack().indexOf("xxmay_take_indefinite_article#") != -1 )
            GlobalVars.OBJECT.getOBJECT(0).setStack(GlobalVars.OBJECT.getOBJECT(0).getStack().replaceAll("xxmay_take_indefinite_article# ",""));
    }






    /**
    * putAdjectivePronouns()<br>
    * "his her this those" gibi definer zamirleri yerlestiriyor.<br>
    * <br>
    * <u>Kullanilan Turler</u> : prononun noun article<br>
    * <u>Elenen Turler</u> : bir kisim noun, <b>tum definer pronounlar</b><br>
    * <u>Eklenen Turler</u> : article
    * @return void
    */
    private void putAdjectivePronouns() {

        String whichPronoun;

        for( int i=GlobalVars.OBJECT.size()-1 ; i >= 1  ; i-- ) {

            if (  GlobalVars.OBJECT.get(i-1) instanceof Pronoun && GlobalVars.OBJECT.getPronoun(i-1).isAdjectivePronoun() ) { // direkt Noun dedik ��nk� daha �nceki "clarifyVerbsAndNouns()" yordaminda bu fiiller Noun a �evrildi.

                if( GlobalVars.OBJECT.get(i) instanceof Noun ) {


                    if ( GlobalVars.OBJECT.getPronoun(i-1).isPossessivePronoun() ) {

                        whichPronoun = GlobalVars.OBJECT.getPronoun(i-1).getPronoun();

                        if ( whichPronoun.equals("my") ) {

                            GlobalVars.OBJECT.getNoun(i).makePossessiveMy();
                            //GlobalVars.OBJECT.set(i-1,new Noun("benim "+ GlobalVars.OBJECT.getNoun(i).getNoun() , GlobalVars.OBJECT.getNoun(i).isPlural() , true ) );
                            GlobalVars.OBJECT.set(i-1,new Noun( GlobalVars.OBJECT.getNoun(i).getNoun() , GlobalVars.OBJECT.getNoun(i).isPlural() , true ) );
                        }
                        else if ( whichPronoun.equals("your") || whichPronoun.equals("ur") ) {
                            GlobalVars.OBJECT.getNoun(i).makePossessiveYour();
                            //GlobalVars.OBJECT.set(i-1,new Noun("sizin "+ GlobalVars.OBJECT.getNoun(i).getNoun() , GlobalVars.OBJECT.getNoun(i).isPlural() , true ) );
                            GlobalVars.OBJECT.set(i-1,new Noun( GlobalVars.OBJECT.getNoun(i).getNoun() , GlobalVars.OBJECT.getNoun(i).isPlural() , true ) );
                        }

                        // ! PROBLEM
                        // the father who saw his son
                        // o�lunu g�ren baba yerine .. onun o�lunu g�ren baba oluyor.
                        else if ( whichPronoun.equals("his") || whichPronoun.equals("her") || whichPronoun.equals("its") ) {
                            GlobalVars.OBJECT.getNoun(i).makePossessiveHis();
                            GlobalVars.OBJECT.set(i-1,new Noun( GlobalVars.OBJECT.getNoun(i).getNoun() , GlobalVars.OBJECT.getNoun(i).isPlural() , true ) );
                        }
                        else if ( whichPronoun.equals("our") ) {
                            GlobalVars.OBJECT.getNoun(i).makePossessiveOur();
                            //GlobalVars.OBJECT.set(i-1,new Noun("bizim "+ GlobalVars.OBJECT.getNoun(i).getNoun() , GlobalVars.OBJECT.getNoun(i).isPlural() , true ) );
                            GlobalVars.OBJECT.set(i-1,new Noun( GlobalVars.OBJECT.getNoun(i).getNoun() , GlobalVars.OBJECT.getNoun(i).isPlural() , true ) );
                        }
                        else if ( whichPronoun.equals("their") ) {
                            GlobalVars.OBJECT.getNoun(i).makePossessiveTheir();
                            GlobalVars.OBJECT.set(i-1,new Noun( GlobalVars.OBJECT.getNoun(i).getNoun() , GlobalVars.OBJECT.getNoun(i).isPlural() , true ) );
                        }
                        else if ( whichPronoun.equals("my own") ) {
                            GlobalVars.OBJECT.getNoun(i).makePossessiveMy();
                            GlobalVars.OBJECT.set(i-1,new Noun("kendi "+ GlobalVars.OBJECT.getNoun(i).getNoun() , GlobalVars.OBJECT.getNoun(i).isPlural() , true ) );
                        }
                        else if ( whichPronoun.equals("your own") || whichPronoun.equals("ur own") ) {
                            GlobalVars.OBJECT.getNoun(i).makePossessiveYour();
                            GlobalVars.OBJECT.set(i-1,new Noun("kendi "+ GlobalVars.OBJECT.getNoun(i).getNoun() , GlobalVars.OBJECT.getNoun(i).isPlural() , true ) );
                        }
                        else if ( whichPronoun.equals("his own") || whichPronoun.equals("her own") || whichPronoun.equals("its own") ) {
                            GlobalVars.OBJECT.getNoun(i).makePossessiveHis();
                            GlobalVars.OBJECT.set(i-1,new Noun("kendi "+ GlobalVars.OBJECT.getNoun(i).getNoun() , GlobalVars.OBJECT.getNoun(i).isPlural() , true ) );
                        }
                        else if ( whichPronoun.equals("our own") ) {
                            GlobalVars.OBJECT.getNoun(i).makePossessiveOur();
                            GlobalVars.OBJECT.set(i-1,new Noun("kendi"+ GlobalVars.OBJECT.getNoun(i).getNoun() , GlobalVars.OBJECT.getNoun(i).isPlural() , true ) );
                        }
                        else if ( whichPronoun.equals("their own") ) {
                            GlobalVars.OBJECT.getNoun(i).makePossessiveTheir();
                            GlobalVars.OBJECT.set(i-1,new Noun("kendi"+ GlobalVars.OBJECT.getNoun(i).getNoun() , GlobalVars.OBJECT.getNoun(i).isPlural() , true ) );
                        }
                        else // silinebilir
                            GlobalVars.OBJECT.set(i-1,new Noun( GlobalVars.OBJECT.getPronoun(i-1).getStack() , false,false ) );
                        GlobalVars.OBJECT.remove(i);
                        i--;
                    }


                    else {

                        GlobalVars.OBJECT.set(i-1,new Noun( GlobalVars.OBJECT.getPronoun(i-1).getStack()+" "+GlobalVars.OBJECT.getNoun(i).getNoun() , GlobalVars.OBJECT.getNoun(i).isPlural() , false) );
                        GlobalVars.OBJECT.remove(i);
                    }



                }



                // yani �nlerinde isim yoksa ...

                else if ( GlobalVars.OBJECT.getPronoun(i-1).isDemonstrativePronoun() ) {
                    // �zel metodu var
                    GlobalVars.OBJECT.set(i-1,new Noun(GlobalVars.OBJECT.getPronoun(i-1).getDemonstrativePronounInNounForm(),false,true)); // tamlama alm�� gibi
                    i--;

                }
                else  {
                    //aynen ge�iyor
                    GlobalVars.OBJECT.set(i-1,new Noun(GlobalVars.OBJECT.getPronoun(i-1).getStack(),false,true)); // tamlama alm�� gibi
                    i--;

                }

            }


        }

        if(  GlobalVars.OBJECT.get(0) instanceof Pronoun && GlobalVars.OBJECT.getPronoun(0).isAdjectivePronoun() ) // EN BASTAK� ELEMENA AZ ONCEK� DONGUDE BAKAMADIK BURDA BAKIORUZ.
            GlobalVars.OBJECT.set(0,new Noun(GlobalVars.OBJECT.getPronoun(0).getStack(),false,true));

    }


    /**
    * connectNounsSeparatedByCommas
    * virg�lle birbirinden ayri noun ve commlari birlestiriyor
    * <br>
    * <u>Kullanilan Turler</u> : comma article noun<br>
    * <u>Elenen Turler</u> : bi kisim noun comma<br>
    * <u>Eklenen Turler</u> : article
    * @return void
    */
    private void connectNounsSeparatedByCommas() {

        for ( int i = GlobalVars.OBJECT.size()-2 ; i >= 1 ; i-- ) {

            if ( GlobalVars.OBJECT.get(i) instanceof Comma && GlobalVars.OBJECT.get(i-1) instanceof Noun && GlobalVars.OBJECT.get(i+1) instanceof Noun ) {

                if ( GlobalVars.OBJECT.getComma(i).isComma() )
                    GlobalVars.OBJECT.set( i-1 , new Noun( GlobalVars.OBJECT.getNoun(i-1).getStack()+", "+GlobalVars.OBJECT.getNoun(i+1).getStack() ,true , GlobalVars.OBJECT.getNoun(i+1).isConjunctionNoun() ) ) ;
                else
                    GlobalVars.OBJECT.set( i-1 , new Noun( GlobalVars.OBJECT.getNoun(i-1).getStack()+' '+GlobalVars.OBJECT.getComma(i).getStack()+' '+GlobalVars.OBJECT.getNoun(i+1).getStack() ,true , GlobalVars.OBJECT.getNoun(i+1).isConjunctionNoun() ) );
                GlobalVars.OBJECT.remove(i+1);
                GlobalVars.OBJECT.remove(i);
                i--;
            }

        }

    }


    /**
    * Conjunctionlari ( of - xxs# ) ayarliyor.
    * Iki ayaktan olusuyor. Ilk ayakta xxs#ler yerlesiyor,
    * ikinci ayakta "of" lar yerlesiyor.<br>
    * xxs# ler yerlesirken "xxMayComeAnEndsINGDefinerHere#" isaretcisi
    * yerlestiriliyor. Bu isaretci, elimizdeki nouna daha sonradan eklenebilecek
    * -ing lerin problem cikartmamasinda yararli.
    * <br>
    * <u>Kullanilan Turler</u> : conjunction noun pronoun article <br>
    * <u>Elenen Turler</u> : conjunction noun pronoun article ( bir kisim ) <br>
    * <u>Eklenen Turler</u> : article
    * @param void
    */
    private void organizeConjunctions() {

        for ( int i = GlobalVars.OBJECT.size()-2 ; i >= 1 ; i-- ) {
            if ( GlobalVars.OBJECT.get(i) instanceof Conjunction && !GlobalVars.OBJECT.getConjunction(i).isConjunctionOF() && GlobalVars.OBJECT.get(i+1) instanceof Noun && GlobalVars.OBJECT.get(i-1) instanceof Noun ) {
                GlobalVars.OBJECT.getNoun(i-1).makeDefiner();
                GlobalVars.OBJECT.getNoun(i+1).makeDefined();
                GlobalVars.OBJECT.set( i-1 , new Noun( GlobalVars.OBJECT.getNoun(i-1).getNoun() + " xxMayComeAnEndsINGDefinerHere# " + GlobalVars.OBJECT.getNoun(i+1).getNoun() , GlobalVars.OBJECT.getNoun(i+1).isPlural() , true ) );
                GlobalVars.OBJECT.remove(i+1);
                GlobalVars.OBJECT.remove(i);
                i--;
            }
        }




        for ( int i = GlobalVars.OBJECT.size()-2 ; i >= 1 ; i-- ) {

            if ( GlobalVars.OBJECT.get(i) instanceof Conjunction && GlobalVars.OBJECT.getConjunction(i).isConjunctionOF() && ( GlobalVars.OBJECT.get(i+1) instanceof Noun || GlobalVars.OBJECT.get(i+1) instanceof Pronoun ) && GlobalVars.OBJECT.get(i-1) instanceof Noun  ) {

                if ( GlobalVars.OBJECT.get(i+1) instanceof Pronoun ) {

                    if ( GlobalVars.OBJECT.getPronoun(i+1).getPronoun().equals("me") || GlobalVars.OBJECT.getPronoun(i+1).getPronoun().equals("i") || GlobalVars.OBJECT.getPronoun(i+1).getPronoun().equals("mine") ) {

                        GlobalVars.OBJECT.getNoun(i-1).makePossessiveMy();
                        GlobalVars.OBJECT.set(i-1,new Noun(  GlobalVars.OBJECT.getNoun(i-1).getNoun() , GlobalVars.OBJECT.getNoun(i-1).isPlural() , true ) );
                    }
                    else if ( GlobalVars.OBJECT.getPronoun(i+1).getPronoun().equals("thine") ) {
                        GlobalVars.OBJECT.getNoun(i-1).makePossessiveYour();
                        GlobalVars.OBJECT.set(i-1,new Noun( "senin " + GlobalVars.OBJECT.getNoun(i-1).getNoun().substring(0,GlobalVars.OBJECT.getNoun(i-1).getNoun().length()-2) , GlobalVars.OBJECT.getNoun(i-1).isPlural() , true ) );
                    }
                    else if ( GlobalVars.OBJECT.getPronoun(i+1).getPronoun().equals("you") || GlobalVars.OBJECT.getPronoun(i+1).getPronoun().equals("u") || GlobalVars.OBJECT.getPronoun(i+1).getPronoun().equals("yours") || GlobalVars.OBJECT.getPronoun(i+1).getPronoun().equals("urs") ) {
                        GlobalVars.OBJECT.getNoun(i-1).makePossessiveYour();
                        GlobalVars.OBJECT.set(i-1,new Noun(  GlobalVars.OBJECT.getNoun(i-1).getNoun() , GlobalVars.OBJECT.getNoun(i-1).isPlural() , true ) );
                    }
                    else if ( GlobalVars.OBJECT.getPronoun(i+1).getPronoun().equals("him") || GlobalVars.OBJECT.getPronoun(i+1).getPronoun().equals("her") || GlobalVars.OBJECT.getPronoun(i+1).getPronoun().equals("hers") || GlobalVars.OBJECT.getPronoun(i+1).getPronoun().equals("his") ) {
                        GlobalVars.OBJECT.getNoun(i-1).makePossessiveHis();
                        GlobalVars.OBJECT.set(i-1,new Noun( "onun " + GlobalVars.OBJECT.getNoun(i-1).getNoun() , GlobalVars.OBJECT.getNoun(i-1).isPlural() , true ) );
                    }
                    else if ( GlobalVars.OBJECT.getPronoun(i+1).getPronoun().equals("ours") || GlobalVars.OBJECT.getPronoun(i+1).getPronoun().equals("us") ) {
                        GlobalVars.OBJECT.getNoun(i-1).makePossessiveOur();
                        GlobalVars.OBJECT.set(i-1,new Noun( GlobalVars.OBJECT.getNoun(i-1).getNoun() , GlobalVars.OBJECT.getNoun(i-1).isPlural() , true ) );
                    }
                    else if ( GlobalVars.OBJECT.getPronoun(i+1).getPronoun().equals("them") || GlobalVars.OBJECT.getPronoun(i+1).getPronoun().equals("theirs") ) {
                        GlobalVars.OBJECT.getNoun(i-1).makePossessiveHis();
                        GlobalVars.OBJECT.set(i-1,new Noun( "onlar\u0131n " + GlobalVars.OBJECT.getNoun(i-1).getNoun() , GlobalVars.OBJECT.getNoun(i-1).isPlural() , true ) );
                    }
                    GlobalVars.OBJECT.remove(i+1);
                    GlobalVars.OBJECT.remove(i);
                }

                else {
                    GlobalVars.OBJECT.getNoun(i+1).makeDefiner();
                    GlobalVars.OBJECT.getNoun(i-1).makeDefined();
                    GlobalVars.OBJECT.set( i-1 , new Noun( GlobalVars.OBJECT.getNoun(i+1).getNoun() + ' ' + GlobalVars.OBJECT.getNoun(i-1).getNoun() , GlobalVars.OBJECT.getNoun(i-1).isPlural() , true ) );

                    GlobalVars.OBJECT.remove(i+1);
                    GlobalVars.OBJECT.remove(i);
                }

            }


        }
    }


    
    







   /**
    * LeftVerb leri bulup, onlarla LEFTVERBS s�n�f� i�inde
    * ilgileniyor.
    * <br> Veritaban�ndan bu ayak i�n gelen "^^" tak�s� gene bu ayak i�nde temizleniyor.
    * <br> Bu aya��n bir di�er �zelli�i de �u ki ; Bu ayakta
    * OBJECTListimize yeni OBJECTler eklenebiliyor.
    */
   private void dealWithLeftVerbs() {
      
       for( int i=GlobalVars.OBJECT.size()-1 ; i>=0 ; i-- ) {
           if(GlobalVars.OBJECT.get(i) instanceof LeftVerb)
               new LEFTVERBS(i);
           
           // veritaban�ndan gelen ^^ ile ba�layan kelimeler temizlenmi� oldu.
           else if( GlobalVars.OBJECT.getOBJECT(i).getStack().startsWith("^^") )
               GlobalVars.OBJECT.getOBJECT(i).setStack(GlobalVars.OBJECT.getOBJECT(i).getStack().substring(2));
       }
   
   }
    














    private void dealWithConjugatedVerbProperties() {

        boolean gotObjectPronoun,ok;

        // burada bir mantiksizlik olabilir.
        NounOBJECT objectPronoun =  new Pronoun("that",6);

        for ( int i = GlobalVars.OBJECT.size()-1 ; i >= 0 ; i-- ) {

            if ( GlobalVars.OBJECT.get(i) instanceof ConjugatedVerb ) {

                gotObjectPronoun = false ;
                ok=false;

                if( GlobalVars.OBJECT.getConjugatedVerb(i).getObjectivePronoun() != -1 && ( ( GlobalVars.OBJECT.get(i+1) instanceof Pronoun && GlobalVars.OBJECT.getPronoun(i+1).isObjectPronoun() ) || ( GlobalVars.OBJECT.get(i+1) instanceof Noun && ( GlobalVars.OBJECT.getNoun(i+1).withArticle() || GlobalVars.OBJECT.getNoun(i+1).isHumanName() ) ) ) ) {
                    gotObjectPronoun = true ;
                    objectPronoun = GlobalVars.OBJECT.getNounOBJECT(i+1);
                    GlobalVars.OBJECT.remove(i+1);
                }

                if ( GlobalVars.OBJECT.getConjugatedVerb(i).withThat() && ( GlobalVars.OBJECT.get(i+1) instanceof Pronoun && GlobalVars.OBJECT.getPronoun(i+1).getPronoun().equals("that") ) ) {

                    ok=true;

                    if( gotObjectPronoun ) {
                        objectPronoun.makeDative();
                        GlobalVars.OBJECT.getConjugatedVerb(i).appendAdverb(objectPronoun.getStack());
                    }
                    GlobalVars.OBJECT.getConjugatedVerb(i).setVerbObject(7);
                    GlobalVars.OBJECT.set(i+1,new SentenceConnector("ki"));
                    continue;
                }


                if ( !ok && GlobalVars.OBJECT.getConjugatedVerb(i).withVerbING() && GlobalVars.OBJECT.get(i+1) instanceof EndsING ) {

                    ok=true;

                    if( gotObjectPronoun ) {  // eger -ing verb den once bir kelime varsa

                        // o tamlama eki alir.
                        objectPronoun.makeDefiner();
                        // Fiilin nesnesi -ing ile bitenin nesnesi olur artik.
                        GlobalVars.OBJECT.getConjugatedVerb(i).setProperties( GlobalVars.OBJECT.getEndsING(i+1).getType() );
                        // Bu -ing ile  biten, bir Noun a dondurulur ki ek alabilsin.
                        GlobalVars.OBJECT.set(i+1,new Noun(GlobalVars.OBJECT.getEndsING(i+1).getInfinitive(),false,false));
                        // once tamlanan eki
                        GlobalVars.OBJECT.getNoun(i+1).makeDefined();
                        //sonra -i hal eki ; bu daha sonraki asamalarda fiilinn kendi nesnesine de donusturulebilir.
                        GlobalVars.OBJECT.getNoun(i+1).makeObjective();
                        // bu zarf obegi fiile yapistiriliyor.
                        GlobalVars.OBJECT.getConjugatedVerb(i).appendAdverb( objectPronoun.getStack()+' '+GlobalVars.OBJECT.getNoun(i+1).getStack() );
                        // bir sonrki kelime siliniyor.
                        GlobalVars.OBJECT.remove(i+1);
                    }
                    else {
                        // yukaridaki surece benzer bir surec izleniyor.
                        GlobalVars.OBJECT.getConjugatedVerb(i).setProperties( GlobalVars.OBJECT.getEndsING(i+1).getType() );
                        GlobalVars.OBJECT.set(i+1,new Noun( GlobalVars.OBJECT.getEndsING(i+1).getInfinitive() , false , false ) );
                        GlobalVars.OBJECT.getNoun(i+1).makeDefined();
                        GlobalVars.OBJECT.getNoun(i+1).makeObjective();
                        GlobalVars.OBJECT.getConjugatedVerb(i).appendAdverb( GlobalVars.OBJECT.getNoun(i+1).getStack() );
                        GlobalVars.OBJECT.remove(i+1);
                    }


                }


                if ( !ok && GlobalVars.OBJECT.getConjugatedVerb(i).withVerbTo() && GlobalVars.OBJECT.get(i+1) instanceof ToVerb )

                    if( gotObjectPronoun ) {  // eger -ing verb den once bir kelime varsa

                        // o tamlama eki alir.
                        objectPronoun.makeDefiner();
                        // Fiilin nesnesi -ing ile bitenin nesnesi olur artik.
                        GlobalVars.OBJECT.getConjugatedVerb(i).setProperties( GlobalVars.OBJECT.getToVerb(i+1).getType() );
                        // Bu -ing ile  biten, bir Noun a dondurulur ki ek alabilsin.
                        GlobalVars.OBJECT.set(i+1,new Noun(GlobalVars.OBJECT.getToVerb(i+1).getInfinitive(),false,false));
                        // once tamlanan eki
                        GlobalVars.OBJECT.getNoun(i+1).makeDefined();
                        //sonra -i hal eki ; bu daha sonraki asamalarda fiilinn kendi nesnesine de donusturulebilir.
                        GlobalVars.OBJECT.getNoun(i+1).makeObjective();
                        // bu zarf obegi fiile yapistiriliyor.
                        GlobalVars.OBJECT.getConjugatedVerb(i).appendAdverb( objectPronoun.getStack()+' '+GlobalVars.OBJECT.getNoun(i+1).getStack() );
                        // bir sonrki kelime siliniyor.
                        GlobalVars.OBJECT.remove(i+1);
                    }
                    else {
                        // yukaridaki surece benzer bir surec izleniyor.
                        GlobalVars.OBJECT.getConjugatedVerb(i).setProperties( GlobalVars.OBJECT.getToVerb(i+1).getType() );
                        GlobalVars.OBJECT.set(i+1,new Noun( GlobalVars.OBJECT.getToVerb(i+1).getInfinitive() , false , false ) );
                        GlobalVars.OBJECT.getNoun(i+1).makeDefined();
                        GlobalVars.OBJECT.getNoun(i+1).makeObjective();
                        GlobalVars.OBJECT.getConjugatedVerb(i).appendAdverb( GlobalVars.OBJECT.getNoun(i+1).getStack() );
                        GlobalVars.OBJECT.remove(i+1);
                    }
            }
        }

    }



















    private void findOutGerunds() {
        for( int i=GlobalVars.OBJECT.size()-1 ; i>=0 ; i-- ) {

            if (GlobalVars.OBJECT.get(i) instanceof EndsING ) {
                if( i<GlobalVars.OBJECT.size()-1 && GlobalVars.OBJECT.get(i+1) instanceof Noun ) {
                    GlobalVars.OBJECT.getNoun(i+1).commentSuffixe(GlobalVars.OBJECT.getEndsING(i).getType());
                    GlobalVars.OBJECT.set(i,new Noun(GlobalVars.OBJECT.getNoun(i+1).getNoun()+' '+GlobalVars.OBJECT.getEndsING(i).getInfinitive(),false,false));
                    GlobalVars.OBJECT.remove(i+1);

                }

                else
                    GlobalVars.OBJECT.set(i,GlobalVars.OBJECT.getEndsING(i).toNoun());



            }
        }
    }

















































    /**
    * finishPrepositions() <br>
    * Preposition lari �nlerindeki kelimelerele birlestiren
    * basit bir yordam. Onlerindeki OBJECT Noun veya Pronoun
    * instance i ise ; gerekli cekim yapilip bu iki OBECT
    * birlestiriliyor. <br>
    * <b>Bu yordamdan sonra prepositionlarla ilgili herhangi bir islem
    * gerceklestirilemez.</b><br>
    * <u>Kullanilan turler</u> : hepsi<br>
    * <u>Elenen turler</u> : hepsinden bir kisim<br>
    * <u>Eklenen turler</u> : yok
    * @return void
    */ 
    private void finishPrepositionsAndAdjectivePatterns() {

        for ( int i = GlobalVars.OBJECT.size()-2 ; i >= 0 ; i-- ) {

            if( ( GlobalVars.OBJECT.get(i) instanceof Preposition && !GlobalVars.OBJECT.getPreposition(i).isLocked() ) || GlobalVars.OBJECT.get(i) instanceof AdjectivePattern ) {

                // burdan ��kanlar s�fat oluyor.
                if( GlobalVars.OBJECT.get(i+1) instanceof EndsD || GlobalVars.OBJECT.get(i+1) instanceof VerbPP ) {
                    GlobalVars.OBJECT.set(i+1,GlobalVars.OBJECT.getVerbalOBJECT(i+1).toNoun());

                    this.attachAdjectivesAndNouns();

                }

                // burdan ��kanlar isim oluyor.
                // �ncesinde de sadee prep veya adjPrep var dolay�s�yla s�fat yok
                // ve birle�me durumu da yok.
                else if ( GlobalVars.OBJECT.get(i+1) instanceof Verb || ( GlobalVars.OBJECT.get(i+1) instanceof Noun && GlobalVars.OBJECT.getNoun(i+1).mayBeVerb() ) || GlobalVars.OBJECT.get(i+1) instanceof ToVerb )  {
                    GlobalVars.OBJECT.set(i+1,GlobalVars.OBJECT.getVerbalOBJECT(i+1).toNoun());


                }

                if( GlobalVars.OBJECT.get(i+1) instanceof Noun || GlobalVars.OBJECT.get(i+1) instanceof Pronoun ) {
                    GlobalVars.OBJECT.getNounOBJECT(i+1).commentSuffixe( GlobalVars.OBJECT.getOBJECT(i).getType() );
                    GlobalVars.OBJECT.getOBJECT(i).setStack( GlobalVars.OBJECT.getOBJECT(i+1).getStack() + ' ' + GlobalVars.OBJECT.getOBJECT(i).getStack() );
                    GlobalVars.OBJECT.remove(i+1);
                    if(GlobalVars.OBJECT.get(i) instanceof Preposition) // ba�ka dabir�ey olamalar ger�i��nk�ya lockedlar ya da noun ..
                        GlobalVars.OBJECT.getPreposition(i).lock();
                }
                else
                    GlobalVars.OBJECT.set(i,new Noun(GlobalVars.OBJECT.getOBJECT(i).getStack(),false,false));

            }


        }
    }









    /**
    * Bu yordam  be# yaln?z ise ona isim ekleyip onu isim-fiil
    * hale sokuyor.
    * @return void
    */
    private void dealWith_BE() {

        for( int i = GlobalVars.OBJECT.size()-2 ; i>=0 ; i-- ) {

            if( GlobalVars.OBJECT.get(i) instanceof ConjugatedVerb && GlobalVars.OBJECT.getConjugatedVerb(i).getStack().equals("be#") ) {

                if ( GlobalVars.OBJECT.get(i+1) instanceof Noun || GlobalVars.OBJECT.get(i+1) instanceof Pronoun)
                    GlobalVars.OBJECT.getConjugatedVerb(i).setStack(GlobalVars.OBJECT.getOBJECT(i+1).getStack()+' '+GlobalVars.OBJECT.getConjugatedVerb(i).getStack());
                else if ( GlobalVars.OBJECT.get(i+1) instanceof Verb )
                    GlobalVars.OBJECT.getConjugatedVerb(i).setStack(GlobalVars.OBJECT.getVerb(i+1).getInfinitive()+' '+GlobalVars.OBJECT.getConjugatedVerb(i).getStack());
                else
                    continue;

                GlobalVars.OBJECT.remove(i+1);
            }
        }




    }


    /**
     * Hala tam olarak kar��l��� bulunamamm�� nesnelerle ilgileniyor.
     * <br> .EndsD --> ConjugatedVerb() (time "11")
     * <br> .VerbPP --> Noun
     * <br> .Verb --> ConjugatedVerb ("21")
     * <br> .Noun --> Noun
     * <br> .ToVerb --> Noun
     * @return void
     */
    private void finishFuzzies() {
        System.out.println("celdu");
        for ( int i=GlobalVars.OBJECT.size()-1 ; i>=0 ; i-- ) {

            if ( GlobalVars.OBJECT.get(i) instanceof Noun && GlobalVars.OBJECT.getNoun(i).mayBeVerb() ) {
                GlobalVars.OBJECT.getNoun(i).cantBeVerb();
                this.attachAdjectivesAndNouns();
            }
            else if (GlobalVars.OBJECT.get(i) instanceof ToVerb ) {
                if(GlobalVars.OBJECT.get(i+1)instanceof Noun ) {
                    GlobalVars.OBJECT.getNoun(i+1).commentSuffixe(GlobalVars.OBJECT.getToVerb(i).getType());
                    GlobalVars.OBJECT.set(i,GlobalVars.OBJECT.getNoun(i+1).getNoun()+" "+GlobalVars.OBJECT.getToVerb(i).toNoun());
                    GlobalVars.OBJECT.remove(i+1);
                }
                else
                    GlobalVars.OBJECT.set(i,GlobalVars.OBJECT.getToVerb(i).toNoun());
            }
            else if( GlobalVars.OBJECT.get(i) instanceof VerbPP ) {
                GlobalVars.OBJECT.set(i,GlobalVars.OBJECT.getVerbPP(i).toNoun());
                this.attachAdjectivesAndNouns();
            }
            else if ( GlobalVars.OBJECT.get(i) instanceof Verb )
                GlobalVars.OBJECT.set(i,GlobalVars.OBJECT.getVerb(i).toConjugatedVerb());
            else if ( GlobalVars.OBJECT.get(i) instanceof EndsD )
                GlobalVars.OBJECT.set(i,GlobalVars.OBJECT.getEndsD(i).toConjugatedVerb());


        }


        System.out.println("celdu");
    }







    /**
    * theWhoseTheDo() <br>
    * !! Whose dan sonra the geliyo gibi yaptik, fakat aslinda<br>
    * oraya the gelemez, normal Noun gelir.
    */
    private void theWhoseTheDo() {
        try {
            int comma=0;
            boolean objectOK=false;
            String intermediateObject = new String();
            boolean isConjunctionNoun,isPlural;
            String mainNoun = new String();


            for ( int i = GlobalVars.OBJECT.size()-1 ; i>=3 ; i-- ) {

                if ( GlobalVars.OBJECT.get(i) instanceof ConjugatedVerb && GlobalVars.OBJECT.get(i-1) instanceof Noun && !GlobalVars.OBJECT.getNoun(i-1).withArticle() && ( GlobalVars.OBJECT.get(i-2) instanceof Pronoun && GlobalVars.OBJECT.getPronoun(i-2).getPronoun().equals("whose") ) ) {

                    comma=0;
                    objectOK=false;
                    intermediateObject="";


                    if ( GlobalVars.OBJECT.get(i-3) instanceof Comma && GlobalVars.OBJECT.getComma(i-3).isComma() )
                        comma = 1;


                    if ( this._lookForIntermediateObject(i) ) {
                        System.out.println("thewhosethedo");
                        objectOK = true ;
                        GlobalVars.OBJECT.getNoun(i+1).commentSuffixe( GlobalVars.OBJECT.getConjugatedVerb(i).getVerbObject() );
                        intermediateObject = GlobalVars.OBJECT.getOBJECT(i+1).getStack()+' ';
                    }


                    if(  i-3-comma >= 0 && GlobalVars.OBJECT.get(i-3-comma) instanceof Noun ) {
                        mainNoun =  GlobalVars.OBJECT.getNoun(i-3-comma).getStack() ;
                        isPlural =  GlobalVars.OBJECT.getNoun(i-3-comma).isPlural();
                        isConjunctionNoun =  GlobalVars.OBJECT.getNoun(i-3-comma).isConjunctionNoun() ;
                    }
                    else {  //bu d�zeltilebilir.
                        mainNoun=  GlobalVars.OBJECT.getOBJECT(i-3-comma).getStack();
                        isPlural = false ;
                        isConjunctionNoun = false ;
                    }

                    GlobalVars.OBJECT.getConjugatedVerb(i).setMode(1);
                    GlobalVars.OBJECT.getConjugatedVerb(i).conjugate();
                    GlobalVars.OBJECT.getNoun(i-1).makeObjective();
                    GlobalVars.OBJECT.set(i-3-comma, new Noun( GlobalVars.OBJECT.getNoun(i-1).getStack() + ' ' + intermediateObject + GlobalVars.OBJECT.getConjugatedVerb(i).getStack() + ' ' + mainNoun , isPlural , isConjunctionNoun ) );

                    if(objectOK)
                        GlobalVars.OBJECT.remove(i+1);
                    GlobalVars.OBJECT.remove(i);
                    GlobalVars.OBJECT.remove(i-1);
                    GlobalVars.OBJECT.remove(i-2);
                    if(comma==1)
                        GlobalVars.OBJECT.remove(i-3);
                }
            }
        } catch (IndexOutOfBoundsException e) {}
    }











    /**
    * Soru yuklemlerini bulmak icin tasarlandi.
    * Modelimiz su ; LeftVerb + ( Noun || Object Pronoun ) + !please! + Verb
    */





    private void dealWith_Whose() {

        for( int i = GlobalVars.OBJECT.size()-2 ; i>=0 ; i-- ) {

            // Noun i�in bir de isNoun()kontrol� yap�lm�yor. ��nk� aksi m�mk�n de�il.
            if( GlobalVars.OBJECT.get(i) instanceof Pronoun && GlobalVars.OBJECT.getPronoun(i).getPronoun().equals("whose") &&  GlobalVars.OBJECT.get(i+1) instanceof Noun ) {

                GlobalVars.OBJECT.getNoun(i+1).makeDefined();
                GlobalVars.OBJECT.set(i,new Noun("kimin "+GlobalVars.OBJECT.getNoun(i+1).getStack(),false,false)); // bilhassa plural m� tekil yap�ld� ki .. oznelerde tekil ��ks�n diye.
                GlobalVars.OBJECT.remove(i+1);
            }
        }


    }














    private void theWhoDo() {

        for ( int i = GlobalVars.OBJECT.size()-1 ; i >= 2 ; i-- ) {

            if (
                GlobalVars.OBJECT.get(i) instanceof ConjugatedVerb &&
                ( GlobalVars.OBJECT.get(i-2) instanceof Noun && ( GlobalVars.OBJECT.getNoun(i-2).withArticle() || GlobalVars.OBJECT.getNoun(i-2).isHumanName() ) ) &&
                ( GlobalVars.OBJECT.get(i-1) instanceof Pronoun && ( GlobalVars.OBJECT.getPronoun(i-1).getPronoun().equals("that") || GlobalVars.OBJECT.getPronoun(i-1).getPronoun().equals("who") || GlobalVars.OBJECT.getPronoun(i-1).getPronoun().equals("which") ) )
            )
            {
                if ( _lookForIntermediateObject(i) ) {

                    GlobalVars.OBJECT.getConjugatedVerb(i).setMode(1);
                    GlobalVars.OBJECT.getConjugatedVerb(i).conjugate();
                    GlobalVars.OBJECT.getNounOBJECT(i+1).commentSuffixe( GlobalVars.OBJECT.getConjugatedVerb(i).getVerbObject() );
                    GlobalVars.OBJECT.set(i-2, new Noun( GlobalVars.OBJECT.getOBJECT(i+1).getStack() + " " + GlobalVars.OBJECT.getConjugatedVerb(i).getStack() + " " + GlobalVars.OBJECT.getNoun(i-2).getStack() , GlobalVars.OBJECT.getNoun(i-2).isPlural() , GlobalVars.OBJECT.getNoun(i-2).isConjunctionNoun() ) );
                    GlobalVars.OBJECT.remove(i+1);
                    GlobalVars.OBJECT.remove(i);
                    GlobalVars.OBJECT.remove(i-1);
                    i-=2;
                }
                else {
                    GlobalVars.OBJECT.getConjugatedVerb(i).setMode(1);
                    GlobalVars.OBJECT.getConjugatedVerb(i).conjugate();
                    GlobalVars.OBJECT.set(i-2, new Noun( GlobalVars.OBJECT.getConjugatedVerb(i).getStack() + " " + GlobalVars.OBJECT.getNoun(i-2).getStack() , GlobalVars.OBJECT.getNoun(i-2).isPlural() , GlobalVars.OBJECT.getNoun(i-2).isConjunctionNoun() ) );
                    GlobalVars.OBJECT.remove(i);
                    GlobalVars.OBJECT.remove(i-1);
                    i-=2;
                }
            }
        }
    }


    /**
    * !! Do dan sonra gelse gelse adverb gelir.
    * Onla bir ilgilenmek lazim.
    */
    private void theThatTheDo() {

        int existThat,existComma;

        for ( int i = GlobalVars.OBJECT.size()-1 ; i >= 2 ; i-- ) {

            if ( GlobalVars.OBJECT.get(i) instanceof ConjugatedVerb && ( ( GlobalVars.OBJECT.get(i-1) instanceof Noun && GlobalVars.OBJECT.getNoun(i-1).withArticle() ) || ( GlobalVars.OBJECT.get(i-1) instanceof Pronoun && GlobalVars.OBJECT.getPronoun(i-1).isSubjectPronoun() ) ) ) {

                existThat = 0 ;
                existComma = 0 ;


                if ( GlobalVars.OBJECT.get(i-2) instanceof Pronoun && ( GlobalVars.OBJECT.getPronoun(i-2).getPronoun().equals("that") || GlobalVars.OBJECT.getPronoun(i-2).getPronoun().equals("which") || GlobalVars.OBJECT.getPronoun(i-2).getPronoun().equals("who") || GlobalVars.OBJECT.getPronoun(i-2).getPronoun().equals("whom") ) )
                    existThat = 1;

                if ( GlobalVars.OBJECT.get(i-2-existThat) instanceof Comma && GlobalVars.OBJECT.getComma(i-2-existThat).isComma() )
                    existComma = 1 ;

                if ( GlobalVars.OBJECT.get(i-2-existThat-existComma) instanceof Noun && GlobalVars.OBJECT.getNoun(i-2-existThat-existComma).withArticle() ) {

                    if( GlobalVars.OBJECT.get(i-1) instanceof Pronoun && GlobalVars.OBJECT.getPronoun(i-1).isSubjectPronoun() ) {
                        if( GlobalVars.OBJECT.getPronoun(i-1).getPronoun().equals("i") )
                            GlobalVars.OBJECT.getConjugatedVerb(i).setMode(201);
                        else if ( GlobalVars.OBJECT.getPronoun(i-1).getPronoun().equals("you") || GlobalVars.OBJECT.getPronoun(i-1).getPronoun().equals("u") )
                            GlobalVars.OBJECT.getConjugatedVerb(i).setMode(202);
                        else if ( GlobalVars.OBJECT.getPronoun(i-1).getPronoun().equals("we") )
                            GlobalVars.OBJECT.getConjugatedVerb(i).setMode(211);
                        else
                            GlobalVars.OBJECT.getConjugatedVerb(i).setMode(200);

                    }
                    else
                        GlobalVars.OBJECT.getConjugatedVerb(i).setMode(200);
                    GlobalVars.OBJECT.getConjugatedVerb(i).conjugate();

                    if( GlobalVars.OBJECT.get(i-1) instanceof Noun)
                        GlobalVars.OBJECT.getNoun(i-1).makeDefiner();
                    else
                        GlobalVars.OBJECT.getPronoun(i-1).makeDefiner();

                    GlobalVars.OBJECT.set( i-2-existThat-existComma , new Noun( GlobalVars.OBJECT.getOBJECT(i-1).getStack()+' '+GlobalVars.OBJECT.getConjugatedVerb(i).getStack()+' '+GlobalVars.OBJECT.getNoun(i-2-existThat-existComma).getStack() , GlobalVars.OBJECT.getNoun(i-2-existThat-existComma).isPlural() , GlobalVars.OBJECT.getNoun(i-2-existThat-existComma).isConjunctionNoun() ) ) ;


                    GlobalVars.OBJECT.remove(i);
                    GlobalVars.OBJECT.remove(i-1);

                    if(existThat==1 && existComma==1) {
                        GlobalVars.OBJECT.remove(i-2);
                        GlobalVars.OBJECT.remove(i-3);
                    }
                    else if (existThat==1 || existComma==1)
                        GlobalVars.OBJECT.remove(i-2);

                    i -= (2+existThat+existComma) ;
                }
                else
                    i -= (1+existThat+existComma) ;
            }
        }
    }











    public void finalize() {
        System.out.println(GlobalVars.OBJECT.getOBJECT(0).getStack());
        int start = 0 ;
        StringBuffer phrase = new StringBuffer();
        String verbalPrep = new String();
        int verbMode = -1 ; // -1 elde verbal rpreposition yok demek.
        PHRASE p;

        for ( int i = 0 ; i < GlobalVars.OBJECT.size() ; i++ ) {

            if ( GlobalVars.OBJECT.get(i) instanceof SentenceConnector ) {

                if(verbMode==-1)
                    p = new PHRASE(start,i);
                else
                    p = new PHRASE(start,i,verbMode,verbalPrep);
                phrase.append( p.getPhrase() + ' ' + GlobalVars.OBJECT.getSentenceConnector(i).getStack() + ' ' );
                start = i+1;
                verbMode=-1;


            }
            else if ( GlobalVars.OBJECT.get(i) instanceof VerbalPreposition ) {

                if(verbMode==-1)
                    p = new PHRASE(start,i);
                else
                    p = new PHRASE(start,i,verbMode,verbalPrep);

                phrase.append( p.getPhrase() + ", " );
                start = i+1;
                verbMode= GlobalVars.OBJECT.getVerbalPreposition(i).getType();
                verbalPrep = GlobalVars.OBJECT.getVerbalPreposition(i).getStack();

            }
            else if ( i == GlobalVars.OBJECT.size()-1 ) {

                if(verbMode==-1)
                    p = new PHRASE(start,i+1);
                else
                    p = new PHRASE(start,i+1,verbMode,verbalPrep);

                phrase.append( p.getPhrase() );

            }
        }

        // son olarak TRANSLATED de��ikenine elimizdekiler aktar�l�yor ve CYCLE3 teki i�imiz de bitiyor.
        System.out.println("hh"+phrase.toString().trim());
        GlobalVars.TRANSLATED[phraseNo] = phrase.toString().trim();
    }









    /**
    * nesne bulmak icin
    * @param hangi yuklemden sonra
    * @return boolean ( true => nesne aliyor ; false => nesne almiyor )
     */
    private boolean _lookForIntermediateObject(int i) {


        if (GlobalVars.OBJECT.get(i) instanceof ConjugatedVerb) {

            if( GlobalVars.OBJECT.size() > i+1 && ( ( GlobalVars.OBJECT.get(i+1) instanceof Noun && GlobalVars.OBJECT.getNoun(i+1).isAdverb() ) || ( GlobalVars.OBJECT.get(i+1) instanceof Preposition && GlobalVars.OBJECT.getPreposition(i+1).isLocked() ) ) ) {

                GlobalVars.OBJECT.getConjugatedVerb(i).appendAdverb(GlobalVars.OBJECT.getOBJECT(i+1).getStack());
                GlobalVars.OBJECT.remove(i+1);


            }

            if ( GlobalVars.OBJECT.size() == i+1 || !GlobalVars.OBJECT.getConjugatedVerb(i).mayTakeVerbObject() ) // son kelimeyse veya fiil nesne alam?yorsa ..
                return false;
            else if( GlobalVars.OBJECT.get(i+1) instanceof Noun || ( GlobalVars.OBJECT.get(i+1) instanceof Pronoun && GlobalVars.OBJECT.getPronoun(i+1).isObjectPronoun() ) ) {
                if( GlobalVars.OBJECT.size() > i+2 && ( ( GlobalVars.OBJECT.get(i+2) instanceof Preposition && GlobalVars.OBJECT.getPreposition(i+2).isLocked() ) || ( GlobalVars.OBJECT.get(i+2) instanceof Noun && GlobalVars.OBJECT.getNoun(i+2).isAdverb() ) ) ) {

                    GlobalVars.OBJECT.getConjugatedVerb(i).appendAdverb(GlobalVars.OBJECT.getOBJECT(i+2).getStack());
                    GlobalVars.OBJECT.remove(i+2);
                }
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }





}
