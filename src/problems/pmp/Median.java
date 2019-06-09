package problems.pmp;

import java.nio.file.attribute.FileOwnerAttributeView;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Median {
	public PMP p_medians;
	
	public Set<Integer> customers;
	
	public Integer medianIndex;
	
	public Double cost;
	
	public Median(PMP p_medians, Integer medianIndex) {
		this.p_medians = p_medians;
		this.medianIndex = medianIndex;
		this.cost = 0d;
		customers = new HashSet<Integer>();
	}
	
	public Double addCustomers(Integer... customers) {
		for (Integer customer: customers) {
			this.cost += this.p_medians.costs[customer][medianIndex];
			this.customers.add(customer);
		}
		return this.cost;
	}
	
	public Double removeCustomers(Integer... customers) {
		for (Integer customer: customers) {
			this.cost -= this.p_medians.costs[customer][medianIndex];
			this.customers.remove(customer);
		}
		return this.cost;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String str = "Median [median=" + medianIndex + ", cost=" + cost + ", customers={";
		for (Integer customer : customers) {
			str += customer + ",";			
		}
		return str + "}]";
		
	}
	
	
		
}
