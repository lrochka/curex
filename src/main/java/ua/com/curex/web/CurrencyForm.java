/* 
 * Copyright (c) 2015
 */
package ua.com.curex.web;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.constraints.ScriptAssert;

/**
 * @author Irochka (afanasievaiv@ya.ru)
 */
// Requires scripting engine (e.g. Rhino included automatically with Java 6)
@ScriptAssert.List({@ScriptAssert(
			lang = "javascript",
			script = "_this.cur.length>0",
			message = "currencies.isnull.message")})
public class CurrencyForm {

	private String cur;
	
	public String getCur() { return cur; }

	public void setCur(String cur) { this.cur = cur; }
	
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("cur", cur)
			.toString();			
	}
	
}
