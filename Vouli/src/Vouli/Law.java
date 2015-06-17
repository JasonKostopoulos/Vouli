/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Vouli;

import java.util.ArrayList;

/**
 *
 * @author Iasonas
 */
public class Law {
    private boolean vote_result;
    private String title;
    private int for_vote;
    private int against_vote;
    private ArrayList<Speech> speech;
    private String legislator;
    
    public Law (){

        this.against_vote= against_vote;
        this.for_vote= for_vote;
        this.vote_result= vote_result;
        this.speech= new ArrayList<Speech>();
        this.title= title;
        this.legislator= legislator;
    }
    
    
    public String get_title(){
        return this.title;
    }


    public void set_title( String value){
        this.title = value ;
    }
    
    public String get_legislator(){
        return this.legislator;
    }


    public void set_legislator( String value){
        this.legislator = value ;
    }
    
    public boolean get_voteResult(){
        return this.vote_result;
    }
    

    
    public int getForVote(){
        return this.for_vote;
    }
    public int calculateForVote(){
      return 0;   
    }
    
    public int getAgainstVote(){
        return this.against_vote;
    }
    
    public Boolean calculateVoteResult(){
        return true;
    }
    
    public void set_voteResult( boolean value){
        this.vote_result = value;
    }
    
    public void set_againstVote(int value){
        this.against_vote= value;
    }
    
    public void set_ForVote(int value){
        this.for_vote = value;
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
    
    
    public Speech search_speech (String value){
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
