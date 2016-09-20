package dilbaz.en2tr;


class DATABASE extends dilbaz.en2tr.util.DATABASEpool {


    /**
    * Veritabaninda isimler ve fiiller arasinda bir arama yapiyor
    * @param s aramasi yapilacak kelime (ingilizce)
    * @param p cümle no
    * @param n p nolu cümlenin n nolu kelimesi
    * @return DatabaseVars
    */

    public static DatabaseVars queryVerbPPAndNounAndVerbDB(String s,int p,int n) {

        DatabaseVars toReturn = queryDB(s,p,n,"( type=1 OR type=2 OR type=4 OR ( type=3 AND prop>=100 ) ) AND");
        if(toReturn.getType()==3)
            return new DatabaseVars( 4,toReturn.getProperty()-100,toReturn.getStack(),toReturn.getJump() );
        else
            return toReturn;

    }

    /**
    * Zarflari adverbleri veritabanindan çekiyor. Bunu yaparken fiil olma imkani olanlari dahil etmemeye ozen gosteriyor.
    * Property 100 den kucuk olmali. Aksi takdirde, fiili tercih ederiz.
    * @param s kelime (ingilizce)
    * @param p cumle no
    * @param n kelime no
    * @return DatabaseVars
    */


    public static DatabaseVars queryAdverbDB(String s,int p,int n) {

        return queryDB(s,p,n,"type=1 AND property%10=3 AND prop<100 AND");

    }

    /**
    * Tüm veritabaninda arama yapiyor.<br>
    * Sadece Unchangeable (17) bu aramaya dahil edilmiyor ;
    * Cunku o zatn CYCLE2 nin basinda ayri bir yordamla
    * kontrol ediliyor. 
    * @param s aramasi yapilacak kelime (ingilizce)
    * @param p cümle no
    * @param n p nolu cümlenin n nolu kelimesi
    * @return DatabaseVars
    */



    public static DatabaseVars queryWholeDB(String s,int p,int n) {

        return queryDB(s,p,n,"type !=17 AND");

    }




    /**
    * Unchangeable lar (17) arasinda bir arama yapiliyor. 
    * @param s aramasi yapilacak kelime (ingilizce)
    * @param p cümle no
    * @param n p nolu cümlenin n nolu kelimesi
    * @return DatabaseVars
    */



    public static DatabaseVars queryUnchangeableDB(String s,int p,int n) {

        return queryDB(s,p,n,"type =17 AND ");

    }


    /**
    * Sadece isimler arasinda arama yapiyor.<br>
    * Eger fiil olma olasiligi olan bir isim bulursa ( property >=100 )
    * ordan isim kismini bulup, cikartip, geri donduruyor.
    * @param s ingilizce kelime
    * @param p cumle nosu
    * @param n kelime nosu
    * @return DatabaseVars
    */
    public static DatabaseVars queryNounDB(String s,int p,int n) {

        DatabaseVars toReturn = queryDB(s,p,n,"type=1 AND");

        if ( toReturn.exists() && toReturn.getProperty() >= 100 )
            return new DatabaseVars ( 1 , toReturn.getProperty()-100 , toReturn.getStack().substring( 0 , toReturn.getStack().indexOf("++") ) , toReturn.getJump() ) ;
        else
            return toReturn;
    }


    /**
    * Sadece fiiller arasinda arama yapiyor.<br>
    * Bu arada fiil olabilecek isimler arasinda da arastirma yapiyor (type=1 && property>=100).
    * Eger isimVeyaFiil bulursa onu duzenleyip geri donduruyor.
    * @param s ingilizce kelime
    * @param p cumle nosu
    * @param n kelime nosu
    * @return DatabaseVars
    */    
    public static DatabaseVars queryVerbDB(String s,int p,int n) {

        DatabaseVars toReturn = queryDB(s,p,n,"(type=2 OR (type=1 AND prop>=100)) AND");

        if( toReturn.exists() &&  toReturn.getType()==1  ) // property zaten 100 un uzerinde olmak zorunda.
            return new DatabaseVars(2,(toReturn.getProperty()/100-1),toReturn.getStack().substring(toReturn.getStack().indexOf("++")+2),toReturn.getJump());
        else
            return toReturn;
    }


