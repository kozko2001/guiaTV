package net.coscolla.guiaTv.model.parser;

import java.util.ArrayList;
import java.util.Date;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import net.coscolla.android.utils.XmlHelper;
import net.coscolla.guiaTv.model.Channel;
import net.coscolla.guiaTv.model.Programa;
import net.coscolla.guiaTv.model.Programa.Categoria;
import net.coscolla.guiaTv.model.Programacio;

public class ProgramacioParser {

	public static Programacio parse(String _xml)
	{
		XmlHelper xml = new XmlHelper(_xml);
		NodeList programmes = xml.getElements("programme");
		ArrayList<Programa>  ret = new ArrayList<Programa>();
		
		for(int i=0; i<programmes.getLength(); i++)
		{
			Node currentNode = programmes.item(i);
			String start = XmlHelper.getAttributeValue(currentNode, "start");
			String title = "";
			String category = "";
			for(int j = 0; j<currentNode.getChildNodes().getLength(); j++)
			{
				Node currentChild = currentNode.getChildNodes().item(j);
				if( currentChild.getNodeName().equalsIgnoreCase("title") )
					title = currentChild.getTextContent();
				if( currentChild.getNodeName().equalsIgnoreCase("category") )
					category = currentChild.getTextContent();
			}
			
			Programa p  = new Programa();
			p.rawHora = start.split(" ")[0];
			int year = Integer.parseInt(p.rawHora.substring(0,4));
			int month  = Integer.parseInt(p.rawHora.substring(4, 6));
			int day  = Integer.parseInt(p.rawHora.substring(6,8));
			
			int hora = Integer.parseInt(p.rawHora.substring(8, 10));
			int minut = Integer.parseInt(p.rawHora.substring(10, 12));
			
			Date auxDate = new Date(year-1900, month-1, day, hora, minut);
			p.hora = auxDate.getTime();
			
			p.programaCategoria = getCategoria(category);
			p.programName = title;
			
			
			if( ret.size() == 0)
			{
				ret.add(p);
			}
			if( ret.size() > 0 && i <= programmes.getLength() )
			{
				ret.get(ret.size()-1).hora_fin = p.hora;
				ret.add(p);
			}
				
			
		}
		
		ret.remove(0);
		
		Programacio programacio = new Programacio();
		programacio.programes = ret;
		return programacio;
	}

	private static Categoria getCategoria(String category) {
		if( category.equalsIgnoreCase("PROGRAMAS"))
			return Categoria.Programa;
		if( category.equalsIgnoreCase("DEPORTES"))
			return Categoria.Deporte;
		if( category.equalsIgnoreCase("NOTICIAS"))
			return Categoria.Noticias;
		if( category.equalsIgnoreCase("SERIES"))
			return Categoria.Serie;
		if( category.equalsIgnoreCase("CINE"))
			return Categoria.Cine;
		return Categoria.Programa;
	}
}
 