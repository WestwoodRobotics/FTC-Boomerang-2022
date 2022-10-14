package org.firstinspires.ftc.teamcode.Teleops;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

public class MecanumDrive {
    public class BlankLinearOpMode extends LinearOpMode {
        DcMotor frontRight = null;
        DcMotor frontLeft = null;
        DcMotor backRight = null;
        DcMotor backLeft = null;

        @Override
        public void runOpMode() {
            frontRight = hardwareMap.get(DcMotor.class, "frontRight");
            frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
            backRight = hardwareMap.get(DcMotor.class, "backRight");
            backLeft = hardwareMap.get(DcMotor.class, "backLeft");

            //test comment
            frontRight.setDirection(DcMotor.Direction.REVERSE);
            frontLeft.setDirection(DcMotor.Direction.FORWARD);
            backRight.setDirection(DcMotor.Direction.REVERSE);
            backLeft.setDirection(DcMotor.Direction.FORWARD);


            double drive = gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;
            double frontRightPower = 0;
            double frontLeftPower = 0;
            double backRightPower = 0;
            double backLeftPower = 0;
            waitForStart();

            while (opModeIsActive()) {
                frontRightPower = drive - strafe - turn;
                frontLeftPower = drive + strafe + turn;
                backRightPower = drive + strafe - turn;
                backLeftPower = drive - strafe + turn;

                frontRight.setPower(frontRightPower);
                frontLeft.setPower(frontLeftPower);
                backRight.setPower(backRightPower);
                backLeft.setPower(backLeftPower);
            }
        }
    }

}
