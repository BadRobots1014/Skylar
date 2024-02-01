// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.Constants.LimelightConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimelightSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  private final ShuffleboardTab m_tab = Shuffleboard.getTab("Limelight");
  GenericEntry customTagHeight;
  GenericEntry customCamHeight;
  GenericEntry customCamAngle;
  
  public LimelightSubsystem() {

    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    table.getEntry("pipeline").setNumber(2);
    // NetworkTableEntry tx = table.getEntry("tx");
    // NetworkTableEntry ty = table.getEntry("ty"); 
    // NetworkTableEntry ta = table.getEntry("ta");
    System.out.println("LIMELIGHT SUBSYSTEM");
    m_tab.addNumber("AprilTag ID: ", this::getAprilTagID);
    m_tab.addNumber("Tag tx", this::getTx);
    m_tab.addNumber("Tag ty", this::getTy);
    m_tab.addNumber("Tag ta", this::getTa);

    m_tab.addNumber("CameraPose X", this::getCameraPoseX);
    m_tab.addNumber("CameraPose Y", this::getCameraPoseY);
    m_tab.addNumber("CameraPose Z", this::getCameraPoseZ);
    
    m_tab.addNumber("CameraPose Roll", this::getCameraPoseRoll);
    m_tab.addNumber("CameraPose Pitch", this::getCameraPosePitch);
    m_tab.addNumber("CameraPose Yaw", this::getCameraPoseYaw);
    
    //Gets input from shuffleboard for the height an april tag for testing purposes
    customTagHeight = m_tab.add("Custom April Tag Height", 0).getEntry();

    //Gets input from shuffleboard for the height of the camera for testing purposes
    customCamHeight = m_tab.add("Custom Limelight Height", 0).getEntry();

    //Gets input from shuffleboard for the angle of the camera for testing purposes
    customCamAngle = m_tab.add("Custom Limelight Angle", 0).getEntry();

    m_tab.addNumber("April Tag Distance", this::getDistance);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }

  public double getAprilTagID(){
    NetworkTableEntry tID = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tid");
    double AprilTagID = tID.getDouble(0.0);
    return AprilTagID;
  }

  public double getTx(){
    double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0);
    return tx;
  }

  public double getTy(){
    double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0.0);
    return ty;
  }

  public double getTa(){
    double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0.0);
    return ta;
  }

  public double[] getCameraPose(){
    double[] camera3DPose = new double[6];
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camerapose_targetspace").getDoubleArray(camera3DPose);
    return camera3DPose;  
    //I beleive Translation (X,Y,Z) Rotation(Roll,Pitch,Yaw)
}

public double getCameraPoseX(){
    double[] camera3DPose = getCameraPose();
    return camera3DPose[0];
}

public double getCameraPoseY(){
    double[] camera3DPose = getCameraPose();
    return camera3DPose[1];
}

public double getCameraPoseZ(){
    double[] camera3DPose = getCameraPose();
    return camera3DPose[2];
}

public double getCameraPoseRoll(){
    double[] camera3DPose = getCameraPose();
    return camera3DPose[3];
}

public double getCameraPosePitch(){
    double[] camera3DPose = getCameraPose();
    return camera3DPose[4];
}

public double getCameraPoseYaw(){
    double[] camera3DPose = getCameraPose();
    return camera3DPose[5];
}

public double getTagHeight(){ //returns height of the viewed april tag in inches based off of their expected game field height
  double customHeight = customTagHeight.getDouble(0);
  if (customHeight != 0) return customHeight;
  double id = getAprilTagID();

  if (id == 1 || id == 2 || id == 5 || id == 6 || id == 9 || id == 10){
    return 53.38; //height of source and amp april tags
  }
  if (id == 3 || id == 4 || id == 7 || id == 8){
    return 57.13;
  }
  else {
    return 52.00; //remaining tags are for stage april tags
  }
}

public double getDistance(){
  double camHeight = customCamHeight.getDouble(0) == 0 ? LimelightConstants.kCamHeight : customCamHeight.getDouble(0);
  double camAngle = customCamAngle.getDouble(0) == 0 ? LimelightConstants.kCamAngle : customCamAngle.getDouble(0);
  double d = (getTagHeight() - camHeight) / Math.tan(Math.toRadians(getTy()+camAngle));
  return d;
}
  

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  
}