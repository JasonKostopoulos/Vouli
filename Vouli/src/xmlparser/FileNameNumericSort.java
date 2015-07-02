/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlparser;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
/**
 *
 * @author iasonas
 */
public class FileNameNumericSort {

    public  File[] files = null; ;
    
    public FileNameNumericSort(){
        this.files = files;
    }
    
    
    public void sortByName() {
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                
                return (((o1.getName().split("\\.")[0]).split("_")[1]).compareTo(((o2.getName().split("\\.")[0]).split("_")[1])));
            
            }
        });
   }
    
     public void sortByNumber() {
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
               if((o1.getName().split("\\.")[0]).split("_")[1].equalsIgnoreCase((o2.getName().split("\\.")[0]).split("_")[1])){ 
                int n1 = Integer.parseInt((o1.getName().split("\\.")[0]).split("_")[2]);
                int n2 = Integer.parseInt((o2.getName().split("\\.")[0]).split("_")[2]);
                return n1 - n2;
            }
               return 0;
         
            }             
                //    int s = Integer.parseInt((name.split("\\.")[0]).split("_")[2]);
                    
            
        });

    }
}
