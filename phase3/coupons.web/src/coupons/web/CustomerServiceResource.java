package coupons.web;

import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import IncomeDelegate.IncomeDelegate;
import coupons.core.ClientType;
import coupons.core.CouponException;
import coupons.core.CouponSystem;
import coupons.core.CustomerFacade;
import coupons.web.beans.Message;
import income.pay.OperationType;
import income.pay.income;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerServiceResource {
	private static final String CUSTOMER_SESSION_ATTR = "customer";
	@EJB
	private IncomeDelegate stub ;
	
	
	
	
	@GET
	@Path("/login/{username}/{password}")
	public Message login (
			@PathParam("username") String name, 
			@PathParam("password") String password, 
			@Context HttpServletRequest request) throws CouponException{
		CustomerFacade customerFacade = (CustomerFacade) CouponSystem.getInstance().login(name, password, ClientType.CUSTOMER);
		request.getSession(true).setAttribute(CUSTOMER_SESSION_ATTR, customerFacade);
		return new Message("Successfully logged in as customer: " + name);
	}
	
	@GET
	@Path("/purchase/{couponName}/{couponPrice}")
	public Message purchaseCoupon(
			@PathParam("couponName") String couponName,
			@PathParam("couponPrice") double couponPrice,
			@Context HttpServletRequest request) throws CouponException{
		CustomerFacade customerFacade = (CustomerFacade) request.getSession(false).getAttribute(CUSTOMER_SESSION_ATTR);
		stub.storeIncome(new income(null,customerFacade.getId() , couponPrice, new Date(), OperationType.customer_purchase));
		return new Message("Customer #" + customerFacade.getId() + " successfully purcahsed coupon: " + couponName);
	}
	
	@GET
	@Path("/logout")
	public Message logout (
			@Context HttpServletRequest request) throws CouponException{
		HttpSession session = request.getSession(false);
		CustomerFacade customerFacade = (CustomerFacade) request.getSession(false).getAttribute(CUSTOMER_SESSION_ATTR);
		long id = customerFacade.getId();
		if (session != null){
			session.invalidate();
		}
		return new Message("Successfully logged out customer #" + id);
	}
}
