/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfazprogra;

/**
 *
 * @author David
 */
public class prueba {

    GUI view; 
    public prueba() {
        view= new GUI();
        view.setVisible(true);
    }
    public void viewArbol(){
        view.arbol.inorder(view.arbol.root);
    }
    
    public static void main(String[] args) {
        prueba p = new prueba();
        p.viewArbol();
        System.out.println("interfazprogra.prueba.main()");
    }
    
    
}
