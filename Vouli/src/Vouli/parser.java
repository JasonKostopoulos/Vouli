/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Vouli;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Iasonas
 */
public class parser {
      Pattern r;
      Matcher m;

      
      public parser(){
          this.r=r;
          this.m=m;

      }
      
      
      public String sinedriash(String line, String sinedriasi){
             this.r = Pattern.compile(sinedriasi);
             this.m = this.r.matcher(line);
            if (m.find()) { 
                return this.m.group(1);
            }
            return null;
      }
      public String imerominia(String line, String hmeromhnia){
             this.r = Pattern.compile(hmeromhnia);
             this.m = this.r.matcher(line);
            if (m.find()) {
                return this.m.group(1);
            }
            return null;
      }
      
      
      public String nomothetikhs_ergasias(String line, String nomothetikis_ergasias){
            this.r = Pattern.compile(nomothetikis_ergasias);
            this.m = this.r.matcher(line);
            if (this.m.find()) {
                return this.m.group(0);
            }
            return null;
      }
      
      
      public String epikairwn_erwthsewn(String line, String erwthseis){
            this.r = Pattern.compile(erwthseis);
            this.m = this.r.matcher(line);
            if (this.m.find()) {

                return this.m.group(0);
            }
            return null;
      }
      
      
      
      public void omolitis (String line, String onoma_omilith, session sessiont, Deputy participant){
        String value;
        this.r = Pattern.compile(onoma_omilith );
        this.m = r.matcher(line);
           if (this.m.find()) {

              value =m.group(1);
              if(sessiont.get_participant(value)==null){
                  participant.set_name(value);                                          
                  if(m.group(2)!= null){
                      participant.set_job(m.group(2));
                  }
              }
           }
      }
      
      public String chairman(String line, String proedros){
             this.r = Pattern.compile(proedros);
             this.m = this.r.matcher(line);
            if (m.find()) {

              return this.m.group(2);
                 }
       return null;
      }
      
      
      
      
     public Matcher nomos_ewrhsh_info ( String line, String nomos){
         
             r = Pattern.compile(nomos);
             m = r.matcher(line);
             return m;
         
         
     }
     
}
