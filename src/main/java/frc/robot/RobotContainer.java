// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;


  //Robot system imports
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.robotsystems.DriveSubsystem;
import frc.robot.subsystems.scoring.exampleSubsystem;
import frc.robot.subsystems.scoring.autonCommands.ExampleCommand;
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

  private final exampleSubsystem m_shooter = new exampleSubsystem();

      // The driver's controller
  private Joystick m_driverController = new Joystick (OIConstants.kDriverControllerPort);
  private Joystick m_operatorController = new Joystick (OIConstants.kOperatorControllerPort);


  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
  
      //Shoots a single note
    NamedCommands.registerCommand("Shoot Speaker", new ExampleCommand(m_shooter).withTimeout(1));
  


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
        
    //Speaker shot  y button
  new JoystickButton(m_operatorController, 4).whileTrue(new RunCommand(() -> m_shooter.shooterSpeaker(), m_shooter));   

     

    //speical intake
  //new JoystickButton(m_operatorController, 5).whileTrue(new RunCommand(() -> m_intake.intakeSonarControl(), m_intake));    

                //End button Bindings
  }


  //Pathplanner auto selector
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }

}

