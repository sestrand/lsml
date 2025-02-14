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
import java.util.List;
import org.lisoft.lsml.model.UpgradeDB;
import org.lisoft.mwo_data.Faction;
import org.lisoft.mwo_data.equipment.Engine;
import org.lisoft.mwo_data.equipment.Item;
import org.lisoft.mwo_data.equipment.JumpJet;
import org.lisoft.mwo_data.equipment.Upgrade;
import org.lisoft.mwo_data.modifiers.Modifier;

/**
 * This class represents a bare, non-configured inner sphere Mech chassis. This class is immutable.
 *
 * @author Li Song
 */
public class ChassisStandard extends Chassis {
  @XStreamAsAttribute private final int engineMax;
  @XStreamAsAttribute private final int engineMin;
  @XStreamAsAttribute private final int maxJumpJets;
  private final Collection<Modifier> quirks;

  /**
   * Creates a new {@link ChassisStandard}.
   *
   * @param aMwoID The MWO ID of the chassis as found in the XML.
   * @param aMwoName The MWO name of the chassis as found in the XML.
   * @param aSeries The name of the series for example "ORION" or "JENNER".
   * @param aName The long name of the mech, for example "JENNER JR7-F".
   * @param aShortName The short name of the mech, for example "JR7-F".
   * @param aMaxTons The maximum tonnage of the mech.
   * @param aVariant The variant type of the mech, like hero, champion etc.
   * @param aBaseVariant The base chassisID that this chassis is based on if any, -1 if not based on
   *     any chassis.
   * @param aMovementProfile The {@link MovementProfile} of this chassis.
   * @param aFaction The {@link Faction} this chassis belongs to.
   * @param aEngineMin The smallest engine rating that can be equipped.
   * @param aEngineMax The largest engine rating that can be equipped.
   * @param aMaxJumpJets The maximal number of jump jets that can be equipped.
   * @param aComponents An array of {@link ComponentStandard} that defines the internal components
   *     of the chassis.
   * @param aQuirks The chassis quirks for this chassis.
   * @param aMascCapable Whether this chassis is MASC capable.
   */
  public ChassisStandard(
      int aMwoID,
      String aMwoName,
      String aSeries,
      String aName,
      String aShortName,
      int aMaxTons,
      ChassisVariant aVariant,
      int aBaseVariant,
      MovementProfile aMovementProfile,
      Faction aFaction,
      int aEngineMin,
      int aEngineMax,
      int aMaxJumpJets,
      ComponentStandard[] aComponents,
      Collection<Modifier> aQuirks,
      boolean aMascCapable) {
    super(
        aMwoID,
        aMwoName,
        aSeries,
        aName,
        aShortName,
        aMaxTons,
        aVariant,
        aBaseVariant,
        aMovementProfile,
        aFaction,
        aComponents,
        aMascCapable);
    engineMin = aEngineMin;
    engineMax = aEngineMax;
    maxJumpJets = aMaxJumpJets;
    quirks = aQuirks;
  }

  @Override
  public boolean canUseUpgrade(Upgrade aUpgrade) {
    if (aUpgrade.getFaction().isCompatible(getFaction())) {
      if (aUpgrade == UpgradeDB.IS_STEALTH_ARMOUR) {
        return getHardPointsCount(HardPointType.ECM) > 0;
      }
      return true;
    }
    return super.canUseUpgrade(aUpgrade);
  }

  @Override
  public ComponentStandard getComponent(Location aLocation) {
    return (ComponentStandard) super.getComponent(aLocation);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Collection<ComponentStandard> getComponents() {
    return (Collection<ComponentStandard>) super.getComponents();
  }

  /**
   * @return The largest engine rating that this chassis can support.
   */
  public int getEngineMax() {
    return engineMax;
  }

  /**
   * @return The smallest engine rating required to move this chassis.
   */
  public int getEngineMin() {
    return engineMin;
  }

  /**
   * @param aHardPointType The type of hard points to count.
   * @return The number of hard points of the given type.
   */
  public int getHardPointsCount(HardPointType aHardPointType) {
    int sum = 0;
    for (final ComponentStandard part : getComponents()) {
      sum += part.getHardPointCount(aHardPointType);
    }
    return sum;
  }

  /**
   * @return The maximal number of jump jets the chassis can support.
   */
  public int getJumpJetsMax() {
    return maxJumpJets;
  }

  /**
   * @return A {@link List} of all the {@link Modifier} on the chassis.
   */
  public Collection<Modifier> getQuirks() {
    return quirks;
  }

  @Override
  public boolean isAllowed(Item aItem) {
    if (aItem instanceof final Engine engine) {
      if (engine.getRating() < getEngineMin() || engine.getRating() > getEngineMax()) {
        return false;
      }
    } else if (aItem instanceof JumpJet && getJumpJetsMax() <= 0) {
      return false;
    }
    return super.isAllowed(aItem);
  }
}
