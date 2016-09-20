package dilbaz.en2tr;


class PHRASE {

    private int verbNo;
    private ConjugatedVerb [] verb;
    private OBJECTList  [] subject;
    private OBJECTList  [] object ;
    private String [] separator;

    private String verbalPreposition=new String();
    private int verbMode=0;


    public PHRASE( int start, int until, int mode, String vp ) {


        this(start,until);
        this.verbalPreposition = vp ;
        this.verbMode = mode ;

    }




    public PHRASE( int start, int until) {
        
        System.out.println("PHRASE");
        
        int i = 0 ;  // dongulerde kullanilmak uzere
        int p = 0 ; // cumle sayisi


        for ( i = start ; i < until ; i++ )
            if ( GlobalVars.OBJECT.get(i) instanceof ConjugatedVerb )
                p++;


        this.verbNo = p ;
        System.out.println(this.verbNo);

        if( p==0) {

            this.separator = new String[0];
            this.verb = new ConjugatedVerb [0];
            this.object = new OBJECTList[0];
            this.subject = new OBJECTList[1];
            this.subject[0] = new OBJECTList();

        }

        else {
            this.separator = new String[p];
            this.verb = new ConjugatedVerb[p];
            this.object = new OBJECTList[p];
            this.subject = new OBJECTList[p];

            for( i=0 ; i<p ; i++) {
                this.object[i] = new OBJECTList();
                this.subject[i] = new OBJECTList();
            }
        }


        p=0;

        for ( i = start ; i < until ; i++ ) {
            if ( GlobalVars.OBJECT.get(i) instanceof ConjugatedVerb ) {
                this.verb[p]=GlobalVars.OBJECT.getConjugatedVerb(i);
                p++;
            }
            else if ( p!= 0 )
                this.object[p-1].add( GlobalVars.OBJECT.get(i) );
            else
                this.subject[0].add( GlobalVars.OBJECT.get(i) );

        }



    }







    /**
    * _commentPerson() <br>
    * getPhrase() tarafindan kulaniliyor.<br>
    * Sahsi veriyor.
    * @param p getPhrase() de toplanan subject verileri.
    * @return sahis
    */

    private int _commentPerson(int p) {

        if ( p > 100 )
            return 11;
        else if ( p==100 )
            return 1;
        else if ( p > 9 )
            return 12;
        else if ( p == 9 )
            return 2;
        else if ( p > 1 )
            return 13;
        else
            return 3;
    }








    /**
     * Cümleyi TRANSLATED global degiskeninde kullanilabilir
     * sekle sokup geri donduruyor.
     * @return Turkce cumle
     */


    public String getPhrase() {

        int i;

        if( verbNo ==	0 )
            return getPhraseWithoutVerb();


        else if ( verbNo == 1 )
            return getPhraseWithOneVerb();

        else
            return getPhraseWithMultipleVerbs();



    }







    /**
    * getOutPhrase() yordami icin yuklemsiz cumlelerde
    * arastirma yapiyor.
    * Ve geriye gene turkce cumleyi donduruyor.
    * @return turkce cumle
    */


    private String getPhraseWithoutVerb() {
        System.out.println("0 caným 0");
        StringBuffer s = new StringBuffer() ;
        for( int i =0 ; i < subject[0].size();i++) {
            s.append( ' ' + subject[0].getOBJECT(i).getStack() );
        }
        try {
            return s.toString().substring(1);
        } catch (StringIndexOutOfBoundsException e) { return s.toString(); }
    }




    /**
     * getOutPhrase() yordami icin tek yuklemli cumlelerde
     * arastirma yapiyor.
     * Ve geriye gene turkce cumleyi donduruyor.
     * @return turkce cumle
     */        

