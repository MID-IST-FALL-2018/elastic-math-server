package com.mid.elastic;

import akka.actor.AbstractActor;

public class Worker extends AbstractActor {




  /*********************/
  /** Message handler **/
  /*********************/
  @Override
  public Receive createReceive() {
    return receiveBuilder()
    // TODO: Add `match(...)` cases to handle messages
    .build();
  }




  /*****************/
  /** Operations **/
  /*****************/
  // NOTE: The methods below cannot be modified
  private int sum(int x, int y) {
    System.out.println("Computing: "+x+" + "+y+" ...");
    try{Thread.sleep(1000);}catch(InterruptedException e){System.out.println(e);} // Artificial addition delay
    return x+y;
  }
  private int sub(int x, int y) {
    System.out.println("Computing: "+x+" - "+y+" ...");
    try{Thread.sleep(2000);}catch(InterruptedException e){System.out.println(e);} // Artificial substraction delay
    return x-y;
  }
  private int mul(int x, int y) {
    System.out.println("Computing: "+x+" * "+y+" ...");
    try{Thread.sleep(3000);}catch(InterruptedException e){System.out.println(e);} // Artificial multipliation delay
    return x*y;
  }
}
