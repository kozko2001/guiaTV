package net.coscolla.guiaTv.crawler.guiaTv
import net.coscolla.guiaTv.crawler.guiaTv.GuiaTvCrawler.Programacio
import scala.xml._
import java.io.FileOutputStream
import java.nio.channels.Channels
import net.coscolla.guiaTv.crawler.guiaTv.GuiaTvCrawler.ProgrammeDetail
import net.coscolla.guiaTv.crawler.guiaTv.GuiaTvCrawler.GuiaTvProgramInfoValue


object GuiaTvXml {
	def writeChannels( programacions : Seq[Programacio]) = {
	    val channels = programacions.map( p => p.channel)
	  
		val c = channels.map( c => <channel><display-name>{c.name}</display-name></channel> % Attribute(None,"id", Text(c.id), Null))
		val p = programacions.map( p => p.emisions.map(emissio => {
		  val channel = p.channel
		  val details = emissio.programDetail.getOrElse(ProgrammeDetail("","","",List(),"","",0,0)) 
		  <programme>
		  	<title>{emissio.name}</title>
		  	<desc>{details.description}</desc>
		  	<categoria>{emissio.categoria}</categoria>
		  	<chapter_title>{details.chapterTitle}</chapter_title>
		  	<chapter_desc>{details.chapterDescription}</chapter_desc>
		  	<votos> 
		  		<total>{details.v_total}</total>
		  		<rating>{details.v_votos}</rating>
		  	</votos>
		  	<properties>
		  		{getPropertiesTag(details)}
		  	</properties>
		  	<image>{details.image}</image>
		  </programme> % Attribute(None,"channel", Text(channel.id), Null) %
		  				 Attribute(None,"start", Text(emissio.date.toString("yyyyMMdd")+emissio.hours.first.replace(":","")), Null) %
		  				 Attribute(None,"end", Text(emissio.date.toString("yyyyMMdd")+emissio.hours.last.replace(":","")), Null)
		})).flatten
		save( <tv>{c}{p}</tv>, "/Users/jordicoscolla/Desktop/channels.xml")
	}
	
	def getPropertiesTag(detail:ProgrammeDetail) = 
	{
	  detail.info.map( info => {
	    val values = info.depends.map(d => d.asInstanceOf[GuiaTvProgramInfoValue]).map( _info => {
	      _info.value 
	    }).map( v => <value>{v}</value>);
	    
	    <property>{values}</property> % Attribute(None,"name", Text(info.name), Null)
	  })
	}
	val Encoding = "UTF-8"

	def save(node: Node, fileName: String) = {
	
	    val pp = new PrettyPrinter(80, 2)
	    val fos = new FileOutputStream(fileName)
	    val writer = Channels.newWriter(fos.getChannel(), Encoding)
	
	    try {
	        writer.write("<?xml version='1.0' encoding='" + Encoding + "'?>\n")
	        writer.write(pp.format(node))
	    } finally {
	        writer.close()
	    }
	
	    fileName
	}
}