package com.example.sawt_al_amal.activity.apiMacspeech.processing.postprocessing;

import com.example.sawt_al_amal.activity.apiMacspeech.imaging.IFrame;
import com.example.sawt_al_amal.activity.apiMacspeech.processing.IFrameProcessor;

public class OutputFramePostProcessor implements IFramePostProcessor {

    private IFramePostProcessor upScalingFramePostProcessor;
    private IFrameProcessor resizer;
    private IFrame outputFrame;

    public OutputFramePostProcessor(IFramePostProcessor upScalingFramePostProcessor,
                                    IFrameProcessor resizingFrameProcessor) {
        this.upScalingFramePostProcessor = upScalingFramePostProcessor;
        this.resizer = resizingFrameProcessor;
    }

    @Override
    public IFrame postProcess(IFrame inputFrame) {

        // Currently Unused
//        outputFrame = resizer.process(inputFrame);

//        outputFrame = upScalingFramePostProcessor.postProcess(outputFrame);

        return inputFrame;

    }

}
