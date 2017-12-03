package instantiation;
import java.util.HashMap;

public class SFXLibrary{
	private static HashMap<String, AudioSource> sfxList = new HashMap<String, AudioSource> ();

	public SFXLibrary(){
		try{	
			//werpa ups
			sfxList.put("HealthUp", new AudioSource("sfx/life.wav"));
			sfxList.put("SpeedUp", new AudioSource("sfx/flash.wav"));
			sfxList.put("DamageUp", new AudioSource("sfx/buff.wav"));
			sfxList.put("LifeUp", new AudioSource("sfx/lifeup.wav"));
		
			// missiles
			sfxList.put("MissileHit", new AudioSource("sfx/thump.wav"));
			sfxList.put("MissileShot", new AudioSource("sfx/missile.wav"));
			sfxList.put("BigMissileShot", new AudioSource("sfx/shotgun.wav"));
			
			// life
			sfxList.put("Resurrect", new AudioSource("sfx/resurrect.wav"));
		} catch (Exception e){}
	}

	public AudioSource returnAudio(String pseudo){
            return this.sfxList.get(pseudo);
    }
}