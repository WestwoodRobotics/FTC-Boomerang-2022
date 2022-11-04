package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp()
public class FourBarLinkage extends LinearOpMode{
    DcMotor arm = null;

    @Override
    public void runOpMode() {
        arm = hardwareMap.get(DcMotor.class, "arm");


        //test comment
        arm.setDirection(DcMotor.Direction.REVERSE);


        waitForStart();

        while (opModeIsActive()) {
            double armPower = gamepad1.right_stick_y;
            arm.setPower(armPower);
        }
    }
}