    private String getPhraseWithOneVerb() {
System.out.println("1 caným 1");
        int i;
        StringBuffer objectBuffer = new StringBuffer();
        StringBuffer subjectBuffer = new StringBuffer();
        int person=0;


        for ( i = 0 ; i < object[0].size() ; i++ ) {
            // gerekli hal ekleri ekleniyor.
            if( ( object[0].get(i) instanceof Noun && !object[0].getNoun(i).isAdverb() ) || ( object[0].get(i) instanceof Pronoun && ( object[0].getPronoun(i).isObjectPronoun() || object[0].getPronoun(i).isReflexivePronoun() ) ) )
                object[0].getNounOBJECT(i).commentSuffixe(verb[0].getVerbObject());

            else if (object[0].get(i) instanceof Preposition ) {
                objectBuffer.insert(0,' ' + object[0].getPreposition(i).getStack() );
                continue;
            }
            objectBuffer.append(' ' + object[0].getOBJECT(i).getStack() );
        }



        for ( i = 0 ; i < subject[0].size() ; i++ ) {

            if ( subject[0].get(i) instanceof Pronoun && subject[0].getPronoun(i).isSubjectPronoun() ) {

                if ( subject[0].getPronoun(i).getPronoun().equals("i") ) {
                    subjectBuffer.append(' '+"ben");
                    person += 100 ;
                }

                else if ( subject[0].getPronoun(i).getPronoun().equals("he") || subject[0].getPronoun(i).getPronoun().equals("she") || subject[0].getPronoun(i).getPronoun().equals("it") ) {
                    subjectBuffer.append(' '+"o");
                    person += 1;
                }


                else if ( subject[0].getPronoun(i).getPronoun().equals("we") ) {
                    subjectBuffer.append(' '+"biz");
                    person += 1000 ;
                }
                else if ( subject[0].getPronoun(i).getPronoun().equals("you") || subject[0].getPronoun(i).getPronoun().equals("u") ) {
                    subjectBuffer.append(' '+"siz");
                    person += 10 ;
                }
                else if ( subject[0].getPronoun(i).getPronoun().equals("thou") ) {
                    subjectBuffer.append(' '+"sen");
                    person += 9 ;
                }
                else if ( subject[0].getPronoun(i).getPronoun().equals("they") ) {
                    subjectBuffer.append(' '+"onlar");
                    person += 2 ;
                }



                else if ( subject[0].getPronoun(i).getPronoun().equals("i one") ) {
                    subjectBuffer.append(' '+"ben tek ba\u015F\u0131ma");
                    person += 100 ;
                }

                else if ( subject[0].getPronoun(i).getPronoun().equals("he one") || subject[0].getPronoun(i).getPronoun().equals("she one") || subject[0].getPronoun(i).getPronoun().equals("it one") ) {
                    subjectBuffer.append(' '+"o tek ba\u015F\u0131na");
                    person += 1;
                }

                else if ( subject[0].getPronoun(i).getPronoun().equals("we one") ) {
                    subjectBuffer.append(' '+"biz tek ba\u015F\u0131m\u0131za");
                    person += 1000 ;
                }
                else if ( subject[0].getPronoun(i).getPronoun().equals("you one") || subject[0].getPronoun(i).getPronoun().equals("u one") ) {
                    subjectBuffer.append(' '+"siz tek ba\u015F\u0131n\u0131za");
                    person += 10 ;
                }
                else if ( subject[0].getPronoun(i).getPronoun().equals("thou one") ) {
                    subjectBuffer.append(' '+"sen tek ba\u015F\u0131na");
                    person += 9 ;
                }
                else if ( subject[0].getPronoun(i).getPronoun().equals("they one") ) {
                    subjectBuffer.append(' '+"onlar tek ba\u015Flar\u0131na");
                    person += 2 ;
                }


                else {
                    subjectBuffer.append(' '+"o");
                    person += 1;
                }
            }

            else if ( subject[0].get(i) instanceof Noun  ) {
                if (subject[0].getNoun(i).isPlural() )
                    person += 2 ;

                else

                    person += 1 ;
                subjectBuffer.append(' '+ subject[0].getNoun(i).getStack() );
            }
            else
                subjectBuffer.append(' '+ subject[0].getOBJECT(i).getStack() );

        }

        verb[0].setPerson(_commentPerson(person));
        verb[0].setMode(verbMode);
        verb[0].conjugate();
        try {
            return  subjectBuffer.toString().substring(1)+' '+objectBuffer.toString().substring(1)+' '+verb[0].getStack()+' '+verbalPreposition;
        } catch (StringIndexOutOfBoundsException e) {
            return  subjectBuffer.toString()+' '+objectBuffer.toString()+' '+verb[0].getStack()+' '+verbalPreposition;
        }
    }



