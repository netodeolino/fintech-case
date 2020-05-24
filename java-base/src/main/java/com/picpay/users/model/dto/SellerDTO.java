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
public class SellerDTO {

	private Long id;

	@NotNull(message = "SocialName cannot be null")
	private String socialName;

	private String fantasyName;

	@NotNull(message = "Cnpj cannot be null")
	private String cnpj;

	@NotNull(message = "Username cannot be null")
	private String username;

	@NotNull(message = "UserId cannot be null")
	private Long user_id;

}
