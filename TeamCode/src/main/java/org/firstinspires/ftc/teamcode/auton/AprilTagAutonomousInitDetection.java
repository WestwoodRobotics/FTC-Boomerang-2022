package org.firstinspires.ftc.teamcode.auton;///*
// * Copyright (c) 2021 OpenFTC Team
// *
// * Permission is hereby granted, free of charge, to any person obtaining a copy
// * of this software and associated documentation files (the "Software"), to deal
// * in the Software without restriction, including without limitation the rights
// * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// * copies of the Software, and to permit persons to whom the Software is
// * furnished to do so, subject to the following conditions:
// *
// * The above copyright notice and this permission notice shall be included in all
// * copies or substantial portions of the Software.
// * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// * SOFTWARE.
// */
//
//package org.firstinspires.ftc.teamcode.auton;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.Servo;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.teamcode.AprilTagDetectionPipeline;
//import org.openftc.apriltag.AprilTagDetection;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvCameraRotation;
//import org.openftc.easyopencv.OpenCvInternalCamera;
//
//import java.util.ArrayList;
//
//@Autonomous
//public class AprilTagAutonomousInitDetection extends LinearOpMode
//{
//    OpenCvCamera camera;
//    AprilTagDetectionPipeline aprilTagDetectionPipeline;
//    DcMotor frontRight = null;
//    DcMotor frontLeft = null;
//    DcMotor backRight = null;
//    DcMotor backLeft = null;
//    static final double FEET_PER_METER = 3.28084;
//
//    // Lens intrinsics
//    // UNITS ARE PIXELS
//    // NOTE: this calibration is for the C920 webcam at 800x448.
//    // You will need to do your own calibration for other configurations!
//    double fx = 822.317;
//    double fy = 822.317;
//    double cx = 319.495;
//    double cy = 242.502;
//
//    // UNITS ARE METERS
//    double tagsize = 0.166;
//
//    // Tag ID 1,2,3 from the 36h11 family
//    int LEFT = 1;
//    int MIDDLE = 2;
//    int RIGHT = 3;
//
//    AprilTagDetection tagOfInterest = null;
//
//    @Override
//    public void runOpMode()
//    {
//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
//        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);
//
//        camera.setPipeline(aprilTagDetectionPipeline);
//        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
//        {
//            @Override
//            public void onOpened()
//            {
//                camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
//            }
//
//            @Override
//            public void onError(int errorCode)
//            {
//
//            }
//        });
//
//        telemetry.setMsTransmissionInterval(50);
//
//        frontRight  = hardwareMap.get(DcMotor.class, "frontRight");
//        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
//        backRight  = hardwareMap.get(DcMotor.class, "backRight");
//        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
//
//        frontRight.setDirection(DcMotor.Direction.REVERSE);
//        frontLeft.setDirection(DcMotor.Direction.FORWARD);
//        backRight.setDirection(DcMotor.Direction.REVERSE);
//        backLeft.setDirection(DcMotor.Direction.FORWARD);
//
//
//        while (!isStarted() && !isStopRequested())
//        {
//            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();
//
//            if(currentDetections.size() != 0)
//            {
//                boolean tagFound = false;
//
//                for(AprilTagDetection tag : currentDetections)
//                {
//                    if(tag.id == LEFT || tag.id == MIDDLE || tag.id == RIGHT)
//                    {
//                        tagOfInterest = tag;
//                        tagFound = true;
//                        break;
//                    }
//                }
//
//                if(tagFound)
//                {
//                    telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
//                    tagToTelemetry(tagOfInterest);
//                }
//                else
//                {
//                    telemetry.addLine("Don't see tag of interest :(");
//
//                    if(tagOfInterest == null)
//                    {
//                        telemetry.addLine("(The tag has never been seen)");
//                    }
//                    else
//                    {
//                        telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
//                        tagToTelemetry(tagOfInterest);
//                    }
//                }
//
//            }
//            else
//            {
//                telemetry.addLine("Don't see tag of interest :(");
//
//                if(tagOfInterest == null)
//                {
//                    telemetry.addLine("(The tag has never been seen)");
//                }
//                else
//                {
//                    telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
//                    tagToTelemetry(tagOfInterest);
//                }
//
//            }
//
//            telemetry.update();
//            sleep(20);
//        }
//
//        /*
//         * The START command just came in: now work off the latest snapshot acquired
//         * during the init loop.
//         */
//
//        /* Update the telemetry */
//        if(tagOfInterest != null)
//        {
//            telemetry.addLine("Tag snapshot:\n");
//            tagToTelemetry(tagOfInterest);
//            telemetry.update();
//        }
//        else
//        {
//            telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
//            telemetry.update();
//        }
//
//        final double frontRightPower = 0.5;
//        final double frontLeftPower = 0.5;
//        final double backRightPower = 0.5;
//        final double backLeftPower = 0.5;
//        frontRight.setPower(-frontRightPower);
//        frontLeft.setPower(-frontLeftPower);
//        backRight.setPower(-backRightPower);
//        backLeft.setPower(-backLeftPower);
//        sleep(2000);
//        frontRight.setPower(0);
//        frontLeft.setPower(0);
//        backRight.setPower(0);
//        backLeft.setPower(0);
//        sleep(500);
//        frontRight.setPower(frontRightPower);
//        frontLeft.setPower(frontLeftPower);
//        backRight.setPower(backRightPower);
//        backLeft.setPower(backLeftPower);
//        sleep(2000);
//        frontRight.setPower(0);
//        frontLeft.setPower(0);
//        backRight.setPower(0);
//        backLeft.setPower(0);
//        sleep(1000);
//        /* Actually do something useful */
//        if(tagOfInterest == null || tagOfInterest.id == LEFT){
//            frontRight.setPower(-frontRightPower);
//            frontLeft.setPower(frontLeftPower);
//            backRight.setPower(backRightPower);
//            backLeft.setPower(-backLeftPower);
//            sleep(1000);
//            frontRight.setPower(frontRightPower);
//            frontLeft.setPower(frontLeftPower);
//            backRight.setPower(backRightPower);
//            backLeft.setPower(backLeftPower);
//            sleep(1000);
//            frontRight.setPower(0);
//            frontLeft.setPower(0);
//            backRight.setPower(0);
//            backLeft.setPower(0);
//            sleep(1000);
//        }else if(tagOfInterest.id == MIDDLE){
//            frontRight.setPower(-frontRightPower);
//            frontLeft.setPower(frontLeftPower);
//            backRight.setPower(backRightPower);
//            backLeft.setPower(-backLeftPower);
//            sleep(1000);
//            frontRight.setPower(0);
//            frontLeft.setPower(0);
//            backRight.setPower(0);
//            backLeft.setPower(0);
//            sleep(1000);
//        }else{
//            frontRight.setPower(-frontRightPower);
//            frontLeft.setPower(frontLeftPower);
//            backRight.setPower(backRightPower);
//            backLeft.setPower(-backLeftPower);
//            sleep(1000);
//            frontRight.setPower(-frontRightPower);
//            frontLeft.setPower(-frontLeftPower);
//            backRight.setPower(-backRightPower);
//            backLeft.setPower(-backLeftPower);
//            sleep(1000);
//            frontRight.setPower(0);
//            frontLeft.setPower(0);
//            backRight.setPower(0);
//            backLeft.setPower(0);
//            sleep(1000);
//        }
//
//
//        /* You wouldn't have this in your autonomous, this is just to prevent the sample from ending */
//        while (opModeIsActive()) {sleep(20);}
//    }
//
//    void tagToTelemetry(AprilTagDetection detection)
//    {
//        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
//        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
//        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
//        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
//        telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
//        telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
//        telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
//    }
//}

