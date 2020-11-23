package edu.umn.cs.csci3081w.project.model;

import java.time.LocalDateTime;
import java.util.Random;

public class OrderBasedBusFactory extends BusFactory {
  private int countS1 = 0;
  private int countS2 = 0;
  private int countS3 = 0;

  @Override
  public Bus makeRandomBus(String name, Route outbound, Route inbound, double speed) {
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

  @Override
  public Bus makeStrategyBus(String name, Route outbound, Route inbound, double speed) {
    LocalDateTime timeOfNow = LocalDateTime.now();
    int timeOfHour = timeOfNow.getHour();
    if (timeOfHour >= 5 && timeOfHour < 8) {
      if (countS1 % 2 == 0) {
        countS1++;
        return new SmallBus(name, outbound, inbound, speed);
      } else {
        countS1++;
        return new RegularBus(name, outbound, inbound, speed);
      }
    } else if (timeOfHour >= 8 && timeOfHour < 16) {
      if (countS2 % 2 == 0) {
        countS2++;
        return new RegularBus(name, outbound, inbound, speed);
      } else {
        countS2++;
        return new LargeBus(name, outbound, inbound, speed);
      }
    } else if (timeOfHour >= 16 && timeOfHour < 21) {
      if (countS3 % 3 == 0) {
        countS3++;
        return new SmallBus(name, outbound, inbound, speed);
      } else if (countS3 % 3 == 1) {
        countS3++;
        return new RegularBus(name, outbound, inbound, speed);
      } else if (countS3 % 3 == 2) {
        countS3++;
        return new LargeBus(name, outbound, inbound, speed);
      }
    }
    return new SmallBus(name, outbound, inbound, speed);
  }

  public int getCount_s1() {
    return countS1;
  }

  public int getCount_s2() {
    return countS2;

  }

  public int getCount_s3() {
    return countS3;
  }

}
