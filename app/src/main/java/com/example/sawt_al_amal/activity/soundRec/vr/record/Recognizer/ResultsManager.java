package com.example.sawt_al_amal.activity.soundRec.vr.record.Recognizer;


import android.app.ProgressDialog;
import android.os.Vibrator;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.sawt_al_amal.activity.soundRec.vr.record.list.RecordingsDatabase;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ResultsManager {

    private static final String TAG = "recordings_db";


    private static Vibrator vibratorRef;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm:ss");

    private static ProgressDialog progressBarRef;

    private static TextView textViewRef;

    public static void setTextViewReference(TextView textViewReference) {
        textViewRef = textViewReference;
    }

    private static ImageView img;

    public static void setImage(ImageView image) {
        img = image;
    }

    //afficher le resultat dans l'interface
    public static void setRecognizedSound(String sound) {
        Log.d(TAG, "hhhhhhhhh " + sound);

        if (textViewRef == null) {
            return;
        }
        textViewRef.setText(sound + " : " + getFormattedDate());
        img.setImageBitmap(RecordingsDatabase.findImage(SoundsManager.backup));
        if (vibratorRef != null) {
            vibratorRef.vibrate(100);
        }
    }

    private static String getFormattedDate() {
        return dateFormat.format(new Date());
    }

    public static void setVibrator(Vibrator vibrator) {
        vibratorRef = vibrator;
    }

    public static void setProgressBar(ProgressDialog progressBar) {
        progressBarRef = progressBar;
    }

    public static void activateProgressbar() {
        if (progressBarRef != null) {
            progressBarRef.show();
        }
    }

    public static void deactivateProgressbar() {
        if (progressBarRef != null) {
            progressBarRef.dismiss();
        }
    }
}
