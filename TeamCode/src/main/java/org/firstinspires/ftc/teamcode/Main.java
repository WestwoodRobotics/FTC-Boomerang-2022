package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class Main extends OpMode {
    public Servo leftFinger = null;
    public Servo rightFinger = null;
    public DcMotorEx frontRight = null;
    public DcMotorEx frontLeft = null;
    public DcMotorEx backRight = null;
    public DcMotorEx backLeft = null;
    public DcMotorEx slide = null;
    //EDIT THESE VALUES FOR SET POSITION
    private final int smallJunction = 2500;
    private final int mediumJunction = 4200;
    private final int highJunction = 5700;
    @Override
    public void init() {
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");

        frontRight.setDirection(DcMotorEx.Direction.REVERSE);
        frontLeft.setDirection(DcMotorEx.Direction.FORWARD);
        backRight.setDirection(DcMotorEx.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.FORWARD);
        frontRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        leftFinger = hardwareMap.get(Servo.class, "leftFinger");
        rightFinger = hardwareMap.get(Servo.class, "rightFinger");
        leftFinger.scaleRange(0.0, 1.0);
        rightFinger.scaleRange(0.0, 1.0);

        slide = hardwareMap.get(DcMotorEx.class, "slide");
        slide.setDirection(DcMotorEx.Direction.REVERSE);
        slide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        slide.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    double frontRightPower = 0;
    double frontLeftPower = 0;
    double backRightPower = 0;
    double backLeftPower = 0;
    double leftFingerPos = 0.9;
    double rightFingerPos = 0.1;
    int target = 0;
    int current = 0;
    double slowmode = 1;
    boolean slow_on = false;

    @Override
    public void loop() {
        double drive = -gamepad1.left_stick_x;
        double strafe = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
        frontRightPower = drive + strafe - turn;
        frontLeftPower = drive - strafe + turn;
        backRightPower = drive - strafe - turn;
        backLeftPower = drive + strafe + turn;
        frontRight.setPower(frontRightPower*slowmode);
        frontLeft.setPower(frontLeftPower*slowmode);
        backRight.setPower(backRightPower*slowmode);
        backLeft.setPower(backLeftPower*slowmode);
        //slowmode
        if (gamepad1.a || slide.getCurrentPosition() >= 6000) {
            if (slow_on) {
                slow_on = false;
                slowmode = 1;
            }

            else {
                slow_on = true;
                slowmode = 0.5;
            }
        }

        //gamepad 2 stuff
        //SERVO POSITIONS (CHANGE THE FINGERS IF IT DOESN'T OPEN OR CLOSE ON TIME)
        if (gamepad2.right_bumper == true) { //change
            leftFingerPos = 0.4;
            rightFingerPos = 0.6;
        }
        else if (gamepad2.left_bumper== true) { //change
            leftFingerPos = 0.9;
            rightFingerPos = 0.1;
        }

        //INCREASE VALUES 
        if (gamepad2.right_trigger > 0 || gamepad2.left_trigger > 0) { //change
            if (gamepad2.right_trigger > 0) { //change
                target += 20;
            }
            else if (gamepad2.left_trigger > 0) { //change
                target -= 20;
            }
        }

        else {
            if (gamepad2.a) { //change
                target = smallJunction;
            }
            else if (gamepad2.b) { //change
                target = mediumJunction;
            }
            else if (gamepad2.y) { //change
                target = highJunction;
            }
            else if (gamepad2.x) { // change
                target = 0;
            }
        }
        slide.setTargetPosition(target);
        slide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        current = slide.getCurrentPosition();
        if (target > current) {
            slide.setPower(.5);
        }
        else if (current > target) {
            slide.setPower(-0.8);
        }
        else if (current == target) {
            slide.setPower(0);
        }


        leftFinger.setPosition(leftFingerPos);
        rightFinger.setPosition(rightFingerPos);
        telemetry.addData("slide pos", slide.getCurrentPosition());
    }
}

