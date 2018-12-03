package com.mid.elastic;

// Java libraries
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

// Message imports
import com.mid.elastic.Client.ClientStart;

// Object imports
import com.mid.elastic.Task.BinaryOperation;
import com.mid.elastic.ClientTask;

// Akka libraries
import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class Main {
  public static void main(String[] args) {
    // Initialise the actor system
    final ActorSystem system = ActorSystem.create("elastic-server");
    try {

      // Initialise list of tasks
      List<ClientTask> list = new ArrayList<ClientTask>();
      list.add(new ClientTask(1, 42, 43, BinaryOperation.SUM));
      list.add(new ClientTask(2, 42, 43, BinaryOperation.SUB));
      list.add(new ClientTask(3, 42, 43, BinaryOperation.MUL));

      // Create an elastic server with at least 2 workers and at most 5
      // The server checks the number of active workers every 2 seconds
      final ActorRef server = system.actorOf(Server.props(2,5,2), "server_actor");
      // Create a client with the list of tasks above
      final ActorRef client = system.actorOf(Client.props(server,list), "client_actor");

      // Tell client to start processing tasks
      client.tell(new ClientStart(), ActorRef.noSender());

      System.out.println(">>> Press ENTER to exit <<<");
      System.in.read();
    } catch (IOException ioe) {
    } finally {
      system.terminate();
    }
  }
}
