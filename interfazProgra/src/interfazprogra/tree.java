/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazprogra;

/**
 *
 * @author MSI Leopard Pro
 */
import java.awt.Shape;
import java.util.LinkedList; 
import java.util.Queue; 
public class tree { 
       Node root;
    /* A binary tree node has key, pointer to  
    left child and a pointer to right child */
//    static class Node { 
//        int key; 
//        int posX;
//        int posY;
//        double angle;
//        Shape ellipse;
//
//        public void setArc(Shape arc) {
//            this.ellipse = arc;
//        }
//        
//        Node left, right; 
//          
//        // constructor 
//        Node(int key){ 
//            this.key = key; 
//            left = null; 
//            right = null; 
//        } 
//        Node (int posX, int posY, double angle){
//            this.posX=posX;
//            this.posY=posY;
//            this.angle=angle;
//            left = null; 
//            right = null;
//        }
//        Node (int posX, int posY, double angle,Shape arc){
//            this.posX=posX;
//            this.posY=posY;
//            this.angle=angle;
//            this.ellipse=arc;
//            left = null; 
//            right = null;
//        }
//    } 
//    
//    static Node root; 
//    static Node temp = root; 
//    Node test;
    tree(int x, int y, double angle){
        root=new Node(x,y,angle);
    }
      
    /* Inorder traversal of a binary tree*/
    public void inorder(Node temp) 
    { 
        if (temp == null) 
            return; 
       
        inorder(temp.left); 
        if(temp.ellipse==null)
            System.out.println(temp.posX+" "+temp.posY+" "); 
        else            
            System.out.println(temp.posX+" "+temp.posY+" "+temp.ellipse.toString()); 
        inorder(temp.right); 
        
    } 
       
    /*function to insert element in binary tree */
    static void insert(Node temp, int posX,int posY,double angle, Shape arc) 
    { 
        Queue<Node> q = new LinkedList<Node>(); 
        q.add(temp); 
       
        // Do level order traversal until we find 
        // an empty place.  
        while (!q.isEmpty()) { 
            temp = q.peek(); 
            q.remove(); 
       
            if (temp.left == null) { 
                if(arc==null)
                    temp.left=new Node(posX,posY,angle);
                else
                    temp.left = new Node(posX,posY,angle,arc); 
                break; 
            } else
                q.add(temp.left); 
       
            if (temp.right == null) { 
                if(arc==null)
                    temp.right=new Node(posX,posY,angle);
                else
                    temp.right = new Node(posX,posY,angle,arc); 
                break; 
            } else
                q.add(temp.right); 
        } 
    } 
       
    // Driver code 
    /*
    public static void main(String args[]) 
    { 
        root = new Node(10); 
        root.left = new Node(11); 
        root.left.left = new Node(7); 
        root.right = new Node(9); 
        root.right.left = new Node(15); 
        root.right.right = new Node(8); 
       
        System.out.print( "Inorder traversal before insertion:"); 
        inorder(root); 
       
        int key = 12; 
        insert(root, key); 
       
        System.out.print("\nInorder traversal after insertion:"); 
        inorder(root); 
    } */
} 
// This code is contributed by Sumit Ghosh 
