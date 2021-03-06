/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Vouli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 *
 * @author Iasonas
 */
public class Input {
      //
   public static void main(String[] args) throws FileNotFoundException, IOException {
       // regex for metadata extraction
      String sinedriasi = "^([Α-Ω]+ [Α-Ω]+)΄"  ;// ΣΥΝΕΔΡΙΑΣΗ
      String hmeromhnia= "^([Ά-ΏΑ-Ωα-ωά-ώΐΰ]+ [0-9]+ [Ά-ΏΑ-Ωα-ωά-ώΐΰ]+ [0-9]+)";// ημερομηνία
      String proedros="^(ΠΡΟΕΔΡΕΥΩΝ|ΠΡΟΕΔΡΕΥΟΥΣΑ|ΠΡΟΕΔΡΟΣ) \\(([Ά-ΏΑ-Ωα-ωά-ώΐΰ\\-]+ [Ά-ΏΑ-Ωα-ωά-ώΐΰ\\-]+)\\)\\:";// Προεδρεύον δινει στο 0 ολο στο 1 μονο ονομα
      String onoma_omilith="^([Ά-ΏΑ-Ωΐΰ\\-]+ [Ά-ΏΑ-Ωΐΰ\\-]+) ?(\\(.*\\))?\\:";// ονομα ομιλητών 0 ολο 1 ονομα 2 ιδιοτητα αν  έχει
      String nomothetikis_ergasias="(ΝΟΜΟΘΕΤΙΚΗΣ ΕΡΓΑΣΙΑΣ)";
      String nomos= "του σχεδίου νόμου του (.*)\\: \\«(.*)\\»" ;// 0 ολο , 1 υπουργείο,2 τίτλος
      String topothethsh = ":(.+)"; 
    // 
    // Κανόνες για  Επίκαιρες ερωτήσεις.  
    //  
      String erwthseis = "(ΕΠΙΚΑΙΡΩΝ ΕΡΩΤΗΣΕΩΝ)";
      String erwthsh_info = "με αριθμό ([0-9\\-\\/]+) (επίκαιρη|επίκαιρης) (ερώτηση|ερώτησης) [ά-ώα-ωΐΰ]+ [ά-ώα-ωΐΰ]+( του| της| των) (Βουλευτή|Βουλευτού) (.+)( του| της| των) ([Α-ΩΆ-Ώά-ώα-ωΐΰ\\-].+)κ\\. (.+)προς (.+)σχετικά με (.+)";
      // ερωτήσεις 1 αριθμός, 6 περιφέρια βουλευτή, 8 κόμμα,9 όνομα,10 προς,1 θέμα
   
      // variables declaration***
      
      session session= new  session();
      Deputy participant= new Deputy();
      Law law= new Law();
      Question question= new Question();
      int legislation=0;
      int questions=0;
      Legislation legalize= new Legislation();
      TimelyQuestions timely= new TimelyQuestions();
      Speech speech=new Speech();
      int SpeechFlag=1;
      ArrayList<Deputy> listOfParticipants;
      

// Create a Pattern object



      parser p = new parser();
      Matcher m;
      

File folder = new File("/home/iasonas/Desktop/laptop/InputProceduresTxt");

File[] listOfFiles = folder.listFiles();



//read each txt line by line extract metadata  and write it on a new txt after parse with the acronym Parsed on the beggining of its name
// the new form of txt files will contain speeches separated by newlines and on the header of each speech  there is a line with the metadata separated by "_"
for (File file : listOfFiles) {
    if (file.isFile()) {
        File wfile = new File("Parsed"+file.getName());
        wfile.createNewFile();
        FileWriter fw = new FileWriter(wfile.getAbsoluteFile());
	BufferedWriter bw = new BufferedWriter(fw);
        System.out.println(file.getName());
        BufferedReader br = new BufferedReader(new FileReader(file.getAbsolutePath()));
        String line;
        while ((line = br.readLine()) != null) {
            
            
            //remove extra unwanted blanks
            try{

                while((line.charAt(0)==' ')||(line.charAt(0)=='\t')){
                    line= line.substring(1);
                }
            }
            catch(StringIndexOutOfBoundsException ex){
            }

            
            //nomos_ewthsh_info uses the current line and a regex pattern 
            
            
            //SINEDRIASH
            
            m=p.nomos_ewrhsh_info(line, sinedriasi);
            if (m.find()) { 
                SpeechFlag=0;
                session.set_name(m.group(1));
            }
            
 

            
            //DATE

            p = new parser();
            m=p.nomos_ewrhsh_info(line, hmeromhnia);
            if (m.find()) {
                SpeechFlag=0;
                session.set_date(m.group(1));
            }
            
            
            
            // NOMTHETIKIS ERGASIAS
                p = new parser();
                m=p.nomos_ewrhsh_info(line, nomothetikis_ergasias);
            if (m.find()) {
                SpeechFlag=0;
                legislation=1;
                questions=0;
               legalize.set_name(m.group(0));
               session.add_topic(legalize);
            }


            
            // EPIKAIRWN ERWTHSEWN
            p = new parser();
            m= p.nomos_ewrhsh_info(line, erwthseis);
            if (m.find()) {
                SpeechFlag=0;
                questions=1;
                legislation=0;
                timely.set_name(m.group(0));
               session.add_topic((Theme)timely);
            }
          
            
            // dEPUTY NAME
            
            p = new parser();
            m=p.nomos_ewrhsh_info(line, onoma_omilith);
            if (m.find()) {
                bw.write("\n");
                bw.write("\n");
                bw.write(m.group(0)+" _ ");
                bw.write(session.get_date()+" _ ");
                bw.write(session.get_name()+" _ ");
                if((questions==1) && (timely.get_name()!=null)){
                    bw.write(timely.get_name()+" _ ");
                    if(question.get_title()!=null){
                        bw.write(question.get_title()+" _\n");
                    }
                    else{
                            bw.write("\n");
                        }
                }
                else{
                    if(legalize.get_name()!=null){
                        bw.write(legalize.get_name()+" _ " );
                        if(law.get_title()!=null){
                         bw.write(law.get_title()+" _ \n");
                         }
                        else{
                            bw.write("\n");
                        }
                    }
                    }
                SpeechFlag= 1;
                line=line.substring(m.group(0).length());

                
               if(session.get_participant(m.group(1))==null &&(questions!=0||legislation!=0)){
                   participant.set_name(m.group(1)); 
                   if(m.group(2)!= null){
                       participant.set_job(m.group(2));
                   }
                   session.add_participant(participant);
               }
               if(legislation==1){
                speech.set_speaker(participant);
               }
               if (questions==1){
                speech.set_speaker(participant);
               }
            }

          
         
            
            //CHAIRMAN NAME
            
            p = new parser();
            m=p.nomos_ewrhsh_info(line, proedros);
            if (m.find()) {
               SpeechFlag=0;
              if( session.get_participant(m.group(2))== null&&(questions!=0||legislation!=0)){
                  participant.set_name(m.group(2));
                  session.add_participant(participant);
                  session.add_president(participant);
              }
            }
          




            // LAW TITLE

              p = new parser();
              m=p.nomos_ewrhsh_info(line, nomos);
               
             if (m.find()&&(questions!=0||legislation!=0)) {
                 law.set_title(m.group(2));
                 law.set_legislator(m.group(1));
                 legalize=(Legislation)session.get_topic(legalize.get_name());
                 legalize.addLaw(law);
                 session.set_topic(legalize);

             }


             
             
             // QUESTION TITLE
             
             
             p = new parser();
              
             m=p.nomos_ewrhsh_info(line, erwthsh_info);
             if (m.find()&&(questions!=0||legislation!=0)) {
                 question.set_title(m.group(11));

                
                if(session.get_participant(m.group(9))==null&&(questions!=0||legislation!=0)){
                    
                   participant.set_name(m.group(9));
                   session.add_participant(participant);
                   question.set_askedBy(participant);
               }
                else{
                 participant= session.get_participant(m.group(9));
                 question.set_askedBy(participant);
                 question.set_AskedTo(m.group(10));
                }
             }
             
             
             // REMOVE ALL BLANKS THAT ARE UNWANTED ON THE LINE TO-BE-WRITEN
            line= line.replaceAll("[^\\p{L}\\p{Nd}]+", " ");
            if(line.endsWith("\n")){
                line=line.substring(0,line.length()-1);
            }

            if( SpeechFlag==1){
                        if (!line.isEmpty()){
                        while(((line.charAt(0)==' ')||(line.charAt(0)=='\t'))&&(line.length()>=2)){

                              line= line.substring(1);
                        }
                }
                bw.write(line);
            }                           
           }
        

        br.close();
        bw.close();
    }
}

	}

}
