package instantiation;
public class ClientMissile{
	public int x;
	public int y;
	public int size;
	public String src;
	public boolean is_collided;
	public ClientMissile(String src, int x, int y, int size, boolean is_collided){
		this.src = src;
		this.x = x;
		this.y = y;
		this.is_collided = is_collided;
		this.size = size;
	}
}