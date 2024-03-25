package com.interns.grpc.demo;

import com.interns.grpc.demo.stubs.hello.HelloRequest;
import com.interns.grpc.demo.stubs.hello.HelloResponse;
import com.interns.grpc.demo.stubs.hello.HelloServiceGrpc;
import com.interns.grpc.demo.stubs.hello.HelloToEveryoneRequest;
import io.grpc.stub.StreamObserver;

import java.util.Arrays;
import java.util.List;

public class HelloService extends HelloServiceGrpc.HelloServiceImplBase{

    @Override
    public void sayHello(HelloRequest helloRequest, StreamObserver<HelloResponse> responseObserver){
        String name = helloRequest.getName();
        String response = "Hello " + name + "!";
        HelloResponse helloResponse = HelloResponse.newBuilder().setHelloName(response).build();
        responseObserver.onNext(helloResponse);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<HelloRequest> sayHelloToOneByOne(StreamObserver<HelloResponse> responseObserver) {
        return new StreamObserver<HelloRequest>() {
            @Override
            public void onNext(HelloRequest helloRequest) {
                String name = helloRequest.getName();
                String response = "Hello" + name + "!";
                HelloResponse helloResponse = HelloResponse.newBuilder().setHelloName(response).build();
                responseObserver.onNext(helloResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error occured while streaming");
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void sayHelloToPeopleYouKnow(HelloToEveryoneRequest request, StreamObserver<HelloResponse> responseObserver) {
        List<String> names = Arrays.asList("Shreyash", "Pranav", "Vismaya");
        names.forEach((name) -> {
            String response = "Hello " + name + "!";
            HelloResponse helloResponse = HelloResponse.newBuilder().setHelloName(response).build();
            responseObserver.onNext(helloResponse);
        });
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<HelloRequest> sayHelloToEveryone(StreamObserver<HelloResponse> responseObserver) {
       return new StreamObserver<HelloRequest>() {
           private final HelloResponse.Builder helloResponseBuilder = HelloResponse.newBuilder().setHelloName("Hello ");

           @Override
           public void onNext(HelloRequest helloRequest) {
               String name = helloRequest.getName();
               String response = name + ", ";
               helloResponseBuilder.setHelloName(helloResponseBuilder.getHelloName() + response);
           }

           @Override
           public void onError(Throwable throwable) {
            System.out.println("Error occured");
           }

           @Override
           public void onCompleted() {
               responseObserver.onNext(helloResponseBuilder.setHelloName(helloResponseBuilder.getHelloName() + "!").build());
               responseObserver.onCompleted();
           }
       };
    }
}
