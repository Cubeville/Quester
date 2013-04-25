package com.gmail.molnardad.quester;

import org.bukkit.Location;

import com.gmail.molnardad.quester.storage.StorageKey;
import com.gmail.molnardad.quester.utils.Util;

public class QuesterSign {

	/**
	 * @uml.property  name="holder"
	 */
	private int holder = -1;
	/**
	 * @uml.property  name="location"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private final Location location;
	
	public QuesterSign(Location location) {
		this.location = location;
	}
	
	/**
	 * @return
	 * @uml.property  name="location"
	 */
	public Location getLocation() {
		return location;
	}
	
	public int getHolderID() {
		return holder;
	}
	
	public void setHolderID(int ID) {
		holder = ID;
	} 
	
	public void serialize(StorageKey key) {
		key.setString("location", Util.serializeLocString(location));
		key.setInt("holder", holder);
	}
	
	public static QuesterSign deserialize(StorageKey key) {
		Location loc;
		QuesterSign sign = null;
		
		try{
			loc = Util.deserializeLocString(key.getString("location", ""));
			if(loc == null) {
				return null;
			}
			sign = new QuesterSign(loc);
			sign.setHolderID(key.getInt("holder" , -1));
		} catch (Exception ignore) {}
			
		return sign;
	}
}
