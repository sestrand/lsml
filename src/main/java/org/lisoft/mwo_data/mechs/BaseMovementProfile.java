/*
 * Li Song Mechlab - A 'mech building tool for PGI's MechWarrior: Online.
 * Copyright (C) 2013-2023  Li Song
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.lisoft.mwo_data.mechs;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import java.util.Collection;
import org.lisoft.mwo_data.modifiers.Attribute;
import org.lisoft.mwo_data.modifiers.Modifier;
import org.lisoft.mwo_data.modifiers.ModifierDescription;

/**
 * This class contains the movement parameters for a chassis.
 *
 * @author Li Song
 */
public class BaseMovementProfile implements MovementProfile {
  @XStreamAsAttribute private final MovementArchetype archetype;
  private final Attribute armTurnSpeedPitch;
  private final Attribute armTurnSpeedYaw;
  private final Attribute maxArmRotationPitch;
  private final Attribute maxArmRotationYaw;
  private final Attribute maxMovementSpeed;
  private final Attribute maxTorsoAnglePitch;
  private final Attribute maxTorsoAngleYaw;
  private final Attribute reverseSpeedMultiplier;
  private final Attribute torsoTurnSpeedPitch;
  private final Attribute torsoTurnSpeedYaw;
  private final Attribute turnLerpHighRate;
  private final Attribute turnLerpHighSpeed;
  private final Attribute turnLerpLowRate;
  private final Attribute turnLerpLowSpeed;
  private final Attribute turnLerpMidRate;
  private final Attribute turnLerpMidSpeed;

  public BaseMovementProfile(
      double aMaxMovementSpeed,
      double aReverseSpeedMultiplier,
      double aTorsoTurnSpeedYaw,
      double aTorsoTurnSpeedPitch,
      double aArmTurnSpeedYaw,
      double aArmTurnSpeedPitch,
      double aMaxTorsoAngleYaw,
      double aMaxTorsoAnglePitch,
      double aMaxArmRotationYaw,
      double aMaxArmRotationPitch,
      double aTurnLerpLowSpeed,
      double aTurnLerpMidSpeed,
      double aTurnLerpHighSpeed,
      double aTurnLerpLowRate,
      double aTurnLerpMidRate,
      double aTurnLerpHighRate,
      MovementArchetype aMovementArchetype) {
    maxMovementSpeed =
        new Attribute(aMaxMovementSpeed, ModifierDescription.SEL_MOVEMENT_MAX_FWD_SPEED);
    reverseSpeedMultiplier =
        new Attribute(aReverseSpeedMultiplier, ModifierDescription.SEL_MOVEMENT_MAX_REV_SPEED);

    torsoTurnSpeedYaw =
        new Attribute(
            aTorsoTurnSpeedYaw,
            ModifierDescription.SEL_MOVEMENT_TORSO,
            ModifierDescription.SPEC_MOVEMENT_YAW_SPEED);
    torsoTurnSpeedPitch =
        new Attribute(
            aTorsoTurnSpeedPitch,
            ModifierDescription.SEL_MOVEMENT_TORSO,
            ModifierDescription.SPEC_MOVEMENT_PITCH_SPEED);
    maxTorsoAngleYaw =
        new Attribute(
            aMaxTorsoAngleYaw,
            ModifierDescription.SEL_MOVEMENT_TORSO,
            ModifierDescription.SPEC_MOVEMENT_YAW_ANGLE);
    maxTorsoAnglePitch =
        new Attribute(
            aMaxTorsoAnglePitch,
            ModifierDescription.SEL_MOVEMENT_TORSO,
            ModifierDescription.SPEC_MOVEMENT_PITCH_ANGLE);

    armTurnSpeedYaw =
        new Attribute(
            aArmTurnSpeedYaw,
            ModifierDescription.SEL_MOVEMENT_ARM,
            ModifierDescription.SPEC_MOVEMENT_YAW_SPEED);
    armTurnSpeedPitch =
        new Attribute(
            aArmTurnSpeedPitch,
            ModifierDescription.SEL_MOVEMENT_ARM,
            ModifierDescription.SPEC_MOVEMENT_PITCH_SPEED);
    maxArmRotationYaw =
        new Attribute(
            aMaxArmRotationYaw,
            ModifierDescription.SEL_MOVEMENT_ARM,
            ModifierDescription.SPEC_MOVEMENT_YAW_ANGLE);
    maxArmRotationPitch =
        new Attribute(
            aMaxArmRotationPitch,
            ModifierDescription.SEL_MOVEMENT_ARM,
            ModifierDescription.SPEC_MOVEMENT_PITCH_ANGLE);

    turnLerpLowSpeed =
        new Attribute(
            aTurnLerpLowSpeed,
            ModifierDescription.SEL_MOVEMENT_TURN_SPEED,
            ModifierDescription.SPEC_LOW_RATE);
    turnLerpMidSpeed =
        new Attribute(
            aTurnLerpMidSpeed,
            ModifierDescription.SEL_MOVEMENT_TURN_SPEED,
            ModifierDescription.SPEC_MID_RATE);
    turnLerpHighSpeed =
        new Attribute(
            aTurnLerpHighSpeed,
            ModifierDescription.SEL_MOVEMENT_TURN_SPEED,
            ModifierDescription.SPEC_HIGH_RATE);
    turnLerpLowRate =
        new Attribute(
            aTurnLerpLowRate,
            ModifierDescription.SEL_MOVEMENT_TURN_RATE,
            ModifierDescription.SPEC_LOW_RATE);
    turnLerpMidRate =
        new Attribute(
            aTurnLerpMidRate,
            ModifierDescription.SEL_MOVEMENT_TURN_RATE,
            ModifierDescription.SPEC_MID_RATE);
    turnLerpHighRate =
        new Attribute(
            aTurnLerpHighRate,
            ModifierDescription.SEL_MOVEMENT_TURN_RATE,
            ModifierDescription.SPEC_HIGH_RATE);

    archetype = aMovementArchetype;
  }

