package utils.heap;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import problems.pmp.Median;
import problems.pmp.Medians;
import problems.pmp.PMP;
import solutions.Solution;

public class Util {
	public static void checkBinPackingSolution(Medians medians, PMP pmp) {
		for (int i = 0; i < pmp.size; i++) {
			int c = 0;
			for (Median median : medians) {
				if (median.customers.contains(i))
					c++;
			}
			if (c > 1) {
				System.out.println("Customer "+i+" repeated");				
			}else if (c == 0) {
				System.out.println("Customer "+i+" not allocated");
			}
		}		
	}
}
