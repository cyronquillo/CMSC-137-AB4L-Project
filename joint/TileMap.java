package instantiation;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class TileMap{
	private int x;
	private int y;

	private int tileSize;
	private int[][] map;

	public static final int MAP_HEIGHT = 15;
	public static final int MAP_WIDTH = 20;

	public TileMap(String s){
		try{

			BufferedReader br = new BufferedReader(new FileReader(s));		// reads the map from the .txt file using BufferedReader

			this.map = new int[MAP_HEIGHT][MAP_WIDTH];

			String delimiters = " ";
			for(int row = 0; row < MAP_HEIGHT; row++){
				String line = br.readLine();
				String[] tokens = line.split(delimiters);
				for(int col = 0; col < MAP_WIDTH; col++){
					map[row][col] = Integer.parseInt(tokens[col]);
					System.out.print(map[row][col] + " ");
				}
				System.out.println();
			}

		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}


	public int[][] getMap(){
		return this.map;
	}
}