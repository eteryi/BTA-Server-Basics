package cross.simplyhomes.commands.warps;

import cross.simplyhomes.SimpleHomes;
import cross.simplyhomes.modules.Homes;
import cross.simplyhomes.modules.Modules;
import cross.simplyhomes.modules.Warps;
import cross.simplyhomes.utils.Location;
import cross.simplyhomes.utils.TCommand;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.net.command.TextFormatting;

public class SetWarpCommand implements TCommand {
	@Override
	public boolean run(CommandSender sender, CommandHandler handler, String[] args) {
		if (!sender.isPlayer()) return false;
		if (!sender.isAdmin()) return false;

		if (args.length <= 0) {
			sender.sendMessage("/setwarp <name>");
			return true;
		}
		Warps warpModule = (Warps) SimpleHomes.MODULE_REGISTRY.getModule(Modules.ID.WARP);
		Warps.Warp warp = new Warps.Warp(args[0], Location.from(sender.getPlayer()));
		boolean t = warpModule.register(warp);
		if (!t) {
			sender.sendMessage(TextFormatting.RED + " > There's already an existing warp with that name");
			return true;
		}
		sender.sendMessage(TextFormatting.LIME + "You have created the warp [" + TextFormatting.LIGHT_GRAY + warp.name + TextFormatting.LIME + "]");
		return true;
	}

	@Override
	public boolean isOp() {
		return true;
	}
}
