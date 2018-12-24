package Builder;

import java.util.List;

import Model.Individual;

/***
 * Population builder interface
 * @author Aloïs Bretaudeau, Florent Yvon, Julien Raillard, Mickael Meneux
 */
public interface IPopulationBuilder {

	/***
	 * Build a population of individuals
	 * @param model : individual model
	 * @param populationCount : number of individual in population
	 * @return Population generated
	 */
	public List<Individual> BuildPopulation(Individual model, int populationCount);
}