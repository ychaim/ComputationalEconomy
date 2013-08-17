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

package compecon.engine.dao.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;

import compecon.economy.markets.ordertypes.MarketOrder;
import compecon.economy.sectors.financial.Currency;
import compecon.economy.sectors.state.law.property.Property;
import compecon.engine.Agent;
import compecon.engine.dao.DAOFactory.IMarketOrderDAO;
import compecon.materia.GoodType;

public class MarketOrderDAO extends HibernateDAO<MarketOrder> implements
		IMarketOrderDAO {

	@SuppressWarnings("unchecked")
	@Override
	public void deleteAllSellingOrders(Agent offeror) {
		String hql = "FROM MarketOrder m WHERE m.offeror = :offeror";
		List<MarketOrder> marketOrders = getSession().createQuery(hql)
				.setEntity("offeror", offeror).list();
		for (MarketOrder marketOrder : marketOrders)
			this.delete(marketOrder);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteAllSellingOrders(Agent offeror, Currency currency,
			GoodType goodType) {
		String hql = "FROM MarketOrder m WHERE m.offeror = :offeror AND m.offerorsBankAcount.currency = :currency AND m.goodType = :goodType";
		List<MarketOrder> marketOrders = getSession().createQuery(hql)
				.setEntity("offeror", offeror)
				.setParameter("currency", currency)
				.setParameter("goodType", goodType).list();
		for (MarketOrder marketOrder : marketOrders)
			this.delete(marketOrder);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteAllSellingOrders(Agent offeror, Currency currency,
			Currency commodityCurrency) {
		String hql = "FROM MarketOrder m WHERE m.offeror = :offeror AND m.offerorsBankAcount.currency = :currency AND m.commodityCurrency = :commodityCurrency";
		List<MarketOrder> marketOrders = getSession().createQuery(hql)
				.setEntity("offeror", offeror)
				.setParameter("currency", currency)
				.setParameter("commodityCurrency", commodityCurrency).list();
		for (MarketOrder marketOrder : marketOrders)
			this.delete(marketOrder);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteAllSellingOrders(Agent offeror, Currency currency,
			Class<? extends Property> propertyClass) {
		String hql = "FROM MarketOrder m WHERE m.offeror = :offeror AND m.offerorsBankAcount.currency = :currency AND m.property.class = :propertyClass";
		List<MarketOrder> marketOrders = getSession().createQuery(hql)
				.setEntity("offeror", offeror)
				.setParameter("currency", currency)
				.setParameter("propertyClass", propertyClass.getSimpleName())
				.list();
		for (MarketOrder marketOrder : marketOrders)
			this.delete(marketOrder);
	}

	@Override
	public double findMarginalPrice(Currency currency, GoodType goodType) {
		String hql = "SELECT m.pricePerUnit FROM MarketOrder m "
				+ "WHERE m.offerorsBankAcount.currency = :currency AND m.goodType = :goodType ORDER BY pricePerUnit ASC";
		Object marginalPrice = getSession().createQuery(hql).setMaxResults(1)
				.setParameter("currency", currency)
				.setParameter("goodType", goodType).uniqueResult();
		if (marginalPrice == null)
			return Double.NaN;
		return (double) marginalPrice;
	}

	@Override
	public double findMarginalPrice(Currency currency,
			Currency commodityCurrency) {
		String hql = "SELECT m.pricePerUnit FROM MarketOrder m "
				+ "WHERE m.offerorsBankAcount.currency = :currency AND m.commodityCurrency = :commodityCurrency ORDER BY pricePerUnit ASC";
		Object marginalPrice = getSession().createQuery(hql).setMaxResults(1)
				.setParameter("currency", currency)
				.setParameter("commodityCurrency", commodityCurrency)
				.uniqueResult();
		if (marginalPrice == null)
			return Double.NaN;
		return (double) marginalPrice;
	}

	@Override
	public double findMarginalPrice(Currency currency,
			Class<? extends Property> propertyClass) {
		String hql = "SELECT m.pricePerUnit FROM MarketOrder m "
				+ " WHERE m.offerorsBankAcount.currency = :currency AND m.property.class = :propertyClass ORDER BY pricePerUnit ASC";
		Object marginalPrice = getSession().createQuery(hql).setMaxResults(1)
				.setParameter("currency", currency)
				.setParameter("propertyClass", propertyClass.getSimpleName())
				.uniqueResult();
		if (marginalPrice == null)
			return Double.NaN;
		return (double) marginalPrice;
	}

	@Override
	public Iterator<MarketOrder> getIterator(Currency currency,
			GoodType goodType) {
		String queryString = "FROM MarketOrder m "
				+ "WHERE m.offerorsBankAcount.currency = :currency AND m.goodType = :goodType "
				+ "ORDER BY m.pricePerUnit ASC";
		ScrollableResults itemCursor = getSession().createQuery(queryString)
				.setParameter("currency", currency)
				.setParameter("goodType", goodType)
				.scroll(ScrollMode.FORWARD_ONLY);
		return new HibernateIterator<MarketOrder>(itemCursor);
	}

	@Override
	public Iterator<MarketOrder> getIterator(Currency currency,
			Currency commodityCurrency) {
		String queryString = "FROM MarketOrder m "
				+ "WHERE m.offerorsBankAcount.currency = :currency AND m.commodityCurrency = :commodityCurrency "
				+ "ORDER BY m.pricePerUnit ASC";
		ScrollableResults itemCursor = getSession().createQuery(queryString)
				.setParameter("currency", currency)
				.setParameter("commodityCurrency", commodityCurrency)
				.scroll(ScrollMode.FORWARD_ONLY);
		return new HibernateIterator<MarketOrder>(itemCursor);
	}

	@Override
	public Iterator<MarketOrder> getIterator(Currency currency,
			Class<? extends Property> propertyClass) {
		String queryString = "FROM MarketOrder m "
				+ "WHERE m.offerorsBankAcount.currency = :currency AND m.property.class = :propertyClass "
				+ "ORDER BY m.pricePerUnit ASC";
		ScrollableResults itemCursor = getSession().createQuery(queryString)
				.setParameter("currency", currency)
				.setParameter("propertyClass", propertyClass.getSimpleName())
				.scroll(ScrollMode.FORWARD_ONLY);
		return new HibernateIterator<MarketOrder>(itemCursor);
	}
}