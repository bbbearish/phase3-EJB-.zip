package IncomeDelegate;
 
import java.util.List;

import javax.ejb.Remote;

import income.pay.income;

@Remote
public interface IncomeDelegate {
	//16.05

	public void storeIncome(income income);
	public List<income>getAllIncome();
	public List<income>getIncomeByCompanyId(long companyId);
	public List<income>getIncomeByCustomerId(long customerId);
	
	

}
