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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
    public parser(){
        hash = new HashSet<>();
        this.date=date;
        this.speach=speach;
        this.speaker=speaker;
        this.title=title;
        this.theme=theme;
        this.session=session;
        
    }
    
public void loadStopWords() throws FileNotFoundException, IOException{           
       
        File folder = new File("C:\\Users\\Iasonas\\Documents\\NetBeansProjects\\parser\\stopwords");

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


public void to_lemma(File inputFile)throws UnirestException, IOException { 
    int FlagName=0;
    int countsymbol=0;
    String field = "";
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = null;
    try { dBuilder = dbFactory.newDocumentBuilder();
    } catch (ParserConfigurationException ex) { }
    Document doc = null;
    try { doc = (Document) dBuilder.parse(inputFile); } 
    catch (SAXException ex) { } 
    catch (IOException ex) { } 
    doc.getDocumentElement().normalize(); 
    NodeList nList = doc.getElementsByTagName("Speech"); 
    for (int temp = 0; temp < nList.getLength(); temp++) {
        Node nNode = nList.item(temp); 
        Element elm = (Element) nNode;
        elm.getAttribute("speech");
         } 
}
    
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
