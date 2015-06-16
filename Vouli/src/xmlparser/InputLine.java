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
                    node= new TNode();

                }
                
                
            }
        } catch (IOException ex) {
            Logger.getLogger(InputLine.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arraynode;
        }

        public void to_lemma(ArrayList<TNode> lemmaArray)throws UnirestException, IOException { 
            int FlagName=0;
            int countsymbol=0;
            String field = "";



      if(lemmaArray.size()>0){     
            File file = new File("/home/iasonas/Desktop/laptop/LemmaInput/LemmaInput _"+lemmaArray.get((lemmaArray.size()-1)).date);


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
        
        
        
        public void startTagger(String docs, String tagger)
        {
            
            if(tagger.charAt(tagger.length()-1)!='/') tagger=tagger+"/"; 
            if(docs.charAt(docs.length()-1)!='/') docs=docs+"/";
            String cmd = "java -jar "+tagger+"ilsp-nlp-asclient-1.1.5.jar " + "-b tcp://snf-228373.vm.okeanos.grnet.gr:61616 " + "-e ilsp-lemmatizer-aggregate-queue " + "-id "+docs+" -ot xceslemma -s .txt -a .xml";
            System.out.println("Exectunig: "+cmd);
            Process runtime;
            try {
                runtime = Runtime.getRuntime().exec(cmd);
            int processComplete = runtime.waitFor();
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(InputLine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        
        
        
        public ArrayList<TNode> from_lemma(ArrayList<TNode> lemmaArray) throws IOException{
            int i =0;
            File folder = new File("/home/iasonas/Desktop/laptop/LemmaInput");
            File[] listOfFiles = folder.listFiles();

            for (File file : listOfFiles) {
                if (file.isFile()) {

                    InputLine input =new InputLine(file.getAbsolutePath());
                    

                    BufferedReader br = null ;
                    try {
                        br = new BufferedReader(new FileReader(input.path));
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(InputLine.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                            if((line.startsWith("^[Ά-ΏΑ-Ωα-ωά-ώ]+"))){
                                System.out.println("speech"); 
                                lemmaArray.get(i).lemmaSpeech=lemmaArray.get(i).lemmaSpeech + line;
                            }
                            else{
                                //System.out.println(lemmaArray.get(i).lemmaSpeech);
                                System.out.println("newline");
                                i++;
                            }
                        }
                           
                        }
                     }
            
            
                
               return lemmaArray;
        }
}
        


