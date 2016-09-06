package com.core.utils;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.core.base.LogManager;
import com.core.base.TestManager;

public class XmlUtils {
	
	
	
	public static void verifyNodeValue(final String filePath , final String tagName, final String expectedValue){
		verifyNodeValue(filePath , tagName, expectedValue, null , null);
	}
	

	public static void verifyNodeValue(final String filePath , final String tagName, final String expectedValue, Integer maxTimeOutMs , Integer intervalMs){
		final StringRef actualValue = new StringRef("");
		boolean ispass = Utils.tryUntil(new ActionWrapper("Verify Node Value" , maxTimeOutMs , intervalMs) {
			@Override
			public boolean invoke() throws Exception {
				actualValue.setValue(getNodeValue(filePath,tagName));
				return expectedValue.equals(actualValue.value);
			}
		});
		LogManager.verify(ispass, String.format("Verify Node Value : %s . Expected = %s , Actual = %s", tagName, expectedValue, actualValue.value));
	}
	
	
	
	
	public static String getNodeValue(String filePath , String tagName){
		LogManager.debug(String.format("Get Node Value by Tag name= %s , from : %s",tagName,filePath));
		Document doc;
		try {
			doc = loadDocument(filePath);
			NodeList nodeList = doc.getElementsByTagName(tagName);
			if(nodeList == null)
				throw new Exception("Failed to find Element with tag name = " + tagName);
			if(nodeList.getLength() != 1)
				throw new Exception("Error: we get incorrect #elemnts (!1) for tag name = " + tagName);
			for (int temp = 0; temp < nodeList.getLength(); temp++) {
				Node node = nodeList.item(temp);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) node;
					return eElement.getTextContent();
				}
			}
	
		} catch (Exception e) {
			LogManager.error("Get Node Value - Error : " + e.getMessage());
		}
		return "UnKnown";
	}
	
	
	
	private static Document loadDocument(String path) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory dbFactory  = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(path);
		doc.getDocumentElement().normalize();
		return doc;
	}

}
