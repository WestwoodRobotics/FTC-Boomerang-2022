package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp()
public class Main extends LinearOpMode{
    Servo leftFinger = null;
    Servo rightFinger = null;
    DcMotor frontRight = null;
    DcMotor frontLeft = null;
    DcMotor backRight = null;
    DcMotor backLeft = null;
    DcMotor arm = null;

    @Override
    public void runOpMode() {
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);

        leftFinger = hardwareMap.get(Servo.class, "leftFinger");
        rightFinger = hardwareMap.get(Servo.class, "rightFinger");

        leftFinger.scaleRange(0.0, 1.0);
        rightFinger.scaleRange(0.0, 1.0);

        arm = hardwareMap.get(DcMotor.class, "arm");

        arm.setDirection(DcMotor.Direction.REVERSE);


        double frontRightPower = 0;
        double frontLeftPower = 0;
        double backRightPower = 0;
        double backLeftPower = 0;
        double armPower = 0;
        double leftFingerPos = 0.25;
        double rightFingerPos = 0.75;
        waitForStart();

        while (opModeIsActive()) {
            double drive = gamepad1.left_stick_y;
            double strafe = -gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;
            frontRightPower = drive - strafe - turn;
            frontLeftPower = drive + strafe + turn;
            backRightPower = drive + strafe - turn;
            backLeftPower = drive - strafe + turn;

            frontRight.setPower(frontRightPower);
            frontLeft.setPower(frontLeftPower);
            backRight.setPower(backRightPower);
            backLeft.setPower(backLeftPower);

            if (gamepad1.b == true) {
                leftFingerPos = 0.25;
                rightFingerPos = 0.75;
            }
            else if (gamepad1.x == true) {
                leftFingerPos = 0.9;
                rightFingerPos = 0.1;
            }

            leftFinger.setPosition(leftFingerPos);
            rightFinger.setPosition(rightFingerPos);

            armPower = -gamepad1.right_stick_y * 0.5;
            arm.setPower(armPower);
        }
    }
}

