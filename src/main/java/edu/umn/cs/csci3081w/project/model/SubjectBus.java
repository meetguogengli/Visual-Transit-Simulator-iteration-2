package edu.umn.cs.csci3081w.project.model;


import java.util.*;

public class SubjectBus implements Subject{
  private String id;
  private Position position;
  private int numPeople;
  private int capacity;
  private List<Observer> observers = new ArrayList<>();


  @Override
  public void registerObserver(Observer o){
    observers.add(o);
  }
  @Override
  public void removeObserver(Observer o){
    int i = observers.indexOf(o);
    if (i >= 0){
      observers.remove(i);
    }
  }

  @Override
  public void notifyObservers(){
    for (int i =0;i<observers.size();i++){
      Observer observer =(Observer)observers.get(i);
      observer.update(id,position,numPeople);
    }
  }
  public void measurementsChanged(){
    notifyObservers();
  }
  public void setMeasurements(String id,Position position,int numPeople,int capacity){
    this.id=id;
    this.position=position;
    this.numPeople=numPeople;
    this.capacity=capacity;
  }
}
