package cross.simplyhomes.utils.homes;

import java.util.*;

public class Homes {
	private HashMap<String, HashMap<String, Home>> homes;
	public Homes() {
		this.homes = new HashMap<>();
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
