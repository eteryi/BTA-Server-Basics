package cross.simplyhomes;

import cross.simplyhomes.commands.home.HomeCommand;
import cross.simplyhomes.commands.home.SetHomeCommand;
import cross.simplyhomes.commands.tpa.TPACommand;
import cross.simplyhomes.modules.Modules;
import cross.simplyhomes.modules.TPA;
import cross.simplyhomes.utils.TCommand;
import cross.simplyhomes.utils.TCommands;
import cross.simplyhomes.modules.Homes;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.net.packet.Packet3Chat;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


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
        LOGGER.info("SimplyHomes initialized.");
		MODULE_REGISTRY.register(new Homes());
		MODULE_REGISTRY.register(new TPA());
		MODULE_REGISTRY.registerCommands(getCommandRegistry());
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
