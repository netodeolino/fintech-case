package com.picpay.users.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.picpay.users.model.dto.TransactionDTO;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional = false)
	private User payee;
	
	@ManyToOne(optional = false)
	private User payer;
	
	private Double value;
	
	@JsonFormat(pattern = "dd-MM-yyy@HH:mm:ss.SSSZ")
	private Date transactionDate;

	public static Transaction mapFromDTO(TransactionDTO transactionDTO) {
		User payee = new User();
		payee.setId(transactionDTO.getPayeeId());

		User payer = new User();
		payer.setId(transactionDTO.getPayerId());

		return new Transaction(transactionDTO.getId(), payee, payer, transactionDTO.getValue(),
				transactionDTO.getTransactionDate());
	}

	public static TransactionDTO mapFromEntity(Transaction transaction) {
		return new TransactionDTO(transaction.getId(), transaction.getPayee().getId(), transaction.getPayer().getId(),
				transaction.getValue(), transaction.getTransactionDate());
	}

}
