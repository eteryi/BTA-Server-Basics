package cross.simplyhomes.modules;

import com.google.common.collect.Streams;
import cross.simplyhomes.SimpleHomes;
import cross.simplyhomes.commands.warps.DelWarpCommand;
import cross.simplyhomes.commands.warps.SetWarpCommand;
import cross.simplyhomes.commands.warps.WarpCommand;
import cross.simplyhomes.utils.Location;
import cross.simplyhomes.utils.TCommands;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

public class Warps implements Module {
	@Override
	public boolean registerCommands(TCommands registry) {
		registry.register(new WarpCommand(), "warp");
		registry.register(new DelWarpCommand(), "delwarp");
		registry.register(new SetWarpCommand(), "setwarp");
		return true;
	}

	@Override
	public Modules.ID getID() {
		return Modules.ID.WARP;
	}

	public static class Warp {
		public final Location loc;
		public final String name;

		public Warp(String name, Location loc) {
			this.name = name;
			this.loc = loc;
		}

		public Warp(Warp w) {
			this.name = w.name;
			this.loc = w.loc;
		}

		public static JSONObject serialize(Warp w) {
			// name
			// location [2.0, 5.0, 8.0, 1]
			JSONObject object = new JSONObject();
			object.put("name", w.name);
			JSONArray array = new JSONArray();
			array.put(0, w.loc.x);
			array.put(1, w.loc.y);
			array.put(2, w.loc.z);
			array.put(3, w.loc.dimension);
			object.put("location", array);

			return object;
		}
		public static Warp deserialize(JSONObject s) {
			String name = s.getString("name");
			JSONArray locArr = s.getJSONArray("location");
			Location loc = new Location(locArr.getDouble(0), locArr.getDouble(1), locArr.getDouble(2), locArr.getInt(3));
			return new Warp(name, loc);
		}
	}

	private ArrayList<Warp> warps;
	public Warps() {
		this.warps = new ArrayList<>();
	}

	public boolean register(Warp w) {
		if (warps.stream().anyMatch(it -> it.name.equals(w.name))) return false;
		warps.add(w);
		SimpleHomes.CONFIG.updateWarp(warps);
		return true;
	}

	public void unregister(Warp w) {
		warps.remove(w);
		SimpleHomes.CONFIG.updateWarp(warps);
	}

	public Optional<Warp> getWarp(String name) {
		Stream<Warp> s = warps.stream().filter(it -> it.name.equals(name));
		return s.findFirst();
	}

	public void loadFromConfig(Warp w) {
		if (warps.stream().anyMatch(it -> it.name.equals(w.name))) return;
		warps.add(w);
	}

	public ArrayList<Warp> getWarps() {
		return warps;
	}
}
