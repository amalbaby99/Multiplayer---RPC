package twoPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static PrintWriter outclient1;
    static BufferedReader inclient1;
    static PrintWriter outclient2;
    static BufferedReader inclient2;
    static Socket client_1;
    static Socket client_2;

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(5000);
        System.out.println("Server is now running....");
        
        int player_1_score = 0;
        int player_2_score = 0;
                
        String inputClient_1;
        String inputClient_2;
        
        while (!ss.isClosed()) {
            
            //Connecting Player one
            
            client_1 = ss.accept();
            if (client_1.isConnected()) {
                //initializing the communnication links
                outclient1 = new PrintWriter(client_1.getOutputStream());
                inclient1 = new BufferedReader(new InputStreamReader(client_1.getInputStream()));
                //Reading the Player name sent from client
                String player1Name = inclient1.readLine();
                System.out.println(player1Name+" Connected");
            }
            //Connnecting player two
            client_2 = ss.accept();
            if (client_2.isConnected()) {
                //initializing the communnication links
                outclient2 = new PrintWriter(client_2.getOutputStream());
                inclient2 = new BufferedReader(new InputStreamReader(client_2.getInputStream()));
                //Reading the Player name sent from client
                String player2Name = inclient2.readLine();
                System.out.println(player2Name+" Connected");
            }
            
            
            //Checking the inputs for a best of three
            for(int i = 0; i<3; i++){
            inputClient_1 = inclient1.readLine().toLowerCase();
            inputClient_2 = inclient2.readLine().toLowerCase();
            
            //Game Logic
            if(inputClient_1.matches("rock")&inputClient_2.matches("scissors") | inputClient_1.matches("scissors") & inputClient_2.matches("paper") | inputClient_1.matches("paper")& inputClient_2.matches("rock")){
                outclient1.println("You win");
                player_1_score+= 1;
                outclient2.println("You Loose");
                outclient1.flush();
                outclient2.flush();
            }else if(inputClient_1.matches(inputClient_2)){
                outclient1.println("Draw");
                outclient2.println("Draw");
                outclient1.flush();
                outclient2.flush();
            }else{
                outclient2.println("You win");
                player_2_score += 1;
                outclient1.println("You Loose");
                outclient1.flush();
                outclient2.flush();
            }
                
            

            //Echoing to each client for testing
            /*
            outclient1.println(inputClient_2);
            outclient2.println(inputClient_1);
            outclient1.flush();
            outclient2.flush();*/

            System.out.println(inputClient_1);
            System.out.println(inputClient_2);
            }
            
            //letting each player know their final score after 3 games
            if(player_1_score > player_2_score){
                outclient1.println("You win, Final Score is: "+player_1_score);
                outclient2.println("You loose, Final Score is: "+player_2_score);
                outclient1.flush();
                outclient2.flush();
            }else{
                outclient2.println("You win, Final Score is: "+player_2_score);
                outclient1.println("You loose, Final Score is: "+player_1_score);
                outclient1.flush();
                outclient2.flush();
            }
            client_1.close();
            client_2.close();
            }
            
        

    }
    
}
