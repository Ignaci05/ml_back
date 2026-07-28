package com.example.mercaditolibre.controller;

import com.example.mercaditolibre.models.VentasEntity;
import com.example.mercaditolibre.service.VentasService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stripe")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@RequiredArgsConstructor

public class StripeController {
    private final VentasService ventasService;
    
    @Value ("${stripe.secret.key}")
    private String stripeApikey;

    @Value("${frontend.url:http://localhost:5173}") 
    private String frontendUrl;

    @PostMapping ("/checkout/{venta_id}")
    public ResponseEntity<Map<String, String>> createCheckoutSession(@PathVariable Long venta_id) throws StripeException{

        Stripe.apiKey = stripeApikey;
        VentasEntity venta = ventasService.getVentaById(venta_id);
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        venta.getDetallesVenta().forEach(detalle ->{
            long precioCentavos = (long)(detalle.getProducto().getPrecio() * 100);

            SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
            .setCurrency("mxn")
            .setUnitAmount(precioCentavos)
            .setProductData(
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(detalle.getProducto().getNombre()).build()
            )
            .build();

            lineItems.add(
                SessionCreateParams.LineItem.builder()
                .setQuantity((long) detalle.getCantidad())
                .setPriceData(priceData)
                .build()
            );
        });

        SessionCreateParams params = SessionCreateParams.builder()
        .setMode(SessionCreateParams.Mode.PAYMENT)
        .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
        .addAllLineItem(lineItems)
        .setClientReferenceId(String.valueOf(venta.getId()))
        .setSuccessUrl(frontendUrl + "/?payment=success&venta_id=" + venta.getId())
        .setCancelUrl(frontendUrl + "/")
        .build();

        Session session = Session.create(params);

        Map<String, String> responseData = new HashMap<>();
        responseData.put("url", session.getUrl());

        return ResponseEntity.ok(responseData);

    }



}
