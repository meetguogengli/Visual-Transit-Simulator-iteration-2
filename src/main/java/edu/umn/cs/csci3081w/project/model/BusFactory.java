package edu.umn.cs.csci3081w.project.model;
import java.util.Random;

public abstract class BusFactory {
  public abstract Bus makeBus(String name, Route outbound, Route inbound, double speed);
}
