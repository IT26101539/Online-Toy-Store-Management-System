package com.toystore.Order_Management.model;

import com.toystore.Toy_Management.model.Toy;

// COMPONENT 03: Order Management | Owner: Placeholder
public class CartItem {
    private Toy toy;
    private int quantity;

    public CartItem(Toy toy, int quantity) {
        this.toy = toy;
        this.quantity = quantity;
    }