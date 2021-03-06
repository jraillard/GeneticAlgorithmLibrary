package Comparators;

import java.util.Comparator;

import Model.Individual;

/***
 * Comparator class to sort individuals by their fitness in descending order
 * @author Alo�s Bretaudeau, Florent Yvon, Julien Raillard, Mickael Meneux
 */
public class SortByFitnessDesc implements Comparator<Individual>{
	
	/***
	 * Compare method used by comparator
	 */
	public int compare(Individual a, Individual b) {		
		return (int) (b.GetFitness() - a.GetFitness());
	}
}