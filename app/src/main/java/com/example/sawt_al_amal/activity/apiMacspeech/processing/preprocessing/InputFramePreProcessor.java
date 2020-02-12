package com.example.sawt_al_amal.activity.apiMacspeech.processing.preprocessing;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;

import com.example.sawt_al_amal.activity.apiMacspeech.imaging.IFrame;

public class InputFramePreProcessor implements IFramePreProcessor {

    private IFramePreProcessor frameAdapter;
    private IFrame outputFrame;

    public InputFramePreProcessor(IFramePreProcessor inputFrameAdapter) {
        frameAdapter = inputFrameAdapter;
    }

    @Override
    public IFrame preProcess(CvCameraViewFrame inputFrame) {

        outputFrame = frameAdapter.preProcess(inputFrame);

        return outputFrame;

    }

    public void setFrameAdapter(IFramePreProcessor frameAdapter) {
        this.frameAdapter = frameAdapter;
    }

}
