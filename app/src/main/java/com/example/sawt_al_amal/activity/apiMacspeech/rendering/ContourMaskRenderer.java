package com.example.sawt_al_amal.activity.apiMacspeech.rendering;

import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import com.example.sawt_al_amal.activity.apiMacspeech.imaging.IFrame;

public class ContourMaskRenderer implements IRenderer {

    private Scalar contourColour;

    public ContourMaskRenderer() {
        setDefaultColour();
    }

    @Override
    public void display(IFrame inputFrame) {

        Imgproc.drawContours(inputFrame.getRGBA(),
                inputFrame.getContours(),
                0,
                contourColour,
                -1);

    }

    private void setDefaultColour(){
        contourColour = new Scalar(0,255,0,255);
    }

}
