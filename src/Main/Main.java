package Main;

import Template.NonThreadedGeneticTemplate;

import java.util.List;

import GeneticEnum.ReplaceEnum;
import GeneticEnum.SelectionEnum;
import Model.Individual;
import Template.GeneticTemplate;

public class Main {

	public static void main(String[] args) {
		
		GeneticTemplate genetics = new NonThreadedGeneticTemplate();		
		MyIndividual customIndividual = new MyIndividual();
		
		List<Individual> populationGenerated = genetics.Compute(customIndividual, 10, 3, SelectionEnum.BestFitness , ReplaceEnum.Lowest, 10, 3);
		
		if(populationGenerated == null) { System.out.println("Processus failed"); return; }
		
		for(Individual i : populationGenerated) {
			
			System.out.println("Individual n°" + i.hashCode() + " - Fitness : " + i.GetFitness());			
		}
	}

}