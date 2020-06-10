package com.challenge.users.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.challenge.users.model.dto.ConsumerDTO;

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
public class Consumer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, length = 75)
	private String username;

	@OneToOne(optional = false)
	private User user;

	public static Consumer mapFromDTO(ConsumerDTO consumerDTO) {
		User user = new User();
		user.setId(consumerDTO.getUserId());
		return new Consumer(consumerDTO.getId(), consumerDTO.getUsername(), user);
	}

	public static ConsumerDTO mapFromEntity(Consumer consumer) {
		return new ConsumerDTO(consumer.getId(), consumer.getUsername(), consumer.getUser().getId());
	}
}
