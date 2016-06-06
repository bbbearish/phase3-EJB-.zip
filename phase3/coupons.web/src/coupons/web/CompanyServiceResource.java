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
import coupons.core.CompanyFacade;
import coupons.core.CouponException;
import coupons.core.CouponSystem;
import coupons.web.beans.Message;
import income.pay.OperationType;
import income.pay.income;

@Path("/company")
@Produces(MediaType.APPLICATION_JSON)
public class CompanyServiceResource {
	private static final String COMPANY_SESSION_ATTR = "company";
	@EJB
	private IncomeDelegate stub ;
	
	@GET
	@Path("/login/{username}/{password}")
	public Message login (
			@PathParam("username") String name, 
			@PathParam("password") String password, 
			@Context HttpServletRequest request) throws CouponException{
		CompanyFacade companyFacade = (CompanyFacade) CouponSystem.getInstance().login(name, password, ClientType.COMPANY);
		request.getSession(true).setAttribute(COMPANY_SESSION_ATTR, companyFacade);
		return new Message("Successfully logged in as company: " + name);
	}
	
	@GET
	@Path("/coupon/create/{couponName}")
	public Message createCoupon(
			@PathParam("couponName") String couponName,
			@Context HttpServletRequest request) throws CouponException{
		CompanyFacade companyFacade = (CompanyFacade) request.getSession(false).getAttribute(COMPANY_SESSION_ATTR);
		stub.storeIncome(new income(null,companyFacade.getId() , 100, new Date(), OperationType.company_create));
		return new Message("Company #" + companyFacade.getId() + " successfully created coupon: " + couponName);
	}
	
	@GET
	@Path("/coupon/update/{couponName}")
	public Message updateCoupon(
			@PathParam("couponName") String couponName,
			@Context HttpServletRequest request) throws CouponException{
		CompanyFacade companyFacade = (CompanyFacade) request.getSession(false).getAttribute(COMPANY_SESSION_ATTR);
		stub.storeIncome(new income(null,companyFacade.getId() , 10, new Date(), OperationType.company_update));
		return new Message("Company #" + companyFacade.getId() + " successfully updated coupon: " + couponName);
	}

	@GET
	@Path("/logout")
	public Message logout (
			@Context HttpServletRequest request) throws CouponException{
		HttpSession session = request.getSession(false);
		CompanyFacade compnayFacade = (CompanyFacade) request.getSession(false).getAttribute(COMPANY_SESSION_ATTR);
		long id = compnayFacade.getId();
		if (session != null){
			session.invalidate();
		}
		return new Message("Successfully logged out company #" + id);
	}
	
	//16.05
	@GET 
	@Path("/getAllIncome")
	public income[] getAllIncome(
			@Context HttpServletRequest request){
		System.out.println("CompanyServiceResource.getAllIncome()");
		HttpSession session = request.getSession(false);
		CompanyFacade compnayFacade = (CompanyFacade) session.getAttribute(COMPANY_SESSION_ATTR);
		long companyId = compnayFacade.getId();
		return stub.getIncomeByCompanyId(companyId).toArray(new income[0]);
		
	}
}
