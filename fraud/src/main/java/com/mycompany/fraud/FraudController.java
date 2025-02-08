package com.mycompany.fraud;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/fraud-check")
public class FraudController {

    private final FraudCheckService fraudCheckService;

    @GetMapping(path = "{customerId}")
    public FraudCheckResponse isFraudster(@PathVariable("customerId")Integer customerID){
        boolean isFraudulentCusomter=fraudCheckService.isFraudulentCustomer(customerID);

        return new FraudCheckResponse(isFraudulentCusomter);
    }

}
