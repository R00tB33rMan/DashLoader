package dev.quantumfusion.dashloader.data.image.shader;

import com.mojang.blaze3d.platform.GlStateManager;
import dev.quantumfusion.dashloader.mixin.accessor.EffectShaderStageAccessor;
import dev.quantumfusion.dashloader.mixin.accessor.ShaderStageAccessor;
import dev.quantumfusion.dashloader.util.MissingDataException;
import java.util.List;

import net.minecraft.client.gl.EffectShaderStage;
import net.minecraft.client.gl.ShaderStage;
import org.apache.commons.lang3.StringUtils;
import static dev.quantumfusion.dashloader.DashLoader.DL;

public final class DashShaderStage {
	public final ShaderStage.Type shaderType;
	public final String name;
	public final List<String> shader;

	public DashShaderStage(ShaderStage.Type shaderType, String name, List<String> shader) {
		this.shaderType = shaderType;
		this.name = name;
		this.shader = shader;
	}

	public DashShaderStage(ShaderStage program) throws MissingDataException {
		ShaderStageAccessor access = (ShaderStageAccessor) program;
		this.shaderType = access.getType();
		this.name = program.getName();
		List<String> shader = DL.getData().getWriteContextData().programData.get(access.getGlRef());
		if (shader == null) {
			throw new MissingDataException();
		}
		this.shader = shader;
	}

	public int createProgram(ShaderStage.Type type) {
		//noinspection ConstantConditions (MixinAccessor shit)
		int i = GlStateManager.glCreateShader(((ShaderStageAccessor.TypeAccessor) (Object) type).getGlType());
		GlStateManager.glShaderSource(i, this.shader);
		GlStateManager.glCompileShader(i);
		if (GlStateManager.glGetShaderi(i, 35713) == 0) {
			String string2 = StringUtils.trim(GlStateManager.glGetShaderInfoLog(i, 32768));
			throw new RuntimeException("Couldn't compile " + type.getName() + " : " + string2);
		} else {
			return i;
		}
	}

	public ShaderStage exportProgram() {
		final ShaderStage program = ShaderStageAccessor.create(this.shaderType, this.createProgram(this.shaderType), this.name);
		this.shaderType.getLoadedShaders().put(this.name, program);
		return program;
	}

	public EffectShaderStage exportEffectProgram() {
		return EffectShaderStageAccessor.create(this.shaderType, this.createProgram(this.shaderType), this.name);
	}
}
