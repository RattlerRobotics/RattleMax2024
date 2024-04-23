package frc.robot.subsystems.scoring.autonCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.scoring.Intake;
import frc.robot.subsystems.scoring.Shooter;

public class shootNote extends Command{
    
     private final Shooter m_Shooter;
     private final Intake m_Intake;

      private long startTime = System.currentTimeMillis();


     public shootNote(Shooter scorer, Intake sucker) {
         m_Shooter = scorer;
         m_Intake = sucker;
         
            //Makes sure command will not run unless these have been intilized
         addRequirements(m_Shooter, m_Intake);
     }

     // Called when the command is initially scheduled.
     @Override
     public void initialize() {
        m_Shooter.shooterSpeaker(); // (1)
     }

     // Called every time the scheduler runs while the command is scheduled.
     @Override
     public void execute() {
        m_Intake.intakeRun();

     }

     // Called once the command ends or is interrupted.
     @Override
     public void end(boolean interrupted) {
        m_Shooter.shooterStop(); // (3)
     }

     // Returns true when the command should end.
     @Override
     public boolean isFinished() {
      if (System.currentTimeMillis() - startTime > 1000){
         return true;
      }else{
         return false;
      }
      
     }

     @Override
     public boolean runsWhenDisabled() {
        return false;
     }
    
}
