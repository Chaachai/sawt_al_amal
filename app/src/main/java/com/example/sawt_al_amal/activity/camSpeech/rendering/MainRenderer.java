package com.example.sawt_al_amal.activity.camSpeech.rendering;

import java.util.ArrayList;

import com.example.sawt_al_amal.activity.camSpeech.imaging.IFrame;
import com.example.sawt_al_amal.activity.camSpeech.processing.DetectionMethod;

public class MainRenderer implements IRenderer {

    private ArrayList<IRenderer> renderers = new ArrayList<>();

    public MainRenderer(DetectionMethod method) {
        setRenderers(method);
    }

    public void display(IFrame inputFrame) {

        for (IRenderer renderer : renderers){
            renderer.display(inputFrame);
        }

    }

    private void setRenderers(DetectionMethod method) {
        switch (method){
            case CANNY_EDGES:
                renderers.add(new CannyEdgesRenderer());
                break;
            case CONTOUR_MASK:
                renderers.add(new ContourMaskRenderer());
                break;
            case SKELETON:
                renderers.add(new SkeletonRenderer());
                break;
            default:
                break;
        }

    }

}
