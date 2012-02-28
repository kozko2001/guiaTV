package net.coscolla.guiaTv.crawler
import sun.net.www.http.HttpClient
import java.net.URL
import java.net.HttpURLConnection
import java.text.SimpleDateFormat
import java.io.InputStream

object HttpUtil {

  
  def httpget(uri: String)  : Option[InputStream]= 
  {
      val url = new URL(uri)
      
      try
      {
    	  Some(url.openStream())
      }catch{
        case _ => None
      }
  }
  
  
  
}