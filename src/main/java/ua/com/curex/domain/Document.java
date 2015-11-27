/* 
 * Copyright (c) 2015
 */
package ua.com.curex.domain;

import java.sql.Date;
import java.text.SimpleDateFormat;

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

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author Irochka
 */
@Entity
@Table(name = "documents")
@NamedQueries({
@NamedQuery(name = "documents.countByCompanyType",
			query = "select count(*) from Document d where d.company = :company and d.type = :type"),
@NamedQuery(name = "documents.byId",
			query = "from Document d where d.id = :id"),
@NamedQuery(name = "documents.byCompanyCurrencyDate",
	query = "from Document d where d.company = :company and d.currency = :currency and d.ddate = :ddate"),
@NamedQuery(name = "documents.byCompanyCurrency",
query = "from Document d where d.company = :company and d.currency = :currency"),
@NamedQuery(name = "remns.byCompanyDate",
query = "from Document d where d.company = :company and d.ddate = :ddate")})
public class Document {
	private Long id;
	@JsonIgnore
	private Company company;
	@JsonIgnore
	private Currency currency;
	@JsonIgnore
	private Account author;
	private int type;
	private int status;
	private Date ddate;
	private Double sum;
	private String comment;
	private String namecompany, codecurrency;
	private String typename;
	final String NEW_FORMAT = "MM/dd/yyyy";
	

	@Transient
	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}
	
	public void setTypename(){
		switch(getType())
		{
		case 0: setTypename("Приход"); break;
		case 1: setTypename("Расход"); break;
		}
	}
	
	@Transient
	public String getNamecompany() {
		return namecompany;
	}

	public void setNamecompany(String namecompany) {
		this.namecompany = namecompany;
	}
	
	@Transient
	public String getCodecurrency() {
		return codecurrency;
	}

	public void setCodecurrency(String codecurrency) {
		this.codecurrency = codecurrency;
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
	public void setType(int type) { this.type = type; }
	
	@Column(name = "ddate")
	public Date getDdate() { return ddate; }
	
	@NotNull
	private void setDdate(Date ddate) { this.ddate = ddate; }
	
	@Column(name = "dsum")
	public Double getSum() { return sum; }
	
	@NotNull
	public void setSum(Double sum) { this.sum = sum; }
	
	@Column(name = "scomment")
	public String getComment() { return comment; }
	
	@NotNull
	public void setComment(String comment) { this.comment = comment; }

	@Column(name = "nstatus")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat(NEW_FORMAT);
		return new ToStringBuilder(this)
				.append("id", id)
				.append("type", type)
				.append("ddate", sdf.format(ddate))
				.append("typename", typename)
				.append("namecompany", namecompany)
				.append("codecurrency", codecurrency)
				.append("sum", sum)
				.append("comment", comment)
				.append("status", status)
				.toString();
	}
}
