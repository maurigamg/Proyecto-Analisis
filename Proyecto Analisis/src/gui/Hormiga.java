/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author David
 */
    public class Hormiga {

        final int VELOCITY = 2;
        int XAntHILL;
        int y = 700;
        int xArbol;
        int x;
        int randomDelayedStart;
        boolean draw = false;
        boolean down = false;
        Color color;

        public Hormiga(int xAntHill, int randomDelayedStart, Color color, int xArbol) {
            this.XAntHILL = xAntHill;
            this.randomDelayedStart = randomDelayedStart;
            this.color = color;
            this.xArbol = xArbol;
            this.x = xAntHill;
        }

        public void drawShape(Graphics g) {
            if (draw) {
                g.setColor(color);
                g.fillOval(x, y, 10, 10);
            }
        }

        public void move() {
            if (draw) {
                
                if (x == xArbol) {
                    down = true;
                    this.color = Color.green;
                    y += 10;
                }
                
                
                if (down) {
                    x += VELOCITY;
                } else {
                    x -= VELOCITY;
                }
                if(x == XAntHILL){
                   draw = false;
                   randomDelayedStart = 1000;
                }
            }
        }

        public void decreaseDelay() {
            if (randomDelayedStart <= 0) {
                draw = true;
            } else {
                randomDelayedStart -= 1;
            }
        }

    }
