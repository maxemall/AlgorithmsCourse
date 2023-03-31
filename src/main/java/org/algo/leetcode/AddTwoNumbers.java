package org.algo.leetcode;

public class AddTwoNumbers {

  public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
    ListNode result = new ListNode(0);
    ListNode previous = new ListNode();
    ListNode root = result;
    while (l1 != null || l2 != null) {
      int sum = (l1 != null ? l1.val : 0) + (l2 != null ? l2.val : 0) + result.val;
      result.val = sum % 10;
      result.next = new ListNode(sum / 10);
      previous = result;
      result = result.next;
      if (l1 != null) l1 = l1.next;
      if (l2 != null) l2 = l2.next;
    }
    if (result.val == 0) previous.next = null;
    return root;
  }

  public static class ListNode {
    int val;
    ListNode next;

    ListNode() {}

    ListNode(int val) {
      this.val = val;
    }

    ListNode(int val, ListNode next) {
      this.val = val;
      this.next = next;
    }
  }

  public static void main(String[] args){
    ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
    ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));
    ListNode listNode = addTwoNumbers(l1, l2);
    System.out.println(listNode);
  }
}
