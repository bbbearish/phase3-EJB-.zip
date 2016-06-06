package IncomeDao;
import java.util.List;

import javax.ejb.Local;

import income.pay.income;

@Local
public interface IncomeDao {
	
	public income storeIncome(income income);
	public List<income>getAllIncome();
	public List<income>getIncomeByCompanyId(Long companyId);
	public List<income>getIncomeByCustomerId(Long customerId);

}
