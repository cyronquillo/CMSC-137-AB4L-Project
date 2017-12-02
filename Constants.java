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
	public static final int FRAME_WIDTH = 1000;
	public static final int FRAME_HEIGHT = 800;

	//chatPanel
	public static final int CHAT_PANEL_WIDTH = 250;
	public static final int CHAT_PANEL_HEIGHT = 800;

	//statPanel
	public static final int STAT_PANEL_WIDTH = 250;
	public static final int STAT_PANEL_HEIGHT = 800;

	//delimiter
	public static final String ATTR_DELIM = " ";
	public static final String PLAYER_DELIM = ":";
	public static final String STATE_DELIM = ".";
	public static final String MISSILE_DELIM = ",";

	//blocks
	public static final int BLOCK_SIZE = 40;
	public static final int SPRITE_SIZE = 32;
	public static final int TILE_FLOOR = 0;
	public static final int TILE_CORNER = 1;	
	public static final int HORIZONTAL_BORDER = 2;
	public static final int VERTICAL_BORDER = 3;
	public static final int HORIZONTAL_LEFT_BORDER = 4;
	public static final int HORIZONTAL_RIGHT_BORDER = 5;
	public static final int VERTICAL_UP_BORDER = 6;
	public static final int VERTICAL_DOWN_BORDER = 7;
	public static final int STEEL_BLOCK = 8;
	public static final int STEELER_BLOCK = 9;
	public static final int STEELEST_BLOCK = 10;
	public static final int HEALTH_UP = 11;
	public static final int SPEED_UP = 12;
	public static final int DAMAGE_UP = 13;


	//blocknames
	public static final String GROUND = "ground";
	public static final String CORNER = "corner";
	public static final String HORIZONTAL = "horizontal";
	public static final String HORIZONTAL_LEFT = "horizontal-left";
	public static final String HORIZONTAL_RIGHT = "horizontal-right";
	public static final String VERTICAL = "vertical";
	public static final String VERTICAL_UP = "vertical-up";
	public static final String VERTICAL_DOWN = "vertical-down";
	public static final String STEELEST = "steelest";
	public static final String STEELER = "steeler";
	public static final String STEEL = "steel";
	public static final String HEALTH = "health-up";
	public static final String DAMAGE = "damage-up";
	public static final String SPEED = "speed-up";



	// map unit size
	public static final int MAP_WIDTH = 25;
	public static final int MAP_HEIGHT = 20;

	//collision
	public static final boolean HAS_COLLIDED = true;
	public static final boolean NOT_COLLIDED = false;


	// sprite speed

	public static final int NORMAL_SPEED = 5;
	public static final int FAST_SPEED = 10;
	public static final int FASTER_SPEED = 13;
	public static final int FASTEST_SPEED = 15;
	//dead
	public static final boolean IS_DEAD = true;
	public static final boolean NOT_DEAD = false;

	//bullet
	public static final int BULLET_SIZE = 10;
	public static final int BIG_BULLET_SIZE = 25;

	//sprite init attrs
	public static final int INIT_LIFE = 3;
	public static final int INIT_HEALTH = 100;

}