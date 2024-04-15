// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.scoring;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.AngleAdjustConstants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;


public class AngleAdjust extends SubsystemBase {

  private final CANSparkMax angler = new CANSparkMax(AngleAdjustConstants.kAngleAdjustID,MotorType.kBrushless);
 

  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

  /** Creates a new AngleAdjust. */
  public AngleAdjust() {
      // Sets the rate limit to 30 amps so the motor doesn't fry
    angler.setSecondaryCurrentLimit(30);
  }

  @Override
  public void periodic() { }



   /**
    * This is used for controling the angle adjust with a linear input

    * @param speed is the speed you wish to send into the motor (in volts)
    */ 
public void angleAdjustJoystickControl(double speed){  
  angler.set(speed);
}

public void angleAdjustStop(double speed) {  
  angler.set(0);
}  

}