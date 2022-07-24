package io.github.pkstdev.btamodlist.screen.list;

import io.github.pkstdev.btamodlist.screen.info.ScreenModInfo;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiOptionsButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.StringTranslate;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ScreenModList extends GuiScreen {
    private ScreenModListSlot modList;
    private final GuiScreen parent;

    public ScreenModList(GuiScreen parent) {
        super(parent);
        this.parent = parent;
    }

    @Override
    public void initGui() {
        StringTranslate i18n = StringTranslate.getInstance();
        this.controlList.add(new GuiOptionsButton(10000, this.width / 2 - 229, this.height - 48, "Open Mods Folder"));
        this.controlList.add(new GuiOptionsButton(6, this.width / 2 + 79, this.height - 48, i18n.translateKey("gui.done")));
        this.controlList.add(new GuiOptionsButton(1919, this.width / 2 - 75, this.height - 48, "More Info"));
        this.modList = new ScreenModListSlot(this);
        this.modList.registerScrollButtons(this.controlList, 10001, 10002);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button.id == 10000) {
                try {
                    Desktop.getDesktop().open(new File(Minecraft.getMinecraftDir(), "mods").getAbsoluteFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (button.id == 6) {
                this.mc.displayGuiScreen(this.parent);
            } else if (button.id == 1919) {
                this.mc.displayGuiScreen(new ScreenModInfo(this, modList.getSelectedMod()));
            } else {
                this.modList.actionPerformed(button);
            }
        }
    }

    @Override
    public void drawScreen(int x, int y, float renderPartialTicks) {
        this.modList.drawScreen(x, y, renderPartialTicks);
        this.drawString(mc.fontRenderer,
                "Mods (" + FabricLoader.getInstance().getAllMods().size() + " Loaded)",
                (this.parent.width - mc.fontRenderer.getStringWidth("Mods (" + FabricLoader.getInstance().getAllMods().size() + " Loaded)")) / 2,
                16,
                16777215);
        super.drawScreen(x, y, renderPartialTicks);
    }
}
