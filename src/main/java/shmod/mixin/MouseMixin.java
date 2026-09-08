package shmod.mixin;

import shmod.ExampleMod;
import net.minecraft.client.MouseHandler;
import org.spongepowered.mixin.injection.At;
import org.spongepowered.mixin.injection.Inject;
import org.spongepowered.mixin.injection.callback.CallbackInfo;
import org.spongepowered.mixin.Mixin;

@Mixin(MouseHandler.class)
public class MouseMixin {
    @Inject(method = "turnPlayer", at = @At("HEAD"), cancellable = true)
    private void onTurnPlayer(CallbackInfo ci) {
        if (ExampleMod.isMouseLocked) {
            ci.cancel();
        }
    }
}
