package com.convenience_store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.convenience_store.entity.Product;

@Repository
public interface StatisticsRepo extends CrudRepository<Product, Long>{
	
	//First Row Content
	@Query(value="Select count(*) from invoice i where i.date = CAST( GETDATE() AS Date)",nativeQuery=true)
	Long getTodayOrders();

	@Query(value="Select count(*) from invoice",nativeQuery=true)
	Long getTotalOrders();

	@Query(value="Select count(*) from detailedProduct dtp where dtp.available = 1",nativeQuery=true)
	Long getAvailable();

	@Query(value="Select count(*) from detailedProduct",nativeQuery=true)
	Long getTotalProduct();
		
	@Query(value="Select Isnull(sum(i.totalPaymentNet),0) from invoice i where i.date = CAST( GETDATE() AS Date) and i.status = N'Paid'",nativeQuery=true)
	Double getTodayIncome();
	
	@Query(value="Select Isnull(sum(i.totalPaymentNet),0) from invoice i where i.status = N'Paid'",nativeQuery=true)
	Double getTotalIncome();
	
	@Query(value="Select count(a.roleID)  "
			+ "from users u  "
			+ "inner join authority a on u.id = a.userID "
			+ "where a.roleID = N'CSM'",nativeQuery=true)
	Long getTotalCustomer();
	
	@Query(value="Select count(*) from users u",nativeQuery=true)
	Long getTotalAccount();
	//First Row Content Edge
	
	
	//Second Row Content	
	@Query(value="Select distinct "
			+ "c.name as name, Isnull(sum(dti.quantity),0) as value "
			+ "from category c  "
			+ "inner join categorySub cs on c.id = cs.categoryID "
			+ "left join product p on p.categorySubID = cs.id "
			+ "left join detailedProduct dtp on dtp.productID = p.id "
			+ "left join detailedInvoice dti on dti.detailedProductID = dtp.id "
			+ "left join invoice i on dti.invoiceID = i.id "
			+ "where i.status = N'Paid' "
			+ "Group by c.name order by value desc",nativeQuery=true)
	List<Object[]>getNumberOfProductSoldByCategory();

	@Query(value="Select distinct "
			+ "c.name as name, Isnull(sum(dti.quantity),0) as value "
			+ "from category c  "
			+ "inner join categorySub cs on c.id = cs.categoryID "
			+ "left join product p on p.categorySubID = cs.id "
			+ "left join detailedProduct dtp on dtp.productID = p.id "
			+ "left join detailedInvoice dti on dti.detailedProductID = dtp.id "
			+ "left join invoice i on dti.invoiceID = i.id "
			+ "where i.date between ?1 and ?2 and i.status = N'Paid' "
			+ "Group by c.name Order By value desc",nativeQuery=true)
	List<Object[]> getNumberOfProductSoldByCategory(String startDate,String endDate);

	@Query(value=";WITH cte "
			+ "     AS (SELECT Cast(dateadd(day,-7,Getdate()) AS DATE) AS dates "
			+ "         UNION ALL "
			+ "         SELECT Dateadd(day, 1, dates) "
			+ "         FROM   cte "
			+ "         WHERE  dates < cast(Getdate() as date)) "
			+ "SELECT a.dates AS [Date] "
			+ "FROM   cte a "
			+ "       LEFT JOIN invoice qa "
			+ "              ON a.dates = Cast(qa.date AS DATE) "
			+ "GROUP  BY a.Dates  "
			+ "ORDER  BY a.Dates ",nativeQuery=true)
	String[] getDateRangeByWeek();

	@Query(value=";WITH cte "
			+ "     AS (SELECT Cast(dateadd(day,-14,Getdate()) AS DATE) AS dates "
			+ "         UNION ALL "
			+ "         SELECT Dateadd(day, 1, dates) "
			+ "         FROM   cte "
			+ "         WHERE  dates < cast(Getdate() as date)) "
			+ "SELECT a.dates AS [Date] "
			+ "FROM   cte a "
			+ "       LEFT JOIN invoice qa "
			+ "              ON a.dates = Cast(qa.date AS DATE) "
			+ "GROUP  BY a.Dates  "
			+ "ORDER  BY a.Dates ",nativeQuery=true)
	String[] getDateRangeBy14Days();
	
	@Query(value=";WITH cte "
			+ "     AS (SELECT Cast(dateadd(day,-30,Getdate()) AS DATE) AS dates "
			+ "         UNION ALL "
			+ "         SELECT Dateadd(day, 1, dates) "
			+ "         FROM   cte "
			+ "         WHERE  dates < cast(Getdate() as date)) "
			+ "SELECT a.dates AS [Date] "
			+ "FROM   cte a "
			+ "       LEFT JOIN invoice qa "
			+ "              ON a.dates = Cast(qa.date AS DATE) "
			+ "GROUP  BY a.Dates  "
			+ "ORDER  BY a.Dates ",nativeQuery=true)
	String[] getDateRangeBy30Days();
	
	@Query(value="Select i.date as date, sum(i.totalPaymentNet) as income "
			+ "from invoice i "
			+ "where i.status = N'Paid' "
			+ "Group by i.date "
			+ "Order by i.date",nativeQuery=true)
	List<Object[]> getIncomeByDate();
	//Second Row Content Edge
	
