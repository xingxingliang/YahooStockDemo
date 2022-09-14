package com.demo.stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.demo.stock.model.Stock;
import com.demo.stock.model.StockRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

	// mvnw spring-boot:run

	@Autowired
	StockRepository stockRepository;

	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	// help method for logging
	private void log(String info) {
		System.out.println(info);
		log.debug(info);
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/")
	public String hello() {
		String help = "Yahoo stock Spring Boot demo" + "\n"
				+ "POST http://localhost:8080/stock/{AAPL}/history/{1y} initialize database with selected date range, e.g. 1y, 30d"
				+ "\n"
				+ "GET http://localhost:8080/stock/{AAPL}?date=1631491200000&money=149000 return profit for buy on selected date(integer)";
		return help;
	}

	@GetMapping(path="/stock/{symbol}", produces="application/json")
	public ResponseEntity getStockValue(@PathVariable String symbol, @RequestParam long date, @RequestParam int money) {

		try {
			log("in GET request ... ");

			Stock stock = stockRepository.findByDate(date);
			if (stock != null) {
				double boughtPrice = stock.getClose();
				int share = (int)(money/boughtPrice);

				log("Price:" + boughtPrice + ", Shares can be bought: " + share);

				// get the latest price
				List<Stock> latest = stockRepository.findFirst1ByOrderByDateDesc();
				double currentPrice = latest.get(0).getClose();
				log("Latest price:" + currentPrice + ", date: " + latest.get(0).getDate());
				double todayValue = currentPrice * share;

				// build json string, only return 2 decimal values for prices
				String response = "{\"stock\": \"" + symbol + "\",\"buy price\": " + String.format("%.2f", boughtPrice) + ",\"current price\":"
						+ String.format("%.2f", currentPrice) + ",\"cost\": " + money + ",\"latest value\":"
						+ String.format("%.2f", todayValue) + "}";
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("{\"Error\": \"no stock data found for " + symbol + "\"}", HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping(path = "/stock/{symbol}/history/{period}")
	public ResponseEntity loadHistoryPrice(@PathVariable String symbol, @PathVariable(required = false) String period) {

		log("Request received......");

		boolean success = initializeData(symbol, period);

		if (success) {
			log("Data initialized successfully!");
			return new ResponseEntity<>("Data initialized successfully!", HttpStatus.OK);
		} else {
			log("Data initialization failed :(");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private boolean initializeData(String symbol, String period) {

		// build request body
		String requestBody;
		if (period != null) {
			requestBody = "symbol=" + symbol + "&period=" + period;
		} else {
			requestBody = "symbol=" + symbol + "&period=1y"; // default to 1 year
		}

		// request url
		String url = "https://yahoo-finance97.p.rapidapi.com/price";

		// create an instance of RestTemplate
		RestTemplate restTemplate = new RestTemplate();

		// create headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		headers.set("X-RapidAPI-Key", "517c5415eamsh5f7f6f17ed3af27p1b9945jsn0df06f454ed2");
		headers.set("X-RapidAPI-Host", "yahoo-finance97.p.rapidapi.com");

		log("Request body:" + requestBody);

		// build the request
		HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

		// send POST request
		ResponseEntity<Response> response = restTemplate.postForEntity(url, entity, Response.class);

		// check response
		if (response.getStatusCode() == HttpStatus.OK) {
			log("Yahoo stock data received...");
			log("Total records: " + response.getBody().getData().length);

			Data[] data = response.getBody().getData();
			if (fillDatabase(data))
				return true;
			else
				return false;
		} else {
			log("Yahoo stock data request Failed :(");
			log("Response code: " + response.getStatusCode());

			return false;
		}

	}

	private boolean fillDatabase(Data[] stockData) {
		try {

			List<Stock> list = new ArrayList<>();
			for (Data data : stockData) {
				Stock stock = new Stock(data.getDate(), data.getOpen(), data.getClose(), data.getHigh(), data.getLow(),
						data.getVolume());
				list.add(stock);
			}
			if (!list.isEmpty()) {
				// use batch save
				stockRepository.saveAll(list);
			} else {
				log("empty data found, nothing added to database.");
				return false;
			}

			log("Stock data import into database success.");
			return true;

		} catch (Exception e) {

			log("Failed to add stock data input database!");
			return false;
		}
	}

}
