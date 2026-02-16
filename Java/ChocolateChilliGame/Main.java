import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main(String args[])throws IOException{
        //initialises all objects and passes them into gameLoop method
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        Player player = new Player();
        Game.GameLoop(scanner, random, player);
        scanner.close();
    }
}