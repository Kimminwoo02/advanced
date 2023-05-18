package com.example.advanced.v2;

import com.example.advanced.trace.TraceId;
import com.example.advanced.trace.TraceStatus;
import com.example.advanced.trace.hellotrace.HelloTraceV1;
import com.example.advanced.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV2 {
    private final OrderServiceV2 orderService;
    private final HelloTraceV2 trace;

    @GetMapping("/v2/request")
    public String request(TraceId traceId, String itemId){

        TraceStatus status = null;
        try{
            status =  trace.begin("OrderController.request()");
            orderService.orderItem(traceId,itemId);
            trace.end(status);
            return "Ok";
        }catch (Exception e){
            trace.exception(status,e);
            throw e; // 예외를 다시 던져주어야 한다.
        }

    }
}
