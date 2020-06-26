package ar.edu.ub.testing;

import java.util.HashMap;
import java.util.Set;
import java.util.function.BiConsumer;

public class CBenchmarkRunner
{
	public CBenchmarkRunner(CCellphonesData data, OutputPrinter printer)
	{
		this.m_data = data;
		this.m_printer = printer;
	}
	
	public CBenchmarkRunner(CCellphonesData data)
	{
		this(data, new OutputPrinter());
	}
	
	public void run()
	{
		for (CCellphone cellphone : this.m_data.cellphones())
		{
			this.m_printer.print("Running " + cellphone.name() + " benchmark:");
			HashMap<String, Boolean> results = new CBenchmark(cellphone, this.m_data.applications()).run();
			String longest = this.longestString(results.keySet());
			if (longest != null)
			{
				for (String app : results.keySet())
				{
					this.m_printer.print(String.format("Supports %-" + longest.length() + "s: %s", app, results.get(app)? "O" : "X"));
				}
			}
			this.m_printer.print();
		}
	}
	
	private String longestString(Set<String> fromSet)
	{
	      String longest = null;
	      for (String s : fromSet)
	      {
	          if (longest == null || s.length() > longest.length())
	          {
	        	  longest = s;
	          }
	      }
	      return longest;
	  }
	
	private CCellphonesData m_data;
	private OutputPrinter m_printer;
}
