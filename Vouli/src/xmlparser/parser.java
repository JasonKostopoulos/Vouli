/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xmlparser;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import static org.w3c.dom.Node.ELEMENT_NODE;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Iasonas
 */
public class parser {
    HashSet<String> hash;
    String date;
    String speach;
    String speaker;
    String title;
    String theme;
    String session;
    String type;
    String omilia= "Nspeech";
    String eisigisi= "Introspeech"; 
    public parser(){
        hash = new HashSet<>();
        this.date=date;
        this.speach=speach;
        this.speaker=speaker;
        this.title=title;
        this.theme=theme;
        this.session=session;
        this.type=type;
        
    }
    
public void loadStopWords() throws FileNotFoundException, IOException{           
       
        File folder = new File("/home/iasonas/NetBeansProjects,Parliament/Vouli/Vouli/stopwordsGr.txt");

        File[] listOfFiles = folder.listFiles();



        
        for (File file : listOfFiles) {
            if (file.isFile()) {
           //     System.out.println(file.getName());
                BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
                String line;
           while ((line = br.readLine()) != null) {
               this.hash.add(line);
              // System.out.println(line);
             }
         }
        }
    }    





//public void IntroSet (File inputFile) throws TransformerConfigurationException, TransformerException{
//    String Lasttopic= ""; 
//    int intro=0;
//     DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//    DocumentBuilder dBuilder = null;
//    try { dBuilder = dbFactory.newDocumentBuilder();
//    } catch (ParserConfigurationException ex) { }
//    Document doc = null;
//    try { doc = (Document) dBuilder.parse(inputFile); } 
//    catch (SAXException ex) { } 
//    catch (IOException ex) { } 
//    doc.getDocumentElement().normalize(); 
//        NodeList dateList= doc.getChildNodes();
//        for(int j=0; j< dateList.getLength(); j++){
//            Element dateelm= (Element) dateList.item(j);
//           // System.out.println(dateelm.getAttribute("Date"));
//          //  System.out.println(dateelm.getAttribute("Theme"));
//            NodeList themeList= dateList.item(j).getChildNodes();
//            System.out.println(themeList.getLength());
//            for(int th=0; th<themeList.getLength(); th++){
//               
//                if(themeList.item(th).getNodeType()== ELEMENT_NODE){
//                
//                  Element themeelm= (Element)themeList.item(th);
//                //  System.out.println(themeelm.getAttribute("Theme"));
//               // System.out.println(themeelm.getAttribute("Date"));
//             //    System.out.println(themeelm.getAttribute("theme"));
//                }
//                NodeList topicList = themeList.item(th).getChildNodes();
//                for( int tp=0; tp< topicList.getLength();tp++){
//                    if(topicList.item(tp).getNodeType()==  ELEMENT_NODE){
//                  Element topicelm= (Element)topicList.item(tp);
//                 // System.out.println(topicelm.getAttribute("Topic"));
//               // System.out.println(themeelm.getAttribute("Date"));
//                  if(!Lasttopic.equals(topicelm.getAttribute("topic"))){
//                     Lasttopic=topicelm.getAttribute("topic"); 
//                     NodeList speakerList= topicList.item(tp).getChildNodes();
//                     for(int i=0; i<speakerList.getLength(); i++){
//                          if(speakerList.item(i).getNodeType()==  ELEMENT_NODE){
//                              Element speakerelm= (Element)speakerList.item(i);
//                              if(intro==0){
//                                  
//                                 speakerelm.setAttribute("type", "introSpeech");
//                                doc.getChildNodes().item(j).getChildNodes().item(th).getChildNodes().item(tp).replaceChild(speakerelm, doc.getChildNodes().item(j).getChildNodes().item(th).getChildNodes().item(tp).getChildNodes().item(i));
//                                 System.out.println("eishghsh");
//                                 intro++;
//                               }
//                              else{
//                                  //System.out.println("normal");
//                                
//                                  speakerelm.setAttribute("type", "normalSpeech");
//                                  doc.getChildNodes().item(j).getChildNodes().item(th).getChildNodes().item(tp).replaceChild(speakerelm, (Element)doc.getChildNodes().item(j).getChildNodes().item(th).getChildNodes().item(tp).getChildNodes().item(i));
//                              }
//                              // System.out.println(speakerelm.getAttribute("speaker"));
//                             // System.out.println(themeelm.getAttribute("Date"));
//                              }
//                          }
//                          
//                       intro=0;
//                        }
//                  else{
//                       NodeList speakerList= topicList.item(tp).getChildNodes();
//                     for(int i=0; i<speakerList.getLength(); i++){
//                          if(speakerList.item(i).getNodeType()==  ELEMENT_NODE){
//                              Element speakerelm= (Element)speakerList.item(i);
//
//                                  speakerelm.setAttribute(type, "normalSpeech");
//                                  doc.getChildNodes().item(j).getChildNodes().item(th).getChildNodes().item(tp).replaceChild(speakerelm, (Element)doc.getChildNodes().item(j).getChildNodes().item(th).getChildNodes().item(tp).getChildNodes().item(i));
//                        }
//                    }
//                }
//            }
//        }
//      }
//    }
//         
//        
//    
//    //System.out.println(nList.getLength());
//   //System.out.println(nList.item(0).hasAttributes());
//   // System.out.println(nList.item(0).getChildNodes().item(0).hasAttributes());
////    NodeList tList = doc.getElementsByTagName("Topic");
////    NodeList sList = doc.getElementsByTagName("Speaker");
////   
////    for (int temp = 0; temp < nList.getLength(); temp++) {
////        Node nNode = nList.item(temp); 
////        
////        Node tNode= tList.item(temp);
////        Node sNode= sList.item(temp);
////        Element nelm = (Element) nNode;
////        Element telm= (Element)  tNode;
////        Element selm= (Element)  sNode;
////                
////                    
////            // if(nelm.getAttribute("theme").equalsIgnoreCase(" ΝΟΜΟΘΕΤΙΚΗΣ ΕΡΓΑΣΙΑΣ ")){
////                 //   System.out.println(telm.getAttribute("topic"));
////                    System.out.println(nelm.getAttribute("theme"));
////                 //   System.out.println(selm.getAttribute("speaker"));
////                //}
//   // }
//    		TransformerFactory transformerFactory = TransformerFactory.newInstance();
//		Transformer transformer = transformerFactory.newTransformer();
//		DOMSource source = new DOMSource(doc);
//		StreamResult result = new StreamResult(new File("/home/iasonas/Desktop/laptop/xmlProcedures/xml"+inputFile.getName()+".xml"));
// 
//		// Output to console for testing
//		// StreamResult result = new StreamResult(System.out);
//                //normalize the output so it is produced on different lines
//               transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//		transformer.transform(source, result);
//}
    

    
public void sendToElasticSearch(String speaker, String speach, String date,String session, String theme, String title, int i) throws UnirestException, MalformedURLException, IOException{
       
        try {

        String url = "http://192.168.1.114:9200/parliament/speach/"+i;

        URL obj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("PUT");
        String data =  "{\"speaker\": \""+speaker+"\", \"date\": \""+date+"\", \"session\": \""+session+"\", \"theme\": \""+theme+"\",  \"title\": \""+title+"\", \"speach\": \""+speach+"\"}";
        try (OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream())) {
            out.write(data);
        }
        new InputStreamReader(conn.getInputStream());   
        } catch (Exception e) {
            e.printStackTrace();
        }
  }
