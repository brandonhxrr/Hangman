package escom.ipn.hangman;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.StandardSocketOptions;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

  private static Socket socket;

  public static void main(String[] args) {
      
      getRequests(getServerSocket(1234));
 
  }
  
  private static ServerSocket getServerSocket(int port) {
      
      ServerSocket s = null;
      
      try {
          s = new ServerSocket(1234);
          s.setOption(StandardSocketOptions.SO_REUSEADDR, true);
          System.out.println("Servidor iniciado...");
      } catch (IOException ex) {
          Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      return s;
  }
  
  private static void getRequests(ServerSocket s) {
      for (;;) {
          try {
              String[] words = {"animal", "perro", "gato", "vaca", "cerdo", "pecho", "cintura", "cadera"};
              
              int x = (int)(Math.random()*(words.length)+1);
              
              String word = words[x];
              Socket cl = s.accept();
              
              PrintWriter pw = new PrintWriter( new OutputStreamWriter(cl.getOutputStream()));
              pw.println(word.length()); // Send the String length
              //Receive letters
              
              boolean end = false;
              
              BufferedReader br = new BufferedReader(new InputStreamReader(cl.getInputStream()));
              
              while(!end) {
                  String letra = br.readLine();
                  System.out.println("RECV: " + letra);
                  
                  if(letra == "-1"){
                      end = true;
                  }else {
                      pw.print(word.indexOf(letra));
                  }
              }
              
              br.close();
              //Validatation loop
              pw.close();
              cl.close();
              
          } catch (IOException ex) {
              Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
  }
  

  
}