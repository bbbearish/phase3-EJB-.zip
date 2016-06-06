package income.pay;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table
@XmlRootElement

@NamedQueries({
	@NamedQuery(name="GetAllIncome", query="SELECT inc  FROM income AS inc ORDER BY inc.incomeID DESC"),
	@NamedQuery(name="GetAllCompanies", query="SELECT inc  FROM income AS inc WHERE inc.operation <> :companyCreate AND inc.payerID = :companyId ORDER BY inc.incomeID DESC"),
	@NamedQuery(name="GetAllCustomers", query="SELECT inc  FROM income AS inc WHERE inc.operation = :customerPurchase AND inc.payerID = :customerId ORDER BY inc.incomeID DESC"),

})
public class income implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long incomeID;
	private long payerID;
	private double amount;
	private Date timeStamp;
	private OperationType  operation;
	
	public income() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public income(Long incomeID, long payerID, double amount, Date timeStamp, OperationType operation) {
		super();
		this.incomeID = incomeID;
		this.payerID = payerID;
		this.amount = amount;
		this.timeStamp = timeStamp;
		this.operation = operation;
	}



	@Override
	public String toString() {
		return "income [incomeID=" + incomeID + ", payerID=" + payerID + ", amount=" + amount + ", timeStamp="
				+ timeStamp + ", operation=" + operation + "]";
	}
	@GeneratedValue
	@Id
	public Long getIncomeID() {
		return incomeID;
	}
	public void setIncomeID(Long incomeID) {
		this.incomeID = incomeID;
	}
	public long getPayerID() {
		return payerID;
	}
	public void setPayerID(long payerID) {
		this.payerID = payerID;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@XmlTransient
	public Date getTimeStamp() {
		return timeStamp;
	}
	
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Transient
	public String getPublicationDateString() {
		return new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss").format(timeStamp);
	}

	public void setPublicationDateString(String timeStamp) throws ParseException {
		this.timeStamp = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss").parse(timeStamp);
	}
	@Enumerated(EnumType.STRING)
	//or ordinal if you want number
	public OperationType getOperation() {
		return operation;
	}
	public void setOperation(OperationType operation) {
		this.operation = operation;
	}
	
	

}
