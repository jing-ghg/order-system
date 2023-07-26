package com.backend.ordersystem;

import com.backend.ordersystem.DTO.OrderRequestDTO;
import com.backend.ordersystem.DTO.OrderResponseDTO;
import com.backend.ordersystem.domain.OrderSystem;
import com.backend.ordersystem.repository.OrderSystemRepository;
import com.backend.ordersystem.service.OrderService;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.shadow.com.univocity.parsers.common.NormalizedString.toArray;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderSystemApplicationTests {

	@Autowired
	private MockMvc mockMvc;


	@Mock
	private OrderService orderService;

	@Mock
	private OrderSystem orderSystem;

	@Mock
	private OrderSystemRepository orderSystemRepository;


	@Mock
	private OrderRequestDTO orderRequestDTO;

	@Test
	public void testPlaceOrder() throws Exception {

		given(orderService.takeOrder(anyString(), anyInt())).willReturn("SUCCESS");
		mockMvc.perform(post("/orders").content("{\n" +
						"    \"origin\": [\"37.419734\",\"-122.0827784\"],\n" +
						"    \"destination\": [\"37.417170\", \"-122.079595\"]\n" +
						"}").contentType("application/json"))
				.andExpect(status().isCreated())
				.andReturn();


	}
	@Test
	public void testGetOrdersWithException() throws Exception {
		List<OrderResponseDTO> orders = Arrays.asList(
				new OrderResponseDTO(new OrderSystem(1F, 2F, 3F, 4F, 10, "UNASSIGNED"))
		);
		given(orderService.getOrderList(anyInt(), anyInt())).willReturn(orders);
			mockMvc.perform(get("/orders?page=-1&limit=10"))
					.andExpect(status().isBadRequest())
					.andReturn();
	}



	@Test
	public void testTakeOrderWithException() throws Exception {

		mockMvc.perform(patch("/orders?id=1").content("{\"status\":\"ORDER\"}"))
				.andExpect(status().isBadRequest())
				.andReturn();
	}

}
