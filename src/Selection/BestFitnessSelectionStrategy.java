package Selection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import Model.Individual;

public class BestFitnessSelectionStrategy implements ISelectionStrategy{

	@Override
	public List<Individual> Selection(List<Individual> population, int nbParent) {
		List<Individual> selectedPopulation = null;
		
		population.sort(new SortByFitnessAsc());		
		selectedPopulation = population.subList(0, nbParent-1);
		
		return selectedPopulation;	
	}
}

class SortByFitnessAsc implements Comparator<Individual>{

	public int compare(Individual a, Individual b) {
		return (int) (a.GetFitness() - b.GetFitness());
	}	
}