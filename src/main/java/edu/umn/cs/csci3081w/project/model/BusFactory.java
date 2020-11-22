package edu.umn.cs.csci3081w.project.model;
import java.util.Random;

public abstract class BusFactory {
  public abstract Bus makeRandomBus(String name, Route outbound, Route inbound, double speed);

  public abstract Bus makeStrategyBus(String name, Route outbound, Route inbound, double speed);
}
