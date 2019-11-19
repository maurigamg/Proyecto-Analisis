/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazprogra;

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
