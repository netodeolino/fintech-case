package com.picpay.users.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.picpay.users.model.User;
import com.picpay.users.model.dto.UserAccountsDTO;
import com.picpay.users.repository.UserRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {

	private User user;

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Before
	public void setup() {
		this.user = new User(1L, "PicPay", "12345678901", "88993393232", "picpay@email.com", "12345678");

		when(this.userRepository.findById(anyLong())).thenReturn(Optional.of(this.user));
		when(this.userRepository.findAll()).thenReturn(new ArrayList<User>());
		when(this.userRepository.findByUserName(any())).thenReturn(Optional.of(new ArrayList<User>()));
		when(this.userRepository.findByFullNameStartingWithIgnoreCase(any())).thenReturn(Optional.of(new ArrayList<User>()));
	}

	@Test
	public void testList() {
		List<User> users = this.userService.list(Optional.of("pic"));
		
		assertThat(users.size()).isEqualTo(0);
	}

	@Test
	public void testFindById() {
		UserAccountsDTO uAccountsDTO = this.userService.findById(1L);
		assertThat(uAccountsDTO.getUser().getId()).isEqualTo(this.user.getId());
	}
}