//        String cmmd[] = new String[3];
//        cmmd[0] = "C:\\cygwin64\\bin\\bash.exe";
//        cmmd[1] = "-c";
//        cmmd[2] = "curl -XPUT 'http://192.168.10.4:9200/twitter/tweet/2' -d '{\"message\": \"Another tweet, will it be indexed?\" }'";
//        Runtime rt= Runtime.getRuntime();
//        Process proc = rt.exec(cmmd);
 //       Process p = Runtime.getRuntime().exec(new String[]{"C:\\cygwin64\\bin\\curl.exe", "-XPUT 'http://192.168.10.4:9200/parliament/tweet/3' -d '{\"message\": \"Another tweet, will it be indexed?\" }'"});
//        String command="curl -XPUT 'http://192.168.10.4:9200/twitter/tweet/2' -d '\n" +
//                        "{\n" +
//                        "    \"user\": \"kimchy\",\n" +
//                        "    \"postDate\": \"2009-11-15T14:12:12\",\n" +
//                        "    \"message\": \"Another tweet, will it be indexed?\"\n" +
//                        "}'";
        
      //  Process p = Runtime.getRuntime().exec(command);

//
//        HttpResponse<JsonNode> response = Unirest.put("http://192.168.10.4:9200/parliament/speach/"+i+"/")
//       .header("accept", "application/json")
//       .field("speaker", speaker)
//       .field("date", date)
//       .field("session", session)
//       .field("theme", theme)
//       .field("title", title) 
//       .field("speach", speach)
//       .asJson();
//        
//        HttpURLConnection httpcon = (HttpURLConnection) ((new URL("http://192.168.10.4:9200/parliament_v3/speach/"+i+"/").openConnection()));
//        httpcon.setDoOutput(true);
////        httpcon.setRequestProperty("Content-Type", "application/json");
////        httpcon.setRequestProperty("Accept", "application/json");
//        httpcon.setRequestMethod("PUT");
//        httpcon.connect();
//
//        byte[] outputBytes = ("{'speach': "+this.speach+"}").getBytes();
//        OutputStream os = httpcon.getOutputStream();
//        os.write(outputBytes);
//
//
//
//        os.close();
        
