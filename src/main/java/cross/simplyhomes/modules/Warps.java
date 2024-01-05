package cross.simplyhomes.modules;

import cross.simplyhomes.utils.TCommands;

public class Warps implements Module {
	@Override
	public boolean registerCommands(TCommands registry) {
		return true;
	}

	@Override
	public Modules.ID getID() {
		return Modules.ID.WARP;
	}


	public Warps() {

	}
}
