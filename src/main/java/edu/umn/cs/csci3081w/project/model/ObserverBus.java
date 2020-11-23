package edu.umn.cs.csci3081w.project.model;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.webserver.MyWebServer;
import edu.umn.cs.csci3081w.project.webserver.MyWebServerSession;

public class ObserverBus implements Observer {
  private String id;
  private Position position;
  private int numPeople;
  private int capacity;
  private SubjectBus subjectBus;
  private MyWebServerSession session;
  private MyWebServer myWS;

  public ObserverBus(SubjectBus subjectBus) {
    this.subjectBus = subjectBus;
    subjectBus.registerObserver(this);
  }

  @Override
  public void update(String id, Position position, int numPeople) {
    this.id = id;
    this.position = position;
    this.numPeople = numPeople;
    display(session, myWS);
  }

  /**
   * Displaying the information for bus.
   *
   * @param session session of myWebServerSession
   * @param myWS    myWebServer
   */
  public void display(MyWebServerSession session, MyWebServer myWS) {
    JsonObject data = new JsonObject();
    data.addProperty("command", "observeBus");
    String info = "";
    info = info + "BUS" + "\n" + id
        + "-------------------------"
        + "\n" + "* Position: ("
        + position.getXcoordLoc()
        + ","
        + position.getYcoordLoc()
        + ")\n"
        + "* Passengers: "
        + numPeople
        + "* Capacity: "
        + capacity;
    data.addProperty("text", info);
    session.sendJson(data);
  }

}
