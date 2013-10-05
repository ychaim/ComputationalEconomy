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

package compecon.engine.dashboard.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.DefaultHighLowDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;

import compecon.economy.sectors.financial.Currency;
import compecon.economy.sectors.state.law.bookkeeping.BalanceSheet;
import compecon.engine.Simulation;
import compecon.engine.dashboard.panel.BalanceSheetPanel.BalanceSheetTableModel;
import compecon.engine.statistics.model.ModelRegistry.IncomeSource;
import compecon.engine.statistics.model.NotificationListenerModel.IModelListener;
import compecon.engine.statistics.model.PeriodDataDistributionModel.SummaryStatisticalData;
import compecon.engine.statistics.model.PricesModel;
import compecon.engine.statistics.model.PricesModel.PriceModel;
import compecon.materia.GoodType;

public class HouseholdsPanel extends AbstractChartsPanel implements
		IModelListener {

	public class HouseholdsPanelForCurrency extends JPanel implements
			IModelListener {

		protected final Currency currency;

		protected JFreeChart incomeDistributionChart;

		protected JPanel priceTimeSeriesPanel;

		protected JPanel marketDepthPanel;

		public HouseholdsPanelForCurrency(Currency currency) {
			this.currency = currency;

			this.setLayout(new GridLayout(0, 3));

			// panelForCurrency.add(AgentsPanel.createAgentNumberPanel(currency,
			// Household.class));
			this.add(createLabourPanel(currency));
			this.add(createUtilityPanel(currency));
			this.add(createIncomeConsumptionSavingPanel(currency));
			this.add(createConsumptionSavingRatePanel(currency));
			this.add(createWageDividendPanel(currency));
			this.add(createIncomeSourcePanel(currency));
			this.incomeDistributionChart = createIncomeDistributionPanel(currency);
			this.add(new ChartPanel(incomeDistributionChart));
			this.add(createLorenzCurvePanel(currency));
			this.add(createHouseholdBalanceSheetPanel(currency));

			Simulation.getInstance().getModelRegistry()
					.getIncomeDistributionModel(currency)
					.registerListener(this);
			// no registration with the price and market depth model, as they
			// call listeners synchronously

			notifyListener();
		}

		@Override
		public void notifyListener() {
			if (this.isShowing()) {
				/*
				 * income distribution chart
				 */

				XYPlot plot = ((XYPlot) incomeDistributionChart.getPlot());
				plot.setDataset(Simulation.getInstance().getModelRegistry()
						.getIncomeDistributionModel(currency)
						.getHistogramDataset());

				plot.clearDomainMarkers();
				SummaryStatisticalData summaryStatisticalData = Simulation
						.getInstance().getModelRegistry()
						.getIncomeDistributionModel(currency)
						.getSummaryStatisticalData();
				if (summaryStatisticalData.originalValues != null
						&& summaryStatisticalData.originalValues.length > 0) {
					addValueMarker(
							incomeDistributionChart,
							summaryStatisticalData.originalValues[summaryStatisticalData.xWith10PercentY],
							"10 %");
					addValueMarker(
							incomeDistributionChart,
							summaryStatisticalData.originalValues[summaryStatisticalData.xWith20PercentY],
							"20 %");
					addValueMarker(
							incomeDistributionChart,
							summaryStatisticalData.originalValues[summaryStatisticalData.xWith30PercentY],
							"30 %");
					addValueMarker(
							incomeDistributionChart,
							summaryStatisticalData.originalValues[summaryStatisticalData.xWith40PercentY],
							"40 %");
					addValueMarker(
							incomeDistributionChart,
							summaryStatisticalData.originalValues[summaryStatisticalData.xWith50PercentY],
							"50 %");
					addValueMarker(
							incomeDistributionChart,
							summaryStatisticalData.originalValues[summaryStatisticalData.xWith60PercentY],
							"60 %");
					addValueMarker(
							incomeDistributionChart,
							summaryStatisticalData.originalValues[summaryStatisticalData.xWith70PercentY],
							"70 %");
					addValueMarker(
							incomeDistributionChart,
							summaryStatisticalData.originalValues[summaryStatisticalData.xWith80PercentY],
							"80 %");
					addValueMarker(
							incomeDistributionChart,
							summaryStatisticalData.originalValues[summaryStatisticalData.xWith90PercentY],
							"90 %");
				}

				/*
				 * price panel & market depth panel
				 */
				if (this.priceTimeSeriesPanel != null)
					this.remove(this.priceTimeSeriesPanel);
				this.priceTimeSeriesPanel = createPriceTimeSeriesChartPanel(currency);
				this.add(this.priceTimeSeriesPanel);

				if (this.marketDepthPanel != null)
					this.remove(this.marketDepthPanel);
				this.marketDepthPanel = createMarketDepthPanel(currency);
				this.add(this.marketDepthPanel);

				validate();
				repaint();
			}
		}
	}

	JTabbedPane jTabbedPaneCurrency = new JTabbedPane();

	public HouseholdsPanel() {
		this.setLayout(new BorderLayout());

		for (Currency currency : Currency.values()) {
			HouseholdsPanelForCurrency panelForCurrency = new HouseholdsPanelForCurrency(
					currency);
			jTabbedPaneCurrency.addTab(currency.getIso4217Code(),
					panelForCurrency);
			panelForCurrency.setBackground(Color.lightGray);
		}

		jTabbedPaneCurrency.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (e.getSource() instanceof JTabbedPane) {
					JTabbedPane pane = (JTabbedPane) e.getSource();
					HouseholdsPanelForCurrency selectedComponent = (HouseholdsPanelForCurrency) pane
							.getSelectedComponent();
					selectedComponent.notifyListener();
				}
			}
		});

		add(jTabbedPaneCurrency, BorderLayout.CENTER);
	}

	protected ChartPanel createLabourPanel(Currency currency) {
		TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();

		timeSeriesCollection.addSeries(Simulation.getInstance()
				.getModelRegistry()
				.getGoodTypeProductionModel(currency, GoodType.LABOURHOUR)
				.getOutputModel().getTimeSeries());
		timeSeriesCollection.addSeries(Simulation.getInstance()
				.getModelRegistry().getLabourHourCapacityModel(currency)
				.getTimeSeries());

		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				GoodType.LABOURHOUR.toString() + " Output", "Date",
				"Capacity & Output", (XYDataset) timeSeriesCollection, true,
				true, false);
		configureChart(chart);
		return new ChartPanel(chart);
	}

	protected ChartPanel createUtilityPanel(Currency currency) {
		TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();

		timeSeriesCollection.addSeries(Simulation.getInstance()
				.getModelRegistry().getUtilityModel(currency).getOutputModel()
				.getTimeSeries());

		for (GoodType inputGoodType : Simulation.getInstance()
				.getModelRegistry().getUtilityModel(currency)
				.getInputGoodTypes()) {
			timeSeriesCollection.addSeries(Simulation.getInstance()
					.getModelRegistry().getUtilityModel(currency)
					.getInputModel(inputGoodType).getTimeSeries());
		}

		JFreeChart chart = ChartFactory.createTimeSeriesChart("Utility",
				"Date", "Total Utility", (XYDataset) timeSeriesCollection,
				true, true, false);
		configureChart(chart);
		return new ChartPanel(chart);
	}

	protected ChartPanel createIncomeConsumptionSavingPanel(Currency currency) {
		TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();

		timeSeriesCollection.addSeries(Simulation.getInstance()
				.getModelRegistry().getIncomeModel(currency).getTimeSeries());
		timeSeriesCollection.addSeries(Simulation.getInstance()
				.getModelRegistry().getConsumptionModel(currency)
				.getTimeSeries());
		timeSeriesCollection.addSeries(Simulation.getInstance()
				.getModelRegistry().getSavingModel(currency).getTimeSeries());

		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Consumption & Saving", "Date", "Consumption & Saving",
				timeSeriesCollection, true, true, false);
		configureChart(chart);
		return new ChartPanel(chart);
	}

	protected ChartPanel createConsumptionSavingRatePanel(Currency currency) {
		TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();

		timeSeriesCollection.addSeries(Simulation.getInstance()
				.getModelRegistry().getConsumptionRateModel(currency)
				.getTimeSeries());
		timeSeriesCollection.addSeries(Simulation.getInstance()
				.getModelRegistry().getSavingRateModel(currency)
				.getTimeSeries());

		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Consumption & Saving Rate", "Date",
				"Consumption & Saving Rate", timeSeriesCollection, true, true,
				false);
		configureChart(chart);
		return new ChartPanel(chart);
	}

	protected ChartPanel createWageDividendPanel(Currency currency) {
		TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();

		timeSeriesCollection.addSeries(Simulation.getInstance()
				.getModelRegistry().getWageModel(currency).getTimeSeries());
		timeSeriesCollection.addSeries(Simulation.getInstance()
				.getModelRegistry().getDividendModel(currency).getTimeSeries());

		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Wage & Dividend", "Date", "Wage & Dividend",
				timeSeriesCollection, true, true, false);
		configureChart(chart);
		return new ChartPanel(chart);
	}

	protected ChartPanel createIncomeSourcePanel(Currency currency) {
		TimeSeriesCollection timeSeriesCollection = new TimeSeriesCollection();

		for (IncomeSource incomeSource : Simulation.getInstance()
				.getModelRegistry().getIncomeSourceModel(currency)
				.getIndexTypes()) {
			timeSeriesCollection.addSeries(Simulation.getInstance()
					.getModelRegistry().getIncomeSourceModel(currency)
					.getTimeSeries(incomeSource));
		}

		JFreeChart chart = ChartFactory.createTimeSeriesChart("Income Source",
				"Date", "Income Source", timeSeriesCollection, true, true,
				false);
		configureChart(chart);
		return new ChartPanel(chart);
	}

	protected JFreeChart createIncomeDistributionPanel(Currency currency) {
		IntervalXYDataset dataset = Simulation.getInstance().getModelRegistry()
				.getIncomeDistributionModel(currency).getHistogramDataset();
		JFreeChart incomeDistributionChart = ChartFactory.createHistogram(
				"Income Distribution", "Income", "% Households at Income",
				dataset, PlotOrientation.VERTICAL, true, false, false);
		return incomeDistributionChart;
	}

	protected void addValueMarker(JFreeChart chart, double position,
			String label) {
		ValueMarker marker = new ValueMarker(position);
		marker.setPaint(Color.black);
		marker.setLabel(label);
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.addDomainMarker(marker);
	}

	protected ChartPanel createLorenzCurvePanel(Currency currency) {
		XYDataset dataset = Simulation.getInstance().getModelRegistry()
				.getIncomeDistributionModel(currency).getLorenzCurveDataset();
		JFreeChart lorenzCurveChart = ChartFactory.createXYLineChart(
				"Lorenz Curve", "% of Households", "% of Income", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		return new ChartPanel(lorenzCurveChart);
	}

	protected JPanel createHouseholdBalanceSheetPanel(Currency currency) {
		final BalanceSheetTableModel balanceSheetTableModel = new BalanceSheetTableModel(
				currency) {
			@Override
			protected BalanceSheet getModelData() {
				return Simulation.getInstance().getModelRegistry()
						.getBalanceSheetsModel(referenceCurrency)
						.getHouseholdNationalAccountsBalanceSheet();
			}
		};
		return new BalanceSheetPanel(currency, balanceSheetTableModel,
				"Balance Sheet for " + currency.getIso4217Code()
						+ " Households");
	}

	protected ChartPanel createPriceTimeSeriesChartPanel(Currency currency) {
		JFreeChart priceChart = ChartFactory.createCandlestickChart(
				"Price Chart for " + GoodType.LABOURHOUR, "Time", "Price in "
						+ currency.getIso4217Code(),
				this.getDefaultHighLowDataset(currency), false);
		ChartPanel chartPanel = new ChartPanel(priceChart);
		chartPanel.setDomainZoomable(true);
		chartPanel.setPreferredSize(new java.awt.Dimension(800, 400));
		return chartPanel;
	}

	protected ChartPanel createMarketDepthPanel(Currency currency) {
		XYDataset dataset = Simulation.getInstance().getModelRegistry()
				.getMarketDepthModel(currency)
				.getMarketDepthDataset(currency, GoodType.LABOURHOUR);
		JFreeChart chart = ChartFactory.createXYStepAreaChart(
				"Market Depth for " + GoodType.LABOURHOUR, "Price", "Volume",
				dataset, PlotOrientation.VERTICAL, true, true, false);
		return new ChartPanel(chart);
	}

	protected DefaultHighLowDataset getDefaultHighLowDataset(Currency currency) {
		PricesModel pricesModel = Simulation.getInstance().getModelRegistry()
				.getPricesModel(currency);
		if (pricesModel.getPriceModelsForGoodTypes().containsKey(currency)) {
			Map<GoodType, PriceModel> priceModelsForGoodType = pricesModel
					.getPriceModelsForGoodTypes().get(currency);
			PriceModel priceModel = priceModelsForGoodType
					.get(GoodType.LABOURHOUR);
			if (priceModel != null)
				return new DefaultHighLowDataset("", priceModel.getDate(),
						priceModel.getHigh(), priceModel.getLow(),
						priceModel.getOpen(), priceModel.getClose(),
						priceModel.getVolume());
		}
		return null;
	}

	@Override
	public void notifyListener() {
		if (this.isShowing()) {
			HouseholdsPanelForCurrency householdPanel = (HouseholdsPanelForCurrency) jTabbedPaneCurrency
					.getSelectedComponent();
			householdPanel.notifyListener();
		}
	}
}