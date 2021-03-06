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

package compecon;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import compecon.economy.sectors.financial.CreditBankTest;
import compecon.economy.sectors.household.HouseholdImplTest;
import compecon.economy.sectors.industry.FactoryImplTest;
import compecon.engine.applicationcontext.InterdependenciesConfigurationTest;
import compecon.engine.applicationcontext.NoDependenciesConfigurationTest;
import compecon.engine.applicationcontext.TestingConfigurationTest;
import compecon.engine.dao.BankAccountDAOTest;
import compecon.engine.dao.HouseholdDAOTest;
import compecon.engine.dao.PropertyDAOTest;
import compecon.engine.service.AgentServiceTest;
import compecon.engine.service.MarketServiceTest;
import compecon.engine.service.PropertyServiceTest;
import compecon.math.CESFunctionTest;
import compecon.math.CobbDouglasFunctionTest;
import compecon.math.intertemporal.ModiglianiIntertemporalConsumptionFunctionTest;
import compecon.math.production.CobbDouglasProductionFunctionTest;
import compecon.math.util.MathUtilTest;
import compecon.math.utility.CobbDouglasUtilityFunctionTest;

@RunWith(Suite.class)
@SuiteClasses({ MathUtilTest.class, InterdependenciesConfigurationTest.class,
		NoDependenciesConfigurationTest.class, TestingConfigurationTest.class,
		BankAccountDAOTest.class, HouseholdDAOTest.class,
		PropertyDAOTest.class, AgentServiceTest.class, MarketServiceTest.class,
		PropertyServiceTest.class, CreditBankTest.class,
		HouseholdImplTest.class, FactoryImplTest.class,
		ModiglianiIntertemporalConsumptionFunctionTest.class,
		CobbDouglasFunctionTest.class, CobbDouglasUtilityFunctionTest.class,
		CobbDouglasProductionFunctionTest.class, CESFunctionTest.class })
public class CompEconTestSuite {
}
