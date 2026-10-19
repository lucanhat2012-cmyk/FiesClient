
package dev.fiesclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FiesClient implements ClientModInitializer {
    public static final String MOD_ID = "fiesclient";
    public static final Logger LOGGER =
            LoggerFactory.getLogger(MOD_ID);

    private static KeyMapping openGuiKey;

    @Override
    public void onInitializeClient() {
        openGuiKey = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                "key.fiesclient.open_gui",
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                KeyMapping.Category.MISC
            )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.consumeClick()) {
                client.setScreen(new FiesClickGui());
            }
        });

        LOGGER.info("FiesClient initialized!");
    }

    public static class FiesClickGui extends Screen {
        private static final int WINE = 0xFF321322;
        private static final int PINK = 0xFFFF8FCB;
        private static final int PANEL = 0xFF1D101B;

        protected FiesClickGui() {
            super(Component.literal("FiesClient"));
        }

        @Override
        protected void init() {
            int centerX = width / 2;
            int centerY = height / 2;

            addRenderableWidget(
                net.minecraft.client.gui.components.Button.builder(
                    Component.literal("Visuals"),
                    button -> showCategory("Visuals")
                ).bounds(centerX - 145, centerY - 65, 120, 28).build()
            );

            addRenderableWidget(
                net.minecraft.client.gui.components.Button.builder(
                    Component.literal("Animations"),
                    button -> showCategory("Animations")
                ).bounds(centerX - 15, centerY - 65, 120, 28).build()
            );

            addRenderableWidget(
                net.minecraft.client.gui.components.Button.builder(
                    Component.literal("Player"),
                    button -> showCategory("Player")
                ).bounds(centerX - 145, centerY - 30, 120, 28).build()
            );

            addRenderableWidget(
                net.minecraft.client.gui.components.Button.builder(
                    Component.literal("Camera"),
                    button -> showCategory("Camera")
                ).bounds(centerX - 15, centerY - 30, 120, 28).build()
            );

            addRenderableWidget(
                net.minecraft.client.gui.components.Button.builder(
                    Component.literal("Sounds"),
                    button -> showCategory("Sounds")
                ).bounds(centerX - 145, centerY + 5, 120, 28).build()
            );

            addRenderableWidget(
                net.minecraft.client.gui.components.Button.builder(
                    Component.literal("Interface"),
                    button -> showCategory("Interface")
                ).bounds(centerX - 15, centerY + 5, 120, 28).build()
            );

            addRenderableWidget(
                net.minecraft.client.gui.components.Button.builder(
                    Component.literal("Close"),
                    button -> onClose()
                ).bounds(centerX - 60, centerY + 55, 120, 28).build()
            );
        }

        private void showCategory(String category) {
            if (Minecraft.getInstance().player != null) {
                Minecraft.getInstance().player.displayClientMessage(
                    Component.literal("FiesClient: " + category),
                    true
                );
            }
        }

        @Override
        public void render(
            net.minecraft.client.gui.GuiGraphics graphics,
            int mouseX,
            int mouseY,
            float delta
        ) {
            renderBackground(graphics, mouseX, mouseY, delta);

            int left = width / 2 - 165;
            int top = height / 2 - 100;

            graphics.fill(
                left, top, left + 330, top + 205, WINE
            );
            graphics.fill(
                left + 5, top + 5,
                left + 325, top + 200, PANEL
            );

            graphics.drawCenteredString(
                font, "FiesClient",
                width / 2, top + 15, PINK
            );
            graphics.drawCenteredString(
                font, "BETA  •  1.21.11",
                width / 2, top + 32, 0xFFE9D6E4
            );

            super.render(graphics, mouseX, mouseY, delta);
        }

        @Override
        public boolean isPauseScreen() {
            return false;
        }
    }
}
