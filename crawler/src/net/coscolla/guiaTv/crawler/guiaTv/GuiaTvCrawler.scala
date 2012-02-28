package net.coscolla.guiaTv.crawler.guiaTv
import net.coscolla.guiaTv.crawler.HttpUtil
import scala.xml.XML
import scala.io.Source
import net.coscolla.guiaTv.crawler.HTML5Parser
import org.xml.sax.InputSource
import scala.xml.Node
import net.coscolla.guiaTv.crawler.CryptoUtil
import net.coscolla.guiaTv.crawler.{MongoDbUtil, ProgramDetailToMongo}
import com.mongodb.casbah.commons.MongoDBObject
import net.liftweb.json.JsonParser

object GuiaTvCrawler {

  val CHANNELS_URL = "http://www.laguiatv.com/programacion/"
  val LA_GUIA_TV_BASE = "http://www.laguiatv.com"
  val PROGRAME_DETAILS_COLLECTION  = "programDetails"
  def obtainHtml(url:String) : Option[Node] =
  {
    Console.println(url)
    val htmlParser = new HTML5Parser()
    
    val body = HttpUtil.httpget(url) match {
      case Some(x) => Some(htmlParser.loadXML(new InputSource(x)))
      case None    =>{
        Console.println("Error getting the url " + url )
        None
      }
    }
    
    return body
  }
  
  def getChannels = {
    val html = obtainHtml(CHANNELS_URL).map(html => 
    {
	    val channels = (html \\ "ul" )
	    		.filter( n => n.attribute("class").isDefined && n.attribute("class").get.text == "channel-list" )
	    		.map(n => n \\ "a").first.map(n => (n.attribute("title").get.text, n.attribute("href").get.text))
	    		.map(c => new Channel(c._1, c._2))
	
	    val programacio = channels.map ( c => Programacio(c, getProgramme(c.href)))
	    
	    val xml = GuiaTvXml.writeChannels(programacio)
	    Console.println(xml)
    })
 
  }
  
  case class Programacio( channel: Channel, emisions: Seq[Programme])
  
  def getProgramme( href:String ) : Seq[Programme] = 
  {
	  val html = obtainHtml(LA_GUIA_TV_BASE + href)
	  html match
	  { case Some(html) => 
	    {
			  val days = (html \\ "ul") . filter( n => n.attribute("class").isDefined && n.attribute("class").get.text == "channels")
			  var i:Int = 0;
			  
			  val result = days.map ( day => {
				val currentDate = new org.joda.time.DateTime().plusDays(i);
			   
			      
				val categoria_nodes = (day \\ "li") . filter( n => n.attribute("class").isDefined )
			    val programs = categoria_nodes.map( categoria_n => {
					  val categoria = categoria_n.map( n => n.attribute("class").get.text).first match {
					    case "tag-a" => "Cine"
					    case "tag-b" => "Deportes"
					    case "tag-c" => "Programa"
					    case "tag-d" => "Series"
					    case "tag-e" => "Noticias"
					  }
					  
					  val anchor = (categoria_n \\ "a"). filter( n => n.attribute("href").isDefined )
					  val links = anchor.map(n => n.attribute("href").get.text)
					  val name = anchor.map( n => (n \\ "p").text ).first
					  val hours = anchor.map(n => (n \\ "time").map(_.text) ).first//anchor.\\("time").map( _.text)
					  
					  val programDetail = links.map({
					    case "#" => None
					    case link   => getProgrammeDetail(link);
					  }).first
					    
					  Programme(currentDate, categoria, name, hours, programDetail)
				  })
				
			    i+= 1;
				programs
			  }).flatten
			  result
	    }
	  	case None => Seq()
	  }
  }
  
  case class Programme(date:org.joda.time.DateTime, categoria:String, name:String, hours:Seq[String], programDetail: Option[ProgrammeDetail] )
  
