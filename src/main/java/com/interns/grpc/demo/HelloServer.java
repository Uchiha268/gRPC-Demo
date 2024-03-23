package com.interns.grpc.demo;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class HelloServer {
    private final int port;
    private final Server server;

    public HelloServer(int port) {
        this.port = port;
        this.server = ServerBuilder.forPort(port)
                .addService(new HelloService())
                .build();
    }

    public void start() throws IOException{
        server.start();
        System.out.println("Server has started " + this.port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            this.stop();
        }));
    }
    public void stop() {
        if(server != null){
            server.shutdown();
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException{
        HelloServer server = new HelloServer(8080);
        server.start();
        server.server.awaitTermination();
    }
}
