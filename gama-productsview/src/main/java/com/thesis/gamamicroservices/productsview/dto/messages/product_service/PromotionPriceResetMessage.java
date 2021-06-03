package com.thesis.gamamicroservices.productsview.dto.messages.product_service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//para a view atualizar os preços
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromotionPriceResetMessage {
    List<Integer> productsEnded;
}
