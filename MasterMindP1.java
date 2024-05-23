package MasterMind;
import java.util.*;
public class MasterMindP1 {


    //method for determining hits and misses
    public static int HitsAndMisses(String codeWord, String guess){
        int hits=0;
        int misses=0;

        //for loop that inspects guess and checks for hits
        //if number at char(i) in guess is the same as code word add hit
        for (int i=0; i< guess.length(); i++ ) {

            if (guess.charAt(i) == codeWord.charAt(i)) {
                hits++;
            }
        }
        // arrays count the occurrence of each digit in codeword and guess
        int[] codeWordCount= new int[7];
        int[] guessCount = new int[7];

        for (int i =0; i< codeWord.length(); i++){
            codeWordCount[codeWord.charAt(i) - '0']++;
            guessCount[guess.charAt(i) - '0']++;
        }

        //for loop that finds the number of misses
        // looks for correct digits in wrong places
        for (int i = 1; i <= 6; i++) {
            misses += Math.min(codeWordCount[i], guessCount[i]);
        }

        misses -= hits;
        System.out.println(hits + " hits, " + misses + " misses");
        return hits * 10 + misses;
    }
    //end of method that counts hits and misses



    //method for inputting guess
    public static String InputGuess() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Guess the code word!");
        return keyboard.next();
    }
    //end of method for inputting guess


    //method that ensures inputted guess is valid
    // takes the guess from input guess method and makes sure it follows the rules of the game
    public static boolean ValidGuess(String guess) {

        //check guess length
        if (guess.length() !=4){
            System.out.println("Your guess is invalid, try again.");
            return false;
        }

        // check guess and makes sure each char is between 1 and 6
        for (int i=0; i< guess.length(); i++ ){
            char digitChar= guess.charAt(i);
            int digit= Character.getNumericValue(digitChar);

            if (digit < 1 || digit > 6){
                System.out.print("Keep all the digits in your guess between 1 and 6. Try again.");
                return false;
            }
        }
        return true;
    }      // end of method that validates guess


    //method that generates a random codeword
    public static String GenerateCodeWord(){
        Random random= new Random();
        StringBuilder codeWord= new StringBuilder();

        // for loop that repeats four times and selects four random number between 1 and 6
        for (int i= 0; i< 4; i++){
            int randomDigit = random.nextInt(6) + 1;
            codeWord.append(randomDigit);
        }
        return codeWord.toString();

    }
    // end of method for generating random code word


    // main method
    public static void main(String[]args){
        Scanner keyboard= new Scanner(System.in);
        Random random= new Random();
        boolean moreGames= true;

        //while boolean is true, start game, print instructions
        while (moreGames){
            String codeWord= GenerateCodeWord();
            System.out.println("---- Let 's play the game of Mastermind. ----");
            System.out.println("Your guess must be a four-digit integer.");
            System.out.println("All digits must be in between 1 through 6.");

            // ask user if they want to see the code word
            System.out.print("Reveal the codeword (y/n)? ");
            String the_answer=keyboard.next();

            // if y display the codeword
            if (the_answer.equalsIgnoreCase("y")) {
                System.out.println("The codeword is " + codeWord + ".");
            }

            // if n do not show word
            else if (the_answer.equalsIgnoreCase("n")){
            }

            //if the user selects anything other than y or n try again
            else {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
                continue;
            }
            //starts at round 1, user guess the code word
            // if the user enters zero or guess correct the while loop stops
            int round= 1;
            while (true){
                String guess= InputGuess();
                System.out.println("Round: "+ round + ", your guess (0 to stop): "+ guess);

                if (guess.equals("0")){
                    System.out.println("The codeword was "+ codeWord + ".");
                    break;
                }
                if (!ValidGuess(guess)){
                    continue;
                }
                int feedBack= HitsAndMisses(codeWord, guess);

                if (feedBack== 40){
                    System.out.println("You've got it!");
                    break;
                }
                round++;
            }
            // ask user if they want to play another game
            System.out.print("Another game (y/n)?");
            String playAgain= keyboard.next();
            // connect their answer with the boolean more games and restart the while loop
            moreGames=playAgain.equalsIgnoreCase("y");


        }
        System.out.print("Thanks for playing!");

    }

}
