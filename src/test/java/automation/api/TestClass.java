package automation.api;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestClass {

	@Test
	public void compareAPITest() {

		List<String> apiList1 = null;
		List<String> apiList2 = null;
		String file1 = System.getProperty("user.dir") + "/resources/apiList1.txt";
		String file2 = System.getProperty("user.dir") + "/resources/apiList2.txt";
		apiList1 = Utility.readTextFile(file1);
		apiList2 = Utility.readTextFile(file2);
		Assert.assertTrue(apiList1.size() > 0, "File 1 doesn't have any data.");
		Assert.assertTrue(apiList2.size() > 0, "File 2 doesn't have any data.");
		Assert.assertEquals(apiList1.size(), apiList2.size(), "File doesn't match. Both file should have the same number of API's");
		for (int i = 0; i < apiList1.size(); i++) {
			{
				String response1 = Utility.getAPIResponse(apiList1.get(i), "GET");
				String response2 = Utility.getAPIResponse(apiList2.get(i), "GET");
				if(Utility.compareJson(response1, response2))
				{
					System.out.println(apiList1.get(i) + " equals " + apiList2.get(i));
				}
				else
				{
					System.out.println(apiList1.get(i) + " not equals " + apiList2.get(i));
				}
			}
		}
	}
}
