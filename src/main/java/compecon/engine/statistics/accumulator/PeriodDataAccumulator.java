/*
Copyright (C) 2013 u.wol@wwu.de

This file is part of ComputationalEconomy.

ComputationalEconomy is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

ComputationalEconomy is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with ComputationalEconomy. If not, see <http://www.gnu.org/licenses/>.
 */

package compecon.engine.statistics.accumulator;

public class PeriodDataAccumulator {

	double sumPerPeriod = 0;

	public void add(final double amount) {
		sumPerPeriod += amount;
	}

	public double getAmount() {
		return sumPerPeriod;
	}

	/**
	 * Reset values to zero
	 */
	public void reset() {
		sumPerPeriod = 0;
	}
}