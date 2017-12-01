package instantiation;

public interface Constants {
	public static final String APP_NAME = "Ghost Wars";

	//game-states
	public static final int GAME_START = 0;
	public static final int IN_PROGRESS = 1;
	public static final int GAME_END = 2;
	public final int WAITING_FOR_PLAYERS = 3;

	//port
	public static final int PORT = 8889;


	//premade image library
	public static final GFXLibrary gfx = new GFXLibrary();

	//frame
	public static final int FRAME_WIDTH = 800;
	public static final int FRAME_HEIGHT = 600;

	//delimiter
	public static final String ATTR_DELIM = " ";
	public static final String PLAYER_DELIM = ":";
	public static final String STATE_DELIM = ".";
	public static final String MISSILE_DELIM = ",";

	//blocks
	public static final int BLOCK_SIZE = 40;
	public static final int TILE_FLOOR = 0;
	public static final int CORNER = 1;	
	public static final int HORIZONTAL_BORDER = 2;
	public static final int VERTICAL_BORDER = 3;
	
}