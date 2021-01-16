package twoPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);
        Socket clientSocket;
        try {
            //Connecting to server on port 5000
            clientSocket = new Socket("localhost", 5000);
            //Creating communication links
            PrintWriter outToServer = new PrintWriter(clientSocket.getOutputStream());
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //Getting Player's Name  and sending to server to let the server know who is connected
            System.out.println("Enter Player Name: ");
            String player_name = input.nextLine();
            outToServer.println(player_name);
            outToServer.flush();
            
            //Playing a best fo 3 game
            int Score = 0;
            for(int i =0; i<3; i++){
            System.out.println("Rock Paper Scissors");
            String input_Player = input.nextLine();

            outToServer.println(input_Player);
            outToServer.flush();
            String From_Server = fromServer.readLine();
            if(From_Server.toLowerCase().matches("you win")){
                Score +=1;
            }
            
            System.out.println(From_Server);
            System.out.println("Current Score = "+Score);
            }
            
            //Recieving Final Scores after a game of 3
            String final_score = fromServer.readLine();
            System.out.println("Final Score: "+final_score);
            clientSocket.close();
            
        } catch (IOException ex) {
            System.out.println("IO Exception");
        }
    }
}
