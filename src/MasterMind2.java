package MasterMind;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MasterMind2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean moreGames = true;
        while (moreGames) {
            // Call playMastermind function to start a game
            if (playMastermind(scanner)) {
                System.out.println("Another game (y/n)?");
                String response = scanner.next().toLowerCase();
                moreGames = response.equals("y");
            } else {
                moreGames = false;
            }
        }

        System.out.println("Goodbye!");
    }

    // This method manages the overall game flow.
    public static boolean playMastermind(Scanner scanner) {
        System.out.println("---- Let's play the game of Mastermind. ----");

        int codeword;
        boolean validCodeword;
        do {
            System.out.print("Enter your codeword: ");
            codeword = scanner.nextInt();
            validCodeword = isValidCodeword(codeword);
            if (!validCodeword) {
                System.out.println("Invalid codeword.");
                System.out.print("Another game (y/n)? ");
                String response = scanner.next().toLowerCase();
                if (response.equals("n")) {
                    return false; // End the game
                }
            }
        } while (!validCodeword);

        System.out.print("Enter feedback yourself (y/n)? ");
        boolean userFeedback = scanner.next().toLowerCase().equals("y");

        int round = 1;
        List<Integer> candidates = generateAllPossibleCodewords();

        while (candidates.size() > 1) {
            int guess = getNextGuess(candidates);
            System.out.println("Round = " + round + ", Size = " + candidates.size() + ", Guess = " + guess);

            int feedback;
            if (userFeedback) {
                System.out.print("Enter feedback: ");
                feedback = scanner.nextInt();
            } else {
                feedback = computeFeedback(codeword, guess);
                System.out.println("hits=" + (feedback / 10) + ", misses=" + (feedback % 10));
            }

            candidates = filterCandidates(candidates, guess, feedback);
            round++;

            if (candidates.isEmpty()) {
                System.out.println("Error. No more candidates.");
                break;
            }
        }

        return true; // Continue playing


    }

    // This method checks if the provided codeword is valid.
    public static boolean isValidCodeword(int codeword) {
        // Check if the codeword is a 4-digit integer with digits between 1 and 6
        String codewordStr = String.valueOf(codeword);
        return codewordStr.length() == 4 && codewordStr.matches("[1-6]+");
    }

    // This method generates a list of all possible codewords.
    public static List<Integer> generateAllPossibleCodewords() {
        List<Integer> candidates = new ArrayList<>();
        for (int i = 1111; i <= 6666; i++) {
            if (isValidCodeword(i)) {
                candidates.add(i);
            }
        }
        return candidates;
    }


    // This method selects the next guess from the list of candidates.
    public static int getNextGuess(List<Integer> candidates) {
        // For simplicity, you can choose the first candidate as the guess
        return candidates.get(0);
    }

    // This method computes the feedback for a guess compared to the codeword.
    public static int computeFeedback(int codeword, int guess) {
        int hits = 0;
        int misses = 0;
        String codewordStr = String.valueOf(codeword);
        String guessStr = String.valueOf(guess);

        for (int i = 0; i < 4; i++) {
            if (codewordStr.charAt(i) == guessStr.charAt(i)) {
                hits++;
            }
        }

        for (char digit = '1'; digit <= '6'; digit++) {
            int codewordCount = countOccurrences(codewordStr, digit);
            int guessCount = countOccurrences(guessStr, digit);
            misses += Math.min(codewordCount, guessCount);
        }

        misses -= hits;
        return hits * 10 + misses;
    }

    // This method counts the occurrences of a character in a string.
    public static int countOccurrences(String str, char target) {
        int count = 0;
        for (char c : str.toCharArray()) {
            if (c == target) {
                count++;
            }
        }
        return count;
    }

    // This method filters candidates based on the feedback from a guess.
    public static List<Integer> filterCandidates(List<Integer> candidates, int guess, int feedback) {
        List<Integer> filteredCandidates = new ArrayList<>();
        for (int candidate : candidates) {
            if (computeFeedback(candidate, guess) == feedback) {
                filteredCandidates.add(candidate);
            }
        }
        return filteredCandidates;
    }
}


