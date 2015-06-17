/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xmlparser;

import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    TNode previous = new TNode();
       
    String line;
    String NameReg[];


public ArrayList<TNode> getline(){
// populate ArrayList, read parsed txt line by line and split headers for the metadata.
// also calculates whether  a speaker has an introductory  role or not
        int flagint=0;
        int introd=0;
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
                  int i=0;
                  node.name= NameReg[i];
                  i++;
                  if(i<NameReg.length){
                  node.date= NameReg[i];
                  }
                  i++;
                  if(i<NameReg.length){
                  node.Session= NameReg[i];
                  }
                  i++;
                  if(i<NameReg.length){
                  node.theme=  NameReg[i];
                  }
                  i++;
                  if(i<NameReg.length){
                  node.topic= NameReg[i];
                  }
                  
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
                    if(!(node.topic.equalsIgnoreCase(previous.topic))){
                        flagint=1;
                    }
                        
                        if(node.speech.length()>2000 && flagint==1){
                            flagint=0;
                            introd++;
                            node.intro= true;
                    
                        }
                    
                    previous= node;
                    node= new TNode();

                }
                
                
            }
           // System.out.println(introd);
        } catch (IOException ex) {
            Logger.getLogger(InputLine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arraynode;
        }

public void to_lemma(ArrayList<TNode> lemmaArray)throws UnirestException, IOException { 
    // creates a new set of txt files only with the speeches separated by new lines for the input of the tagger
            int FlagName=0;
            int countsymbol=0;
            String field = "";
            



      if(lemmaArray.size()>0){     
            File file = new File("/home/iasonas/Desktop/laptop/LemmaInput/LemmaInput_"+this.path.split("/")[this.path.split("/").length-1]);


            if (!file.exists()) {
                    file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
    
            for (int temp = 0; temp < lemmaArray.size(); temp++) { 

                bw.write(lemmaArray.get(temp).speech);
                bw.write("\n");
                bw.write("\n");
               

                        }
            bw.close();

        }
   
        }
        
        
        
 void startTagger(String tagger, String docs){
        
        if(tagger.charAt(tagger.length()-1)!='/')
            tagger=tagger+"/";
        
        if(docs.charAt(docs.length()-1)!='/')
            docs=docs+"/";

        
        System.out.println("Starting ilsp tagger..");
        
        String cmd = "java -jar "+tagger+"ilsp-nlp-asclient-1.1.5.jar  " +
                    "-b tcp://snf-228375.vm.okeanos.grnet.gr:61616 " +
                    "-e  ilsp-lemmatizer-aggregate-queue " +
                    "-id "+docs+" -ot xceslemma -s .txt -a .xml";
        
        System.out.println("Execunig: "+cmd);
                
        Process runtime;
        try {
            
            runtime = Runtime.getRuntime().exec(cmd);
            
            InputStream stdin = runtime.getInputStream();
            InputStreamReader isr = new InputStreamReader(stdin);
            BufferedReader br = new BufferedReader(isr);
            
            String line;

            while ( (line = br.readLine()) != null)
                System.out.println(line);
            
            int processComplete = runtime.waitFor();
            
            System.out.println("Done tagging .txt files!");
            
        } catch (IOException | InterruptedException ex) {
            System.err.println("Abort!");
            Logger.getLogger(InputLine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
        
}
        


