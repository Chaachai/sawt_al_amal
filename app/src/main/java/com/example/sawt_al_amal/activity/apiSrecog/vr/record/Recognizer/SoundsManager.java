package com.example.sawt_al_amal.activity.apiSrecog.vr.record.Recognizer;

import android.util.Log;
import com.example.sawt_al_amal.activity.apiSrecog.savarese.spatial.GenericPoint;
import com.example.sawt_al_amal.activity.apiSrecog.savarese.spatial.KDTree;
import com.example.sawt_al_amal.activity.apiSrecog.savarese.spatial.NearestNeighbors;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class SoundsManager {

    private static final String TAG = "recordings_db";

    public static String backup;

    private NearestNeighbors<Double, GenericPoint<Double>, String>
            nn;

    private final int COUNT_OF_CLOSEST = 10;

    //private final int MIN_ACCEPTANCE_HITS=130; //16000/2
    private final int MIN_ACCEPTANCE_HITS = 65; //16000/4

    private KDTree<Double, GenericPoint<Double>, String>
            tree;

    public SoundsManager() {
        nn = new NearestNeighbors<Double, GenericPoint<Double>, String>();
        tree = new KDTree<Double, GenericPoint<Double>, String>();
    }
///Enregistrer audio
    public void addSound(byte[] soundSample, String soundDescriptor) {
        //Prepare MelVectors.
        double[] sample = Utils.convertRawByteArrayToDoubleArray(soundSample);
        List<GenericPoint<Double>> finalVectors = SoundProcessor.CalculateMelVectors(sample);

        HashSet<GenericPoint<Double>> points = new HashSet<GenericPoint<Double>>();
        for (int i = 0; i < points.size(); i++) {
            GenericPoint<Double> doubleGenericPoint = finalVectors.get(i);
            if (doubleGenericPoint != null && doubleGenericPoint.hashCode() != 0) {
                points.add(doubleGenericPoint);
            }
        }
        //Add vectors to KD-Tree.
        fillMap(tree, points, soundDescriptor);
    }

    public void addSound(short[] soundSample, String soundDescriptor) {
        //Prepare MelVectors.
        double[] sample = Utils.convertRawShortArrayToDoubleArray(soundSample);
        List<GenericPoint<Double>> finalVectors = SoundProcessor.CalculateMelVectors(sample);

        HashSet<GenericPoint<Double>> points = new HashSet<GenericPoint<Double>>();
        for (int i = 0; i < finalVectors.size(); i++) {
            GenericPoint<Double> doubleGenericPoint = finalVectors.get(i);
            if (doubleGenericPoint.getCoord(0) > 0.0f) {
                points.add(doubleGenericPoint);
            }
        }

        //Add vectors to KD-Tree.
        fillMap(tree, points, soundDescriptor);
    }

    private static void fillMap(Map map, Set points, String descriptor) {
        for (Object point : points) {
            map.put(point, descriptor);
        }
    }

    public String recognizeSound(byte[] soundSample) {
        //Prepare MelVectors.
        double[] sample = Utils.convertRawByteArrayToDoubleArray(soundSample);
        List<GenericPoint<Double>> finalVectors = SoundProcessor.CalculateMelVectors(sample);

        NearestNeighbors.Entry<Double, GenericPoint<Double>, String>[] n;
        for (int i = 0; i < finalVectors.size(); i++) {

            //if(finalVectors.get(i)!=null && finalVectors.get(i).hashCode()!=0) {
            n = nn.get(tree, finalVectors.get(i), COUNT_OF_CLOSEST, false);
            for (int j = 0; j < n.length; j++) {
                //HashMap<String,Integer> result = new HashMap<String,Integer>();
                String res = n[j].getNeighbor().getValue();
                //Log.e(j + "-recognized", res.toString());
            }
            //}
        }
        return "hololo";
    }

    private Map<String, Integer> counter = new HashMap<>();

//verifier si le bruit existe dans notre database
public String recognizeSound(short[] soundSample) {
        counter = new HashMap<>();
        //Prepare MelVectors.
        double[] sample = Utils.convertRawShortArrayToDoubleArray(soundSample);
        List<GenericPoint<Double>> finalVectors = SoundProcessor.CalculateMelVectors(sample);

        NearestNeighbors.Entry<Double, GenericPoint<Double>, String>[] n;
        for (int i = 0; i < finalVectors.size(); i++) {
            GenericPoint<Double> point = finalVectors.get(i);
            if (point.getCoord(0) > 0) {
                n = nn.get(tree, finalVectors.get(i), COUNT_OF_CLOSEST, false);
                for (int j = 0; j < n.length; j++) {
                    String res = n[j].getNeighbor().getValue();
                    if (counter.containsKey(res)) {
                        counter.put(res, counter.get(res) + 1);
                    } else {
                        counter.put(res, 1);
                    }
                }
            }
        }
        return ResultDecider();
    }

    public Map<String, Integer> getCounter() {
        return counter;
    }


    //recuperer la source du bruit
    private String ResultDecider() {
        if (counter == null) {
            return "error";
        }
        Set<Map.Entry<String, Integer>> entries = counter.entrySet();
        Map.Entry<String, Integer> highestResult = null;
        for (Map.Entry entry : entries) {
            if (highestResult == null) {
                highestResult = entry;
            } else {
                if ((int) highestResult.getValue() < (int) entry.getValue()) {
                    highestResult = entry;
                }
            }
        }
        Log.e("EpicResult", "key " + highestResult.getKey() + " has " + highestResult.getValue() + " hits!");

        if (highestResult.getValue() > MIN_ACCEPTANCE_HITS) {
            backup = highestResult.getKey();
            Log.d(TAG, "hhhhhhhhh " + backup);

            return highestResult.getKey() + " : " + Math.floor(100 * (highestResult.getValue() / MIN_ACCEPTANCE_HITS))
                    + "%.";
        } else {
            return "noise";
        }
    }
}
