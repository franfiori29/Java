package ar.edu.ub.testing;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CCellphonesData
{
	public CCellphonesData()
	{
		this.m_capabilities = new ArrayList<>();
		this.m_cellphones = new ArrayList<>();
		this.m_applications = new ArrayList<>();
	}
	
	public ArrayList<CCapability> capabilities()
	{
		return this.m_capabilities;
	}
	
	public ArrayList<CCellphone> cellphones()
	{
		return this.m_cellphones;
	}
	
	public ArrayList<CApplication> applications()
	{
		return this.m_applications;
	}
	
	public boolean load(String fileName)
	{
		JSONObject data = null;
		try
		{
			data = new JSONObject(new String(Files.readAllBytes(FileSystems.getDefault().getPath(fileName))));
		}
		catch (IOException e)
		{
		}
		catch (JSONException e)
		{
		}
		
		if (data != null)
		{
			JSONArray capabilities = data.optJSONArray("capabilities");
			if (capabilities != null)
			{
				for (int i = 0; i < capabilities.length(); i++)
				{
					JSONObject capability = capabilities.optJSONObject(i);
					if (capability != null && capability.has("name") && capability.has("categories"))
					{
						ArrayList<String> categories = new ArrayList<>();
						JSONArray jcategories = capability.optJSONArray("categories");
						for (int j = 0; j < jcategories.length(); j++)
						{
							categories.add(jcategories.optString(j));
						}
						this.m_capabilities.add(new CCapability(capability.optString("name"), categories));
					}
				}
			}
			
			JSONArray cellphones = data.optJSONArray("cellphones");
			if (cellphones != null)
			{
				for (int i = 0; i < cellphones.length(); i++)
				{
					JSONObject cellphone = cellphones.optJSONObject(i);
					if (cellphone != null && cellphone.has("name") && cellphone.has("capabilities"))
					{
						ArrayList<String> cellPhoneCapabilities = new ArrayList<>();
						JSONArray jcapabilities = cellphone.optJSONArray("capabilities");
						for (int j = 0; j < jcapabilities.length(); j++)
						{
							cellPhoneCapabilities.add(jcapabilities.optString(j));
						}
						this.m_cellphones.add(new CCellphone(cellphone.optString("name"), this.capabilities(cellPhoneCapabilities)));
					}
				}
			}
			
			JSONArray applications = data.optJSONArray("applications");
			if (applications != null)
			{
				for (int i = 0; i < applications.length(); i++)
				{
					JSONObject application = applications.optJSONObject(i);
					if (application != null && application.has("name"))
					{
						ArrayList<String> appCapabilities = new ArrayList<>();
						if (application.has("required_capabilities"))
						{
							JSONArray jcapabilities = application.optJSONArray("required_capabilities");
							for (int j = 0; j < jcapabilities.length(); j++)
							{
								appCapabilities.add(jcapabilities.optString(j));
							}
						}
						
						ArrayList<String> appCategories = new ArrayList<>();
						if(application.has("required_categories"))
						{
							JSONArray jcategories = application.optJSONArray("required_categories");
							for (int j = 0; j < jcategories.length(); j++)
							{
								appCategories.add(jcategories.optString(j));
							}
						}
						this.m_applications.add(new CApplication(application.optString("name"), appCapabilities, appCategories));
					}
				}
			}
			
			return true;	
		}
		
		return false;
	}
	
	private ArrayList<CCapability> capabilities(ArrayList<String> withNames)
	{
		ArrayList<CCapability> capabilities = new ArrayList<>();
		for (String name : withNames)
		{
			for (CCapability existing : this.capabilities())
			{
				if (existing.name().equalsIgnoreCase(name))
				{
					capabilities.add(existing);
				}
			}
		}
		return capabilities;
	}

	private ArrayList<CCapability> m_capabilities;
	private ArrayList<CCellphone> m_cellphones;
	private ArrayList<CApplication> m_applications;
}
