package com.example.sawt_al_amal.activity.camSpeech.processing.preprocessing;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;

import com.example.sawt_al_amal.activity.camSpeech.imaging.IFrame;

public interface IFramePreProcessor {

    IFrame preProcess(CvCameraViewFrame inputFrame);

}
