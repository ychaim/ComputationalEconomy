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

package compecon.engine.dao;

import java.util.Iterator;

import compecon.economy.agent.Agent;
import compecon.economy.markets.MarketOrder;
import compecon.economy.property.Property;
import compecon.economy.sectors.financial.Currency;
import compecon.materia.GoodType;

public interface MarketOrderDAO extends GenericDAO<MarketOrder> {

	public void deleteAllSellingOrders(Agent offeror);

	public void deleteAllSellingOrders(Agent offeror, Currency currency,
			GoodType goodType);

	public void deleteAllSellingOrders(Agent offeror, Currency currency,
			Currency commodityCurrency);

	public void deleteAllSellingOrders(Agent offeror, Currency currency,
			Class<? extends Property> propertyClass);

	public double findMarginalPrice(Currency currency, GoodType goodType);

	public double findMarginalPrice(Currency currency,
			Currency commodityCurrency);

	public double findMarginalPrice(Currency currency,
			Class<? extends Property> propertyClass);

	public Iterator<MarketOrder> getIterator(Currency currency,
			GoodType goodType);

	public Iterator<MarketOrder> getIterator(Currency currency,
			Currency commodityCurrency);

	public Iterator<MarketOrder> getIterator(Currency currency,
			Class<? extends Property> propertyClass);

	public Iterator<MarketOrder> getIteratorThreadsafe(Currency currency,
			GoodType goodType);

	public Iterator<MarketOrder> getIteratorThreadsafe(Currency currency,
			Currency commodityCurrency);

	public double getAmountSum(Currency currency, GoodType goodType);

	public double getAmountSum(Currency currency, Currency commodityCurrency);
}