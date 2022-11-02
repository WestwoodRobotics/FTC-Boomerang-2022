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
        leftFinger.setPosition(0.5);
        rightFinger.setPosition(0.5);
    }

    @Override
    public void loop() {

    }
}
