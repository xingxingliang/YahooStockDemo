package com.demo.stock.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
  
	Stock findByDate(long date);
  
//	@Query(value = "SELECT com.demo.stock.model.Stock (Max(s.date), s.close) FROM Stocks s")
//  Stock findLatestOne();

	//get latest record
	List<Stock> findFirst1ByOrderByDateDesc();

}