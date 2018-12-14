package Selection;

import java.util.List;

import Model.Individual;

/***
 * Selection strategy interface
 * @author Aloïs Bretaudeau, Florent Yvon, Julien Raillard, Mickael Meneux
 */
public interface ISelectionStrategy {

	/***
	 * Select population parents 
	 * @param population : population where to make selection
	 * @param nbParent : number of parent to select
	 * @return Parents list
	 */
	public List<Individual> Selection(List<Individual> population, int nbParent);
}