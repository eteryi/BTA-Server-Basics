package cross.simplyhomes.test.io.impl;

import cross.simplyhomes.test.io.Sender;
import net.minecraft.core.net.command.CommandSender;

public class LuaSender implements Sender {
	public final CommandSender sender;

	public LuaSender(CommandSender sender) {
		this.sender = sender;
	}

	@Override
	public void sendMessage(String msg) {
		sender.sendMessage(msg);
	}
}
