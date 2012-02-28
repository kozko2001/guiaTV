package net.coscolla.android.utils;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.util.Log;

public class XmlHelper {
	private static final String LOGTAG = "XmlHelper";
	private Document doc;
	
	public XmlHelper(String xml)
	{
		doc = parse(xml);
		if( doc == null)
			throw new RuntimeException("Error parsing the xml");
	}
	
	private Document parse(String xml)
	{
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(new StringReader(xml)));
			
			return doc;
		}catch(Exception e)
		{
			Log.e(LOGTAG, e.toString());
		}
		
		return null;
	}
	
	public NodeList getElements(String tagName)
	{
		return doc.getElementsByTagName(tagName);
	}
	
	public static String getAttributeValue(Node node, String attributeName)
	{
		Node n = node.getAttributes().getNamedItem(attributeName);
		if( n == null)
			return null;
		else
			return n.getTextContent();
	}
	
	
	
	
}
