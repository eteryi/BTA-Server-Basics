package cross.simplyhomes.utils;

import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;

public interface TCommand {
	public boolean run(CommandSender sender, CommandHandler handler, String[] args);
	public boolean isOp();
}
