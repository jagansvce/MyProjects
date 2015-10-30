package org.boss.internal.cars.scottclark.honda;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.boss.internal.cars.model.Car;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SCHonda {

	public static void main(String[] args) {

		List<Car> cars = new ArrayList<Car>();
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
					Element title = inventory.select(".vehicleTitle.margin-x a").get(0);
					Element internetPrice = inventory.select("li.internetPrice>span").get(0);
					Element mileageDisplay = inventory.select("li.mileageDisplay").get(0);
					Element mpg = inventory.select("li.MPG").get(0);
					Element bodyStyleDisplay = inventory.select("li.bodyStyleDisplay").get(0);
					Element engineDisplay = inventory.select("li.engineDisplay").get(0);
					Element driveTrainDisplay = inventory.select("li.driveTrainDisplay").get(0);
					Element vinDisplay = inventory.select("li.vinDisplay").get(0);
					Element stockDisplay = inventory.select("li.stockDisplay").get(0);
					
					System.out.print(ind + ",");
					String titleText = title.text().trim().replaceAll(",", "")+ ",";
					/*
					String strArr[] = titleText.split(" ");
					String model = "";
					for(int i=2; i<strArr.length-1; i++) {
						model = " " + strArr[i];
					}
					String str = internetPrice.text().trim().replaceAll(",", "").replaceAll("\\$", "").replaceAll("Call for Price", "0");
					long price = Long.parseLong(str);
					str = mileageDisplay.text().replaceAll("Mileage:", "").trim().replaceAll(",", "");
					long miles = Long.parseLong(str);
					
					System.out.print(titleText + ",");
//					System.out.print(strArr[0] + ",");
//					System.out.print(strArr[1] + ",");
//					System.out.print(model.trim() + ",");
					System.out.print(strArr[strArr.length-1] + ",");
					System.out.print(price);
					System.out.print(miles);
					System.out.print(mpg.text().replaceAll("MPG #:", "").trim().replaceAll(",", "")+ ",");
					System.out.print(bodyStyleDisplay.text().replaceAll("Body Style:", "").trim().replaceAll(",", "")+ ",");
					System.out.print(engineDisplay.text().replaceAll("Engine:", "").trim().replaceAll(",", "")+ ",");
					System.out.print(driveTrainDisplay.text().replaceAll("Drive Type:", "").trim().replaceAll(",", "")+ ",");
					
					System.out.println();

					str = driveTrainDisplay.text().replaceAll("Drive Type:", "").trim().replaceAll(",", "");
					
					System.out.println("driveTrainDisplay=============="+driveTrainDisplay.ownText());
					*/
					Car car = new Car();
					car.setId(ind);
					car.setTitle(clearStr(title.text()));
					car.setSrc("Scott Clark Honda");
					car.setYear("");
					car.setMake("");
					car.setModel("");
					car.setPrice(Long.parseLong(clearStr(internetPrice.ownText())));
					car.setMiles(Long.parseLong(clearStr(mileageDisplay.ownText())));
					car.setMpg(clearStr(mpg.ownText()));
					car.setBodyStyle(clearStr(bodyStyleDisplay.ownText()));
					car.setEngine(clearStr(engineDisplay.ownText()));
					car.setDriveType(clearStr(driveTrainDisplay.ownText()));
					car.setStockNo(clearStr(vinDisplay.ownText()));
					car.setVin(clearStr(stockDisplay.ownText()));
					cars.add(car);
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

	public static String clearStr(String string) {
		return string.trim().replaceAll(",", "").replaceAll("\\$", "").replaceAll("Call for Price", "0");
	}
	public static String fixLen(String string, int length) {
		return String.format("%1$" + length + "s", string);
	}
}
