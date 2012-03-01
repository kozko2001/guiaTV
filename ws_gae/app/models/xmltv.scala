package models
import siena.Text
import siena.Id
import siena.Query
import siena.Model

class xmltv extends Model 
{
 
  @Id
  var id:Long = 0 ;

  @Text
  var xml:String = "";
  
}
