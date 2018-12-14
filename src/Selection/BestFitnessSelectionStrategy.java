package Selection;

import java.util.Comparator;
import java.util.List;
import Model.Individual;

public class BestFitnessSelectionStrategy implements ISelectionStrategy{

	@Override
	public List<Individual> Selection(List<Individual> population, int nbParent) {
				
		population.sort(new SortByFitnessAsc());		
		return population.subList(0, nbParent-1);
	}
}

class SortByFitnessAsc implements Comparator<Individual>{

	public int compare(Individual a, Individual b) {
		return (int) (a.GetFitness() - b.GetFitness());
	}	
}