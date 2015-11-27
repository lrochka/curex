/* 
 * Copyright (c) 2015
 */
package ua.com.curex.web;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.ScriptAssert;

/**
 * @author Irochka (afanasievaiv@ya.ru)
 */
// Requires scripting engine (e.g. Rhino included automatically with Java 6)
@ScriptAssert.List({
@ScriptAssert(
	lang = "javascript",
	script = "_this.confirmPassword.equals(_this.password)",
	message = "account.password.mismatch.message"),
@ScriptAssert(
	lang = "javascript",
	script = "_this.role.length>0",
	message = "account.role.isnull.message")})
public class AccountForm {
	private String username, password, confirmPassword, firstName, lastName, email, phone;
	private String[] role;
	//private boolean isnew;
	private Long idcompany;
	
	@NotNull
	@Size(min = 1, max = 50, message = "Не может быть меньше 1 символа")
	//@Pattern(regexp="^(?:[A-Za-z0-9_])$", message = "Только латинский алфавит, цифры допустимы")
	public String getUsername() { return username; }

	public void setUsername(String userName) { this.username = userName; }

	@NotNull
	@Size(min = 3, max = 50, message = "Не может быть меньше 3 символов")
	public String getPassword() { return password; }

	public void setPassword(String password) { this.password = password; }

	public String getConfirmPassword() { return confirmPassword; }

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@NotNull
	@Size(min = 1, max = 50, message = "Не может быть меньше 1 символа")
	public String getFirstName() { return firstName; }

	public void setFirstName(String firstName) { this.firstName = firstName; }

	@NotNull
	@Size(min = 1, max = 50, message = "Не может быть меньше 1 символа")
	public String getLastName() { return lastName; }

	public void setLastName(String lastName) { this.lastName = lastName; }
	
	@NotNull
	@Size(min = 6, max = 50, message = "Не может быть меньше 6 символов")
	@Email(message = "Введите корректный email адерс")
	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }
	
	@NotNull
	//@Size(min = 10, max = 10, message = "Необходимо ровно 10 цифр!")
	@Pattern(regexp="\\(\\d{3}\\)\\d{3}-\\d{2}-\\d{2}", message="(xxx)xxx-xx-xx")
	//@Digits(integer = 10, fraction = 0, message = "Телефонный номер состоит только из цифр!")
	public String getPhone() { return phone; }

	public void setPhone(String phone) { this.phone = phone; }

	@NotNull(message = "Выберите хотя бы одну роль!")
	//@Size(min = 8, max = 50, message = "Выберите хотя бы одну роль!")
	public String[] getRole() { return role; }

	public void setRole(String[] role) { this.role = role; }
	
	@NotNull
	public Long getCompany() { return idcompany; }
	
	public void setCompany(Long idcompany) { this.idcompany = idcompany; }
	
	//public boolean getIsNew() {return this.isnew; }
	
	//public void setIsNew(boolean isnew){ this.isnew = isnew; }
	
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("username", username)
			.append("firstName", firstName)
			.append("lastName", lastName)
			.append("email", email)
			.append("phone", phone)
			.append("role", role)
			.append("company", idcompany)
			.toString();			
	}		
}
