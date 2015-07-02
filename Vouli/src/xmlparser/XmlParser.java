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
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 *
 * @author Iasonas
 */
public class XmlParser {


    private static  File fXmlFile;
    
   public static void main(String[] args) throws FileNotFoundException, IOException, UnirestException, TransformerException, ParserConfigurationException, SAXException {
         int id=0;  
         InputLine input=null;
         parser p= new parser();
         File folder = new File("/home/iasonas/Desktop/laptop/InputProceduresTxt2");
         File[] listOfFiles = folder.listFiles();
        ArrayList<TNode> arraynode= new ArrayList();
             TNode a= new TNode();
             


       System.out.println("0");
for (File file : listOfFiles) {
    if (file.isFile()) {
        
       input =new InputLine(file.getAbsolutePath());
        arraynode= input.getline();
        input.to_lemma( arraynode);
        


         }
    }
 input.partialize("/home/iasonas/Desktop/laptop/LemmaInput" );
 input.startTagger( "/home/iasonas/NetBeansProjects/Vouli/Vouli","/home/iasonas/Desktop/laptop/LemmaInput1");

 for (File file : listOfFiles) {
    if (file.isFile()) {
        ArrayList<TNode> arraynodes= new ArrayList();
        input =new InputLine(file.getAbsolutePath());
        arraynodes= input.getline();
        input.to_lemma( arraynodes );
        arraynodes = input.loadLemmas( arraynodes, "/home/iasonas/Desktop/laptop/LemmaInput1" );
        p.loadStopWords();  
        arraynodes= p.filterstopWords(arraynodes);
        id = p.elasticSearch(arraynodes, id);
         }
    }
 
       System.out.println("duo");
    for (File file : listOfFiles) {
    if (file.isFile()) {
      input =new InputLine(file.getAbsolutePath());
       arraynode= input.getline();
       p.setScore(arraynode);
       XmlMake xml= new XmlMake();
       xml.makeXml(arraynode, file.getName()); 
    }
   }

   
 

    }
}
    
    

	




    
    

