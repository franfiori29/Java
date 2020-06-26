package ar.edu.ub.testing;

import java.util.ArrayList;

public class CCapability extends CNameable
{
	public CCapability(String name, ArrayList<String> categories)
	{
		super(name);
		this.m_categories = categories;
	}
	
	public ArrayList<String> categories()
	{
		return this.m_categories;
	}
	
	private ArrayList<String> m_categories;
}
