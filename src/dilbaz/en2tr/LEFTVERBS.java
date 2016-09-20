package dilbaz.en2tr;

/**
* Bu s?n?f CYCLE3 s?n?f?ndan ça?r?l?yor
* Bu s?n?f LeftVeb s?n?f?yla ilgilenip, soru cümlelerini
* veya LeftVerblerin di?er görevlerinin bulunmas?n? sa?l?yor.
* @author Emre SOKULLU
*/

class LEFTVERBS {
    
    /**
     * OBJECT no
     */
    private int i; // OBJECT no
    
    /**
     * LeftVerb'ün olumlu u olumsuz mu oldu?unu takip
     * ediyor.
     */
    private boolean isNegative;
    
    /**
     * getPronounSequence() metodundan ç?kan pronoun ?n
     * Türkçe hali
     */
    private String tempPronounStack=new String();
    
    /**
     * getPronounSequence() metodundan ç?kan pronoun ?n
     * z?plama say?s?
     */
    private int tempPronounJump;
    
    /**
     * getPronounSequence() metodundan ç?kan pronoun ?n
     * ?ahs?
     */
    private int tempPronounPerson;

    
    /**
     * Kurucu yordam, kendi kendine içindeki tüm yordamlar? ça??r?p,
     * gerekli de?i?iklikleri GlobalVars içnde yap?p, kendi kendini yok ediyor.
     * @param objectNo OBJECT indeksi
     */
    public LEFTVERBS(int objectNo) {
        

        this.i=objectNo;
        
        switch(GlobalVars.OBJECT.getLeftVerb(i).getType()) {
            case 1:
            case 2:
                caseAreAndIs(); break;
            case 3:
            case 4:
                caseHaveAndHas();  break;
            case 5:
                caseWas(); break;/*
            case 6:
            case 7:
                caseModalAndWill(); break;*/
            case 8:
            case 9:
                caseShallAndCan(); break;
            case 10:
            case 11:
                caseDoAndDoes(); break;
            case 12:
                caseDid(); break;
        }
        
        //this.finalize();
        
                
                    
    }
    
    
    /**
     * E?er LeftVerb ümüz kendinden sonra pronoun sequence ? al?yorsa bunu yakal?yor.
     * Yordam al?p almad???n? boolean de?er döndürüp metoda bildiriyor, fakat as?l gerekli bilgiler
     * nesne alanlar? içnde saklan?yor.
     * <br>Araya isimler de kat?labiliyor.
     * @return boolean ( pronoun sequence m? degil mi; pronoun sequence sa s?n?f ici degisiklikler var)
     */
    private boolean _getSubjectSequence(int ii) {
        
        int start=ii; // ii nin ilk de?eri, daha sonradan gerekecek.
        boolean mayTakeComma=false; // il döngümüzde gerekecek.
        boolean cantTakeNoun=false; // ard?ndan isim alamaz.
        StringBuffer tempStack=new StringBuffer(); 
        int person=0;
        
        /*
         * Ilk Dongu :
         * Pronounlar? topluyor.
         * Person de?erleri topluyor (yorumsuz)
         * Jump ilerliyor.
         */
        while(ii-start<=5) {

            if( GlobalVars.OBJECT.get(ii) instanceof Pronoun && GlobalVars.OBJECT.getPronoun(ii).isSubjectPronoun() ) {
                

                if ( GlobalVars.OBJECT.getPronoun(ii).getPronoun().equals("i") ) {
                    tempStack.append(' '+"ben");
                    person += 100 ;
                }

                else if ( GlobalVars.OBJECT.getPronoun(ii).getPronoun().equals("he") || GlobalVars.OBJECT.getPronoun(ii).getPronoun().equals("she") || GlobalVars.OBJECT.getPronoun(ii).getPronoun().equals("it") ) {
                    tempStack.append(' '+"o");
                    person += 1;
                }


                else if ( GlobalVars.OBJECT.getPronoun(ii).getPronoun().equals("we") ) {
                    tempStack.append(' '+"biz");
                    person += 1000 ;
                }
                else if ( GlobalVars.OBJECT.getPronoun(ii).getPronoun().equals("you") || GlobalVars.OBJECT.getPronoun(ii).getPronoun().equals("u") ) {
                    tempStack.append(' '+"siz");
                    person += 10 ;
                }
                else if ( GlobalVars.OBJECT.getPronoun(ii).getPronoun().equals("thou") ) {
                    tempStack.append(' '+"sen");
                    person += 9 ;
                }
                else if ( GlobalVars.OBJECT.getPronoun(ii).getPronoun().equals("they") ) {
                    tempStack.append(' '+"onlar");
                    person += 2 ;
                }



                else if ( GlobalVars.OBJECT.getPronoun(ii).getPronoun().equals("i one") ) {
                    tempStack.append(' '+"ben tek ba\u015F\u0131ma");
                    person += 100 ;
                }

                else if ( GlobalVars.OBJECT.getPronoun(ii).getPronoun().equals("he one") || GlobalVars.OBJECT.getPronoun(ii).getPronoun().equals("she one") || GlobalVars.OBJECT.getPronoun(ii).getPronoun().equals("it one") ) {
                    tempStack.append(' '+"o tek ba\u015F\u0131na");
                    person += 1;
                }

                else if ( GlobalVars.OBJECT.getPronoun(ii).getPronoun().equals("we one") ) {
                    tempStack.append(' '+"biz tek ba\u015F\u0131m\u0131za");
                    person += 1000 ;
                }
                else if ( GlobalVars.OBJECT.getPronoun(ii).getPronoun().equals("you one") || GlobalVars.OBJECT.getPronoun(ii).getPronoun().equals("u one") ) {
                    tempStack.append(' '+"siz tek ba\u015F\u0131n\u0131za");
                    person += 10 ;
                }
                else if ( GlobalVars.OBJECT.getPronoun(ii).getPronoun().equals("thou one") ) {
                    tempStack.append(' '+"sen tek ba\u015F\u0131na");
                    person += 9 ;
                }
                else if ( GlobalVars.OBJECT.getPronoun(ii).getPronoun().equals("they one") ) {
                    tempStack.append(' '+"onlar tek ba\u015Flar\u0131na");
                    person += 2 ;
                }


                else {
                    tempStack.append(' '+"o");
                    person += 1;
                }


                ii++;
                mayTakeComma=true;
                cantTakeNoun=true;
            }
            
            // Nounlarla u?ra?ma k?sm?
            else if ( !cantTakeNoun && GlobalVars.OBJECT.get(ii) instanceof Noun && !GlobalVars.OBJECT.getNoun(ii).getStack().startsWith("^^") ) {
                tempStack.append( " " + GlobalVars.OBJECT.getNoun(ii).getNoun() );
                
                if(GlobalVars.OBJECT.getNoun(ii).isPlural())
                    person += 2;
                else
                    person++;
                
                ii++;
                mayTakeComma=true;
                cantTakeNoun=true;
            }
            
            // Comma larla u?ra?ma k?sm?
            else if( mayTakeComma && GlobalVars.OBJECT.get(ii) instanceof Comma )  {
                if(GlobalVars.OBJECT.getComma(ii).isComma())
                    tempStack.append(",");
                else
                    tempStack.append(" "+GlobalVars.OBJECT.getComma(ii).getStack());
                
                ii++;
                mayTakeComma=false;
                cantTakeNoun=false;
            }
            
            // yoksa
            else
                break;
        }

       
    /*
     * 2. K?s?m
     * Eldeki de?erleri yorumlay?p metodun as?l görevini yerine getiriyor.
     */
        if(ii-start>=1) {
            this.tempPronounStack=tempStack.toString();
            this.tempPronounJump=ii-start;
       
            if ( person > 100 )
            this.tempPronounPerson=11;
        else if ( person==100 )
            this.tempPronounPerson=1;
        else if ( person > 9 )
            this.tempPronounPerson=12;
        else if ( person == 9 )
            this.tempPronounPerson=2;
        else if ( person > 1 )
            this.tempPronounPerson=13;
        else
            this.tempPronounPerson=3;
           
            return true;
        }
        else
            return false;
    }
        
            

            
            
            
            
