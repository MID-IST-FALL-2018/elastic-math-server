# Elastic Mathematical Server

Your task in this assignment is to implement an server that is able to perform some mathematical binary operations (concretely: addition, subtraction and multiplication).
The server must have the following features

1. Parallelism - The server must be able to process many operations in parallel.
2. Elasticity - The server must be able to reduce/increase the amount of resources that it uses depending on how many operations are requested.

The operations are submitted by clients that you must implemented as well. A client has a list of tasks that submits to the server and wait for the answer.

## Architecture

Your task is to implement the server described above using the Actors model.
More precisely, **you must use the [Akka library for Java](https://akka.io)** to implement the system.

The figure below depicts a high level overview of the architecture of your application.

![Architecture](images/architecture.svg)

In the figure above, circles represent the different actors in the system (clients, server and workers), arrows model the allowed flows of communication.
The systems is composed by *n* clients that have a lists of tasks (mathematical operations) to be computed by the server.
For each task, the clients submit a a request to the server.
The server keeps track of a list of workers which perform the tasks requested by the clients.
When the server receives a task it checks whether there is an available worker, and, if so, it assigns the task to the worker.
Otherwise, the server marks the task as pending so that it can be performed in the future by an available worker.
The amount of workers depends on the workload in the server.
The server has a `min` and `max` number of workers that can be created.
The system will always have at least `min` workers and at most `max` workers.
When a task is submitted, if there are no workers available, but the amount of active workers (workers computing some operation) `max` have not reached, then the server is allowed to create a new worker.
Every `tick` seconds, the server must check whether there are idle workers (i.e., workers not computing any operation), if there are more than `min`, then at worker must be terminated.
This processes can be repeated until there are `min` workers in the system.



## Requirements

### Client

Clients are modelled as Akka actors. They have a list of `ClientTask`. A `ClientTask` is an object that contains a `BinaryOperation` (which can be `SUM`, `SUB` or `MUL`), two parameters `x` and `y`, the `result` of the binary operation, and a boolean flag `done` which indicates whether the task has been done.


* For each request to the server, clients can send at most one task from their list of tasks. For instance, if the list of tasks is composed by 3 tasks, three requests must be sent to the server.

* There are no assumptions about the `id` of the tasks lists. For instance, two tasks from different clients may have the same `id`.

* Clients cannot receive messages from the server.

* There can be an unbounded number of clients in the system.

* The results of the submitted tasks must stored in the same list as when they are initialised (variable `private List<ClientTask> tasks` in `Client.java`) and **in the same order as when they were requested**. For instance, if the list of tasks is `[1+2,3-2]` the list of tasks after processing must be `[3,1]`. If after submitting the tasks, the resulting list is, e.g., `[1,3]`, the result is considered as incorrect.
Note that in this example I am using a simplification of `ClientTask` objects as the should include `id`, the flag `done` and so on.

### Server

The server is also modelled as an Akka actor.
The minimum elements of the server state are: the minimum `min` and maximum `max` number of workers, and a recurrence `tick` (expressed in seconds) which determines how often the server must check that the amount of workers is correct.

* The server may receive messages from clients but cannot send messages to clients.

* The amount of workers depends on the submitted tasks. At any point in time there can be at most `max` workers and at least `min` workers. When a task is submitted, if there are no available workers and the number of active workers is equal to `max` a new worker may be created. Also, every `tick` seconds, the amount of idle workers must be revise, if the number of idle workers is greater than `min`, then a worker must be terminated.

* If a task request is received, the amount of active workers is equal to `max`, and none of the workers can currently work on the task, then the task must be set as pending.
When a worker finishes processing a task, the server must make sure that, if there are pending task, the worker will immediately work in one of them.

* The server cannot be blocked at any moment. It must always be able to receive requests.

### Worker

* Workers may receive messages from the server, but cannot send messages to the server. Moreover, workers can send messages to clients.

<!-- ## Running the code
### Correctness unit test -->
