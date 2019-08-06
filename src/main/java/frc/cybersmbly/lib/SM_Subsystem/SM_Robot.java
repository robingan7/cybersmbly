package frc.cybersmbly.lib.SM_Subsystem;
/**
 * <h1>CyberSMbly Libary</h1>
 * This is the class that contains all the public users interface. This is a library which 
 * makes testing and learning roboics programming easier. 
 * <p>
 * It contains code to actiavte autonomous, PID, etc. I still adding things to this library. 
 * So let me know if you have questions on any of the version.  
 * <b>Note:</b>Please point out things that I can improve. Thanks
 * 
 * @author Xiangyu(Robin) Gan 
 * @version 1.0
 * @since 2019.5.7
 */
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import java.util.ArrayList;
import java.util.HashMap;

import frc.cybersmbly.lib.SM_Electronics.*;
import frc.cybersmbly.until.functions.MotorFunction;
import frc.cybersmbly.until.functions.SolenoidFunction;

public class SM_Robot{
    private static ArrayList<Multi_Motor> motorsgroups;
    private static ArrayList<Multi_Solenoid> solenoidsgroups;
    private static Joystick j1, j2;
    private static DifferentialDrive drive;
    public static SM_Robot Robot=new SM_Robot();
    private static final HashMap<String, MotorFunction> motor_select;
    private static MotorFunction tem_talon=new Single_Motor_Talon();
    private static MotorFunction tem_victor=new Single_Motor_Victor();

    private static HashMap<String, MotorGroup_Feature> motor_feature;

    static{
        motor_select=new HashMap<>();
        motor_select.put("talon",tem_talon);
        motor_select.put("victor",tem_victor);

        motor_feature=new HashMap<>();
    }

    private static class InvalidTypeException extends Exception {
        public InvalidTypeException(){
            super("Invalid Type");
        }
    }
    private static class InvalidValueException extends Exception {
        public InvalidValueException(){
            super("Invalid Value");
        }
    }

    /**
     * contains all possible directions
     */
    public enum Directions{
        kForward(1),
        kBackward(-1);

        private int val;
        private Directions(int v){
            val=v;
        }

        public int getVal(){
            return val;
        }
    }
   
    /**
     * contains all possible button numbers
     */
    public static enum Button{
        button1(1),
        button2(2),
        button3(3),
        button4(4),
        button5(5),
        button6(6),
        button7(7),
        button8(8),
        button9(9),
        button10(10),
        button11(11),
        button12(12);

        private int val;
        private Button(int v){
            val=v;
        }
        public int getVal(){
            return val;
        }
    }

    /**
     * contains the two joysticks 
     */
    public enum JoyStick{
        joystick1(j1),
        joystick2(j2);

        private Joystick joy;
        private JoyStick(Joystick jj){
            joy=jj;
        }
        public Joystick getJoy(){
            return joy;
        }
    }

    /**
     * contains all axis on joystick
     */
    public enum Axis{
        axis0(0),
        axis1(1),
        axis2(2),
        axis3(3),
        axis4(4),
        axis5(5),
        axis6(6),
        axis7(7),
        axis8(8);

        private int num;
        private Axis(int n){
            num=n;
        }
        public int getVal(){
            return num;
        }
    }

    /**
     * contains four types of electronics
     * 1. talon motor controller
     * 2. victor motor controller 
     * 3. solenoid(pneumatics controller)
     * 4. doublesolenoid(pneumatics controller, this solenoid will keep its position after the robot is turned off)
     */
    public static enum Electronics{
        talon("talon"),
        victor("victor"),
        solenoid("solenoid"),
        doublesolenoid("doublesolenoid");

        private String type;
        private Electronics(String s){
            type=s;
        }
        public String getString(){
            return type;
        }
    }

    /**
     * the static class instance contains a motor groups, a solenoid groups and two drivers joysticks
     */
    public SM_Robot(){
        motorsgroups=new ArrayList<Multi_Motor>();
        solenoidsgroups =new ArrayList<Multi_Solenoid>();
        j1=new Joystick(0);
        j2=new Joystick(1);
    }

