package Replace;

import java.util.List;

import Model.Individual;

/***
 * Replacement strategy interface
 * @author Aloïs Bretaudeau, Florent Yvon, Julien Raillard, Mickael Meneux
 */
public interface IReplaceStrategy {

	/***
	 * Replace individuals from the population by news
	 * @param newIndividuals : childrens generated
	 * @param population : base population
	 * @return Population replaced
	 */
	public List<Individual> Replace(List<Individual> newIndividuals, List<Individual> population);
}