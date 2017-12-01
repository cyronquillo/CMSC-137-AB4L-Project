package instantiation;

public interface Constants {
	public static final String APP_NAME = "Ghost Wars";

	//game-states
	public static final int GAME_START = 0;
	public static final int IN_PROGRESS = 1;
	public static final int GAME_END = 2;
	public final int WAITING_FOR_PLAYERS = 3;

	//port
	public static final int PORT = 8888;


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
	public static final int TILE_CORNER = 1;	
	public static final int HORIZONTAL_BORDER = 2;
	public static final int VERTICAL_BORDER = 3;
	public static final int MAP_HEIGHT = 15;
	public static final int MAP_WIDTH = 20;

	//blocknames
	public static final String GROUND = "ground";
	public static final String CORNER = "corner";
	public static final String HORIZONTAL = "horizontal";
	public static final String VERTICAL = "vertical";

	//collision
	public static final boolean HAS_COLLIDED = true;
	public static final boolean NOT_COLLIDED = false;

	//bullet
	public static final int BULLET_SIZE = 10;
}