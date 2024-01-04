package cross.simplyhomes.test;

import cross.simplyhomes.SimpleHomes;
import party.iroiro.luajava.Lua;
import party.iroiro.luajava.luajit.LuaJit;

public class LuaPlugin {
	private final String script;
	public LuaPlugin(String s) {
		this.script = s;
	}

	public String getScript() {
		return this.script;
	}

	public void load() {
		try (
			Lua l = new LuaJit()) {
			l.openLibraries();
			l.pushJavaObject(System.out);
			l.setGlobal("sys");

			l.pushJavaObject(SimpleHomes.COMMAND_REGISTRY);
			l.setGlobal("command_registry");

			l.run(getScript());
		}
	}
}
