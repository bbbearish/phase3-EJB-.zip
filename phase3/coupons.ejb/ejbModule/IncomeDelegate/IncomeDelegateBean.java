package IncomeDelegate;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;

import IncomeDao.IncomeDao;
import income.pay.income;

@Stateless
public class IncomeDelegateBean implements IncomeDelegate{
	private QueueConnectionFactory factory;
	private QueueConnection connection;
	private QueueSession session;
	private Queue destination;
	private QueueSender producer;
	private ObjectMessage message;

	//16.05

	@EJB
	private IncomeDao dao;

	@Override
	public void storeIncome(income income) {
		try {
			//income income1 = new income();
			this.message.setObject(income);
			producer.send(this.message);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	@PostConstruct
	public void init(){
		try {
			InitialContext ctx = new InitialContext();
			 factory = (QueueConnectionFactory) ctx.lookup("ConnectionFactory");
			connection = factory.createQueueConnection();
			session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			 destination = (Queue) ctx.lookup("java:/jms/queue/ExpiryQueue");
			producer = session.createSender(destination);
			message = session.createObjectMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PreDestroy
	public void destroy(){
		try {
			producer.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//16.05

	@Override
	public List<income> getAllIncome() {
		return dao.getAllIncome();

	}


	@Override
	public List<income> getIncomeByCompanyId(long companyId) {
		return dao.getIncomeByCompanyId(companyId);
	}


	@Override
	public List<income> getIncomeByCustomerId(long customerId) {
		return dao.getIncomeByCustomerId(customerId);
	}
	
}


