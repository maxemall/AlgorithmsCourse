package org.algo.leetcode;

public class Problem1 {
  public int[] twoSum(int[] nums, int target) {
    int dif;
    for (int i = nums.length - 1; i >= 0; i--) {
      dif = target - nums[i];
      for (int i1 = i - 1; i1 >= 0; i1--) {
        if (nums[i1] == dif) return new int[] {i, i1};
      }
    }
    return new int[] {0, 1};
  }

  public static void main(String[] args){
      Problem1 problem1 = new Problem1();
      problem1.twoSum(new int[]{3,2,4}, 6);
  }
}
