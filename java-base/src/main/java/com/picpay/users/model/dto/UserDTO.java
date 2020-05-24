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
public class UserDTO {

	private Long id;

	@NotNull(message = "FullName cannot be null")
	private String fullName;

	@NotNull(message = "Cpf cannot be null")
	private String cpf;

	private String phoneNumber;
	
	@NotNull(message = "Email cannot be null")
	private String email;
	
	private String password;

}
