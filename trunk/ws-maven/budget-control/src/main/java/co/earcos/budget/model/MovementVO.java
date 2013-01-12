package co.earcos.budget.model;

import java.util.Date;

import co.earcos.budget.util.Constants.Account;

/**
 * 
 * @author Erik
 */
public class MovementVO {

	private Integer id;
	private Date date;
	private Account account;
	private Double value;
	private String concept;
	private String observation;

	public MovementVO() {
	}

	public MovementVO(Date date, Account account, Double value, String concept,
			String observation) {
		this.date = date;
		this.account = account;
		this.value = value;
		this.concept = concept;
		this.observation = observation;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
}