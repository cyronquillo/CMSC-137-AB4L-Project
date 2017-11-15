import java.util.HashMap;                         //import statements

public class AudioLibrary{

    private static HashMap<String, AudioSource> soundList = new HashMap<String, AudioSource>();
                                                  //hashmap to contain all music and sfx to use in the program
    public AudioLibrary(){
        try{
          soundList.put("Intro", new AudioSource("sfx/oso.wav"));
          soundList.put("Start", new AudioSource("sfx/start.wav"));
          soundList.put("Paused", new AudioSource("sfx/paused.wav"));
          soundList.put("HealthUp", new AudioSource("sfx/life.wav"));
          soundList.put("SpeedUp", new AudioSource("sfx/flash.wav"));     //all music
          soundList.put("DamageUp", new AudioSource("sfx/buff.wav"));
          soundList.put("Victory", new AudioSource("sfx/victory.wav"));
          soundList.put("WallResponse", new AudioSource("sfx/wallCollision.wav"));
          soundList.put("NormalShoot", new AudioSource("sfx/normalShoot.wav"));
          soundList.put("BuffedShoot", new AudioSource("sfx/fireball.wav"));
          soundList.put("MissileResponse", new AudioSource("sfx/spriteCollision.wav"));

        }
        catch(Exception e){}
    }

    public AudioSource returnAudio(String className){
        return this.soundList.get(className);         //gets a specific AudioSource instance using the given string as key
    }
}
