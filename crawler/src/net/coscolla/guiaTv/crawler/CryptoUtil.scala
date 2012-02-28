package net.coscolla.guiaTv.crawler

object CryptoUtil {
	def SHA1(value:String ) : String = 
	{
	  val md = java.security.MessageDigest.getInstance("SHA-1")
	  
	  bytesToString( md.digest(strToBytes(value)) )
	}
	
	private def strToBytes(value:String ) = 
	{
	  value.toCharArray().map(_.toByte)
	}
	
	private def bytesToString( value:Array[Byte]) : String = 
	{
	  return new sun.misc.BASE64Encoder().encode(value)
	}
}