    /**
     * Are ve Is ile baþlayan fiilleri bulmak üzere tasarlandý.
     */
    private void caseAreAndIs() {
        
        int jump=1; // z?pla
        String subj = new String(); // özne stack i
        int person=0; // bilinmeyen kisi
        

        if( i+jump<GlobalVars.OBJECT.size() && this._getSubjectSequence(i+jump) ) {
            jump+=this.tempPronounJump;
            subj=this.tempPronounStack;
            person=this.tempPronounPerson;
        }
        else {
            
            if(GlobalVars.OBJECT.getLeftVerb(i).getType()==2) //is
                GlobalVars.OBJECT.set( i , new ConjugatedVerb("be#",0,"21",3,this.isNegative,false,false,0) ); // soru de?il
            else
                GlobalVars.OBJECT.set( i , new ConjugatedVerb("be#",0,"21",0,this.isNegative,false,false,0) ); // soru de?il
            return;
        
        }
        
        
        
        /* ASIL KISIM */

        // e?er do?ruysa kendinden sonra ToVerb geldi?i kesin.
        if ( i+jump<GlobalVars.OBJECT.size() && GlobalVars.OBJECT.get(i+jump) instanceof EndsING && GlobalVars.OBJECT.getEndsING(i+jump).isGoingToVerb()  ) {
            
            if( i+jump+2<GlobalVars.OBJECT.size() && GlobalVars.OBJECT.getToVerb(i+jump+1).isBe() && i+jump+2 < GlobalVars.OBJECT.size() && ( GlobalVars.OBJECT.get(i+jump+2) instanceof VerbPP || GlobalVars.OBJECT.get(i+jump+2) instanceof EndsD ) ) {
                GlobalVars.OBJECT.set(i+jump+1,new Noun(subj,false,false));
                GlobalVars.OBJECT.set(i+jump+2,new ConjugatedVerb(GlobalVars.OBJECT.getVerbalOBJECT(i+jump+2).getStack(),GlobalVars.OBJECT.getVerbalOBJECT(i+jump+1).getType(),"31",person,this.isNegative,true,true,0));
                GlobalVars.OBJECT.removeRange(i,i+jump+1);
            }
            else {
                GlobalVars.OBJECT.set(i+jump,new Noun(subj,false,false));
                GlobalVars.OBJECT.set(i+jump+1,new ConjugatedVerb(GlobalVars.OBJECT.getToVerb(i+jump+1).getVerb(),GlobalVars.OBJECT.getToVerb(i+jump+1).getType(),"31",person,this.isNegative,true,false,0));
                GlobalVars.OBJECT.removeRange(i,i+jump);
            }
            
            return;
        }
        
        

        
        else if ( i+jump<GlobalVars.OBJECT.size() && GlobalVars.OBJECT.get(i+jump) instanceof EndsING  ) {
           
            
            
            if( i+jump+1<GlobalVars.OBJECT.size() && GlobalVars.OBJECT.getEndsING(i+jump).isBe() && ( GlobalVars.OBJECT.get(i+jump+1) instanceof EndsD || GlobalVars.OBJECT.get(i+jump+1) instanceof VerbPP ) ) {
                GlobalVars.OBJECT.set(i+jump,new Noun(subj,false,false));
                GlobalVars.OBJECT.set(i+jump+1,new ConjugatedVerb(GlobalVars.OBJECT.getVerbalOBJECT(i+jump+1).getVerb(),GlobalVars.OBJECT.getVerbalOBJECT(i+jump+1).getType(),"22",person,this.isNegative,true,true,0));
                GlobalVars.OBJECT.removeRange(i,i+jump);
            }
            else {
                
                GlobalVars.OBJECT.set(i+jump,new ConjugatedVerb(GlobalVars.OBJECT.getEndsING(i+jump).getVerb(),GlobalVars.OBJECT.getEndsING(i+jump).getType(),"22",person,this.isNegative,true,false,0));
                GlobalVars.OBJECT.add(i+jump,new Noun(subj,false,false));
                GlobalVars.OBJECT.removeRange(i,i+jump);
            }

            return;
        }
        
        
        
        
        else if ( i+jump<GlobalVars.OBJECT.size() && ( GlobalVars.OBJECT.get(i+jump) instanceof EndsD || GlobalVars.OBJECT.get(i+jump) instanceof VerbPP ) ) {
            
            GlobalVars.OBJECT.set(i+jump,new ConjugatedVerb(GlobalVars.OBJECT.getVerbalOBJECT(i+jump).getVerb(),7,"21",person,this.isNegative,true,true,0)); // tüm pasifler 7 (geçi?siz) olacak.
            GlobalVars.OBJECT.add(i+jump,new Noun(subj,false,false));
            GlobalVars.OBJECT.removeRange(i,i+jump);
            return;
        }
        
        
        
        /*
         * Bu kalkabilir çünkü normal durumlarla çak??ma yaratabiliyor.
         */
        // problemli ve gereksiz.
        /*
        else if ( i+jump<GlobalVars.OBJECT.size() && GlobalVars.OBJECT.get(i+jump) instanceof ToVerb ) {
           
            
           
            if( i+jump+1<GlobalVars.OBJECT.size() && GlobalVars.OBJECT.getToVerb(i+jump).isBe() && ( GlobalVars.OBJECT.get(i+jump+1) instanceof EndsD || GlobalVars.OBJECT.get(i+jump+1) instanceof VerbPP ) ) {
                GlobalVars.OBJECT.set(i+jump,new Noun(subj,false,false));
                GlobalVars.OBJECT.set(i+jump+1,new ConjugatedVerb(GlobalVars.OBJECT.getVerbalOBJECT(i+jump+1).getVerb(),GlobalVars.OBJECT.getVerbalOBJECT(i+jump+1).getType(),"must",person,this.isNegative,true,true,0));
                GlobalVars.OBJECT.removeRange(i,i+jump);
            }
            else {
                
                GlobalVars.OBJECT.set(i+jump,new ConjugatedVerb(GlobalVars.OBJECT.getToVerb(i+jump).getVerb(),GlobalVars.OBJECT.getToVerb(i+jump).getType(),"must",person,this.isNegative,true,false,0));
                GlobalVars.OBJECT.add(i+jump,new Noun(subj,false,false));
                GlobalVars.OBJECT.removeRange(i,i+jump);
            }
            return;
        }
        */
            
            
        /**
         * bu da silinebilir
         */
        // supposed durumu
        // problemli ve gereksiz.
        /*
        else if ( i+jump+1<GlobalVars.OBJECT.size() && GlobalVars.OBJECT.get(i+jump) instanceof EndsD && GlobalVars.OBJECT.getEndsD(i+jump).getVerb().equals("^^varsay") && GlobalVars.OBJECT.get(i+jump+1) instanceof ToVerb ) {

            
           
            if( i+jump+2<GlobalVars.OBJECT.size() && GlobalVars.OBJECT.getToVerb(i+jump+1).isBe() && ( GlobalVars.OBJECT.get(i+jump+2) instanceof EndsD || GlobalVars.OBJECT.get(i+jump+2) instanceof VerbPP ) ) {
                GlobalVars.OBJECT.set(i+jump+1,new Noun(subj,false,false));
                GlobalVars.OBJECT.set(i+jump+2,new ConjugatedVerb(GlobalVars.OBJECT.getVerbalOBJECT(i+jump+2).getVerb(),GlobalVars.OBJECT.getVerbalOBJECT(i+jump+2).getType(),"must",person,this.isNegative,true,true,0));
                GlobalVars.OBJECT.removeRange(i,i+jump+1);
            }
            else {
                GlobalVars.OBJECT.set(i+jump,new Noun(subj,false,false));
                GlobalVars.OBJECT.set(i+jump+1,new ConjugatedVerb(GlobalVars.OBJECT.getToVerb(i+jump+1).getVerb(),GlobalVars.OBJECT.getToVerb(i+jump+1).getType(),"must",person,this.isNegative,true,false,0));
                GlobalVars.OBJECT.removeRange(i,i+jump);
            }
            return;







        }
        */


        // able durumu
        // bi problem ç?k?yor.
        else if ( i+jump+1<GlobalVars.OBJECT.size() && GlobalVars.OBJECT.get(i+jump) instanceof Noun && GlobalVars.OBJECT.getNoun(i+jump).getNoun().equals("^^yetenekli") && GlobalVars.OBJECT.get(i+jump+1) instanceof ToVerb ) {

            if( i+jump+2<GlobalVars.OBJECT.size() && GlobalVars.OBJECT.getToVerb(i+jump+1).isBe() && ( GlobalVars.OBJECT.get(i+jump+2) instanceof EndsD || GlobalVars.OBJECT.get(i+jump+2) instanceof VerbPP ) ) {
                GlobalVars.OBJECT.set(i+jump+1,new Noun(subj,false,false));
                GlobalVars.OBJECT.set(i+jump+2,new ConjugatedVerb(GlobalVars.OBJECT.getVerbalOBJECT(i+jump+2).getVerb(),7,"can",person,this.isNegative,true,true,0));
                GlobalVars.OBJECT.removeRange(i,i+jump+1);
            }
            else {
                GlobalVars.OBJECT.set(i+jump,new Noun(subj,false,false));
                GlobalVars.OBJECT.set(i+jump+1,new ConjugatedVerb(GlobalVars.OBJECT.getToVerb(i+jump+1).getVerb(),GlobalVars.OBJECT.getToVerb(i+jump+1).getType(),"can",person,this.isNegative,true,false,0));
                GlobalVars.OBJECT.removeRange(i,i+jump);
            }
            return;


        }

        /*
        // bound durumu
        else if ( i+jump+1<GlobalVars.OBJECT.size() && GlobalVars.OBJECT.get(i+jump) instanceof ConjugatedVerb && GlobalVars.OBJECT.getConjugatedVerb(i+jump).getStack().equals("^^ba\u011Fla") && GlobalVars.OBJECT.get(i+jump+1) instanceof ToVerb ) {

            if( i+jump+2<GlobalVars.OBJECT.size() && GlobalVars.OBJECT.getToVerb(i+jump+1).isBe() && ( GlobalVars.OBJECT.get(i+jump+2) instanceof EndsD || GlobalVars.OBJECT.get(i+jump+2) instanceof VerbPP ) ) {
                GlobalVars.OBJECT.set(i+jump+1,new Noun(subj,false,false));
                GlobalVars.OBJECT.set(i+jump+2,new ConjugatedVerb(GlobalVars.OBJECT.getVerbalOBJECT(i+jump+2).getVerb(),GlobalVars.OBJECT.getVerbalOBJECT(i+jump+2).getType(),"bound",person,this.isNegative,true,true,0));
                GlobalVars.OBJECT.removeRange(i,i+jump+1);
            }
            else {
                GlobalVars.OBJECT.set(i+jump,new Noun(subj,false,false));
                GlobalVars.OBJECT.set(i+jump+1,new ConjugatedVerb(GlobalVars.OBJECT.getToVerb(i+jump+1).getVerb(),GlobalVars.OBJECT.getToVerb(i+jump+1).getType(),"bound",person,this.isNegative,true,false,0));
                GlobalVars.OBJECT.removeRange(i,i+jump);
            }
            return;

        }
        */

       
        // SONDAN BI ONCEKI DURUM
        else if ( GlobalVars.OBJECT.size() > i+2 && ( ( GlobalVars.OBJECT.get(i+1) instanceof Pronoun && GlobalVars.OBJECT.getPronoun(i+1).isSubjectPronoun() ) || ( GlobalVars.OBJECT.get(i+1) instanceof Noun && ( GlobalVars.OBJECT.getNoun(i+1).isHumanName() || GlobalVars.OBJECT.getNoun(i+1).withArticle() ) ) ) && ( GlobalVars.OBJECT.get(i+2) instanceof Noun && ( GlobalVars.OBJECT.getNoun(i+2).isHumanName() || GlobalVars.OBJECT.getNoun(i+2).withArticle() ) ) ){
            GlobalVars.OBJECT.set(i,new Noun(GlobalVars.OBJECT.getOBJECT(i+1).getStack(),false,false));
            GlobalVars.OBJECT.set(i+1,new ConjugatedVerb(GlobalVars.OBJECT.getOBJECT(i+1).getStack()+" be#",7,"21",person,false,true,false,0));
            GlobalVars.OBJECT.remove(i+2);
            return;
        }
        
        // son
        else {
            if(GlobalVars.OBJECT.getLeftVerb(i).getType()==2) // is ; 3. tekil ?ah?s
                GlobalVars.OBJECT.set(i,new ConjugatedVerb("be#",0,"21",3,this.isNegative,false,false,0)); // bu soru degil ( sondan iki onceki false )
            else
                GlobalVars.OBJECT.set(i,new ConjugatedVerb("be#",0,"21",0,this.isNegative,false,false,0));
            return;
        }
   
    }

                        

    
    
    
    
    
    
    
    /**
     * Are ve Is ile ba?layan fiilleri bulmak üzere tasarland?.
     */
    private void caseHaveAndHas() {
        
        int jump=1; // z?pla
        String subj = new String(); // özne stack i
        int person=0; // bilinmeyen kisi
        

        if( this._getSubjectSequence(i+jump) ) {
            jump+=this.tempPronounJump;
            subj=this.tempPronounStack;
            person=this.tempPronounPerson;
        }
        else {
            
            if(GlobalVars.OBJECT.getLeftVerb(i).getType()==4) //is
                GlobalVars.OBJECT.set( i , new ConjugatedVerb("have#",0,"21",3,this.isNegative,false,false,0) ); // soru de?il
            else
                GlobalVars.OBJECT.set( i , new ConjugatedVerb("have#",0,"21",person,this.isNegative,false,false,0) ); // soru de?il
            return;
        
        }
        
        
        
        /* ASIL KISIM */


        if ( GlobalVars.OBJECT.size() > i+jump && ( GlobalVars.OBJECT.get(i+jump) instanceof EndsD || GlobalVars.OBJECT.get(i+jump) instanceof VerbPP ) ) {

            
           
            if(  GlobalVars.OBJECT.size() > i+1 && GlobalVars.OBJECT.get(i+jump) instanceof VerbPP && GlobalVars.OBJECT.getVerbPP(i+jump).getVerb().equals("^^ol") && ( GlobalVars.OBJECT.get(i+jump+1) instanceof EndsD || GlobalVars.OBJECT.get(i+jump+1) instanceof VerbPP ) ) {
                GlobalVars.OBJECT.set(i+jump,new Noun(subj,false,false));
                GlobalVars.OBJECT.set(i+jump+1,new ConjugatedVerb(GlobalVars.OBJECT.getVerbalOBJECT(i+jump).getVerb(),7,"11",person,this.isNegative,true,true,0));
                GlobalVars.OBJECT.removeRange(i,i+jump);
            }
            else {
                
                GlobalVars.OBJECT.set(i+jump,new ConjugatedVerb(GlobalVars.OBJECT.getVerbalOBJECT(i+jump).getVerb(),GlobalVars.OBJECT.getVerbalOBJECT(i+jump).getType(),"11",person,this.isNegative,true,false,0));
                GlobalVars.OBJECT.add(i+jump,new Noun(subj,false,false));
                GlobalVars.OBJECT.removeRange(i,i+jump);
            }
            return;


        }



        else {
            if(GlobalVars.OBJECT.getLeftVerb(i).getType()==4) // is ; 3. tekil ?ah?s
                GlobalVars.OBJECT.set(i,new ConjugatedVerb("have#",0,"21",3,this.isNegative,false,false,0)); // bu soru degil ( sondan iki onceki false )
            else
                GlobalVars.OBJECT.set(i,new ConjugatedVerb("have#",0,"21",0,this.isNegative,false,false,0));
            return;
        }
   
    } 
    
    
    
    
    
    
    
