package IncomeDao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import income.pay.OperationType;
import income.pay.income;
@Stateless
public class IncomeDaoBean implements IncomeDao{
	@PersistenceContext(unitName="coupons")
	private EntityManager em;
	
	@Override
	public income storeIncome(income income) {
		em.persist(income);
		return income;
	}

	@Override
	public List<income> getAllIncome() {
		return em.createNamedQuery("GetAllIncome", income.class).getResultList();
	}
	

	@Override
	public List<income> getIncomeByCustomerId(Long customerId) {
		
			return 
			em.createNamedQuery("GetAllCustomers", income.class)
				.setParameter("customerId", customerId)
				.setParameter("customerPurchase", OperationType.customer_purchase)
				.getResultList();
		}
		

	@Override
	public List<income> getIncomeByCompanyId(Long companyId) {

		return 
				em.createNamedQuery("GetAllCompanies", income.class)
					.setParameter("companyId", companyId)
					.setParameter("companyCreate", OperationType.customer_purchase)
					.getResultList();
			}

}
