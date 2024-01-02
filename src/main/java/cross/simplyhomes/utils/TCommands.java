package cross.simplyhomes.utils;

import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.CommandHandler;
import net.minecraft.core.net.command.CommandSender;
import net.minecraft.core.net.command.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class TCommands {
	private ArrayList<Command> registry;
	private List<Command> commandRegistry;
	public TCommands() {
		registry = new ArrayList<>();
	}

	public ArrayList<Command> getRegistry() {
		return (ArrayList<Command>) registry.clone();
	}

	public void setCommandRegistry(List<Command> commands) {
		if (commandRegistry != null) {
			System.out.println("[Therium] Command Registry was set more than once.");
		}
		this.commandRegistry = commands;
	}


	public void register(TCommand command, String... aliases) {
		Command c = new Command(aliases[0], aliases) {
			@Override
			public boolean execute(CommandHandler commandHandler, CommandSender commandSender, String[] strings) {
				return command.run(commandSender, commandHandler, strings);
			}

			@Override
			public boolean opRequired(String[] strings) {
				return command.isOp();
			}

			@Override
			public void sendCommandSyntax(CommandHandler commandHandler, CommandSender commandSender) {
				commandSender.sendMessage(TextFormatting.RED + " [] > You didn't use /" + aliases[0] + " correctly");
			}
		};

		registry.add(c);
		if (commandRegistry != null) {
			commandRegistry.add(c);
		}
	}
}
