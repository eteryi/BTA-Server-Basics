package cross.simplyhomes.utils;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.server.entity.player.EntityPlayerMP;

public class Location {
	public final double x;
	public final double y;
	public final double z;

	public final int dimension;
	public Location(double x, double y, double z, int dimension) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.dimension = dimension;
	}

	public void teleport(EntityPlayerMP playerMP) {
		teleport(playerMP, this.dimension, this.x, this.y, this.z);
	}

	public String toString() {
		return String.format("(x: %.1f, y: %.1f, z: %.1f)", this.x, this.y, this.z);
	}

	private static void teleport(EntityPlayer p1, Integer dimension, double x, double y, double z) {
		if (p1 instanceof EntityPlayerMP) {
			EntityPlayerMP p1MP = (EntityPlayerMP)p1;
			if (dimension != null && p1MP.dimension != dimension) {
				p1MP.mcServer.playerList.sendPlayerToOtherDimension(p1MP, dimension);
			}

			p1MP.playerNetServerHandler.teleportAndRotate(x, y, z, p1MP.yRot, p1MP.xRot);
		} else {
			p1.absMoveTo(x, y, z, p1.yRot, p1.xRot);
		}
	}

	public static Location from(EntityPlayer player) {
		return new Location(player.x, player.y, player.z, player.dimension);
	}
}
