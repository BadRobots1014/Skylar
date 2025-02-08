// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.TestMotorSubsystem;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class TestMotorControllerCommand extends Command {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final TestMotorSubsystem m_subsystem;

  ShuffleboardTab m_tab;
  GenericEntry m1Speed;
  GenericEntry m2Speed;
  GenericEntry m1BrakeMotor;
  GenericEntry m2BrakeMotor;

  public TestMotorControllerCommand(TestMotorSubsystem subsystem) {
    m_subsystem = subsystem;

    m_tab = Shuffleboard.getTab("Test Motors");

    m1Speed = m_tab.add("Motor One Speed", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    m1Speed = m_tab.add("Motor One Speed", 0).withWidget(BuiltInWidgets.kNumberSlider).getEntry();
    m1BrakeMotor = m_tab.add("Motor Type", false).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
     m1BrakeMotor = m_tab.add("Motor Type", false).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_subsystem.setM1(m1Speed.getDouble(0));
    m_subsystem.setM2(m1Speed.getDouble(0));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_subsystem.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
