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
    private final int smallJunction = 1599;
    private final int mediumJunction = 3548;
    private final int highJunction = 5500; //TWEAK THIS VALUE IF NECESSARY
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

        slide = hardwareMap.get(DcMotorEx.class, "arm");
        slide.setDirection(DcMotorEx.Direction.REVERSE);
        slide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        slide.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    double frontRightPower = 0;
    double frontLeftPower = 0;
    double backRightPower = 0;
    double backLeftPower = 0;
    double armPower = 0;
    double leftFingerPos = 0.9;
    double rightFingerPos = 0.1;
    int target = 0;
    int current = 0;

    @Override
    public void loop() {
        double drive = gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;
        frontRightPower = drive + strafe - turn;
        frontLeftPower = drive - strafe + turn;
        backRightPower = drive - strafe - turn;
        backLeftPower = drive + strafe + turn;
        frontRight.setPower(frontRightPower*0.75);
        frontLeft.setPower(frontLeftPower*0.75);
        backRight.setPower(backRightPower*0.75);
        backLeft.setPower(backLeftPower*0.75);

        if (gamepad1.right_bumper == true) {
            leftFingerPos = 0.5;
            rightFingerPos = 0.5;
        }
        else if (gamepad1.left_bumper== true) {
            leftFingerPos = 0.9;
            rightFingerPos = 0.1;
        }


        if (gamepad1.right_trigger > 0 || gamepad1.left_trigger > 0) {
            if (gamepad1.right_trigger > 0) {
                target += 28;
            }
            else if (gamepad1.left_trigger > 0) {
                target -= 28;
            }
        }

        else {
            if (gamepad1.a) {
                target = smallJunction;
            }
            else if (gamepad1.b) {
                target = mediumJunction;
            }
            else if (gamepad1.y) {
                target = highJunction;
            }
        }
        slide.setTargetPosition(target);
        slide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        current = slide.getCurrentPosition();
        if (target > current) {
            slide.setPower(.5);
        }
        else if (current > target) {
            slide.setPower(-.5);
        }
        else if (current == target) {
            slide.setPower(0);
        }

        leftFinger.setPosition(leftFingerPos);
        rightFinger.setPosition(rightFingerPos);
        //telemetry.addData("left finger pos", leftFinger.getPosition());
        //telemetry.addData("right finger pos", rightFinger.getPosition());

    }
}

