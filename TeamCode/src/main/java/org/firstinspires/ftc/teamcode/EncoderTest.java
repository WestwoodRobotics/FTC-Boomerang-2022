package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class EncoderTest extends OpMode {


    private DcMotorEx frontRight;
    private DcMotorEx frontLeft;
    private DcMotorEx backRight;
    private DcMotorEx backLeft;


    @Override
    public void init() {
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");

        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        double yIn = gamepad1.left_stick_y;

        frontRight.setPower(yIn);
        frontLeft.setPower(yIn);
        backRight.setPower(yIn);
        backLeft.setPower(yIn);

        telemetry.addData("frontRight Poaition: ", frontRight.getCurrentPosition());
        telemetry.addData("frontLeft Poaition: ", frontLeft.getCurrentPosition());
        telemetry.addData("backRight Poaition: ", backRight.getCurrentPosition());
        telemetry.addData("backLeft Poaition: ", backLeft.getCurrentPosition());


    }
}
