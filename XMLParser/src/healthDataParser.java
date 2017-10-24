import java.io.*;
import java.util.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;



public class healthDataParser {
	public static void main(String args[]) throws ParserConfigurationException {

		Set<String> recordTypes = new HashSet<String>();
		double sum =0;
		String oldSD=null, oldED=null;

		try {
			FileWriter fstream = new FileWriter("/Users/urjanadibail/Desktop/"
					+ "apple_health_export/output.txt", true); 
			BufferedWriter out  = new BufferedWriter(fstream);

			File fXmlFile = new File("/Users/urjanadibail/Desktop/"
					+ "apple_health_export/export.xml"); 
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			NodeList nList = doc.getElementsByTagName("Record");

			int flag = 0, t=0;
			for (int i = 0; i < nList.getLength() && t<13111; i++) {
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;
					String s = eElement.getAttribute("type");
					//recordTypes.add(s);

					
					String startDate = eElement.getAttribute("startDate").substring(0,10);
					String endDate = eElement.getAttribute("endDate").substring(0,10);
					double value = Double.parseDouble(eElement.getAttribute("value"));
					if(flag==0) {oldSD = startDate; oldED = endDate; flag =1; }


					if(s.equals("HKQuantityTypeIdentifierDistanceWalkingRunning")) {
						t++;
						System.out.println( startDate+ ","+ endDate + "," + value+","+ t);
						

						if(!startDate.equals(oldSD)) {
							System.out.println("YES!");
							out.write(startDate+","+endDate + ","+sum+"\n");
							sum = 0;
							
						}
							sum+=value;
							oldSD = startDate;
							oldED = endDate;

							
							
						
					}


				}
			}

			out.flush();
			out.close();
			
			//System.out.println("Types of records:"+recordTypes.toString());

			//			 <Record type="HKQuantityTypeIdentifierDistanceWalkingRunning" sourceName="Yeah, whatever!" sourceVersion="10.2.1" device="&lt;&lt;HKDevice: 0x170e9ba30&gt;, name:iPhone, manufacturer:Apple, model:iPhone, hardware:iPhone8,4, software:10.2.1&gt;" unit="km" creationDate="2017-03-23 14:59:36 -0400" startDate="2017-03-23 14:48:21 -0400" endDate="2017-03-23 14:58:21 -0400" value="0.2002"/>




		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}



}
