package ar.edu.ub.testing;

public class Main
{
	public static void main(String[] args)
	{
		CCellphonesData cd = new CCellphonesData();
		if (args.length > 0 && cd.load(args[0]))
		{
			new CBenchmarkRunner(cd).run();
		}
	}
}
