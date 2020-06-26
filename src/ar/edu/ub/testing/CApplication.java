package ar.edu.ub.testing;

import java.util.ArrayList;

public class CApplication extends CNameable
{
	public CApplication(String name, ArrayList<String> requiredCapabilities, ArrayList<String> requiredCategories)
	{
		super(name);
		this.m_requiredCapabilities = requiredCapabilities;
		this.m_requiredCategories = requiredCategories;
	}
	
	public ArrayList<String> requiredCapabilities()
	{
		return this.m_requiredCapabilities;
	}
	
	public ArrayList<String> requiredCategories()
	{
		return this.m_requiredCategories;
	}

	private ArrayList<String> m_requiredCapabilities;
	private ArrayList<String> m_requiredCategories;
}
