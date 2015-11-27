/* 
 * Copyright (c) 2015
 */
package ua.com.curex.web;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Irochka (afanasievaiv@ya.ru)
 */
// Requires scripting engine (e.g. Rhino included automatically with Java 6)
/*@ScriptAssert.List({
@ScriptAssert(
	lang = "javascript",
	script = "_this.confirmPassword.equals(_this.password)",
	message = "account.password.mismatch.message"),
@ScriptAssert(
	lang = "javascript",
	script = "_this.role.length>0",
	message = "account.role.isnull.message")})*/
public class ClientForm {
	private String name, phone, addContact;
	private Long idcompany;
	
	@NotNull
	@Size(min = 1, max = 50, message = "Не может быть меньше 1 символа")
	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	@NotNull
	@Size(min = 10, max = 10, message = "Необходимо ровно 10 цифр!")
	//@Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}", message="(xxx)xxx-xx-xx")
	@Digits(integer = 10, fraction = 0, message = "Телефонный номер состоит только из цифр!")
	public String getPhone() { return phone; }

	public void setPhone(String phone) { this.phone = phone; }

	@NotNull
	public Long getCompany() { return idcompany; }
	
	public void setCompany(Long idcompany) { this.idcompany = idcompany; }
	
	public String getAddContact() {return addContact;}
	
	public void setAddContact(String addContact) {this.addContact = addContact;}
	
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("name", name)
			.append("phone", phone)
			.append("company", idcompany)
			.toString();			
	}		
}
