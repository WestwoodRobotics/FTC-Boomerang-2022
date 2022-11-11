package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class Main extends OpMode {
    public Servo leftFinger = null;
    public Servo rightFinger = null;
    public DcMotor frontRight = null;
    public DcMotor frontLeft = null;
    public DcMotor backRight = null;
    public DcMotor backLeft = null;
    public DcMotor arm = null;


    @Override
    public void init() {
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftFinger = hardwareMap.get(Servo.class, "leftFinger");
        rightFinger = hardwareMap.get(Servo.class, "rightFinger");
        leftFinger.scaleRange(0.0, 1.0);
        rightFinger.scaleRange(0.0, 1.0);

        arm = hardwareMap.get(DcMotor.class, "arm");
        arm.setDirection(DcMotor.Direction.REVERSE);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    double frontRightPower = 0;
    double frontLeftPower = 0;
    double backRightPower = 0;
    double backLeftPower = 0;
    double armPower = 0;
    double leftFingerPos = 0.5;
    double rightFingerPos = 0.5;

    @Override
    public void loop() {
        double drive = gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;
        frontRightPower = drive - strafe - turn;
        frontLeftPower = drive + strafe + turn;
        backRightPower = drive + strafe - turn;
        backLeftPower = drive - strafe + turn;
        //test comment
        frontRight.setPower(frontRightPower*0.5);
        frontLeft.setPower(frontLeftPower*0.5);
        backRight.setPower(backRightPower*0.5);
        backLeft.setPower(backLeftPower*0.5);

        if (gamepad1.right_bumper == true) {
            leftFingerPos = 0.5;
            rightFingerPos = 0.5;
        }
        else if (gamepad1.left_bumper== true) {
            leftFingerPos = 0.9;
            rightFingerPos = 0.1;
        }

        leftFinger.setPosition(leftFingerPos);
        rightFinger.setPosition(rightFingerPos);
        telemetry.addData("left finger pos", leftFinger.getPosition());
        telemetry.addData("right finger pos", rightFinger.getPosition());

        armPower = -gamepad1.right_trigger * 0.25;
        arm.setPower(armPower);
    }
}

