package com.challenge.users.domain.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.challenge.users.domain.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

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

	@Enumerated(EnumType.STRING)
	private TransactionStatus status;

}
