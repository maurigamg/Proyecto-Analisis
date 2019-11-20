package planning;

import common.TestTree;
import common.ITestConstants;
import java.util.ArrayList;

/**
 *
 * @author Mauricio
 */
public class GreedyAlgorithm {

  private ArrayList<TestTree> trees;
  private ArrayList<Integer> treesUsed;
  private ArrayList<Task> tasks;
  private int leaves;
  private int time;

  public GreedyAlgorithm(ArrayList<TestTree> pTrees) {
    tasks = new ArrayList<>();
    treesUsed = new ArrayList<>();
    trees = pTrees;
    time = IPlanningConstants.TIME;
    leaves = 0;
    startGreedyAlgorithm();
  }
  
  public int getLeaves(){
    return leaves;
  }
  
  public ArrayList<Task> getTasks(){
    return tasks;
  }
  
  private void startGreedyAlgorithm() {
    //This is the method to call to calcule the necessary data, based on the list of trees given
    while (time > 0 && trees.size() != treesUsed.size()) {
      TestTree closestTestTree = getClosestTree();
      createTaskOfClosestAvailableTree(closestTestTree);
      System.out.println(time);
      System.out.println("Task terminado de crear numero: " + treesUsed.size() + " / " + trees.size());
    }
    System.out.println("TODOS LOS TASKS TERMINADOS");
  }

  private TestTree getClosestTree() {
    TestTree closestTree = trees.get(0);
    for (int treeIterator = 0; treeIterator < trees.size(); treeIterator++) {
      if (!treesUsed.contains(treeIterator) && trees.get(treeIterator).getPosX() < closestTree.getPosX()) {
        closestTree = trees.get(treeIterator);
      }
    }
    return closestTree;
  }

  private int getDistanceToLeaf(TestTree pTree) {
    float totalDistance = Math.abs(ITestConstants.TEST_POSICION_HORMIGUERO - pTree.getPosX());
    float pastBranchLength = pTree.getLength();
    totalDistance += pastBranchLength;
    for (int currentLevel = 0; currentLevel < pTree.getLevels(); currentLevel++) {
      pastBranchLength *= pTree.getGrow_percentage();
      totalDistance += pastBranchLength;
    }
    return (int) totalDistance;
  }

  private void createTaskOfClosestAvailableTree(TestTree pTree) {
    int distance = getDistanceToLeaf(pTree);
    System.out.println(distance);
    int routeTime = distance * IPlanningConstants.SPEED * 2;
    int indexOfClosestTree = trees.indexOf(pTree);
    int timeRemaining = time - routeTime;
    if (timeRemaining > 0) {
      if (Math.pow(2, pTree.getLevels()) > IPlanningConstants.ANTS) {
        //There isnt enough ants to work the whole tree in one task
        int leavesLeft = (int) Math.pow(2, pTree.getLevels());
        int leavesToWork = 0;
        while (time > 0 && leavesLeft > 0 && timeRemaining>0) {
          System.out.println("time :"+time);
          System.out.println("timeRemaining :"+timeRemaining);
          //The amount of leaves to be worked is wither the amount of leaves left in the tree or the
          //total amount of ants, depending on what is the lower value
          leavesToWork = (int) ((IPlanningConstants.ANTS < leavesLeft)
                  ? IPlanningConstants.ANTS : leavesLeft);
          if(timeRemaining<leavesToWork){
            leavesToWork = timeRemaining;
          }
          createTask(routeTime, indexOfClosestTree, leavesToWork);
          leavesLeft -= leavesToWork;
          leaves += leavesToWork;
          time = timeRemaining - leavesToWork;
          timeRemaining = time - routeTime;
        }
        time = timeRemaining - leavesToWork;
      } else {
        //There is enough ants to work the whole tree in one task
        int leavesToWork = (int) Math.pow(2, pTree.getLevels());
        if(timeRemaining<leavesToWork){
            leavesToWork = timeRemaining;
        }
        createTask(routeTime, indexOfClosestTree,leavesToWork);
        leaves += leavesToWork;
        time = timeRemaining - leavesToWork;
      }
      treesUsed.add(indexOfClosestTree);
    }
  }

  private void createTask(int pRouteTime, int pTreeIndex, int pAntsAmount) {
    if (!tasks.isEmpty()) {
      //Runs if this is not the first task
      Task taskToAdd = new Task(pTreeIndex, pAntsAmount, pRouteTime);
      tasks.add(taskToAdd);
    } else {
      //Runs if this is the first task created
      Task taskToAdd = new Task(pTreeIndex, pAntsAmount, pRouteTime);
      tasks.add(taskToAdd);
    }
  }
  
  public static void main(String[] args){
    ArrayList<TestTree> x = new ArrayList<>();
    x.add(new TestTree(900, 50, 10));
    x.add(new TestTree(800, 50, 10));
    GreedyAlgorithm greedyAlgorithm = new GreedyAlgorithm(x);
    System.out.println("hojas: "+greedyAlgorithm.getLeaves());
    System.out.println("\nTasks");
    for(Task y: greedyAlgorithm.getTasks()){
      System.out.println("\nidTree: "+y.getIdTree());
      System.out.println("Amounts: "+y.getAntsAmount());
      System.out.println("RoutTime: "+y.getRouteTime());
    }
  }
}
