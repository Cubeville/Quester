package com.gmail.molnardad.quester.qevents;

import static com.gmail.molnardad.quester.utils.Util.getLoc;
import static com.gmail.molnardad.quester.utils.Util.parseItem;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.gmail.molnardad.quester.commandbase.QCommand;
import com.gmail.molnardad.quester.commandbase.QCommandContext;
import com.gmail.molnardad.quester.commandbase.exceptions.QCommandException;
import com.gmail.molnardad.quester.elements.QElement;
import com.gmail.molnardad.quester.elements.Qevent;
import com.gmail.molnardad.quester.utils.Util;

@QElement("BLOCK")
public final class SetBlockQevent extends Qevent {

	private final Location location;
	public final int material;
	public final byte data;
	
	public SetBlockQevent(int mat, int dat, Location loc) {
		this.location = loc;
		this.material = mat;
		this.data = (byte) dat;
	}
	
	@Override
	public String info() {
		return material + ":" + data + "; " + "; LOC: " + Util.displayLocation(location);
	}

	@Override
	protected void run(Player player) {
		location.getBlock().setTypeIdAndData(material, data, true);
	}

	@QCommand(
			min = 2,
			max = 2,
			usage = "{<block>} {<location>}")
	public static Qevent fromCommand(QCommandContext context) throws QCommandException {
		int[] itm = parseItem(context.getString(0));
		if(itm[0] > 255) {
			throw new QCommandException(context.getSenderLang().ERROR_CMD_BLOCK_UNKNOWN);
		}
		int dat = itm[1] < 0 ? 0 : itm[1];
		Location loc = getLoc(context.getPlayer(), context.getString(1));
		return new SetBlockQevent(itm[0], dat, loc);
	}

	// TODO serialization
	
	public void serialize(ConfigurationSection section) {
		section.set("block", Util.serializeItem(material, data));
		section.set("location", Util.serializeLocString(location));
		
	}
	
	public static SetBlockQevent deser(ConfigurationSection section) {
		int mat = 0, dat = 0;
		Location loc = null;
		try {
			int[] itm = Util.parseItem(section.getString("block"));
			mat = itm[0];
			dat = itm[1];
			if(dat < 0)
				dat = 0;
			if(section.isString("location"))
				loc = Util.deserializeLocString(section.getString("location"));
			if(loc == null)
				return null;
		} catch (Exception e) {
			return null;
		}
		
		return new SetBlockQevent(mat, dat, loc);
	}
}