    /**
     * This method is overloaded several times. It adds a new electronics element to the robot.
     * @param name the name you want for the electronics you add. 
     * @param e the type of electronics @see #Electronics
     * @param channelnumber the channel number of the new electronics
     */
    public static void add(String name, final Electronics e, int channelnumber){
        try{
        int count=0;
        if(e.getString().equalsIgnoreCase("talon") || e.getString().equalsIgnoreCase("victor")){
            if(motorsgroups.size()>0){
                for(Multi_Motor ele: motorsgroups){
                    if(ele.getType().equalsIgnoreCase(name)){
                        ele.addMotor(motor_select.get(e.getString()).make_motor(name, channelnumber));
                        count++;
                        break;
                    }
                }

                if(count==0){
                    Multi_Motor m1=new Multi_Motor(name);
                    m1.addMotor(motor_select.get(e.getString()).make_motor(name, channelnumber));
                    motorsgroups.add(m1);
                    motor_feature.put(name, new MotorGroup_Feature());
                }
           
        }else{
            Multi_Motor m1=new Multi_Motor(name);
            m1.addMotor(motor_select.get(e.getString()).make_motor(name, channelnumber));  
            motorsgroups.add(m1);
            motor_feature.put(name, new MotorGroup_Feature());
        }
        }
        else if(e.getString().equalsIgnoreCase("solenoid")){
            count=0;
            if(solenoidsgroups.size()>0){
                for(Multi_Solenoid ele: solenoidsgroups){
                    if(ele.getType().equalsIgnoreCase(name)){
                        ele.addSolenoid(new SM_Solenoid(channelnumber));
                        count++;
                    }
                }
            if(count==0){
                Multi_Solenoid m1=new Multi_Solenoid(name);
                m1.addSolenoid(new SM_Solenoid(channelnumber));
                solenoidsgroups.add(m1);
            }
        }else{
            Multi_Solenoid m1=new Multi_Solenoid(name);
            m1.addSolenoid(new SM_Solenoid(channelnumber));
            solenoidsgroups.add(m1);
        }
        }else{
            throw new InvalidTypeException();
        }
    }
    catch(InvalidTypeException e1){
        System.out.println("Unknown Type");
    }
    }

   /**
     * This method overloads the previous method. It is for electronics that have two channels
     * @param name the name you want for the electronics you add. 
     * @param e the type of electronics @see #Electronics
     * @param channelnumber1 the first channel number of the new electronics
     * @param channelnumber2 the seoond channel number of the new electronics
     */
    public static void add(String name, final Electronics e, int channelnumber1,int channelnumber2){
        try{
        int count=0;
        if(e.getString().equalsIgnoreCase("doublesolenoid")){
            if(solenoidsgroups.size()>0){
                for(Multi_Solenoid ele: solenoidsgroups){
                    if(ele.getType().equalsIgnoreCase(name)){
                        ele.addSolenoid(new SM_DoubleSolenoid(channelnumber1,channelnumber2));
                        count++;
                        break;
                    }
            }
            if(count==0){
                Multi_Solenoid m1=new Multi_Solenoid(name);
                m1.addSolenoid(new SM_DoubleSolenoid(channelnumber1,channelnumber2));
                solenoidsgroups.add(m1);
            }
        }
        }else{
            throw new InvalidTypeException();
        }
    }
    catch(InvalidTypeException e1){
        System.out.println("Unknown Type");
    }

    }

    /**
     * This method that change the invert state of a specific motor group
     * @param n name of the motor group you want to change invert state
     * @param isInverted the invert state you want to set to the motor group
     */
    public static void setInverse(String n,boolean isInverted){
        
       for(int i=0; i<motorsgroups.size();i++){
           if(motorsgroups.get(i).getType().equalsIgnoreCase(n)){
                motorsgroups.get(i).setInverted(isInverted);
                break;
           }
       }
    }

    /**
     * apply deadband to the input value
     * @param value the original value input
     * @param deadband the deadband that applies to the value
     */
    private static double applyDeadband(double value, double deadband) {
        if (Math.abs(value) > deadband) {
          if (value > 0.0) {
            return (value - deadband) / (1.0 - deadband);
          } else {
            return (value + deadband) / (1.0 - deadband);
          }
        } else {
          return 0.0;
        }
      }    

