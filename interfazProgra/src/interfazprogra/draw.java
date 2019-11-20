/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazprogra;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;

/**
 *
 * @author erick
 */
public class draw extends JPanel{
    static tree treeToTest=new tree(400,500,-90);
    public draw(){
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        //super.paint(grphcs); //To change body of generated methods, choose Tools | Templates.
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(
//        RenderingHints.KEY_ANTIALIASING,
//        RenderingHints.VALUE_ANTIALIAS_ON);
//        g2d.setComposite(AlphaComposite.getInstance(
//        AlphaComposite.SRC_OVER, 0.3f));
        Color color=new Color(0, 102, 0);
        g2d.setColor(color);
        g2d.fillRect(0, 700, 1199, 250);
        Color brown=new Color(102, 51, 0);
        g2d.setColor(brown);
        g2d.fillRect(800, 700, 10, 10);
        g2d.setColor(Color.ORANGE);
        g2d.fillRect(0, 0, 1199, 700);
        g2d.setColor(Color.BLACK);
        drawTree(g2d, 400, 750, -90, 10);
    
  
    
    }
    private void drawTree(Graphics g, int x1, int y1, double angle, int depth) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        
        if (depth == 0){
            g2d.setColor(new Color(0,153,0));            
            Shape ellipse=new Ellipse2D.Double(x1,y1,5,5);
            g2d.fill(ellipse);
            tree.insert(treeToTest.test,x1,y1,angle,ellipse);
           
            return;        
        }

        int x2 = x1 + (int) (Math.cos(Math.toRadians(angle)) * depth * 1.5);
        int y2 = y1 + (int) (Math.sin(Math.toRadians(angle)) * depth * 1.5);
       
        
        g2d.drawLine(x1, y1, x2, y2);
        tree.insert(treeToTest.test,x1,y1,angle,null);
        drawTree(g, x2, y2, angle - 20, depth - 1);
        
        drawTree(g, x2, y2, angle + 20, depth - 1);
    }
    
    
    
}
