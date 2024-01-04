package cross.simplyhomes.test;

import cross.simplyhomes.modules.Module;
import cross.simplyhomes.modules.Modules;
import cross.simplyhomes.utils.TCommands;

import java.util.ArrayList;


public class LuaPlugins implements Module {
	@Override
	public boolean registerCommands(TCommands registry) {
		return true;
	}


	@Override
	public Modules.ID getID() {
		return Modules.ID.LUA;
	}

	private ArrayList<LuaPlugin> plugins;

	public LuaPlugins() {
		this.plugins = new ArrayList<>();
	}

	public ArrayList<LuaPlugin> getPlugins() {
		return plugins;
	}

	public void addPlugin(String s) {
		plugins.add(new LuaPlugin(s));
	}

	public void addPlugin(LuaPlugin p) {
		plugins.add(p);
	}
}
