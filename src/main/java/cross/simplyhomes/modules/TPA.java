package cross.simplyhomes.modules;

import cross.simplyhomes.SimpleHomes;
import cross.simplyhomes.commands.tpa.TPAAcceptCommand;
import cross.simplyhomes.commands.tpa.TPACommand;
import cross.simplyhomes.commands.tpa.TPADenyCommand;
import cross.simplyhomes.utils.Location;
import cross.simplyhomes.utils.TCommands;
import cross.simplyhomes.utils.runnable.TRunnable;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.net.command.TextFormatting;
import net.minecraft.core.util.helper.Time;
import net.minecraft.server.entity.player.EntityPlayerMP;
import org.lwjgl.Sys;

import java.util.HashMap;
import java.util.Stack;

public class TPA implements Module {

	private static final int MAXIMUM_TPA_TIME = 20;
	@Override
	public boolean registerCommands(TCommands registry) {
		registry.register(new TPACommand(), "tpa", "tprequest");
		registry.register(new TPAAcceptCommand(), "tpaccept", "tpaaccept");
		registry.register(new TPADenyCommand(), "tpdeny", "tpadeny");
		return true;
	}

	@Override
	public Modules.ID getID() {
		return Modules.ID.TPA;
	}

	private static class Request {
		public final EntityPlayerMP sender;
		public final EntityPlayerMP receiver;

		public final long timeSent;
		private TRunnable runnable;
		private final Request request;
		private void start() {
			runnable = new TRunnable() {
				@Override
				public void run() {
					while (true) {
						if (System.currentTimeMillis() - timeSent >= MAXIMUM_TPA_TIME * 1000) {
							TPA tpa = (TPA) SimpleHomes.MODULE_REGISTRY.getModule(Modules.ID.TPA);
							tpa.timeOut(request);
							this.cancel();
							break;
						}
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
							break;
                        }
                    }
					return;
				}
			}.start();
		}
        private Request(EntityPlayerMP sender, EntityPlayerMP receiver) {
            this.sender = sender;
            this.receiver = receiver;
			this.timeSent = System.currentTimeMillis();
			this.request = this;

			this.start();
        }

		public void accept() {
			if (runnable != null) {
				runnable.cancel();
			}
			Location.from(receiver).teleport(sender);
			SimpleHomes.sendMessage(this.receiver, TextFormatting.GRAY + " > " + TextFormatting.LIGHT_GRAY + "Accepted teleport request from " + sender.getDisplayName());
		}

		public void deny() {
			if (runnable != null) {
				runnable.cancel();
			}
			SimpleHomes.sendMessage(this.receiver, TextFormatting.GRAY + " > " + TextFormatting.LIGHT_GRAY + "You have " + TextFormatting.RED + "denied" + TextFormatting.LIGHT_GRAY + " the teleport request from " + sender.getDisplayName());
			SimpleHomes.sendMessage(this.sender, TextFormatting.GRAY + " > " + TextFormatting.LIGHT_GRAY + "Your teleport request to " + receiver.getDisplayName() + TextFormatting.RESET + TextFormatting.LIGHT_GRAY + " was " + TextFormatting.RED + "denied");
		}
    }

	private HashMap<EntityPlayerMP, Stack<Request>> tpaRequests;

	public TPA() {
		this.tpaRequests = new HashMap<>();
	}

	public Stack<Request> getStack(EntityPlayerMP p) {
		if (tpaRequests.get(p) == null) {
			tpaRequests.put(p, new Stack<>());
		}
		return tpaRequests.get(p);
	}
	public boolean sendRequest(EntityPlayerMP sender, EntityPlayerMP receiver) {
		if (peekRequest(receiver, sender) != null) {
			return false;
		}
		Request request = new Request(sender, receiver);
		SimpleHomes.sendMessage(request.receiver, TextFormatting.GRAY + " > " + sender.getDisplayName() + TextFormatting.LIGHT_GRAY + " sent you a teleport request.");
		SimpleHomes.sendMessage(request.receiver, TextFormatting.GRAY + " > " + TextFormatting.LIME + " | /tpaaccept" + TextFormatting.LIGHT_GRAY + " to accept the request");
		SimpleHomes.sendMessage(request.receiver, TextFormatting.GRAY + " > " + TextFormatting.RED + " | /tpadeny" + TextFormatting.LIGHT_GRAY + " to deny the request");

		Stack<Request> receiverStack = getStack(receiver);

		receiverStack.add(request);
		return true;
	}
	private Request getRequest(EntityPlayerMP player) {
		Stack<Request> stack = getStack(player);
		if (stack.isEmpty()) return null;
		Request r = stack.pop();
		return r;
	}
	private Request peekRequest(EntityPlayerMP player, EntityPlayerMP specific) {
		Stack<Request> stack = getStack(player);

		Request found = null;
		for (Request r : stack) {
			if (r.sender.username.equals(specific.username)) {
				found = r;
				break;
			}
		}
		return found;
	}
	private Request getRequest(EntityPlayerMP player, EntityPlayerMP specific) {
		Stack<Request> stack = getStack(player);

		Request found = null;
		for (Request r : stack) {
			if (r.sender.username.equals(specific.username)) {
				found = r;
				break;
			}
		}
		if (found == null) return null;
		stack.remove(found);
		return found;
	}
	public boolean accept(EntityPlayerMP player) {
		Request r = getRequest(player);
		if (r == null) return false;
		r.accept();
		return true;
	}
	public boolean accept(EntityPlayerMP player, EntityPlayerMP specific) {
		Request r = getRequest(player, specific);
		if (r == null) return false;
		r.accept();
		return false;
	}

	public boolean deny(EntityPlayerMP player) {
		Request r = getRequest(player);
		if (r == null) return false;
		r.deny();
		return true;
	}
	public boolean deny(EntityPlayerMP player, EntityPlayerMP specific) {
		Request r = getRequest(player, specific);
		if (r == null) return false;
		r.deny();
		return false;
	}

	public boolean timeOut(Request r) {
		Stack<Request> requests = getStack(r.receiver);
		requests.remove(r);
		SimpleHomes.sendMessage(r.sender, TextFormatting.RED + " > Your teleport request to " + r.receiver.getDisplayName() + TextFormatting.RESET + TextFormatting.RED + " has expired");
		SimpleHomes.sendMessage(r.receiver, TextFormatting.RED + " > The teleport request from " + r.sender.getDisplayName() + TextFormatting.RESET + TextFormatting.RED + " has expired");
		return false;
	}
}
