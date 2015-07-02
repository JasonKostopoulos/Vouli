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
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


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
            int Flag=0;
            int countsymbol=0;
            String field = "";
            



      if(lemmaArray.size()>0){   
          for (int temp1 = 0; temp1 < lemmaArray.size(); temp1 ++) {
              if(lemmaArray.get(temp1).date != null){
                  Flag= 1;
                  field = lemmaArray.get(temp1).date;
              }
          }
       if( Flag == 1){   
                File file = new File("/home/iasonas/Desktop/laptop/LemmaInput/LemmaInput_"+field);


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
        }



        
        
        
public ArrayList<TNode> loadLemmas(ArrayList<TNode> array, String path) throws ParserConfigurationException, SAXException, IOException{
       File folder = new File(path);
       int count = 0;
       String lemmas =" ";
       int max = 0;
       int current = 0;
       int dateFlag= 0;
       int found =0 ;
       int i ;
       File[] listOfFiles = folder.listFiles();
       
       for (File file : listOfFiles) {
         if (file.isFile() ){
             path = file.getAbsolutePath();
             if( !path.split("\\.")[1].equalsIgnoreCase("xml")){
                 file.delete();
             }

         }
       }
      listOfFiles = folder.listFiles();
      FileNameNumericSort sort = new FileNameNumericSort();
     sort.files = listOfFiles;
     sort.sortByName();
     sort.sortByNumber();

    if(array.size()>0){
     for(int search =0; search< array.size();search++){
         if (array.get(search).date!= null){
                 dateFlag =search;
                 
             }
           }

        for (File files : sort.files) {
             if (files.isFile() ){
                    path = files.getAbsolutePath();

                      if (dateFlag!= 0 &&(array.get(dateFlag).date.equalsIgnoreCase((path.split("\\.")[0]).split("_")[1]))){

                            i =current;
                            File fXmlFile = new File(path);
                            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                            Document doc = dBuilder.parse(fXmlFile);


                            NodeList pList = doc.getElementsByTagName("p");
                            for (int ptemp = 0; ptemp < pList.getLength(); ptemp++) {
                                NodeList sList = pList.item(ptemp).getChildNodes();

                                for (int stemp = 0; stemp < sList.getLength(); stemp++) {
                                    NodeList tList = sList.item(stemp).getChildNodes();
                                      Node snode = sList.item(stemp);
                                     if (snode.getNodeType() == Node.ELEMENT_NODE) {
                                      Element sElement = (Element) snode;

                                     }
                                    for (int ttemp = 0; ttemp < tList.getLength(); ttemp++) {
                                        Node tNode = tList.item(ttemp);
                                        if (tNode.getNodeType() == Node.ELEMENT_NODE) {
                                                found= 1;
                                                Element eElement = (Element) tNode;
                                                lemmas= lemmas+" "+eElement.getAttribute("lemma");


                                        }
                                    }
                                    if(found ==1 && i< array.size()){
                                        found=0;
                                        array.get(i).lemmaSpeech =lemmas;
                                        i++;
                                        lemmas = " ";
                                    }


                                }

                            }
                              current= i;
                              i=0;
                        }

                 }
         }

    }

    return array;
}
        
void partialize (String docs) throws IOException{
    String path = null;
    int counter = 0;
    int partials=0;
    String pathTo = null;
    File folder = new File(docs);
    File[] listOfFiles = folder.listFiles();
    BufferedReader br = null ;
    
    for (File file : listOfFiles) {
        if (file.isFile()) {
        
            path = file.getAbsolutePath();
            try {
                   br = new BufferedReader(new FileReader(path));
             } catch (FileNotFoundException ex) {
                  Logger.getLogger(InputLine.class.getName()).log(Level.SEVERE, null, ex);
              }
            File filewrite = new File("/home/iasonas/Desktop/laptop/LemmaInput1/"+(path.split("/")[path.split("/").length-1])
                             .split(".txt")[(path.split("/")[path.split("/").length-1]).split(".txt").length-1 ]+"_"+"0"+".txt");
            if (!filewrite.exists()) {
                    filewrite.createNewFile();
            }
            FileWriter fw = new FileWriter(filewrite.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            try {
            while ((line = br.readLine()) != null) {
                counter++;
                if(counter >= 100){
                    counter = 0;
                    partials++;
                    bw.close();
                    pathTo= "/home/iasonas/Desktop/laptop/LemmaInput1/"+(path.split("/")[path.split("/").length-1])
                             .split(".txt")[(path.split("/")[path.split("/").length-1]).split(".txt").length-1 ]+"_"+partials+".txt";
                     System.out.println(pathTo);
                     filewrite = new File( pathTo );
                     if (!filewrite.exists()) {
                            filewrite.createNewFile();
                      }
                    fw = new FileWriter(filewrite.getAbsoluteFile());
                    bw = new BufferedWriter(fw);
                }
                else{
                    bw.write(line);
                    bw.write("\n");
                }
                

            }
            bw.close();
            counter = 0;
            partials = 0;
                }catch (IOException ex) {
            Logger.getLogger(InputLine.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }

}


 void startTagger(String tagger, String docs) throws IOException{
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
        


