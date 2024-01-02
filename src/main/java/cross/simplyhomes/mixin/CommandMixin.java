package cross.simplyhomes.mixin;

import cross.simplyhomes.SimpleHomes;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.Commands;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Commands.class)
public class CommandMixin {
	@Shadow
	public static List<Command> commands;

	@Inject(at=@At("TAIL"), method = "initServerCommands", remap = false)
	private static void initServerCommands(MinecraftServer server, CallbackInfo ci) {
        commands.addAll(SimpleHomes.getCommandRegistry().getRegistry());
		SimpleHomes.getCommandRegistry().setCommandRegistry(commands);
	}

}
