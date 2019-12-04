// Derrick Liang 2/20/18
import java.util.*;
import java.io.*;

public class BSTConverter
{
//   public static void main(String[] args)
//   {
//      PrintWriter pw = new PrintWriter(new File("BST.out"));
//      BufferedReader br = new BufferedReader(new FileReader("BST.in"));
//
//      String in = br.readLine();
//      TreeNode tree = new TreeNode(in.charAt(0), null, null);
//      for (int i = 1; i < in.length(); i++)
//      {
//         insert(tree, in.charAt(i));
//      }
//      System.out.println(display(tree, 0));
//   }
//
//   public static String display(TreeNode t, int level)
//   {
//      String toRet = "";
//      if(t == null)
//         return "";
//      toRet += display(t.getRight(), level + 1); //recurse right
//      for(int k = 0; k < level; k++)
//         toRet += "\t";
//      toRet += t.getValue() + "\n";
//      toRet += display(t.getLeft(), level + 1); //recurse left
//      return toRet;
//   }
//
//   public static void insert(TreeNode t, char s)
//   {
//      if (t == null)
//         return new TreeNode(s);
//      else if (((Comparable) t.getValue()).compareTo(s) >= 0)
//      {
//         t.setLeft(insert(t.getLeft(), s));
//      }
//      else
//         t.setRight(insert(t.getRight(), s));
//   }
}