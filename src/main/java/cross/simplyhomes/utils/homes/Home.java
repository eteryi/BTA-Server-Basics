package cross.simplyhomes.utils.homes;

import cross.simplyhomes.utils.Location;

public class Home {
	public final String name;
	public final Location location;
	public final String owner;

	public Home(String name, Location loc, String owner) {
		this.name = name;
		this.location = loc;
        this.owner = owner;
    }
}
