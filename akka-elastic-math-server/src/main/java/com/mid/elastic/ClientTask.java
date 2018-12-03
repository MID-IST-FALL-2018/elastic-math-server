package com.mid.elastic;

import com.mid.elastic.Task;

// Wrapper Task class that includes whether the task is done and its result
public class ClientTask extends Task {
  public Boolean done;
  public int result;

  public ClientTask(int id, int x, int y, BinaryOperation op) {
    super(id, x, y, op);
    this.done   = false;
    this.result = 0; // Initialised to 0 since it is not done yet.
                     // Any other value could be chosen.
  }
}
