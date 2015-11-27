/* 
 * Copyright (c) 2015
 */
package ua.com.curex.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author Irochka
 */
@Entity
@Table(name = "clients")
@NamedQuery(name = "clients.byPhone",
	query = "from Client c where c.phone = :phone")
//@XmlRootElement
public class Client {
	private Long id;
	//@XmlElement(name = "company", required = true)
	private Company company;
	//@XmlElement(name = "author", required = true)
	@JsonIgnore
	private Account author;
	private ClientSource clnSrc;
	private ClientStatus clnStats;
	private String phone, name, addContact;
	private Date dateCreated;
	private int dealCnt, doneDealCnt, dealLateCnt;
	//private Collection<ClientContact> clnContacts = new HashSet<ClientContact>();
	
	public Client() { }
	
	public Client(String phone) { this.phone = phone; }
	/*
	public void addClientContact(ClientContact clnContact) {
        this.clnContacts.add(clnContact);
    }*/
	
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
	@JoinColumn(name="nuser", referencedColumnName = "id")
	public Account getAuthor() { return author; }
	
	public void setAuthor(Account author) { this.author = author; }
	
	@ManyToOne
	@JoinColumn(name="nclninsrc", referencedColumnName = "id")
	public ClientSource getClnSrc() { return clnSrc; }
	
	public void setClnSrc(ClientSource clnSrc) { this.clnSrc = clnSrc; }

	@ManyToOne
	@JoinColumn(name="nclnstats", referencedColumnName = "id")
	public ClientStatus getClnStats() { return clnStats; }
	
	public void setClnStats(ClientStatus clnStats) { this.clnStats = clnStats; }
	
	@NotNull
	@Size(min = 1, max = 50, message = "Не может быть меньше 1 символа")
	@Column(name = "sname")
	public String getName() { return name; }

	public void setName(String name) { this.name = name; }
	
	@Column(name = "sadditionalcontacts")
	public String getAddContact() { return addContact; }

	public void setAddContact(String addContact) { this.addContact = addContact; }
	
	@NotNull
	@Digits(integer = 10, fraction = 0, message = "Телефонный номер состоит из 10 цифр!")
	@Column(name = "sphone")
	public String getPhone() { return phone; }

	public void setPhone(String phone) { this.phone = phone; }

	@Column(name = "ddate_created")
	public Date getDateCreated() { return dateCreated; }
	
	public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }
	
	@Column(name = "ndealcnt")
	public int getDealCnt() { return dealCnt; }

	public void setDealCnt(int dealCnt) { this.dealCnt = dealCnt; }
	
	@Column(name = "ndonedealcnt")
	public int getDoneDealCnt() { return doneDealCnt; }

	public void setDoneDealCnt(int doneDealCnt) { this.doneDealCnt = doneDealCnt; }
	
	@Column(name = "ndeallatecnt")
	public int getDealLateCnt() { return dealLateCnt; }

	public void setDealLateCnt(int dealLateCnt) { this.dealLateCnt = dealLateCnt; }
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", id)
				.append("author", author.getUsername())
				.append("company", company.getCode())
				.append("clnSrc", clnSrc)
				.append("clnStats", clnStats)
				.append("phone", phone)
				.append("name", name)
				.append("addContact", addContact)
				.append("dateCreated", dateCreated)
				.append("dealCnt", dealCnt)
				.append("doneDealCnt", doneDealCnt)
				.append("dealLateCnt", dealLateCnt)
				.toString();
	}
	/*
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Collection<ClientContact> getClnContacts() { return clnContacts; }
	
	public void setClnContacts(Collection<ClientContact> clnContacts) { this.clnContacts = clnContacts; }*/
}
