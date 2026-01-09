package main.java.InputOutput;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Writer {
    public static void Write(String fileName,String text){
        String path = "data/" + fileName;
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path,false));
            bufferedWriter.write(text);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
