package com.challenge.users.adapter.web;

import com.challenge.users.adapter.in.web.UserController;
import com.challenge.users.domain.dto.*;
import com.challenge.users.domain.entity.User;
import com.challenge.users.application.service.ConsumerService;
import com.challenge.users.application.service.SellerService;
import com.challenge.users.application.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.List;

import static com.challenge.users.util.JsonUtil.mapToJson;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTests {

	private MockMvc mockMvc;

	@Mock
	private UserService userService;

	@Mock
	private ConsumerService consumerService;

	@Mock
	private SellerService sellerService;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private UserController userController;

	private User user;
	private UserDTO userDTO;
	private SellerDTO sellerDTO;
	private ConsumerDTO consumerDTO;
	private AccountsDTO accountsDTO;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
				.build();

		this.user = new User(1L, "PicPay", "12345678901", "88993393232", "picpay@email.com", "12345678");
		this.userDTO = modelMapper.map(this.user, UserDTO.class);
		this.sellerDTO = new SellerDTO(1L, "Social", "Fantasy", "12345678901234", "seller", 1L);
		this.consumerDTO = new ConsumerDTO(1L, "consumer", 1L);
		this.accountsDTO = new AccountsDTO(this.consumerDTO, this.sellerDTO);
	}

	@Test
	public void testFindById() throws Exception {
		UserAccountsDTO userAccountsDTO = new UserAccountsDTO(userDTO, accountsDTO);

		when(userService.findById(anyLong())).thenReturn(userAccountsDTO);

		mockMvc.perform(get("/users/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.user.email", is("picpay@email.com")));
	}

	@Test
	public void testList() throws Exception {
		List<User> users = new ArrayList<>();
		users.add(user);

		when(userService.list(any())).thenReturn(users);

		mockMvc.perform(get("/users")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is(user.getId().intValue())));
	}

	@Test
	public void testSave() throws Exception {
		when(userService.save(any(User.class))).thenReturn(user);

		mockMvc
				.perform(post("/users").contentType(APPLICATION_JSON).content(mapToJson(user)))
				.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.phoneNumber", is(user.getPhoneNumber())));
	}

	@Test
	public void testSaveConsumer() throws Exception {
		when(consumerService.save(any(ConsumerDTO.class))).thenReturn(consumerDTO);

		this.mockMvc
				.perform(post("/users/consumers").contentType(APPLICATION_JSON)
						.content(mapToJson(consumerDTO)))
				.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.username", is(consumerDTO.getUsername())));
	}

	@Test
	public void testSaveSeller() throws Exception {
		when(sellerService.save(anyLong(), any(SellerDTO.class))).thenReturn(sellerDTO);

		this.mockMvc
				.perform(post("/users/sellers/1").contentType(APPLICATION_JSON)
						.content(mapToJson(sellerDTO)))
				.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.username", is(sellerDTO.getUsername())));
	}

}
