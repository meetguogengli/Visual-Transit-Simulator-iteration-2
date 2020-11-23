package edu.umn.cs.csci3081w.project.model;
//package edu.umn.cs.csci3081w.project.webserver;

import edu.umn.cs.csci3081w.project.webserver.MyWebServer;
import edu.umn.cs.csci3081w.project.webserver.MyWebServerSession;



public interface Observer {
  public void update(String id, Position position, int numPeople);

  public void display(MyWebServerSession session, MyWebServer myWS);
}