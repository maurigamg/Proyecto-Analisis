package planning;

import common.TestTree;
import common.ITestConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Mauricio
 */
public class Planning {

  private ArrayList<TestTree> trees;
  private HashMap<Integer,Boolean> treesUsed;
  private ArrayList<Task> tasks;
  private int treesUsedCount;
  private int leaves;
  private int time;

  public Planning(ArrayList<TestTree> pTrees) {
    tasks = new ArrayList<>();
    treesUsed = new HashMap<>();
    for(int treeIndex = 0; treeIndex < pTrees.size(); treeIndex++){
        treesUsed.put(treeIndex, Boolean.FALSE);
    }
    trees = pTrees;
    time = IPlanningConstants.TIME;
    time *= 0.8;
    leaves = 0;
    treesUsedCount = 0;
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
        if (!treesUsed.get(indexTrees)){
            summation+=Math.abs((trees.get(indexTrees).getPosX()-ITestConstants.TEST_POSICION_HORMIGUERO)+trees.get(indexTrees).getLength());
        }          
    }
    System.out.println("SUMMATION: "+summation);
    for(int indexTrees=0;indexTrees<trees.size();indexTrees++){
        if (!treesUsed.get(indexTrees)){
            double distance=Math.abs((trees.get(indexTrees).getPosX()-ITestConstants.TEST_POSICION_HORMIGUERO)+trees.get(indexTrees).getLength());
            trees.get(indexTrees).setProbability(1-(distance/summation));
        }          
    }      
  }
  
  private int getProbabilityTree(){
    int luckyTree = 0;
    //Random rand = new Random();
    while(treesUsed.get(luckyTree)){
      luckyTree++;
    }
    double probability=Math.random()*1;
    System.out.println("PROBABILITY "+probability);
    double min=0;
    double max=0;
    for(int indexTrees=luckyTree;indexTrees<trees.size();indexTrees++){
        if (!treesUsed.get(indexTrees)){
            max+=trees.get(indexTrees).getProbability();
            if(probability>=min&&probability<=max){
                luckyTree=indexTrees;
                treesUsed.replace(indexTrees,Boolean.TRUE);
                treesUsedCount++;
                this.alocateProbability(trees.get(luckyTree).getProbability(), (double)trees.size()-treesUsedCount);
                return luckyTree;
            }
            min=max;        
        }
    }
    return luckyTree;
  }
  
  private void alocateProbability(double summationToAssign, double treesToAssign){
      for(int indexTrees=0;indexTrees<trees.size();indexTrees++){
          if (!treesUsed.get(indexTrees)){
            System.out.println("BEFORE: "+trees.get(indexTrees).getProbability());
            trees.get(indexTrees).setProbability(trees.get(indexTrees).getProbability()+(summationToAssign/treesToAssign));
            System.out.println("AFTER: "+trees.get(indexTrees).getProbability());
        }
    }
  }
  
  public void startProbabilisticAlgorithm(){
      this.assignProbability();
      while (time > 0 && trees.size() != treesUsedCount) {
          int luckyTree=getProbabilityTree();
          createTaskOfAvailableTree(trees.get(luckyTree),luckyTree);
          System.out.println(time);
          System.out.println("Task terminado de crear numero: " + treesUsedCount + " / " + trees.size());
      }
      System.out.println("TODOS LOS TASKS TERMINADOS");
  }
  
 public void startGreedyAlgorithm() {
     System.out.println(treesUsedCount);
    //This is the method to call to calcule the necessary data, based on the list of trees given
    while (time > 0 && trees.size() != treesUsedCount) {
      int idTreeClosest = getClosestTree();
      createTaskOfAvailableTree(trees.get(idTreeClosest),idTreeClosest);
      System.out.println(time);
      System.out.println("Task terminado de crear numero: " + treesUsedCount + " / " + trees.size());
    }
    System.out.println("TODOS LOS TASKS TERMINADOS");
  }

  private int getClosestTree() {
    int idTree = 0;
    while(treesUsed.get(idTree)){
      idTree++;
    }
    for (int treeIterator = idTree; treeIterator < trees.size(); treeIterator++) {
      if (!treesUsed.get(treeIterator) && trees.get(treeIterator).getPosX() > trees.get(idTree).getPosX()) {
        idTree = treeIterator;
      }
    }
    treesUsed.replace(idTree,Boolean.TRUE);
    treesUsedCount++;
    return idTree;
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

  private void createTaskOfAvailableTree(TestTree pTree,int pIdTree) {
    int distance = getDistanceToLeaf(pTree);
    System.out.println("Distancia: "+distance);
    int routeTime = distance * IPlanningConstants.SPEED * 2;
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
          createTask(routeTime, pIdTree, leavesToWork);
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
        createTask(routeTime, pIdTree,leavesToWork);
        leaves += leavesToWork;
        time = timeRemaining - leavesToWork;
      }
    }
  }


  private void createTask(int pRouteTime, int pTreeIndex, int pAntsAmount) {
    Task taskToAdd = new Task(pTreeIndex, pAntsAmount, pRouteTime);
    tasks.add(taskToAdd);
  }
  
}
