import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/* Comparator for priority queue to sort priority based on frequency */
class HuffmanComparator implements Comparator<Node> {

  public int compare(Node x, Node y) {
    return x.freq - y.freq;
  }
}

/**
 * huffmancode
 */
public class Huffmancode {

  public static void main(String[] args) {
    ArrayList<Integer> freqTable = genFreqTable(new File("sherlockholmes.txt"));
    HashMap<Character, String> huffmanMap = new HashMap<>();

    mapHuffmanCodes(runHuffman(freqTable), "", huffmanMap);

    printFreqTable(freqTable);
    printHuffmanTable(huffmanMap);

    int totalFixedBits = calcFixedBits(freqTable);
    int totalHuffmanbits = calcHuffmanBits(huffmanMap, freqTable);

    System.out.println(
      "\nFixed Length Encoding 7: " + totalFixedBits + " bits"
    );
    System.out.println("Huffman Encoding: " + totalHuffmanbits + " bits");
    System.out.println(
      "\nSaved " +
      (totalFixedBits - totalHuffmanbits) +
      " bits using Huffman Encoding!\n"
    );
  }

  /* Puts key, value pairs into hash map with ascii character and encoded string respectively */
  public static void mapHuffmanCodes(
    Node node,
    String s,
    HashMap<Character, String> map
  ) {
    if (node.left == null && node.right == null && node.ascii != 1) {
      map.put((char) node.ascii, s);
      return;
    }

    mapHuffmanCodes(node.left, s + "0", map);
    mapHuffmanCodes(node.right, s + "1", map);
  }

  /* Huffman Encoding Algorithm */
  public static Node runHuffman(ArrayList<Integer> freqTable) {
    PriorityQueue<Node> queue = new PriorityQueue<>(
      freqTable.size(),
      new HuffmanComparator()
    );
    for (int i = 0; i < freqTable.size(); i++) {
      queue.add(new Node(i + 32, freqTable.get(i)));
    }

    while (queue.size() > 1) {
      Node x = queue.poll();
      Node y = queue.poll();
      Node z = new Node(1, x.freq + y.freq);
      z.left = x;
      z.right = y;
      queue.add(z);
    }

    return queue.poll();
  }

  /* Generates frequency table of each ascii character */
  public static ArrayList<Integer> genFreqTable(File file) {
    ArrayList<Integer> res = new ArrayList<>();

    // Set default count values to 0 for each array list
    for (int i = 0; i < 96; i++) {
      res.add(i, 0);
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      int asciiValue;
      while ((asciiValue = reader.read()) != -1) {
        if (asciiValue < 32 || asciiValue > 128) {
          continue;
        }
        res.set(asciiValue - 32, res.get(asciiValue - 32) + 1);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return res;
  }

  // Find total number of bits for fixed length encoding of 7
  public static int calcFixedBits(ArrayList<Integer> freqTable) {
    int totalFixedBits = 0;
    for (Integer freq : freqTable) {
      totalFixedBits += freq;
    }
    totalFixedBits *= 7;
    return totalFixedBits;
  }

  // Find total number of bits for huffman encoding
  public static int calcHuffmanBits(
    HashMap<Character, String> map,
    ArrayList<Integer> freqTable
  ) {
    int totalHuffmanBits = 0;
    for (int i = 0; i < freqTable.size(); i++) {
      totalHuffmanBits += map.get((char) (i + 32)).length() * freqTable.get(i);
    }
    return totalHuffmanBits;
  }

  public static void printHuffmanTable(HashMap<Character, String> huffmanMap) {
    System.out.println("\nHuffman Encoding List:\n");

    for (Character ch : huffmanMap.keySet()) {
      System.out.println(ch + ": " + huffmanMap.get(ch));
    }
  }

  public static void printFreqTable(ArrayList<Integer> table) {
    System.out.println("\nASCII Character Frequency List:\n");

    for (int i = 0; i < table.size(); i++) {
      System.out.println((char) (i + 32) + ": " + table.get(i));
    }
  }
}
