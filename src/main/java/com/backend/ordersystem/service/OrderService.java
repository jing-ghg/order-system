package com.backend.ordersystem.service;

import com.backend.ordersystem.DTO.OrderResponseDTO;
import com.backend.ordersystem.domain.CustomResponseStatusException;
import com.backend.ordersystem.domain.OrderSystem;
import com.backend.ordersystem.mapper.OrderSystemMapper;
import com.backend.ordersystem.repository.OrderSystemRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {
    @Value("${google.map.api.key}")
    private String apiKey;
    @Autowired
    private OrderSystemRepository orderSystemRepository;

    public OrderSystem createOrder(OrderSystem order) {
        return orderSystemRepository.save(order);
    }

    public String takeOrder(String statusObject, Integer orderid) {
        JsonObject request = new Gson().fromJson(statusObject, JsonObject.class);
        String status = request.get("status").getAsString();
        if (!status.equals("TAKEN"))
        {
            throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, "Status value is not valid");
        }
        OrderSystem checkIfColumnHasModified = orderSystemRepository.findByOrderidAndStatus(orderid, "TAKEN");
        if (checkIfColumnHasModified != null)
        {
            throw new CustomResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Order has been taken by others");
        }

        int response = orderSystemRepository.updateStatus(status, orderid);
        if (response != 0){
            return "SUCCESS";
        }
        return "";


    }

    public List<OrderResponseDTO> getOrderList(Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page, limit);

        Page<OrderSystem> ordersPage = orderSystemRepository.findAll(pageable);
        List<OrderSystem> orders = ordersPage.getContent();
        OrderSystemMapper orderSystemMapper = Mappers.getMapper(OrderSystemMapper.class);
        List<OrderResponseDTO> orderDTOs = orderSystemMapper.toDtoList(orders);
        return orderDTOs;
    }



    public int calculateDistance(String[] origin, String[] destination) {
        if (!(origin[0] instanceof String)||!(origin[1] instanceof String)||!(destination[0] instanceof String)||!(destination[0] instanceof String)){
            throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, "Origin and destination must be in String format");
        }
        for (int i=0; i<origin.length;i++){
            try{
                Float.parseFloat(origin[i]);
            }catch (NumberFormatException e){
                throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, "Origin is not a valid number");
            }
        }
        for (int i=0; i<destination.length;i++){
            try{
                Float.parseFloat(destination[i]);
            }catch (NumberFormatException e){
                throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, "Destination is not a valid number");
            }
        }

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("https://routes.googleapis.com/directions/v2:computeRoutes").queryParam("key", apiKey);
        String url = builder.build().toString();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Goog-FieldMask","routes.distanceMeters");
        Map<String, Object> originLatLong = new HashMap<>();
        originLatLong.put("latitude", origin[0]);
        originLatLong.put("longitude", origin[1]);
        Map<String, Object> destinationLatLong = new HashMap<>();
        destinationLatLong.put("latitude", destination[0]);
        destinationLatLong.put("longitude", destination[1]);
        Map<String, Object> originlatLng = new HashMap<>();
        originlatLng.put("latLng", originLatLong);
        Map<String, Object> destinationlatLng = new HashMap<>();
        destinationlatLng.put("latLng",destinationLatLong);
        Map<String, Object> originLocation = new HashMap<>();
        originLocation.put("location",originlatLng);
        Map<String, Object> destinationLocation = new HashMap<>();
        destinationLocation.put("location",destinationlatLng);
        Map<String, Object> body = new HashMap<>();
        body.put("origin",originLocation);
        body.put("destination",destinationLocation);
        String serializedBody = new Gson().toJson(body);




        HttpEntity<String> request = new HttpEntity<>(serializedBody, headers);
        String responseFromApi = restTemplate.postForObject(url, request, String.class);
        JsonObject response = new Gson().fromJson(responseFromApi, JsonObject.class);
        if (response.isEmpty())
        {
            throw new CustomResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Distance is too far");
        }

        JsonArray routes = response.getAsJsonArray("routes");
        Integer distance = routes.get(0).getAsJsonObject().get("distanceMeters").getAsInt();
        return distance;
    }
}
