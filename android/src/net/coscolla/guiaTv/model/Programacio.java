package net.coscolla.guiaTv.model;

import java.util.ArrayList;
import java.util.Hashtable;

import android.os.Parcel;
import android.os.Parcelable;

public class Programacio  implements Parcelable{
	public ArrayList<Programa> programes;
	public String dia;
	
	public Programacio()
	{
		programes = new ArrayList<Programa>();
	}
	

	public Programacio(Parcel in)
	{
		dia = in.readString();
		
		int size = in.readInt();
		for(int i = 0; i<size; i++)
		{
			programes.add((Programa)in.readParcelable(null));
		}
	}
	
	public static ArrayList<Programacio> programacioPerDies(Programacio programacio)
	{
		Hashtable<String, Programacio> r = new Hashtable<String, Programacio>();
		Programa prev = null;
		String   prevDay = null;
		
		for(Programa prog : programacio.programes)
		{
			String day = prog.rawHora.substring(0,4+2+2);
			
			if( !r.containsKey(day))
			{
				Programacio aux = new Programacio();
				aux.dia = day;
				r.put(day, aux);
				
				if(prev != null)
					aux.programes.add(prev); // add the last program of the day before;
			}
			
			r.get(day).programes.add(prog);
			
			prevDay = day;
			prev = prog;
		}
		
		return new ArrayList<Programacio>(r.values());
	}
	
	

	
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(dia);
		dest.writeInt(programes.size());
		for(int i = 0 ; i<0; i++)
		{
			dest.writeParcelable(programes.get(i), 0);
		}
	}
	
	   public static final Parcelable.Creator CREATOR =
	    	new Parcelable.Creator() {
	            public Programacio createFromParcel(Parcel in) {
	                return new Programacio(in);
	            }
	 
	            public Programacio[] newArray(int size) {
	                return new Programacio[size];
	            }
	        };
	
}
