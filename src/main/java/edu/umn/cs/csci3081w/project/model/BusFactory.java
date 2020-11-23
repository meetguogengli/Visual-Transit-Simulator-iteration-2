package edu.umn.cs.csci3081w.project.model;

import java.time.LocalDateTime;

public abstract class BusFactory {
  public abstract Bus makeRandomBus(String name, Route outbound, Route inbound, double speed);

  public abstract Bus makeStrategyBus(String name, Route outbound, Route inbound, double speed);

  /**
   * This is a operation for selecting task6 and task5.
   * @param name name of the bus
   * @param outbound outbound of route
   * @param inbound inbound of route
   * @param speed speed of bus
   * @return
   */
  public Bus makeBusSelection(String name, Route outbound, Route inbound, double speed) {
    LocalDateTime dateOfNow = LocalDateTime.now();
    int timeOfDate = dateOfNow.getDayOfMonth();
    if (timeOfDate == 1 || timeOfDate == 15) {
      Bus bus = makeRandomBus(name, outbound, inbound, speed);
      return bus;
    } else {
      Bus bus = makeStrategyBus(name, outbound, inbound, speed);
      return bus;
    }
  }
}