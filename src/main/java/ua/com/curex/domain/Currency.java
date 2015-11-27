/* 
 * Copyright (c) 2015
 */
package ua.com.curex.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * @author Irochka 
 */
@Entity
@Table(name = "currencies")
@NamedQueries({
@NamedQuery(name = "currencies.byAlphaCode",
	query = "from Currency c where c.alpha = :alpha"),
@NamedQuery(name = "currencies.byId",
query = "from Currency c where c.id = :id")})
public class Currency {
	private Long id;
	private String numeric, alpha, country, country_rus, currency, currency_rus;
	private byte[] img;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() { return id; }
	
	@SuppressWarnings("unused")
	private void setId(Long id) { this.id = id; }
	
	@NotNull
	@Size(min = 3, max = 3, message = "Только 3 символа")
	@Column(name = "salphabeticcode")
	public String getAlpha() { return alpha; }
	
	public void setAlpha(String alpha) { this.alpha = alpha; }
	
	@NotNull
	@Size(min = 3, max = 3, message = "Только 3 символа")
	@Column(name = "snumericcode")
	public String getNumeric() { return numeric; }
	
	public void setNumeric(String numeric) { this.numeric = numeric; }
	
	@NotNull
	@Size(min = 1, max = 50, message = "Не может быть меньше 1 символа")
	@Column(name = "scountry")
	public String getCountry() { return country; }

	public void setCountry(String country) { this.country = country; }

	@NotNull
	@Size(min = 1, max = 50, message = "Не может быть меньше 1 символа")
	@Column(name = "scountry_rus")
	public String getCountry_rus() { return country_rus; }

	public void setCountry_rus(String country_rus) { this.country_rus = country_rus; }

	@NotNull
	@Size(min = 1, max = 50, message = "Не может быть меньше 1 символа")
	@Column(name = "scurrency")
	public String getCurrency() { return currency; }

	public void setCurrency(String currency) { this.currency = currency; }

	@NotNull
	@Size(min = 1, max = 50, message = "Не может быть меньше 1 символа")
	@Column(name = "scurrency_rus")
	public String getCurrency_rus() { return currency_rus; }

	public void setCurrency_rus(String currency_rus) { this.currency_rus = currency_rus; }
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name = "image")
	public byte[] getImg() { return img; }

	public void setImg(byte[] img) { this.img = img; }
	
	public boolean isEqual(Currency c) {
		if (c.getId()!=this.getId()) return false;
		return true;
	}

}
