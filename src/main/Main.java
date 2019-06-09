package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import gurobi.GRB;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBModel;
import metaheuristics.vns.AbstractVNS;
import metaheuristics.vns.LocalSearch;
import problems.Evaluator;
import problems.pmp.Median;
import problems.pmp.Medians;
import problems.pmp.PMP;
import problems.pmp.PMP_Inverse;
import problems.pmp.construction.ConstructionMethod;
import problems.pmp.solvers.Gurobi_PMP;
import problems.pmp.solvers.VNS_PMP;
import solutions.Solution;
import utils.heap.ArrayHeap;
import utils.heap.Heap;
import utils.heap.Util;

public class Main {
	
	public static Integer VNS_ITERATION_MAX_NUMBER = Integer.MAX_VALUE;
	public static Integer VNS_TIME_MAX_MILI_SECONDS = 10000;
	public static Integer EXACT_TIME_MAX_SECONDS = 6000;
	
	public static void main(String[] args) {
		String[] pmp_instances = new String[] {
			"./pmp_instances/instance0.pmp",
			"./pmp_instances/instance1.pmp",
			"./pmp_instances/instance2.pmp",
			"./pmp_instances/instance3.pmp",
			"./pmp_instances/instance4.pmp",
			"./pmp_instances/instance5.pmp",	
			"./pmp_instances/instance6.pmp",
			"./pmp_instances/instance7.pmp",		
			"./pmp_instances/instance8.pmp",			
			"./pmp_instances/instance9.pmp",									
		};		
		try {
			runExacts(pmp_instances);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (GRBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  
	}
	
	public static String runVNS(String[] instances, ConstructionMethod constructionMethod, AbstractVNS.VNS_TYPE vns_type) {		
		String str = "";
		for (int i = 0; i < instances.length; i++) {		
			System.out.println(instances[i]);
			str += instances[i] + "\n";
			try {
				PMP_Inverse bpp = new PMP_Inverse(instances[i]);
				
//				VNS_BPP vns_bpp = new VNS_BPP(bpp, VNS_ITERATION_MAX_NUMBER, VNS_TIME_MAX_MILI_SECONDS, constructionMethod, 
//						local Search, local searchs list, vns_type);
//				Medians sol = new Medians(vns_bpp.solve());
//				str += "\tVNS cost: "+sol.size() + "\n";
//				System.out.println("\tVNS cost: "+sol.size());
//				Util.checkBinPackingSolution(sol, (PMP) vns_bpp.getObjFunction());
				
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
//			break;
			
		}	
		return str;
	}
	
	public static void runExacts(String[] instances) throws IOException, GRBException {
		String str = "";
		for (int i = 0; i < instances.length; i++) {
			str += instances[i] + "\n";
			// instance name
			Gurobi_PMP gurobi = new Gurobi_PMP(instances[i]);
//			gurobi.env = new GRBEnv("mip1.log");
			gurobi.env = new GRBEnv();
			gurobi.model = new GRBModel(gurobi.env);
			// execution time in seconds 
			gurobi.model.getEnv().set(GRB.DoubleParam.TimeLimit, EXACT_TIME_MAX_SECONDS);
			// generate the model
			gurobi.populateNewModel(gurobi.model);
			// write model to file
//			gurobi.model.write("model.lp");
			long time = System.currentTimeMillis();
			gurobi.model.optimize();
			time = System.currentTimeMillis() - time;
			System.out.println("\n\nZ* = " + gurobi.model.get(GRB.DoubleAttr.ObjVal));
			str += "Z* = " + gurobi.model.get(GRB.DoubleAttr.ObjVal)+"\n";
			System.out.println("\n\nObjBound = " + gurobi.model.get(GRB.DoubleAttr.ObjBound));
			str += "ObjBound = " + gurobi.model.get(GRB.DoubleAttr.ObjBound)+"\n";
			System.out.println("\n\nObjBoundC = " + gurobi.model.get(GRB.DoubleAttr.ObjBoundC));
			str += "ObjBoundC = " + gurobi.model.get(GRB.DoubleAttr.ObjBoundC)+"\n";
			System.out.println("\nTime = "+time);
			str += "Time = " + time +"\n";
//			Write file			
//			get solution
			Medians medians = new Medians(); 
			for (int j = 0; j < gurobi.pmp.size; j++) {
				if (gurobi.y[j].get(GRB.DoubleAttr.X) == 1) {
					Median median = new Median(gurobi.pmp, j);
					for (int k = 0; k < gurobi.pmp.size; k++) {
						if (gurobi.x[k][j].get(GRB.DoubleAttr.X) == 1) {						
							median.addCustomers(k);
						}
					}
					medians.add(median);
				}
			}		
			System.out.println(medians);
			gurobi.model.dispose();
			gurobi.env.dispose();
			break;
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter("exacts.txt"));
		writer.write(str);		     
	    writer.close();
	}
}
