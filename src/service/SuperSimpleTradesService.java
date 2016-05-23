package service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import model.stock.Stock;
import model.trade.Trade;
import model.trade.TradeIndicator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.TradeUtils;

public class SuperSimpleTradesService {
	
	public List<Trade> getTrades() {
		List<Trade> trades = new ArrayList<Trade>();
		SuperSimpleStocksService stockService = new SuperSimpleStocksService();
		try {
			File fXmlFile = new File("../JPMorganStocks/src/resources/trades.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("trade");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.MINUTE, -15);
			Date lastFifteenMins = cal.getTime();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					Trade currentTrade = new Trade();
					String date = eElement.getElementsByTagName("timestamp").item(0).getTextContent();
					Date tradeDate = df.parse(date);
					if(lastFifteenMins.before(tradeDate)){
						currentTrade.setTradeTimestamp(tradeDate);
						currentTrade.setTradeIndicator(TradeIndicator.valueOf(eElement.getElementsByTagName("tradeIndicator").item(0).getTextContent()));
						currentTrade.setTradeQuantityShares(Integer.valueOf(eElement.getElementsByTagName("tradeQuantityShares").item(0).getTextContent()));
						currentTrade.setTradePrice(Double.valueOf(eElement.getElementsByTagName("tradePrice").item(0).getTextContent()));
						currentTrade.setTradeStock(stockService.getStock(eElement));
						trades.add(currentTrade);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return trades;
	}
	
	public void recordNewTrade(String xmlSource) throws IOException {
		java.io.FileWriter fw = new java.io.FileWriter("../JPMorganStocks/src/resources/recordNewTrade.xml");
		fw.write(xmlSource);
		fw.close();
	}

	public static void main(String argv[]) {
		//iii.	record a trade, with timestamp, quantity of shares, buy or sell indicator and price to recordNewTrade.xml
		Trade newTrade = new Trade();
		newTrade.setTradeTimestamp(new Date());
		newTrade.setTradeIndicator(TradeIndicator.BUY);
		newTrade.setTradePrice(13.0);
		newTrade.setTradeQuantityShares(100);
		Stock tradeStock = new Stock();
		newTrade.setTradeStock(tradeStock);
		String tradeToString = TradeUtils.jaxbObjectToXML(newTrade);
		SuperSimpleTradesService tradeService = new SuperSimpleTradesService();
		try {
			tradeService.recordNewTrade(tradeToString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(tradeToString);
		
		//iv.	Calculate Stock Price based on trades recorded in past 15 minutes
		List<Trade> stock = tradeService.getTrades();
		System.out.println("Stock Price based on trades recorded in past 15 minutes: " + TradeUtils.calculateStockPrice(stock));
		
		//b.	Calculate the GBCE All Share Index using the geometric mean of prices for all stocks
		System.out.println("geometric mean of prices: " + TradeUtils.calculateGeometricMean(stock));
		
	}
}
