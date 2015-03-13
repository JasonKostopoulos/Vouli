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

/**
 *
 * @author Iasonas
 */
public class XmlParser {


    private static  File fXmlFile;
    
   public static void main(String[] args) throws FileNotFoundException, IOException, UnirestException {

//         File folder = new File("C:\\Users\\Iasonas\\Desktop\\ParseTest");
//         File[] listOfFiles = folder.listFiles();
//             ArrayList<TNode> arraynode= new ArrayList();
//             TNode a= new TNode();
//             
//
//
////read each txt line by line and write it on a new txt after parse with the acronym Parsed on the beggining of its name
//for (File file : listOfFiles) {
////    if (file.isFile()) {
//        
//         InputLine input =new InputLine(file.getAbsolutePath());
//         arraynode= input.getline();
        XmlMake xml= new XmlMake();
        parser p= new parser();
//         p.loadStopWords();
         fXmlFile = new File("C:\\Users\\User\\Desktop\\laptop\\testParliament.xml");
         p.to_lemma(fXmlFile);
//         p.xml_traverse(fXmlFile);
      //  for(int i=0; i<arraynode.size()-1;i++){
        //    System.out.println(arraynode.get(i).Session);
        //}
//         xml.makeXml(arraynode);
//         for(int i=0; i<arraynode.size()-1;i++){
//             System.out.println(i+ arraynode.get(i).name+"  "+ arraynode.get(i).speech);
//         }
  
    
         }
    }
//}
    
    

//	}




    
    

