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
package org.lisoft.lsml.model.metrics;

import org.lisoft.mwo_data.equipment.Item;

/**
 * This is an other type of metric that performs calculations for a specific item on a loadout or
 * part.
 *
 * @author Li Song
 */
public interface ItemMetric {
  /**
   * Calculates the value of the metric. May employ caching but the caching must be transparent.
   *
   * @param aItem The {@link Item} to calculate the metric for.
   * @return The value of the metric.
   */
  double calculate(Item aItem);
}