//        System.out.println(response.getBody());
//        System.out.println(response.getStatus());



    public void xml_traverse(File inputFile) throws UnirestException, IOException {
            int FlagName=0;
            int countsymbol=0;
            String field = "";
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {

        }
            Document doc = null;
        try {
            doc = (Document) dBuilder.parse(inputFile);
        } catch (SAXException ex) {

        } catch (IOException ex) {

        }


            doc.getDocumentElement().normalize();
 
            NodeList nList = doc.getElementsByTagName("s");

           
                    
					
					//gia to sid
            for (int temp = 0; temp < nList.getLength(); temp++) {  
 
               	Node nNode = nList.item(temp);
                NodeList sList=nNode.getChildNodes();
                Element eElement=(Element) nNode;
                String sent_id= new String(eElement.getAttribute("id"));
               if(FlagName==0){
                   this.speach= field;
                   this.sendToElasticSearch(this.speaker, this.speach, this.date, this.session, this.theme, this.title,temp);
                  //System.out.println(" "+this.speaker+" "+this.date+" "+this.session+" "+this.theme+" "+this.title+" \n"+this.speach+"\n\n");
                       
               }
                FlagName=0;
                countsymbol=0;
                
                field="";
                
				//gia ta t
                for (int tmp2=0; tmp2<sList.getLength(); tmp2++){
             
                    
                    Node sNode=sList.item(tmp2);
                    if (sNode.getNodeType()==Node.ELEMENT_NODE){
                        Element sElement= (Element) sNode;
                       if( sElement.getAttribute("word").matches("[Ά-ΏΑ-Ω\\-]{2,}")&&tmp2==1){
                           FlagName=1;
                         
                       }
                       if(FlagName==1){
                           //System.out.println(""+sElement.getAttribute("word"));
		         if (!sElement.getAttribute("word").equals("-")){
                             field=field+" "+sElement.getAttribute("word");

                             //name
                             //date
                             //session
                             //theme
                             //topic
                            // System.out.println(field);
                         }
                         else{
                              switch(countsymbol){
                                  case 0: this.speaker = field;
                                  case 1: this.date= field;
                                  case 2: this.session= field;
                                  case 3: this.theme= field;
                                  default: this.title= field;
                                      
                              }
                             countsymbol++;
                            // System.out.println(field+"\n\n");
                            field= "";
                         }
                       }
                       else{
                           if(!this.hash.contains(sElement.getAttribute("lemma"))){
                                field=field+" "+sElement.getAttribute("lemma");
                           }
                       }

                    }
                    
                }
              

            }
    }
}
