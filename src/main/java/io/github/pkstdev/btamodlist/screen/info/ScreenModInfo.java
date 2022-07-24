package io.github.pkstdev.btamodlist.screen.info;

import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.Person;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiOptionsButton;
import net.minecraft.src.GuiScreen;

import java.util.ArrayList;
import java.util.List;

public class ScreenModInfo extends GuiScreen {
    private final GuiScreen parent;
    private final ModContainer mod;

    public ScreenModInfo(GuiScreen parent, ModContainer mod) {
        super(parent);
        this.parent = parent;
        this.mod = mod;
    }

    @Override
    public void initGui() {
        this.controlList.add(new GuiOptionsButton(1145, this.width / 2 - 75, this.height - 48, "Back to Mod List"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button.id == 1145) {
                this.mc.displayGuiScreen(parent);
            }
        }
    }

    @Override
    public void drawScreen(int x, int y, float renderPartialTicks) {
        this.drawDefaultBackground();
        int renderX = this.width / 2;
        int renderY = this.height / 5;
        List<String> lines = new ArrayList<>();
        ModMetadata metadata = mod.getMetadata();
        if (metadata != null) {
            if (metadata.getName() != null) {
                lines.add(metadata.getName());
            }
            if (metadata.getId() != null) {
                lines.add("Mod Id: " + metadata.getId());
            }
            if (metadata.getVersion() != null) {
                lines.add("Version: " + metadata.getVersion().getFriendlyString());
                lines.add("");
            }
            if (!metadata.getAuthors().isEmpty()) {
                lines.add("Authors:");
                for (Person author : metadata.getAuthors()) {
                    lines.add(author.getName());
                }
                lines.add("");
            }
            if (!metadata.getContributors().isEmpty()) {
                lines.add("Contributors:");
                for (Person contributor : metadata.getContributors()) {
                    lines.add(contributor.getName());
                }
                lines.add("");
            }
            if (metadata.getLicense() != null) {
                lines.add("Licenses:");
                lines.addAll(metadata.getLicense());
                lines.add("");
            }
            if (metadata.getDescription() != null) {
                lines.add("Description:");
                lines.add(metadata.getDescription());
            }
        }
        FontRenderer textRenderer = this.fontRenderer;
        int m = 0;
        for (String line : lines) {
            textRenderer.drawStringWithShadow(line,
                    renderX - (textRenderer.getStringWidth(line) / 2), renderY + m, 16777215);
            m += 14;
        }
        super.drawScreen(x, y, renderPartialTicks);
    }
}
