package ar.edu.ub.testing;

import java.util.ArrayList;
import java.util.HashMap;

public class CBenchmark
{
	public CBenchmark(CCellphone cellphone, ArrayList<CApplication> applications)
	{
		this.m_cellphone = cellphone;
		this.m_applications = applications;
	}
	
	public CCellphone cellphone()
	{
		return this.m_cellphone;
	}
	
	public ArrayList<CApplication> applications()
	{
		return this.m_applications;
	}
	
	public HashMap<String, Boolean> run()
	{
		HashMap<String, Boolean> results = new HashMap<>();
		for (CApplication app : this.applications())
		{
			results.put(app.name(), this.cellphone().supportsApplication(app));
		}
		return results;
	}
	
	private CCellphone m_cellphone;
	private ArrayList<CApplication> m_applications;
}
