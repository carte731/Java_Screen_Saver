/*
Corey Carter
carte731@umn.edu
ID: 5026487
CSCI_1933, Sec-001
Project 2:Object-oriented geometry and graphics - MAIN class and operations
*/

import java.awt.*;
import java.util.Scanner;

public class Main{

    private int choosingNum; //Creates private variables, need methods to change or use.
    private int ballAmount;
    public int balls;

    public int Starter(){
        Scanner start = new Scanner(System.in); //used for number input, User can shoose how many balls they want in play
        Scanner finish = new Scanner(System.in); //used for String input
        System.out.println("\nHow many balls would you like to display? Please choose any number between 1 and 100 (or type quit(q) to quit): ");
        try{ //checks if the input is a number
            choosingNum = start.nextInt(); //looks for int input
        }catch(java.util.InputMismatchException e){ //if input isn't number, then excute String input
            System.out.println("\nDid you want to quit? (type yes(y) or no(n).\n");
            String stringKeys = finish.nextLine();
            stringKeys = stringKeys.toLowerCase();
            if(stringKeys.equals("quit") || stringKeys.equals("q") || stringKeys.equals("yes") || stringKeys.equals("y")){
                System.out.println("\nThank you for using my app.");
                System.out.println("CLOSING...\n");
                System.exit(0); //quits if user decided to quit
            }else if(stringKeys.equals("no") || stringKeys.equals("n")){
                System.out.println("RESTARTING...\n");
                this.Starter(); //recursively restart if they dont want to quit
            }else{
                System.out.println("\nPlease enter a valid answer."); //outputs this if they input a invalid answer
                System.out.println("RESTARTING...\n");
                this.Starter(); 
            }
        }
        if((choosingNum >= 1) && (choosingNum <= 100)){ //if user select ball amount between 1 to 100
            balls = choosingNum;
        }else if((choosingNum < 1) || (choosingNum > 100)){ //out of bounds it restarts
            System.out.println("\nPlease enter a valid number.");
            System.out.println("RESTARTING...\n");
            this.Starter();
        }
        return(balls); //outputs ball amounts
    }

    public static void main(String[] args){
        Main bSS = new Main(); //creates Main instance allowing for recursive restarting of Main class
        int ballAmount = bSS.Starter(); //outouts user ball amount
        System.out.println("\t\nTap or hold these keys for:\nLEFT Key: Slow-down\nRIGHT Key: Speed-up\nUP Key: efault speed  reset\nQ key: Quit\n");
		BallScreenSaver bb= new BallScreenSaver(800,800,"Corey Carter", ballAmount); //Creates BallScreenSaver instance
		bb.start(); //starts BallScreenSaver instance
	}

}