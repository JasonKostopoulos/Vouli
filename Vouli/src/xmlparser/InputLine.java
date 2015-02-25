/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xmlparser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Iasonas
 */
public class InputLine {
    int Flagmeta=0;
    String path;
    public InputLine(String paths){

        this.path= paths;
        
    }
    ArrayList<TNode> arraynode= new ArrayList();
    TNode node= new TNode();
       
    String line;
    String NameReg[];


public ArrayList<TNode> getline(){
//read each txt line by line and write it on a new txt after parse with the acronym Parsed on the beggining of its name

        BufferedReader br = null ;
        try {
            br = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InputLine.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            while ((line = br.readLine()) != null) {
    
                
                
                if(line.matches("^[Ά-ΏΑ-Ω\\-]{2,}(.*)")){
                    
                  Flagmeta=1;
                  NameReg =  line.split("_");
                 //   System.out.println(NameReg[0]);
                  node.name= NameReg[0];
                  node.date= NameReg[1];
                  node.Session= NameReg[2];
                  node.theme=  NameReg[3];
                  node.topic= NameReg[4];
                }
                else{
                  Flagmeta=0;
                  if(node.speech.equals(" ")){
                   node.speech= line;   
                  }
                  else{
                        node.speech= node.speech+ " "+line;
                  }
                }
                if(line.isEmpty()){
                    arraynode.add(node);
                    node= new TNode();

                }
                
                
            }
        } catch (IOException ex) {
            Logger.getLogger(InputLine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arraynode;
        }
}
        


