package utils;

import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import model.trade.Trade;

public final class TradeUtils {

	private TradeUtils(){
	}
	
	public static double calculateStockPrice(List<Trade> trades){
		double stockPrice = 0.0;
		int sumOfQuantity = 0;
		double sumOfTradePriceQuantity = 0.0;
		for(Trade currentTrade : trades){
			sumOfTradePriceQuantity += currentTrade.getTradePrice() * currentTrade.getTradeQuantityShares();
			sumOfQuantity += currentTrade.getTradeQuantityShares();
		}
		stockPrice = sumOfTradePriceQuantity / sumOfQuantity;
		return stockPrice;
		
	}
	
	public static double calculateGeometricMean(List<Trade> trades){
		double geometricMean = 0.0;
		double piMultiplied = 1.0;
		for(Trade currentTrade : trades){
			double yield = StockUtils.calculateDividendYield(currentTrade.getTradeStock());
			double pi = StockUtils.getPeRatio(currentTrade.getTradeStock(), yield);
			piMultiplied *= pi;
		}
		geometricMean = Math.pow(piMultiplied, 1.0 / trades.size());
		return geometricMean;
		
	}
	
	public static String jaxbObjectToXML(Trade trade) {
	    String xmlString = "";
	    try {
	        JAXBContext context = JAXBContext.newInstance(Trade.class);
	        Marshaller m = context.createMarshaller();

	        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); 

	        StringWriter sw = new StringWriter();
	        m.marshal(trade, sw);
	        xmlString = sw.toString();

	    } catch (JAXBException e) {
	        e.printStackTrace();
	    }

	    return xmlString;
	}
}
