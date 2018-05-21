package org.teemo;


import javafx.scene.media.AudioClip;

public class PentakillSound {

    private static PentakillSound sound;

    private AudioClip audioClip;
    private AudioClip audioClipB;
    private AudioClip audioClipEnglish;

    private PentakillSound() {
        try {
            audioClip = new AudioClip(PentakillSound.class.getResource("/org/teemo/media/pentakill.wav").toExternalForm());
            audioClipB = new AudioClip(PentakillSound.class.getResource("/org/teemo/media/pentakillLatino.wav").toExternalForm());

            audioClipEnglish = new AudioClip(PentakillSound.class.getResource("/org/teemo/media/pentakillEnglish.wav").toExternalForm());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void playClip(boolean english) {
        if(english) {
            audioClipEnglish.play();
        }else{
            if (System.currentTimeMillis() % 2 == 0) {
                audioClip.play();
            }else{
                audioClipB.play();
            }
        }
    }

    public static void play(boolean english) {
        if (sound == null) {
            sound = new PentakillSound();
        }

        sound.playClip(english);
    }


}
