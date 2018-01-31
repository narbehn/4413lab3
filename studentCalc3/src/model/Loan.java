package model;

public class Loan {
	
	public double computePayment(double principal, double period, double interest, 
			double fixedInt, double graceInterest, double gracePeriod, boolean isGrace) throws Exception{
		double formula;
		
		double finalInt = interest + fixedInt;

		formula = ((0.01*finalInt)/12)*principal/(1-Math.pow(1+((0.01*finalInt)/12), (-1)*period));
		if(isGrace) {
			formula += (graceInterest/gracePeriod);

		}
		return formula;
	}
	
	public double computeGraceInterest(double principal, double period, double interest,
		 double fixedInt, double gracePeriod, boolean isGrace) throws Exception{

			double graceInterest;
		double finalInt = interest + fixedInt;
		
		if(gracePeriod < 0 || principal < 0 || interest < 0 || period < 0) {
			throw new Exception();
		}
		
		if(!isGrace) {
			return 0;
		}else {
		 graceInterest= principal*((finalInt*0.01)/12)*gracePeriod;
		
		}

		return graceInterest;
	}
}
