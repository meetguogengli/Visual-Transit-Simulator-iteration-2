package edu.umn.cs.csci3081w.project.model;

import edu.umn.cs.csci3081w.project.webserver.MyWebServerSession;

public interface Subject {
  void registerObserver(Observer o);

  void removeObserver(Observer o);

  void notifyObservers();
}