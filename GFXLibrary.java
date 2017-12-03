package instantiation;
import java.util.HashMap;
import java.io.File;                                //import statements
import java.awt.Image;
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


			// pause
			gfxList.put("pause", ImageIO.read(new File("gfx/screen/paused.png")));

		} catch(Exception e){}
	}

	public Image returnImage(String pseudo){
		return this.gfxList.get(pseudo);
	}
}