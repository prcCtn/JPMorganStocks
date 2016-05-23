package service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import model.stock.Stock;
import model.stock.StockType;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utils.StockUtils;

public class SuperSimpleStocksService {

	public Stock getStock(Element eElement){
		Stock stock = new Stock();
		stock.setStockSymbol(eElement.getElementsByTagName("stockSymbol").item(0).getTextContent());
		stock.setStockType(StockType.valueOf(eElement.getElementsByTagName("stockType").item(0).getTextContent()));
		stock.setLastDividend(Double.valueOf(eElement.getElementsByTagName("lastDividend").item(0).getTextContent()));
		stock.setStockSymbol(eElement.getElementsByTagName("stockSymbol").item(0).getTextContent());
		stock.setFixedDividend(Double.valueOf(eElement.getElementsByTagName("fixedDividend").item(0).getTextContent()));
		stock.setParValue(Double.valueOf(eElement.getElementsByTagName("parValue").item(0).getTextContent()));
		stock.setTickerPrice(Double.valueOf(eElement.getElementsByTagName("tickerPrice").item(0).getTextContent()));
		return stock;
	}
	
	public List<Stock> getStocks(){
		List<Stock> stocks = new ArrayList<Stock>();
		try {
			File fXmlFile = new File("../JPMorganStocks/src/resources/gobalBeverageCorporation.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("stock");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					stocks.add(getStock(eElement));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stocks;
	}
	
	public static void main(String argv[]) {
		/**
		 * i.	calculate the dividend yield
		   ii.	calculate the P/E Ratio

		 */
		SuperSimpleStocksService service = new SuperSimpleStocksService();
		List<Stock> stocks = service.getStocks();
		for(Stock currentStock : stocks) {
			System.out.println("--------------- ");
			System.out.println("Stock Symbol: " + currentStock.getStockSymbol());
			double yield = StockUtils.calculateDividendYield(currentStock);
			System.out.println("Dividend Yield: " + yield);
			System.out.println("Pe Ratio: " + StockUtils.getPeRatio(currentStock, yield));
		}
		
	}
}
