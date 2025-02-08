// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.TestConstants;

public class TestMotorSubsystem extends SubsystemBase {
    
  private WPI_TalonSRX m1 = new WPI_TalonSRX(TestConstants.kMotorOnePort);
  private WPI_TalonSRX m2 = new WPI_TalonSRX(TestConstants.kMotorTwoPort);

  ShuffleboardTab m_tab;
  GenericEntry m1Speed;
  GenericEntry m2Speed;
  GenericEntry m1BrakeMotor;
  GenericEntry m2BrakeMotor;

  /** Creates a new ShooterSubsystem. */
  public TestMotorSubsystem() {

    m1.setNeutralMode(NeutralMode.Coast);
    m1.setNeutralMode(NeutralMode.Coast);

    m2.setInverted(false);
    m2.setInverted(false);

    
    m_tab = Shuffleboard.getTab("Test Motors");

    m1Speed = m_tab.add("Motor One Speed", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    m2Speed = m_tab.add("Motor Two Speed", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    m1BrakeMotor = m_tab.add("Motor One Brake", false).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
    m2BrakeMotor = m_tab.add("Motor Two Brake", false).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();

  }

  public void setMotorType(NeutralMode type) {
    m1.setNeutralMode(type);
    m2.setNeutralMode(type);
  }

  public void setM1(double speed) {
    m1.set(speed);
  }
  public void setM2(double speed) {
    m2.set(speed);
  }

  public void setM1Brake(boolean breakMotor) {
    if (breakMotor) {
        m1.setNeutralMode(NeutralMode.Brake);
    } else {
        m1.setNeutralMode(NeutralMode.Coast);
    }
  }
  public void setM2Brake(boolean breakMotor) {
    if (breakMotor) {
        m2.setNeutralMode(NeutralMode.Brake);
    } else {
        m2.setNeutralMode(NeutralMode.Coast);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    setM1(m1Speed.getDouble(0));
    setM2(m1Speed.getDouble(0));
    setM1Brake(m1BrakeMotor.getBoolean(false));
    setM2Brake(m2BrakeMotor.getBoolean(false));
  }

  public void stop() {
    m1.stopMotor();
    m2.stopMotor();
  }
}
