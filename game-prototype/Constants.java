
public interface Constants {
	public static final String APP_NAME="Ghost Wars";
	
	//states
	public static final int GAME_START=0;
	public static final int IN_PROGRESS=1;
	public final int GAME_END=2;
	public final int WAITING_FOR_PLAYERS=3;
	
	//port
	public static final int PORT=4444;

	//premade image library
	public static final GFXLibrary gfx = new GFXLibrary();

	//frame
	public static final int FRAME_WIDTH = 640;
	public static final int FRAME_HEIGHT = 480;
}
