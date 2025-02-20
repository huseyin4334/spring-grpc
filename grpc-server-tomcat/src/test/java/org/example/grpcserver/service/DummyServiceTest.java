package org.example.grpcserver.service;

import com.example.proto.unary.UnaryRequest;
import com.example.proto.unary.UnaryResponse;
import io.grpc.internal.testing.StreamRecorder;
import org.example.grpcserver.model.ScopedBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class DummyServiceTest {

    private DummyService dummyService;

    private ScopedBean scopedBean;
    private ScopedBean singletonBean;

    @BeforeEach
    void setUp() {
        scopedBean = new ScopedBean();
        singletonBean = new ScopedBean();
        dummyService = new DummyService(scopedBean, singletonBean);
    }

    @Test
    void dummyMethod() throws Exception {
        UnaryRequest request = UnaryRequest.newBuilder()
                .setName("Test")
                .build();

        StreamRecorder<UnaryResponse> responseObserver = StreamRecorder.create();
        dummyService.dummyMethod(request, responseObserver);

        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS))
            fail("The call did not terminate in time");

        assertNull(responseObserver.getError());
        List<UnaryResponse> results = responseObserver.getValues();

        assertEquals(1, results.size());
        assertEquals("Hello Test!", results.get(0).getName());
        assertEquals(1, results.get(0).getDefaultBeanCounter());

        assertEquals(UnaryResponse.newBuilder()
                .setName("Hello Test!")
                .setDefaultBeanCounter(1)
                .setScopedBeanCounter(1)
                .build(), results.get(0));
    }
}