package algorithm;

/**
 * 这是极客时间课程：数据结构与算法之美 06 节的课后习题。只是对一个回复的注释说明。
 * 原始源码： https://github.com/andavid/leetcode-java/blob/master/note/234/README.md
 * 原理说明参考另一评论：https://raw.githubusercontent.com/SmallNum/MyPic/master/img/20190130171307.png
 * 注意：该实现修改了链表结构。实现之后，链表已经不是原始链表了。后续恢复部分可能还没实现，不过已经实现了判断回文的功能。
 */
class Solution {
    public boolean isPalindrome(ListNode head) {
        //回文：比如 “level” 或者 “noon”
        //只有一个结点，肯定是回文
        if (head == null || head.next == null) {
            return true;
        }

        ListNode prev = null;  //暂存慢指针的前一个结点。用于反转。初始为空用于构建尾结点
        ListNode slow = head;  //慢指针
        ListNode fast = head;  //快指针

        while (fast != null && fast.next != null) { //
            fast = fast.next.next; //快指针前进两步
            ListNode next = slow.next; //暂存慢指针的后一个结点（局部暂存）
            slow.next = prev;  //慢指针对前面遍历过的结点进行反转
            prev = slow;  //慢指针的前一个结点前进一步（）
            slow = next; //慢指针前进一步（通过前面暂存点后继结点实现）
        }

        if (fast != null) { //节点个数是奇数的情况：此时fast不为null，fast.next 为 null；如果节点个数为偶数，在上一个while循环里，fast会为null
            slow = slow.next;  //此时，将慢指针移至中间的那个节点
        }

        //注意：经过上面的处理。慢指针进入链表后半部分，可以理解为后半部分的首节点；同时prev为链表前半部分被反转后生成链表的首节点。
        //反转的作用在于：prev可以从中间向前遍历，slow从中间继续向后遍历。
        //下面，就给进行比较了。

        while (slow != null) {  //
            if (slow.val != prev.val) { //不相等就不是回文
                return false;
            }
            slow = slow.next;  //遍历下一个结点
            prev = prev.next;  //遍历下一个结点
        }

        return true;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}

