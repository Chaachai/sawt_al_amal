package com.example.sawt_al_amal.activity.camSpeech.rendering;

import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import com.example.sawt_al_amal.activity.camSpeech.imaging.IFrame;

public class CannyEdgesRenderer implements IRenderer {

    private Scalar edgesColour;

    public CannyEdgesRenderer() {
        setDefaultColour();
    }

    @Override
    public void display(IFrame inputFrame) {

        // display edges
        Imgproc.drawContours(inputFrame.getRGBA(),
                inputFrame.getCannyEdges(),
                -1,
                edgesColour,
                1);

    }

    private void setDefaultColour(){
        edgesColour = new Scalar(0,0,255,255);
    }

}
