package com.mid.elastic;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class Server extends AbstractActor {




  /***********/
  /** State **/
  /***********/
  private final int minWorkers;
  private final int maxWorkers;
  private final int tick;




  /*******************/
  /** Configuration **/
  /*******************/
  static public Props props(int minWorkers, int maxWorkers, int tick) {
    return Props.create(Server.class, () -> new Server(minWorkers,maxWorkers,tick));
  }




  /*****************/
  /** Constructor **/
  /*****************/
  public Server(int minWorkers, int maxWorkers, int tick) {
    this.minWorkers       = minWorkers;  // Fix number of min workers
    this.maxWorkers       = maxWorkers;  // Fix number of max workers
    this.tick             = tick;        // Fix tick frenquency (in seconds)
  }




  /*********************/
  /** Message handler **/
  /*********************/
  @Override
  public Receive createReceive() {
    return receiveBuilder()
    // TODO: Add `match(...)` cases to handle messages
    .build();
  }
}
