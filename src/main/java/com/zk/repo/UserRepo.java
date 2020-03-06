package com.zk.repo;


import com.zk.entity.User;
import org.springframework.stereotype.Repository;

// for simulation..... you can use jpa
@Repository
public class UserRepo {

	public User getOne(int id){
		if (id == 1){
			return new User().setName("bob").setId(1);
		}else if(id ==2){
			return new User().setName("zk").setId(2);
		}
		return null;
	}
}
