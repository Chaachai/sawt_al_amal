package com.example.sawt_al_amal.activity.camSpeech.processing.preprocessing;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;

import com.example.sawt_al_amal.activity.camSpeech.imaging.Frame;
import com.example.sawt_al_amal.activity.camSpeech.imaging.IFrame;
import com.example.sawt_al_amal.activity.camSpeech.processing.IFrameProcessor;

public class CameraFrameAdapter implements IFramePreProcessor {

    private IFrameProcessor downSampler, resizer;
    private IFrame outputFrame;

    public CameraFrameAdapter(IFrameProcessor downSamplingFrameProcessor,
                              IFrameProcessor resizingFrameProcessor) {
        downSampler = downSamplingFrameProcessor;
        resizer = resizingFrameProcessor;
    }

    @Override
    public IFrame preProcess(CvCameraViewFrame inputFrame) {

        // Create the pre-processed output frame
        outputFrame = new Frame(inputFrame.rgba());

//         Resize image
        outputFrame = resizer.process(outputFrame);

        // Set the original size of the frame
        outputFrame.setOriginalSize(inputFrame.rgba().size());

        // Resize image - currently unused
//        outputFrame = resizer.process(outputFrame);

        // Further downsampling for efficiency
        outputFrame = downSampler.process(outputFrame);

        return outputFrame;
    }

}
