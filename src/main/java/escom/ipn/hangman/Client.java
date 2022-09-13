package escom.ipn.hangman;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class Client {
    
    static Scanner sc = new Scanner(System.in);
    
    public static void main (String[] args){
        try{
            establecerConexion();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    
    public static void establecerConexion() throws IOException{
        int pto = 1234;
        String dir = "127.0.0.1";
        Socket cl = new Socket(dir,pto);
        System.out.println("Conexion con servidor establecida...");
        
        BufferedReader br = new BufferedReader(new InputStreamReader(cl.getInputStream()));
        
        String word =br.readLine();
        
        char[] hidden = new char[word.length()];
        boolean guessed = false;
        int attemps = word.length();
        
        for (int i = 0; i < word.length(); i++) {
            hidden[i] = '_';
        }
        
        String guess = "";
        
        boolean found;
        int charFound = 0;        
        
        while(attemps > 0 && charFound != word.length()){
            found = false;
            
            System.out.println("\nAdivina la palabra: ");
            System.out.println("Número de vidas: " + String.valueOf(attemps));
            System.out.println(Arrays.toString(hidden) + "\n");
            System.out.print("Ingresa una letra: ");
            char letra = sc.next().charAt(0);
            
            for (int i = 0; i < hidden.length; i++) {
                if(letra == word.charAt(i)) {
                    hidden[i] = letra;
                    found = true;
                    charFound += 1;
                }                
            }
            
            if(!found) attemps -= 1;            
            
        }
        
        if(charFound == word.length()) {
            System.out.println("\n!Felicidades!");
            System.out.println(Arrays.toString(hidden) + "\n");
        }else {
            System.out.println("Suerte para la próxima ):");
        }
              
        br.close();
        cl.close();
        
        
    }
    
}
