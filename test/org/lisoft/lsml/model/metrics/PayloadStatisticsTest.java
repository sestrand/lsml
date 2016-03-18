/*
 * @formatter:off
 * Li Song Mechlab - A 'mech building tool for PGI's MechWarrior: Online.
 * Copyright (C) 2013  Li Song
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
//@formatter:on
package org.lisoft.lsml.model.metrics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.lisoft.lsml.model.chassi.ChassisOmniMech;
import org.lisoft.lsml.model.chassi.ChassisStandard;
import org.lisoft.lsml.model.datacache.ChassisDB;
import org.lisoft.lsml.model.datacache.UpgradeDB;
import org.lisoft.lsml.model.upgrades.ArmorUpgrade;
import org.lisoft.lsml.model.upgrades.Upgrades;
import org.mockito.Mockito;

public class PayloadStatisticsTest {

    @Test
    public final void testOmniMech() {
        double strippedMass = 50;
        int maxMass = 100;
        int fixedHs = 8; // < 10
        ChassisOmniMech chassis = Mockito.mock(ChassisOmniMech.class);
        Mockito.when(chassis.getMassMax()).thenReturn(maxMass);
        Mockito.when(chassis.getFixedMass()).thenReturn(strippedMass);
        Mockito.when(chassis.getFixedHeatSinks()).thenReturn(fixedHs);

        PayloadStatistics cut = new PayloadStatistics(false, false, null);

        assertEquals(maxMass - strippedMass - (10 - fixedHs), cut.calculate(chassis), 0.0);
    }

    @Test
    public final void testOmniMech_lotsOfHeatsinks() {
        double strippedMass = 50;
        int maxMass = 100;
        int fixedHs = 15; // > 10
        ChassisOmniMech chassis = Mockito.mock(ChassisOmniMech.class);
        Mockito.when(chassis.getMassMax()).thenReturn(maxMass);
        Mockito.when(chassis.getFixedMass()).thenReturn(strippedMass);
        Mockito.when(chassis.getFixedHeatSinks()).thenReturn(fixedHs);

        PayloadStatistics cut = new PayloadStatistics(false, false, null);

        assertEquals(maxMass - strippedMass, cut.calculate(chassis), 0.0);
    }

    @Test
    public final void testOmniMech_MaxArmor() {
        int maxArmor = 300;
        double strippedMass = 50;
        int maxMass = 100;
        double armorMass = 10;
        int fixedHs = 8; // < 10

        ArmorUpgrade armorUpgrade = Mockito.mock(ArmorUpgrade.class);
        Mockito.when(armorUpgrade.getArmorMass(maxArmor)).thenReturn(armorMass);

        ChassisOmniMech chassis = Mockito.mock(ChassisOmniMech.class);
        Mockito.when(chassis.getMassMax()).thenReturn(maxMass);
        Mockito.when(chassis.getFixedMass()).thenReturn(strippedMass);
        Mockito.when(chassis.getFixedHeatSinks()).thenReturn(fixedHs);
        Mockito.when(chassis.getArmorMax()).thenReturn(maxArmor);
        Mockito.when(chassis.getFixedArmorType()).thenReturn(armorUpgrade);

        PayloadStatistics cut = new PayloadStatistics(false, true, null);

        assertEquals(maxMass - strippedMass - (10 - fixedHs) - armorMass, cut.calculate(chassis), 0.0);
    }

    @Test
    public final void testChangeUseXLEngine() throws Exception {
        ChassisStandard jm6_a = (ChassisStandard) ChassisDB.lookup("JM6-A");
        Upgrades upgrades = Mockito.mock(Upgrades.class);
        Mockito.when(upgrades.getStructure()).thenReturn(UpgradeDB.IS_STD_STRUCTURE);
        Mockito.when(upgrades.getArmor()).thenReturn(UpgradeDB.IS_STD_ARMOR);
        PayloadStatistics cut = new PayloadStatistics(false, false, upgrades);

        cut.changeUseXLEngine(true);

        assertEquals(46.0, cut.calculate(jm6_a, 250), 0.0);
    }

    @Test
    public final void testChangeUseMaxArmor() throws Exception {
        ChassisStandard jm6_a = (ChassisStandard) ChassisDB.lookup("JM6-A");
        Upgrades upgrades = Mockito.mock(Upgrades.class);
        Mockito.when(upgrades.getStructure()).thenReturn(UpgradeDB.IS_STD_STRUCTURE);
        Mockito.when(upgrades.getArmor()).thenReturn(UpgradeDB.IS_STD_ARMOR);
        PayloadStatistics cut = new PayloadStatistics(false, false, upgrades);

        cut.changeUseMaxArmor(true);

        assertEquals(26.81, cut.calculate(jm6_a, 250), 0.01);
    }

    @Test
    public final void testChangeUpgrades() throws Exception {
        ChassisStandard jm6_a = (ChassisStandard) ChassisDB.lookup("JM6-A");
        Upgrades upgrades = Mockito.mock(Upgrades.class);
        PayloadStatistics cut = new PayloadStatistics(false, false, upgrades);
        Upgrades upgradesNew = Mockito.mock(Upgrades.class);

        Mockito.when(upgrades.getArmor()).thenReturn(UpgradeDB.IS_STD_ARMOR);
        Mockito.when(upgradesNew.getStructure()).thenReturn(UpgradeDB.IS_ES_STRUCTURE);

        cut.changeUpgrades(upgradesNew);

        assertEquals(43.0, cut.calculate(jm6_a, 250), 0.0);
    }

    @Test
    public final void testCalculate_smallEngine() throws Exception {
        ChassisStandard jm6_a = (ChassisStandard) ChassisDB.lookup("JM6-A");
        Upgrades upgrades = Mockito.mock(Upgrades.class);
        Mockito.when(upgrades.getStructure()).thenReturn(UpgradeDB.IS_STD_STRUCTURE);
        Mockito.when(upgrades.getArmor()).thenReturn(UpgradeDB.IS_STD_ARMOR);

        PayloadStatistics cut = new PayloadStatistics(false, false, upgrades);
        assertEquals(45.0, cut.calculate(jm6_a, 200), 0.0); // Needs two additional heat sinks
        assertEquals(44.0, cut.calculate(jm6_a, 205), 0.0); // Needs two additional heat sinks
        assertEquals(42.5, cut.calculate(jm6_a, 220), 0.0); // Needs two additional heat sinks
    }

    @Test
    public final void testCalculate() throws Exception {
        ChassisStandard jm6_a = (ChassisStandard) ChassisDB.lookup("JM6-A");
        Upgrades upgrades = Mockito.mock(Upgrades.class);
        Mockito.when(upgrades.getStructure()).thenReturn(UpgradeDB.IS_STD_STRUCTURE);
        Mockito.when(upgrades.getArmor()).thenReturn(UpgradeDB.IS_STD_ARMOR);

        PayloadStatistics cut = new PayloadStatistics(false, false, upgrades);
        assertEquals(40.0, cut.calculate(jm6_a, 250), 0.0);
        assertEquals(33.5, cut.calculate(jm6_a, 300), 0.0);
    }

    @Test
    public final void testCalculate_xl() throws Exception {
        ChassisStandard jm6_a = (ChassisStandard) ChassisDB.lookup("JM6-A");
        Upgrades upgrades = Mockito.mock(Upgrades.class);
        Mockito.when(upgrades.getStructure()).thenReturn(UpgradeDB.IS_STD_STRUCTURE);
        Mockito.when(upgrades.getArmor()).thenReturn(UpgradeDB.IS_STD_ARMOR);

        PayloadStatistics cut = new PayloadStatistics(true, false, upgrades);
        assertEquals(46.0, cut.calculate(jm6_a, 250), 0.0);
        assertEquals(43.0, cut.calculate(jm6_a, 300), 0.0);
    }

    @Test
    public final void testCalculate_maxArmor() throws Exception {
        ChassisStandard jm6_a = (ChassisStandard) ChassisDB.lookup("JM6-A");
        Upgrades upgrades = Mockito.mock(Upgrades.class);
        Mockito.when(upgrades.getStructure()).thenReturn(UpgradeDB.IS_STD_STRUCTURE);
        Mockito.when(upgrades.getArmor()).thenReturn(UpgradeDB.IS_STD_ARMOR);

        PayloadStatistics cut = new PayloadStatistics(false, true, upgrades);
        assertEquals(26.81, cut.calculate(jm6_a, 250), 0.01);
        assertEquals(20.31, cut.calculate(jm6_a, 300), 0.01);
    }

    @Test
    public final void testCalculate_ferroMaxArmor() throws Exception {
        ChassisStandard jm6_a = (ChassisStandard) ChassisDB.lookup("JM6-A");
        Upgrades upgrades = Mockito.mock(Upgrades.class);
        Mockito.when(upgrades.getArmor()).thenReturn(UpgradeDB.IS_FF_ARMOR);
        Mockito.when(upgrades.getStructure()).thenReturn(UpgradeDB.IS_STD_STRUCTURE);

        PayloadStatistics cut = new PayloadStatistics(false, true, upgrades);
        assertEquals(28.23, cut.calculate(jm6_a, 250), 0.01);
        assertEquals(21.73, cut.calculate(jm6_a, 300), 0.01);
    }

    @Test
    public final void testCalculate_endo() throws Exception {
        ChassisStandard jm6_a = (ChassisStandard) ChassisDB.lookup("JM6-A");
        Upgrades upgrades = Mockito.mock(Upgrades.class);
        Mockito.when(upgrades.getStructure()).thenReturn(UpgradeDB.IS_ES_STRUCTURE);
        Mockito.when(upgrades.getArmor()).thenReturn(UpgradeDB.IS_STD_ARMOR);

        PayloadStatistics cut = new PayloadStatistics(false, false, upgrades);
        assertEquals(43.0, cut.calculate(jm6_a, 250), 0.0);
        assertEquals(36.5, cut.calculate(jm6_a, 300), 0.0);
    }
}