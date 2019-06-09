package problems.pmp;

import java.util.Collection;

import solutions.Solution;

public class Medians extends Solution<Median>{
	
	public Medians() {
		super();
		super.cost = 0d;
	}
	
	public Medians(int size) {
		super(size);
		super.cost = 0d;
	}
	
	public Medians(Medians medians) {
		super(medians);
		super.cost = 0d;
	}
	
	@Override
	public boolean add(Median median) {
		super.cost += median.cost;
		return super.add(median);
	}
	
	@Override
	public boolean addAll(Collection<? extends Median> medians) {		
		for (Median median : medians) {
			super.cost += median.cost;
		}
		return super.addAll(medians);
	}
	
	@Override
	public Median remove(int index) {
		Median median = super.remove(index);
		super.cost -= median.cost;
		return median;
	}
	
	@Override
	public boolean remove(Object object) {	
		if (this.contains(object) && object instanceof Median) {			
			super.cost -= ((Median) object).cost;		
		}
		return super.remove(object);
	}
	
	@Override
	public boolean removeAll(Collection<?> medians) {
		for (Object object : medians) {
			if (object instanceof Median) {
				super.cost -= ((Median) object).cost;
			}
		}
		return super.removeAll(medians);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String str = "Medians [cost="+this.cost+"\n";
		for (Median median : this) {
			str += median.toString()+"\n";
		}
		str += "]";
		return str;
	}
}
