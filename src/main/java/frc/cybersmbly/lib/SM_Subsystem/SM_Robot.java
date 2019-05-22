package frc.cybersmbly.lib.SM_Subsystem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import java.util.ArrayList;
import java.util.HashMap;

import frc.cybersmbly.lib.SM_Electronics.*;
import frc.cybersmbly.until.functions.MotorFunction;

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
    public SM_Robot(){
        motorsgroups=new ArrayList<Multi_Motor>();
        solenoidsgroups =new ArrayList<Multi_Solenoid>();
        j1=new Joystick(0);
        j2=new Joystick(1);
    }

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
    public static void setInverse(String n,boolean isInverted){
        
       for(int i=0; i<motorsgroups.size();i++){
           if(motorsgroups.get(i).getType().equalsIgnoreCase(n)){
                motorsgroups.get(i).setInverted(isInverted);
                break;
           }
       }
    }

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

    public static void movePercent(String n, JoyStick j, Axis a){
        for(Multi_Motor ele: motorsgroups){
            if(ele.getType().equals(n)){
                ele.set(applyDeadband(a.getVal()*motor_feature.get(n).getDirection()*motor_feature.get(n).getSpeed(),motor_feature.get(n).getDeadband()));
                System.out.println(j.getJoy().getRawAxis(a.getVal()));
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

    public void moveDistance(String n,JoyStick j, Button b,double distance){
        if(j.getJoy().getRawButton(b.getVal())){
            moveDistance(n,distance);
        }
    }
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
     public static void turnOff(String n){
        
        for(int i=0; i<solenoidsgroups.size();i++){
            if(solenoidsgroups.get(i).getType().equalsIgnoreCase(n)){
                for(int i2=0;i2<solenoidsgroups.get(i).getSolenoidList().size();i2++){
                    solenoidsgroups.get(i).getSolenoidList().get(i2).turnOff();
                }
                 break;
            }
        }
     }
     public void turnOff(String n, JoyStick j, Button b){
         if(j.getJoy().getRawButton(b.getVal())){
             turnOff(n);
         }
     }

     public void turnOn(String n, JoyStick j, Button b){
        if(j.getJoy().getRawButton(b.getVal())){
            turnOn(n);
        }
    }

    public static void setRightAndLeft(String right, String left){
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
        motor_feature.put("do_not_use_this_name_四川绵阳",new MotorGroup_Feature());    
        //System.out.println(motorsgroups.get(righti).numberOfMotors()+"\n"+motorsgroups.get(lefti).numberOfMotors());
        drive=new DifferentialDrive(motorsgroups.get(righti), motorsgroups.get(lefti));
    }
    
     public static void arcadeDrive(JoyStick j, Axis a1,Axis a2){
       
        
        double forward, turn;
       // forward=turn=0;
        forward=j.getJoy().getRawAxis(a1.getVal());
        turn=j.getJoy().getRawAxis(a2.getVal());

        MotorGroup_Feature fea= motor_feature.get("do_not_use_this_name_四川绵阳");

        drive.arcadeDrive(applyDeadband(-forward*fea.getDirection()*fea.getSpeed(),fea.getDeadband()),applyDeadband( turn*fea.getSpeed(),fea.getDeadband()));
       // System.out.println(forward+" "+turn);
     }

     public static void setDirection(String name, final Directions e){
        motor_feature.get(name).setDirection(e.getVal());
     }
     public static void setDeadband(String name,double val){
        motor_feature.get(name).setDeadband(val);
     }
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
    public static void configure_drivebase_deadband(double val){
        setDeadband("do_not_use_this_name_四川绵阳", val);
    }
    public static void configure_drivebase_speed(double val){
        setSpeed("do_not_use_this_name_四川绵阳", val);
    }
    public static void configure_drivebase_direction(final Directions e){
        setDirection("do_not_use_this_name_四川绵阳", e);
    }

     public static void activeSoldeniodByPress(String solendoid_name, JoyStick j, Button b){
         for(Multi_Solenoid ele: solenoidsgroups){
            if(ele.getType().equals(solendoid_name)){
                if(j.getJoy().getRawButton(b.getVal())){
                    ele.turnOn();
                }
            }
         }
     }

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