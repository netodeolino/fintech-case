package com.challenge.users.application.service;

import com.challenge.users.adapter.out.web.TransactionClient;
import com.challenge.users.domain.entity.Transaction;
import com.challenge.users.domain.entity.User;
import com.challenge.users.domain.dto.TransactionDTO;
import com.challenge.users.adapter.out.database.TransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionServiceTests {

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private TransactionClient transactionClient;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private TransactionService transactionService;

	@Test
	public void testFindById() {
		// given
		Long transactionId = 1L;

		User userPayee = new User();
		userPayee.setId(1L);

		User userPayer = new User();
		userPayer.setId(2L);

		Transaction transaction = new Transaction(transactionId, userPayee, userPayer, 100.0, new Date());

		// when
		when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));

		TransactionDTO transactionDTO = transactionService.findById(transactionId);

		// then
		assertThat(transactionDTO.getId()).isEqualTo(transaction.getId());
	}

	@Test
	public void testTransaction() {
		// given
		User userPayee = new User();
		userPayee.setId(1L);

		User userPayer = new User();
		userPayer.setId(2L);

		Transaction transaction = new Transaction(1L, userPayee, userPayer, 100.0, new Date());

		TransactionDTO transactionDTO = modelMapper.map(transaction, TransactionDTO.class);

		// when
		doNothing().when(transactionClient).validateTransaction(transactionDTO);
		when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

		TransactionDTO transactionDTOSaved = transactionService.transaction(transactionDTO);

		// then
		assertThat(transactionDTO.getValue()).isEqualTo(transactionDTOSaved.getValue());
	}

}
