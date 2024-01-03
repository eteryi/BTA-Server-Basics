package cross.simplyhomes.commands.tpa;

import cross.simplyhomes.SimpleHomes;
import cross.simplyhomes.modules.Modules;
import cross.simplyhomes.modules.TPA;
import cross.simplyhomes.utils.TCommand;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.server.entity.player.EntityPlayerMP;

public class TPACommand implements TCommand {
	@Override
	public boolean run(CommandSender sender, CommandHandler handler, String[] args) {
		TPA tpa = (TPA) SimpleHomes.MODULE_REGISTRY.getModule(Modules.ID.TPA);
		if (!sender.isPlayer()) return false;

		if (args.length <= 0) {
			return false;
		}

		EntityPlayerMP receiver = (EntityPlayerMP) handler.getPlayer(args[0]);
		if (receiver == null) {
			sender.sendMessage(TextFormatting.RED + " > Player not found");
			return true;
		}

		if (receiver == sender.getPlayer()) {
			sender.sendMessage(TextFormatting.RED + " > You can't teleport to yourself");
			return true;
		}
		if (!tpa.sendRequest((EntityPlayerMP) sender.getPlayer(), receiver)) {
			sender.sendMessage(TextFormatting.RED + " > You already have a teleport requested to " + receiver.getDisplayName());
			return true;
		}
		sender.sendMessage(TextFormatting.LIME + " > Teleport request sent to " + receiver.getDisplayName());
		return true;
	}

	@Override
	public boolean isOp() {
		return false;
	}
}
