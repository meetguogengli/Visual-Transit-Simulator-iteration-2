package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import edu.umn.cs.csci3081w.project.webserver.VisualizationSimulator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;
import java.time.LocalDateTime;

public class BusTest {

  /**
   * Setup deterministic operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;
    RandomPassengerGenerator.DETERMINISTIC = true;
  }

  /**
   * Testing state after using constructor.
   */
  @Test
  public void testConstructorNormal() {
    Stop stop1 = new Stop(0, 44.972392, -93.243774);
    Stop stop2 = new Stop(1, 44.973580, -93.235071);
    Stop stop3 = new Stop(2, 44.975392, -93.226632);
    List<Stop> stopsIn = new ArrayList<Stop>();
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    stopsIn.add(stop3);
    List<Double> distancesIn = new ArrayList<Double>();
    distancesIn.add(0.008784);
    distancesIn.add(0.008631);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.15);
    probabilitiesIn.add(0.3);
    probabilitiesIn.add(.025);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(probabilitiesIn, stopsIn);
    Route testRouteIn = new Route("testRouteIn", stopsIn, distancesIn, 3, generatorIn);
    List<Stop> stopsOut = new ArrayList<>();
    stopsOut.add(stop3);
    stopsOut.add(stop2);
    stopsOut.add(stop1);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.008631);
    distancesOut.add(0.008784);
    List<Double> probabilitiesOut = new ArrayList<>();
    probabilitiesOut.add(.025);
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.15);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(probabilitiesOut, stopsOut);
    Route testRouteOut = new Route("testRouteIn", stopsOut, distancesOut, 3, generatorOut);
    Bus bus = new Bus("TestBus", testRouteOut, testRouteIn, 5, 1);
    assertEquals("TestBus", bus.getName());
    assertEquals(5, bus.getCapacity());
    assertEquals(1.0, bus.getSpeed());
  }

  /**
   * Testing state after using constructor.
   */
  @Test
  public void testBusDataConstructorNormal() {
    Position x = new Position(44.972392, -93.243774);
    BusData myData = new BusData("myBus", x, 5, 7);
    assertEquals(5, myData.getNumPassengers());
    assertEquals(7, myData.getCapacity());
    assertEquals("myBus", myData.getId());
    assertEquals(x, myData.getPosition());

  }

  /**
   * Testing state after using default constructor.
   */
  @Test
  public void testBusDataConstructorDefault() {
    BusData data = new BusData();
    Position my = new Position();
    assertEquals(0.0, data.getPosition().getXcoordLoc());
    assertEquals(0.0, data.getPosition().getYcoordLoc());


  }

  /**
   * Test Large bus constructor.
   */
  @Test
  public void testLargeBusConstructor() {
    Stop stop1 = new Stop(0, 44.972392, -93.243774);
    Stop stop2 = new Stop(1, 44.973580, -93.235071);
    Stop stop3 = new Stop(2, 44.975392, -93.226632);

    List<Stop> stopsIn = new ArrayList<Stop>();
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    stopsIn.add(stop3);
    List<Double> distancesIn = new ArrayList<Double>();
    distancesIn.add(0.008784);
    distancesIn.add(0.008631);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.15);
    probabilitiesIn.add(0.3);
    probabilitiesIn.add(.025);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(probabilitiesIn, stopsIn);
    Route testRouteIn = new Route("testRouteIn", stopsIn, distancesIn, 3, generatorIn);
    List<Stop> stopsOut = new ArrayList<>();
    stopsOut.add(stop3);
    stopsOut.add(stop2);
    stopsOut.add(stop1);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.008631);
    distancesOut.add(0.008784);
    List<Double> probabilitiesOut = new ArrayList<>();
    probabilitiesOut.add(.025);
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.15);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(probabilitiesOut, stopsOut);
    Route testRouteOut = new Route("testRouteIn", stopsOut, distancesOut, 3, generatorOut);
    LargeBus large = new LargeBus("large", testRouteOut, testRouteIn, 3.14);
    assertEquals("large", large.getName());
    assertEquals(testRouteIn, large.getIncomingRoute());
    assertEquals(testRouteOut, large.getOutgoingRoute());
    assertEquals(3.14, large.getSpeed());
    assertEquals(90, large.getCapacity());
  }

  /**
   * Test small bus constructor.
   */
  @Test
  public void testSmallBusConstructor() {
    Stop stop1 = new Stop(0, 44.972392, -93.243774);
    Stop stop2 = new Stop(1, 44.973580, -93.235071);
    Stop stop3 = new Stop(2, 44.975392, -93.226632);
    List<Stop> stopsIn = new ArrayList<Stop>();
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    stopsIn.add(stop3);
    List<Double> distancesIn = new ArrayList<Double>();
    distancesIn.add(0.008784);
    distancesIn.add(0.008631);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.15);
    probabilitiesIn.add(0.3);
    probabilitiesIn.add(.025);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(probabilitiesIn, stopsIn);
    Route testRouteIn = new Route("testRouteIn", stopsIn, distancesIn, 3, generatorIn);
    List<Stop> stopsOut = new ArrayList<>();
    stopsOut.add(stop3);
    stopsOut.add(stop2);
    stopsOut.add(stop1);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.008631);
    distancesOut.add(0.008784);
    List<Double> probabilitiesOut = new ArrayList<>();
    probabilitiesOut.add(.025);
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.15);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(probabilitiesOut, stopsOut);
    Route testRouteOut = new Route("testRouteIn", stopsOut, distancesOut, 3, generatorOut);
    SmallBus small = new SmallBus("small", testRouteOut, testRouteIn, 3.2);
    assertEquals("small", small.getName());
    assertEquals(testRouteIn, small.getIncomingRoute());
    assertEquals(testRouteOut, small.getOutgoingRoute());
    assertEquals(3.2, small.getSpeed());

  }

  /**
   * Test regular bus constructor.
   */
  @Test
  public void testRegularBusConstructor() {
    Stop stop1 = new Stop(0, 44.972392, -93.243774);
    Stop stop2 = new Stop(1, 44.973580, -93.235071);
    Stop stop3 = new Stop(2, 44.975392, -93.226632);
    List<Stop> stopsIn = new ArrayList<Stop>();
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    stopsIn.add(stop3);
    List<Double> distancesIn = new ArrayList<Double>();
    distancesIn.add(0.008784);
    distancesIn.add(0.008631);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.15);
    probabilitiesIn.add(0.3);
    probabilitiesIn.add(.025);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(probabilitiesIn, stopsIn);
    Route testRouteIn = new Route("testRouteIn", stopsIn, distancesIn, 3, generatorIn);
    List<Stop> stopsOut = new ArrayList<>();
    stopsOut.add(stop3);
    stopsOut.add(stop2);
    stopsOut.add(stop1);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.008631);
    distancesOut.add(0.008784);
    List<Double> probabilitiesOut = new ArrayList<>();
    probabilitiesOut.add(.025);
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.15);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(probabilitiesOut, stopsOut);
    Route testRouteOut = new Route("testRouteIn", stopsOut, distancesOut, 3, generatorOut);
    RegularBus regular = new RegularBus("regular", testRouteOut, testRouteIn, 3.2);
    assertEquals("regular", regular.getName());
    assertEquals(testRouteIn, regular.getIncomingRoute());
    assertEquals(testRouteOut, regular.getOutgoingRoute());
    assertEquals(3.2, regular.getSpeed());

  }

  /**
   * Test report with null stream.
   */
  @Test
  public void testBusReportNullStream() {
    Exception exception = assertThrows(NullPointerException.class, () -> {
      Bus testBus = TestUtils.createBus();
      testBus.report(null);
    });
  }

  /**
   * Test report with no passenger.
   */
  @Test
  public void testBusReportNoPassengers() {
    try {
      Bus testBus = TestUtils.createBus();
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      testBus.report(testStream);
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
              "####Bus Info Start####" + System.lineSeparator()
                      + "Name: TestBus" + System.lineSeparator()
                      + "Speed: 1.0" + System.lineSeparator()
                      + "Distance to next stop: 0.0" + System.lineSeparator()
                      + "****Passengers Info Start****" + System.lineSeparator()
                      + "Num of passengers: 0" + System.lineSeparator()
                      + "****Passengers Info End****" + System.lineSeparator()
                      + "####Bus Info End####" + System.lineSeparator();
      assertEquals(data, strToCompare);
    } catch (IOException ioe) {
      fail();
    }
  }

  /**
   * Test report with passenger.
   */
  @Test
  public void testBusReportWithPassengers() {
    try {
      Bus testBus = TestUtils.createBus();
      Passenger passenger = new Passenger(1, "Goldy");
      testBus.loadPassenger(passenger);
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      testBus.report(testStream);
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
              "####Bus Info Start####" + System.lineSeparator()
                      + "Name: TestBus" + System.lineSeparator()
                      + "Speed: 1.0" + System.lineSeparator()
                      + "Distance to next stop: 0.0" + System.lineSeparator()
                      + "****Passengers Info Start****" + System.lineSeparator()
                      + "Num of passengers: 1" + System.lineSeparator()
                      + "####Passenger Info Start####" + System.lineSeparator()
                      + "Name: Goldy" + System.lineSeparator()
                      + "Destination: 1" + System.lineSeparator()
                      + "Total wait: 1" + System.lineSeparator()
                      + "Wait at stop: 0" + System.lineSeparator()
                      + "Time on bus: 1" + System.lineSeparator()
                      + "####Passenger Info End####" + System.lineSeparator()
                      + "****Passengers Info End****" + System.lineSeparator()
                      + "####Bus Info End####" + System.lineSeparator();
      assertEquals(strToCompare, data);
    } catch (IOException ioe) {
      fail();
    }
  }

  /**
   * Test trip not complete when bus just created.
   */
  @Test
  public void testIsTripCompleteWhenBusJustCreated() {
    Bus testBus = TestUtils.createBus();
    boolean testBusAtEnd = testBus.isTripComplete();
    assertFalse(testBusAtEnd);
  }

  /**
   * Test load passenger with a full bus.
   */
  @Test
  public void testloadPassengerFullBus() {
    Bus testBus = TestUtils.createBus();
    Passenger passenger = new Passenger(1, "Goldy");
    Passenger passenger2 = new Passenger(1, "Cookie");
    Passenger passenger3 = new Passenger(1, "Potato");
    Passenger passenger4 = new Passenger(1, "Biscuit");
    Passenger passenger5 = new Passenger(1, "Peanut");
    Passenger passenger6 = new Passenger(1, "Chickpea");
    testBus.loadPassenger(passenger);
    testBus.loadPassenger(passenger2);
    testBus.loadPassenger(passenger3);
    testBus.loadPassenger(passenger4);
    testBus.loadPassenger(passenger5);
    boolean passengersLoaded = testBus.loadPassenger(passenger6);
    assertFalse(passengersLoaded);
  }

  /**
   * Test load passenger on empty bus.
   */
  @Test
  public void testLoadPassengerEmptyBus() {
    Bus testBus = TestUtils.createBus();
    Passenger passenger = new Passenger(1, "Goldy");
    boolean passengersLoaded = testBus.loadPassenger(passenger);
    assertTrue(passengersLoaded);
  }

  /**
   * Test load passenger with a full bus.
   */
  @Test
  public void testUnloadPassengerFullBus() {
    Bus testBus = TestUtils.createBus();
    Passenger passenger = new Passenger(1, "Goldy");
    Passenger passenger2 = new Passenger(1, "Cookie");
    Passenger passenger3 = new Passenger(1, "Potato");
    Passenger passenger4 = new Passenger(1, "Biscuit");
    Passenger passenger5 = new Passenger(2, "Peanut");
    Stop stop1 = new Stop(1, 44.972392, -93.243774);
    Stop stop2 = new Stop(1, 44.973580, -93.235071);
    testBus.loadPassenger(passenger);
    testBus.loadPassenger(passenger2);
    testBus.loadPassenger(passenger3);
    testBus.loadPassenger(passenger4);
    testBus.loadPassenger(passenger5);
    List<Passenger> List = new ArrayList<>();
    List.add(passenger);
    List.add(passenger2);
    List.add(passenger3);
    List.add(passenger4);
    List.add(passenger5);
    PassengerUnloader loader = new PassengerUnloader();

    assertEquals(4, loader.unloadPassengers(List, stop1));
  }

  /**
   * Test move without passenger.
   */
  @Test
  public void testMoveNoPassengersOnBus() {
    Bus testBus = TestUtils.createBus();
    boolean testBusMoved = testBus.move();
    assertEquals(1, testBus.getNextStop().getId());
  }

  /**
   * Test move with passenger.
   */
  @Test
  public void testMoveWithPassengersOnBus() {
    Bus testBus = TestUtils.createBus();
    Passenger passenger = new Passenger(1, "Goldy");
    Passenger passenger2 = new Passenger(1, "Cookie");
    testBus.loadPassenger(passenger);
    testBus.loadPassenger(passenger2);
    boolean testBusMoved = testBus.move();
    assertEquals(2, passenger.getTotalWait());
    assertEquals(2, passenger2.getTotalWait());

    assertEquals(1, testBus.getNextStop().getId());
  }

  /**
   * Test update on outgoing route.
   */
  @Test
  public void testOutgoingRouteUpdate() {
    try {
      Bus testBus = TestUtils.createBus();
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      testBus.report(testStream);
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
              "####Bus Info Start####" + System.lineSeparator()
                      + "Name: TestBus" + System.lineSeparator()
                      + "Speed: 1.0" + System.lineSeparator()
                      + "Distance to next stop: 0.0" + System.lineSeparator()
                      + "****Passengers Info Start****" + System.lineSeparator()
                      + "Num of passengers: 0" + System.lineSeparator()
                      + "****Passengers Info End****" + System.lineSeparator()
                      + "####Bus Info End####" + System.lineSeparator();
      assertEquals(strToCompare, data);
      testBus.update();
      final Charset charset2 = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
      PrintStream testStream2 = new PrintStream(outputStream2, true, charset2.name());
      testBus.report(testStream2);
      String data2 = new String(outputStream2.toByteArray(), charset2);
      testStream2.close();
      outputStream2.close();
      String strToCompare2 =
              "####Bus Info Start####" + System.lineSeparator()
                      + "Name: TestBus" + System.lineSeparator()
                      + "Speed: 1.0" + System.lineSeparator()
                      + "Distance to next stop: 0.9712663713083954" + System.lineSeparator()
                      + "****Passengers Info Start****" + System.lineSeparator()
                      + "Num of passengers: 0" + System.lineSeparator()
                      + "****Passengers Info End****" + System.lineSeparator()
                      + "####Bus Info End####" + System.lineSeparator();
      assertEquals(strToCompare2, data2);
    } catch (IOException ioe) {
      fail();
    }
  }

  /**
   * Test update bus data without moving the bus.
   */
  @Test
  public void testRouteUpdateBusDataNoMove() {
    try {
      Bus testBus = TestUtils.createBus();
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      testBus.report(testStream);
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
              "####Bus Info Start####" + System.lineSeparator()
                      + "Name: TestBus" + System.lineSeparator()
                      + "Speed: 1.0" + System.lineSeparator()
                      + "Distance to next stop: 0.0" + System.lineSeparator()
                      + "****Passengers Info Start****" + System.lineSeparator()
                      + "Num of passengers: 0" + System.lineSeparator()
                      + "****Passengers Info End****" + System.lineSeparator()
                      + "####Bus Info End####" + System.lineSeparator();
      assertEquals(strToCompare, data);
      testBus.updateBusData();
      final Charset charset2 = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
      PrintStream testStream2 = new PrintStream(outputStream2, true, charset2.name());
      testBus.report(testStream2);
      String data2 = new String(outputStream2.toByteArray(), charset2);
      testStream2.close();
      outputStream2.close();
      String strToCompare2 =
              "####Bus Info Start####" + System.lineSeparator()
                      + "Name: TestBus" + System.lineSeparator()
                      + "Speed: 1.0" + System.lineSeparator()
                      + "Distance to next stop: 0.0" + System.lineSeparator()
                      + "****Passengers Info Start****" + System.lineSeparator()
                      + "Num of passengers: 0" + System.lineSeparator()
                      + "****Passengers Info End****" + System.lineSeparator()
                      + "####Bus Info End####" + System.lineSeparator();
      assertEquals(strToCompare2, data2);
    } catch (IOException ioe) {
      fail();
    }
  }

  /**
   * Test update bus data with moving the bus.
   */
  @Test
  public void testRouteUpdateBusDataWithMove() {
    try {
      Bus testBus = TestUtils.createBus();
      while (!testBus.getIncomingRoute().isAtEnd() || !testBus.getOutgoingRoute().isAtEnd()) {
        testBus.move();
      }
      ;

      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      testBus.report(testStream);
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
              "####Bus Info Start####" + System.lineSeparator()
                      + "Name: TestBus" + System.lineSeparator()
                      + "Speed: 1.0" + System.lineSeparator()
                      + "Distance to next stop: 999.0" + System.lineSeparator()
                      + "****Passengers Info Start****" + System.lineSeparator()
                      + "Num of passengers: 0" + System.lineSeparator()
                      + "****Passengers Info End****" + System.lineSeparator()
                      + "####Bus Info End####" + System.lineSeparator();
      assertEquals(strToCompare, data);
      testBus.updateBusData();
      final Charset charset2 = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
      PrintStream testStream2 = new PrintStream(outputStream2, true, charset2.name());
      testBus.report(testStream2);
      String data2 = new String(outputStream2.toByteArray(), charset2);
      testStream2.close();
      outputStream2.close();
      String strToCompare2 =
              "####Bus Info Start####" + System.lineSeparator()
                      + "Name: TestBus" + System.lineSeparator()
                      + "Speed: 1.0" + System.lineSeparator()
                      + "Distance to next stop: 999.0" + System.lineSeparator()
                      + "****Passengers Info Start****" + System.lineSeparator()
                      + "Num of passengers: 0" + System.lineSeparator()
                      + "****Passengers Info End****" + System.lineSeparator()
                      + "####Bus Info End####" + System.lineSeparator();
      assertEquals(strToCompare2, data2);
    } catch (IOException ioe) {
      fail();
    }
  }

  /**
   * Test update bus data with moving the bus.
   */
  @Test
  public void testRouteUpdateBusDataWithMove2() {
    try {
      Bus testBus = TestUtils.createBus();
      testBus.getOutgoingRoute();
      while (!testBus.getOutgoingRoute().isAtEnd()) {
        testBus.move();
      }
      testBus.move();
      testBus.move();
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      testBus.report(testStream);
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
              "####Bus Info Start####" + System.lineSeparator()
                      + "Name: TestBus" + System.lineSeparator()
                      + "Speed: 1.0" + System.lineSeparator()
                      + "Distance to next stop: 0.9712663713083954" + System.lineSeparator()
                      + "****Passengers Info Start****" + System.lineSeparator()
                      + "Num of passengers: 0" + System.lineSeparator()
                      + "****Passengers Info End****" + System.lineSeparator()
                      + "####Bus Info End####" + System.lineSeparator();
      assertEquals(strToCompare, data);
      testBus.updateBusData();
      final Charset charset2 = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
      PrintStream testStream2 = new PrintStream(outputStream2, true, charset2.name());
      testBus.report(testStream2);
      String data2 = new String(outputStream2.toByteArray(), charset2);
      testStream2.close();
      outputStream2.close();
      String strToCompare2 =
              "####Bus Info Start####" + System.lineSeparator()
                      + "Name: TestBus" + System.lineSeparator()
                      + "Speed: 1.0" + System.lineSeparator()
                      + "Distance to next stop: 0.9712663713083954" + System.lineSeparator()
                      + "****Passengers Info Start****" + System.lineSeparator()
                      + "Num of passengers: 0" + System.lineSeparator()
                      + "****Passengers Info End****" + System.lineSeparator()
                      + "####Bus Info End####" + System.lineSeparator();
      assertEquals(strToCompare2, data2);
    } catch (IOException ioe) {
      fail();
    }
  }

  /* Test number of passengers on bus.
   */
  @Test
  public void testGetNumPassengersOnePass() {
    Bus testBus = TestUtils.createBus();
    Passenger passenger = new Passenger(1, "Goldy");
    testBus.loadPassenger(passenger);
    long passNum = testBus.getNumPassengers();
    assertEquals(1, passNum);
  }

  /* Test OrderBasedFactory
   */
  @Test
  public void testOrderBasedFactory() {
    OrderBasedBusFactory order = new OrderBasedBusFactory();
    LocalDateTime timeOfNow = LocalDateTime.now();
    int timeOfHour = timeOfNow.getHour();
    Stop stop1 = new Stop(0, 44.972392, -93.243774);
    Stop stop2 = new Stop(1, 44.973580, -93.235071);
    Stop stop3 = new Stop(2, 44.975392, -93.226632);
    List<Stop> stopsIn = new ArrayList<Stop>();
    stopsIn.add(stop1);
    stopsIn.add(stop2);
    stopsIn.add(stop3);
    List<Double> distancesIn = new ArrayList<Double>();
    distancesIn.add(0.008784);
    distancesIn.add(0.008631);
    List<Double> probabilitiesIn = new ArrayList<Double>();
    probabilitiesIn.add(.15);
    probabilitiesIn.add(0.3);
    probabilitiesIn.add(.025);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(probabilitiesIn, stopsIn);
    Route testRouteIn = new Route("testRouteIn", stopsIn, distancesIn, 3, generatorIn);
    List<Stop> stopsOut = new ArrayList<>();
    stopsOut.add(stop3);
    stopsOut.add(stop2);
    stopsOut.add(stop1);
    List<Double> distancesOut = new ArrayList<>();
    distancesOut.add(0.008631);
    distancesOut.add(0.008784);
    List<Double> probabilitiesOut = new ArrayList<>();
    probabilitiesOut.add(.025);
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.15);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(probabilitiesOut, stopsOut);
    Route testRouteOut = new Route("testRouteIn", stopsOut, distancesOut, 3, generatorOut);
    int a = order.makeStrategyBus("what", testRouteOut, testRouteIn, 3).getCapacity();
    if (timeOfHour >= 5 && timeOfHour < 8) {
      if (order.getCount_s1() % 2 == 0) {
        assertEquals(30, a);
      } else {
        assertEquals(60, a);
      }
    }
      else if(timeOfHour >= 8 && timeOfHour < 16){
        if(order.getCount_s2() % 2 == 0){
          assertEquals(a,60);
        }
        else{
          assertEquals(90,a);
        }
    }
    else if(timeOfHour >= 16 && timeOfHour < 21){
      if(order.getCount_s3() % 3 == 0){
        assertEquals(a,30);
      }
      else if(order.getCount_s3() % 3 == 1){
        assertEquals(a,60);
      }
      else if(order.getCount_s3() % 3 == 1){
        assertEquals(90,a);
      }

    }
    else{
      assertEquals(30,a);
    }

  }
}

