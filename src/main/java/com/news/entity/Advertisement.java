                                                                                      package com.news.entity;

import java.time.LocalDate;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "advertisement")
public class Advertisement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long advertisementId;

	private String advertisementTitle;

	private String advertisementDescription;


	private LocalDate publicationDate;

	private LocalDate expiryDate;

	private double advertisementPrice;

	private String advertisementStatus;

	@ManyToOne
	@JoinColumn(name = "newspaper_id")
	private Newspaper newspaper;

	@ManyToOne
	@JoinColumn(name = "advertiser_id")
	private Advertiser advertiser;
	
}
