package com.demo.stock.model;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class DataModelUnitTestClass {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	StockRepository repository;

	// help method for logging
	private void log(String info) {
		System.out.println(info);		
	}

	@Test
	public void testNoStocksIfRepositoryIsEmpty() {
		
		List<Stock> stocks = repository.findFirst1ByOrderByDateDesc();
		assertThat(stocks).isEmpty();
	}

	@Test
	public void testFindStockByDate() {
		Stock stock1 = new Stock(1631491200000l, 149.79, 148.71, 150.57, 147.92, 100000);
		entityManager.persist(stock1);

		Stock stock2 = new Stock(1663027200000l, 159.89, 154.58, 160.53, 154.55, 120000);
		entityManager.persist(stock2);

		Stock foundStock = repository.findByDate(stock1.getDate());

		assertThat(foundStock).isEqualTo(stock1);
	}
	
	@Test
	public void testFindLatestStock() {
		Stock stock1 = new Stock(1631491200000l, 149.79, 148.71, 150.57, 147.92, 100000);
		entityManager.persist(stock1);

		Stock stock2 = new Stock(1663027200000l, 159.89, 154.58, 160.53, 154.55, 120000);
		entityManager.persist(stock2);

		List<Stock> foundStock = repository.findFirst1ByOrderByDateDesc();

		assertThat(foundStock.size()).isEqualTo(1);
		
		assertThat(foundStock.get(0)).isEqualTo(stock2);	
	}

}