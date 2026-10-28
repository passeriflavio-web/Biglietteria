package com.example;

import java.io.*;
import java.net.*;
import java.util.*;


public class Main {
    public static void main(String[] args) throws IOException {
        
        List<String> users = new ArrayList<String>();
        
        List<Integer> avaiable = new ArrayList<Integer>();
        avaiable.add(15); 
        avaiable.add(25);
        avaiable.add(5);

        ServerSocket ss = new ServerSocket(3000);
        Socket mSocekt =ss.accept();

        BufferedReader in = new BufferedReader(new InputStreamReader(mSocekt.getInputStream()));
        PrintWriter out = new PrintWriter(mSocekt.getOutputStream(), true);

        out.println("WELCOME");
        String loginUsername;
        do{
            loginUsername = in.readLine();
            String[] parts = loginUsername.split(" ");
            if(parts.length == 2 && parts[0].equalsIgnoreCase("login")){
                if(!users.contains(parts[1])){
                    users.add(parts[1]);
                    break;
                }
            out.println("ERR USERINUSE");
            }
            out.println("ERR UNKNOWNCMD");
        }while(true);
        
        out.println("OK");

        boolean quit = false;
        do{
            
            String clientCommand = in.readLine();
            switch (clientCommand.toLowerCase()) {
                case "BUY":
                    String ticektChoice = in.readLine();
                    String[] choiceParts = ticektChoice.split(" ",2);
                    
                    int qnt = Integer.parseInt(choiceParts[1]);          
                    int var;
                    switch (choiceParts[0].toLowerCase()) {
                        case "gold":
                            var=avaiable.get(0);
                            if(var >= qnt){
                                var-= qnt;
                                avaiable.set(0, var);
                                out.println("OK");
                            }else{
                                out.println("KO");
                            }
                            break;
                        case "pit":
                            var=avaiable.get(1);
                            if(var >= qnt){
                                var-= qnt;
                                avaiable.set(1, var);
                                out.println("OK");
                            }else{
                                out.println("KO");
                            }
                            break;
                        case "parterre":
                            var=avaiable.get(1);
                            if(var >= qnt){
                                var-= qnt;
                                avaiable.set(1, var);
                                out.println("OK");
                            }else{
                                out.println("KO");
                            }
                            break;
                        default:
                            out.println("ERR SYNTAX");
                            break;
                    }
                    break;
                case "N":
                    out.println("AVAIL Gold:" + avaiable.get(0) + " Pit:" + avaiable.get(1)+ " Parterre:" + avaiable.get(2));
                    break;
                case "QUIT":
                    out.println("BYE");
                    quit=true;
                    break;
                default:
                    out.println("ERR UNKNOWNCMD");
                    break;
            }
        }while(!quit);
    }
}