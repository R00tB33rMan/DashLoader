package dev.quantumfusion.dashloader.mixin.option.cache.font;

import dev.quantumfusion.dashloader.DashLoader;
import dev.quantumfusion.dashloader.minecraft.font.DashTrueTypeFont;
import dev.quantumfusion.dashloader.minecraft.font.FontCacheHandler;
import net.minecraft.client.font.Font;
import net.minecraft.client.font.TrueTypeFontLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.lwjgl.stb.STBTTFontinfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(TrueTypeFontLoader.class)
public class TrueTypeFontLoaderMixin {
	@Shadow
	@Final
	private Identifier filename;

	@Inject(
			method = "load",
			at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/TextureUtil;readResource(Ljava/io/InputStream;)Ljava/nio/ByteBuffer;"),
			locals = LocalCapture.CAPTURE_FAILSOFT
	)
	private void loadInject(ResourceManager manager, CallbackInfoReturnable<Font> cir, STBTTFontinfo sTBTTFontinfo) {
		FontCacheHandler.FONT_TO_IDENT.visit(DashLoader.Status.SAVE, map -> {
			map.put(sTBTTFontinfo, this.filename);
		});
	}
}
