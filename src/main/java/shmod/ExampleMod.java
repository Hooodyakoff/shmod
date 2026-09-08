package shmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ExampleMod implements ClientModInitializer {
    public static boolean isMouseLocked = false;

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("sh")
                .then(ClientCommandManager.literal("rotation")
                    .then(ClientCommandManager.argument("x", FloatArgumentType.floatArg())
                        .then(ClientCommandManager.argument("y", FloatArgumentType.floatArg())
                            .executes(context -> {
                                float x = FloatArgumentType.getFloat(context, "x");
                                float y = FloatArgumentType.getFloat(context, "y");
                                Minecraft client = Minecraft.getInstance();
                                if (client.player != null) {
                                    client.player.setYRot(x);
                                    client.player.setXRot(y);
                                    context.getSource().sendFeedback(Component.literal("Камера повернута на X: " + x + ", Y: " + y));
                                }
                                return 1;
                            })
                        )
                    )
                )
                .then(ClientCommandManager.literal("mouselock")
                    .executes(context -> {
                        isMouseLocked = !isMouseLocked;
                        String message = isMouseLocked ? "§cМышь заблокирована!" : "§aМышь разблокирована!";
                        context.getSource().sendFeedback(Component.literal(message));
                        return 1;
                    })
                )
            );
        });
    }
}
