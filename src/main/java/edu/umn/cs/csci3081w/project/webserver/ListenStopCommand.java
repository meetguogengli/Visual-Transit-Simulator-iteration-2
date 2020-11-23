package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Observer;


public class ListenStopCommand extends MyWebServerCommand {
    private VisualizationSimulator sim;
    private MyWebServer server;
    public ListenStopCommand(VisualizationSimulator sim) {
        this.sim = sim;
        this.server = new MyWebServer();
    }

    /**
     * the give stop information for the simulation.
     *
     * @param session current simulation session
     * @param command the initialize routes command content
     * @param state the state of the simulation session
     */

    @Override
    public void execute(MyWebServerSession session,
                        JsonObject command, MyWebServerSessionState state) {

        int id = command.get("id").getAsInt();
//    sim.update();
        sim.listenStop(id);
        sim.display(session, server);
    }
}