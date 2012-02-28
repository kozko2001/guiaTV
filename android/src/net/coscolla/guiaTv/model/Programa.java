package net.coscolla.guiaTv.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Programa implements Parcelable {
	public Programa()
	{
		
	}
	
	public enum Categoria
	{
		Programa,
		Deporte,
		Serie,
		Cine,
		Noticias
	}
	
	public String rawHora;
	public long hora;
	public long hora_fin;
	public String programName;
	public Categoria programaCategoria;
	
	

	public Programa(Parcel in)
	{
		hora  = in.readLong();
		programName= in.readString();
	}
	
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(hora);
		dest.writeString(programName);
	}
	
	   public static final Parcelable.Creator CREATOR =
	    	new Parcelable.Creator() {
	            public Programa createFromParcel(Parcel in) {
	                return new Programa(in);
	            }
	 
	            public Programa[] newArray(int size) {
	                return new Programa[size];
	            }
	        };
}
