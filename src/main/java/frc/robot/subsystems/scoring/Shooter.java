package frc.robot.subsystems.scoring;

  //Robot Imports
import frc.robot.Constants.ShooterConstants;

//Talon imports
import com.ctre.phoenix6.hardware.TalonFX;
import frc.robot.subsystems.helpers.TalonFXRPMHelper;
//WPI imports
import edu.wpi.first.wpilibj2.command.SubsystemBase;




public class Shooter extends SubsystemBase {
    /**bottom shooter with canbus ID 31 */
public static final TalonFX shooterBottom = new TalonFX (ShooterConstants.kShooterBottomID);
  /**top shooter with canbus ID 32 */
public static final TalonFX shooterTop = new TalonFX (ShooterConstants.kShooterTopID);

    /* Creates a new Shooter. */
public Shooter() {
  TalonFXRPMHelper.setPID(shooterBottom, 0.12, 0.11, 0, 0, 0.05);
  TalonFXRPMHelper.setPID(shooterTop, 0.12, 0.11, 0, 0, 0.05);
}
    
@Override
public void periodic() {
  // This method will be called once per scheduler run
}

    /**
     * Makes the shooter score an AMP note using velocity control
     */
public void shooterAmpRPM(){
  TalonFXRPMHelper.setRPM(shooterBottom, -18);
  TalonFXRPMHelper.setRPM(shooterTop, 2);
}
    /**
     * Manual Shooter Speed Control 
     * @param speed the speed to control the motor * .6
     */ 
public void shooterControl(double speed){
  shooterBottom.set(speed*.6);
  shooterTop.set(speed*.6);
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
    /**
    * Makes the shooter run as a source intake
    */
public void shooterSuck(){
  shooterBottom.set(.3);
  shooterTop.set(-.30);
  
  Intake.talonIntake.set(-.6);
  Intake.intakeBottom.set(.6);
}

    /**
     * Spins motors kinda fast, suposedly does the trap
    */
public void shooterTrap(){
  shooterBottom.set(-.5);
  shooterTop.set(.50);

  
}

}