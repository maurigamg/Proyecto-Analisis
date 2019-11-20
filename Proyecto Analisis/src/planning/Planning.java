package planning;

import common.TestTree;
import common.ITestConstants;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Mauricio
 */
public class Planning {

  private ArrayList<TestTree> trees;
  private ArrayList<Integer> treesUsed;
  private ArrayList<Task> tasks;
  private int leaves;
  private int time;

  public Planning(ArrayList<TestTree> pTrees, int type) {
    tasks = new ArrayList<>();
    treesUsed = new ArrayList<>();
    trees = pTrees;
    time = IPlanningConstants.TIME;
    leaves = 0;
    if(type==1)
        startGreedyAlgorithm();
    else{
        startProbabilisticAlgorithm();
    }
  }
  
  public int getLeaves(){
    return leaves;
  }
  
  public ArrayList<Task> getTasks(){
    return tasks;
  }
  
  private void assignProbability(){
    double summation=0;
    for(int indexTrees=0;indexTrees<trees.size();indexTrees++){
        if (!treesUsed.contains(indexTrees)){
            summation+=Math.abs((trees.get(indexTrees).getPosX()-ITestConstants.TEST_POSICION_HORMIGUERO)+trees.get(indexTrees).getLength());
        }          
    }
    System.out.println("SUMMATION: "+summation);
    for(int indexTrees=0;indexTrees<trees.size();indexTrees++){
        if (!treesUsed.contains(indexTrees)){
            double distance=Math.abs((trees.get(indexTrees).getPosX()-ITestConstants.TEST_POSICION_HORMIGUERO)+trees.get(indexTrees).getLength());
            trees.get(indexTrees).setProbability(1-(distance/summation));
        }          
    }      
  }
  
  private TestTree getProbabilityTree(){
    TestTree luckyTree= trees.get(0);
    //Random rand = new Random();
    double probability=Math.random()*1;
    System.out.println("PROBABILITY "+probability);
    double min=0;
    double max=0;
    for(int indexTrees=0;indexTrees<trees.size();indexTrees++){
        if (!treesUsed.contains(indexTrees)){
            max+=trees.get(indexTrees).getProbability();
            if(probability>=min&&probability<=max){
                luckyTree=trees.get(indexTrees);
                treesUsed.add(indexTrees);
                this.alocateProbability(luckyTree.getProbability(), (double)trees.size()-treesUsed.size());
                return luckyTree;
            }
            min=max;        
        }
    }
    return luckyTree;
  }
  
  private void alocateProbability(double summationToAssign, double treesToAssign){
      for(int indexTrees=0;indexTrees<trees.size();indexTrees++){
          if (!treesUsed.contains(indexTrees)){
            System.out.println("BEFORE: "+trees.get(indexTrees).getProbability());
            trees.get(indexTrees).setProbability(trees.get(indexTrees).getProbability()+(summationToAssign/treesToAssign));
            System.out.println("AFTER: "+trees.get(indexTrees).getProbability());
        }
    }
  }
  
  private void startProbabilisticAlgorithm(){
      this.assignProbability();
      while (time > 0 && trees.size() != treesUsed.size()) {
          TestTree luckyTree=getProbabilityTree();
          createTaskOfAvailableTree(luckyTree);
          System.out.println(time);
          System.out.println("Task terminado de crear numero: " + treesUsed.size() + " / " + trees.size());
      }
      System.out.println("TODOS LOS TASKS TERMINADOS");
  }
  
  private void startGreedyAlgorithm() {
    //This is the method to call to calcule the necessary data, based on the list of trees given
    while (time > 0 && trees.size() != treesUsed.size()) {
      TestTree closestTestTree = getClosestTree();
      createTaskOfAvailableTree(closestTestTree);
      System.out.println(time);
      System.out.println("Task terminado de crear numero: " + treesUsed.size() + " / " + trees.size());
    }
    System.out.println("TODOS LOS TASKS TERMINADOS");
  }

  private TestTree getClosestTree() {
    TestTree closestTree = trees.get(0);
    int idTree=0;
    for (int treeIterator = 0; treeIterator < trees.size(); treeIterator++) {
      if (!treesUsed.contains(treeIterator) && trees.get(treeIterator).getPosX() > closestTree.getPosX()) {
        closestTree = trees.get(treeIterator);
        idTree=treeIterator;
      }
    }
    treesUsed.add(idTree);
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

  private void createTaskOfAvailableTree(TestTree pTree) {
    int distance = getDistanceToLeaf(pTree);
    System.out.println(distance);
    int routeTime = distance * IPlanningConstants.SPEED * 2;
    int indexOfTree = trees.indexOf(pTree);
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
          createTask(routeTime, indexOfTree, leavesToWork);
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
        createTask(routeTime, indexOfTree,leavesToWork);
        leaves += leavesToWork;
        time = timeRemaining - leavesToWork;
      }      
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
    Planning greedyAlgorithm = new Planning(x,1);
    System.out.println("hojas: "+greedyAlgorithm.getLeaves());
    System.out.println("\nTasks");
    for(Task y: greedyAlgorithm.getTasks()){
      System.out.println("\nidTree: "+y.getIdTree());
      System.out.println("Amounts: "+y.getAntsAmount());
      System.out.println("RoutTime: "+y.getRouteTime());
    }
  }
}
