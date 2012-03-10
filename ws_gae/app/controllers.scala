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
import scala.tools.nsc.io.Streamable
import java.io.ByteArrayInputStream
import java.io.FileOutputStream

object Application extends Controller {
  
  def getData() = 
	  {
		val model = currentXmlTv();
		
		model.xmlDecoded()
	  }
  
	  def getXml() = 
	  {
		val model = currentXmlTv();
		
		XML.loadString(model.xmlDecoded());
	  }
	
  def currentXmlTv(): xmltv = {
	val m = new xmltv();
	    
	val l = Model.all(m.getClass()).fetch();
    l.isEmpty() match
    {
      case true =>{
        m.id = 0;
        m.xmlEndoded("<test />");
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
     val xml = scala.io.Source.fromInputStream(data.asStream()).mkString
     
     val m = currentXmlTv();
     m.xmlEndoded(xml)
     m.update();
     
     <Ok />
  }
  
}
