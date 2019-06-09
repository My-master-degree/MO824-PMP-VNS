package problems.pmp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import problems.Evaluator;
import solutions.Solution;

/**
 * A quadractic binary function (QBF) is a function that can be expressed as the
 * sum of quadractic terms: f(x) = \sum{i,j}{a_{ij}*x_i*x_j}. In matricial form
 * a QBF can be expressed as f(x) = x'.A.x 
 * The problem of minimizing a QBF is NP-hard [1], even when no constraints
 * are considered.
 * 
 * [1] Kochenberger, et al. The unconstrained binary quadratic programming
 * problem: a survey. J Comb Optim (2014) 28:58–81. DOI
 * 10.1007/s10878-014-9734-0.
 * 
 * @author ccavellucci, fusberti
 *
 */
public class PMP implements Evaluator<Median, Medians> {

	/**
     * Dimension of the domain.
     */
    public Integer size;

    /**
     * Number of medians
     */
    public Integer p;

    /**
     * The matrix of costs
     */
    public Double[][] costs;    
	
	/**
	 * The constructor for Bin Packing Problem class. The filename of the
	 * input for setting the items and bin capacity. 	
	 * 
	 * @param filename
	 *            Name of the file containing the input for setting the BPP.
	 * @throws IOException
	 *             Necessary for I/O operations.
	 */
	public PMP(String filename) throws IOException {
		readInput(filename);
	}	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see problems.Evaluator#getDomainSize()
	 */
	@Override
	public Integer getDomainSize() {
		return size;
	}

	/**
	 * {@inheritDoc} In the case of a BPP, the evaluation correspond to
	 * computing the sum of bins used in the solution.
	 * 
	 * @return The evaluation of the BPP.
	 */
	@Override
	public Double evaluate(Medians sol) {
		return sol.cost;		
	}	
	
	 /**
     * Responsible for setting the BPP function parameters by reading the
     * necessary input from an external file. this method reads the domain's
     * dimension and matrix {@link #A}.
     *
     * @param filename Name of the file containing the input for setting the
     * black box function.
     * @return The dimension of the domain.
     * @throws IOException Necessary for I/O operations.
     */
    protected Integer readInput(String filename) throws IOException {

        Reader fileInst = new BufferedReader(new FileReader(filename));
        StreamTokenizer stok = new StreamTokenizer(fileInst);

        stok.nextToken();
        this.size = (int) stok.nval;
        stok.nextToken();
        this.p = (int) stok.nval;
        this.costs = new Double[this.size][this.size];
        for (int i = 0; i < this.size; i++) {        	
        	for (int j = 0; j < this.size; j++) {        		
        		stok.nextToken();
        		this.costs[i][j] = (Double) stok.nval;           
        	}
        }
        return this.size;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String str = "PMP\n[size=" + size + ",\np=" + p + "\ncosts:\n";
		for (int i = 0; i < costs.length; i++) {
			for (int j = 0; j < costs.length; j++) {
				str += costs[i][j] + " ";				
			}
			str += "\n";
		}
		return str;
	}
    
    
    
}
