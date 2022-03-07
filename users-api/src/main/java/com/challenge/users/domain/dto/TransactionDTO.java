package com.challenge.users.domain.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

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
public class TransactionDTO implements Serializable {

	private Long id;
	
	@NotNull(message = "Payee cannot be null")
	private Long payeeId;
	
	@NotNull(message = "Payer cannot be null")
	private Long payerId;
	
	@NotNull(message = "Value cannot be null")
	private Double value;
	
	private Date transactionDate;

}
