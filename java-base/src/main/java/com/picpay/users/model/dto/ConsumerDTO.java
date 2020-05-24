package com.picpay.users.model.dto;

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
public class ConsumerDTO {

	private Long id;

	@NotNull(message = "Username cannot be null")
	private String username;

	@NotNull(message = "UserId cannot be null")
	private Long userId;

}
