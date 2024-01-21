package cross.simplyhomes.mixin;


import cross.simplyhomes.SimpleHomes;
import net.minecraft.server.entity.player.EntityPlayerMP;
import net.minecraft.server.net.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerListMixin {
	@Inject(at = @At("TAIL"), method = "playerLoggedIn", remap = false)
	public void onLogin(EntityPlayerMP player, CallbackInfo ci) {
		SimpleHomes.CONFIG.loadPlayerData(player.username);
	}
}
