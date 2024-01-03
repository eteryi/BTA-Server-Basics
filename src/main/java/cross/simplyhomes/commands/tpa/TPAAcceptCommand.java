package cross.simplyhomes.commands.tpa;

import cross.simplyhomes.SimpleHomes;
import cross.simplyhomes.modules.Modules;
import cross.simplyhomes.modules.TPA;
import cross.simplyhomes.utils.TCommand;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.server.entity.player.EntityPlayerMP;

public class TPAAcceptCommand implements TCommand {
	@Override
	public boolean run(CommandSender sender, CommandHandler handler, String[] args) {
		TPA tpa = (TPA) SimpleHomes.MODULE_REGISTRY.getModule(Modules.ID.TPA);
		if (!sender.isPlayer()) return false;

		if (args.length >= 1) {
			EntityPlayerMP receiver = (EntityPlayerMP) handler.getPlayer(args[0]);
			if (receiver == null) {
				sender.sendMessage(TextFormatting.RED + " > Player " + args[0] + " was not found");
				return true;
			}

			if (!tpa.accept((EntityPlayerMP) sender.getPlayer(), receiver)) {
				sender.sendMessage(TextFormatting.RED + " > You don't have any teleport requests from " + args[0]);
			}
			return true;
		}
		if (!tpa.accept((EntityPlayerMP) sender.getPlayer())) {
			sender.sendMessage(TextFormatting.RED + " > You don't have any teleport requests");
		}
		return true;
	}

	@Override
	public boolean isOp() {
		return false;
	}
}
