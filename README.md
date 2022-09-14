# YahooStockDemo

a spring boot demo project

  run the spring boot server: mvnw spring-boot:run
  use this URL to get basic info: http://localhost:8080/

1. initialize data base: GET http://localhost:8080/stock/{stock}/history/{period}
  {stock} : stock symbol, e.g. AAPL
  {period} : time span for the history data, e.g. 1y, 30d
  sample request: http://localhost:8080/stock/AAPL/history/1y
  if data import into database success, this message returns:
  "Data initialized successfully!"

2. check history price and calculate the profit:
  POST http://localhost:8080/stock/{stock}?date={date}&money={invest}
  {stock} : stock symbol, e.g. AAPL
  {date} : long integer value of the selected Date
  {invest} : proposed money to buy stock with history price

  sample request: http://localhost:8080/stock/AAPL?date=1631491200000&money=100000
  sample response: 
  {
    "stock": "AAPL",
    "buy price": 148.72,
    "current price": 154.59,
    "cost": 100000,
    "latest value": 103881.59
  }
  
  Note: H2 in memory database is used in this demo.
