package com.fundripple.api.model.dto.read;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayPalReadModel {
    private String id;
    private String intent;
    private String paymentMethod;
    private String currency;
    private String total;
    private String description;
    private String state;
    private String createTime;
    private String approvalUrl;
    private String executeUrl;
}
