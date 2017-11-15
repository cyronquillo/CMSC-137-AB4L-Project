import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class TileMap{
	private int x;
	private int y;

	private int tileSize;
	private int[][] map;
	private int numFloors;
	private int numBricks;

	public static final int MAP_HEIGHT = 15;
	public static final int MAP_WIDTH = 20;

	public TileMap(String s){
		numFloors = 0;
		numBricks = 0;
		try{

			BufferedReader br = new BufferedReader(new FileReader(s));		// reads the map from the .txt file using BufferedReader

			this.map = new int[MAP_HEIGHT][MAP_WIDTH];

			String delimiters = " ";
			for(int row = 0; row< MAP_HEIGHT; row++){
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
	public int getNumFloors(){
		return this.numFloors;
	}
	public int getNumBricks(){
		return this.numBricks;
	}

	public Block[][] getBlockSet(){				// gets the current blocks
		numFloors = 0;
		for(int i = 0; i < MAP_HEIGHT; i++){
			for(int j = 0; j < MAP_WIDTH; j++){
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
	
		Block[][] blockSet = new Block[MAP_HEIGHT][MAP_WIDTH];
		Block block = null;
		for(int i = 0; i < MAP_HEIGHT; i++){
			int a = i * Block.BLOCK_SIZE;
			for(int j = 0; j < MAP_WIDTH; j++){
				int b = j * Block.BLOCK_SIZE;
				if(map[i][j] == Block.STEEL){
					block = new Steel(b ,a, false, Block.STEEL);
				}
				if(map[i][j] == Block.GRASS){
					block = new Grass(b ,a,true,Block.GRASS);
				}
				if(map[i][j] == Block.FLOOR){
					block = new Floor(b ,a ,true,Block.FLOOR);
					numFloors += 1;
				}
				if(map[i][j] == Block.BRICK){
					block = new Brick(b ,a,false,Block.BRICK);
					numBricks += 1;
				}
				if(map[i][j] == Block.BORDER){
					block = new Border(b ,a ,false,Block.BORDER);
				}
				blockSet[i][j] = block;
			}
		}
		//System.out.println("Floors: "+numFloors);
		return blockSet;
	}
}
