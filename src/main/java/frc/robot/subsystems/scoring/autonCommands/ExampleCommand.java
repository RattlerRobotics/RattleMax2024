package frc.robot.subsystems.scoring.autonCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.scoring.exampleSubsystem;

public class ExampleCommand extends Command{
   
   private final exampleSubsystem m_Shooter;



   public ExampleCommand(exampleSubsystem scorer) {
      m_Shooter = scorer;
      
         //Makes sure command will not run unless these have been intilized
      addRequirements(m_Shooter);
   }

   // Called when the command is initially scheduled.
   @Override
   public void initialize() {
      // (1)
   }

   // Called every time the scheduler runs while the command is scheduled.
   @Override
   public void execute() {
      m_Shooter.shooterSpeaker();
      
   }

   // Called once the command ends or is interrupted.
   @Override
   public void end(boolean interrupted) {
      m_Shooter.shooterStop();
   }

   // Returns true when the command should end.
   @Override
   public boolean isFinished() {
      //return m_Intake.noteIsFired();
      return false;
   }

   @Override
   public boolean runsWhenDisabled() {
      return false;
   }
   
}
