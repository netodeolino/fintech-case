package com.picpay.users.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.picpay.users.model.dto.UserDTO;

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
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String fullName;

	@Column(unique = true, length = 11)
	private String cpf;

	private String phoneNumber;

	@Column(unique = true, length = 100)
	private String email;

	private String password;

	public static User mapFromDTO(UserDTO userDTO) {
		return new User(userDTO.getId(), userDTO.getFull_name(), userDTO.getCpf(), userDTO.getPhone_number(),
				userDTO.getEmail(), userDTO.getPassword());
	}

	public static UserDTO mapFromEntity(User user) {
		return new UserDTO(user.getId(), user.getFullName(), user.getCpf(), user.getPhoneNumber(),
				user.getEmail(), user.getPassword());
	}
}
