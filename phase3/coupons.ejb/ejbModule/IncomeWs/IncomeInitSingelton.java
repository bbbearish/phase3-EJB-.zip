package IncomeWs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import IncomeDao.IncomeDao;

//@Singleton
//@Startup
public class IncomeInitSingelton {
	@EJB
	private IncomeDao dao;

	@PostConstruct
	public void init(){
		try {
			dao.getAllIncome();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
