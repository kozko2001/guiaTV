package net.coscolla.guiaTv.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Channel implements Parcelable{
	public String channelId;
	public String channelDesc;
	
	public Channel(String id, String desc)
	{
		this.channelDesc = desc;
		this.channelId = id;
		
	}
	
	public Channel(Parcel in)
	{
		channelId  = in.readString();
		channelDesc= in.readString();
	}
	
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(channelId);
		dest.writeString(channelDesc);
	}
	
	   public static final Parcelable.Creator CREATOR =
	    	new Parcelable.Creator() {
	            public Channel createFromParcel(Parcel in) {
	                return new Channel(in);
	            }
	 
	            public Channel[] newArray(int size) {
	                return new Channel[size];
	            }
	        };
	
}
