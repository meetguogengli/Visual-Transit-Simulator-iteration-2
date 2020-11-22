package edu.umn.cs.csci3081w.project.model;
import java.time.LocalDateTime;
import java.util.Random;

public abstract class BusFactory {

  public Bus makeBusSelection(String name, Route outbound, Route inbound, double speed){
    LocalDateTime dateOfNow=LocalDateTime.now();
    int timeOfDate=dateOfNow.getDayOfMonth();
    if(timeOfDate==1 || timeOfDate ==15){
      Bus bus=makeRandomBus(name,outbound,inbound,speed);
      return bus;
    }
    else{
      Bus bus=makeStrategyBus(name,outbound,inbound,speed);
      return bus;
    }
  }
  public abstract Bus makeRandomBus(String name, Route outbound, Route inbound, double speed);
  public abstract Bus makeStrategyBus(String name, Route outbound, Route inbound, double speed);
}
