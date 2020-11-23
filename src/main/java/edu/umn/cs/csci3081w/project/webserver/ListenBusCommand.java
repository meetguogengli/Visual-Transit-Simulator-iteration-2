package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.BusData;

import java.util.List;

public class ListenBusCommand extends MyWebServerCommand {

  private VisualizationSimulator mySim;
  private MyWebServer myWS;

  public ListenBusCommand(VisualizationSimulator sim) {
    this.mySim = sim;
    this.myWS = new MyWebServer();
  }


  /**
   * Updates the state of the simulation.
   *
   * @param session current simulation session
   * @param command the update simulation command content
   * @param state   the state of the simulation session
   */

  @Override
  public void execute(MyWebServerSession session, JsonObject command,
                      MyWebServerSessionState state) {
    List<BusData> busses = myWS.busses;
    JsonObject data = new JsonObject();
    data.addProperty("command", "listenBus");
    JsonArray bussesArray = new JsonArray();
    int id=command.get("id").getAsInt();
    data.addProperty("id", id);
    mySim.displayBus(session, myWS);
    mySim.update();
    mySim.listenBus(id, session, myWS);
    session.sendJson(data);

//    int id = command.get("id").getAsInt();
//    mySim.update();
//    mySim.listenBus(id, session, myWS);
//    mySim.displayBus(session, myWS);
  }
}

