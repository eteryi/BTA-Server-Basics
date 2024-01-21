package cross.simplyhomes.commands.home;

import cross.simplyhomes.SimpleHomes;
import cross.simplyhomes.modules.Modules;
import cross.simplyhomes.utils.TCommand;
import cross.simplyhomes.modules.Homes;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.server.entity.player.EntityPlayerMP;

public class HomeCommand implements TCommand {
	@Override
	public boolean run(CommandSender sender, CommandHandler handler, String[] args) {
		if (!sender.isPlayer()) return false;
		Homes homeModule = (Homes) SimpleHomes.MODULE_REGISTRY.getModule(Modules.ID.HOME);

		if (args.length <= 0) {
			sender.sendMessage(TextFormatting.ORANGE + " - Your Homes " + TextFormatting.LIGHT_GRAY + "(" + homeModule.getHomesFromUser(sender.getPlayer().username).size() + "/" + homeModule.MAX_HOMES + ")" + TextFormatting.ORANGE + ": ");
			for (Homes.Home h : homeModule.getHomesFromUser(sender.getPlayer().username)) {
				sender.sendMessage(TextFormatting.ORANGE + "     > " + h.name + " " + h.location);
			}
			return true;
		}

		Homes.Home h = homeModule.getHomeFromUser(sender.getPlayer().username, args[0]);
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
