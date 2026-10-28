package com.example;

import java.io.*;
import java.net.*;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException {
        
        List<String> users = new ArrayList<String>();
        
        List<Integer> avaiable = new ArrayList<Integer>();
        avaiable.add(15); 
        avaiable.add(35);
        avaiable.add(80);

        try (ServerSocket ss = new ServerSocket(3000)) {
            do{
            Socket mSocekt =ss.accept();
            Thread ClientThread = new Thread(new ThreadBiglietteria(mSocekt,users,avaiable));
            
            ClientThread.start();
            }while(true);
        } catch(IOException e){
            System.err.println("Errore nel server: " + e.getMessage());
        }
        
    }
}