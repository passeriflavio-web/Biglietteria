package com.example;

import java.io.*;
import java.net.*;
import java.util.*;

public class ThreadBiglietteria implements Runnable {
    private Socket mSocket;
    private List<String> users = new ArrayList<String>();
    private List<Integer> avaiable = new ArrayList<Integer>();

    public ThreadBiglietteria(Socket socket, List<String> users, List<Integer> avaiable) {
        this.mSocket = socket;
        this.users = users;
        this.avaiable = avaiable;
    }
    
    @Override
    public synchronized void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
            PrintWriter out = new PrintWriter(mSocket.getOutputStream(), true)
        ) {
            out.println("WELCOME");
            String loginUsername;
            
            // FASE DI LOGIN
            while (true) {
                loginUsername = in.readLine();
                
                String[] parts = loginUsername.split(" ");
                if (parts.length == 2 && parts[0].equalsIgnoreCase("login")) {
                    if (!users.contains(parts[1])) {
                        users.add(parts[1]);
                        break;
                    }
                    out.println("ERR USERINUSE");
                } else {
                    out.println("ERR UNKNOWNCMD");
                }
            }
            
            out.println("OK");
            // FASE DI ESECUZIONE
            boolean quit = false;
            while (!quit) {
                String clientCommand = in.readLine();
                switch (clientCommand.toUpperCase()) {
                    case "BUY":
                        String ticketChoice = in.readLine();
                        
                        String[] choiceParts = ticketChoice.split(" ", 2);
                        if (choiceParts.length < 2) {
                            out.println("ERR SYNTAX");
                            break;
                        }
                        try {
                            int qnt = Integer.parseInt(choiceParts[1]);          
                            int var;
                            
                            switch (choiceParts[0].toLowerCase()) {
                                case "gold":
                                    var = avaiable.get(0);
                                    if (var >= qnt) {
                                        var -= qnt;
                                        avaiable.set(0, var);
                                        out.println("OK");
                                    } else {
                                        out.println("KO");
                                    }
                                    break;
                                case "pit":
                                    var = avaiable.get(1);
                                    if (var >= qnt) {
                                        var -= qnt;
                                        avaiable.set(1, var);
                                        out.println("OK");
                                    } else {
                                        out.println("KO");
                                    }
                                    break;
                                case "parterre":
                                    var = avaiable.get(2);
                                    if (var >= qnt) {
                                        var -= qnt;
                                        avaiable.set(2, var);
                                        out.println("OK");
                                    } else {
                                        out.println("KO");
                                    }
                                    break;
                                default:
                                    out.println("ERR SYNTAX");
                                    break;
                            }
                        } catch (NumberFormatException e) {
                            
                            out.println("ERR SYNTAX");
                        } catch (IndexOutOfBoundsException e) {
                            
                            out.println("ERR UNKNOWNTYPE");
                        }
                        break;
                        
                    case "N":
                            out.println("AVAIL Gold:" + avaiable.get(0) + " Pit:" + avaiable.get(1) + " Parterre:" + avaiable.get(2));
                        break;
                        
                    case "QUIT":
                        out.println("BYE");
                        quit = true;
                        break;
                        
                    default:
                        out.println("ERR UNKNOWNCMD");
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Errore di comunicazione con il client: " + e.getMessage());
        } finally {
            // Chiude il socket in ogni caso 
            try {
                if (mSocket != null && !mSocket.isClosed()) {
                    mSocket.close();
                }
            } catch (IOException e) {
                System.err.println("Impossibile chiudere il socket: " + e.getMessage());
            }
        }
    }
}