    /**
    * -ing ile biten fiillere bakiliyor.<br>
    * Bunun icin basta bir son ek tarama duzeltmsi yapiliyor. Oyle bakiliyor fiil veritabanina.
    * @param s ingilizce kelime
    * @param p cumle nosu
    * @param n kelime nosu
    * @return DatabaseVars
    */

    public static DatabaseVars queryVerbINGDB(String s,int p ,int n) {

        /* öncelikle istisna bir durum ele aliniyor */
        s = new String(s.toLowerCase());

        if ( s.equals("willing") )
            return new DatabaseVars (2,111,"iste",1);

        if( s.endsWith("ing") ) {

            DatabaseVars dbv = queryVerbDB(s.substring(0,s.length()-3),p,n);

            if(dbv.exists())
                return dbv;





            else if( s.endsWith("ying") ) {

                return queryVerbDB(s.substring(0,(s.length()-4))+"ie",p,n);

            }

            else if ( s.endsWith("icking") || ( s.length()>6 && s.charAt(s.length()-4)==s.charAt(s.length()-5) ) ) {

                return queryVerbDB(s.substring(0,s.length()-4)+"ie",p,n);


            }
            else return new DatabaseVars();
        }

        else
            return new DatabaseVars();
    }


    /**
    * Past participle lar icin arama yapiyor.<br>
    * Fakat once -ed ile bitiyor mu diye bakiyor. Eger oyleyse normal verbler icinde arama yapacak.
    * Aksi takdirde irregular verblere bakiyor.
    * <br />Irregular verblere bakarken de önemli bir nokta ; Bu yordam sadece CYCLE2verb de fiilerle ilgilenen
    * yordamlar tarafindan cagrildigindan buradan cikacak verinin fiil formatinda olmasi lazim.
    * Dolayisiyla irrgular verbpp bilgisinden isim stack i elimine edilip sadece fiil bilgisi verilecek.
    * @param s ingilizce kelime
    * @param p cumle nosu
    * @param n kelime nosu
    * @return DatabaseVars
    */
    public static DatabaseVars queryVerbPPDB(String s,int p,int n) {

        s = new String(s.toLowerCase());

        if( s.endsWith("id") ) {

            DatabaseVars dbv = queryVerbDB( s.substring(0,s.length()-2)+'y' ,p, n );

            if( dbv.exists() ) {

                return dbv;
            }

            else {
                DatabaseVars dbv2 = queryDB(s,p,n,"( type=4 OR ( type=3 AND prop>=100 ) ) AND");
                return new DatabaseVars( 4 , dbv2.getProperty() , dbv2.getStack().substring( 0 , dbv2.getStack().indexOf('+') ) , dbv2.getJump() );

            }

        }

        else if ( s.endsWith("ed") ) {

            return queryVerbDB( s.substring(0,(s.length()-2)) , p,n );


        }

        else if ( s.endsWith("ied") ) {

            return queryVerbDB( s.substring(0,(s.length()-3))+"y" ,p, n );



        }

        else if (s.endsWith("cked") || ( s.length()>5 && s.charAt(s.length()-3)==s.charAt(s.length()-4) ) ) {

            return queryVerbDB( s.substring(0,(s.length()-3)),p,n);

        }



        else {
            DatabaseVars dbv = queryDB(s,p,n,"( type=4 OR ( type=3 AND prop>=100 ) ) AND");
            if(dbv.exists())
                return new DatabaseVars( 4 , dbv.getProperty() , dbv.getStack().substring( 0 , dbv.getStack().indexOf('+') ) , dbv.getJump() );
            else
                return dbv;

        }

    }

}
