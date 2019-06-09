package problems.pmp.solvers;

import java.io.IOException;
import java.util.List;

import metaheuristics.vns.AbstractVNS;
import metaheuristics.vns.LocalSearch;
import problems.pmp.Median;
import problems.pmp.Medians;
import problems.pmp.PMP;
import problems.pmp.PMP_Inverse;
import problems.pmp.construction.ConstructionMethod;

public class VNS_PMP extends AbstractVNS<Median, Medians> {
	
	private ConstructionMethod constructionMethod;
	private LocalSearch<Median, Medians> localSearch;
	
	public VNS_PMP(String filename, Integer iterations, Integer maxDurationInMilliseconds, 
			ConstructionMethod constructionMethod, LocalSearch<Median, Medians> localSearch, 
			List<LocalSearch<Median, Medians>> neighborhoodStructures, VNS_TYPE vns_type) throws IOException {		
		super(new PMP_Inverse(filename), iterations, maxDurationInMilliseconds, neighborhoodStructures, vns_type);
		this.constructionMethod = constructionMethod;
		this.localSearch = localSearch;
	}
	
	public VNS_PMP(PMP eval, Integer iterations, Integer maxDurationInMilliseconds, 
			ConstructionMethod constructionMethod, LocalSearch<Median, Medians> localSearch, 
			List<LocalSearch<Median, Medians>> neighborhoodStructures, VNS_TYPE vns_type) throws IOException {		
		super(eval, iterations, maxDurationInMilliseconds, neighborhoodStructures, vns_type);
		this.constructionMethod = constructionMethod;
		this.localSearch = localSearch;
	}

	@Override
	public Medians random(Medians solution) {
		return localSearch.randomSolution((PMP_Inverse) super.ObjFunction, solution);
	}

	@Override
	public Medians constructiveHeuristic() {
		return constructionMethod.construct((PMP_Inverse) super.ObjFunction);	
	}

}
