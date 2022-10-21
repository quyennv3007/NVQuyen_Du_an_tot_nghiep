package com.convenience_store.api.admin;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.convenience_store.repository.StatisticsRepo;
import com.convenience_store.util.XDate;
import com.fasterxml.jackson.databind.JsonNode;

@CrossOrigin
@RestController
@RequestMapping("/v1/api/admin/statistics")
public class Statistics_Admin {
	
	@Autowired private StatisticsRepo statisticsRepo;
	@Autowired private XDate xdate;
	
	@GetMapping("firstRow")
	public Map<String,Object>firstRow(){
		Map<String,Object> map = new HashMap<>();
		map.put("todayOrder",statisticsRepo.getTodayOrders());
		map.put("totalOrder",statisticsRepo.getTotalOrders());
		map.put("available", statisticsRepo.getAvailable());
		map.put("totalProduct", statisticsRepo.getTotalProduct());
		map.put("todayIncome",statisticsRepo.getTodayIncome());
		map.put("totalIncome", statisticsRepo.getTotalIncome());
		map.put("totalCustomer", statisticsRepo.getTotalCustomer());
		map.put("totalAccount", statisticsRepo.getTotalAccount());
		return map;
	}
	
	@GetMapping("secondRow")
	public Map<String,List<Object[]>> showFirstChart(){
		Map<String,List<Object[]>> map = new HashMap<>();
		map.put("firstBarChart",statisticsRepo.getNumberOfProductSoldByCategory());
		return map;
	}
	
	@PostMapping("secondRow/firstBarChartDateRange")
	public Map<String,List<Object[]>> refillFirstChart(@RequestBody JsonNode dateRange) throws ParseException{
		String startDate = xdate.dateConverter(dateRange.get("startDate").asText(),"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String endDate = xdate.dateConverter(dateRange.get("endDate").asText(),"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
//		System.out.println(xdate.dateConverter(startDate, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		Map<String,List<Object[]>> map = new HashMap<>();
		map.put("firstBarChart",statisticsRepo.getNumberOfProductSoldByCategory(startDate,endDate));
		return map;
	}
	@GetMapping("secondRow/firstLineChartData")
	public Map<String,List<Object[]>> firstBarChartData(){
		Map<String,List<Object[]>> map = new HashMap<>();
		map.put("firstLineChart",statisticsRepo.getIncomeByDate());
		return map;
	}
	
	@GetMapping("thirdRow")
	public Map<String,List<Object[]>>firstPieChart(){
		Map<String,List<Object[]>> map = new HashMap<>();
		map.put("firstBarChart",statisticsRepo.getInvoiceByStatus());
		return map;
	}
	
	@GetMapping("thirdRow/importedByCate")
	public Map<String,List<Object[]>>importedByCateOverTheTime(){
		Map<String,List<Object[]>> map = new HashMap<>();
		map.put("firstStackedBarChart",statisticsRepo.getImportedByCateOverTheTime());
		return map;
	}
	
	@GetMapping("thirdRow/importTimeGroupBy")
	public String[] importedByCateTimeGroupBy(){
		return statisticsRepo.getImportedTimeGroupBy();
	}
	
	@GetMapping("thirdRow/importExpenseByCate")
	public Map<String,List<Object[]>>importedExpenseByCate(){
		Map<String,List<Object[]>> map = new HashMap<>();
		map.put("firstSideBySideStackBar",statisticsRepo.getImportedExpenseByCateOverTheTime());
		return map;
	}
	
	@GetMapping("fourthRow/expenseAndRevenueOverTheTime")
	public Map<String,List<Object[]>>expenseAndRevenueOverTheTime(){
		Map<String,List<Object[]>> map = new HashMap<>();
		map.put("firstSpineChartExpense",statisticsRepo.getExpenseOverTheTime());
		map.put("firstSpineChartRevenue",statisticsRepo.getIncomeByDate());
		return map;
	}
	
	@GetMapping("fourthRow/top10ProductSold")
	public Map<String,List<Object[]>>top10ProductSold(){
		Map<String,List<Object[]>> map = new HashMap<>();
		map.put("secondBarChart",statisticsRepo.getTop10Sold());
		return map;
	}
	
	@PostMapping("fourthRow/top10ProductSoldByDateRange")
	public Map<String,List<Object[]>>top10ProductSoldByDateRange(@RequestBody JsonNode dateRange) throws ParseException{
		String startDate = xdate.dateConverter(dateRange.get("startDate").asText(),"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		String endDate = xdate.dateConverter(dateRange.get("endDate").asText(),"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Map<String,List<Object[]>> map = new HashMap<>();
		map.put("secondBarChart",statisticsRepo.getTop10SoldByDateRange(startDate,endDate));
		return map;
	}
	
	@GetMapping("fifthRow/companyPositionLevel")
	public Map<String,List<Object[]>>companyPositionLevel(){
		Map<String,List<Object[]>> map = new HashMap<>();
		map.put("firstPyramidChart",statisticsRepo.getCompanyPositionLevel());
		return map;
	}
	
	@GetMapping("fifthRow/productRating")
	public Map<String,List<Object[]>>productRating(){
		Map<String,List<Object[]>> map = new HashMap<>();
		map.put("firstMultipleAxesChart",statisticsRepo.getProductRating());
		return map;
	}
	//date range to be merged api
	@PostMapping("dateRange")
	public String[] getDateRange(@RequestBody String range) {
		if(range.equalsIgnoreCase("week")) {
			return statisticsRepo.getDateRangeByWeek();
		}
		if(range.equalsIgnoreCase("fourteen")) {
			return statisticsRepo.getDateRangeBy14Days();
		}
		if(range.equalsIgnoreCase("thirty")) {
			return statisticsRepo.getDateRangeBy30Days();
		}
		return null;
	}
	
	@GetMapping("topCustomer")
	public Map<String,List<Object[]>>topCustomer(){
		Map<String,List<Object[]>> map = new HashMap<>();
		map.put("topCustomer",statisticsRepo.getTopCustomer());
		return map;
	}
	
}
