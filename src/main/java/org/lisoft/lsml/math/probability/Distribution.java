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
package org.lisoft.lsml.math.probability;

/**
 * This interface models a probability distribution.
 * 
 * @author Li Song
 */
public interface Distribution {
    /**
     * Calculates the value of the Probability Density Function (PDF) for the the given value.
     * 
     * @param x
     *            The value to calculate the probability density for.
     * @return The probability density for the argument.
     */
    public double pdf(double x);

    /**
     * Calculates the Cumulative Density Function (CDF) for the given value.
     * 
     * @param x
     *            The value to calculate the cumulative density for.
     * @return The cumulative density value for the argumetn.
     */
    public double cdf(double x);
}