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
package org.lisoft.mwo_data;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import java.util.Collection;
import org.lisoft.mwo_data.modifiers.Attribute;
import org.lisoft.mwo_data.modifiers.Modifier;
import org.lisoft.mwo_data.modifiers.ModifierDescription;

/**
 * This class represents the model of an environment for mechs. The environment can affect a Mech's
 * behavior.
 *
 * @author Li Song
 */
public class Environment {
  public static final Environment NEUTRAL = new Environment("Neutral", 0.0);

  private final Attribute heat;
  @XStreamAsAttribute private final String name;

  /**
   * Creates a new {@link Environment} with the given name and heat value.
   *
   * @param aName The name of the environment.
   * @param aHeat The heat level of the environment.
   */
  public Environment(String aName, double aHeat) {
    name = aName;
    heat = new Attribute(aHeat, ModifierDescription.SEL_HEAT_EXTERNAL_TRANSFER);
  }

  /**
   * Will return the base heat penalty for an environment.
   *
   * @param aModifiers The modifiers to apply to the environmental heat.
   * @return A <code>double</code> that is a heat dissipation penalty to apply to the mech. A number
   *     &lt 0 means the environment cools the mech.
   */
  public double getHeat(Collection<Modifier> aModifiers) {
    return heat.value(aModifiers);
  }

  /**
   * @return The human-readable name of the environment.
   */
  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return getName();
  }
}
