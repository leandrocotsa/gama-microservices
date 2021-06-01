package com.thesis.gamamicroservices.productservice.dto.messages.consumed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromotionEndedMessage {
    List<Integer> productsEnded;
}
