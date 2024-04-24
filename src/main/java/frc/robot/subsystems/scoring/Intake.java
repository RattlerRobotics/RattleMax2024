// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.scoring;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.IntakeConstants;

//Rev imports
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//Talon imports
import com.ctre.phoenix6.hardware.TalonFX;





public class Intake extends SubsystemBase {

public static final CANSparkMax intakeBottom = new CANSparkMax(IntakeConstants.kBottomIntakeID,MotorType.kBrushless );
public static final TalonFX talonIntake = new TalonFX (IntakeConstants.kIntakeID);
public static final Spark led = new Spark(DriveConstants.kLedPortID);

private static final I2C.Port i2cPort = I2C.Port.kOnboard;
private static final ColorSensorV3 m_colorSensorV3 = new ColorSensorV3(i2cPort);

private boolean noteIn = true;

  /** Creates a new Intake. */
  public Intake() {
                            //If this fries a motor blame that weird kid in our pit at SA 2024
    intakeBottom.setSecondaryCurrentLimit(30);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run  
  }


  /**
   * Controls the intake with the joystick
   * @param speed is the speed sent to the motors
   */
  public void intakeJoystickControl(double speed){
    talonIntake.set(((speed/m_colorSensorV3.getProximity())*-100));
    intakeBottom.set(((speed/m_colorSensorV3.getProximity())*-100));

    if(m_colorSensorV3.getProximity() > 2000) {
      led.set(-0.49);
      noteIn = true;
    }else{
      led.set(0.0);
      noteIn = false;
    }
    SmartDashboard.putBoolean("Note is in", noteIn);

  }

    /**Runs the intake at 0.6 and 1 */
  public void intakeRun(){
    talonIntake.set(0.6);
    intakeBottom.set(1);
    
    var alliance = DriverStation.getAlliance();
    if (alliance.isPresent()) {
      if (alliance.get() == DriverStation.Alliance.Red){
        led.set(-0.85);
      }else if (alliance.get() == DriverStation.Alliance.Blue){
        led.set(-0.83); 
      }
    }
  }

  
    /**stops the talon intake (not the bottom) */
  public void intakeStop(){
    talonIntake.set(0);
    intakeBottom.set(0);
    led.set(0.0);
  }

 


}

