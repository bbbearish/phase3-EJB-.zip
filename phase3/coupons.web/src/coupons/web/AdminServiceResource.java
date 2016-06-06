package coupons.web;


import java.util.List;

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
import coupons.core.AdminFacade;
import coupons.core.ClientType;
import coupons.core.CouponException;
import coupons.core.CouponSystem;
import coupons.web.beans.Message;
import income.pay.income;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
public class AdminServiceResource {
	private static final String ADMIN_SESSION_ATTR = "admin";
	@EJB
	private IncomeDelegate dao;

	@GET
	@Path("/login/{username}/{password}")
	public Message login (
			@PathParam("username") String name, 
			@PathParam("password") String password, 
			@Context HttpServletRequest request) throws CouponException{
		AdminFacade adminFacade = (AdminFacade) CouponSystem.getInstance().login(name, password, ClientType.ADMIN);
		request.getSession(true).setAttribute(ADMIN_SESSION_ATTR, adminFacade);
		return new Message("Successfully logged in as administrator");
	}

	@GET
	@Path("/logout")
	public Message logout (
			@Context HttpServletRequest request) throws CouponException{
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute(ADMIN_SESSION_ATTR) == null){
			throw new CouponException("You must be logged in first");
		}
		if (session != null){
			session.invalidate();
		}
		return new Message("Successfully logged out administrator");
	}
	//16.05
	@GET 
	@Path("/getAllIncome")
	public income[] getAllIncome(){
		List<income> income = dao.getAllIncome();
		return income.toArray(new income[0]);

	}//16.05
	@GET 
	@Path("/getAllIncomeCompany/{companyId}")
	public income[]  getByCompany(@PathParam("companyId") long companyId){
		List<income> incomes = dao.getIncomeByCompanyId(companyId);
		return incomes.toArray(new income[0]);

	}//16.05
	@GET 
	@Path("/getAllIncomeCustoemr/{customerId}")
	public income[] getByCustomer(@PathParam("customerId") long customerId){
		List<income> income = dao.getIncomeByCustomerId(customerId);
		return income.toArray(new income[0]);

	}
}