package controllers

import play._
import play.mvc._
import scala.xml.XML
import scala.xml.Node
import play.templates.Html
import models.xmltv
import siena.Model
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import play.data.Upload

object Application extends Controller {
    
	def index = <HOLA /> 

	  def getXml() = 
	  {
		val model = currentXmlTv();
		XML.loadString(model.xml);
	  }
	
  def currentXmlTv(): xmltv = {
	val m = new xmltv();
	    
	val l = Model.all(m.getClass()).fetch();
    l.isEmpty() match
    {
      case true =>{
        m.id = 0;
        m.xml = "<test />";
        m.insert();
        m
      }
      case false => l.get(0).asInstanceOf[xmltv]
    }
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
  
  def upload(data:Upload)
  {
     val xml = scala.io.Source.fromInputStream(data.asStream()).getLines().mkString("\n");
     
     val m = currentXmlTv();
     m.xml = xml;
     m.update();
     
     <Ok />
  }
  
}
