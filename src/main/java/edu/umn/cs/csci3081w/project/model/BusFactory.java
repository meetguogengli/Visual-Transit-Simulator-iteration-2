package edu.umn.cs.csci3081w.project.model;
import java.time.LocalDateTime;

public abstract class BusFactory {
  public Bus busSelection(String name, Route outbound, Route inbound, double speed){
    LocalDateTime dateOfNow=LocalDateTime.now();
    int timeOfDate=dateOfNow.getDayOfMonth();
    if(timeOfDate==1 || timeOfDate ==15){
      Bus bus=makeBus(name,outbound,inbound,speed);
      return bus;
    }
    else{
      Bus bus=makeBus(name,outbound,inbound,speed);
      return bus;
    }
  }
  public abstract Bus makeBus(String name, Route outbound, Route inbound, double speed);
}
