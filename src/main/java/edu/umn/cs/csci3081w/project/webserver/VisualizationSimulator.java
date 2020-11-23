package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Bus;
import edu.umn.cs.csci3081w.project.model.Observer;
import edu.umn.cs.csci3081w.project.model.OrderBasedBusFactory;
import edu.umn.cs.csci3081w.project.model.Route;
import edu.umn.cs.csci3081w.project.model.Stop;
import edu.umn.cs.csci3081w.project.model.StopData;
import edu.umn.cs.csci3081w.project.model.SubjectBus;
import edu.umn.cs.csci3081w.project.model.SubjectStop;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class VisualizationSimulator {

  private WebInterface webInterface;
  private ConfigManager configManager;
  private List<Integer> busStartTimings;
  private List<Integer> timeSinceLastBus;
  private int numTimeSteps = 0;
  private int simulationTimeElapsed = 0;
  private List<Route> prototypeRoutes;
  private List<Bus> busses;
  private int busId = 1000;
  private boolean paused = false;
  private Random rand;
  private SubjectStop subjectStop;
  private StopData stopdata;
  private MyWebServerSession myWebServerSession;
  private OrderBasedBusFactory busFacotry;
  private SubjectBus subjectBus;

  /**
   * Constructor for Simulation.
   *
   * @param webI    MWS object
   * @param configM config object
   */
  public VisualizationSimulator(MyWebServer webI, ConfigManager configM) {
    this.webInterface = webI;
    this.configManager = configM;
    //initialize these lists so that we do not get a null pointer
    this.busStartTimings = new ArrayList<Integer>();
    this.prototypeRoutes = new ArrayList<Route>();
    this.busses = new ArrayList<Bus>();
    this.timeSinceLastBus = new ArrayList<Integer>();
    this.rand = new Random();
    this.busFacotry = new OrderBasedBusFactory();
    this.subjectStop = new SubjectStop();
    this.stopdata = new StopData();
    this.myWebServerSession = new MyWebServerSession();
    this.busFacotry = new OrderBasedBusFactory();
    this.subjectBus = new SubjectBus();

  }

  /**
   * Starts the simulation.
   *
   * @param busStartTimingsParam start timings of bus
   * @param numTimeStepsParam    number of time steps
   */
  public void start(List<Integer> busStartTimingsParam, int numTimeStepsParam) {
    this.busStartTimings = busStartTimingsParam;
    this.numTimeSteps = numTimeStepsParam;
    for (int i = 0; i < busStartTimings.size(); i++) {
      this.timeSinceLastBus.add(i, 0);
    }
    simulationTimeElapsed = 0;
    prototypeRoutes = configManager.getRoutes();
    for (int i = 0; i < prototypeRoutes.size(); i++) {
      prototypeRoutes.get(i).report(System.out);
      prototypeRoutes.get(i).updateRouteData();
      webInterface.updateRoute(prototypeRoutes.get(i).getRouteData(), false);
    }
  }

  /**
   * Toggles the pause state of the simulation.
   */
  public void togglePause() {
    paused = !paused;
  }

  /**
   * Create bus randomly.
   *
   * @param name     parameter for the name of the bus
   * @param outbound parameter for outbound route
   * @param inbound  parameter for inbound route
   * @param speed    parameter for bus speed
   * @return created bus
   */

  public Bus createBuses(String name, Route outbound, Route inbound, double speed) {
    LocalDateTime dateOfNow = LocalDateTime.now();
    int timeOfDate = dateOfNow.getDayOfMonth();
    return busFacotry.makeBusSelection(name, outbound, inbound, speed);
  }

  public Bus createRandomBus(String name, Route outbound, Route inbound, double speed) {
    Bus bus = busFacotry.makeRandomBus(name, outbound, inbound, speed);
    return bus;
  }

  public Bus createStrategyBus(String name, Route outbound, Route inbound, double speed) {
    Bus bus = busFacotry.makeStrategyBus(name, outbound, inbound, speed);
    return bus;
  }


  /**
   * Updates the simulation at each step.
   */
  public void update() {
    if (!paused) {
      simulationTimeElapsed++;
      System.out.println("~~~~The simulation time is now"
          + "at time step "
          + simulationTimeElapsed + "~~~~");
      // Check if we need to generate new busses
      for (int i = 0; i < timeSinceLastBus.size(); i++) {
        // Check if we need to make a new bus
        if (timeSinceLastBus.get(i) <= 0) {
          Route outbound = prototypeRoutes.get(2 * i);
          Route inbound = prototypeRoutes.get(2 * i + 1);
          busses
              .add(createBuses(String.valueOf(busId),
                  outbound.shallowCopy(), inbound.shallowCopy(), 1));
          busId++;
          timeSinceLastBus.set(i, busStartTimings.get(i));
          timeSinceLastBus.set(i, timeSinceLastBus.get(i) - 1);
        } else {
          timeSinceLastBus.set(i, timeSinceLastBus.get(i) - 1);
        }
      }
      // Update busses
      for (int i = busses.size() - 1; i >= 0; i--) {
        busses.get(i).update();
        if (busses.get(i).isTripComplete()) {
          webInterface.updateBus(busses.get(i).getBusData(), true);
          busses.remove(i);
          continue;
        }
        webInterface.updateBus(busses.get(i).getBusData(), false);
        busses.get(i).report(System.out);
      }
      // Update routes
      for (int i = 0; i < prototypeRoutes.size(); i++) {
        prototypeRoutes.get(i).update();
        webInterface.updateRoute(prototypeRoutes.get(i).getRouteData(), false);
        prototypeRoutes.get(i).report(System.out);
      }
    }
    subjectStop.notifyObservers();
  }

  /**
   * This is a listenBus function passes if of the bus.
   * @param id id of bus
   * @param session session of myWebServerSession
   * @param myWS myWS of MyWebServer
   */
  public void listenBus(int id, MyWebServerSession session, MyWebServer myWS) {
    for (int i = 0; i <= busses.size() - 1; i++) {
      Observer myObserver = (Observer) busses.get(i).getBusData();
      subjectBus.registerObserver((Observer) busses.get(i));
      //subjectImpl.measurementsChanged();
    }
    for (int i = 0; i <= busses.size() - 1; i++) {
      if ((busses.get(i).getBusData().getId()).equals(String.valueOf(id))) {
        Observer myObserver = (Observer) busses.get(i).getBusData();
        //subjectImpl.measurementsChanged();
        myObserver.display(session, myWS);
      }
    }
  }

  /**
   * Display the information of Task8.
   *
   * @param session session for MyWebServerSession
   * @param myWS    myWebServer for MyWebServer
   */
  public void displayBus(MyWebServerSession session, MyWebServer myWS) {
    JsonObject data = new JsonObject();
    data.addProperty("command", "observeBus");
    String info = "";
    info = info + "BUS " + busses.get(0).getBusData().getId()
        + "\n"
        + "-------------------------"
        + "\n" + "* Position: ("
        + busses.get(0).getBusData().getPosition().getXcoordLoc()
        + ","
        + busses.get(0).getBusData().getPosition().getYcoordLoc()
        + ")\n"
        + "* Passengers: "
        + busses.get(0).getNumPassengers()
        + "\n"
        + "* Capacity: "
        + busses.get(0).getCapacity() + "\n" + "\n" + "\n";
    data.addProperty("text", info);
    session.sendJson(data);
  }

  /**
   * This is a listenStop command.
   * @param id id of the stop.
   */
  public void listenStop(int id) {
    for (int i = 0; i < prototypeRoutes.size(); i++) {
      List<Stop> stop = prototypeRoutes.get(i).getStops();
      for (int j = 0; j < stop.size() - 1; j++) {
        if (id == stop.get(j).getId()) {
          subjectStop.registerObserver((Observer) stop.get(j));
          List<StopData> stopDataList = prototypeRoutes.get(i).getRouteData().getStops();
          stopdata = stopDataList.get(j);
          subjectStop.setMeasurements(stopDataList.get(j).getId(),
              stopDataList.get(j).getPosition(),
              stopDataList.get(j).getNumPeople());
          subjectStop.notifyObservers();
        }
      }
    }
    subjectStop.notifyObservers();
  }

  /**
   * This funciton displays the information of stop.
   * @param session session of MyWebServerSession
   * @param myWS myWS of MyWebServer
   */
  public void display(MyWebServerSession session, MyWebServer myWS) {
    JsonObject data = new JsonObject();
    data.addProperty("command", "observeStop");
    StopData stopdata = prototypeRoutes.get(0).getRouteData().getStops().get(0);
    String info = "";
    info += "STOP ID = " + stopdata.getId() + "\n"
        + "-------------------------"
        + "\n" + "* Position: ("
        + stopdata.getPosition().getXcoordLoc()
        + ","
        + stopdata.getPosition().getYcoordLoc()
        + ")\n"
        + "* Passengers: "
        + stopdata.getNumPeople();
    data.addProperty("text", info);
    session.sendJson(data);
  }
}