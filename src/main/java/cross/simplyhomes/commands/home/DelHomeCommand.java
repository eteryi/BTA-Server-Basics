package cross.simplyhomes.commands.home;

import cross.simplyhomes.SimpleHomes;
import cross.simplyhomes.modules.Homes;
import cross.simplyhomes.modules.Modules;
import cross.simplyhomes.utils.TCommand;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.net.command.TextFormatting;

public class DelHomeCommand implements TCommand {
	@Override
	public boolean run(CommandSender sender, CommandHandler handler, String[] args) {
		if (!sender.isPlayer()) return false;

		if (args.length <= 0) {
			sender.sendMessage(TextFormatting.RED + " > /delhome [name]");
			return true;
		}
		Homes homeModule = (Homes) SimpleHomes.MODULE_REGISTRY.getModule(Modules.ID.HOME);
		String homeName = args[0];
		Homes.Home h = homeModule.getHomeFromUser(sender.getPlayer().username, homeName);

		if (h == null) {
			sender.sendMessage(TextFormatting.RED + " > You don't have a home with the name [" + TextFormatting.WHITE +  homeName + TextFormatting.RED +  "]");
			return true;
		}

		homeModule.remove(sender.getPlayer().username, h);
		sender.sendMessage(TextFormatting.GRAY + " > " + TextFormatting.LIGHT_GRAY + "Successfully deleted home [" + TextFormatting.WHITE + homeName + TextFormatting.LIGHT_GRAY + "]");
		return true;
	}

	@Override
	public boolean isOp() {
		return false;
	}
}
