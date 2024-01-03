package com.fundripple.api.mapper;

import com.fundripple.api.model.dto.read.PayPalReadModel;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

public class PayPalMapper {

    public static PayPalReadModel mapToPaymentDTO(Payment payment) {
        PayPalReadModel dto = new PayPalReadModel();

        dto.setId(payment.getId());
        dto.setIntent(payment.getIntent());
        dto.setState(payment.getState());
        dto.setCreateTime(payment.getCreateTime());

        // Set payer payment method
        if (payment.getPayer() != null && payment.getPayer().getPaymentMethod() != null) {
            dto.setPaymentMethod(payment.getPayer().getPaymentMethod());
        }

        // Set transaction details
        if (payment.getTransactions() != null && !payment.getTransactions().isEmpty()) {
            Transaction transaction = payment.getTransactions().get(0);
            if (transaction.getAmount() != null) {
                dto.setCurrency(transaction.getAmount().getCurrency());
                dto.setTotal(transaction.getAmount().getTotal());
                dto.setDescription(transaction.getDescription());
            }
        }

        // Extract approval and execute URLs
        for (Links link : payment.getLinks()) {
            if ("approval_url".equals(link.getRel())) {
                dto.setApprovalUrl(link.getHref());
            } else if ("execute".equals(link.getRel())) {
                dto.setExecuteUrl(link.getHref());
            }
        }

        return dto;
    }
}
