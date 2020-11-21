package edu.umn.cs.csci3081w.project.model;
import java.util.Random;

public class OrderBasedBusFactory extends BusFactory{
  public Bus makeBus(String name, Route outbound, Route inbound, double speed){
    Random rand = new Random();
    int choice = rand.nextInt(3);
    if (choice == 0) {
      return new SmallBus(name, outbound, inbound, speed);
    } else if (choice == 1) {
      return new RegularBus(name, outbound, inbound, speed);
    } else {
      return new LargeBus(name, outbound, inbound, speed);
    }
  }
}
