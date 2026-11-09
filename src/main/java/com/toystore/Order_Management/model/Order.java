package com.toystore.Order_Management.model;

import java.time.LocalDateTime;

// COMPONENT 03: Order Management | Owner: Placeholder
public class Order {
    private String id;
    private String userId;
    private String itemsSummary; // Format: toyId1:qty1,toyId2:qty2
    private double totalAmount;
    private String deliveryAddress;
    private String status; // PENDING,SHIPPED,DELIVERED,CANCELLED
    private String orderDate;