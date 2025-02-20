# Grpc Clients
Grpc clients uses for sending requests to the server. It is a client side code that is used to communicate with gRPC server. 
`channel` is used to create a connection to the server. `stub` is used to call the methods of the server.
Channel manage the Http/2 connection between client and server. Multiple stubs can be used with a single channel.

We have some types of stubs;
- `BlockingStub`: It is a synchronous blocking stub. It blocks the client until the server responds. It can be used for unidirectional streaming.
- `FutureStub`: It is an asynchronous non-blocking stub. It returns a ListenableFuture. It can be used for unidirectional streaming.
- `AbstractAsyncStub`: It is an asynchronous non-blocking stub. It can be used for bidirectional streaming.