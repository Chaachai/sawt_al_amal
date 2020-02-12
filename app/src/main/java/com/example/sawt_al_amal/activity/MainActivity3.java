package com.example.sawt_al_amal.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.example.sawt_al_amal.R;
import com.example.sawt_al_amal.activity.camSpeech.imaging.IFrame;
import com.example.sawt_al_amal.activity.camSpeech.processing.DetectionMethod;
import com.example.sawt_al_amal.activity.camSpeech.processing.DownSamplingFrameProcessor;
import com.example.sawt_al_amal.activity.camSpeech.processing.IFrameProcessor;
import com.example.sawt_al_amal.activity.camSpeech.processing.MainFrameProcessor;
import com.example.sawt_al_amal.activity.camSpeech.processing.ResizingFrameProcessor;
import com.example.sawt_al_amal.activity.camSpeech.processing.SizeOperation;
import com.example.sawt_al_amal.activity.camSpeech.processing.postprocessing.IFramePostProcessor;
import com.example.sawt_al_amal.activity.camSpeech.processing.postprocessing.OutputFramePostProcessor;
import com.example.sawt_al_amal.activity.camSpeech.processing.postprocessing.UpScalingFramePostProcessor;
import com.example.sawt_al_amal.activity.camSpeech.processing.preprocessing.CameraFrameAdapter;
import com.example.sawt_al_amal.activity.camSpeech.processing.preprocessing.IFramePreProcessor;
import com.example.sawt_al_amal.activity.camSpeech.processing.preprocessing.InputFramePreProcessor;
import com.example.sawt_al_amal.activity.camSpeech.rendering.IRenderer;
import com.example.sawt_al_amal.activity.camSpeech.rendering.MainRenderer;
import com.example.sawt_al_amal.activity.camSpeech.svm.FrameClassifier;
//CAMERA
public class MainActivity3 extends Activity implements CvCameraViewListener2 {

    private CameraBridgeViewBase mOpenCvCameraView;

    private IFramePreProcessor preProcessor;
    private IFramePostProcessor postProcessor;
    private IFrame preProcessedFrame, processedFrame, postProcessedFrame, classifiedFrame;

    private IFrameProcessor mainFrameProcessor, frameClassifier;

    private IRenderer mainRenderer;

    private Button btnAdd, btnClear, btnBack;
    private TextView txtView;

    private String currentLetter, previousLetter, modLetter;

    private DetectionMethod detectionMethod;

    private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    mOpenCvCameraView.enableView();
                    mOpenCvCameraView.enableFpsMeter();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        // Set view parameters
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Set content view
        setContentView(R.layout.activity_main3);

        // Camera config
        mOpenCvCameraView = findViewById(R.id.sign_language_activity_surface_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);
        }

        btnAdd = (Button) findViewById(R.id.button_add);
        btnClear = (Button) findViewById(R.id.button_clear);
        btnBack = (Button) findViewById(R.id.button_back);
        txtView = (TextView) findViewById(R.id.textView);


        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!modLetter.equals("?"))
                    txtView.append(modLetter);
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                txtView.setText("");
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                txtView.setText(txtView.getText().toString().substring(0, txtView.getText().toString().length() - 1));
            }
        });


    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {

        // Generate Frame from input frame and downsample
        preProcessedFrame = preProcessor.preProcess(inputFrame);

        // Generate useful information from frame
        processedFrame = mainFrameProcessor.process(preProcessedFrame);

        // Post processing of processed frame and upsampling
        postProcessedFrame = postProcessor.postProcess(processedFrame);

        // Actual frame classification
        classifiedFrame = frameClassifier.process(postProcessedFrame);

        previousLetter = currentLetter;
        currentLetter = getDisplayableLetter(classifiedFrame.getLetterClass().toString());

        setLetterIfChanged();

        // Display anything required
//        mainRenderer.display(postProcessedFrame);

        // Return processed Mat
        return classifiedFrame.getRGBA();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }



    public void onCameraViewStarted(int width, int height) {

        setProcessors(DetectionMethod.CANNY_EDGES);

        File xmlFile = initialiseXMLTrainingData();

        frameClassifier = new FrameClassifier(xmlFile);

    }

    public void onCameraViewStopped() {

    }

    private void setProcessors(DetectionMethod method){
        // Set detection method
        detectionMethod = method;

        // Create renderers
        mainRenderer = new MainRenderer(detectionMethod);

        // Pre processors
        preProcessor = new InputFramePreProcessor(
                new CameraFrameAdapter(
                        new DownSamplingFrameProcessor(),
                        new ResizingFrameProcessor(SizeOperation.UP)
                )
        );

        // Frame Processors
        mainFrameProcessor = new MainFrameProcessor(detectionMethod);

        // Post processors
        postProcessor = new OutputFramePostProcessor(
                new UpScalingFramePostProcessor(),
                new ResizingFrameProcessor(SizeOperation.UP)
        );
    }

    private void setLetterIfChanged(){
        if (!currentLetter.equals(previousLetter)){
            modLetter = currentLetter;
            if (modLetter.equals("NONE"))
                modLetter = "?";
            if (modLetter.equals("SPACE"))
                modLetter = " ";
            setPossibleLetter(modLetter);
        }
    }

    private void setPossibleLetter(final String currentLetterForMod){

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                if (txtView.getText().toString().length() > 0)
                    txtView.setText(txtView.getText().toString().substring(0, txtView.getText().toString().length() - 1));

                txtView.append(currentLetterForMod);

            }
        });
    }

    private String getDisplayableLetter(String letter){

        switch (letter){
            case "NONE":
                return "?";
            case "SPACE":
                return " ";
            default:
                return letter;
        }

    }

    private File initialiseXMLTrainingData(){

        try {
            InputStream is = getResources().openRawResource(R.raw.trained);
            File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
            File mCascadeFile = new File(cascadeDir,"training.xml");

            FileOutputStream os = new FileOutputStream(mCascadeFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            is.close();
            os.close();

            return mCascadeFile;

        } catch (Exception e) {
            e.printStackTrace();
            return new File("");
        }

    }

}