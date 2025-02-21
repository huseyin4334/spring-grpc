# gRPC
gRPC is a high-performance, open-source universal RPC framework. 
It is based on HTTP/2, Protocol Buffers, and other Google technologies. gRPC is designed to be efficient, fast, and highly extensible. 
It supports multiple programming languages and platforms.

Let's explain some terminology:
- Protocol Buffers: 
  - A language-neutral, platform-neutral, extensible way of serializing structured data for use in communications protocols, data storage, and more.
  - We can think like JSON, but more efficient and faster. It's working with binary data.
- RPC is Remote Procedure Call:
  - A protocol that one program can use to request a service from a program located on another computer in a network without having to understand the network's details.
  - It's like calling a method from another machine.
- HTTP/2:
  - The second major version of the HTTP network protocol used by the World Wide Web.
  - It's faster than HTTP/1.1.
  - Multiplexing: The biggest difference is that it allows multiple requests to be sent in parallel over a single TCP connection.
  - HTTP/1.1 requires a new TCP connection for each request/response exchange.
  - Server Push: HTTP/2 allows the server to push resources to the client before the client requests them.
  - Binary: HTTP/2 is a binary protocol, instead of textual like HTTP/1.1.
  - SSL: HTTP/2 requires SSL/TLS encryption.
  - Less chatty: HTTP/2 is less chatty, meaning fewer bytes are sent over the wire.
  - Less bandwidth: HTTP/2 is more efficient with bandwidth because it compresses headers.
- Code Generation:
  - gRPC generates client and server code using Protocol Buffers.
  - It generates strongly typed code in the language of your choice.
  - It's like a contract between the client and the server. 
  - For example, we design a service in the proto file. We said this api will get ... type of request and return ... type of response. 
  - After that, gRPC generates the client and server code in the language of your choice.

# Types Of gRPC APIs
There are 4 types of gRPC APIs:
1. Unary
   - The client sends a single request to the server and gets a single response back.
2. Server Streaming
    - The client sends a single request to the server and gets a stream of responses back.
3. Client Streaming
    - The client sends a stream of requests to the server and gets a single response back.
4. Bi-Directional Streaming
    - Both the client and the server send a stream of requests and responses in parallel.

We will define streams with the keyword `stream` in the proto file.

```protobuf
syntax = "proto3";

package greet;

service GreetService {
  // Unary
  rpc Greet(GreetRequest) returns (GreetResponse) {}

  // Server Streaming
  rpc GreetManyTimes(GreetManyTimesRequest) returns (stream GreetManyTimesResponse) {}

  // Client Streaming
  rpc LongGreet(stream LongGreetRequest) returns (LongGreetResponse) {}

  // Bi-Directional Streaming
  rpc GreetEveryone(stream GreetEveryoneRequest) returns (stream GreetEveryoneResponse) {}
}
```

# Scalability in gRPC
Server side can asynchronously handle multiple requests at the same time.
Client side can also asynchronously handle multiple requests at the same time or block until all requests are done.
gRPC can handle 10B+ gRPC requests per day at Google.

# Security in gRPC
gRPC has built-in support for authentication, load balancing, health checking, and more.
Schema based serialization is a good way to validate the data. It's like a contract between the client and the server.
Easy SSL certificate management.
Interceptors to provide authentication, logging, monitoring, etc. They are come with the gRPC library.


| gRPC             | REST                   |
|------------------|------------------------|
| Protocol Buffers | JSON                   |
| HTTP/2           | HTTP/1.1               |
| Streaming        | Unary                  |
| Bidirectional    | Client to Server       |
| Free Design      | GET/POST/UPDATE/DELETE |


# Deadlines in gRPC
Deadlines are a way to tell a server how much time it has to process a request.
If the deadline is exceeded, the server can cancel the request and return an error to the client.
Deadlines are propagated across gRPC calls.


# TLS in gRPC
gRPC has built-in support for TLS encryption.
TLS is the successor to SSL.
We can also use self-signed certificates for development.

Server needs `server.crt` and `server.pem` files. (We will use `openssl` to create them)
- `server.crt` is the public key.
- `server.pem` is the private key.
Client needs `ca.crt` file.