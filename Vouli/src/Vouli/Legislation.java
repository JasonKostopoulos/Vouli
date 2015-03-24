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
public class Legislation extends Theme {
  
    private ArrayList<Law> legislation_proposals;
    
    
    public Legislation(){
        super();
        this.legislation_proposals= new ArrayList<Law>();
    }
    
    public void addLaw(Law law){
        this.legislation_proposals.add(law);
    }
    
    public Law getLaw(String title){
        if(!legislation_proposals.isEmpty()){
            for(int i=0;i<legislation_proposals.size();i++){
                if(legislation_proposals.get(i).get_title().equals(title)){
                    return legislation_proposals.get(i);
                }
            }
        }
        return null;
    }
    
}