//This is our safety auton



/*

 * Copyright (c) 2021 OpenFTC Team

 *

 * Permission is hereby granted, free of charge, to any person obtaining a copy

 * of this software and associated documentation files (the "Software"), to deal

 * in the Software without restriction, including without limitation the rights

 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell

 * copies of the Software, and to permit persons to whom the Software is

 * furnished to do so, subject to the following conditions:

 *

 * The above copyright notice and this permission notice shall be included in all

 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR

 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,

 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE

 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER

 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,

 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE

 * SOFTWARE.

 */






import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;

import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;



import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import org.firstinspires.ftc.teamcode.AprilTagDetectionPipeline;
import org.firstinspires.ftc.teamcode.AprilTagDetectionPipeline;

import org.openftc.apriltag.AprilTagDetection;

import org.openftc.easyopencv.OpenCvCamera;

import org.openftc.easyopencv.OpenCvCameraFactory;

import org.openftc.easyopencv.OpenCvCameraRotation;



import java.util.ArrayList;



@Autonomous

public class AprilTagAutonomousInitDetection extends LinearOpMode

{

    //INTRODUCE VARIABLES HERE

    private ElapsedTime runtime = new ElapsedTime();

    public Servo leftFinger = null;

    public Servo rightFinger = null;

    public DcMotor slide = null;

