package ar.edu.ub.testing;

public abstract class CNameable implements INameable
{
	public CNameable(String name)
	{
		this.m_name = name;
	}
	
	@Override
	public String name()
	{
		return this.m_name;
	}
	
	private String m_name;
}
