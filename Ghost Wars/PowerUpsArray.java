import java.util.ArrayList;

public class PowerUpsArray{
	public static ArrayList<PowerUps> pUps = new ArrayList<PowerUps>();
	public PowerUpsArray(TileMap tm, Block[][] blocks){
		for(int i = 0; i < 5; i++){									//adds the powerups to the array. used to limit the generation of powerups
			pUps.add(new Damage(tm,blocks));
			pUps.add(new Speed(tm,blocks));
			pUps.add(new Life(tm,blocks));
		}
	}



}
