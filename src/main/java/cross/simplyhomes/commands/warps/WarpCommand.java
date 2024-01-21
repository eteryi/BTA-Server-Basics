package cross.simplyhomes.commands.warps;

import cross.simplyhomes.SimpleHomes;
import cross.simplyhomes.modules.Homes;
import cross.simplyhomes.modules.Modules;
import cross.simplyhomes.modules.Warps;
import cross.simplyhomes.utils.TCommand;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.server.entity.player.EntityPlayerMP;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toCollection;


public class WarpCommand implements TCommand {
	@Override
	public boolean run(CommandSender sender, CommandHandler handler, String[] args) {
		if (!sender.isPlayer()) return false;
		Warps warp = (Warps) SimpleHomes.getModuleRegistry().getModule(Modules.ID.WARP);

		String action = args.length <= 0 ? "help" : args[0].toLowerCase();
		switch (action) {
			case "list":
				int page = args.length >= 2 ? Integer.parseInt(args[1]) : 1;
				if (page <= 0) return false;
				ArrayList<Warps.Warp> warps = warp.getWarps().stream().map(Warps.Warp::new).collect(toCollection(ArrayList::new));
				int pages = (int) Math.ceil((double) warps.size() / (double) 10);
				if (page > pages) page = pages;
				sender.sendMessage(TextFormatting.LIGHT_GRAY + "Warps  - Page (" + page + "/" + pages + ")");
				if (warps.size() <= 0) return true;
				for (int i = 10 * (page - 1); i < 10 * page; i++) {
					if (i >= warps.size()) break;
					Warps.Warp w = warps.get(i);
					sender.sendMessage("     > " + w.name + " " + w.loc);
				}

				return true;
			case "tp":
				String name = args[1];
				Optional<Warps.Warp> warpOptional = warp.getWarp(name);
				if (!warpOptional.isPresent()) {
					sender.sendMessage(TextFormatting.RED + "Warp [" + TextFormatting.WHITE + name + TextFormatting.RED + "] not found");
					return true;
				}
				Warps.Warp w = warpOptional.get();
				w.loc.teleport((EntityPlayerMP) sender.getPlayer());
				sender.sendMessage(TextFormatting.LIME + "Teleporting to warp [" + TextFormatting.WHITE + name + TextFormatting.LIME + "]");
				return true;
			default:
				sender.sendMessage("/warp <list/tp> <page/name>");
				return true;
		}
    }

	@Override
	public boolean isOp() {
		return false;
	}
}
