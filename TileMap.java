package instantiation;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class TileMap implements Constants{
	private int x;
	private int y;

	private int tileSize;
	private int[][] map;

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
				}
			}
		} catch(Exception e){
			System.out.println(e.getMessage());
		}
	}


	public int[][] getMap(){
		return this.map;
	}

	public String toString(){
		String retval = "";
		for(int i = 0; i < MAP_HEIGHT; i++){
			for(int j = 0; j < MAP_WIDTH; j++){
				retval += map[i][j] + " ";
			}
			if(i != MAP_HEIGHT-1){
				retval += "\n";
			}
		}
		return retval;
	}
}