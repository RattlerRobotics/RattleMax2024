package frc.robot.subsystems.scoring;

  //Robot Imports
import frc.robot.Constants.exampleSubsystemConstants;

//Talon imports
import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.subsystems.helpers.TalonFXRPMHelper;
//WPI imports
import edu.wpi.first.wpilibj2.command.SubsystemBase;




public class exampleSubsystem extends SubsystemBase {
    /**bottom shooter with canbus ID 31 */
public static final TalonFX shooterBottom = new TalonFX (exampleSubsystemConstants.kShooterBottomID);
  /**top shooter with canbus ID 32 */
public static final TalonFX shooterTop = new TalonFX (exampleSubsystemConstants.kShooterTopID);

    /* Creates a new Shooter. */
public exampleSubsystem() {
  TalonFXRPMHelper.setPID(shooterBottom, 0.12, 0.11, 0, 0, 0.05);
  TalonFXRPMHelper.setPID(shooterTop, 0.12, 0.11, 0, 0, 0.05);
}
    
@Override
public void periodic() {
  // This method will be called once per scheduler run
}

    /**
     * Makes the shooter score an speaker note
     */
public void shooterSpeaker(){
  TalonFXRPMHelper.setRPM(shooterBottom, -60);
  TalonFXRPMHelper.setRPM(shooterTop, 60);
}


    /**
     * Stops the shooter
     */
public void shooterStop(){
  shooterBottom.set(.0);
  shooterTop.set(0);
}

}