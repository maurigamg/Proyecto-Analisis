/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planning;

import gui.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author David
 */
public class Controller {
    public Planning planningLogic;
    public GUI viewGui;

    public Controller(GUI view, Planning planning ) {
        this.planningLogic = planning;
        this.viewGui = view;
        this.viewGui.addReinarActionListener(new ReinarListener());
        this.viewGui.setVisible(true);
    }
    
    
    class ReinarListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //inicia la vara
            planningLogic.getTasks();
            
        }
    
    }
    
    
}
