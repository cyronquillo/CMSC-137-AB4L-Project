package instantiation;
import java.util.HashMap;
import java.io.File;                                //import statements
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class GFXLibrary{

	public static HashMap<String, Image> gfxList = new HashMap<String, Image>();

	public GFXLibrary(){
		try{

			//blue sprite
			gfxList.put("blueDown", ImageIO.read(new File("gfx/sprites/blue-down.png")));
			gfxList.put("blueLeft", ImageIO.read(new File("gfx/sprites/blue-left.png")));
			gfxList.put("blueRight", ImageIO.read(new File("gfx/sprites/blue-right.png")));
			gfxList.put("blueUp", ImageIO.read(new File("gfx/sprites/blue-up.png")));

			//orange sprite
			gfxList.put("orangeDown", ImageIO.read(new File("gfx/sprites/orange-down.png")));
			gfxList.put("orangeLeft", ImageIO.read(new File("gfx/sprites/orange-left.png")));
			gfxList.put("orangeRight", ImageIO.read(new File("gfx/sprites/orange-right.png")));
			gfxList.put("orangeUp", ImageIO.read(new File("gfx/sprites/orange-up.png")));

			//pink sprite
			gfxList.put("pinkDown", ImageIO.read(new File("gfx/sprites/pink-down.png")));
			gfxList.put("pinkLeft", ImageIO.read(new File("gfx/sprites/pink-left.png")));
			gfxList.put("pinkRight", ImageIO.read(new File("gfx/sprites/pink-right.png")));
			gfxList.put("pinkUp", ImageIO.read(new File("gfx/sprites/pink-up.png")));

			//red sprite
			gfxList.put("redDown", ImageIO.read(new File("gfx/sprites/red-down.png")));
			gfxList.put("redLeft", ImageIO.read(new File("gfx/sprites/red-left.png")));
			gfxList.put("redRight", ImageIO.read(new File("gfx/sprites/red-right.png")));
			gfxList.put("redUp", ImageIO.read(new File("gfx/sprites/red-up.png")));

			//dead sprite
			gfxList.put("SpriteRIP", ImageIO.read(new File("gfx/sprites/dead.png")));


			//blocks
			gfxList.put("ground", ImageIO.read(new File("gfx/blocks/bcground.png")));			
			gfxList.put("corner", ImageIO.read(new File("gfx/blocks/corner.png")));			
			gfxList.put("steel", ImageIO.read(new File("gfx/blocks/steel.png")));			
			gfxList.put("steeler", ImageIO.read(new File("gfx/blocks/steeler.png")));			
			gfxList.put("steelest", ImageIO.read(new File("gfx/blocks/steelest.png")));			
			gfxList.put("horizontal", ImageIO.read(new File("gfx/blocks/horizontal.png")));
			gfxList.put("horizontal-left", ImageIO.read(new File("gfx/blocks/horizontal-left.png")));
			gfxList.put("horizontal-right", ImageIO.read(new File("gfx/blocks/horizontal-right.png")));			
			gfxList.put("vertical", ImageIO.read(new File("gfx/blocks/vertical.png")));
			gfxList.put("vertical-up", ImageIO.read(new File("gfx/blocks/vertical-up.png")));
			gfxList.put("vertical-down", ImageIO.read(new File("gfx/blocks/vertical-down.png")));			
			
			//werpa-ups
			gfxList.put("health-up", ImageIO.read(new File("gfx/werpa/health-up.png")));
			gfxList.put("damage-up", ImageIO.read(new File("gfx/werpa/damage-up.png")));
			gfxList.put("speed-up", ImageIO.read(new File("gfx/werpa/speed-up.png")));


			// screen texts
			gfxList.put("pause", ImageIO.read(new File("gfx/screen/paused.png")));
			gfxList.put("waiting", ImageIO.read(new File("gfx/screen/waiting.png")));
			gfxList.put("you-win", ImageIO.read(new File("gfx/screen/youwin.png")));
			gfxList.put("you-lose", ImageIO.read(new File("gfx/screen/youlose.png")));
			gfxList.put("view-results", ImageIO.read(new File("gfx/screen/viewresults.png")));


			// menu
			gfxList.put("logo", ImageIO.read(new File("gfx/layout/logo.gif")));
			gfxList.put("background", ImageIO.read(new File("gfx/layout/background.jpg")));
			gfxList.put("exit", ImageIO.read(new File("gfx/layout/exit.jpg")));
			gfxList.put("exit-hover", ImageIO.read(new File("gfx/layout/exit-hover.jpg")));
			gfxList.put("howto", ImageIO.read(new File("gfx/layout/howto.jpg")));
			gfxList.put("how-to-hover", ImageIO.read(new File("gfx/layout/howto_hover.jpg")));
			gfxList.put("play", ImageIO.read(new File("gfx/layout/play.jpg")));
			gfxList.put("play-hover", ImageIO.read(new File("gfx/layout/play_hover.jpg")));
			gfxList.put("instructions", ImageIO.read(new File("gfx/layout/howtopanel.jpg")));

			// chatBG
			gfxList.put("blueBG", ImageIO.read(new File("gfx/panels/chat_stat_panel_blue.jpg")));
			gfxList.put("redBG", ImageIO.read(new File("gfx/panels/chat_stat_panel_red.jpg")));
			gfxList.put("pinkBG", ImageIO.read(new File("gfx/panels/chat_stat_panel_pink.jpg")));
			gfxList.put("orangeBG", ImageIO.read(new File("gfx/panels/chat_stat_panel_orange.jpg")));

		} catch(Exception e){}
	}

	public Image returnImage(String pseudo){
		return this.gfxList.get(pseudo);
	}

}