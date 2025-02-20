package org.example.grpcclient.controller;

import lombok.RequiredArgsConstructor;
import org.example.grpcclient.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GrpcServiceCallController {

    @Autowired
    ClientService clientService;

    @GetMapping("/unary")
    public void unary(@RequestParam String name) {
        clientService.callUnaryService(name);
    }

    @GetMapping("/server-streaming")
    public void serverStreaming(
            @RequestParam String name,
            @RequestParam int index
    ) {
        clientService.callServerStreamingService(name, index);
    }

    @GetMapping("/client-streaming")
    public void clientStreaming(
            @RequestParam String name,
            @RequestParam int index
    ) {
        clientService.callClientStreamingService(name, index);
    }

    @GetMapping("/bidirectional-streaming")
    public void bidirectionalStreaming(
            @RequestParam String name,
            @RequestParam int index
    ) {
        clientService.bidirectionalStreamingService(name, index);
    }
}
