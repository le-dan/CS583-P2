public class Node {

  public int ascii;
  public int freq;

  Node left = null;
  Node right = null;

  public Node(int ascii, int freq) {
    this.ascii = ascii;
    this.freq = freq;
  }
}
