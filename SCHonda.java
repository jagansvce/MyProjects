package org.boss.internal.cars.scottclark.honda;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SCHonda {

	public static void main(String[] args) {

		Document doc;
		// System.setProperty("http.proxyHost", "proxy-nc.wellsfargo.com");
		// System.setProperty("http.proxyPort", "8080");
		int ind = 1;
		boolean isNotDone = true;
//		String baseURL = "http://www.scottclarkhonda.com/searchused.aspx?make=Honda&bodystyle=Sport+Utility+Vehicle&pt=";
//		String baseURL = "http://www.scottclarkhonda.com/searchused.aspx?make=Honda&pt=";
		String baseURL = "http://www.scottclarkhonda.com/searchused.aspx?pt=";
		int pageNo = 1;
		while (isNotDone) {
			try {
				doc = Jsoup.connect(baseURL + pageNo).timeout(1000*60*3).get();
				if(pageNo == 1) {
					Elements models = doc.select("#collapse1 ul li a");
					for(Element model : models) {
						String mdl = model.text().trim();
						System.out.print(fixLen(mdl.substring(0, mdl.indexOf('(')).trim(), -20));
						System.out.print(fixLen(mdl.substring(mdl.indexOf('(')+1, mdl.indexOf(')')).trim(), 5));
						System.out.println();
					}
				}
				
				Elements inventories = doc.select("div.row.srpVehicle");
				for (Element inventory : inventories) {
					Element title = inventory.select(".vehicleTitle.margin-x").get(0);
					Element internetPrice = inventory.select("li.internetPrice>span").get(0);
					Element mileageDisplay = inventory.select("li.mileageDisplay").get(0);
					Element mpg = inventory.select("li.MPG").get(0);
					Element bodyStyleDisplay = inventory.select("li.bodyStyleDisplay").get(0);
					Element engineDisplay = inventory.select("li.engineDisplay").get(0);
					Element driveTrainDisplay = inventory.select("li.driveTrainDisplay").get(0);
					
					System.out.print(ind + ",");
					String str = title.text().trim().replaceAll(",", "")+ ",";
					String strArr[] = str.split(" ");
					String model = "";
					for(int i=2; i<strArr.length-1; i++) {
						model = " " + strArr[i];
					}
					System.out.print(strArr[0] + ",");
					System.out.print(strArr[1] + ",");
					System.out.print(model.trim() + ",");
					System.out.print(strArr[strArr.length-1] + ",");
					System.out.print(internetPrice.text().trim().replaceAll(",", "").replaceAll("\\$", "").replaceAll("Call for Price", "CP")+ ",");
					System.out.print(mileageDisplay.text().replaceAll("Mileage:", "").trim().replaceAll(",", "")+ ",");
					System.out.print(mpg.text().replaceAll("MPG #:", "").trim().replaceAll(",", "")+ ",");
					System.out.print(bodyStyleDisplay.text().replaceAll("Body Style:", "").trim().replaceAll(",", "")+ ",");
					System.out.print(engineDisplay.text().replaceAll("Engine:", "").trim().replaceAll(",", "")+ ",");
					System.out.print(driveTrainDisplay.text().replaceAll("Drive Type:", "").trim().replaceAll(",", "")+ ",");
					
					System.out.println();

					
					ind++;
				}
				Elements paginations = doc.select("ul.pagination.pagination-sm li.disabled i.fa.fa-angle-double-right");
				isNotDone = paginations.size()==0;
				pageNo++;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static String fixLen(String string, int length) {
		return String.format("%1$" + length + "s", string);
	}
}
