	//package studentCalc1;

import java.io.IOException;
import java.io.Writer;

//import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Loan;

/**
 * Servlet implementation class Start
 */
@WebServlet({"/Start", "/Start/*", "/StartUp", "/StartUp/*"})
public class Start extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private double principal = 25500;
	private double interest = 1;
	private double period = 48;
	
	private int flag1, flag2 = 0;
	private Double prevPrinc = null;
	private Double prevPer = null;
	private Double formula;
	private Loan loan;
	 String ui = "/UI.jspx";
	 String result = "/Result.jspx";
	private String errormsg;
	private boolean isError;
	//lab3 stuff
	 private Double storedPrincipal = null;
	 
	 
	 

	private	double graceInterest;
	private	double payment;
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
	
    public void init() throws ServletException{
    	loan = new Loan();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		calculate(request, response);
		update(request);
		check(request, response);
	}
    
	private void update(HttpServletRequest request) {
		this.getServletContext().setAttribute("principal", principal);
		this.getServletContext().setAttribute("period", period);
		this.getServletContext().setAttribute("interest", interest);
		this.getServletContext().setAttribute("payment", payment);
		this.getServletContext().setAttribute("graceInterest", graceInterest);
	}
	private void calculate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String princ = request.getParameter("principal");
			//Double principal = Double.parseDouble(princ);
		String prd = request.getParameter("period");
			//Double period = Double.parseDouble(prd);
		String intrst = request.getParameter("interest"); 
			//Double interest = Double.parseDouble(intrst);
		String submit = request.getParameter("Submit");
		String graceParam = request.getParameter("gracePeriod");
		double prnc = 0;
		//double prd = 0;
		//double intrst = 0;	//rate from prev. lab
		double finalint = 0;
		
		if(!inputIsEmpty(princ, prd, intrst) ) {
			
			principal = Double.parseDouble(princ);
			period = Double.parseDouble(prd);
			interest = Double.parseDouble(intrst);
		double gracePeriod = Double.parseDouble(this.getServletContext().getInitParameter("gracePeriod"));
		double  fixedInt = Double.parseDouble(this.getServletContext().getInitParameter("fixedInterest"));
		isError = false;
		
			try {
				graceInterest = loan.computeGraceInterest(principal, period, interest, fixedInt, gracePeriod, graced(request));
				payment = loan.computePayment(principal, period, interest, fixedInt, graceInterest, gracePeriod, graced(request));

			}
			catch(Exception e){
				isError=true;
				errormsg = "";
				
				if(principal < 0) {
					errormsg= "Principal must be greater than 0!\n";
				}
				else if(period <0) {
					errormsg= "Period must be greater than 0!\n";
				}
				else {
					errormsg= "Interest must be greater than 0!\n";
				}
			}
		}	
			
		//double fixedInt = Double.parseDouble(this.getServletContext().getInitParameter("fixedInterest"));
		//double gracePeriod = Double.parseDouble(this.getServletContext().getInitParameter("gracePeriod"));
		//Double prevPrinc = null;//request.getParameter("principal");
		//Double prevPer = null;//request.getParameter("period");
		
		/*resOut.write("principal= " + prnc + " & period = " + prd+ " & interest = " + intrst + "\n");*/
	
				//double formula = ((0.01*intrst)/12)*prnc/(1-Math.pow(1+((0.01*intrst)/12), (-1)*prd));	
		/*resOut.write("\n Monthly Payment: " + Math.round(formula* 100.0) /100.0);*/	
	}

	private boolean graced(HttpServletRequest request) {
		String grace= request.getParameter("gracePeriod");
		if(grace==null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	private void displayError() {
    	this.getServletContext().setAttribute("errorMessage", errormsg);
    }
	
	private boolean inputIsEmpty(String principal, String period, String interest) {
		//if any of the fields are empty, return
		if(principal.isEmpty() || interest.isEmpty() || period.isEmpty()) {
			return true;
		}
		else {
			try {
				Double.parseDouble(principal);
				Double.parseDouble(period);
				Double.parseDouble(interest);
			}catch(Exception e) {
				return true;
			}
			
		}
		return false;
	}
	
	
	
	private void check(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String target = ui;
		String submitParameter = request.getParameter("Submit");
		String restartParameter = request.getParameter("restart");

		if (restartParameter != null && restartParameter.equals("Recompute")) {
			errormsg = "";
			displayError();
		} else if (submitParameter != null && submitParameter.equals("submit")) {
			if (!isError) {
				target = result;
			}
		}
		try {
			request.getRequestDispatcher(target).forward(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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