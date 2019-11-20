package gui;

import java.awt.Shape;

/**
 *
 * @author David
 */
public class Node {
        int key; 
        int posX;
        int posY;
        double angle;
        Shape ellipse;
        Node left;
        Node right;

        public void setArc(Shape arc) {
            this.ellipse = arc;
        }
        
 
          
        // constructor 
        Node(int key){ 
            this.key = key; 
            left = null; 
            right = null; 
        } 
        Node (int posX, int posY, double angle){
            this.posX=posX;
            this.posY=posY;
            this.angle=angle;
            left = null; 
            right = null;
        }
        Node (int posX, int posY, double angle,Shape arc){
            this.posX=posX;
            this.posY=posY;
            this.angle=angle;
            this.ellipse=arc;
            left = null; 
            right = null;
        }
}
