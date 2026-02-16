import java.io.*;
import java.util.Random;

public class MemoryManager {
    public static boolean[][] Memory(int totalChocolate, boolean[][] readMemory) { 
        // updates new memory based on past games if last game had few starting chocolates than this round than extends memory, if fewer than only selects part 


        boolean[][] memory = new boolean[totalChocolate][3]; 
        if (totalChocolate<=readMemory.length){ // fewer starting chocolates selected than past
            for (int i = 0; i < totalChocolate; i++){ // reads part of memory
                for (int j = 0; j < 3; j++){ 
                    memory[i][j] = readMemory[i][j];
                }
            }
        } else { // exceed starting chocolates than past round
            for (int i = 0; i < readMemory.length; i++){ // reads past memory
                for (int j = 0; j < 3; j++){
                    memory[i][j] = readMemory[i][j];
                }
            }
            for (int i = readMemory.length; i < totalChocolate; i++){ // extends memory
                for (int j = 0; j < 3; j++){
                    memory[i][j] = true;
                }
            }
        }
        return memory;
    } 

    public static void MemoryRemove(boolean[][] memory, int[] moves, int chocolatePicked, int index) { 
        // removes all the bad moves from memory

        if (!(chocolatePicked ==-1)){ // if there are no options for chocolate left
            memory[moves[index-2]-1][chocolatePicked-1] = false; // changes last move if player won
            if (CheckMemory(memory,chocolatePicked)[0]==0 && CheckMemory(memory,chocolatePicked)[1]==0 && CheckMemory(memory,chocolatePicked)[2]==0){ // checks if all moves are false
                EditMemory(memory,moves,index); // changes the previous move 
            }
        }else{                                                          
            memory[moves[index-2]-1][(moves[index-2]-moves[index-1])-1] = false; // changes for bad moves eg if chocolates picked exceeds chocolates left
            if (CheckMemory(memory,moves[index-2]-moves[index-1])[0]==0 && CheckMemory(memory,moves[index-2]-moves[index-1])[1]==0 && CheckMemory(memory,moves[index-2]-moves[index-1])[2]==0){ // checks if all of it is false
                EditMemory(memory,moves,index); // changes the previous move 
            }
        }
        return;
    } 
    
    public static int MemoryGetNum(Random random, boolean[][] memory, int chocolatesLeft) { 
        // gets a number from memory that is available 

        int[] listNum = CheckMemory(memory, chocolatesLeft); // list of all available choices
        int values=0;
        int number=0;
        for (int i=0; i<3; i++){
            if (listNum[i]!=0){
                values+=1;  
                number = listNum[i];
            }
        }
        if (values > 1){
            int pickedChocolates = listNum[random.nextInt(values-1)]; // randomly picks one of the availabe options
            return pickedChocolates;
        }else if (values == 1){
           return number; // picks the only number which is available
        }else{
            return 0; // no valid move in memory
        }
    } 

    public static int[] CheckMemory(boolean[][] memory, int chocolatesLeft){
        // checks all the availble options for that amount of chocolates left

        int[] listNum = new int[3];
        int index=0;
        for (int i = 0; i<3; i++){ 
            if (memory[chocolatesLeft-1][i]){ // checks how many options are available 
                listNum[index]=i+1; // adds the options to a array
                index+=1;
            }
        }
        return listNum;
    }

    public static void EditMemory(boolean[][] memory, int[] moves, int index) { 
        // changes previous move when there are no available options
        
        for (int i = index; i >= 0; i-=2){ // finds the position of the empty (all false) inner array
            if (!(CheckMemory(memory, moves[i])[0]==0 && CheckMemory(memory, moves[i])[1]==0 && CheckMemory(memory, moves[i])[2]==0)){
                memory[moves[i]-1][moves[i]-moves[i+1]-1] = false; // changes second last machine move
                break;
            }
        }
        return;
    } 

    public static void writeMemory(boolean[][] memory, boolean[][] readMemory) throws IOException{
        // overwrites the memory from the text file to the updated version 

        String data="";
        PrintWriter writeFile = new PrintWriter(new FileWriter("Memory.txt"));
        if (memory.length >= readMemory.length){ // game played picked more or equal chocolate than past games
            for (int i = 0; i<memory.length;i++){
                data = data +"[";
                for (int j = 0; j<3;j++){
                    data = data + memory[i][j];
                    data = data +" ";
                }
                data = data + "] "; // writing in the file in this formet [...] [...]
            }
            
        }else{ // this game played picked less chocolate than past games
            for (int i = 0; i < memory.length; i++){ 
                for (int j = 0; j < 3; j++){
                    readMemory[i][j] = memory[i][j];
                }
            }
            data = data + "[";
            for (int i = 0; i<readMemory.length;i++){
                for (int j = 0; j<3;j++){
                    data = data + readMemory[i][j];
                    data = data +" ";
                }
                data = data + "] "; // writing in the file in this formet [...] [...]
            }
        }
        writeFile.println(data); // writing the string with data into memory
        writeFile.close();
    }
    
    public static boolean[][] readMemory() throws IOException {
        // reads from the memory and stores data in a 2d array
        
        File file = new File("Memory.txt");
        int k=0;
        if (!(file.exists())) { // checks if file exists
            PrintWriter pw = new PrintWriter(new FileWriter(file)); // makes new file
            pw.println(""); // writes in the file so not empty
            pw.close();
            return new boolean[0][3]; 
        }
        BufferedReader readFile = new BufferedReader(new FileReader("Memory.txt"));// opens file
        String text= readFile.readLine();
        if (text == null || text.isEmpty()) { // checks if file is empty 
            readFile.close();
            return new boolean[0][3]; // retuns a 2d array with inner size 3
        }
        text= text.replace("[", ""); 
        text= text.replace("] ", ""); // makes the array go from [...] [...] [...] to [......] 
        String[] mem = text.split(" "); // makes it into a functioning array with commas [., ., ., .]
        boolean[][] memory = new boolean[mem.length/3][3];
        for (int i = 0; i<mem.length/3; i++){
            for (int j = 0; j<3; j++){
                memory[i][j] = Boolean.parseBoolean(mem[k]); // writes all the options to the 2d array
                k++;
            }
        }
        readFile.close();
        return memory;
    }
} 