
public class SAMain {
	public static void main(String [] args)
	{
		SimulatedAnnealing sa = new SimulatedAnnealing();
		sa.planSimulatedAnnealing(10, 0.0005, .995);
		System.out.println("Final");
		System.out.println(sa);
	}

}
