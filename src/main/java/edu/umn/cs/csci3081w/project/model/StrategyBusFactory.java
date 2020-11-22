package edu.umn.cs.csci3081w.project.model;

import java.time.LocalDateTime;

public class StrategyBusFactory extends BusFactory{

  private int count_s1 = 0;
  private int count_s2 = 0;
  private int count_s3 = 0;

  @Override
  public Bus makeBus(String name, Route outbound, Route inbound, double speed){

    LocalDateTime timeOfNow = LocalDateTime.now();
    int timeOfHour = timeOfNow.getHour();
    if(timeOfHour >= 5 && timeOfHour < 8){
      return Strategy1(name, outbound, inbound, speed);
    }
    else if(timeOfHour >= 8 && timeOfHour < 16){
      return Strategy2(name, outbound, inbound, speed);
    }
    else if(timeOfHour >= 16 && timeOfHour < 21) {
      return Strategy3(name, outbound, inbound, speed);
    }
    return new SmallBus(name, outbound, inbound, speed);
  }

  public Bus Strategy1(String name, Route outbound, Route inbound, double speed){
    if(count_s1 % 2 ==0){
      count_s1 ++;
      return new SmallBus(name, outbound, inbound, speed);
    }
    else{
      count_s1 ++;
      return new RegularBus(name, outbound, inbound, speed);
    }
  }

  public Bus Strategy2(String name, Route outbound, Route inbound, double speed){
    if(count_s2 % 2 == 0){
      count_s2 ++;
      return new RegularBus(name, outbound, inbound, speed);
    }
    else{
      count_s2 ++;
      return new LargeBus(name, outbound, inbound, speed);
    }
  }

  public Bus Strategy3(String name, Route outbound, Route inbound, double speed){
      if(count_s3 % 3 == 0){
        count_s3 ++;
        return new SmallBus(name, outbound, inbound, speed);
      }
      else if(count_s3 % 3 == 1){
        count_s3 ++;
        return new RegularBus(name, outbound, inbound, speed);
      }
      else{
        count_s3 ++;
        return new LargeBus(name, outbound, inbound, speed);
      }
  }
}
