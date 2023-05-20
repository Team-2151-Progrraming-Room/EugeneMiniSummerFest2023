package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
//import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.I2C;
//import java.time.Clock;
import edu.wpi.first.cameraserver.*;
//import edu.wpi.first.wpilibj.DriverStation;
//import edu.wpi.first.wpilibj.DigitalInput;

public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
 //private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  //decalring intigers (PWM ports) for motor controllers
  private static final int frontLeftWheel = 2;   // setting up wheel motors to their PMW ports on the RobotRIO
  private static final int backLeftWheel = 3;
  private static final int frontRightWheel = 1;
  private static final int backRightWheel = 0;

/*
  //declaring integers (DIO ports) for sensors
  private static final int HALT = 0;
  private static final int CEASE = 1;

  //Servos
  Servo servo1 = new Servo(9);
  Servo servo2 = new Servo(10);
  //int replay = 0;
 
  double output;
  */

  private double startTime;

  //private final I2C.Port i2cPort = I2C.Port.kOnboard;

  private static final int gamer = 0; //sets up joystick to connect to usb port 1 on the laptop/computer

  private DifferentialDrive mecaTibbs; //gives the drive train a name
  private Joystick gStick;  //gives the joystick a name


  //Setting up motor controllers and grouping them together
  MotorController m_frontLeft = new PWMVictorSPX(frontLeftWheel);//2
  MotorController m_backLeft = new PWMVictorSPX(backLeftWheel);//3 M,KL
  MotorControllerGroup m_left = new MotorControllerGroup(m_frontLeft, m_backLeft);
  MotorController m_frontRight = new PWMVictorSPX(frontRightWheel);//1
  MotorController m_backRight = new PWMVictorSPX(backRightWheel);//0
  MotorControllerGroup m_right = new MotorControllerGroup(m_frontRight, m_backRight);

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Forward only", kDefaultAuto);
    m_chooser.addOption("Back shooter", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    
    CameraServer.startAutomaticCapture().setResolution(240,180);

    m_frontRight.setInverted(false);
    m_backRight.setInverted(false);
    m_frontLeft.setInverted(false); //flips the left side of motors for wheels
    m_backLeft.setInverted(false);//false cause... not needed this year
 
    mecaTibbs = new DifferentialDrive( m_left, m_right); //hooks up the drive train with the PMW motors
                                                 
    //that are linked to the wheels
      mecaTibbs.setExpiration(0.1);                           
    gStick = new Joystick(gamer); //hooks up joysick to the usb port that is connected to the joystick
    
  
  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  }
  @Override
  public void robotPeriodic(){
    mecaTibbs.setSafetyEnabled(true);}

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the  auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    startTime = Timer.getFPGATimestamp();

     
  }

  
  /*
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    double time = Timer.getFPGATimestamp();
    
    //General plan for autonomous: Drive forward for a period of time, drive backward for a period of time, spin left, than spin right.

             if(time - startTime < 2){//Move forward for two seconds.
              m_frontLeft.set(0.1);
              m_frontRight.set(0.1);
              m_backLeft.set(0.1);
              m_backRight.set(0.1);


            } else if(time-startTime >=2 && time-startTime < 3){//Stop for a second.
              m_frontLeft.set(0);
              m_frontRight.set(0);
              m_backLeft.set(0);
              m_backRight.set(0);
    
            }
            else if(time - startTime >= 3  && time - startTime < 5){//Move backward for two seconds.
                                         
              m_frontLeft.set(-0.1);     
              m_frontRight.set(-0.1);
              m_backLeft.set(-0.1);
              m_backRight.set(-0.1);
              
            } 
            else if(time - startTime >= 4 && time - startTime < 5){//Stop for a second.
              m_frontLeft.set(0);
              m_frontRight.set(0);
              m_backLeft.set(0);
              m_backRight.set(0);

             }
             else if(time - startTime >= 5  && time - startTime < 7){//Spins left for two seconds.
              m_frontRight.set(0.1);
              m_backRight.set(0.1);
              m_frontLeft.set(0);
              m_backLeft.set(0);
             }
             else if(time - startTime >= 7 && time-startTime < 8){//Stop for a second.
              m_frontLeft.set(0.0);
              m_frontRight.set(0.0);
              m_backLeft.set(0.0);
              m_backRight.set(0.0);
             
             }
            else if(time- startTime >= 8 && time-startTime < 10){//Spin right for two seconds.
              m_frontLeft.set(0);
              m_frontRight.set(0);
              m_backLeft.set(0.1);
              m_backRight.set(0.1);
            }
            else if(time-startTime >= 10){//At 10 seconds, the robot stops.
              m_frontLeft.set(0.0);
              m_frontRight.set(0.0);
              m_backLeft.set(0.0);
              m_backRight.set(0.0);
            }
           }


    
  // This function is called periodically during operator control.
  @Override
  public void teleopPeriodic() {
    xValue xylophone = new xValue(gStick.getX());
    yValue yylophone = new yValue(gStick.getY());
    zValue zylophone = new zValue(gStick.getZ());

    mecaTibbs.arcadeDrive(yylophone.yJoy(), zylophone.zJoy(), true); //sets driving to run using  //joystick controls
                                                                                   //joystick controls
                                                                                        
    /*
    //Brakes 
    if(gStick.getRawButton(11)==true) {//11 for normal joystick, 8 for alt
      servo1.set(0);
      servo2.set(0);
    }
     if(gStick.getRawButton(12)==true){//12 for normal joystick, 9 for alt
      servo1.set(90);
      servo2.set(90);
     }
  */
   Timer.delay(0.001);
   
    }  //timer sets up the code to have a 1 millisecond delay to avoid overworking and 
          //over heating the RobotRIO
          
class yValue{
  public yValue(double y){
    yCal = y;
  }
  public double yJoy(){
    if((yCal <= 0.2) && (yCal >= 0.0)){
      return 0.0;
    }
    else if(yCal > 0.2){
      return -yCal;
    }
    else if((yCal >= -0.2) && (yCal <= 0.0)){
      return 0.0;
    }
    else if(yCal < -0.2){
      return -yCal;
    }
    else{
      return 0.0;
    }
  }
  public double yCal;
}
class xValue{
  public xValue(double x){
    xCal = x;
  }
public double xJoy(){
  if((xCal <= 0.2) && (xCal >= 0.0)){
    return 0.0;
  }
  else if(xCal > 0.2){
    return xCal;
  }
  else if((xCal >= -0.2) && (xCal <= 0.0)){
    return 0.0;
  }
  else if(xCal < -0.2){
    return xCal;
  }
  else{
    return 0.0;
  }
}
public double xCal;
}
class zValue{
  public zValue(double z){
    zCal = z;
  }

public double zJoy(){
  if((zCal <= 0.3) && (zCal >= 0.0)){
    return 0.0;
  }
  else if(zCal > 0.3){
    return (zCal * 0.5);
  }
  else if((zCal >= -0.3) && (zCal <= 0.0)){
    return 0.0;
  }
  else if(zCal < -0.3){
    return (zCal * 0.5);
  }
  else{
    return 0.0;
  }
}
public double zCal;
}

}
