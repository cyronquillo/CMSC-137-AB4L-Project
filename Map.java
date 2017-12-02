package instantiation;
import java.util.Random;												//import statement

public class Map{

	private TileMap tm;

	public Map(){
		tm = new TileMap(randomMap());							//has a tileMap attribute which displays the map generated from the randomMap method
	}

	public static String randomMap(){							//returns a random integer from 1 to 6
		String map = "";
		Random rand = new Random();
		int generated = rand.nextInt(6)+1;

		switch(generated){
			case 1:
				map = "map/terrain1.txt";
				break;
			case 2:
				map = "map/terrain2.txt";
				break;
			case 3:
				map = "map/terrain3.txt";
				break;
			case 4:
				map = "map/terrain4.txt";
				break;
			case 5:
				map = "map/terrain5.txt";
				break;
			case 6:
				map = "map/terrain6.txt";
				break;
		}

		return map;										//returns a String based from which generated int from the Random Generators
	}

	public TileMap getTileMap(){
		return tm;
	}


}
