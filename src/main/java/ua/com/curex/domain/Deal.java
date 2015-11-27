package ua.com.curex.domain;

import java.sql.Date;
import java.sql.Time;
//import java.text.SimpleDateFormat;

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
import javax.validation.constraints.Null;

//import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author Irochka
 */
@Entity
@Table(name = "deals")
@NamedQueries({
@NamedQuery(name = "deals.byId",
			query = "from Deal d where d.id = :id"),
@NamedQuery(name = "deals.countByCompanyDealdrct",
			query = "Select count(*) from Deal d where d.company = :company and d.dealdrct = :dealdrct")})
public class Deal {
	private Long id;
	@JsonIgnore
	private Company company;
	private Date ddate;
	@JsonIgnore
	private Client client;
	private int stagedeal;
	private int isadjust;
	private Time opentime;
	private Time plantime;
	private Time facttime;
	private int dealdrct; //0 - покупка 1 - продажа, 2 - откат
	@JsonIgnore
	private Currency curfrom;
	@JsonIgnore
	private Currency curto;
	private Double currate_plan;
	private int isbribe;
	private long nbribe; // - идентификатор отката может быть нулевым
	private Double currate_bribe;
	private Double sumbribe;
	private int iscross;
	private Double currate_fact;
	private Double sumfrom;
	private Double sumto;
	@JsonIgnore
	private Account author;
	private String comment;
	private int status;
	
	private String namecompany, codeCurFrom, codeCurTo;
	private String typename, clientphone, drctname;
	final String NEW_FORMAT = "MM/dd/yyyy";
	
	@Transient
	public String getDrctname() {
		return drctname;
	}

	public void setDrctname(String drctname) {
		this.drctname = drctname;
	}
	
	public void setDrctname(){
		switch(getDealdrct())
		{
		case 0: setDrctname("Покупка"); break;
		case 1: setDrctname("Продажа"); break;
		case 2: setDrctname("Откат"); break;
		}
	}
	
	@Transient
	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}
	
	public void setTypename(){
		switch(getStagedeal())
		{
		case 0: setTypename("Не закрыта"); break;
		case 1: setTypename("Закрыта"); break;
		}
	}
	
	@Transient
	public String getClientphone() {
		return clientphone;
	}

	public void setClientphone(String clientphone) {
		this.clientphone = clientphone;
	}
	
	@Transient
	public String getNamecompany() {
		return namecompany;
	}

	public void setNamecompany(String namecompany) {
		this.namecompany = namecompany;
	}
	
	@Transient
	public String getCodeCurFrom() {
		return codeCurFrom;
	}

	public void setCodeCurFrom(String codeCurFrom) {
		this.codeCurFrom = codeCurFrom;
	}
	@Transient
	public String getCodeCurTo() {
		return codeCurTo;
	}

	public void setCodeCurTo(String codeCurTo) {
		this.codeCurTo = codeCurTo;
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
	@JoinColumn(name="ncurfrom", referencedColumnName = "id")
	public Currency getCurfrom() { return curfrom; }
	
	public void setCurfrom(Currency currency) { this.curfrom = currency; }
	
	@ManyToOne
	@JoinColumn(name="ncurto", referencedColumnName = "id")
	public Currency getCurto() {
		return curto;
	}

	public void setCurto(Currency curto) {
		this.curto = curto;
	}
	
	@ManyToOne
	@JoinColumn(name="nuser", referencedColumnName = "id")
	public Account getAuthor() { return author; }
	
	public void setAuthor(Account author) { this.author = author; }
	
	@Column(name = "ddate")
	public Date getDdate() { return ddate; }
	
	@NotNull
	private void setDdate(Date ddate) { this.ddate = ddate; }
	
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
	@Transient
	@Column(name = "nbribe")
	public long getNbribe() {
		return nbribe;
	}

	public void setNbribe(long nbribe) {
		this.nbribe = nbribe;
	}
	
	@ManyToOne
	@JoinColumn(name="nclient", referencedColumnName = "id")
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	@Column(name = "nstagedeal")
	public int getStagedeal() {
		return stagedeal;
	}

	public void setStagedeal(int stagedeal) {
		this.stagedeal = stagedeal;
	}
	
	@Column(name = "nisadjust")
	public int getIsadjust() {
		return isadjust;
	}

	public void setIsadjust(int isadjust) {
		this.isadjust = isadjust;
	}
	
	@Column(name = "topentime")
	public Time getOpentime() {
		return opentime;
	}

	public void setOpentime(Time opentime) {
		this.opentime = opentime;
	}
	
	@Column(name = "tplantime")
	public Time getPlantime() {
		return plantime;
	}

	public void setPlantime(Time plantime) {
		this.plantime = plantime;
	}

	@Column(name = "tfacttime")
	public Time getFacttime() {
		return facttime;
	}

	public void setFacttime(Time facttime) {
		this.facttime = facttime;
	}
	
	@Column(name = "ndealdrct")
	public int getDealdrct() {
		return dealdrct;
	}

	public void setDealdrct(int dealdrct) {
		this.dealdrct = dealdrct;
	}
	
	@Column(name = "dcurrate_plan")
	public Double getCurrate_plan() {
		return currate_plan;
	}

	public void setCurrate_plan(Double currate_plan) {
		this.currate_plan = currate_plan;
	}
	
	@Column(name = "nisbribe")
	public int getIsbribe() {
		return isbribe;
	}

	public void setIsbribe(int isbribe) {
		this.isbribe = isbribe;
	}
	
	@Column(name = "niscross")
	public int getIscross() {
		return iscross;
	}

	public void setIscross(int iscross) {
		this.iscross = iscross;
	}
	
	@Column(name = "dsumbribe")
	public Double getSumbribe() {
		return sumbribe;
	}

	public void setSumbribe(Double sumbribe) {
		this.sumbribe = sumbribe;
	}

	@Column(name = "dcurrate_bribe")
	public Double getCurrate_bribe() {
		return currate_bribe;
	}

	public void setCurrate_bribe(Double currate_bribe) {
		this.currate_bribe = currate_bribe;
	}
	
	@Column(name = "dcurrate_fact")
	public Double getCurrate_fact() {
		return currate_fact;
	}

	public void setCurrate_fact(Double currate_fact) {
		this.currate_fact = currate_fact;
	}
	
	@Column(name = "dsumfrom")
	public Double getSumfrom() {
		return sumfrom;
	}

	public void setSumfrom(Double sumfrom) {
		this.sumfrom = sumfrom;
	}
	
	@Column(name = "dsumto")
	public Double getSumto() {
		return sumto;
	}

	public void setSumto(Double sumto) {
		this.sumto = sumto;
	}
	
	/*
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
	}*/

}
