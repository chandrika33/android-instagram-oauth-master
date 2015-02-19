package br.com.dina.oauth.instagram.example;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class Home extends Activity {

	ListView m_listView;

	ListLoader m_listLoader;
	
	String m_strHistory = "";
	
	List<String> imagesData = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		Bundle b = getIntent().getExtras();
		if(b != null)
		{
			m_strHistory = b.getString("HISTORY");
			
			Log.d("HISTORY", m_strHistory);
		}
		
		try
		{
			JSONArray jArray = new JSONArray(m_strHistory);
			
			for(int i=0; i<jArray.length(); i++)
			{
				JSONObject jObj = jArray.getJSONObject(i);
				JSONArray jjArray = jObj.getJSONArray("tags");
				if(jjArray.length() != 0)
				{
					JSONObject jj = jObj.getJSONObject("images").getJSONObject("low_resolution");
					String imageUrlString = jj.getString("url");
					
					URL url = new URL(imageUrlString);
					
					imagesData.add(url.toString());
				}
				
			}
				
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		m_listView = (ListView) findViewById(R.id.listView1);
		
		m_listLoader = new ListLoader(this, imagesData);
		
		m_listView.setAdapter(m_listLoader);
	}
}
