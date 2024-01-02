package cross.simplyhomes.commands;

import cross.simplyhomes.SimpleHomes;
import cross.simplyhomes.utils.TCommand;
import cross.simplyhomes.utils.homes.Home;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.server.entity.player.EntityPlayerMP;

public class HomeCommand implements TCommand {
	@Override
	public boolean run(CommandSender sender, CommandHandler handler, String[] args) {
		if (args.length <= 0) {
			sender.sendMessage(TextFormatting.ORANGE + "Your Homes: ");
			for (Home h : SimpleHomes.getHomes().getHomesFromUser(sender.getName())) {
				sender.sendMessage(TextFormatting.ORANGE + "    > " + h.name + " " + h.location);
			}
			return true;
		}

		Home h = SimpleHomes.getHomes().getHomeFromUser(sender.getName(), args[0]);
		if (h == null) {
			sender.sendMessage(TextFormatting.RED + " > Home not found");
			return true;
		}

		h.location.teleport((EntityPlayerMP) sender.getPlayer());
		sender.sendMessage(TextFormatting.LIME + "Teleported to [" + TextFormatting.WHITE + h.name + TextFormatting.LIME + "]");
		return true;
	}

	@Override
	public boolean isOp() {
		return false;
	}
}
