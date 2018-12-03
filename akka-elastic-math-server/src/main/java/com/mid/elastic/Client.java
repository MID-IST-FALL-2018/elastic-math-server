package com.mid.elastic;

import java.util.List;
import java.util.ArrayList;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class Client extends AbstractActor {




  /***********/
  /** State **/
  /***********/
  private final ActorRef server;
  private List<ClientTask> tasks;




  /*******************/
  /** Configuration **/
  /*******************/
  static public Props props(ActorRef server, List<ClientTask> list) {
    return Props.create(Client.class, () -> new Client(server,list));
  }




  /*****************/
  /** Constructor **/
  /*****************/
  public Client(ActorRef server, List<ClientTask> tasks) {
    this.server = server;
    this.tasks  = tasks;
  }




  /**************/
  /** Messages **/
  /**************/
  // Request server to perform all tasks in the list
  static public class ClientStart {}




  /*********************/
  /** Message handler **/
  /*********************/
  @Override
  public Receive createReceive() {
    return receiveBuilder()
    /***********************************/
    /** Start sending tasks to server **/
    /***********************************/
    .match(ClientStart.class, cs -> {
      // TODO: To be implemented...
    })
    // NOTE: Of course, the client may receive other types of message.
    //       Just add them with `.match(...)`
    .build();
  }




  /*************************/
  /** Auxiliary functions **/
  /*************************/
  // NOTE: This function can only be used for testing purposes
  public List<ClientTask> getListOfTasks() {
    return this.tasks;
  }

  //NOTE: Other auxiliary functions may be created
}
