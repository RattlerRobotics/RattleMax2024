// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;


  //Robot system imports
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.robotsystems.DriveSubsystem;
import frc.robot.subsystems.scoring.AngleAdjust;
import frc.robot.subsystems.scoring.Climber;
import frc.robot.subsystems.scoring.Intake;
import frc.robot.subsystems.scoring.Shooter;
import frc.robot.subsystems.scoring.autonCommands.shootNote;
//WPI imports
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.PS4Controller.Button;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
  //Pathplanner Imports
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

/*
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
private final SendableChooser<Command> autoChooser;

  
      // The define robot subsystems so they are usable
  private final DriveSubsystem m_robotDrive = new DriveSubsystem();

  private final AngleAdjust m_angleAdjust = new AngleAdjust();
  private final Climber m_climber = new Climber();
  private final Intake m_intake = new Intake();
  private final Shooter m_shooter = new Shooter();

      // The driver's controller
  private Joystick m_driverController = new Joystick (OIConstants.kDriverControllerPort);
  private Joystick m_operatorController = new Joystick (OIConstants.kOperatorControllerPort);


  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
  
      //Shoots a single note
    NamedCommands.registerCommand("Shoot Speaker", new shootNote(m_shooter, m_intake).withTimeout(1));
  


      // Configure the button bindings
    configureButtonBindings();

      // Configure default commands
    m_robotDrive.setDefaultCommand(
        // Flight stick controls driving the robot, z axist twist turns the robot
        new RunCommand(() -> m_robotDrive.drive(
          -MathUtil.applyDeadband(m_driverController.getY(), OIConstants.kDriveDeadband),
          -MathUtil.applyDeadband(m_driverController.getX(), OIConstants.kDriveDeadband),
          -MathUtil.applyDeadband(m_driverController.getZ(), OIConstants.kDriveDeadband),
          true, true),m_robotDrive));


      //rightstick for Arm
      m_angleAdjust.setDefaultCommand(new RunCommand(() -> m_angleAdjust.angleAdjustJoystickControl(m_operatorController.getRawAxis(5)), m_angleAdjust));

      //leftstick for intake
      m_intake.setDefaultCommand(new RunCommand(() -> m_intake.intakeJoystickControl(m_operatorController.getRawAxis(1)*.6), m_intake));
 
      m_climber.setDefaultCommand(new RunCommand(() -> m_climber.climberControl(0), m_climber));

      m_shooter.setDefaultCommand(new RunCommand(() -> m_shooter.shooterStop(), m_shooter));

     // Build an auto chooser. This will use Commands.none() as the default option.
    autoChooser = AutoBuilder.buildAutoChooser();

      //Adds auto chooser to the dashboard
    Shuffleboard.getTab("Autonomous").add(autoChooser);
}



/**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its
   * subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then calling
   * passing it to a
   * {@link JoystickButton}.
*/


private void configureButtonBindings() {
    //changes turn speed to low
  new JoystickButton(m_driverController, 3).whileTrue(new RunCommand(() -> m_robotDrive.changeTurnSpeedLow(),m_robotDrive));
    
    //changes turn speed to high
  new JoystickButton(m_driverController, 4).whileTrue(new RunCommand(() -> m_robotDrive.changeTurnSpeedHigh(),m_robotDrive));

    //Resets gyro
  new JoystickButton(m_driverController, 7).whileTrue(new RunCommand(() -> m_robotDrive.zeroHeading(),m_robotDrive)); 
  
    //Sets wheels straight
   new JoystickButton(m_driverController, 8).whileTrue(new RunCommand(() -> m_robotDrive.setWheelsStraight(),m_robotDrive));
    
    //wheels in x position--brake--driver top middle button    
  new JoystickButton(m_driverController, Button.kR1.value).whileTrue(new RunCommand(() -> m_robotDrive.setX(),m_robotDrive));

    //climber--back button and right trigger  forward only
  new JoystickButton(m_operatorController, 7).whileTrue(new RunCommand(() -> m_climber.climberControl(m_operatorController.getRawAxis(3)*-1), m_climber));                          
    
    //Fire--Driver trigger                  
  new JoystickButton(m_driverController, 1).whileTrue(new RunCommand(() -> m_intake.intakeRun(), m_intake)); 
        
    //Speaker shot  y button
  new JoystickButton(m_operatorController, 4).whileTrue(new RunCommand(() -> m_shooter.shooterSpeaker(), m_shooter));   

    //manual shooter speed start and left trigger
  new JoystickButton(m_operatorController, 8).whileTrue(new RunCommand(() -> m_shooter.shooterControl(m_operatorController.getRawAxis(2)*-1), m_shooter));   

    //amp shot
  new JoystickButton(m_operatorController, 1).whileTrue(new RunCommand(() -> m_shooter.shooterAmpRPM(), m_shooter));   

    //trap shot
  new JoystickButton(m_operatorController, 2).whileTrue(new RunCommand(() -> m_shooter.shooterTrap(), m_shooter));   

    //shooter intake
  new JoystickButton(m_operatorController, 3).whileTrue(new RunCommand(() -> m_shooter.shooterSuck(), m_shooter));    

    //speical intake
  //new JoystickButton(m_operatorController, 5).whileTrue(new RunCommand(() -> m_intake.intakeSonarControl(), m_intake));    

                //End button Bindings
  }


  //Pathplanner auto selector
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }

}

