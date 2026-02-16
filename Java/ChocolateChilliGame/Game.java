import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Game {
    public static void Rules() { 
        //prints out the rules for the player when they play the game
        
        System.out.println("Chocolate Chilli Game Rules\n");
        System.out.println("1. There is a pile of chocolates on the table.");
        System.out.println("2. Two players take turns");
        System.out.println("3. On your turn, you must eat 1, 2, or 3 chocolates from the pile.");
        System.out.println("4. Players must take at least one chocolate each turn.");
        System.out.println("5. The player who takes the last chocolate wins - the final chocolate contains the hidden chilli surprise!");
        System.out.println("6. The game ends as soon as there are no chocolates left.");
        System.out.println("7. The aim is to avoid taking the last chocolate - so you must try to force your opponent to take it.\n");
        return;
    } 

    public static void Introduction(Scanner scanner, Player player) { 
    // deals with user information and stores it in the record
    
    String difficulty;
    System.out.print("What's your name?\n>");
    player.name = scanner.nextLine(); // name
    do{
        System.out.print("\nChoose difficulty:\n" + "1. Easy (Random)\n" + "2. Medium (Learning AI - your memory system)\n" + "3. Hard (Perfect strategy)\n>");
        difficulty = scanner.nextLine(); // game mode
        if (difficulty.equals( "1") || difficulty.equals("easy")){
            player.gameMode = "Easy"; 
        }else if (difficulty.equals( "2") || difficulty.equals("medium")){
            player.gameMode = "Medium";
        }else if (difficulty.equals( "3") || difficulty.equals("hard")){
            player.gameMode = "Hard";
        }else{
            player.gameMode = scanner.nextLine(); 
        }
    }while(!(player.gameMode.equals("Easy")||player.gameMode.equals("Medium")||player.gameMode.equals("Hard")));
    
    do { 
        try{
        System.out.print("\nHow many chocolates would you like to start with? (Greater than 10 inclusive)\n>");
        player.startingChocolates = Integer.parseInt(scanner.nextLine()); // intial chocolate
        System.out.print("How many games do you want to play?\n>");
        player.totalGames = Integer.parseInt(scanner.nextLine()); // total games
        }catch(NumberFormatException exception){
            System.out.println("Invalid number");
        }
    }while(player.startingChocolates<10 || player.totalGames<=0);
    System.out.println("Thanks, " + player.name + "! There are " + player.startingChocolates + " chocolates on the table. I will go first.\n");
    }

    public static void Player(Scanner scanner, Player player) { 
        // asks player how many chocolates they wish to eat, if they didnt pick the given options it repeats. checks if they won updates remaining chocolates
        
        System.out.println(player.name+"'s turn");
        int pickedChocolates=-1;
        do { 
            try{
                System.out.print(player.name+ " picked : ");
                pickedChocolates = Integer.parseInt(scanner.nextLine()); 
            }catch(NumberFormatException exception){ // makes sure input is integer
                System.out.println("Invalid input");
            }
            if (pickedChocolates>3||pickedChocolates<1){
                System.out.println("You can only eat 1, 2 or 3 chocolates.");
            }
            if (pickedChocolates > player.chocolatesLeft) {
                System.out.println("There are only " + player.chocolatesLeft + " left. Try again\n");
            }
        } while (!(pickedChocolates == 1 || pickedChocolates == 2 || pickedChocolates == 3) || pickedChocolates > player.chocolatesLeft); // makes sure user pick 1, 2 or 3
        player.chocolatesLeft -= pickedChocolates; // subtracts by input
        System.out.println("Chocolates left : " + player.chocolatesLeft + "\n"); // total number of chocolates left
    } 

    public static void Machine(boolean[][] memory, int[] moves, Random random, int index, Player player) { 
        // picks a number (1, 2 or 3) and eats that amount of chocolates. changes as per the dificulty 
        // easy - picks random
        // medium - stores results in txt file to avoid previous mistakes
        // hard - makes the player pick chocolates when number of chocolate left is multiple of 4
        System.out.println("Machine's turn");
        int pickedChocolates;
        if (player.gameMode.equals("Easy")){
            if (player.chocolatesLeft>=3){
                pickedChocolates = random.nextInt(2)+1; // picks random
                player.chocolatesLeft -= pickedChocolates;
            }else{
                pickedChocolates = player.chocolatesLeft;
                player.chocolatesLeft -= pickedChocolates;
            }    
        }else if(player.gameMode.equals("Hard")){
            if (player.chocolatesLeft>3){
                if(player.chocolatesLeft%4==0){
                    pickedChocolates = random.nextInt(2)+1;        
                    player.chocolatesLeft -= pickedChocolates;            
                }else{
                    pickedChocolates = player.chocolatesLeft%4; // picks remainder of chocolatesLeft/4
                    player.chocolatesLeft -= pickedChocolates;
                }
            }else{
                pickedChocolates = player.chocolatesLeft;
                player.chocolatesLeft -= pickedChocolates;
            } 
        }else{
            pickedChocolates = MemoryManager.MemoryGetNum(random, memory, player.chocolatesLeft);
            if (pickedChocolates == 0) { 
                MemoryManager.MemoryRemove(memory, moves, -1, index);
                pickedChocolates = random.nextInt(2)+1; // picks random when all options lead to lose
            }
            if (pickedChocolates > player.chocolatesLeft) {
                MemoryManager.MemoryRemove(memory, moves, pickedChocolates, index); // if number picked exceeds number of chocolate left than removes it from move set
                Machine(memory, moves, random, index, player); // recursion
            }else {
                player.chocolatesLeft -= pickedChocolates; 
            }            
        }
        System.out.println("Machine picked : " + pickedChocolates);
        System.out.println("Chocolates left : " + player.chocolatesLeft + "\n");      
    } 

    public static void GameLoop(Scanner scanner, Random random, Player player) throws IOException{ 
        // prints rules and gets relevent information
        // rotates between player and machine until it finishes all the rounds
        // announces the winner
        
        Rules();
        Introduction(scanner, player);
        player.gamesWon=0;
        player.chocolatesLeft = player.startingChocolates;
        int[] allMoves = new int[player.chocolatesLeft];
        boolean[][] readFile = MemoryManager.readMemory(); // past game memory
        boolean[][] memory = MemoryManager.Memory(player.startingChocolates,readFile); // new memory
        for (int i = 1; i<=player.totalGames; i++){
            int count =0;
            Boolean playerWon=false;
            System.out.println("Game " +i+ "\n");
            do{ 
                Machine(memory, allMoves, random, count, player); // machines turn
                allMoves[count] = player.chocolatesLeft; // stores moves
                count+=1;
                playerWon=false;
                if(player.chocolatesLeft==0){
                    break;
                }
                Player(scanner, player); // players turn
                allMoves[count] = player.chocolatesLeft;
                count+=1;
                playerWon = true;
            } while (!(player.chocolatesLeft<=0)); // repeats until there is no more chocolates left
            player.chocolatesLeft = player.startingChocolates;
            if (playerWon){
                player.gamesWon+=1;
                MemoryManager.MemoryRemove(memory, allMoves, -1, count-1); // removes last move if player won
            }                                           
        }
        Result(player); // announce winner
        System.out.println("Thanks for playing!");
        MemoryManager.writeMemory(memory, readFile); // update memory
        return;
    } 

    public static void Result(Player player) { 
        //checks weather who has got more wins, it than says the score and announces results

        int playerScore = player.gamesWon; 
        int machineScore = player.totalGames-player.gamesWon; 
        System.out.println("Scores");
        System.out.println(player.name + ": " + playerScore); // player score
        System.out.println("Machine: " + machineScore + "\n"); // machine score
        if (playerScore>machineScore) { 
            System.out.println("Winner is " +player.name);
        }else if (playerScore<machineScore) { 
            System.out.println( "Winner is Machine");
        }else {
            System.out.println("It's a draw!"); 
        }
        return;
    } 
    
}
