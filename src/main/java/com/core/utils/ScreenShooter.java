package com.core.utils;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.core.base.LogManager;
import com.core.utils.SystemUtils.Files;

public class ScreenShooter {
	
	
	private static void main(String[] args) throws AWTException, Exception {
		Thread.sleep(5000);
		OutputStream out = null;
		String path = Files.getMediaPath() + "\\itai.jpg";
		byte[] bytes = regularScreenShot();
		try {
		    out = new BufferedOutputStream(new FileOutputStream(path));
		    out.write(bytes);
		} finally {
		    if (out != null) out.close();
		}
		System.out.println("DONE");
	}
	
	public static void capture(String imageName){
		try {
			OutputStream out = null;
			String path = Files.getMediaPath() + imageName + ".jpg";
			byte[] bytes = regularScreenShot();
			try {
			    out = new BufferedOutputStream(new FileOutputStream(path));
			    out.write(bytes);
			} finally {
			    if (out != null) out.close();
			}
		} catch (Exception e) {
			LogManager.error("Failed to take a screen shot : " + e.getMessage());
		} 
	}

	private static byte[] regularScreenShot() throws AWTException, IOException {
		BufferedImage capture = captureScreen();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(capture, "jpg", baos);
		baos.flush();
		capture.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}

	private static BufferedImage captureScreen() throws AWTException {
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		Robot robotic = new Robot();
		BufferedImage capture = robotic.createScreenCapture(screenRect);
		robotic.waitForIdle();
		return capture;
	}

}
