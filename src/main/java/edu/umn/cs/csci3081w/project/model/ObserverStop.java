package edu.umn.cs.csci3081w.project.model;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.webserver.MyWebServer;
import edu.umn.cs.csci3081w.project.webserver.MyWebServerSession;

public class ObserverStop implements Observer {
    private String id;
    private Position position;
    private int numPeople;
    private SubjectImpl subjectImpl;
    private MyWebServerSession session;
    private MyWebServer myWS;

    public ObserverStop(SubjectImpl subjectImpl){
        this.subjectImpl = subjectImpl;
        subjectImpl.registerObserver(this);
        session = new MyWebServerSession();
    }

    @Override
    public void update(String id, Position position, int numPeople) {
        this.id = id;
        this.position = position;
        this.numPeople = numPeople;
        display(session);
    }

    @Override
    public void display(MyWebServerSession session){
        JsonObject data = new JsonObject();
        data.addProperty("command","observeStop");
        String info = "";
        info += "STOP" + "\n" + id
                +"-------------------------"
                +"\n"+"* Position: ("
                + position.getXcoordLoc()
                + ","
                + position.getYcoordLoc()
                + ")\n"
                + "* Passengers: "
                + numPeople;
        data.addProperty("text",info);
        session.sendJson(data);
    }
}