package cross.simplyhomes.commands.warps;

import cross.simplyhomes.SimpleHomes;
import cross.simplyhomes.modules.Modules;
import cross.simplyhomes.modules.Warps;
import cross.simplyhomes.utils.TCommand;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.server.entity.player.EntityPlayerMP;

import java.util.Optional;

public class DelWarpCommand implements TCommand {
	@Override
	public boolean run(CommandSender sender, CommandHandler handler, String[] args) {
		if (!sender.isPlayer()) return false;
		if (!sender.isAdmin()) return false;

		if (args.length <= 0) {
			sender.sendMessage("/delwarp <name>");
			return true;
		}
		Warps warp = (Warps) SimpleHomes.getModuleRegistry().getModule(Modules.ID.WARP);
		String name = args[0];
		Optional<Warps.Warp> warpOptional = warp.getWarp(name);
		if (!warpOptional.isPresent()) {
			sender.sendMessage(TextFormatting.RED + "Warp [" + TextFormatting.WHITE + name + TextFormatting.RED + "] not found");
			return true;
		}
		Warps.Warp w = warpOptional.get();
		sender.sendMessage(TextFormatting.RED + "Removed Warp [" + TextFormatting.WHITE + name + TextFormatting.RED + "]");
		warp.unregister(w);
		return true;
	}

	@Override
	public boolean isOp() {
		return true;
	}
}
