package main.java.InputOutput;

import java.io.BufferedReader;
import java.io.FileReader;


public class Finder{
    public static String Find(String fileName,String sequence){
        fileName = "data/" + fileName;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while((line = br.readLine()) !=null){
                if(line.contains(sequence)){
                    br.close();
                    return line;
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }
}
