package com.challenge.users.service;

import com.challenge.users.model.User;
import com.challenge.users.model.dto.ConsumerDTO;
import com.challenge.users.model.dto.SellerDTO;
import com.challenge.users.model.dto.UserAccountsDTO;
import com.challenge.users.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {

	@Mock
	private ConsumerService consumerService;

	@Mock
	private SellerService sellerService;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@Test
	public void testList() {
		// given
		List<User> users = userService.list(Optional.of("pic"));

		// when
		when(userRepository.findAll()).thenReturn(emptyList());

		// then
		assertThat(users.size()).isEqualTo(0);
	}

	@Test
	public void testFindById() {
		// given
		Long userId = 1L;
		User user = new User(1L, "PicPay", "12345678901", "88993393232", "picpay@email.com", "12345678");
		ConsumerDTO consumerDTO = new ConsumerDTO(1L, "pic", userId);
		SellerDTO sellerDTO = new SellerDTO(1L, "pic", "pay", "123", "pic", userId);

		// when
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(consumerService.findByUserId(userId)).thenReturn(consumerDTO);
		when(sellerService.findByUserId(userId)).thenReturn(sellerDTO);

		UserAccountsDTO uAccountsDTO = userService.findById(userId);

		// then
		assertThat(uAccountsDTO.getUser().getId()).isEqualTo(user.getId());
	}
}
