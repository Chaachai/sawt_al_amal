package com.example.sawt_al_amal.activity.camSpeech.rendering;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import com.example.sawt_al_amal.activity.camSpeech.imaging.IFrame;

public class FeatureRenderer implements IRenderer {

    private Scalar featureColour;

    public FeatureRenderer() {
        setDefaultColour();
    }

    @Override
    public void display(IFrame inputFrame) {

        for (MatOfPoint pointMat: inputFrame.getFeatures()){
            for (Point point: pointMat.toList()){
                Imgproc.circle(inputFrame.getRGBA(), point, 5, featureColour, 2);
            }
        }

    }

    private void setDefaultColour(){
        featureColour = new Scalar(255,0,0,255);
    }


}
