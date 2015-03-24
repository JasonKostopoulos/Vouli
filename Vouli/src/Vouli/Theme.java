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
public class Theme {
    private String name;
    private String topicRemark;
   
    
    public Theme (){
        this.name=name;
        this.topicRemark= topicRemark;

    }
    public String get_name(){
        return this.name;
    }
    
        
    public String get_topicremark(){
    return this.topicRemark;
    }
    

    public void set_name( String value){
        this.name = value ;
    }
    

    
    

    
    public void set_topicRemark(String value){
      this.topicRemark= value;
    }
    
}
