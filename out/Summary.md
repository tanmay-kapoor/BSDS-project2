# Assignment Overview

Overall, throughout this project, learning about RPCs and Java RMI specifically allowed me to create
a distributed system where objects in different JVMs could communicate and
invoke methods remotely. I realized the significance of designing my interfaces carefully to define
remote methods and their parameters, as well as implementing the necessary remote objects and
client-server communication logic.

# Technical Impression

Below are some of the key learnings I gained from this project.

### Understanding Remote Method Invocation

I learned how Java RMI facilitates remote communication between client and server, allowing remote
method calls to be executed transparently. It was crucial to comprehend the RMI architecture,
interfaces, and stubs to establish a reliable and efficient RPC system.

### Designing the API

Developing a well-defined API was essential for the key-value store. I learned to
design interfaces that defined the required remote methods for operations such as inserting,
retrieving, and deleting key-value pairs. It was crucial to consider factors like input validation,
error handling, and exception propagation.

### Handling Remote Exceptions

During the development process, I encountered various remote exceptions that could occur during
remote method invocation. I learned to handle exceptions effectively and provide meaningful error
messages to clients. Understanding the different types of exceptions, such as RemoteException and
application-specific exceptions, helped me ensure reliable error handling.

### Concurrent execution

Java RMI achieves concurrent execution on the server side through thread per request or thread
pooling. Synchronization ensures thread safety, while task isolation allows different threads to
execute separate tasks concurrently, improving performance and resource utilization.

### Making the HashMap thread safe

In this project, I have also utilized the `ReadWrite` lock instead of `synchronization`
since `ReadWrite` lock allows concurrent read requests but maintains a proper lock whenever write
is involved. Hence, acquiring the proper lock at the proper time ensures thread safety. 