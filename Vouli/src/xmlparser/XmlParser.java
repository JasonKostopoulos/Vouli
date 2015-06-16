/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xmlparser;

import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.transform.TransformerException;

/**
 *
 * @author Iasonas
 */
public class XmlParser {


    private static  File fXmlFile;
    
   public static void main(String[] args) throws FileNotFoundException, IOException, UnirestException, TransformerException {

         File folder = new File("/home/iasonas/Desktop/laptop/InputProceduresTxt2");
         File[] listOfFiles = folder.listFiles();
             ArrayList<TNode> arraynode= new ArrayList();
             TNode a= new TNode();
             


//read each txt line by line and write it on a new txt after parse with the acronym Parsed on the beggining of its name
for (File file : listOfFiles) {
    if (file.isFile()) {
        
        InputLine input =new InputLine(file.getAbsolutePath());
        arraynode= input.getline();
        input.to_lemma( arraynode);
       // input.startTagger("/home/iasonas/Desktop/laptop/LemmaInput", "/home/iasonas/NetBeansProjects/Vouli/Vouli");
        input.from_lemma(arraynode);
        XmlMake xml= new XmlMake();
        xml.makeXml(arraynode, file.getName());
        parser p= new parser();
      //s  p.loadStopWords();
        
        
        
       // fXmlFile = new File("/home/iasonas/Desktop/laptop/xml_test/testParliament.xml");
        //p.xml_traverse(fXmlFile);
        //for(int i=0; i<arraynode.size()-1;i++){
          //  System.out.println(arraynode.get(i).Session);
        //}
         
         
//         for(int i=0; i<arraynode.size()-1;i++){
//             System.out.println(i+ arraynode.get(i).name+"  "+ arraynode.get(i).date);
//         }
  
    
         }
    }
    folder= new File("/home/iasonas/Desktop/laptop/xmlProcedures");
    listOfFiles = folder.listFiles();
    for (File file : listOfFiles) {
       
    if (file.isFile()) {
           
            parser p = new parser();
 //           p.IntroSet(file);
    
        }
      }
    }
}
    
    

	




    
    

