package com.gmail.molnardad.quester.qevents;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.gmail.molnardad.quester.Quester;
import com.gmail.molnardad.quester.commandbase.QCommand;
import com.gmail.molnardad.quester.commandbase.QCommandContext;
import com.gmail.molnardad.quester.commandbase.exceptions.QCommandException;
import com.gmail.molnardad.quester.elements.QElement;
import com.gmail.molnardad.quester.elements.Qevent;
import com.gmail.molnardad.quester.storage.StorageKey;
import com.gmail.molnardad.quester.utils.Util;

@QElement("SOUND")
public final class SoundQevent extends Qevent {

	private final Location location;
	private final Sound sound;
	private final float volume;
	private final float pitch;
	
	public SoundQevent(Location loc, Sound snd, float vol, float pit) {
		this.location = loc;
		this.sound = snd;
		this.volume = vol;
		this.pitch = pit;
	}
	
	@Override
	public String info() {
		String locStr = "PLAYER";
		if(location != null) {
			locStr = Util.displayLocation(location);
		}
		return sound.name() + "; LOC: " + locStr + "; VOL: " + volume + "; PIT: " + pitch;
	}

	@Override
	protected void run(Player player, Quester plugin) {
		Location temp;
		if(location == null)
			temp = player.getLocation();
		else
			temp = location;
		temp.getWorld().playSound(temp, sound, volume, pitch);
	}

	@QCommand(
			min = 2,
			max = 4,
			usage = "{<sound>} {<location>} [volume] [pitch]")
	public static Qevent fromCommand(QCommandContext context) throws QCommandException {
		Sound snd = Util.parseSound(context.getString(0));
		Location loc = Util.getLoc(context.getPlayer(), context.getString(1));
		float vol = 1F;
		float pit = 1F;
		if(context.length() > 2) {
			vol = (float) context.getDouble(2);
			if(context.length() > 3) {
				pit = (float) context.getDouble(3);
			}
			if(vol < 0) {
				throw new QCommandException(context.getSenderLang().ERROR_CMD_VOL_PIT);
			}
		}
		return new SoundQevent(loc, snd, vol, pit);
	}

	@Override
	protected void save(StorageKey key) {
		key.setString("sound", sound.name());
		key.setString("location", Util.serializeLocString(location));
		if(volume != 1F) {
			key.setDouble("volume", volume);
		}
		if(pitch != 1F) {
			key.setDouble("pitch", pitch);
		}
	}
	
	protected static Qevent load(StorageKey key) {
		Sound snd = Util.parseSound(key.getString("sound", ""));
		if(snd == null) {
			return null;
		}
		float vol = (float) key.getDouble("volume", 1F);
		float pit = (float) key.getDouble("pitch", 1F);
		Location loc = Util.deserializeLocString(key.getString("location", ""));
		if(vol < 0F) {
			vol = 1F;
		}
		if(pit < 0F) {
			pit = 1F;
		}
		
		return new SoundQevent(loc, snd, vol, pit);
	}
}
