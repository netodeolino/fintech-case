package com.challenge.users.controller;

import com.challenge.users.model.dto.TransactionDTO;
import com.challenge.users.service.TransactionService;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TransactionControllerTests {

	private MockMvc mockMvc;

	@Mock
	private TransactionService transactionService;

	@InjectMocks
	private TransactionController transactionController;

	private TransactionDTO transactionDTO;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(transactionController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
				.build();

		this.transactionDTO = new TransactionDTO(1L, 1L, 2L, 100.0, new Date());
	}

	@Test
	public void testTransaction() throws Exception {
		when(transactionService.transaction(any(TransactionDTO.class))).thenReturn(transactionDTO);

		JSONObject transJson = new JSONObject();
		transJson.put("payeeId", transactionDTO.getPayeeId());
		transJson.put("payerId", transactionDTO.getPayerId());
		transJson.put("value", transactionDTO.getValue());

		this.mockMvc
				.perform(post("/transactions").contentType(APPLICATION_JSON)
						.content(transJson.toJSONString()))
				.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.payeeId", is(transactionDTO.getPayeeId().intValue())));
	}

	@Test
	public void testFindById() throws Exception {
		when(transactionService.findById(anyLong())).thenReturn(transactionDTO);

		this.mockMvc.perform(get("/transactions/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(transactionDTO.getId().intValue())));
	}
}
