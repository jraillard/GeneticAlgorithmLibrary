package Selection;

import java.util.List;
import Model.Individual;

public interface ISelectionStrategy {

	public List<Individual> Selection(List<Individual> population, int nbParent);
}