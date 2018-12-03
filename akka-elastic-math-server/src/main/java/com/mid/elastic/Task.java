package com.mid.elastic;

// Class to encapsulate the operation to perform and id
public class Task {
  public enum BinaryOperation {SUM, SUB, MUL}

  public final int id;  // tasks id
  public final int x,y; // parameters
  public final BinaryOperation operation; // Binary operation to perform

  public Task(int id, int x, int y, BinaryOperation operation) {
    this.id        = id;
    this.x         = x;
    this.y         = y;
    this.operation = operation;
  }
}
