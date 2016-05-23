package model.trade;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import model.stock.Stock;

@XmlRootElement
public class Trade {
	private Date tradeTimestamp;
	private TradeIndicator tradeIndicator = TradeIndicator.BUY;
	private int tradeQuantityShares;
	private double tradePrice = 0.0;
	private Stock tradeStock;

	public Date getTradeTimestamp() {
		return tradeTimestamp;
	}

	public void setTradeTimestamp(Date tradeTimestamp) {
		this.tradeTimestamp = tradeTimestamp;
	}

	public TradeIndicator getTradeIndicator() {
		return tradeIndicator;
	}

	public void setTradeIndicator(TradeIndicator tradeIndicator) {
		this.tradeIndicator = tradeIndicator;
	}

	public int getTradeQuantityShares() {
		return tradeQuantityShares;
	}

	public void setTradeQuantityShares(int tradeQuantityShares) {
		this.tradeQuantityShares = tradeQuantityShares;
	}

	public double getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(double tradePrice) {
		this.tradePrice = tradePrice;
	}

	public Stock getTradeStock() {
		return tradeStock;
	}

	public void setTradeStock(Stock tradeStock) {
		this.tradeStock = tradeStock;
	}

}
