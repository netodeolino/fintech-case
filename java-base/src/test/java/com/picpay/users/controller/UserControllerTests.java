package com.picpay.users.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.picpay.users.model.User;
import com.picpay.users.model.dto.AccountsDTO;
import com.picpay.users.model.dto.ConsumerDTO;
import com.picpay.users.model.dto.SellerDTO;
import com.picpay.users.model.dto.UserAccountsDTO;
import com.picpay.users.model.dto.UserDTO;
import com.picpay.users.service.ConsumerService;
import com.picpay.users.service.SellerService;
import com.picpay.users.service.UserService;
import com.picpay.users.util.JsonUtil;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UserControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private ConsumerService consumerService;

	@MockBean
	private SellerService sellerService;

	private User user;
	private UserDTO userDTO;
	private SellerDTO sellerDTO;
	private ConsumerDTO consumerDTO;
	private AccountsDTO accountsDTO;
	private UserAccountsDTO userAccountsDTO;

	@Before
	public void setup() {
		this.user = new User(1L, "PicPay", "12345678901", "88993393232", "picpay@email.com", "12345678");
		this.userDTO = User.mapFromEntity(this.user);
		this.sellerDTO = new SellerDTO(1L, "Social", "Fantasy", "12345678901234", "seller", 1L);
		this.consumerDTO = new ConsumerDTO(1L, "consumer", 1L);
		this.accountsDTO = new AccountsDTO(this.consumerDTO, this.sellerDTO);
		this.userAccountsDTO = new UserAccountsDTO(this.userDTO, this.accountsDTO);
	}

	@Test
	public void testFindById() throws Exception {
		when(this.userService.findById(anyLong())).thenReturn(this.userAccountsDTO);

		this.mockMvc.perform(get("/users/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.user.email", is("picpay@email.com")));
	}

	@Test
	public void testList() throws Exception {
		List<User> users = new ArrayList<User>();
		users.add(this.user);

		when(this.userService.list(any())).thenReturn(users);

		this.mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is(this.user.getId().intValue())));
	}

	@Test
	public void testSave() throws Exception {
		when(this.userService.save(any(User.class))).thenReturn(this.user);

		this.mockMvc
				.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.mapToJson(this.user)))
				.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.phone_number", is(this.user.getPhoneNumber())));
	}

	@Test
	public void testSaveConsumer() throws Exception {
		when(this.consumerService.save(any(ConsumerDTO.class))).thenReturn(this.consumerDTO);

		this.mockMvc
				.perform(post("/users/consumers").contentType(MediaType.APPLICATION_JSON)
						.content(JsonUtil.mapToJson(this.consumerDTO)))
				.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.username", is(this.consumerDTO.getUsername())));
	}

	@Test
	public void testSaveSeller() throws Exception {
		when(this.sellerService.save(anyLong(), any(SellerDTO.class))).thenReturn(this.sellerDTO);

		this.mockMvc
				.perform(post("/users/sellers/1").contentType(MediaType.APPLICATION_JSON)
						.content(JsonUtil.mapToJson(this.sellerDTO)))
				.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.username", is(this.sellerDTO.getUsername())));
	}
}
