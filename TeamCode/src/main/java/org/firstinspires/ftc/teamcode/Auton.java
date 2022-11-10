package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Robot: Auto Drive By Time", group="Robot")
public class Auton extends LinearOpMode {
    DcMotor frontRight = null;
    DcMotor frontLeft = null;
    DcMotor backRight = null;
    DcMotor backLeft = null;
    DcMotor arm = null;
    public Servo leftFinger;
    public Servo rightFinger;

    final double frontRightPower = 0.5;
    final double frontLeftPower = 0.5;
    final double backRightPower = 0.5;
    final double backLeftPower = 0.5;
    final double armPower = 0.5;
    final double leftFingerClose = 0.25;
    final double rightFingerClose = 0.75;
    final double leftFingerOpen = 0.9;
    final double rightFingerOpen = 0.1;
    @Override

    public void runOpMode() {
        frontRight  = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight  = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        arm = hardwareMap.get(DcMotor.class, "arm");

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        arm.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        frontRight.setPower(frontRightPower);
        frontLeft.setPower(frontLeftPower);
        backRight.setPower(backRightPower);
        backLeft.setPower(backLeftPower);
        rightFinger.setPosition(rightFingerClose);
        leftFinger.setPosition(leftFingerClose);
        sleep(3000);

        frontRight.setPower(frontRightPower);
        frontLeft.setPower(frontLeftPower);
        backRight.setPower(backRightPower);
        backLeft.setPower(backLeftPower);
        arm.setPower(armPower);
        rightFinger.setPosition(rightFingerOpen);
        leftFinger.setPosition(leftFingerOpen);
        sleep(1000);

        frontRight.setPower(frontRightPower);
        frontLeft.setPower(frontLeftPower);
        backRight.setPower(backRightPower);
        backLeft.setPower(backLeftPower);
        arm.setPower(-armPower);
        rightFinger.setPosition(rightFingerClose);
        leftFinger.setPosition(leftFingerClose);
        sleep(3000);

        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);
        sleep(1000);
    }
}
