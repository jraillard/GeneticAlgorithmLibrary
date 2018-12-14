package Template;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Model.Individual;

public class ConcreteGeneticTemplate extends GeneticTemplate {

	@Override
	protected List<Individual> Evaluate(List<Individual> population) {
		for(Individual i : population)
		{
			Thread t = new Thread(i);
			t.start();
		}
		return population;		
	}

	@Override
	protected List<Individual> CrossBeedAndMutate(List<Individual> selectedPopulation, int nbChildrenToGenerate, int mutationProbability) {
		
		List<Individual> newIndividuals = new ArrayList<>();
		Random rand = new Random();
		int tempRandValue = 0;
				
		for(int i=0; i< nbChildrenToGenerate; i++)
		{
			tempRandValue = rand.nextInt(100)+1;
			//Probability to get mutation
			if(1 >= tempRandValue && tempRandValue <= mutationProbability) 
				newIndividuals.add(selectedPopulation.get(i).Mutate());	
			else
				newIndividuals.add(selectedPopulation.get(i).CrossBeed(selectedPopulation.get(i+1)));
		}
		
		return newIndividuals;
	}	
}