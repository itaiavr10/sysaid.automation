package com.core.utils;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.monte.media.*;
import org.monte.media.math.Rational;

import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

import org.monte.screenrecorder.ScreenRecorder;
import org.testng.ITestResult;
import org.testng.internal.TestResult;

import com.core.base.LogManager;

public class VideoRecorder {
	
	ScreenRecorder screenRecorder;
	
	private static VideoRecorder instance = null;
	
	public static VideoRecorder getInstance() {
		if (instance == null) {
			instance = new VideoRecorder();
		}
		return instance;
	}
	
	private VideoRecorder() {
		try {
			init();
		} catch (Exception e) {
			LogManager.warn("Cannot initiate video recorder! .Message: " + e);
		}
	}

	private void init() throws Exception{
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		screenRecorder = new ScreenRecorder(gc, new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
				new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,CompressorNameKey, 
						ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,Rational.valueOf(10), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
				new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),null);	
	}
	
	public void finishRecord(String videoName, boolean testPassed){
		stopRecording();
		if(!testPassed){
			saveVideo(videoName);
		}
	}
	
	private void saveVideo(String videoName){
		LogManager.debug("Saving video record file!");
		List<File> cachedVideos = screenRecorder.getCreatedMovieFiles();
		if(cachedVideos == null || cachedVideos.isEmpty()){
			LogManager.warn("No Videos to save");
		}
			
		File file = cachedVideos.get(0);
		String fileName =   SystemUtils.Files.getMediaPath() +  videoName +".avi";
		File newFile = new File(fileName);
		if(newFile.exists())
			newFile.delete();
		SystemUtils.Files.moveFile(file, newFile);
	}
	
	public void startRecording(){
		LogManager.debug("Starting Video record!");
		try {
			screenRecorder.start();
		} catch (IOException e) {
			LogManager.warn("Starting Video record - Error : " + e);
		}
	}

	private void stopRecording() {
		LogManager.debug("Stopping Video record!");
		try {
			screenRecorder.stop();
		} catch (IOException e) {
			LogManager.warn("Stopping Video record - Error : " + e);
		}
	}

}
