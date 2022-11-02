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
        //rightFinger = hardwareMap.get(Servo.class, "rightFinger");
        leftFinger.scaleRange(0.0, 1.0);
        //rightFinger.scaleRange(0.0, 1.0);
    }

    @Override
    public void loop() {
        leftFinger.setPosition(0.75);
        //rightFinger.setPosition(0.5);

    }
}
