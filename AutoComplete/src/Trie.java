import java.util.ArrayList;
import java.util.HashSet;

public class Trie {
    private Node root = new Node(' ', false);
    private ArrayList<String> dictionary = new ArrayList<>();
    private class Node {
        private char letter;
        private boolean isKey;
        public HashSet<Node> branches = new HashSet<>();
        private Node(char c, boolean k) {
            letter = c;
            isKey = k;
        }

        @Override
        public boolean equals(Object anObject) {
            if (anObject instanceof Node b) {
                return (this.letter == b.letter);
            }
            return false;
        }
    }
    public Trie() {
    }
    private Node getNode(char c, Node t) {
        for (Node n: t.branches) {
            if (n.letter == c) {
                return n;
            }
        }
        return null;
    }
    public void put(String s) {
        put(s, root, 0);
    }
    private void put(String s, Node t, int index) {
        if (index == s.length()) {
            return;
        }
//        if (getNode(s.charAt(index), t) != null) {
//            if (index == s.length() - 1) {
//                System.out.println("true");
//                t.branches.add(new Node(s.charAt(index), true));        /** FIXME: This line is not working */
//            }
        Node temp = getNode(s.charAt(index), t);
        if (temp != null) {
            if (index == s.length() - 1) {
                temp.isKey = true;
            }
        } else {
            t.branches.add(new Node(s.charAt(index), (index == s.length() - 1)));
        }
        Node n = getNode(s.charAt(index), t);
        put(s, n, index + 1);
    }
    public ArrayList<String> get() {
        return get("");
    }
    public ArrayList<String> get(String prefix) {
        return get(root, prefix, "", new ArrayList<>());

    }
    private ArrayList<String> get(Node t, String prefix, String word, ArrayList<String> words) {
        if (prefix.length() > 0) {
            word += prefix;         /** Comment to get only suffix of prefix */
            while (prefix.length() > 0) {
                t = getNode(prefix.charAt(0), t);
                prefix = prefix.substring(1);
            }
            if (t.isKey) {
                words.add(word);
            }
        }
        System.out.println(t.letter + " " + t.isKey);

        for (Node n: t.branches) {
            //System.out.print(n.letter + " ");
            if (n.isKey) {
                //System.out.println(word + n.letter);
                words.add(word + n.letter);
            }
            get(n, prefix, word + n.letter, words);
        }
        return words;
    }
    @Override
    public String toString() {

        return null;
    }
    public void print() {
        print("");
    }
    public void print(String prefix) {
        System.out.print("{ ");
        for (String word: get(prefix)) {
            System.out.print(word + " ");
        }
        System.out.print("}\n");
    }
    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.put("hello");
        trie.put("helios");
        trie.put("helicopter");
        trie.put("hell");
        trie.put("bell");
        trie.put("helluva");
        trie.print("hell");

    }
}
