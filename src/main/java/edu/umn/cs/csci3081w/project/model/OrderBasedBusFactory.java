package edu.umn.cs.csci3081w.project.model;
import java.util.Random;
import java.time.LocalDateTime;

public class OrderBasedBusFactory extends BusFactory{
    private int count_s1 = 0;
    private int count_s2 = 0;
    private int count_s3 = 0;

    @Override
    public Bus makeRandomBus(String name, Route outbound, Route inbound, double speed){
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
    public Bus makeStrategyBus(String name, Route outbound, Route inbound, double speed){
        LocalDateTime timeOfNow = LocalDateTime.now();
        int timeOfHour = timeOfNow.getHour();
        if(timeOfHour >= 5 && timeOfHour < 8){
            if(count_s1 % 2 ==0){
                count_s1 ++;
                return new SmallBus(name, outbound, inbound, speed);
            }else{
                count_s1 ++;
                return new RegularBus(name, outbound, inbound, speed);
            }
        }else if(timeOfHour >= 8 && timeOfHour < 16){
            if(count_s2 % 2 == 0){
                count_s2 ++;
                return new RegularBus(name, outbound, inbound, speed);
            }else{
                count_s2 ++;
                return new LargeBus(name, outbound, inbound, speed);
            }
        }else if(timeOfHour >= 16 && timeOfHour < 21){
            if(count_s3 % 3 == 0){
                count_s3 ++;
                return new SmallBus(name, outbound, inbound, speed);
            }else if(count_s3 % 3 == 1){
                count_s3 ++;
                return new RegularBus(name, outbound, inbound, speed);
            }else if(count_s3 % 3 == 2){
                count_s3 ++;
                return new LargeBus(name, outbound, inbound, speed);
            }
        }
        return new SmallBus(name, outbound, inbound, speed);
    }
}