  @Override
  public double getArmPitchMax(Collection<Modifier> aModifiers) {
    return maxArmRotationPitch.value(aModifiers);
  }

  @Override
  public double getArmPitchSpeed(Collection<Modifier> aModifiers) {
    return armTurnSpeedPitch.value(aModifiers);
  }

  @Override
  public double getArmYawMax(Collection<Modifier> aModifiers) {
    return maxArmRotationYaw.value(aModifiers);
  }

  @Override
  public double getArmYawSpeed(Collection<Modifier> aModifiers) {
    return armTurnSpeedYaw.value(aModifiers);
  }

  @Override
  public MovementArchetype getMovementArchetype() {
    return archetype;
  }

  @Override
  public double getReverseSpeedMultiplier(Collection<Modifier> aModifiers) {
    return reverseSpeedMultiplier.value(aModifiers);
  }

  @Override
  public double getSpeedFactor(Collection<Modifier> aModifiers) {
    return maxMovementSpeed.value(aModifiers);
  }

  @Override
  public double getTorsoPitchMax(Collection<Modifier> aModifiers) {
    return maxTorsoAnglePitch.value(aModifiers);
  }

  @Override
  public double getTorsoPitchSpeed(Collection<Modifier> aModifiers) {
    return torsoTurnSpeedPitch.value(aModifiers);
  }

  @Override
  public double getTorsoYawMax(Collection<Modifier> aModifiers) {
    return maxTorsoAngleYaw.value(aModifiers);
  }

  @Override
  public double getTorsoYawSpeed(Collection<Modifier> aModifiers) {
    return torsoTurnSpeedYaw.value(aModifiers);
  }

  @Override
  public double getTurnLerpHighRate(Collection<Modifier> aModifiers) {
    return turnLerpHighRate.value(aModifiers);
  }

  @Override
  public double getTurnLerpHighSpeed(Collection<Modifier> aModifiers) {
    return turnLerpHighSpeed.value(aModifiers);
  }

  @Override
  public double getTurnLerpLowRate(Collection<Modifier> aModifiers) {
    return turnLerpLowRate.value(aModifiers);
  }

  @Override
  public double getTurnLerpLowSpeed(Collection<Modifier> aModifiers) {
    return turnLerpLowSpeed.value(aModifiers);
  }

  @Override
  public double getTurnLerpMidRate(Collection<Modifier> aModifiers) {
    return turnLerpMidRate.value(aModifiers);
  }

  @Override
  public double getTurnLerpMidSpeed(Collection<Modifier> aModifiers) {
    return turnLerpMidSpeed.value(aModifiers);
  }
}
