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

package compecon.economy.security.debt;

import compecon.economy.property.PropertyIssued;
import compecon.economy.sectors.financial.BankAccountDelegate;
import compecon.economy.sectors.financial.Currency;

public interface Bond extends PropertyIssued {

	public void deconstruct();

	public double getFaceValue();

	public BankAccountDelegate getFaceValueFromBankAccountDelegate();

	public BankAccountDelegate getFaceValueToBankAccountDelegate();

	public Currency getIssuedInCurrency();

	public int getTermInYears();

	public void setFaceValue(final double faceValue);

	public void setFaceValueFromBankAccountDelegate(
			final BankAccountDelegate faceValueFromBankAccountDelegate);

	public void setFaceValueToBankAccountDelegate(
			final BankAccountDelegate faceValueToBankAccountDelegate);

	public void setIssuedInCurrency(final Currency issuedInCurrency);

	public void setTermInYears(final int termInYears);

}