package ar.edu.ub.testing;

import java.util.ArrayList;

public class CCellphone extends CNameable {

	public CCellphone(String name, ArrayList<CCapability> capabilities)
	{
		super(name);
		this.m_capabilities = capabilities;
	}
	
	ArrayList<CCapability> capabilities()
	{
		return this.m_capabilities;
	}
	
	public boolean hasCapability(String name)
	{
		for (CCapability capability : this.capabilities())
		{
			if (capability.name().equalsIgnoreCase(name))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasCategory(String name)
	{
		for (CCapability capability : this.capabilities())
		{
			for (String category : capability.categories())
			{
				if (category.equalsIgnoreCase(name))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean supportsApplication(CApplication app)
	{
		for (String category : app.requiredCategories())
		{
			if (!this.hasCategory(category))
			{
				return false;
			}
		}
		
		for (String capability : app.requiredCapabilities())
		{
			if (!this.hasCapability(capability))
			{
				return false;
			}
		}
		
		return true;
	}
	
	private ArrayList<CCapability> m_capabilities;
}
