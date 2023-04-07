package org.algo.leetcode;

public class Trie {

  private static final char CHAR_A = 'a';
  private TrieNode root;

  public Trie() {
    root = new TrieNode();
  }

  public static void main(String[] args) {
    Trie trie = new Trie();
    trie.insert("app");
    trie.insert("apple");
    trie.insert("apteka");
    trie.insert("abpr");
    System.out.println(trie.search("abp"));
  }

  public void insert(String word) {
    TrieNode node = root;
    for (char c : word.toCharArray()) {
      if (node.children[c - CHAR_A] == null) {
        node.children[c - CHAR_A] = new TrieNode();
      }
      node = node.children[c - CHAR_A];
    }
    node.isWord = true;
  }

  public boolean search(String word) {
    TrieNode node = findNode(word);
    return node != null && node.isWord;
  }

  public boolean startsWith(String prefix) {
    return findNode(prefix) != null;
  }

  private TrieNode findNode(String word) {
    if ((word == null) || (word.equals(""))) return null;
    TrieNode node = root;
    for (char c : word.toCharArray()) {
      if (node.children[c - CHAR_A] == null) {
        return null;
      }
      node = node.children[c - CHAR_A];
    }
    return node;
  }

  private static class TrieNode {
    TrieNode[] children;
    boolean isWord;

    public TrieNode() {
      children = new TrieNode[26];
    }
  }
}