  def getProgrammeDetail(href :String ) : Option[ProgrammeDetail] = 
  {
    val findedObject = MongoDbUtil.find(PROGRAME_DETAILS_COLLECTION, MongoDBObject("href" -> href), ProgramDetailToMongo)
    if( findedObject.isDefined )
    {
      return Some(findedObject.get);
    }
    
    obtainHtml(LA_GUIA_TV_BASE + href).map(html => 
      {
    	val main = (html \\ "article") . filter( n => n.attribute("class").isDefined && n.attribute("class").get.text == "main film")
    	if(main.length == 0)
    	  return None
    	  
    	val script = (html \\ "script").filter( n => n.text.contains("CONTENIDO.id_contenido")).map( n => n.text)
    	
    	val f_parseScriptVariable = (scriptBlock:String, s:String ) => {
    	   scriptBlock.split(";").filter(n => n.contains(s) ).
    	   map(n => n.replace(s,"").replace("=","").replace(";", "").trim().toInt)
    	}
    	
    	val id_contenido = script.flatMap( n => f_parseScriptVariable(n ,"CONTENIDO.id_contenido")).first
    	val id_tipo = script.flatMap( n => f_parseScriptVariable(n ,"CONTENIDO.id_tipo")).first
    	val votos = getVotos(id_contenido, id_tipo)
    	
	    val media = (main \\ "div") . filter( n => n.attribute("class").isDefined && n.attribute("class").get.text == "mod-media")
	    val image = (media \\ "img") . map(n=> n.attribute("src")).filter( option => option.isDefined).map( attr => attr.get).map( attr => "http://www.laguiatv.com/" + attr.text)
	    
	    val mod_txt = (main \\ "div") . filter( n => n.attribute("class").isDefined && n.attribute("class").get.text == "mod-txt")
	    val meta    = (mod_txt \\ "ul"). filter( n => n.attribute("class").isDefined && n.attribute("class").get.text == "meta")
	    val li_item_prop = (meta \\ "li"). filter( n => n.attribute("itemprop").isDefined ).map(n => (n.attribute("itemprop").get.text, n.text))
	    val span_item_prop = (meta \\ "span"). filter( n => n.attribute("itemprop").isDefined ).map(n => (n.attribute("itemprop").get.text, n.text))
	    
	    
	    val dl_info = (mod_txt \ "dl"). filter( n => n.attribute("class").isDefined && n.attribute("class").get.text == "info")
	    
	    val _info = dl_info.map( n => n.child.map( n2 => createInfoFromNode(n2)) ).flatten.filter { 
	      case GuiaTvProgramInfoVoid() => false
	      case _ => true
	    }
	    
	    var info = List[GuiaTvProgramInfoCategory]()
	    
	      for( i <- _info)
	      {
	        i match {
	          case GuiaTvProgramInfoCategory(n) => {
	           info = info.+:(i.asInstanceOf[GuiaTvProgramInfoCategory]) 
	          }
	          case GuiaTvProgramInfoValue(n) => info.first.addDependency(i)
	        }
	      }
	    Console.println(info);
	    val tv_chapter = (main \\ "section") . filter( n => n.attribute("class").isDefined && n.attribute("class").get.text.contains("tv-chapters"))
	    val tv_chapter_title = (tv_chapter \\ "header").map( n => n.text)
	    val tv_chapter_desc = (tv_chapter\\ "p").map( n => n.text)
	    
	    
	    val description_link  = (mod_txt \\ "p").map( n => (n \\ "a").filter(n => n.attribute("href").isDefined).map(n => n.attribute("href").get.text)).flatten
	    val description = description_link.firstOption.map( href =>  getSinopsis(href)).flatten
	    
	    
	    val image_str = image.firstOption.getOrElse("")
	    val descr_str = description.firstOption.getOrElse("")
	    val chap_tit  = tv_chapter_title.firstOption.getOrElse("")
	    val chap_desc = tv_chapter_desc.firstOption.getOrElse("")
	    val v_total = votos.get.total.toInt
	    val v_votos = votos.get.votos.toInt
	    
	    val pDetail = new ProgrammeDetail(href, image_str, descr_str, info, chap_tit, chap_desc, v_total, v_votos)
	    MongoDbUtil.save(PROGRAME_DETAILS_COLLECTION , ProgramDetailToMongo.createMongo(pDetail))
	    return Some(pDetail)
      })
      
      return None
  }
  

  def getSinopsis(href:String):Option[String] = 
  {
     obtainHtml(LA_GUIA_TV_BASE + href).map(html => {
    	 val v = (html \\ "section").filter(n => n.attribute("class").isDefined && n.attribute("class").get.text.contains("textual")).text
    	 return Some(v.replace("Sinopsis","").trim())
     })
     return None
  }
  
  case class Votos(vota:String, clase:String,votos:String,total:String,ratingValue:String,bestRating:String)
  def getVotos(idContenido:Int, idTipo:Int) : Option[Votos]= 
  {
	  val url = "http://www.laguiatv.com/backend/HERRAMIENTAS.getVotos.php?id_contenido="+ idContenido + "&id_tipo="+ idTipo
	  HttpUtil.httpget(url) match {
	    case Some(body) => {
	      implicit val formats = net.liftweb.json.DefaultFormats 
	      
	      val x = Source.fromInputStream(body).mkString("") 
	      val json = JsonParser.parse(x)
	      Some(json.extract[Votos]) 
	    }
	    case None => None
	  }
  }
  
  def createInfoFromNode(node:Node) : GuiaTvProgramInfo   = 
  {
	  if( node.label == "dt")
	    GuiaTvProgramInfoCategory(node.text.trim())
	  else if ( node.label == "dd"){
	    val l = (node \\ "li").map( n => n.text).foldLeft("")( (currentString, node) => currentString + ", " + node) ;
	    if( l.length() > 2 )
	    	GuiaTvProgramInfoValue(l.substring(2));
	    else
	      GuiaTvProgramInfoValue(node.text);
	  }
	  else 
	    GuiaTvProgramInfoVoid()
  }
  
  case class Channel(name:String , href:String) 
  {
    val id = CryptoUtil.SHA1(href) 
  }
  
  case class ProgrammeDetail(href: String, image:String, description: String, info: List[GuiaTvProgramInfoCategory], 
		  					chapterTitle: String, chapterDescription: String, v_total:Int, v_votos: Int  )
  
  abstract  class GuiaTvProgramInfo
  {
    var depends = List[GuiaTvProgramInfo]()
    def addDependency(d:GuiaTvProgramInfo) = 
    {
       depends = depends.+:(d)
    }
  }
  case class GuiaTvProgramInfoCategory(name:String ) extends GuiaTvProgramInfo
  {
    override def toString = "[ name = " + name + " dependency[" + depends + " ] "  
  }
  case class GuiaTvProgramInfoValue(value:String) extends GuiaTvProgramInfo
  case class GuiaTvProgramInfoVoid() extends GuiaTvProgramInfo
}