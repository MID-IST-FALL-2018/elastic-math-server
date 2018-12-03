package com.mid.elastic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mid.elastic.Client.ClientStart;
import com.mid.elastic.Task.BinaryOperation;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import akka.testkit.TestActorRef;

public class ElasticServerTests {
  static ActorSystem system;

  // Start the actor system
  @BeforeClass
  public static void start() {
      system = ActorSystem.create();
  }

  // Shutdown the actor system
  @AfterClass
  public static void shutdown() {
      TestKit.shutdownActorSystem(system);
      system = null;
  }

  @Test
  public void checkCorrectFinalList() {
    // Initialise list of tasks
    List<ClientTask> list = new ArrayList<ClientTask>();
    list.add(new ClientTask(1,42,43,BinaryOperation.SUM));
    list.add(new ClientTask(2,42,43,BinaryOperation.SUB));
    list.add(new ClientTask(3,42,43,BinaryOperation.MUL));

    // Create actors
    final ActorRef server = system.actorOf(Server.props(2,5,1), "server_actor");

    final Props props = Client.props(server,list);
    final TestActorRef<Client> ref = TestActorRef.create(system, props, "testClient");
    final Client client = ref.underlyingActor();

    // Tell client to start processing tasks
    client.getSelf().tell(new ClientStart(), ActorRef.noSender());

    // Get a reference to the list of tasks
    List<ClientTask> resultList = client.getListOfTasks();

    // Wait until all tasks are done
    Boolean allTasksDone = false;
    while(!allTasksDone) {
      System.out.println("Are all tasks done? " + allTasksDone);
      allTasksDone = resultList.parallelStream()
                               .map(t -> t.done)
                               .reduce(true, (a,b) -> a && b);
      try {Thread.sleep(1000);}catch(Exception e){System.out.println(e);}
    }

    // First sanity check: The size of the lists is equal
    assertTrue(resultList.size() == list.size());

    // Check that all results are correct
    // Assuming that `resultList.size() == list.size()`
    for (int i=0; i < resultList.size(); i++) {
      assertTrue(resultList.get(i).id == list.get(i).id); // Correct id?
      assertTrue(resultList.get(i).operation == list.get(i).operation); // Correct operation?
      assertTrue(resultList.get(i).done == true); // Is the task done?
      if(resultList.get(i).operation == BinaryOperation.SUM) { // Correct result?
        assertTrue(resultList.get(i).result == list.get(i).x + list.get(i).y);
      }
      if(resultList.get(i).operation == BinaryOperation.SUB) {
        assertTrue(resultList.get(i).result == list.get(i).x - list.get(i).y);
      }
      if(resultList.get(i).operation == BinaryOperation.MUL) {
        assertTrue(resultList.get(i).result == list.get(i).x * list.get(i).y);
      }
    }
  }
}
