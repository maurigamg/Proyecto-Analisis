package common;

import java.io.FileWriter;
import java.util.ArrayList;

import com.google.gson.Gson;
import static common.ITestConstants.AMOUNT_OF_TESTS;
import static common.ITestConstants.TEST_RULES;
import gui.GUI;
import planning.Controller;
import planning.Planning;
import planning.Task;

public class TestGenerator implements ITestConstants {

  private ArrayList<TestTree>[] tests = new ArrayList[AMOUNT_OF_TESTS];

  public TestGenerator() {
    for (int testCount = 0; testCount < AMOUNT_OF_TESTS; testCount++) {
      tests[testCount] = new ArrayList<TestTree>();
    }

    generateTests();
  }

  private void generateTests() {
    for (int testCount = 0; testCount < AMOUNT_OF_TESTS; testCount++) {
      for (int ruleIndex = 0; ruleIndex < TEST_RULES[testCount].length; ruleIndex++) {
        for (int treeCount = 0; treeCount < TEST_RULES[testCount][ruleIndex][TestRanges.QUANTITY.getIndex()]; treeCount++) {
          int posX = TEST_RULES[testCount][ruleIndex][TestRanges.MIN_DISTANCE.getIndex()]
                  + (int) (Math.random() * (TEST_RULES[testCount][ruleIndex][TestRanges.MAX_DISTANCE.getIndex()] - TEST_RULES[testCount][ruleIndex][TestRanges.MIN_DISTANCE.getIndex()]));

          int length = TEST_RULES[testCount][ruleIndex][TestRanges.MIN_LENGTH.getIndex()]
                  + (int) (Math.random() * (TEST_RULES[testCount][ruleIndex][TestRanges.MAX_LENGTH.getIndex()] - TEST_RULES[testCount][ruleIndex][TestRanges.MIN_LENGTH.getIndex()]));

          int levels = TEST_RULES[testCount][ruleIndex][TestRanges.MIN_LEVELS.getIndex()]
                  + (int) (Math.random() * (TEST_RULES[testCount][ruleIndex][TestRanges.MAX_LEVELS.getIndex()] - TEST_RULES[testCount][ruleIndex][TestRanges.MIN_LEVELS.getIndex()]));

          TestTree test = new TestTree(posX, length, levels);
          tests[testCount].add(test);

        }
      }
    }
  }

  public ArrayList<TestTree>[] getTests() {
    return tests;
  }

  public static void main(String args[]) {
    TestGenerator generator = new TestGenerator();

    //ArrayList<TestTree> x = new ArrayList<>();
    //x.add(new TestTree(900, 50, 10));
    //x.add(new TestTree(800, 50, 10));
//    Planning greedyAlgorithm = new Planning(generator.getTests()[0]);
//    
//    //greedyAlgorithm.startGreedyAlgorithm();
//    Controller controller = new Controller(view, greedyAlgorithm);
//    System.out.println("hojas: "+greedyAlgorithm.getLeaves());
//    System.out.println("\nTasks");
//    for(Task y: greedyAlgorithm.getTasks()){
//      System.out.println("\nidTree: "+y.getIdTree());
//      System.out.println("Amounts: "+y.getAntsAmount());
//      System.out.println("RoutTime: "+y.getRouteTime());
//    }
    GUI view = new GUI(generator.getTests()[0]);
    
  }
}
