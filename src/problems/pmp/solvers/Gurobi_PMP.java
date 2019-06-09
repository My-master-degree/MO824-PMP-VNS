package problems.pmp.solvers;

import java.io.IOException;

import gurobi.GRB;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;
import problems.pmp.PMP_Inverse;

public class Gurobi_PMP {
	public GRBEnv env;
	public GRBModel model;
	public GRBVar[] y;
	public GRBVar[][] x;
	public PMP_Inverse pmp;

	public Gurobi_PMP(String filename) throws IOException {
		this.pmp = new PMP_Inverse(filename);
	}	

	public void populateNewModel(GRBModel model) throws GRBException {
		// variables
		y = new GRBVar[pmp.size];
		x = new GRBVar[pmp.size][pmp.size];

		for (int i = 0; i < pmp.size; i++) {
			y[i] = model.addVar(0, 1, 0.0f, GRB.BINARY, "y[" + i + "]");
			for (int j = 0; j < pmp.size; j++) {
				x[i][j] = model.addVar(0, 1, 0.0f, GRB.BINARY, "x[" + i + "][" + j + "]");
			}
		}
		model.update();

		// objective functions
		GRBLinExpr obj = new GRBLinExpr();
		for (int i = 0; i < pmp.size; i++) {
			for (int j = 0; j < pmp.size; j++) {
				obj.addTerm(pmp.costs[i][j], x[i][j]);
			}
		}

		// constraints
//		\sum_{j=1}^n x_{ij} = 1 - y_i (i \in \{1, ..., N\})
		// 	for each customer
		for (int i = 0; i < pmp.size; i++) {			
			GRBLinExpr leftExpr = new GRBLinExpr();
			//	for each facility
			for (int j = 0; j < pmp.size; j++) {
				if (j != i)
					leftExpr.addTerm(1d, x[i][j]);
			}
			GRBLinExpr rightExpr = new GRBLinExpr();
			rightExpr.addConstant(1);
			rightExpr.addTerm(-1, y[i]);
			model.addConstr(leftExpr, GRB.EQUAL, rightExpr, "customer_"+i+"");
		}
//		x_{ij} \leq y_j (i,j \in \{1, ..., N\})		
		// for each i 
		for (int i = 0; i < pmp.size; i++) {			
			// 	for each j
			for (int j = 0; j < pmp.size; j++) {
				if (i != j) {
					GRBLinExpr leftSidedExpr = new GRBLinExpr();
					leftSidedExpr.addTerm(1, x[i][j]);
					GRBLinExpr rightSidedExpr = new GRBLinExpr();
					rightSidedExpr.addTerm(1, y[j]);
					model.addConstr(leftSidedExpr, GRB.LESS_EQUAL, rightSidedExpr, "facility_"+j);
				}
			}
			
		}		
//		\sum_{j} y_{j} = p		
		// for each j
		GRBLinExpr expr = new GRBLinExpr();
		for (int j = 0; j < pmp.size; j++) {			
			expr.addTerm(1, y[j]);						
		}
		model.addConstr(expr, GRB.EQUAL, pmp.p, "update facility j");
//		y_j \leq x_{jj} j \in N
		// for each j
		for (int j = 0; j < pmp.size; j++) {			
			GRBLinExpr leftExpr = new GRBLinExpr();
			leftExpr.addTerm(1, y[j]);
			GRBLinExpr rightExpr = new GRBLinExpr();
			rightExpr.addTerm(1, x[j][j]);
			model.addConstr(leftExpr, GRB.LESS_EQUAL, rightExpr, "update facility j");
		}		
//		setup
		model.setObjective(obj);
		model.update();
		// maximization objective function
		model.set(GRB.IntAttr.ModelSense, GRB.MINIMIZE);

	}
}
