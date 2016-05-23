package utils;

import model.stock.Stock;
import model.stock.StockType;

public final class StockUtils {

	private StockUtils() {
	}

	public static double calculateDividendYield(Stock stockModel) {
		double dividendYield = -1.0;
		if (stockModel.getTickerPrice() > 0.0) {
			if (StockType.COMMON.name().equals(stockModel.getStockType().name())) {
				dividendYield = stockModel.getLastDividend() / stockModel.getTickerPrice();
			} else {
				dividendYield = (stockModel.getFixedDividend() * stockModel.getParValue()) / stockModel.getTickerPrice();
			}
		}
		return dividendYield;
	}

	public static double getPeRatio(Stock stockModel, double dividendYield) {
		double peRatio = -1.0;
		if (stockModel.getTickerPrice() > 0.0) {
			peRatio = stockModel.getTickerPrice() / dividendYield;
		}
		return peRatio;
	}
}
