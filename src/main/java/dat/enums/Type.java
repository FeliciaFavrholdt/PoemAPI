package dat.enums;

public enum Type {
	HAIKU,
	LIMERICK,
	SENRYU,
	TANKA,
	SONNET,
	VILLANELLE,
	ACROSTIC,
	ELEGY,
	ODE,
	GHAZAL,
	SESTINA,
	PANTOUM,
	TERZA_RIMA,
	TRIOLET,
	RONDEL,
	RONDEAU,
	BALLAD,
	EPIC,
	FREE_VERSE,
	LYRIC,
	NARRATIVE,
	PROSE,
	RHYMED_VERSE,
	BLANK_VERSE,
	CONCRETE,
	FOUND,
	LIGHT,
	VISUAL;

	// Method to convert string to enum, making it case-insensitive
	public static Type fromString(String type) {
		try {
			return Type.valueOf(type.toUpperCase()); // Convert to uppercase and match enum
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid poem type: " + type);
		}
	}
}