	//Third Row Content
	@Query(value="Select  "
			+ "i.status as status, "
			+ "round(cast((Cast(count(*) as decimal(36,2))/Cast((select count(*) from invoice) as decimal(36,2))) as numeric(36,2))*100 ,1) "
			+ "as percentage "
			+ "from invoice i "
			+ "group by status",nativeQuery=true)
	List<Object[]> getInvoiceByStatus();

	@Query(value="Select "
			+ "i.date ,c.id as category , sum(info.quantityImport) as quantityImport "
			+ "from category c  "
			+ "inner join categorySub cs on c.id = cs.categoryID "
			+ "inner join product p on p.categorySubID = cs.id "
			+ "inner join importInfo info on p.id = info.productID "
			+ "inner join import i on info.importID = i.id "
			+ "Group by c.id,i.date "
			+ "order by i.date",nativeQuery=true)
	List<Object[]> getImportedByCateOverTheTime();

	@Query(value="Select i.date as date from import i "
			+ "Group by i.date "
			+ "order by i.date",nativeQuery=true)
	String[] getImportedTimeGroupBy();
	
	@Query(value="Select "
			+ "i.date ,c.id as category , sum(info.quantityImport*priceImport) as quantityImport "
			+ "from category c  "
			+ "inner join categorySub cs on c.id = cs.categoryID "
			+ "inner join product p on p.categorySubID = cs.id "
			+ "inner join importInfo info on p.id = info.productID "
			+ "inner join import i on info.importID = i.id "
			+ "Group by c.id,i.date "
			+ "order by i.date",nativeQuery=true)
	List<Object[]> getImportedExpenseByCateOverTheTime();
	//Third Row Content Edge


	//Fourth Row Content

	@Query(value="Select i.date, sum(info.priceImport*info.quantityImport) as expense "
			+ "from import i  "
			+ "inner join importInfo info on i.id = info.importID "
			+ "group by i.date "
			+ "order by i.date",nativeQuery=true)
	List<Object[]> getExpenseOverTheTime();
	
	
	@Query(value="Select top 5 p.name,sum(dti.quantity) as total "
			+ "from invoice i "
			+ "inner join detailedInvoice dti on i.id = dti.invoiceID "
			+ "inner join detailedProduct dtp on dtp.id = dti.detailedProductID "
			+ "inner join product p on p.id = dtp.productID "
			+ "where i.status = N'Paid' "
			+ "group by p.name "
			+ "order by total desc "
			+ "",nativeQuery=true)
	List<Object[]> getTop10Sold();
	
	@Query(value="Select top 5 p.name,sum(dti.quantity) as total "
			+ "from invoice i "
			+ "inner join detailedInvoice dti on i.id = dti.invoiceID "
			+ "inner join detailedProduct dtp on dtp.id = dti.detailedProductID "
			+ "inner join product p on p.id = dtp.productID "
			+ "where i.date between ?1 and ?2 and i.status = N'Paid' "
			+ "group by p.name "
			+ "order by total desc "
			+ "",nativeQuery=true)
	List<Object[]> getTop10SoldByDateRange(String startDate, String endDate);


	//Fourth Row Content Edge
	
	
	//Fifth Row Content
	
	@Query(value="select count(*) as count,r.name as role "
			+ "from role r  "
			+ "inner join authority au on r.id = au.roleID "
			+ "group by r.name  "
			+ "Order by case when r.name = 'admin' then 0 else 1 end desc,  "
			+ "		 case when r.name = 'manager' then 0 else 1 end desc, "
			+ "		 case when r.name = 'shipper' then 0 else 1 end desc, "
			+ "		 case when r.name = 'customer' then 0 else 1 end desc, "
			+ "		 case when r.name = 'guest' then 0 else 1 end desc,r.name",nativeQuery=true)
	List<Object[]> getCompanyPositionLevel();

	
	@Query(value="Select p.id,p.name,  "
			+ "cast(AVG(r.star) as decimal(36,2)) as average, "
			+ "sum(r.star) as total, "
			+ "count(case when r.star = 5 then 1 end) as fiveStar, "
			+ "count(case when r.star = 4 then 1 end) as fourStar, "
			+ "count(case when r.star = 3 then 1 end) as threeStar, "
			+ "count(case when r.star = 2 then 1 end) as twoStar, "
			+ "count(case when r.star = 1 then 1 end) as oneStar "
			+ "from rating r  "
			+ "inner join product p on r.productID = p.id "
			+ "group by p.id,p.name "
			+ "order by average desc",nativeQuery=true)
	List<Object[]> getProductRating();

	
	
	//Fifth Row Content Edge

	@Query(value="Select  "
			+ "u.photo as photo, "
			+ "u.name as name, "
			+ "u.email as email, "
			+ "u.phone as phone, "
			+ "u.gender as gender, "
			+ "sum(i.totalPaymentNet) as total "
			+ "from invoice i inner join users u on u.id = i.userID "
			+ "where i.status = N'Paid' "
			+ "group by u.photo,u.name,u.email,u.phone,u.gender "
			+ "order by total desc",nativeQuery=true)
	List<Object[]> getTopCustomer();

	

	

	
	
	
	
}
