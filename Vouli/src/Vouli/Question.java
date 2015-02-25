/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vouliparserproject;

import java.util.ArrayList;

/**
 *
 * @author Iasonas
 */
public class Question  {
    private String title;
    private Deputy askedBy;
    private String askedTo;
    private Deputy answeredBy;
    private ArrayList<Speech> speech;

    public Question (){
        this.title= title;
        this.answeredBy= answeredBy;
        this.askedBy= askedBy;
        this.speech= new ArrayList<Speech>();
        this.askedTo=askedTo;
    }
    
    
    
    public String get_title(){
        return this.title;
    }


    public void set_title( String value){
        this.title = value ;
    }
    
  
    public String get_AskedTo(){
        return this.askedTo;
    }


    public void set_AskedTo( String value){
        this.askedTo = value ;
    }
    
    
    public Deputy get_askedBy(){
        return this.askedBy;
    }
    
    public Deputy get_answeredBy(){
        return this.answeredBy;
    }

    
    public void set_askedBy(Deputy value){
        this.askedBy= value;
    }
    
    public void set_answeredBy(Deputy value){
        this.answeredBy = value;
    }
    
    public ArrayList <Speech> get_speech() {
    return this.speech;
    }
    
    
    public void set_speech ( ArrayList<Speech> value){
        this.speech= value;
    }
    
    public void add_omilia(Speech value){
        this.speech.add(value);
    }
    
    
    public Speech search_speech (String value){// ΠΡΟΣΘΗΚΗ ΛΙΣΤΑΣ ΓΙΑ ΠΟΛΛΕΣ ΤΟΠΟΘΕΤΗΣΕΙΣ ΑΠΟ ΕΝΑΝ ΟΜΙΛΗΤΗ ΤΩΡΑ RETURNS FIRST
        if(!this.speech.isEmpty()){
            for( int i=0;i<this.speech.size();i++){
                if (this.speech.get(i).get_speaker().get_name().equals(value)){
                    return this.speech.get(i);
                }
            }
        }
        
        
            return null;
        }

}
