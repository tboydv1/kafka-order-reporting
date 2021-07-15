`Srpingboot` `Java` `Mysql` `Kafka`


**Sales Reporting  Application in Java using Spring Boot (RestController)**


This is a sales report service, that consumes sales order message from Kafka, and persist data to database for retrieval


**Postman** ==> https://documenter.getpostman.com/view/10973323/TzmBBt5n




**Setup**

Clone project 

`git clone <repo url>`

Install Maven Dependencies

`mvn install`

_This application listens to kafka events on localhost:9092. Ensure Kafka is running locally_


Setup Database

`cd into the src/main/resources/db folder
`

`run the setup.sql in mysql client`


**Use Cases**

_Get all Reports grouped By date_
This endpoint takes no parameters, and returns all consumed order message reports

    @GetMapping("/")
    public ResponseEntity<?> getAllReport(){

        Map<LocalDate, ApiResponse> finalResult = reportService.getAllReportDataByDate();
        return ResponseEntity.ok().body(finalResult);
    }    
    
    
    
_Get all Reports grouped By date range filter_
This endpoint takes no parameters, and returns all consumed order message reports

     @GetMapping("/data-range")
      public ResponseEntity<?> getReportByDateRange(@Valid @RequestBody ReportRequestDto reportRequestDto){
  
  
          Map<LocalDate, ApiResponse> finalResult = reportService.getAllReportDataByDateRange(reportRequestDto);
          return ResponseEntity.ok().body(finalResult);
      }
      
Request Body

`{
   "from": "2021-07-10",
   "to": "2021-11-15"
 }`
 
 


    
    

    



