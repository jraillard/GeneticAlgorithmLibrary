package Template;

import java.util.List;
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
}