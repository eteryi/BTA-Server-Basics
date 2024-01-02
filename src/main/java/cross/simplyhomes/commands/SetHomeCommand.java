package cross.simplyhomes.commands;

import cross.simplyhomes.SimpleHomes;
import cross.simplyhomes.utils.Location;
import cross.simplyhomes.utils.TCommand;
import cross.simplyhomes.utils.homes.Home;
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

		Home h = new Home(args[0], Location.from(sender.getPlayer()), sender.getName());
		if (SimpleHomes.getHomes().getHomeFromUser(sender.getName(), h.name) != null) {
			sender.sendMessage(TextFormatting.RED + "You already have a home with that name.");
			return true;
		}

		SimpleHomes.getHomes().register(sender.getName(), h);
		sender.sendMessage(TextFormatting.LIME + "You have registered home [" + TextFormatting.WHITE + h.name + "]");

		return true;
	}

	@Override
	public boolean isOp() {
		return false;
	}
}
