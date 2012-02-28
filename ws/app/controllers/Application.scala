package controllers

import play.api._
import play.api.mvc._
import play.api.templates.Xml
import scala.xml.XML
import scala.xml.Node

object Application extends Controller {
  
  def index = Action {
    
    Ok(views.html.index("Your new application is ready222."))
  }
  
  def getXml() = 
  {
    val file = Play.current.getExistingFile("xmltv.xml").get
    XML.loadFile(file)
  }
  
  def channels = Action {
    val xml = getXml()
    val channels = xml \ "channel"
    
    val ret = <channels>{channels}</channels>
    
    Ok(ret)
  }
 
  def programacion(	id:String ) = Action {
    val xml = getXml()
    val _programacion = (xml \\ "programme" ) 
    
    val ret =_programacion.filter(n=> n.attribute("channel").get.first.text.startsWith(id))
    Ok(<programacion id={id}>{ret}</programacion>)
  }
  
  
}