    /**
     * move motor group in precent output mode.
     * @param n the name of the motor group 
     * @param j joystick that provides percent output value
     * @param a the asix of the joystick 
     */  
    public static void movePercent(String n, JoyStick j, Axis a){
        for(Multi_Motor ele: motorsgroups){
            if(ele.getType().equals(n)){
                ele.set(applyDeadband(j.getJoy().getRawAxis(a.getVal())*motor_feature.get(n).getDirection()*motor_feature.get(n).getSpeed(),motor_feature.get(n).getDeadband()));
                System.out.println(applyDeadband(j.getJoy().getRawAxis(a.getVal())*motor_feature.get(n).getDirection()*motor_feature.get(n).getSpeed(),motor_feature.get(n).getDeadband()));
                break;
            }
        }
    }

    /*
    public void movePercent(String n, double value){
        for(int i=0; i<motorsgroups.size();i++){
            if(motorsgroups.get(i).getType().equalsIgnoreCase(n)){
                for(int i2=0;i2<motorsgroups.get(i).getMotorList().size();i2++){
                    motorsgroups.get(i).getMotorList().get(i2).movePercentage(value*direction*speed);
                }
                 break;
            }
        }
    }
    */

    /**
     * move robot in PID mode, usually for uppersystem or autonomous. 
     * 
     * @param n the name of target motor group 
     * @param distance the distance or the error you want to set to the robot
     */
    public void moveDistance(String n, double distance){
        for(int i=0; i<motorsgroups.size();i++){
            if(motorsgroups.get(i).getType().equalsIgnoreCase(n)){
                for(int i2=0;i2<motorsgroups.get(i).getMotorList().size();i2++){
                    motorsgroups.get(i).getMotorList().get(i2).moveDistance(distance);
                }
                 break;
            }
        }
    }

     /**
     * move robot in PID mode, usually for uppersystem or autonomous. Activate with a button
     * 
     * @param n the name of target motor group 
     * @param j the joystick that contains the activate button @see #Joystick
     * @param b after driver press this button the robot move the distance
     * @param distance the distance or the error you want to set to the robot
     */
    public void moveDistance(String n,JoyStick j, Button b,double distance){
        if(j.getJoy().getRawButton(b.getVal())){
            moveDistance(n,distance);
        }
    }

    /**
     * activate the target solenoid
     * @param n the name of the target solenoid 
     */
    public static void turnOn(String n){
        
        for(int i=0; i<solenoidsgroups.size();i++){
            if(solenoidsgroups.get(i).getType().equalsIgnoreCase(n)){
                for(int i2=0;i2<solenoidsgroups.get(i).getSolenoidList().size();i2++){
                    solenoidsgroups.get(i).getSolenoidList().get(i2).turnOn();
                }
                 break;
            }
        }
     }

     /**
      * deactivate the target solenoid
      * @param n the name of the target solenoid 
      */
     public static void turnOff(String n){
        
        for(SolenoidFunction ele: solenoidsgroups){
            if(ele.getType().equalsIgnoreCase(n)){
                ele.turnOff();
                break;
            }
        }
     }

     /**
      * deactivate the target solenoid after a button is pressed. 
      * @param n the name of the target solenoid 
      * @param j the joystick that contains deactivate button
      * @param b after this button the target solenoid is deactivated
      */
     public void turnOff(String n, JoyStick j, Button b){
         if(j.getJoy().getRawButton(b.getVal())){
             turnOff(n);
         }
     }

     /**
      * activate the target solenoid after a button is pressed. 
      * @param n the name of the target solenoid 
      * @param j the joystick that contains activate button
      * @param b after this button the target solenoid is activated
      */
    public void turnOn(String n, JoyStick j, Button b){
       if(j.getJoy().getRawButton(b.getVal())){
           turnOn(n);
       }
    }

