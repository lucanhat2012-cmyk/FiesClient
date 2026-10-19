
package dev.fiesclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public class FiesClient implements ClientModInitializer {
    public static final String MOD_ID = "fiesclient";
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
    }

    public static class FiesClickGui extends Screen {
        private static final int BACKGROUND = 0xE9160D16;
        private static final int PANEL = 0xFF281725;
        private static final int ACCENT = 0xFFFF83C8;
        private static final int TEXT = 0xFFFFEAF6;
        private static final int MUTED = 0xFFBFA8BA;

        private String category = "Visuals";

        private final String[] categories = {
            "Visuals", "Animations", "Player",
            "Camera", "Sounds", "Interface"
        };

        protected FiesClickGui() {
            super(Component.literal("FiesClient"));
        }

        @Override
        public void render(
            GuiGraphics g, int mouseX, int mouseY, float delta
        ) {
            g.fill(0, 0, width, height, BACKGROUND);

            int panelW = Math.min(500, width - 24);
            int panelH = Math.min(300, height - 24);
            int x = (width - panelW) / 2;
            int y = (height - panelH) / 2;

            g.fill(x, y, x + panelW, y + panelH, PANEL);
            g.fill(x, y, x + panelW, y + 3, ACCENT);

            g.drawString(font, "FiesClient", x + 16, y + 14, ACCENT);
            g.drawString(font, "1.21.11", x + panelW - 62,
                y + 14, MUTED);

            int sideX = x + 12;
            int sideY = y + 42;
            int sideW = 118;

            for (int i = 0; i < categories.length; i++) {
                int cy = sideY + i * 32;
                boolean selected = categories[i].equals(category);

                if (selected) {
                    g.fill(sideX, cy, sideX + sideW, cy + 27,
                        0xFF63304F);
                    g.fill(sideX, cy, sideX + 3, cy + 27, ACCENT);
                } else if (mouseX >= sideX && mouseX <= sideX + sideW
                    && mouseY >= cy && mouseY <= cy + 27) {
                    g.fill(sideX, cy, sideX + sideW, cy + 27,
                        0xFF392438);
                }

                g.drawString(font, categories[i],
                    sideX + 12, cy + 9, selected ? TEXT : MUTED);
            }

            int contentX = sideX + sideW + 16;
            int contentY = sideY;

            g.drawString(font, category, contentX, contentY, ACCENT);
            g.fill(contentX, contentY + 17,
                x + panelW - 14, contentY + 18, 0xFF624052);

            g.drawString(font, "Your modules will appear here.",
                contentX, contentY + 32, TEXT);
            g.drawString(font, "Tap a category to explore.",
                contentX, contentY + 48, MUTED);

            g.drawString(font, "RIGHT SHIFT  •  CLOSE",
                x + 16, y + panelH - 19, MUTED);
        }

        @Override
        public boolean mouseClicked(
            double mouseX, double mouseY, int button
        ) {
            int panelW = Math.min(500, width - 24);
            int panelH = Math.min(300, height - 24);
            int x = (width - panelW) / 2;
            int y = (height - panelH) / 2;
            int sideX = x + 12;
            int sideY = y + 42;

            if (button == 0) {
                for (int i = 0; i < categories.length; i++) {
                    int cy = sideY + i * 32;
                    if (mouseX >= sideX && mouseX <= sideX + 118
                        && mouseY >= cy && mouseY <= cy + 27) {
                        category = categories[i];
                        return true;
                    }
                }
            }

            return super.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        public boolean keyPressed(
            int keyCode, int scanCode, int modifiers
        ) {
            if (keyCode == GLFW.GLFW_KEY_RIGHT_SHIFT
                || keyCode == GLFW.GLFW_KEY_ESCAPE) {
                onClose();
                return true;
            }
            return super.keyPressed(keyCode, scanCode, modifiers);
        }

        @Override
        public void onClose() {
            Minecraft.getInstance().setScreen(null);
        }

        @Override
        public boolean isPauseScreen() {
            return false;
        }
    }
}
