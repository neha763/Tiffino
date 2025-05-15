package com.quantum.tiffino.utils;

import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_imgproc.*;

import java.io.File;

public class FaceRecognitionUtil {

    public static boolean compareFaces(File profilePic, File selfie) {
        // Load images
        Mat profileImg = opencv_imgcodecs.imread(profilePic.getAbsolutePath());
        Mat selfieImg = opencv_imgcodecs.imread(selfie.getAbsolutePath());

        // Check if images are loaded properly
        if (profileImg.empty() || selfieImg.empty()) {
            System.err.println("Error: One or both images are empty!");
            return false;
        }

        // Resize images to a fixed size
        Size size = new Size(200, 200);
        resize(profileImg, profileImg, size);
        resize(selfieImg, selfieImg, size);

        // Convert to grayscale
        Mat grayProfile = new Mat();
        Mat graySelfie = new Mat();
        cvtColor(profileImg, grayProfile, COLOR_BGR2GRAY);
        cvtColor(selfieImg, graySelfie, COLOR_BGR2GRAY);

        // Convert to CV_8U
        grayProfile.convertTo(grayProfile, CV_8U);
        graySelfie.convertTo(graySelfie,CV_8U);

        // Debugging: Print image details
        System.out.println("Gray Profile Depth: " + grayProfile.depth());
        System.out.println("Gray Selfie Depth: " + graySelfie.depth());

        // Ensure images are not empty
        if (grayProfile.empty() || graySelfie.empty()) {
            System.err.println("Error: Grayscale images are empty!");
            return false;
        }
// Initialize histogram variables
        Mat histProfile = new Mat();
        Mat histSelfie = new Mat();

        IntPointer channels = new IntPointer(0);
        IntPointer histSize = new IntPointer(256);
        FloatPointer histRange = new FloatPointer(2);
        histRange.put(0, 0.0f);
        histRange.put(1, 256.0f);

        Mat mask = new Mat();

// Corrected calcHist calls
        calcHist(new MatVector(grayProfile), channels, mask, histProfile, histSize, histRange);
        calcHist(new MatVector(graySelfie), channels, mask, histSelfie, histSize, histRange);

        normalize(histProfile, histProfile, 0.0, 1.0, NORM_MINMAX, -1, new Mat());
        normalize(histSelfie, histSelfie, 0.0, 1.0, NORM_MINMAX, -1, new Mat());


        // Compare histograms
        double correlation = compareHist(histProfile, histSelfie, HISTCMP_CORREL);
        System.out.println("Selfie Match Score: " + correlation);

        // Cleanup
        profileImg.release();
        selfieImg.release();
        grayProfile.release();
        graySelfie.release();
        histProfile.release();
        histSelfie.release();

        // Return success if correlation is high
        if (correlation >= 0.7) {
            System.out.println("✅ Login successful");
            return true;
        } else {
            System.out.println("❌ Login failed: Face mismatch");
            return false;
        }
    }
}