package com.java.entity;


import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "book_id", nullable = false)
	private Integer bookId;

	@Column(name = "name")
	private String name;

	@Column(name = "unit_price")
	private Double unitPrice;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "total_price")
	private Double totalPrice;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "cart_id", nullable = false)
	@JsonIgnore
	private Cart cart;  // Thêm liên kết đến Cart

	@ManyToOne
	@JoinColumn(name = "book_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Book book;
}
