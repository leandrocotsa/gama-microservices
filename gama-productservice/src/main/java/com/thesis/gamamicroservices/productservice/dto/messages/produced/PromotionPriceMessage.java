package com.thesis.gamamicroservices.productservice.dto.messages.produced;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

//para a view atualizar os preços
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromotionPriceMessage {
    private Map<Integer,Double> productsIds_and_prices;

}
