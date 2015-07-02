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
import java.util.Iterator;
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
        File file = new File("/home/iasonas/NetBeansProjects/Vouli/Vouli/stopwordsGr.txt");


            if (file.isFile()) {
                BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
                String line;
           while ((line = br.readLine()) != null) {
               this.hash.add(line);
             }
         }
        
    }    

public ArrayList<TNode>  filterstopWords(ArrayList<TNode> array) {
    String filter;
    String stop;
    int index;
    for ( int i = 0; i < array.size();i++){
        filter =array.get(i).lemmaSpeech;
        Iterator<String> itr = this.hash.iterator();
        while(itr.hasNext()){
            stop =itr.next();
            stop = " "+stop+" ";
            filter= filter.replace(stop, " ");
            
            }
        array.get(i).lemmaSpeech = filter;
           
        }
    
    return array;
}



public void setScore(ArrayList<TNode> array) throws UnirestException, IOException{
    // sends each  node of the Tnode arraylist to elasticSearch
    parser p= new parser();

    for ( int i = 0; i < array.size();i++){

        
String query = "{\n" +
                        "  \"query\": {\n" +
                        "    \"filtered\": {\n" +
                        "      \"query\": {\n" +
                        "        \"bool\": {\n" +
                        "          \"should\": [\n" +
                        "            {\n" +
                        "              \"query_string\": {\n" +
                        "                \"query\": \""+array.get(i).lemmaSpeech+"\"\n" +
                        "              }\n" +
                        "            }\n" +
                        "          ]\n" +
                        "        }\n" +
                        "      },\n" +
                        "      \"filter\": {\n" +
                        "        \"bool\": {\n" +
                        "          \"must\": [\n" +
                        "            {\n" +
                        "              \"fquery\": {\n" +
                        "                \"query\": {\n" +
                        "                  \"query_string\": {\n" +
                        "                    \"query\": \"intro:(\\\"true\\\")\"\n" +
                        "                  }\n" +
                        "                },\n" +
                        "                \"_cache\": true\n" +
                        "              }\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"fquery\": {\n" +
                        "                \"query\": {\n" +
                        "                  \"query_string\": {\n" +
                        "                    \"query\": \"date:(\\\""+array.get(i).date+"\\\")\"\n" +
                        "                  }\n" +
                        "                },\n" +
                        "                \"_cache\": true\n" +
                        "              }\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"fquery\": {\n" +
                        "                \"query\": {\n" +
                        "                  \"query_string\": {\n" +
                        "                    \"query\": \"session:(\\\""+array.get(i).Session+"\\\")\"\n" +
                        "                  }\n" +
                        "                },\n" +
                        "                \"_cache\": true\n" +
                        "              }\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"fquery\": {\n" +
                        "                \"query\": {\n" +
                        "                  \"query_string\": {\n" +
                        "                    \"query\": \"title:(\\\""+array.get(i).topic+"\\\")\"\n" +
                        "                  }\n" +
                        "                },\n" +
                        "                \"_cache\": true\n" +
                        "              }\n" +
                        "            }\n" +
                        "          ]\n" +
                        "        }\n" +
                        "      }\n" +
                        "    }\n" +
                        "  },\n" +
                        "  \"highlight\": {\n" +
                        "    \"fields\": {},\n" +
                        "    \"fragment_size\": 2147483647,\n" +
                        "    \"pre_tags\": [\n" +
                        "      \"@start-highlight@\"\n" +
                        "    ],\n" +
                        "    \"post_tags\": [\n" +
                        "      \"@end-highlight@\"\n" +
                        "    ]\n" +
                        "  },\n" +
                        "  \"size\": 500,\n" +
                        "  \"sort\": [\n" +
                        "    {\n" +
                        "      \"_score\": {\n" +
                        "        \"order\": \"desc\",\n" +
                        "        \"ignore_unmapped\": true\n" +
                        "      }\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}";
     
                array.get(i).score= p.executeQuery(query);


    }
   
}

public int elasticSearch(ArrayList<TNode> array, int id) throws UnirestException, IOException{
    // sends each  node of the Tnode arraylist to elasticSearch
    parser p= new parser();
    for ( int i = 0; i < array.size();i++){
        p.sendToElasticSearch(array.get(i).name, array.get(i).lemmaSpeech, array.get(i).date, array.get(i).Session, array.get(i).theme,array.get(i).topic, array.get(i).intro,id);
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
              //  System.out.println(line);
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
            
      //      System.out.println(score);
            
            in.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    
        
        return score;
    }


}
