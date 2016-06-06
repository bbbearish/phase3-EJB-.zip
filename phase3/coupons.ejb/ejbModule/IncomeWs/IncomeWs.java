package IncomeWs;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import IncomeDao.IncomeDao;
import income.pay.income;

//@Stateless
//@WebService
public class IncomeWs {
	@EJB
	public IncomeDao dao;

	@WebResult(name="Income")
	public income[] getAll(){
		return dao.getAllIncome().toArray(new income[0]);

	}
	@WebResult(name="Income")
	public List<income> getByCompany(@WebParam(name="companyId") long companyId){
		return dao.getIncomeByCompanyId(companyId);
		
	}
	@WebResult(name="Income")
	public List<income> getByCustomer(@WebParam(name="customerId") long customerId){
		return dao.getIncomeByCustomerId(customerId);
	
	}
	
}
