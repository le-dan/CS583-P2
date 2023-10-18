import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * huffmancode
 */
public class Huffmancode {

    public static void main(String[] args) {
        int[] freqTable = new int[96];
        try (Scanner sc = new Scanner(new File("sherlockholmes.txt"))) {
            while (sc.hasNext()) {
                String word = sc.next();
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) - 32 > 96) {
                        continue;
                    }
                    freqTable[word.charAt(i) - 32]++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int count = 0;
        for (int freq : freqTable) {
            System.out.println(Character.toString(count + 32) + ": " + freq);
            count++;
        }
    }
}