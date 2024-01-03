package com.fundripple.api.controller;

import com.fundripple.api.mapper.PayPalMapper;
import com.fundripple.api.model.dto.read.PayPalReadModel;
import com.fundripple.api.service.PayPalService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paypal")
public class PayPalController {


    private final PayPalService service;

    public PayPalController(PayPalService service) {
        this.service = service;
    }

    @PostMapping("/pay/{projectName}")
    public PayPalReadModel pay(@RequestParam("amount") Double amount,@PathVariable String projectName) {
        try {
            Payment payment = service.createPayment(amount, "PLN", "paypal",
                    "sale", "Support project",
                    "http://localhost:4200/payment/cancel/"+projectName+"/"+amount,
                    "http://localhost:4200/payment/success/"+projectName+"/"+amount);
            return PayPalMapper.mapToPaymentDTO(payment);
        } catch (PayPalRESTException e) {
            // Handle errors appropriately
        }
        return null;
    }

    @GetMapping("/success")
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return "Success";
            }
        } catch (PayPalRESTException e) {
            // Handle errors appropriately
        }
        return "redirect:/";
    }

    @GetMapping("/cancel")
    public String cancelPay() {
        return "Cancel";
    }
}

