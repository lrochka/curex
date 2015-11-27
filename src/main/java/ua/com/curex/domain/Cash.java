/* 
 * Copyright (c) 2015
 */
package ua.com.curex.domain;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author Irochka
 */
@Entity
@Table(name = "cashbook")
@NamedQueries({
@NamedQuery(name = "cashbook.byCompanyCurrencyDateType",
			query = "from Cash c where c.company = :company and c.currency = :currency and c.date = :date and c.type=:type"),
@NamedQuery(name = "cashbook.byCompanyCurrencyDate",
	query = "from Cash c where c.company = :company and c.currency = :currency and c.date = :date"),
@NamedQuery(name = "cashbook.byCompanyCurrency",
query = "from Cash c where c.company = :company and c.currency = :currency"),
@NamedQuery(name = "cashbook.byCompanyDate",
query = "from Cash c where c.company = :company and c.date = :date"),
@NamedQuery(name = "cashbook.byId",
query = "from Cash c where c.id=:id")})
public class Cash {
	private Long id;
	@JsonIgnore
	private Company company;
	@JsonIgnore
	private Currency currency;
	@JsonIgnore
	private Account author;
	private int type; // 0 - остаток на начало дня, 1 - оборот за день, 2 - остаток на конец дня
	private Date date;
	private Double sum;
	private Double sumOut;
	private Double sumIn;
	private Double sumFree;
	private String namecompany, codecurrency;
	
	@Transient
	public String getNamecompany() {
		return namecompany;
	}

	public void setNamecompany(String namecompany) {
		this.namecompany = namecompany;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() { return id; }
	
	@SuppressWarnings("unused")
	private void setId(Long id) { this.id = id; }

	@ManyToOne
	@JoinColumn(name="ncompany", referencedColumnName = "id")
	public Company getCompany() { return company; }
	
	public void setCompany(Company company) { this.company = company; }

	@ManyToOne
	@JoinColumn(name="ncurrency", referencedColumnName = "id")
	public Currency getCurrency() { return currency; }
	
	public void setCurrency(Currency currency) { this.currency = currency; }
	
	@ManyToOne
	@JoinColumn(name="nuser", referencedColumnName = "id")
	public Account getAuthor() { return author; }
	
	public void setAuthor(Account author) { this.author = author; }
	
	@Column(name = "ntype")
	public int getType() { return type; }
	
	@NotNull
	private void setType(int type) { this.type = type; }
	
	@Column(name = "ddate")
	public Date getDate() { return date; }
	
	@NotNull
	private void setDate(Date date) { this.date = date; }
	
	@Column(name = "dsum")
	public Double getSum() { return sum; }
	
	@NotNull
	private void setSum(Double sum) { this.sum = sum; }
	
	@Column(name = "dsum_out")
	public Double getSumOut() { return sumOut; }
	
	@NotNull
	private void setSumOut(Double sumOut) { this.sumOut = sumOut; }

	@Column(name = "dsum_in")
	public Double getSumIn() { return sumIn; }
	
	@NotNull
	private void setSumIn(Double sumIn) { this.sumIn = sumIn; }
	
	@Column(name = "dsum_free")
	public Double getSumFree() { return sumFree; }
	
	@NotNull
	private void setSumFree(Double sumFree) { this.sumFree = sumFree; }

	@Transient
	public String getCodecurrency() {
		return codecurrency;
	}

	public void setCodecurrency(String codecurrency) {
		this.codecurrency = codecurrency;
	}
	
}
