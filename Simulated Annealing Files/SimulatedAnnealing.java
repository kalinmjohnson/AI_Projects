import java.util.Random;

public class SimulatedAnnealing {
	
	/** Values that find best cost */
	private double [] values;

	/** Random number generator*/
	private Random r;
	

	/** Constructor
	 * 
	 */
	public SimulatedAnnealing()
	{
		r = new Random(10);
		values = new double[3];
		values[0] = 0;
		values[1] = 0;
		values[2] = 0;
	}
	
	/**
	 * Calculate cost based on set of values
	 * @param newValues The array of three values
	 * @return The cost of the three values
	 */
	public double calculateCost(double [] newValues)
	{
		return Math.pow(newValues[0], 2.0) - Math.pow(newValues[1], 0.5) + 2/newValues[2];
	}

	/**
	 * toString method
	 */
	public String toString()
	{
		return values[0] + " " + values[1] + " " + values[2] + ": " + calculateCost(values);
	}

	/** 
	 * Planning method
	 * @param tStart The starting temperature
	 * @param tMin The lower temperature at which to stop
	 * @param alpha The value by which temperature changes each time
	 */
	public void planSimulatedAnnealing(double tStart, double tMin, double alpha)
	{
		double [] trialValues = {0.5, 0.5, 0.5};
		double temperature = tStart;
		double trialCost = calculateCost(trialValues);
		while(temperature > tMin) {
			double [] successorValues = setValues(trialValues);
			double successorCost = calculateCost(successorValues);

			if (successorCost <= trialCost) {
				trialValues = successorValues;
				trialCost = successorCost; 
			} else {
				double probability = r.nextDouble();
				double threshold = acceptProbability(trialCost, successorCost, temperature);
				if (threshold > probability) {
					trialValues = successorValues;
					trialCost = successorCost;
				}
			}

			temperature = temperature * alpha;
		}

		values = trialValues;
	}
	

	/**
	 * Determine threshold for whether to accept or reject
	 * @param previousCost Previous cost
	 * @param newCost New cost
	 * @param temperature Current temperature
	 * @return The probability threshold
	 */
	private double acceptProbability(double previousCost, double newCost, double temperature) {
		return Math.exp((previousCost-newCost)/temperature);
	}

	/**
	 * Generate a single successor by randomly picking a value to update
	 * @param currentValues The current array of values
	 * @return The new array
	 */
	public double [] setValues(double [] currentValues)
	{
		double [] newArray = {currentValues[0], currentValues[1], currentValues[2]};
		int index = r.nextInt(3);
		double newValue = r.nextDouble();
		newArray[index] = newValue;
		return newArray;
	}
}
