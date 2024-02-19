package br.com.microservices.orchestrated.orderservice.core.dto;

import br.com.microservices.orchestrated.orderservice.core.document.OrderProducts;

import java.util.List;

public record OrderRequest(List<OrderProducts> products) {
}
