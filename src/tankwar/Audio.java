package tankwar;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

/**
 * 坦克大战的声音类
 * @author chenruiying
 *
 */
public class Audio extends Thread{
	/** 文件名*/
	private String filename;
	/**
	 * 声音类主方法
	 * @param wavfile 声音文件
	 */
	public Audio(String wavfile) {
		filename = wavfile;
	}
	/*
	 * 播放声音的方法
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		File soundFile = new File(filename);
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		AudioFormat format = audioInputStream.getFormat();
		SourceDataLine auline = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		try {
			auline = (SourceDataLine) AudioSystem.getLine(info);
			auline.open(format);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		auline.start();
			
		int nBytesRead = 0;
		byte[] abDate = new byte[512];
		try {
			while(nBytesRead != -1) {
				nBytesRead = audioInputStream.read(abDate, 0, abDate.length);
				if(nBytesRead >= 0) 
					auline.write(abDate, 0, nBytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return;
		} finally {
			auline.drain();
			auline.close();
		}
	}

}