    /**
     * Was ve were ile ba?layan fiilleri bulmak üzere tasarland?.
     */
    private void caseWas() {
        
        int jump=1; // z?pla
        String subj = new String(); // özne stack i
        int person=0; // bilinmeyen kisi
        

        if( i+jump<GlobalVars.OBJECT.size() && this._getSubjectSequence(i+jump) ) {
            jump+=this.tempPronounJump;
            subj=this.tempPronounStack;
            person=this.tempPronounPerson;
        }
        else {

            GlobalVars.OBJECT.set( i , new ConjugatedVerb("be#",0,"11",0,this.isNegative,false,false,0) ); // soru de?il
            return;
        
        }
        
        
        
        /* ASIL KISIM */


        

        if ( i+jump<GlobalVars.OBJECT.size() && GlobalVars.OBJECT.get(i+jump) instanceof EndsING  ) {
           
            
            
            if( i+jump+1<GlobalVars.OBJECT.size() && GlobalVars.OBJECT.getEndsING(i+jump).isBe() && ( GlobalVars.OBJECT.get(i+jump+1) instanceof EndsD || GlobalVars.OBJECT.get(i+jump+1) instanceof VerbPP ) ) {
                GlobalVars.OBJECT.set(i+jump,new Noun(subj,false,false));
                GlobalVars.OBJECT.set(i+jump+1,new ConjugatedVerb(GlobalVars.OBJECT.getVerbalOBJECT(i+jump+1).getVerb(),7,"12",person,this.isNegative,true,true,0));
                GlobalVars.OBJECT.removeRange(i,i+jump);
            }
            else {
                
                GlobalVars.OBJECT.set(i+jump,new ConjugatedVerb(GlobalVars.OBJECT.getEndsING(i+jump).getVerb(),GlobalVars.OBJECT.getEndsING(i+jump).getType(),"12",person,this.isNegative,true,false,0));
                GlobalVars.OBJECT.add(i+jump,new Noun(subj,false,false));
                GlobalVars.OBJECT.removeRange(i,i+jump);
            }

            return;
        }
        
        else if ( i+jump<GlobalVars.OBJECT.size() && ( GlobalVars.OBJECT.get(i+jump) instanceof EndsD || GlobalVars.OBJECT.get(i+jump) instanceof VerbPP ) ) {
            
            GlobalVars.OBJECT.set(i+jump,new ConjugatedVerb(GlobalVars.OBJECT.getVerbalOBJECT(i+jump).getVerb(),7,"11",person,this.isNegative,true,true,0));
            GlobalVars.OBJECT.add(i+jump,new Noun(subj,false,false));
            GlobalVars.OBJECT.removeRange(i,i+jump);
            return;
        }
        
        
        // able durumu
        else if ( i+jump+1<GlobalVars.OBJECT.size() && GlobalVars.OBJECT.get(i+jump) instanceof Noun && GlobalVars.OBJECT.getNoun(i+jump).getNoun().equals("^^yetenekli") && GlobalVars.OBJECT.get(i+jump+1) instanceof ToVerb ) {

            if( i+jump+2<GlobalVars.OBJECT.size() && GlobalVars.OBJECT.getToVerb(i+jump+1).isBe() && ( GlobalVars.OBJECT.get(i+jump+2) instanceof EndsD || GlobalVars.OBJECT.get(i+jump+2) instanceof VerbPP ) ) {
                GlobalVars.OBJECT.set(i+jump+1,new Noun(subj,false,false));
                GlobalVars.OBJECT.set(i+jump+2,new ConjugatedVerb(GlobalVars.OBJECT.getVerbalOBJECT(i+jump+2).getVerb(),7,"canPast",person,this.isNegative,true,true,0));
                GlobalVars.OBJECT.removeRange(i,i+jump+1);
            }
            else {
                GlobalVars.OBJECT.set(i+jump,new Noun(subj,false,false));
                GlobalVars.OBJECT.set(i+jump+1,new ConjugatedVerb(GlobalVars.OBJECT.getToVerb(i+jump+1).getVerb(),GlobalVars.OBJECT.getToVerb(i+jump+1).getType(),"canPast",person,this.isNegative,true,false,0));
                GlobalVars.OBJECT.removeRange(i,i+jump);
            }
            return;


        }


        // SONDAN BI ONCEKI DURUM
        else if ( GlobalVars.OBJECT.size() > i+2 && ( ( GlobalVars.OBJECT.get(i+1) instanceof Pronoun && GlobalVars.OBJECT.getPronoun(i+1).isSubjectPronoun() ) || ( GlobalVars.OBJECT.get(i+1) instanceof Noun && ( GlobalVars.OBJECT.getNoun(i+1).isHumanName() || GlobalVars.OBJECT.getNoun(i+1).withArticle() ) ) ) && ( GlobalVars.OBJECT.get(i+2) instanceof Noun && ( GlobalVars.OBJECT.getNoun(i+2).isHumanName() || GlobalVars.OBJECT.getNoun(i+2).withArticle() ) ) ){
            GlobalVars.OBJECT.set(i,new Noun(GlobalVars.OBJECT.getOBJECT(i+1).getStack(),false,false));
            GlobalVars.OBJECT.set(i+1,new ConjugatedVerb(GlobalVars.OBJECT.getOBJECT(i+1).getStack()+" be#",7,"11",person,this.isNegative,true,false,0));
            GlobalVars.OBJECT.remove(i+2);
            return;
        }
        
        // son
        else {
            GlobalVars.OBJECT.set( i , new ConjugatedVerb("be#",0,"11",0,this.isNegative,false,false,0) );
            return;
        }
   
    }
    
    
    /**
     * Was ve were ile ba?layan fiilleri bulmak üzere tasarland?.
     */
    private void caseShallAndCan() {
        
        int jump=1; // z?pla
        String subj = new String(); // özne stack i
        int person=0; // bilinmeyen kisi
        

        if( i+jump<GlobalVars.OBJECT.size() && this._getSubjectSequence(i+jump) ) {
            
            
            jump+=this.tempPronounJump;
            subj=this.tempPronounStack;
            person=this.tempPronounPerson;

        }
        else {

            GlobalVars.OBJECT.set( i , new ConjugatedVerb("yap",7,"31",0,this.isNegative,false,false,0) ); // soru de?il
            return;
        
        }
        
        
        
        /* ASIL KISIM */
        if ( i+jump < GlobalVars.OBJECT.size() && ( GlobalVars.OBJECT.get(i+jump) instanceof Verb || ( GlobalVars.OBJECT.get(i+jump) instanceof Noun && GlobalVars.OBJECT.getNoun(i+jump).mayBeVerb() ) ) ) {


            GlobalVars.OBJECT.set(i+jump,new ConjugatedVerb(GlobalVars.OBJECT.getVerbalOBJECT(i+jump).getVerb(),GlobalVars.OBJECT.getVerbalOBJECT(i+jump).getType(),"can",person,this.isNegative,true,false,0));
            GlobalVars.OBJECT.add(i+jump,new Noun(subj,false,false));
            GlobalVars.OBJECT.removeRange(i,i+jump);

            return;
        }
        
        
        //sadece can için geçerli
        else if ( GlobalVars.OBJECT.getLeftVerb(i).getType()==9 && i+jump+1 < GlobalVars.OBJECT.size() &&  GlobalVars.OBJECT.get(i+jump) instanceof Verb && GlobalVars.OBJECT.getVerb(i+jump).equals("be#") && ( GlobalVars.OBJECT.get(i+jump+1) instanceof EndsD || GlobalVars.OBJECT.get(i+jump+1) instanceof VerbPP ) ) {


            GlobalVars.OBJECT.set(i+jump+1,new ConjugatedVerb(GlobalVars.OBJECT.getVerbalOBJECT(i+jump+1).getVerb(),7,"can",person,this.isNegative,true,true,0)); //pasif
            GlobalVars.OBJECT.set(i+jump,new Noun(subj,false,false));
            GlobalVars.OBJECT.removeRange(i,i+jump);

            return;
        }
        
        
        
        // SONDAN BI ONCEKI DURUM
        
        else if ( GlobalVars.OBJECT.size() > i+1 && GlobalVars.OBJECT.get(i+1) instanceof Pronoun && GlobalVars.OBJECT.getPronoun(i+1).isSubjectPronoun()  ){
            GlobalVars.OBJECT.set(i,new Noun(GlobalVars.OBJECT.getOBJECT(i+1).getStack(),false,false));
            GlobalVars.OBJECT.set(i+1,new ConjugatedVerb(GlobalVars.OBJECT.getOBJECT(i+1).getStack()+" be#",7,"11",person,this.isNegative,true,false,0));
            return;
        }
        
        // son
        // i can -- i shall ..... o kadar
        else if (GlobalVars.OBJECT.getLeftVerb(i).getType()==9 ) {
            GlobalVars.OBJECT.set( i , new ConjugatedVerb("yap",7,"can",0,this.isNegative,false,false,0) );
            return;
        }
        else {
            GlobalVars.OBJECT.set( i , new ConjugatedVerb("yap",7,"31",0,this.isNegative,false,false,0) );
            return;
        }

   
    }
    
    
    
