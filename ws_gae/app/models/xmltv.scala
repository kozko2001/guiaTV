package models
import siena.Text
import siena.Id
import siena.Query
import siena.Model
import controllers.GZip

class xmltv extends Model 
{
 
  @Id
  var id:Long = 0 ;

  @Text
  var xml:String = "";
  
  
  def xmlDecoded() : String = 
  {
    return GZip.decompress(xml);
  }
  
  def xmlEndoded(_xml:String)  = 
  {
    xml = GZip.compress(_xml)
  }
}
