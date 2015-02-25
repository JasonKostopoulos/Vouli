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
public class Deputy {
    private String onoma;
    private String party;
    private String job;
    private ArrayList <String> topicActivity;

    public  Deputy (){
        this.onoma= onoma;
        this.job= job;
        this.party= party;
        this.topicActivity= new ArrayList<String> ();
    }
    
    public String get_name(){
        return this.onoma;
    }
    
    public String get_party(){
        return this.party;
    }
    
    public String get_job(){
        return this.job;
    }
    
    public ArrayList<String> get_topicActivity(){
        return this.topicActivity;
    }
    
    public void set_topicActivity(ArrayList<String> value){
        this.topicActivity= value;
    }
    
    public void add_topicActivity(String value){
        this.topicActivity.add(value);
    }
    
    public void set_name(String value){
        this.onoma= value;
    }
    public void set_party(String value){
        this.party = value;
    }
    public void set_job(String value){
        this.job = value;
    }
}
