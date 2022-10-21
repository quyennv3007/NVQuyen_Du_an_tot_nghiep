package com.convenience_store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.convenience_store.entity.AccountLog;

public interface AccountLogRepo extends JpaRepository<AccountLog, Long> {
	
	@Query(value="Select u.name, u.photo , acl.*, "
			+ "(select "
			+ "distinct "
			+ "	stuff(( "
			+ "        select ', ' + r1.name "
			+ "        from role r1 inner join authority au1 on r1.id = au1.roleID "
			+ "        where au.userid = au1.userid "
			+ "        order by r1.name "
			+ "        for xml path('') "
			+ "    ),1,1,'') as roles "
			+ "from role r inner join authority au on r.id = au.roleID "
			+ "where userId =acl.userID "
			+ "group by au.userID,r.id) as roles "
			+ "from accountLog acl "
			+ "inner join users u on acl.userID = u.id "
			+ "Order by acl.date desc",nativeQuery = true)
	List<Object[]> getAccLog();

}
