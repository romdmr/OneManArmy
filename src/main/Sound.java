package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30];
    FloatControl fc;
    int volumeScale = 3;
    float volume;

    public Sound(){
        soundURL[0] = getClass().getResource("/sound/zombie_music.wav");
        soundURL[1] = getClass().getResource("/sound/drink_bottle.wav");
        soundURL[2] = getClass().getResource("/sound/basicShot.wav");
        soundURL[3] = getClass().getResource("/sound/sam_lullaby.wav");
        soundURL[4] = getClass().getResource("/sound/fatZombieDeath.wav");
        soundURL[5] = getClass().getResource("/sound/basicZombieDeath.wav");
        soundURL[6] = getClass().getResource("/sound/swing.wav");
        soundURL[7] = getClass().getResource("/sound/hitSound.wav");
        soundURL[8] = getClass().getResource("/sound/pukeZombieDeath.wav");
        soundURL[9] = getClass().getResource("/sound/damagePlayer.wav");
        soundURL[10] = getClass().getResource("/sound/reloadSound.wav");
        soundURL[11] = getClass().getResource("/sound/instakill.wav");
        soundURL[12] = getClass().getResource("/sound/MaxAmmo.wav");
        soundURL[13] = getClass().getResource("/sound/TimeStop.wav");
        soundURL[14] = getClass().getResource("/sound/Kaboom.wav");
        soundURL[15] = getClass().getResource("/sound/roundChange.wav");
        soundURL[16] = getClass().getResource("/sound/samanthas-laugh.wav");
        soundURL[17] = getClass().getResource("/sound/reloadShort.wav");
        soundURL[18] = getClass().getResource("/sound/DoublePoints.wav");
        //soundURL[17] = getClass().getResource("/sound/evilLaugh.wav");
    }

    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void play(){
        clip.start();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }
    public void checkVolume(){
        switch(volumeScale){
            case 0: volume = -80f;
            break;
            case 1: volume =-20f;
            break;
            case 2: volume =-12f;
            break;
            case 3: volume =-5f;
            break;
            case 4: volume = 1f;
            break;
            case 5: volume = 6f;
            break;
        }
        fc.setValue(volume);
    }
}
