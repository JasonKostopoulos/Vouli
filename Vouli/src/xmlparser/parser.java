/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xmlparser;


import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.io.IOException;
import java.io.InputStreamReader;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author Iasonas
 */
public class parser {
    HashSet<String> hash;
   
    public parser(){
        hash = new HashSet<>();

        
    }
    
public void loadStopWords() throws FileNotFoundException, IOException{           
       // loads a set of stopwords on parser that can be used to clear the input speeches on ElasticSearch 
        File folder = new File("/home/iasonas/NetBeansProjects/Vouli/Vouli/stopwordsGr.txt");

        File[] listOfFiles = folder.listFiles();



        
        for (File file : listOfFiles) {
            if (file.isFile()) {
                BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
                String line;
           while ((line = br.readLine()) != null) {
               this.hash.add(line);
             }
         }
        }
    }    




public void setScore(ArrayList<TNode> array) throws UnirestException, IOException{
    // sends each  node of the Tnode arraylist to elasticSearch
    parser p= new parser();

    for ( int i = 0; i < array.size();i++){

        
String query = "{\"facets\":{\"terms\":{\"terms\":{\"field\":\"_type\",\"size\":100,\"order\":\"count\",\"exclude\":[]},\"facet_filter"
                + "\":{\"fquery\":{\"query\":{\"filtered\":{\"query\":{\"bool\":{\"should\":[{\"query_string\":{\""
                + "query\":\""+array.get(i).speech+"\"}}]}},\"filter\":{\"bool\":{\"must\":[{\"fquery\":{\"query\":{\"query_string\":{\"query\":\""
                + "session:(\\\""+array.get(i).Session+"\\\")\"}},\"_cache\":true}},{\"fquery\":{\"query\":{\"query_string\":{\"query\":\""
                + "date:(\\\""+array.get(i).date+"\\\")\"}},\"_cache\":true}},{\"fquery\":{\"query\":{\"query_string\":{\"query\":\""
                + "title:(\\\""+array.get(i).topic+"\\\")\"}},\"_cache\":true}},{\"fquery\":{\"query\":{\"query_string\":{\"query\":\""
                + "intro:(\\\"true\\\")\"}},\"_cache\":true}}]}}}}}}}},\"size\":0}";
     
                array.get(i).score= p.executeQuery(query);


    }
   
}

public int elasticSearch(ArrayList<TNode> array, int id) throws UnirestException, IOException{
    // sends each  node of the Tnode arraylist to elasticSearch
    parser p= new parser();
    for ( int i = 0; i < array.size();i++){
        p.sendToElasticSearch(array.get(i).name, array.get(i).speech, array.get(i).date, array.get(i).Session, array.get(i).theme,array.get(i).topic, array.get(i).intro,id);
        id++;
    }
    return id;
}
    
public void sendToElasticSearch(String speaker, String speach, String date,String session, String theme, String title,boolean intro, int i) throws UnirestException, MalformedURLException, IOException{
       
        try {

        String url = "http://localhost:9200/parliament/speach/"+i;

        URL obj = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("PUT");
        String data =  "{\"speaker\": \""+speaker+"\", \"date\": \""+date+"\", \"session\": \""+session+"\", \"theme\": \""+theme+"\",  \"title\": \""+title+"\", \"intro\": \""+intro+"\", \"speach\": \""+speach+"\"}";
        try (OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream())) {
            out.write(data);
            out.close();
        }
            String line = "";
            BufferedReader in = new BufferedReader(new 
                                     InputStreamReader(conn.getInputStream()));
            
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
  }

 private final String elasticSearchQueryURI = "http://localhost:9200/_all/_search?";
 
private double executeQuery(String query){

        double score = 0.0;
        
       
        try {
            String url = elasticSearchQueryURI;

            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            
            
            try (OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream())) {
                out.write(query);
                out.close();
            }

            String json = "";
            String line = "";
            BufferedReader in = new BufferedReader(new 
                                     InputStreamReader(conn.getInputStream()));
            
            while ((line = in.readLine()) != null) {
                json = json + line + " ";
            }

            JSONObject jsonObj = new JSONObject(json);
            try{
                score = jsonObj.getJSONObject("hits").getDouble("max_score");
            }
            catch (JSONException ex){
                score = 0.0;
            }
            
            System.out.println(score);
            
            in.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    
        
        return score;
    }


}
