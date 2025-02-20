package org.example.grpcserver.service;

import com.example.proto.unary.UnaryRequest;
import com.example.proto.unary.UnaryResponse;
import com.example.proto.unary.UnaryServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.example.grpcserver.interceptor.DummyInterceptor;
import org.example.grpcserver.model.ScopedBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService(interceptors = DummyInterceptor.class, blendWithGlobalInterceptors = true)
public class DummyService extends UnaryServiceGrpc.UnaryServiceImplBase {
    /*
     * Normally, when we add interceptors to a service, it works in a sequence.
     * global interceptors -> service interceptors -> method interceptors
     * When we set blendWithGlobalInterceptors = true, it will work with @Order annotation.
     * First will work that @Order value is less.
     */

    ScopedBean scopedBean;

    ScopedBean singletonBean;

    @Autowired
    public DummyService(ScopedBean scopedBean, ScopedBean singletonBean) {
        this.scopedBean = scopedBean;
        this.singletonBean = singletonBean;
    }

    @Override
    public void dummyMethod(UnaryRequest request, StreamObserver<UnaryResponse> responseObserver) {
        UnaryResponse response = UnaryResponse.newBuilder()
                .setName("Hello " + request.getName() + "!")
                .setScopedBeanCounter(scopedBean.increment())
                .setDefaultBeanCounter(singletonBean.increment())
                .build();
        responseObserver.onNext(response); // Send response to client
        responseObserver.onCompleted(); // Close the connection
    }

    /*
    Theoretically it is not necessary to extend the ImplBase and instead implement BindableService yourself.
    However, doing so might result in bypassing spring security’s checks.
     */
}
