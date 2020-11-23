package edu.umn.cs.csci3081w.project.model;
import edu.umn.cs.csci3081w.project.webserver.MyWebServer;
import edu.umn.cs.csci3081w.project.webserver.MyWebServerSession;

import java.util.ArrayList;

public class SubjectStop implements Subject {
    private ArrayList observers;
    private String id;
    private Position position;
    private MyWebServerSession session;
    private int numPeople;
    private MyWebServer myWS;

    public SubjectStop() {
        observers = new ArrayList();
        session = new MyWebServerSession();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if(i >= 0) {
            observers.remove(i);
        }
    }

    @Override
    public void notifyObservers() {
        for(int i=0; i < observers.size(); i++) {
            ObserverStop observer = (ObserverStop) observers.get(i);
            observer.update(id, position, numPeople);
            observer.display(session, myWS);
        }
    }

    public void measurementsChanged(){
        notifyObservers();
    }

    public void setMeasurements(String id, Position position, int numPeople){
        this.id = id;
        this.position = position;
        this.numPeople = numPeople;
        measurementsChanged();
    }
}