package dev.quantumfusion.dashloader.minecraft.font;

import dev.quantumfusion.dashloader.api.DashObject;
import dev.quantumfusion.dashloader.registry.RegistryReader;
import net.minecraft.client.font.BlankFont;

@DashObject(BlankFont.class)
public final class DashBlankFont implements DashFont {
	@Override
	public BlankFont export(RegistryReader exportHandler) {
		return new BlankFont();
	}
}
