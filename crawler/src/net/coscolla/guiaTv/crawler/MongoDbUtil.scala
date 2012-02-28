package net.coscolla.guiaTv.crawler
import guiaTv.GuiaTvCrawler.{ProgrammeDetail, GuiaTvProgramInfoCategory, GuiaTvProgramInfoValue}
import com.mongodb.casbah.MongoConnection
import java.util.Date
import scala.collection.JavaConverters._
import com.mongodb.BasicDBList
import com.mongodb.casbah.Imports._

object MongoDbUtil {
  private val connection = MongoConnection()("guiatv_crawler")
 
  def save[T](collectionName: String, mongo: DBObject) = 
  {
	  val conn = connection(collectionName)
	  val res = conn.insert(mongo)
  }
  
  def find[T](collectionName: String, query: DBObject, converter: MongoDbConvert[T]  ) : Option[T] = {
     val conn = connection(collectionName)
     val cursor = conn.findOne(query)
     
     cursor match 
     {
       case None => None
       case db_row  => {
         val date = db_row.get.get("timestamp").asInstanceOf[Date]
         val miliSecondsPassed = new Date().getTime() - date.getTime()
         if (miliSecondsPassed > 1000*60*60*24*7)
         {
           conn.remove(query)
           None
         }else 
         {
           Some(converter.fromMongoObject(db_row.get))
         }
       } 
     }
  }
}

object ProgramDetailToMongo extends MongoDbConvert[ProgrammeDetail]
{
    def createMongo(obj:ProgrammeDetail) :DBObject = {
      MongoDBObject("href" -> obj.href, 
          "image" -> obj.image,
          "description" -> obj.description,
          "chapterTitle" -> obj.chapterTitle,
          "chapterDescription" -> obj.chapterDescription, 
          "info" -> saveInfo(obj.info),
          "v_total" -> obj.v_total,
          "v_votos" -> obj.v_votos,
          "timestamp" -> new Date() )
    }
    
    def fromMongoObject(values: DBObject): guiaTv.GuiaTvCrawler.ProgrammeDetail  = {
    	new ProgrammeDetail( values.get("href").asInstanceOf[String],
    	    				 values.get("media").asInstanceOf[String],
    	    				 values.get("description").asInstanceOf[String],
    	    				 loadInfo(values),
    	    				 values.get("chapterTitle").asInstanceOf[String],
    	    				 values.get("chapterDescription").asInstanceOf[String],
    	    				 values.get("v_total").asInstanceOf[Int],
    	    				 values.get("v_votos").asInstanceOf[Int]
    	    				 )
    }
    
    def saveInfo(categories : List[GuiaTvProgramInfoCategory]) = {
      categories.map( c => (c.name, c.depends.map(d => d.asInstanceOf[GuiaTvProgramInfoValue].value )))
    }
    
    def loadInfo(values:DBObject) = 
    {
       val v = values.get("info") 
       v match
       {
         case None =>  List[GuiaTvProgramInfoCategory]()
         case _ => {
           v.asInstanceOf[BasicDBList].toList.map( n  =>  n.asInstanceOf[BasicDBList] ).
           		   map(n => {
           		     val category = GuiaTvProgramInfoCategory(n.first.toString())
           		     val values = n(1).asInstanceOf[BasicDBList].map( n => GuiaTvProgramInfoValue(n.toString())).toList
           		     category.depends = values
           		     
           		     category
           		   })
         }
       }
    }
    
}

trait MongoDbConvert[T] 
{
  def createMongo(obj:ProgrammeDetail) :DBObject
  def fromMongoObject(values: DBObject): T 
}