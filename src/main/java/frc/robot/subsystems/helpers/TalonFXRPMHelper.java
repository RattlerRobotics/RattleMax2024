package frc.robot.subsystems.helpers;

   //Talon imports
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.controls.VelocityVoltage;


                            //If this fries a motor blame that weird kid in our pit at SA 2024

public class TalonFXRPMHelper {
        //Sets PID values for motor (SHOULD ONLY CALL ONCE IN ROBOTINIT, YOU DON'T NEED TO RECONFIG PID EVERY TIME YOU RUN THE MOTOR!!!)
    /**
     * This method is used to help with setting PID values for Talon motors to make our code a bit cleaner! If this fries a motor blame that weird kid who was in our pit as SA 2024
     * 
     * @param motor is the TalonFX motor you wish to set PID values for
     * 
     * @param kVin is a double that refers to velocity feedforward gain
     * @param kPin is a double that refers to proportional gain
     * @param kIin is a double that refers to integral gain
     * @param kDin is a double that refers to derivative gain
     * 
     * @param timeOut is a doulbe that refers to the timeout time on the configurator - Defualt is 0.05 
     */
    
        public static void setPID(TalonFX motor, double kVin, double kPin, double kIin, double kDin, double timeOut){
         Slot0Configs slot0Config = new Slot0Configs();

            //Sets slots to values
        slot0Config.kV = kVin; //Velocity Feedforward Gain
        slot0Config.kP = kPin; //Proportional Gain
        slot0Config.kI = kIin; //Integral Gain
        slot0Config.kD = kDin; //Derivative Gain
       
            //Applys configs to the motor
        motor.getConfigurator().apply(slot0Config, timeOut);
    }

        //Runs the motor based on velocity ONCE PID VALUES ARE SET!!!!
    
        /**
         * Sets motor RPM assuming PID values have been configured! 
         * 
         * @param motor is the motor you with to send the RPM to 
         * @param velocity this is the velocity you wish to send into the motor - I believe the range is from -1.0 to 1.0 but I could be wrong
         */
    public static void setRPM(TalonFX motor, double velocity) {
            //makes velocity into a useful number
        VelocityVoltage velVolt = new VelocityVoltage(velocity);

            //Sets slot for velocity
        velVolt.Slot = 0;
            
            //Sets motor to velocity
        motor.setControl(velVolt);  
    }
}

