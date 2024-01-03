package cross.simplyhomes.commands.home;

import cross.simplyhomes.SimpleHomes;
import cross.simplyhomes.modules.Modules;
import cross.simplyhomes.utils.Location;
import cross.simplyhomes.utils.TCommand;
import cross.simplyhomes.modules.Homes;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.net.command.TextFormatting;

public class SetHomeCommand implements TCommand {
	@Override
	public boolean run(CommandSender sender, CommandHandler handler, String[] args) {
		if (args.length <= 0) {
			sender.sendMessage("/sethome <name>");
			return true;
		}
		Homes homeModule = (Homes) SimpleHomes.MODULE_REGISTRY.getModule(Modules.ID.HOME);

		Homes.Home h = new Homes.Home(args[0], Location.from(sender.getPlayer()), sender.getName());
		if (homeModule.getHomeFromUser(sender.getName(), h.name) != null) {
			sender.sendMessage(TextFormatting.RED + "You already have a home with that name.");
			return true;
		}

		homeModule.register(sender.getName(), h);
		sender.sendMessage(TextFormatting.LIME + "You have registered home [" + TextFormatting.WHITE + h.name + "]");

		return true;
	}

	@Override
	public boolean isOp() {
		return false;
	}
}