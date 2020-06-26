package ar.edu.ub.testing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils
{
	public static String readProperty(String propertyName, JSONObject fromJson, String fallback)
	{
		if (fromJson != null && fromJson.has(propertyName))
		{
			try
			{
				return fromJson.getString(propertyName);
			}
			catch (JSONException e)
			{	
			}
		}
		
		return fallback;
	}
	
	public static int readProperty(String propertyName, JSONObject fromJson, int fallback)
	{
		if (fromJson != null && fromJson.has(propertyName))
		{
			try
			{
				return fromJson.getInt(propertyName);
			}
			catch (JSONException e)
			{	
			}
		}
		
		return fallback;		
	}
	
	public static boolean readProperty(String propertyName, JSONObject fromJson, boolean fallback)
	{
		if (fromJson != null && fromJson.has(propertyName))
		{
			try
			{
				return fromJson.getBoolean(propertyName);
			}
			catch (JSONException e)
			{	
			}
		}
		
		return fallback;
	}
	
	public static JSONArray readArrayProperty(String propertyName, JSONObject fromJson, JSONArray fallback)
	{
		if (fromJson != null && fromJson.has(propertyName))
		{
			try
			{
				return fromJson.getJSONArray(propertyName);
			}
			catch (JSONException e)
			{
			}
		}
		
		return null;
	}
	
	public static JSONObject readObjectProperty(String propertyName, JSONObject fromJson, JSONObject fallback)
	{
		if (fromJson != null && fromJson.has(propertyName))
		{
			try
			{
				return fromJson.getJSONObject(propertyName);
			}
			catch (JSONException e)
			{
			}
		}
		
		return null;
	}
}