    DcMotorEx frontRight = null;

    DcMotorEx frontLeft = null;

    DcMotorEx backRight = null;

    DcMotorEx backLeft = null;



    double frontRightPower = 0; //motor domain [-1,1]

    double frontLeftPower = 0;

    double backRightPower = 0;

    double backLeftPower = 0;



    OpenCvCamera camera;

    AprilTagDetectionPipeline aprilTagDetectionPipeline;



    static final double FEET_PER_METER = 3.28084;



    // Lens intrinsics

    // UNITS ARE PIXELS

    // NOTE: this calibration is for the C920 webcam at 800x448.

    // You will need to do your own calibration for other configurations!

    double fx = 578.272;

    double fy = 578.272;

    double cx = 402.145;

    double cy = 221.506;



    // UNITS ARE METERS

    double tagsize = 0.166;



    // Tag ID 1,2,3 from the 36h11 family

    /*EDIT IF NEEDED!!!*/



    int LEFT = 1;

    int MIDDLE = 2;

    int RIGHT = 3;



    AprilTagDetection tagOfInterest = null;



    @Override

    public void runOpMode()

    {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());

        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);



        camera.setPipeline(aprilTagDetectionPipeline);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()

        {

            @Override

            public void onOpened()

            {

                camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);

            }



            @Override

            public void onError(int errorCode)

            {

            }
        });

        telemetry.setMsTransmissionInterval(50);


        //HARDWARE MAPPING HERE etc.
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");


        frontRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        leftFinger = hardwareMap.get(Servo.class, "leftFinger");
        rightFinger = hardwareMap.get(Servo.class, "rightFinger");
        leftFinger.scaleRange(0.0, 1.0);
        rightFinger.scaleRange(0.0, 1.0);



        //Zero Power Behavior: full break when motor input = 0;
        frontRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        //setting PID coefficients
        frontRight.setVelocityPIDFCoefficients(15, 0, 0, 0);
        frontLeft.setVelocityPIDFCoefficients(15, 0, 0, 0);
        backRight.setVelocityPIDFCoefficients(15, 0, 0, 0);
        backLeft.setVelocityPIDFCoefficients(15, 0, 0, 0);

        slide = hardwareMap.get(DcMotorEx.class, "slide");
        slide.setDirection(DcMotorEx.Direction.REVERSE);
        slide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        slide.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        /*

         * The INIT-loop:

         * This REPLACES waitForStart!

         */
        leftFinger.setPosition(0.4);
        rightFinger.setPosition(0.6);
        slide.setPower(0.5);
        sleep(1000);
        while (!isStarted() && !isStopRequested())
        {
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();
            if(currentDetections.size() != 0)
            {
                boolean tagFound = false;
                for(AprilTagDetection tag : currentDetections)
                {
                    if(tag.id == LEFT || tag.id == MIDDLE || tag.id == RIGHT)
                    {
                        tagOfInterest = tag;
                        tagFound = true;
                        break;
                    }
                }

                if(tagFound)
                {
                    telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                    tagToTelemetry(tagOfInterest);
                }

                else
                {
                    telemetry.addLine("Don't see tag of interest :(");
                    if(tagOfInterest == null)
                    {
                        telemetry.addLine("(The tag has never been seen)");
                    }
                    else
                    {
                        telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        tagToTelemetry(tagOfInterest);
                    }
                }

            }
            else
            {
                telemetry.addLine("Don't see tag of interest :(");

                if(tagOfInterest == null)
                {
                    telemetry.addLine("(The tag has never been seen)");
                }
                else
                {
                    telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    tagToTelemetry(tagOfInterest);
                }
            }
            telemetry.update();
            sleep(20);
        }


        if(tagOfInterest != null)
        {
            telemetry.addLine("Tag snapshot:\n");
            tagToTelemetry(tagOfInterest);
            telemetry.update();
        }
        else
        {
            telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
            telemetry.update();
        }

        //PUT AUTON CODE HERE (DRIVER PRESSED THE PLAY BUTTON!)

        //EDIT STUFF HERE
        if(tagOfInterest.id == LEFT) { //ONE
            //FORWARD
            frontRight.setPower(-0.75);
            frontLeft.setPower(-0.75);
            backRight.setPower(-0.75);
            backLeft.setPower(-0.75);
            runtime.reset();
            sleep(900);

            frontRight.setPower(0);
            frontLeft.setPower(0);
            backRight.setPower(0);
            backLeft.setPower(0);
            sleep(200);

            //LEFT
            frontRight.setPower(0.75);
            frontLeft.setPower(-0.75);
            backRight.setPower(-0.75);
            backLeft.setPower(0.75);
            sleep(900);

            frontRight.setPower(0);
            frontLeft.setPower(0);
            backRight.setPower(0);
            backLeft.setPower(0);

            telemetry.addData("Path", "Complete");
            telemetry.update();
            sleep(1000);

        } else if (tagOfInterest.id == MIDDLE) { //TWO
            //FORWARD
            frontRight.setPower(-0.75);
            frontLeft.setPower(-0.75);
            backRight.setPower(-0.75);
            backLeft.setPower(-0.75);
            sleep(900);

            frontRight.setPower(0);
            frontLeft.setPower(0);
            backRight.setPower(0);
            backLeft.setPower(0);

            telemetry.addData("Path", "Complete");
            telemetry.update();
            sleep(1000);

        } else if (tagOfInterest.id == RIGHT) { //THREE
            //FORWARD
            frontRight.setPower(-0.75);
            frontLeft.setPower(-0.75);
            backRight.setPower(-0.75);
            backLeft.setPower(-0.75);
            runtime.reset();
            sleep(900);

            frontRight.setPower(0);
            frontLeft.setPower(0);
            backRight.setPower(0);
            backLeft.setPower(0);
            sleep(200);

            //RIGHT
            frontRight.setPower(-0.75);
            frontLeft.setPower(0.75);
            backRight.setPower(0.75);
            backLeft.setPower(-0.75);
            sleep(900);

            frontRight.setPower(0);
            frontLeft.setPower(0);
            backRight.setPower(0);
            backLeft.setPower(0);

            telemetry.addData("Path", "Complete");
            telemetry.update();
            sleep(1000);
        } else {
            telemetry.addLine("No tag snapshot available, it was never sighted during the init loop :(");
            telemetry.update();
        }

    }

    void tagToTelemetry(AprilTagDetection detection)
    {
        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
        telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
        telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
        telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
        telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
    }
}

