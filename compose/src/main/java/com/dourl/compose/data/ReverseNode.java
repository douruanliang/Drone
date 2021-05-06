package com.dourl.compose.data;


/**
 * 链表反转 就地逆转
 */
public class ReverseNode {

  public  static void reverse(LNode head){

      if (head == null || head.next == null){
          return;
      }

      LNode pre = null;
      LNode cur = null;
      LNode next = null;

      //第一部分 断头
      cur = head.next;
      next = cur.next;
      cur.next = null; // 把链表的首节点变为尾节点

      //第二部分  过度初始值
      pre = cur; // 为循环变量
      cur = next;
      //第三部分  循环
      while (cur.next!=null){
          next = cur.next;  //下移
          cur.next = pre;   //反转
          pre = cur;   //下移
          cur = next;
      }
      //第四部分  接头
      cur.next = pre; // 节点的最后一个节点指向倒数第二个节点
      head.next = cur; //链表的头节点指向原来的链表的尾节点

  }

    public static void main(String[] args) {
        LNode head = new LNode();
        head.next = null;

        LNode tmp = null;
        LNode cur = head;

        for (int i=0 ; i<8;i++){
            tmp = new LNode();
            tmp.data = i;
            tmp.next = null;
            cur.next = tmp;
            cur = tmp;
        }

        System.out.println("逆序前：");

        for (cur = head.next;cur!=null;cur= cur.next){
            System.out.print(cur.data+"->");
        }

        reverse(head);
        System.out.println(" \n 逆序后：");

        for (cur = head.next;cur!=null;cur= cur.next){
            System.out.print(cur.data+"-");
        }
    }
}
