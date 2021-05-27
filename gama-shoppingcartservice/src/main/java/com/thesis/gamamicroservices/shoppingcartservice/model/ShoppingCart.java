package com.thesis.gamamicroservices.shoppingcartservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "shoppingcart")
public class ShoppingCart {
    @Id
    private int userId;

    @OneToMany(mappedBy = "shoppingcart", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ShoppingCartItem> shoppingCartItems;


    public ShoppingCart(int userId) {
        this.shoppingCartItems = new ArrayList<>();
        this.userId = userId;
    }

    public void addShoppingCartItem(ShoppingCartItem newShoppingCartItem) {

        Optional<ShoppingCartItem> matchingObject = shoppingCartItems.stream().
                filter(p -> p.getProductId() == newShoppingCartItem.getProductId()).
                findFirst();
        if(matchingObject.isPresent()) {
            matchingObject.get().setQty(matchingObject.get().getQty() + newShoppingCartItem.getQty());
        } else {
            newShoppingCartItem.setShoppingcart(this);
            shoppingCartItems.add(newShoppingCartItem);
        }

    }

    public void removeCartItem(int toRemoveID) { //se o objeto item viesse da db podia fazer logo .remove(item)?


        shoppingCartItems.removeIf(item -> item.getProductId() == toRemoveID);


/**
 for (int y = 0; y < shoppingCartItems.size(); ++y) {
 ShoppingCartItem item = shoppingCartItems.get(y);
 if(item.getId() == toRemoveID) {
 shoppingCartItems.remove(item);
 return;
 }
 }
 **/
    }

    public void cleanCart() {
        this.shoppingCartItems.clear();
    }

}