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
        
        int length = Integer.parseInt(br.readLine());
        
        char[] hidden = new char[length];
        boolean guessed = false;
        
        for (int i = 0; i < length; i++) {
            hidden[i] = '_';
        }
        
        System.out.println("LONG: " + String.valueOf(length));
        
        PrintWriter pw = new PrintWriter( new OutputStreamWriter(cl.getOutputStream()));
        
        
        
        String guess = "";
        
        while(length > 0 && !guessed){
            System.out.println("Adivina la palabra: ");
            System.out.println("Número de vidas: " + String.valueOf(length));
            System.out.println(Arrays.toString(hidden) + "\n");
            System.out.println("Ingresa una letra: ");
            char letra = sc.next().charAt(0);
            pw.print(letra);
            
            int answer = Integer.parseInt(br.readLine());
            
            
            
            if(answer == -1){
                length -= 1;
            }else {
                hidden[answer] = letra;
            }
            
            guessed = !Arrays.asList(hidden).contains('_');
            
        }
        
        if(guessed) {
            System.out.println("!Felicidades!");
        }else {
            System.out.println("Suerte para la próxima ):");
        }
        
        pw.print("-1");
        
        
        
        
        
        //Receive letters
        //Validatation loop
        
        br.close();
        pw.close();
        cl.close();
        
        
    }
    
}
