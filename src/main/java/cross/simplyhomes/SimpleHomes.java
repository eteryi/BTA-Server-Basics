package cross.simplyhomes;

import cross.simplyhomes.commands.HomeCommand;
import cross.simplyhomes.commands.SetHomeCommand;
import cross.simplyhomes.utils.TCommands;
import cross.simplyhomes.utils.homes.Homes;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class SimpleHomes implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "therium";
	public static final String VERSION = "0.0.1";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final TCommands COMMAND_REGISTRY = new TCommands();
	private static final Homes HOMES = new Homes();

	public static TCommands getCommandRegistry() {
		return COMMAND_REGISTRY;
	}
	public static Homes getHomes() { return HOMES; }

    @Override
    public void onInitialize() {
        LOGGER.info("SimplyHomes initialized.");
		getCommandRegistry().register(new HomeCommand(), "home", "h");
		getCommandRegistry().register(new SetHomeCommand(), "sethome", "sh");
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
}
