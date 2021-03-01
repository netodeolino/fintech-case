package com.challenge.users.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.challenge.users.model.dto.SellerDTO;

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
public class Seller {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String socialName;
	private String fantasyName;
	
	@Column(length = 14)
	private String cnpj;

	@Column(unique = true, length = 75)
	private String username;

	@OneToOne(optional = false)
	private User user;

	public static Seller mapFromDTO(SellerDTO sellerDTO) {
		User user = new User();
		user.setId(sellerDTO.getUser_id());
		return new Seller(sellerDTO.getId(), sellerDTO.getSocialName(), sellerDTO.getFantasyName(),
				sellerDTO.getCnpj(), sellerDTO.getUsername(), user);
	}

	public static SellerDTO mapFromEntity(Seller seller) {
		return new SellerDTO(seller.getId(), seller.getSocialName(), seller.getFantasyName(), seller.getCnpj(),
				seller.getUsername(), seller.getUser().getId());
	}
}
