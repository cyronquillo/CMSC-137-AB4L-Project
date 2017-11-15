import java.util.HashMap;
import java.io.File;                                //import statements
import java.awt.Image;
import javax.imageio.ImageIO;

public class GFXLibrary{

	private static HashMap<String, Image> gfxList = new HashMap<String, Image>();


	public GFXLibrary(){
		try{

			//blue sprite
			gfxList.put("blueDown", ImageIO.read(new File("gfx/blue-down.png")));
			gfxList.put("blueLeft", ImageIO.read(new File("gfx/blue-left.png")));
			gfxList.put("blueRight", ImageIO.read(new File("gfx/blue-right.png")));

			//orange sprite
			gfxList.put("orangeDown", ImageIO.read(new File("gfx/orange-down.png")));
			gfxList.put("orangeLeft", ImageIO.read(new File("gfx/orange-left.png")));
			gfxList.put("orangeRight", ImageIO.read(new File("gfx/orange-right.png")));

			//pink sprite
			gfxList.put("pinkDown", ImageIO.read(new File("gfx/pink-down.png")));
			gfxList.put("pinkLeft", ImageIO.read(new File("gfx/pink-left.png")));
			gfxList.put("pinkRight", ImageIO.read(new File("gfx/pink-right.png")));

			//red sprite
			gfxList.put("redDown", ImageIO.read(new File("gfx/red-down.png")));
			gfxList.put("redLeft", ImageIO.read(new File("gfx/red-left.png")));
			gfxList.put("redRight", ImageIO.read(new File("gfx/red-right.png")));

			//dead sprite
			gfxList.put("SpriteRIP", ImageIO.read(new File("gfx/dead.png")));

		} catch(Exception e){}
	}

	public Image returnImage(String pseudo){
		return this.gfxList.get(pseudo);
	}
}