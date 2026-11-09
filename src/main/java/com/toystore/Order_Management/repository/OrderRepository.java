package com.toystore.Order_Management.repository;

import com.toystore.common.FileHandler;
import com.toystore.Order_Management.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// COMPONENT 03: Order Management | Owner: Placeholder
@Repository
public class OrderRepository {
    private final FileHandler fileHandler;

    public OrderRepository() {
        this.fileHandler = new FileHandler("orders.txt");
    }

    public synchronized void save(Order order) {
        fileHandler.appendLine(order.toFileString());
    }