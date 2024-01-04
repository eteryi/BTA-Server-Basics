package cross.simplyhomes;

import cross.simplyhomes.commands.home.HomeCommand;
import cross.simplyhomes.commands.home.SetHomeCommand;
import cross.simplyhomes.commands.tpa.TPACommand;
import cross.simplyhomes.modules.Modules;
import cross.simplyhomes.modules.TPA;
import cross.simplyhomes.test.LuaPlugin;
import cross.simplyhomes.test.LuaPlugins;
import cross.simplyhomes.utils.TCommand;
import cross.simplyhomes.utils.TCommands;
import cross.simplyhomes.modules.Homes;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.net.packet.Packet3Chat;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.lwjgl.Sys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import party.iroiro.luajava.JFunction;
import party.iroiro.luajava.Lua;
import party.iroiro.luajava.luajit.LuaJit;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class SimpleHomes implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "simplyhomes";
	public static final String VERSION = "0.0.1";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final TCommands COMMAND_REGISTRY = new TCommands();
	public static final Modules MODULE_REGISTRY = new Modules();

	public static TCommands getCommandRegistry() {
		return COMMAND_REGISTRY;
	}

	public static Modules getModuleRegistry() { return MODULE_REGISTRY; }

    @Override
    public void onInitialize() {
		System.out.println("Hello ???");
		MODULE_REGISTRY.register(new Homes());
		MODULE_REGISTRY.register(new TPA());
		MODULE_REGISTRY.register(new LuaPlugins());
		MODULE_REGISTRY.registerCommands(getCommandRegistry());

		LuaPlugins plugins = (LuaPlugins) MODULE_REGISTRY.getModule(Modules.ID.LUA);
		plugins.addPlugin("function add_command(name)\n" +
			"    command = java.new(java.import('cross.simplyhomes.test.io.LuaCommand', 'Execute'))\n" +
			"    \n" +
			"    callback = {}\n" +
			"    function callback:run(sender, handler, args)\n" +
			"        sys:println(\"command was run\")\n" +
			"        return true\n" +
			"    end\n" +
			"    commandInterface = java.proxy('cross.simplyhomes.utils.SimpleCommand', command)\n" +
			"    command:addRunnable(commandInterface)\n" +
			"    command_registry:register(command, name)\n" +
			"    sys:println(\"went here\")\n" +
			"end\n" +
			"\n" +
			"sys:println(\"Plugin initialized\")\n" +
			"add_command(\"discord\")\n");
		for (LuaPlugin plugin : plugins.getPlugins()) {
			plugin.load();
		}
	}

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void onRecipesReady() {

	}

	public static void sendMessage(EntityPlayerMP player, String message) {
		player.playerNetServerHandler.sendPacket(new Packet3Chat(message));

	}
}
