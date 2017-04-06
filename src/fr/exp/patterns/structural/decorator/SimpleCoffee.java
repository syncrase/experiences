package fr.exp.patterns.structural.decorator;

public class SimpleCoffee extends Coffee {

	@Override
	public double getCost() {
		return 1;
	}

	@Override
	public String getIngredients() {
		return "Coffee";
	}

}
