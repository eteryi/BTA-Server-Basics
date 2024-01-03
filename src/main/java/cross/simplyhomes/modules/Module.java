package cross.simplyhomes.modules;

import cross.simplyhomes.utils.TCommand;
import cross.simplyhomes.utils.TCommands;

public interface Module {
	boolean registerCommands(TCommands registry);

	Modules.ID getID();

}
