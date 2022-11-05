package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class Intake extends OpMode {
    public Servo leftFinger;
    public Servo rightFinger;

    @Override
    public void init() {
        leftFinger = hardwareMap.get(Servo.class, "leftFinger");
        rightFinger = hardwareMap.get(Servo.class, "rightFinger");
        leftFinger.scaleRange(0.0, 1.0);
        rightFinger.scaleRange(0.0, 1.0);
    }

    double leftFingerPos = 0.75;
    double rightFingerPos = 0.2;
    @Override
    public void loop() {
        if (gamepad1.b == true) {
            leftFingerPos = 0.75;
            rightFingerPos = 0.2;
        }
        else if (gamepad1.x == true) {
            leftFingerPos = 0.9;
            rightFingerPos = 0.1;
        }

        leftFinger.setPosition(leftFingerPos);
        rightFinger.setPosition(rightFingerPos);

    }
}
