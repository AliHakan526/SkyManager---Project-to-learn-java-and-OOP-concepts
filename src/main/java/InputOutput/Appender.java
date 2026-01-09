package main.java.InputOutput;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Appender {
    public static void Append(String fileName,String text){
        String path = "data/" + fileName;
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path,true));
            bufferedWriter.write(text);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
