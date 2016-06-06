package mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import IncomeDao.IncomeDao;
import income.pay.income;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/ExpiryQueue"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") 
})
public class IncomeMDB implements MessageListener {
	
	@EJB
	private IncomeDao dao;

	public void onMessage(Message message) {
		try {
			if (message instanceof ObjectMessage){
				ObjectMessage objectMessage = (ObjectMessage) message;
				Object obj = objectMessage.getObject();
				if (obj instanceof income){
					income income = (income) obj;
					System.out.println("MDB RECIVE MESSAGE " );
					dao.storeIncome(income);
				}else{
					throw new Exception("Unrecognized data object within message");
				}
			}else{
				throw new Exception ("Unrecognized message type");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
