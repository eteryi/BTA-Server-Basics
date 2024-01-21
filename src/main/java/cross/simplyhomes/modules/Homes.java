package cross.simplyhomes.modules;

import cross.simplyhomes.SimpleHomes;
import cross.simplyhomes.commands.home.DelHomeCommand;
import cross.simplyhomes.commands.home.HomeCommand;
import cross.simplyhomes.commands.home.SetHomeCommand;
import cross.simplyhomes.commands.tpa.TPACommand;
import cross.simplyhomes.config.Config;
import cross.simplyhomes.utils.Location;
import cross.simplyhomes.utils.TCommand;
import cross.simplyhomes.utils.TCommands;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.*;

public class Homes implements Module {
	private HashMap<String, ArrayList<Home>> homes;
	public final byte MAX_HOMES;
	public Homes() {
		this.homes = new HashMap<>();
		this.MAX_HOMES = 20;
	}

	@Override
	public boolean registerCommands(TCommands registry) {
		registry.register(new HomeCommand(), "home", "h", "homes");
		registry.register(new SetHomeCommand(), "sethome", "sh");
		registry.register(new DelHomeCommand(), "delhome", "dh");

		return true;
	}

	@Override
	public Modules.ID getID() {
		return Modules.ID.HOME;
	}

	public static class Home {
		public final String name;
		public final Location location;
		public final String owner;

		public Home(String name, Location loc, String owner) {
			this.name = name;
			this.location = loc;
			this.owner = owner;
		}

		public static JSONObject serialize(Home h) {
			// name
			// location [2.0, 5.0, 8.0, 1]
			JSONObject object = new JSONObject();
			object.put("name", h.name);
			JSONArray array = new JSONArray();
			array.put(0, h.location.x);
			array.put(1, h.location.y);
			array.put(2, h.location.z);
			array.put(3, h.location.dimension);
			object.put("location", array);

			return object;
		}
		public static Home deserialize(JSONObject s, String username) {
			String name = s.getString("name");
			JSONArray locArr = s.getJSONArray("location");
			Location loc = new Location(locArr.getDouble(0), locArr.getDouble(1), locArr.getDouble(2), locArr.getInt(3));
            return new Home(name, loc, username);
		}
	}

	private ArrayList<Home> getValue(String user) {
		ArrayList<Home> h1 = homes.get(user);
		if (h1 == null) {
			h1 = new ArrayList<>();
			homes.put(user, h1);
		}
		return h1;
	}
	public ArrayList<Home> getHomesFromUser(String user) {
		ArrayList<Home> userHomes = getValue(user);
		return new ArrayList<>(userHomes);
	}

	public void register(String user, Home h) {
		ArrayList<Home> userHomes = getValue(user);
		userHomes.add(h);
		SimpleHomes.CONFIG.updateHomes(user, userHomes);
	}
	public void registerFromConfig(String user, Home h) {
		ArrayList<Home> userHomes = getValue(user);
		userHomes.add(h);
	}

	public Home getHomeFromUser(String user, String homeName) {
		ArrayList<Home> userHomes = getValue(user);

		for (Home i : userHomes) {
			if (i.name.equals(homeName)) {
				return i;
			}
		}
		return null;
	}

	public void remove(String user, Home h) {
		ArrayList<Home> homes = getValue(user);
		homes.remove(h);
		SimpleHomes.CONFIG.updateHomes(user, homes);
	}
}
