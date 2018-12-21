package Template;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Model.Individual;

/***
 * Genetic template algorithm implementation
 * @author Aloïs Bretaudeau, Florent Yvon, Julien Raillard, Mickael Meneux
 */
public class ThreadedGeneticTemplate extends GeneticTemplate {

	@Override
	protected List<Individual> Evaluate(List<Individual> population) {
		// Instantiate thread pool manager
		int threadPool = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(threadPool);
		
		for(Individual i : population)
		{
			// Create asynchronous task
			executor.execute(i);
		}
		
		// Execute tasks
		executor.shutdown();
		
		while(!executor.isTerminated()) { }
		
		return population;		
	}

	@Override
	protected List<Individual> CrossAndMutate(List<Individual> selectedPopulation, int nbChildrenToGenerate, int mutationProbability) {
		
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
				newIndividuals.add(selectedPopulation.get(i).Crossing(selectedPopulation.get(i+1)));
		}
		
		return newIndividuals;
	}	
}