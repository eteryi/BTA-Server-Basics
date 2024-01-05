package cross.simplyhomes.modules;

import cross.simplyhomes.utils.TCommands;

import java.util.ArrayList;
import java.util.HashMap;

public class Modules {
	public enum ID {
		TPA("tpa"),
		LUA("lua"),
		WARP("warp"),
		HOME("home");

		public final String id;
		ID(String id) {
			this.id = id;
		}

		public String toString() {
			return "module:" + id;
		}

		public Module getModule() {
			switch (this) {
                case TPA:
					return new TPA();
				case HOME:
					return new Homes();
				case WARP:
					return new Warps();
				default:
					return null;
			}
		}
	}

	private HashMap<ID, Module> active;
	public Modules() {
		this.active = new HashMap<>();
	}

	public void register(Module m) {
		active.put(m.getID(), m);
	}

	public boolean isActive(Module m) {
		return active.get(m.getID()) != null;
	}
	public Module getModule(ID id) {
		if (active.get(id) == null) {
			System.out.println("Tried accessing module which isn't active: " + id);
		}
		return active.get(id);
	}

	public void registerCommands(TCommands registry) {
		for (Module m : active.values()) {
			m.registerCommands(registry);
		}
	}
}
