package com.picpay.users.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.picpay.users.model.dto.TransactionDTO;
import com.picpay.users.service.TransactionService;

import net.minidev.json.JSONObject;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TransactionControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TransactionService transactionService;

	private TransactionDTO transactionDTO;

	@Before
	public void setup() {
		this.transactionDTO = new TransactionDTO(1L, 1L, 2L, 100.0, new Date());
	}

	@Test
	public void testTransaction() throws Exception {
		when(this.transactionService.transaction(Mockito.any(TransactionDTO.class))).thenReturn(this.transactionDTO);

		JSONObject transJson = new JSONObject();
		transJson.put("payee_id", transactionDTO.getPayeeId());
		transJson.put("payer_id", transactionDTO.getPayerId());
		transJson.put("value", transactionDTO.getValue());

		this.mockMvc
				.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
						.content(transJson.toJSONString()))
				.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.payee_id", is(this.transactionDTO.getPayeeId().intValue())));
	}

	@Test
	public void testFindById() throws Exception {
		when(this.transactionService.findById(Mockito.anyLong())).thenReturn(this.transactionDTO);

		this.mockMvc.perform(get("/transactions/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(this.transactionDTO.getId().intValue())));
	}
}
