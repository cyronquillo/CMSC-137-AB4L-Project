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

		} catch(Exception e){}
	}

	public Image returnImage(String pseudo){
		return this.gfxList.get(pseudo);
	}
}