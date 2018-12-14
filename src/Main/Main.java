package Main;

import Template.NonThreadedGeneticTemplate;
import UserImplementation.MyIndividual;

import java.util.List;

import GeneticEnum.ReplaceEnum;
import GeneticEnum.SelectionEnum;
import Model.Individual;
import Template.GeneticTemplate;

public class Main {

	public static void main(String[] args) {
		
		GeneticTemplate genetics = new NonThreadedGeneticTemplate();		
		MyIndividual customIndividual = new MyIndividual();
		
		List<Individual> populationGenerated = genetics.Compute(customIndividual, 100, 30, SelectionEnum.Random, ReplaceEnum.Random, 10);
		for(Individual i : populationGenerated) {
			
			System.out.println(i.hashCode() + " : " + i.GetFitness());
		}
	}

}
