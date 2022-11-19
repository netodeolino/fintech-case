package com.challenge.transaction.domain.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

	private Long id;
	private Long payeeId;
	private Long payerId;
	private Double value;
	private Date transactionDate;
	private String status;

}
