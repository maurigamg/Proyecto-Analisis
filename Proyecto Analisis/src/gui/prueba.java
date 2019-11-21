package gui;

import common.TestGenerator;
import common.TestTree;
import java.util.ArrayList;
import planning.Controller;
import planning.Planning;

/**
 *
 * @author David
 */
public class prueba {

    
    public static void main(String[] args) {
        TestGenerator generator = new TestGenerator();
        
        
        ArrayList<TestTree> trees = generator.getTests()[0];
        GUI view = new GUI(trees);
        Planning planning = new Planning(trees, 1);
        Controller controller = new Controller(view, planning);
        
    }
    
    
}
