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
public class TimelyQuestions extends Theme {
        private ArrayList<Question> questions;
    
    
    public TimelyQuestions(){
        super();
        this.questions= new ArrayList<Question>();
    }
    
    public void addQuestion(Question question){
        this.questions.add(question);
    }
    
    public Question getQuestion(String title){
        if(!questions.isEmpty()){
            for(int i=0;i<questions.size();i++){
                if(questions.get(i).get_title().equals(title)){
                    return questions.get(i);
                }
            }
        }
        return null;
    }
    
}
