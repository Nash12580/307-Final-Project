package org.example;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**@author Nashali Vicente Lopez**/

public class CodeMetrics {
    public static int countTotalLines(String filePath) throws IOException{
        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            int totalLines = 0;
            while(reader.readLine() != null){
                totalLines++;
            }
            return totalLines;
        }
    }
    public static int countLOC(String filePath) throws IOException{
        try(BufferedReader reader = new BufferedReader((new FileReader(filePath)))){
            int loc = 0;
            String line;
            boolean insideClass = false;
            while((line = reader.readLine()) != null){
                line = line.trim();
                if(line.startsWith("class ") || line.startsWith("public class ") || line.startsWith("private class ") || line.startsWith("public interface ")){
                    insideClass = true;
                }
                if(insideClass){
                    loc++;
                }
            }
            return loc;
        }
    }

    public static int counteLOC(String filePath) throws IOException{
        CompilationUnit cu = parseFile(filePath);
        if(cu == null) return 0;
        Visitor visitor = new Visitor();
        visitor.visit(cu, null);
        return visitor.geteLOC();
    }

    public static int countlLOC(String filePath) throws IOException{
        CompilationUnit cu = parseFile(filePath);
        if(cu == null) return 0;
        Visitor visitor = new Visitor();
        visitor.visit(cu, null);
        return visitor.getLLOC();
    }

    private static  CompilationUnit parseFile(String filePath) throws IOException{
        try(FileInputStream in = new FileInputStream(filePath)){
            JavaParser parser = new JavaParser();
            return parser.parse(in).getResult().orElse(null);
        }
    }

    private static List<String> listJavaFiles(String directoryPath){
        List<String> javaFiles = new ArrayList<>();
        File directory = new File(directoryPath);
        if(directory.isDirectory()){
            for(File file: directory.listFiles()){
                if (file.isFile() && file.getName().endsWith(".java")){
                    javaFiles.add(file.getAbsolutePath());
                }else if(file.isDirectory()){
                    javaFiles.addAll(listJavaFiles(file.getAbsolutePath()));
                }
            }
        }
        return javaFiles;
    }
    public static double calculateAbsractness(String filePath) throws IOException{
        CompilationUnit cu = parseFile(filePath);
        if(cu == null) return 0.0;
        Visitor visitor = new Visitor();
        visitor.visit(cu, null);
        int totalCandI = visitor.getTotalCI();
        int abstractCandI = visitor.getAbstractCI();

        if(totalCandI == 0) return 0.0;
        return (double) abstractCandI/totalCandI;
    }
}