    /**
     * set the two motor groups to left and right side of a west coast drive 
     * @param right the name of motor group that is assigned to right side
     * @param left the name of motor group that is assigned to left side 
     */
    public static void set_West_Coast_Drive(String right, String left){
        int righti,lefti;
        righti=0;
        lefti=0;
        for(int i=0; i<motorsgroups.size();i++){
            if(motorsgroups.get(i).getType().equalsIgnoreCase(right)){
                righti=i;
                break;
            }
        }    

        for(int i=0; i<motorsgroups.size();i++){
            if(motorsgroups.get(i).getType().equalsIgnoreCase(left)){
                lefti=i;
                break;
            }
        }
        motor_feature.put("do_not_use_this_name_sichuanmianyang",new MotorGroup_Feature());    
        //System.out.println(motorsgroups.get(righti).numberOfMotors()+"\n"+motorsgroups.get(lefti).numberOfMotors());
        drive=new DifferentialDrive(motorsgroups.get(righti), motorsgroups.get(lefti));
    }
    
    /**
     * implements arcade drive algorithm
     * @param j the joystick that controls the west coast drive
     * @param a1 the forward axis
     * @param a2 the turn axis
     */
    public static void arcadeDrive(JoyStick j, Axis a1,Axis a2){
        double forward, turn;
       // forward=turn=0;
        forward=j.getJoy().getRawAxis(a1.getVal());
        turn=j.getJoy().getRawAxis(a2.getVal());

        MotorGroup_Feature fea= motor_feature.get("do_not_use_this_name_sichuanmianyang");

        drive.arcadeDrive(applyDeadband(-forward*fea.getDirection()*fea.getSpeed(),fea.getDeadband()),applyDeadband( turn*fea.getSpeed(),fea.getDeadband()));
    }

    /**
     * change direction of the target motor group
     * @param name the name of motor group you want to change direction
     * @param e the direction you want to set @see #Direction
     */
    public static void setDirection(String name, final Directions e){
       motor_feature.get(name).setDirection(e.getVal());
    }

    /**
     * set deadband to a specific motor group 
     * @param name name of the target motor group
     * @param val the deadband value
     */
    public static void setDeadband(String name,double val){
       motor_feature.get(name).setDeadband(val);
    }

    /**
    * set speed to a specific motor group 
     * @param name name of the target motor group
     * @param val the speed value
     */
    public static void setSpeed(String name, double val){
        try{
           if(val>=0d)
               motor_feature.get(name).setSpeed(val);
           else
               throw new InvalidValueException();    
        }
        catch(InvalidValueException e){

        }
    }

    /**
     * DON NOT USE METHOD 
     * it sets deadband to a default motorgroup
     * @param val the deadband value
     */
    public static void configure_drivebase_deadband(double val){
        setDeadband("do_not_use_this_name_sichuanmianyang", val);
    }

     /**
     * DON NOT USE METHOD 
     * it sets deadband to a default motorgroup
     * @param val the speed value
     */
    public static void configure_drivebase_speed(double val){
        setSpeed("do_not_use_this_name_sichuanmianyang", val);
    }

     /**
     * DON NOT USE METHOD 
     * it sets deadband to a default motorgroup
     * @param val the directions value
     */
    public static void configure_drivebase_direction(final Directions e){
        setDirection("do_not_use_this_name_sichuanmianyang", e);
    }


    /**
      * activate the target solenoid group after a button is pressed. 
      * @param n the name of the target solenoid group
      * @param j the joystick that contains activate button
      * @param b after this button the target solenoid is activated
      */
    public static void activeSoldeniodByPress(String solendoid_name, JoyStick j, Button b){
        for(Multi_Solenoid ele: solenoidsgroups){
           if(ele.getType().equals(solendoid_name)){
               if(j.getJoy().getRawButton(b.getVal())){
                   ele.turnOn();
               }
           }
        }
    }

    /**
      * activate the target solenoid group after a button is held. 
      * @param n the name of the target solenoid group
      * @param j the joystick that contains activate button
      * @param b after this button the target solenoid is activated
      */
    public static void activeSoldeniodByHeld(String solendoid_name, JoyStick j, Button b){
       for(Multi_Solenoid ele: solenoidsgroups){
          if(ele.getType().equals(solendoid_name)){
              if(j.getJoy().getRawButton(b.getVal())){
                  ele.turnOn();
              }else{
                  ele.turnOff();
              }
          }
       }
    }  
     
}