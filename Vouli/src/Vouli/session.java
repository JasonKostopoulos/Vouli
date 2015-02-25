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
public class session {
    private String name;
    private String date;
    private ArrayList<Theme> topics;
    private ArrayList<Deputy> president;
    private ArrayList<Deputy> participants;
    
    public session (){
        this.name=name;
        this.date=date;
        this.topics= new ArrayList<Theme>();
        this.participants= new ArrayList<Deputy>();
        this.president= new ArrayList<Deputy>();
    }
    
    public String get_name(){
        return this.name;
    }
    public String get_date(){
        return this.date;
    }
    public Theme get_topic(String value){
        if (!topics.isEmpty()){
            for(int i=0; i <topics.size();i++){
                if(topics.get(i).get_name().equals(value)){
                return topics.get(i);
            }
            }
        }
        return null;
    }
    
    public void set_topic(Theme value){
                if (!topics.isEmpty()){
            for(int i=0; i <topics.size();i++){
                if(topics.get(i).get_name().equals(value.get_name())){
                topics.set(i, value);
            }
            }
        }
    }
    
    public void add_topic( Theme value){
        this.topics.add(value);
    }
    
    public ArrayList<Deputy> get_president(){
        return this.president;
    }
    
    public Deputy get_participant(String value){
        if(! participants.isEmpty()){
            for(int i=0; i< participants.size();i++){
                if( participants.get(i).get_name().equals(value)){
                    return participants.get(i);
                }
            }
        }
        return null;
    }
    
    public void set_name(String value){
        this.name= value;
    }
    
    public void set_date(String value){
        this.date = value;
    }
    
    public void set_president( ArrayList<Deputy> value){
        this.president = value;
    }
    
    public void set_participant( ArrayList<Deputy> value){
        this.participants = value;
    }
    
    public Deputy get_last_president(){
        return this.president.get(this.president.size()-1);
    }
    
    public void add_president(Deputy value){
        this.president.add(value);
    }
    
    public Deputy search_president(String value){
        if(!this.president.isEmpty()){
            for( int i=0;i<this.president.size();i++){
                if (this.president.get(i).get_name().equals(value)){
                    return this.president.get(i);
                }
            }
        }
        
            return null;
        
    }
    
    public ArrayList<Deputy> get_list_participant(){
        return this.participants;
    }
    
    public void add_participant( Deputy value){
        this.participants.add(value);
    }
    
    public Deputy get_last_participant(){
        return this.participants.get(this.participants.size()-1);
    }
    
    public Deputy search_participant(String value){
                if(!this.participants.isEmpty()){
            for( int i=0;i<this.participants.size();i++){
                if (this.participants.get(i).get_name().equals(value)){
                    return this.participants.get(i);
                }
            }
        }
        
            return null;
    }
    
}