    private String getPhraseWithMultipleVerbs() {
System.out.println("cok caným cok");
        
        StringBuffer phraseBuffer = new StringBuffer();
        StringBuffer objectBuffer = new StringBuffer();
        StringBuffer subjectBuffer = new StringBuffer();
        int person=0; // kisiyi bulmak uzere var ..

        int i,j,z,k; // iterator counts.

        /*
         * Öncelikle object[n] den subject[n+1] çikartilacak
         */

        //her fiil icin
        for ( i = 0 ; i < this.verbNo-1 ; i++ ) {

            //eðer intransitive ise
            if( i < this.verbNo-1 && this.verb[i].isIntransitive() ) {
                this.getObjectAndSubjectForIntransitives(this.object[i],i);
                continue;
            }

            // her object elemani arastiriliyor.
            for ( j = object[i].size()-1 ; j >= 0 ; j-- ) {

                // eðer bir Comma nesnesine rastlanirsa ..
                if ( object[i].get(j) instanceof Comma ) {

                    // Comma separator e ekleniyor. Comma objectten siliniyor.
                    separator[i] = " "+object[i].getComma(j).getStack();
                    

                    // ileridekiler subject, geridekiler object oluyor.
                    for ( z = j+1 ; z < object[i].size() ; z++) {
                        subject[i+1].add( object[i].get(z) );
                    }

                    
                    
                    for ( z=object[i].size()-1 ; z >= j ; z-- ) {
                        object[i].remove(z);
                    }

                    break; // içteki döngüyü sonlandýrýyor. Yani Comma aramayý sonlandýrýyor.


                }

                // eger Comma bulunamamissa
                else if ( j == 0 ) {

                    // herbir object nesnesi icin
                    for (z = object[i].size()-1 ; z >= 0 ; j-- ) {

                        // o nesnenin icinde bir Comma barindirip barindirmadigina bakiliyor, barindirriyorsa ;
                        if( object[i].get(z) instanceof Noun && object[i].getNoun(z).hasComma() ) {

                            //gerekli ayarlar yapiliyor
                            String [] divided = object[i].getNoun(z).divideByComma();
                            object[i].set(z,divided[0]);
                            subject[i+1].add(divided[1]);
                            separator[i]=divided[2];
                            for ( k = j+1 ; k < object[i].size() ; z++) {
                                subject[i+1].add( object[i].get(k) );
                                object[i].remove(k);
                            }
                            // ve gene baþka bir fiile geçiliyor.
                            break;
                        }

                        // hic bir nesne Comma barindýrmýyorsa
                        else if (j==0) {

                            // subject yok, object ayný, separator ,
                            separator[i]=",";
                            //baþka fiile geçiliyor.
                            break;

                        }
                    }


                } // ana else if ten çýkýyoruz
            } // her object nesnesi icin olan for dongusunden cýkýyoruz.

        } // her fiil için olan ana for dongusunden cýkýyoruz.
/*
        System.out.println("SEPARATOR IS " + this.separator[0] );
        System.out.println("\nSUBJECT 0 IS");
        for(int gg=0;gg<subject[0].size();gg++)
            System.out.println(gg+" "+subject[0].getOBJECT(gg).getStack());
        System.out.println("\nSUBJECT 1 IS");
        for(int gg=0;gg<subject[1].size();gg++)
            System.out.println(gg+" "+subject[1].getOBJECT(gg).getStack());
        System.out.println("\nOBJECT 0 IS");
        for(int gg=0;gg<object[0].size();gg++)
            System.out.println(gg+" "+object[0].getOBJECT(gg).getStack());
        System.out.println("\nOBJECT 1 IS");
        for(int gg=0;gg<object[1].size();gg++)
            System.out.println(gg+" "+object[1].getOBJECT(gg).getStack());
            */
        //her fiil icin
        for ( i = 0 ; i < verbNo ; i++ ) {

            if(i!=0) {
                subjectBuffer.delete(0,subjectBuffer.length());
                objectBuffer.delete(0,objectBuffer.length());
            }



            for ( j = 0 ; j < object[i].size() ; j++ ) {
                // gerekli hal ekleri ekleniyor.
                if( ( object[i].get(j) instanceof Noun && !object[i].getNoun(j).isAdverb() ) || ( object[i].get(j) instanceof Pronoun && ( object[i].getPronoun(j).isObjectPronoun() || object[i].getPronoun(j).isReflexivePronoun() ) ) )
                    object[i].getNounOBJECT(j).commentSuffixe(verb[i].getVerbObject());
                else if ( object[i].get(j) instanceof Preposition ) {
                    objectBuffer.insert(0,' ' + object[0].getPreposition(j).getStack() );
                    continue;
                }
try {
                objectBuffer.append(' ' + object[i].getOBJECT(j).getStack() );
} catch(ClassCastException ce) {System.out.println("ClassCastException has been caught !");}
            }



            if( subject[i].isEmpty()) { // özne yok
                if(i==0) {
                    verb[i].setPerson(_commentPerson(person));
                    verb[i].setMode(verbMode);
                    verb[i].conjugate();
                    phraseBuffer.append(objectBuffer.toString().substring(1)+' '+verb[i].getStack()+separator[i]);

                }
                else {
                    verb[i].setPerson(_commentPerson(person));
                    verb[i].conjugate();
                    phraseBuffer.append(' '+objectBuffer.toString().substring(1)+' '+verb[i].getStack()+separator[i]);
                }
                continue; //baþka fiile geç
            }

            person = 0;

            for(j=0;j<subject[i].size() ;j++) {

                if ( subject[i].get(j) instanceof Pronoun && subject[i].getPronoun(j).isSubjectPronoun() ) {

                    if ( subject[i].getPronoun(j).getPronoun().equals("i") ) {
                        subjectBuffer.append(' '+"ben");
                        person += 100 ;
                    }

                    else if ( subject[i].getPronoun(j).getPronoun().equals("he") || subject[i].getPronoun(j).getPronoun().equals("she") || subject[i].getPronoun(j).getPronoun().equals("it") ) {
                        subjectBuffer.append(' '+"o");
                        person += 1;
                    }


                    else if ( subject[i].getPronoun(j).getPronoun().equals("we") ) {
                        subjectBuffer.append(' '+"biz");
                        person += 1000 ;
                    }
                    else if ( subject[i].getPronoun(j).getPronoun().equals("you") || subject[i].getPronoun(j).getPronoun().equals("u") ) {
                        subjectBuffer.append(' '+"siz");
                        person += 10 ;
                    }
                    else if ( subject[i].getPronoun(j).getPronoun().equals("thou") ) {
                        subjectBuffer.append(' '+"sen");
                        person += 9 ;
                    }
                    else if ( subject[i].getPronoun(j).getPronoun().equals("they") ) {
                        subjectBuffer.append(' '+"onlar");
                        person += 2 ;
                    }



                    else if ( subject[i].getPronoun(j).getPronoun().equals("i one") ) {
                        subjectBuffer.append(' '+"ben tek ba\u015F\u0131ma");
                        person += 100 ;
                    }

                    else if ( subject[i].getPronoun(j).getPronoun().equals("he one") || subject[i].getPronoun(j).getPronoun().equals("she one") || subject[i].getPronoun(j).getPronoun().equals("it one") ) {
                        subjectBuffer.append(' '+"o tek ba\u015F\u0131na");
                        person += 1;
                    }

                    else if ( subject[i].getPronoun(j).getPronoun().equals("we one") ) {
                        subjectBuffer.append(' '+"biz tek ba\u015F\u0131m\u0131za");
                        person += 1000 ;
                    }
                    else if ( subject[i].getPronoun(j).getPronoun().equals("you one") || subject[i].getPronoun(j).getPronoun().equals("u one") ) {
                        subjectBuffer.append(' '+"siz tek ba\u015F\u0131n\u0131za");
                        person += 10 ;
                    }
                    else if ( subject[i].getPronoun(j).getPronoun().equals("thou one") ) {
                        subjectBuffer.append(' '+"sen tek ba\u015F\u0131na");
                        person += 9 ;
                    }
                    else if ( subject[i].getPronoun(j).getPronoun().equals("they one") ) {
                        subjectBuffer.append(' '+"onlar tek ba\u015Flar\u0131na");
                        person += 2 ;
                    }


                    else {
                        subjectBuffer.append(' '+"o");
                        person += 1;
                    }
                }

                else if ( subject[i].get(j) instanceof Noun  ) {
                    if (subject[i].getNoun(j).isPlural() )
                        person += 2 ;

                    else

                        person += 1 ;
                    subjectBuffer.append(' '+ subject[i].getNoun(j).getStack() );
                }
                else
                    subjectBuffer.append(' '+ subject[i].getOBJECT(j).getStack() );

            }

            
            if(i==0) {
                verb[i].setPerson(_commentPerson(person));
                verb[i].setMode(verbMode);
                verb[i].conjugate();
                phraseBuffer.append(subjectBuffer.toString().substring(1)+' '+objectBuffer.toString().substring(1)+' '+verb[i].getStack()+' '+verbalPreposition+separator[i]);

            }
            else {
                verb[i].setPerson(_commentPerson(person));
                verb[i].conjugate();
                phraseBuffer.append(' '+subjectBuffer.toString().substring(1)+' '+objectBuffer.toString().substring(1)+' '+verb[i].getStack()+separator[i]);
            }




        }
        return phraseBuffer.toString();
    }
    
    
    
    
    /**
     * getPhraseWithMultipleVerbs() yordamýnda kullanýlýyor.
     * <br> Intransitive fiiller için o anki object ten subject kýsmýný çekip
     * bunu bir sonraki indekste subject olarak kullanýyor.
     * Geri kalanlar gene object olarak kalýyor.
     * <br> Geri kalanlar <b>Preposition, Adverb ve Comma</b> nesneleri oluyor.
     * <br><strong>UYARI : </strong>Parametre olarak girilen objectIndex in
     * o OBJECTList indeki son index olmamasý lazým.
     * Bunun kontrolü bu metodun çaðrýldýðý ayaklarda yapýlmalý.
     * Örneðin getPhraseWithMultipleVerbs() metodu, bu metodu çaðýrmadan önce
     * gerekli kontrolleri yapýyor.
     * @param o Ýçinden subject çekilecek olan object nesnesi
     * @param objectIndex Bu object nesnesinin indexi, subject objectIndex+1 oluyor.
     * @return void
     */
    private void getObjectAndSubjectForIntransitives(OBJECTList o,int objectIndex) {
        
        int n=0;  // o nun hangi indexinde yeni temp baþlýyor.
        
        while( n < o.size()) {
            if( o.get(n) instanceof Preposition || ( o.get(n) instanceof Noun && o.getNoun(n).isAdverb() ) || o.get(n) instanceof Comma ) {
                n++;
                continue;
            }
            else
                break;
        }
        
        this.object[objectIndex].removeAll();
        this.subject[objectIndex+1].removeAll();
        
        int i;
        for(i=0;i<n;i++)
            this.object[objectIndex].add(o.get(i));
        for(i=n;i<o.size();i++)
            this.subject[objectIndex+1].add(o.get(i));

        
    }
    
    
    
    
    
}
