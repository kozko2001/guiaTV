package controllers

import play._
import play.mvc._
import scala.xml.XML
import scala.xml.Node
import play.templates.Html

object Application extends Controller {
    
	def index = <HOLA /> 

	  def getXml() = 
	  {
	    val file = Play.getFile("xmltv.xml");
	    XML.loadFile(file)
	  }
	
	  
  def channels =  {
    val xml = getXml()
    val channels = xml \ "channel"
    
    <channels>{channels}</channels>
  }
  
  def programacion(	id:String ) = {
    val xml = getXml()
    val _programacion = (xml \\ "programme" ) 
    
    val ret =_programacion.filter(n=> n.attribute("channel").get.first.text.startsWith(id))
    <programacion id={id}>{ret}</programacion>
  }
  
}
