package Template;

import java.util.List;

import Model.Individual;

/***
 * Genetic template algorithm implementation
 * @author Aloïs Bretaudeau, Florent Yvon, Julien Raillard, Mickael Meneux
 */
public class NonThreadedGeneticTemplate extends GeneticTemplate {

	@Override
	protected List<Individual> Evaluate(List<Individual> population) {
		
		for(Individual i : population)
		{
			i.Evaluate();
		}
		
		return population;		
	}	
}