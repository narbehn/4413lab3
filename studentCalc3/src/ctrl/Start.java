package ctrl;
	//package studentCalc1;

import java.io.IOException;
import java.io.Writer;

//import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Start
 */
@WebServlet({"/Start", "/Start/*", "/StartUp", "/StartUp/*"})
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private int flag1, flag2 = 0;
	private Double prevPrinc = null;
	private Double prevPer = null;
	private Double formula;
	 String ui = "/UI.jspx";
	 String result = "/Result.jspx";
	 
	//lab3 stuff
	 private Double storedPrincipal = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Start() {
        super();
        // TODO Auto-generated constructor stub
        //in here, the println gets printed to the console instead
        //of in the html
    }

	/**?principal=2500
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		
		//String fresh = request.getParameter("submit");
		Writer resOut = response.getWriter();		
		
		
		//principal gives null points exception ?
		//double principal = Double.parseDouble(this.getServletContext().getInitParameter("principal"));//whats this do
		//so "principal" throws exception on start up while "principle" doesn't get me any exception...
		
		
		String princ = request.getParameter("principal"); 
		String period = request.getParameter("period"); 
		String interest = request.getParameter("interest"); 
		String submit = request.getParameter("Submit");
		//THE PROBLEM WAS HERE!!
        //URL IS FUCKING CASE SENSITIVE I HAD LOWERCASE 'S' IN SUBMIT FUCK
		String graceParam = request.getParameter("gracePeriod");
		double prnc = 0;
		double prd = 0;
		double intrst = 0;	//rate from prev. lab
		double finalint = 0;
		double graceInterest = 0;
		
		//double fixedInt = Double.parseDouble(this.getServletContext().getInitParameter("fixedInterest"));
		double gracePeriod = Double.parseDouble(this.getServletContext().getInitParameter("gracePeriod"));
		//Double prevPrinc = null;//request.getParameter("principal");
		//Double prevPer = null;//request.getParameter("period");
		if(submit == null) {	//forward to UI
			request.getRequestDispatcher(ui).forward(request, response);
		}
		else {
			if(graceParam == null) 
			{
				
				if(princ != null)
				{
					prnc = Double.parseDouble(princ);
					prevPrinc = prnc;
					flag1 = 1;
				}
				else if(flag1== 0){ //i.e. princ is null
					prnc = Double.parseDouble(this.getServletContext().getInitParameter("principal"));
					prevPrinc = prnc;
				}
				else{
					prnc = prevPrinc;
				}
				///////////
				if(period != null)
				{
					prd = Double.parseDouble(period);
					prevPer = prd;
					flag2 = 1;
				}
				else if(flag2== 0){
					prd = Double.parseDouble(this.getServletContext().getInitParameter("period"));
					prevPer = prd;
				}
				else{
					prd = prevPer;
				}
				/////////////////
				if(interest != null){
					intrst = Double.parseDouble(interest);
				}
				else{		
					intrst = Double.parseDouble(this.getServletContext().getInitParameter("interest"));
				}
				finalint = intrst + Double.parseDouble(this.getServletContext().getInitParameter("fixedInterest"));

			formula = ((0.01*finalint)/12)*prnc/(1-Math.pow(1+((0.01*finalint)/12), (-1)*prd));
		}
		/*resOut.write("principal= " + prnc + " & period = " + prd+ " & interest = " + intrst + "\n");*/
	
			else {
				if(princ != null)
				{
					prnc = Double.parseDouble(princ);
					prevPrinc = prnc;
					flag1 = 1;
				}
				else if(flag1== 0){ //i.e. princ is null
					prnc = Double.parseDouble(this.getServletContext().getInitParameter("principal"));
					prevPrinc = prnc;
				}
				else{
					prnc = prevPrinc;
				}
				///////////
				if(period != null)
				{
					prd = Double.parseDouble(period);
					prevPer = prd;
					flag2 = 1;
				}
				else if(flag2== 0){
					prd = Double.parseDouble(this.getServletContext().getInitParameter("period"));
					prevPer = prd;
				}
				else{
					prd = prevPer;
				}

				if(interest != null){
					intrst = Double.parseDouble(interest);
				}
				else{		
					intrst = Double.parseDouble(this.getServletContext().getInitParameter("interest"));
				}
				
				finalint = intrst + Double.parseDouble(this.getServletContext().getInitParameter("fixedInterest"));
				double formula_monthly = ((0.01*finalint)/12)*prnc/(1-Math.pow(1+((0.01*finalint)/12), (-1)*prd));
			
				graceInterest= prnc*((finalint*0.01)/12)*gracePeriod;
		
				formula = formula_monthly + (graceInterest/gracePeriod);
				//resOut.write("\n New Monthly Payment: " + Math.round(newFormula* 100.0) /100.0);
			}
				//double formula = ((0.01*intrst)/12)*prnc/(1-Math.pow(1+((0.01*intrst)/12), (-1)*prd));	
		/*resOut.write("\n Monthly Payment: " + Math.round(formula* 100.0) /100.0);*/
	
		request.getSession().setAttribute(princ, storedPrincipal);
		/*resOut.write("\n New Monthly Payment: " + Math.round(newFormula* 100.0) /100.0);*/
		request.setAttribute("principal",Math.round(formula* 100.0) /100.0);
        request.setAttribute("interest",Math.round(graceInterest* 100.0) /100.0);
		request.getRequestDispatcher(result).forward(request, response);

		}
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}