package Selection;

import java.util.List;

import Comparators.SortByFitnessDesc;
import Model.Individual;

/***
 * BestFitness strategy for selection
 * @author Aloïs Bretaudeau, Florent Yvon, Julien Raillard, Mickael Meneux
 */
public class BestFitnessSelectionStrategy implements ISelectionStrategy{

	@Override
	public List<Individual> Selection(List<Individual> population, int nbParent) {		
		
		// Sort population to put the best fitness at first
		population.sort(new SortByFitnessDesc());	
		
		return population.subList(0, nbParent);
	}
}