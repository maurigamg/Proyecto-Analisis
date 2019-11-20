/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planning;

/**
 *
 * @author Maci
 */
public class Task {
  private int idTree;
  private int antsAmount;
  private int routeTime;

  public Task(int pIdTree, int pAntsAmount,int pRouteTime) {
    this.idTree = pIdTree;
    this.antsAmount = pAntsAmount;
    this.routeTime = pRouteTime;
  }

  public int getIdTree() {
    return idTree;
  }

  public int getAntsAmount() {
    return antsAmount;
  }

  public int getRouteTime() {
    return routeTime;
  }
  
  
}
