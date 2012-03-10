package controllers

import java.io.ByteArrayOutputStream
import java.util.zip.GZIPOutputStream
import java.io.File
import java.io.FileInputStream
import scala.tools.nsc.io.Streamable$
import scala.tools.nsc.io.Streamable
import java.util.zip.GZIPInputStream
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.io.FileOutputStream
import java.io.BufferedWriter
import java.io.FileWriter
import java.io.BufferedOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import org.mortbay.log.Log
import play.Logger
import java.util.zip.ZipInputStream

object GZip {
  
  val encoding = "UTF-8"
    
    def compress(str:String):String = {
      
      val is = new ByteArrayInputStream(str.getBytes(encoding));
      compress(is);
    }
	
	
	def compress(is:InputStream):String = {
    	val b = new Streamable.Bytes { def inputStream() = is } toByteArray
    	val out = new ByteArrayOutputStream();
    	val zout= new ZipOutputStream( out);
    	zout.setMethod(ZipOutputStream.DEFLATED)
    	val entry = new ZipEntry("data");
    	
    	zout.putNextEntry(entry)
    	zout.write(b)
    	zout.closeEntry()
    	zout.close();
    	
    	return new sun.misc.BASE64Encoder().encode(out.toByteArray())
	}
	
	def decompress(_str:String):String = {
	  val bytes= new sun.misc.BASE64Decoder().decodeBuffer(_str)
	  val is = new ByteArrayInputStream(bytes);
	  
      val zis = new ZipInputStream( is);
      val entry = zis.getNextEntry()
      
	  val ret = scala.io.Source.fromInputStream(zis).getLines().mkString("\n")
	  zis.close()
	  
	  ret
	}
		
}