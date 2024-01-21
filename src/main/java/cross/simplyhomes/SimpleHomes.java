package cross.simplyhomes;

import cross.simplyhomes.config.Config;
import cross.simplyhomes.modules.Modules;
import cross.simplyhomes.modules.TPA;
import cross.simplyhomes.modules.Warps;
import cross.simplyhomes.utils.TCommands;
import cross.simplyhomes.modules.Homes;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.net.packet.Packet3Chat;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

import java.io.File;


public class SimpleHomes implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "basics";
	public static final String VERSION = "0.0.1";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final TCommands COMMAND_REGISTRY = new TCommands();
	public static final Modules MODULE_REGISTRY = new Modules();
	public static final Config CONFIG = new Config();
	public static TCommands getCommandRegistry() {
		return COMMAND_REGISTRY;
	}

	public static Modules getModuleRegistry() { return MODULE_REGISTRY; }

    @Override
    public void onInitialize() {
		System.out.println("[Basics] Basics has been loaded");
		CONFIG.init();

		if (CONFIG.isModuleActive(Modules.ID.HOME)) {
			MODULE_REGISTRY.register(new Homes());
			System.out.println("[Basics] HOMES has been loaded");
		}
		if (CONFIG.isModuleActive(Modules.ID.TPA)) {
			MODULE_REGISTRY.register(new TPA());
			System.out.println("[Basics] TPA has been loaded");
		}
		if (CONFIG.isModuleActive(Modules.ID.WARP)) {
			MODULE_REGISTRY.register(new Warps());
			System.out.println("[Basics] WARPS has been loaded");
		}
		MODULE_REGISTRY.registerCommands(getCommandRegistry());
		CONFIG.loadWarp();
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
