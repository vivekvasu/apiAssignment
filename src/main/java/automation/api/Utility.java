package automation.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Hello world!
 *
 */
public class Utility {

	/**
	 * 
	 * @param apiUrl
	 * @param method
	 * @return
	 */
	public static String getAPIResponse(String apiUrl, String method)
	{
		URL url = null;
		Scanner scanner = null;
		String entireResponse = "";
		URLConnection connection = null;
		try {
			url = new URL(apiUrl);
			if (apiUrl.toLowerCase().startsWith("https"))
			{
				connection = url.openConnection();
				((HttpsURLConnection) connection).setRequestMethod(method);
			}
			else
			{
				connection = url.openConnection();
				((HttpURLConnection) connection).setRequestMethod(method);
			}
			connection.setRequestProperty("Accept", "application/json");
			connection.connect();
			scanner = new Scanner(url.openStream());
			entireResponse = new String();
			while (scanner.hasNext())
			{
				entireResponse = scanner.nextLine();
			}
			scanner.close();
		} catch (Exception e) {
			entireResponse = "";
			System.out.println("Error occurred while invoking the API : " + e.getMessage());
		}
		return entireResponse;
	}

	/**
	 * 
	 * @param filePath
	 */
	public static List<String> readTextFile(String filePath)
	{
		String line = ""; 
		File file = null;
		BufferedReader bReader = null;
		List<String> apiList = null;
		try {
			file = new File(filePath); 
			bReader = new BufferedReader(new FileReader(file)); 
			apiList = new ArrayList<String>();
			while ((line = bReader.readLine()) != null) 
			{
				apiList.add(line);
			}
			bReader.close();
		}catch (Exception e) 
		{
			apiList = null;
			System.out.println("Error occurred while reading the text file : " + e.getMessage());
		}
		return apiList;
	}

	/**
	 * 
	 * @param json1
	 * @param json2
	 * @return
	 */
	public static boolean compareJson(String json1, String json2)
	{
		boolean isSuccess = false;
		try {
			Gson gson = new Gson();
			Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
			Map<String, Object> firstJsonMap = gson.fromJson(json1, mapType);
			Map<String, Object> secondJsonMap = gson.fromJson(json2, mapType);
			if((Maps.difference(firstJsonMap, secondJsonMap).toString().equals("equal")))
			{
				isSuccess = true;
			}
		}
		catch (Exception e) 
		{
			System.out.println("Error occurred while comparing the Json files : " + e.getMessage());
		}
		return isSuccess;
	}

}
