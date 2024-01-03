package cross.simplyhomes.modules;

import cross.simplyhomes.commands.home.HomeCommand;
import cross.simplyhomes.commands.home.SetHomeCommand;
import cross.simplyhomes.commands.tpa.TPACommand;
import cross.simplyhomes.utils.Location;
import cross.simplyhomes.utils.TCommand;
import cross.simplyhomes.utils.TCommands;

import java.util.*;

public class Homes implements Module {
	private HashMap<String, HashMap<String, Home>> homes;
	public Homes() {
		this.homes = new HashMap<>();
	}

	@Override
	public boolean registerCommands(TCommands registry) {
		registry.register(new HomeCommand(), "home", "h");
		registry.register(new SetHomeCommand(), "sethome", "sh");

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
	}

	private HashMap<String, Home> getValue(String user) {
		HashMap<String, Home> h1 = homes.get(user);
		if (h1 == null) {
			h1 = new HashMap<>();
			homes.put(user, h1);
		}
		return h1;
	}
	public ArrayList<Home> getHomesFromUser(String user) {
		HashMap<String, Home> userHomes = getValue(user);
		return new ArrayList<>(userHomes.values());
	}

	public void register(String user, Home h) {
		HashMap<String, Home> userHomes = getValue(user);
		userHomes.put(h.name, h);
	}

	public Home getHomeFromUser(String user, String homeName) {
		HashMap<String, Home> userHomes = getValue(user);
		return userHomes.get(homeName);
	}
}
