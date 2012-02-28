package net.coscolla.guiaTv.model.parser;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.coscolla.android.utils.XmlHelper;
import net.coscolla.guiaTv.model.Channel;

public class ChannelsParser {
	
	public static ArrayList<Channel> parse(String _xml)
	{
		XmlHelper xml = new XmlHelper(_xml);
		NodeList channels = xml.getElements("channel");
		ArrayList<Channel>  ret = new ArrayList<Channel>();
		
		for(int i=0; i<channels.getLength(); i++)
		{
			Node currentChannel = channels.item(i);
			String id= currentChannel.getAttributes().getNamedItem("id").getTextContent();
			String desc = currentChannel.getTextContent();
			
			ret.add(new Channel(id, desc));
		}
		
		return ret;
	}
	
}
