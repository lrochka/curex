/* 
 * Copyright (c) 2015
 */
package ua.com.curex.domain;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.*;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author Irochka
 */
@Entity
@Table(name = "companies")
@NamedQueries({
@NamedQuery(name = "companies.byCode", query = "from Company c where c.code = :code"),
@NamedQuery(name = "companies.byId", query = "from Company c where c.id= :id")})
//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "company", propOrder = {"code"})
public class Company {
	private Long id;
	//@XmlElement(required = true)
	protected String code;
	private String name;
	private String city;
	
	@JsonIgnore
	private Collection<Currency> currencies = new HashSet<Currency>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() { return id; }
	
	@SuppressWarnings("unused")
	private void setId(Long id) { this.id = id; }
	
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "scode")
	public String getCode() { return code; }

	public void setCode(String code) { this.code = code; }
	
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "sname")
	public String getName() { return name; }

	public void setName(String name) { this.name = name; }
		
	@Column(name = "scity")
	public String getCity() { return city; }

	public void setCity(String city) { this.city = city; }
	
	@ManyToMany
	@JoinTable(
		name = "companycurrency",
		joinColumns = { @JoinColumn(name = "ncompany", referencedColumnName = "id") },
		inverseJoinColumns = { @JoinColumn(name = "ncurrency", referencedColumnName = "id") })
	public Collection<Currency> getCurrencies() { return currencies; }
	
	public void setCurrencies(Collection<Currency> currencies) { this.currencies = currencies; }
	
	public void addCurrency(Currency c){
		this.currencies.add(c);
	}
	
	public boolean isCurrencyExists(Currency curr){
		for(Currency c : this.getCurrencies())
		{
			if (curr.getAlpha().equals(c.getAlpha())) return true;
		}
		return false;
	}
}
