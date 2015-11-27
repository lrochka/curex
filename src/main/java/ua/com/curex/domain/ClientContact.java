/* 
 * Copyright (c) 2015
 */
package ua.com.curex.domain;

/**
 * @author Irochka
 */
public class ClientContact {
	
}
/*
@Entity
@Table(name = "clncontacts")
public class ClientContact {
	private long id;
	private ClientTypeContact clnTypeContact;
	private Client client;
	private String value;
	private Date dateCreated;
	
	@Id
    @GeneratedValue
    @Column(name = "id")
    public long getId() {
        return id;
    }
 
    public void setId(long id) {
        this.id = id;
    }
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="nclient")
	public Client getClient() {return client;}
    
    public void setClient(Client client) {this.client = client;}
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="nclntypecontact")
	public ClientTypeContact getClnTypeContact() { return clnTypeContact; }
	
	public void setClnTypeContact(ClientTypeContact clnTypeContact) { this.clnTypeContact = clnTypeContact; }
	
	@Column(name = "value")
    public String getValue() { return value; }

	public void setValue(String value) { this.value = value; }

	@Column(name = "ddate_created")
	public Date getDateCreated() { return dateCreated; }
	
	public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }
	
}*/
