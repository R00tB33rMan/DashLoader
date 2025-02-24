package dev.quantumfusion.dashloader.mixin.option.cache.shader;

import dev.quantumfusion.dashloader.DashLoader;
import dev.quantumfusion.dashloader.minecraft.shader.ShaderCacheHandler;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.resource.ResourceFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;
import java.util.HashMap;

@Mixin(value = GameRenderer.class, priority = 69)
public abstract class GameRendererMixin {
	@Redirect(
			method = "loadPrograms",
			at = @At(
					value = "NEW",
					target = "(Lnet/minecraft/resource/ResourceFactory;Ljava/lang/String;Lnet/minecraft/client/render/VertexFormat;)Lnet/minecraft/client/gl/ShaderProgram;"
			)
	)
	private ShaderProgram shaderCreation(ResourceFactory factory, String name, VertexFormat format) throws IOException {
		HashMap<String, ShaderProgram> shaders = ShaderCacheHandler.SHADERS.get(DashLoader.Status.LOAD);
		if (shaders != null)  {
			// If we are reading from cache load the shader and check if its cached.
			var shader = shaders.get(name);
			if (shader != null) {
				// Loads OpenGL shader.
				return shader;
			}
		}

		ShaderProgram shader = new ShaderProgram(factory, name, format);
		ShaderCacheHandler.SHADERS.visit(DashLoader.Status.SAVE, map -> map.put(name, shader));
		return shader;
	}


}
