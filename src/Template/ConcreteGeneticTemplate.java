package Template;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import Model.Individual;

public class ConcreteGeneticTemplate extends GeneticTemplate {

	@Override
	public List<Individual> Evaluate(List<Individual> population) {
		for(Individual i : population)
		{
			i.Evaluate();
		}
		return population;		
	}

	@Override
	public List<Individual> CrossBeed(List<Individual> selectedPopulation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Individual> Mutate(List<Individual> selectedPopulation) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void Replace() 
	{
		// TODO Auto-generated method stub
		
	}	
}
