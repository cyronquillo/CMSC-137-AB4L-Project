import java.util.HashMap;
import java.io.File;                                //import statements
import java.awt.Image;
import javax.imageio.ImageIO;

public class ArtLibrary{                            //class definition

    private static HashMap<String, Image> imageList = new HashMap<String, Image>();
                                                    //hashmap to contain all images used in the program
    public ArtLibrary(){
        try{
            /************************************PLAYER SPRITES**************************************/

            imageList.put("RedMushroomF", ImageIO.read(new File("images/sprites/bcredf.png")));
            imageList.put("RedMushroomB", ImageIO.read(new File("images/sprites/bcredb.png")));
            imageList.put("RedMushroomL", ImageIO.read(new File("images/sprites/bcredl.png")));
            imageList.put("RedMushroomR", ImageIO.read(new File("images/sprites/bcredr.png")));
            imageList.put("RedBullet", ImageIO.read(new File("images/bullets/redmushroom.png")));
            imageList.put("GreenBullet", ImageIO.read(new File("images/bullets/greenmushroom.png")));
            imageList.put("RedBulletP", ImageIO.read(new File("images/bullets/redfire.png")));
            imageList.put("GreenBulletP", ImageIO.read(new File("images/bullets/greenfire.png")));
            imageList.put("GreenMushroomF", ImageIO.read(new File("images/sprites/bcgreenf.png")));
            imageList.put("GreenMushroomB", ImageIO.read(new File("images/sprites/bcgreenb.png")));
            imageList.put("GreenMushroomL", ImageIO.read(new File("images/sprites/bcgreenl.png")));
            imageList.put("GreenMushroomR", ImageIO.read(new File("images/sprites/bcgreenr.png")));

            /****************************************************************************************/



            /**************************************ENVIRONMENT***************************************/

            imageList.put("Brick", ImageIO.read(new File("images/blocks/bcbrickclean.png")));
            imageList.put("Brick1", ImageIO.read(new File("images/blocks/bcbrickstate1.png")));
            imageList.put("Brick2", ImageIO.read(new File("images/blocks/bcbrickstate2.png")));
            imageList.put("Steel", ImageIO.read(new File("images/blocks/bcmetalclean.png")));
            imageList.put("Steel1", ImageIO.read(new File("images/blocks/bcmetalstate1.png")));
            imageList.put("Steel2", ImageIO.read(new File("images/blocks/bcmetalstate2.png")));
            imageList.put("Wall", ImageIO.read(new File("images/blocks/bcflower.png")));
            imageList.put("Floor", ImageIO.read(new File("images/blocks/bcground.png")));
            imageList.put("Grass", ImageIO.read(new File("images/blocks/bcgrass.png")));

            /****************************************************************************************/



            /****************************************POWERUPS****************************************/


            imageList.put("HeartPower", ImageIO.read(new File("images/powerups/bcheartpup.png")));
            imageList.put("StarPower", ImageIO.read(new File("images/powerups/bcstarpup.png")));
            imageList.put("BoltPower", ImageIO.read(new File("images/powerups/bcflashpup.png")));
            imageList.put("HeartPowerBW", ImageIO.read(new File("images/powerups/bcheartpupbw.png")));
            imageList.put("StarPowerBW", ImageIO.read(new File("images/powerups/bcstarpupbw.png")));
            imageList.put("BoltPowerBW", ImageIO.read(new File("images/powerups/bcflashpupbw.png")));

            /****************************************************************************************/




            /*****************************************OTHER******************************************/

            imageList.put("Coin", ImageIO.read(new File("images/icon/coin.png")));
            imageList.put("BG", ImageIO.read(new File("images/layout/testbg.png")));
            imageList.put("BG2", ImageIO.read(new File("images/layout/testbg2.png")));
            imageList.put("Background", ImageIO.read(new File("images/layout/startbg.png")));
            imageList.put("Pause",ImageIO.read(new File("images/layout/paused.png")));

            /****************************************************************************************/
        }
        catch(Exception e){}
    }

    public Image returnImage(String className){
        return this.imageList.get(className);       //gets an image in the hashmap with a given string used as the key
    }
}