    private void caseDoAndDoes() {
        

        int jump=1; // z?pla
        String subj = new String(); // özne stack i
        int person=0; // bilinmeyen kisi
        

        if( i+jump<GlobalVars.OBJECT.size() && this._getSubjectSequence(i+jump) ) {
            jump+=this.tempPronounJump;
            subj=this.tempPronounStack;

            if( GlobalVars.OBJECT.getLeftVerb(i).getType()==11
            )
                person=3;
            else
                person=this.tempPronounPerson;
        }
        else { 
            if( GlobalVars.OBJECT.getLeftVerb(i).getType()==10 )
                GlobalVars.OBJECT.set( i , new ConjugatedVerb("yap",0,"21",3,this.isNegative,false,false,0) ); // soru de?il
            else
                GlobalVars.OBJECT.set( i , new ConjugatedVerb("yap",0,"21",0,this.isNegative,false,false,0) ); // soru de?il
            return;
        
        }
        
        // have to ve normal
        if( GlobalVars.OBJECT.size() > i+jump+1 && GlobalVars.OBJECT.get(i+jump) instanceof LeftVerb && GlobalVars.OBJECT.getLeftVerb(i+jump).getType()==3 && GlobalVars.OBJECT.get(i+jump+1) instanceof ToVerb ) {
            GlobalVars.OBJECT.set(i+jump,new Noun(subj,false,false));
            GlobalVars.OBJECT.set(i+jump+1,new ConjugatedVerb(GlobalVars.OBJECT.getToVerb(i+jump+1).getVerb(),GlobalVars.OBJECT.getToVerb(i+jump+1).getType(),"can",person,this.isNegative,true,false,0));
            GlobalVars.OBJECT.removeRange(i,i+jump);
            return;
        }
        else if ( GlobalVars.OBJECT.size() > i+jump && ( GlobalVars.OBJECT.get(i+jump) instanceof Verb || ( GlobalVars.OBJECT.get(i+jump) instanceof Noun && GlobalVars.OBJECT.getNoun(i+jump).mayBeVerb() ) ) ) {
            //GlobalVars.OBJECT.set(i+jump,new Noun(subj,false,false));
            GlobalVars.OBJECT.set(i+jump,new ConjugatedVerb(GlobalVars.OBJECT.getVerbalOBJECT(i+jump).getVerb(),GlobalVars.OBJECT.getVerbalOBJECT(i+jump).getType(),"21",person,this.isNegative,true,false,0));
            GlobalVars.OBJECT.add(i+jump,new Noun(subj,false,false));
            GlobalVars.OBJECT.removeRange(i,i+jump);
            return;
        }
        
        
        // SONDAN BI ONCEKI DURUM     
        else if ( GlobalVars.OBJECT.size() > i+1 && GlobalVars.OBJECT.get(i+1) instanceof Pronoun && GlobalVars.OBJECT.getPronoun(i+1).isSubjectPronoun()  ){
            GlobalVars.OBJECT.set(i,new Noun(GlobalVars.OBJECT.getOBJECT(i+1).getStack(),false,false));
            GlobalVars.OBJECT.set(i+1,new ConjugatedVerb("yap",7,"21",person,this.isNegative,true,false,0));
            return;
        }
        
        // son
        // do fiili
        else if (GlobalVars.OBJECT.getLeftVerb(i).getType()==11 ) {
            GlobalVars.OBJECT.set( i , new ConjugatedVerb("yap",0,"21",3,this.isNegative,false,false,0) );
            return;
        }
        else {
            GlobalVars.OBJECT.set( i , new ConjugatedVerb("yap",0,"21",0,this.isNegative,false,false,0) );
            return;
        }

       
    }
    
    
    /**
     * Modal fiilleri ve Will ile baþlayan fiilleri halletmek üzere tasarlandý
     */
    private void caseModalAndWill() {
        
    }
    
    
    private void caseDid() {
        
        int jump=1; // z?pla
        String subj = new String(); // özne stack i
        int person=0; // bilinmeyen kisi
        

        if( i+jump<GlobalVars.OBJECT.size() && this._getSubjectSequence(i+jump) ) {
            jump+=this.tempPronounJump;
            subj=this.tempPronounStack;
            person=this.tempPronounPerson;
        }
        else {

            GlobalVars.OBJECT.set( i , new ConjugatedVerb("yap",0,"11",0,this.isNegative,false,false,0) ); // soru de?il
            return;
        
        }
        
        // have to ve normal
        if( GlobalVars.OBJECT.size() > i+jump+1 && GlobalVars.OBJECT.get(i+jump) instanceof LeftVerb && GlobalVars.OBJECT.getLeftVerb(i+jump).getType()==3 && GlobalVars.OBJECT.get(i+jump+1) instanceof ToVerb ) {
            GlobalVars.OBJECT.set(i+jump,new Noun(subj,false,false));
            GlobalVars.OBJECT.set(i+jump+1,new ConjugatedVerb(GlobalVars.OBJECT.getToVerb(i+jump+1).getVerb(),GlobalVars.OBJECT.getToVerb(i+jump+1).getType(),"canPast",person,this.isNegative,true,false,0));
            GlobalVars.OBJECT.removeRange(i,i+jump);
            return;
        }
        else if ( GlobalVars.OBJECT.size() > i+jump && ( GlobalVars.OBJECT.get(i+jump) instanceof Verb || ( GlobalVars.OBJECT.get(i+jump) instanceof Noun && GlobalVars.OBJECT.getNoun(i+jump).mayBeVerb() ) ) ) {
            //GlobalVars.OBJECT.set(i+jump,new Noun(subj,false,false));
            GlobalVars.OBJECT.set(i+jump,new ConjugatedVerb(GlobalVars.OBJECT.getVerbalOBJECT(i+jump).getVerb(),GlobalVars.OBJECT.getVerbalOBJECT(i+jump).getType(),"11",person,this.isNegative,true,false,0));
            GlobalVars.OBJECT.add(i+jump,new Noun(subj,false,false));
            GlobalVars.OBJECT.removeRange(i,i+jump);
            return;
        }
        
        
        // SONDAN BI ONCEKI DURUM     
        else if ( GlobalVars.OBJECT.size() > i+1 && GlobalVars.OBJECT.get(i+1) instanceof Pronoun && GlobalVars.OBJECT.getPronoun(i+1).isSubjectPronoun()  ){
            GlobalVars.OBJECT.set(i,new Noun(GlobalVars.OBJECT.getOBJECT(i+1).getStack(),false,false));
            GlobalVars.OBJECT.set(i+1,new ConjugatedVerb("yap",7,"11",person,this.isNegative,true,false,0));
            return;
        }
        
        // son
        // do fiili

        else {
            GlobalVars.OBJECT.set( i , new ConjugatedVerb("yap",0,"11",0,this.isNegative,false,false,0) );
            return;
        }

       
    }
    
    
    
    
    
    
    
    
    
    
}
    