package instantiation;
import java.io.File;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;                                            //import statements
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class AudioSource extends Object{

    private Clip clip;                                                                    //adds a clip as attribute to play audio

    public AudioSource(String s) {
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(s));
            clip = AudioSystem.getClip();                                 //loads the audio in the AudioSource constructor
            clip.open(audioIn);
	    } catch (Exception e) {
            System.out.println(e.getMessage());
		}
	}

    public void play(boolean loop){
        if(clip == null){
            System.out.println("null");
            return;
        }
        this.stop();
        clip.setFramePosition(0);                                                 //plays the audio. stops the currently playing audio if ever
        clip.start();
        if(loop == true) clip.loop(Clip.LOOP_CONTINUOUSLY);     //if the loop variable in the parameter is true, it will loop continuously
    }

    public void stop(){
        if(clip.isRunning()) clip.stop();                         //stops a running clip
    }

    public void close(){
        this.stop();                                                                    //closes the clip
        clip.close();
    }

}
