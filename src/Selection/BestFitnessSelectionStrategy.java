package Selection;

import java.util.List;
import Comparators.SortByFitnessAsc;
import Model.Individual;

/***
 * BestFitness strategy for selection
 * @author Aloïs Bretaudeau, Florent Yvon, Julien Raillard, Mickael Meneux
 */
public class BestFitnessSelectionStrategy implements ISelectionStrategy{

	@Override
	public List<Individual> Selection(List<Individual> population, int nbParent) {				
		population.sort(new SortByFitnessAsc());		
		return population.subList(0, nbParent-1);
	}
}