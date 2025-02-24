package dev.quantumfusion.dashloader.api.option;

public enum Option {
	CACHE_MODEL_LOADER(
			"cache.model",
			"Caches BakedModels which allows the game to load extremely fast", Impact.EXTREME),
	CACHE_SPRITES(
			"cache.sprite",
			"Caches Sprite/Atlases which allows the game to load textures extremely fast", Impact.EXTREME),
	CACHE_FONT(
			"cache.font",
			"Caches all of the fonts and their images.", Impact.HIGH),
	CACHE_SPLASH_TEXT(
			"cache.SplashTextResourceSupplierMixin",
			"Caches the splash texts from the main screen.", Impact.SMALL),
	CACHE_SHADER(
			"cache.shader",
			"Caches the GL Shaders.", Impact.MEDIUM),
	FAST_MODEL_IDENTIFIER_EQUALS(
			"misc.ModelIdentifierMixin",
			"Use a much faster .equals() on the ModelIdentifiers", Impact.MEDIUM),
	FAST_WALL_BLOCK(
			"WallBlockMixin",
			"Caches the 2 most common blockstates for wall blocks.", Impact.MEDIUM);

	public final String mixinContains;
	public final String description;
	public final Impact impact;

	Option(String mixinContains, String description, Impact impact) {
		this.mixinContains = mixinContains;
		this.description = description;
		this.impact = impact;
	}
}
