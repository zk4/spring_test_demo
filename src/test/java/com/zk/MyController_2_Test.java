package com.zk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zk.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class MyController_2_Test {



	// 要注意, 不要用 RestTemplate,
	// 因为 TestRestTemplate 在测试环境里多做了很多事,
	// 比如: 帮你自己把当前 host:port 加上了. (尤其是咱们还指定了随机端口)
	// 			能自动加账号密码,
	// 			ErrorHandler 被设成了 NoOpResponseErrorHandler.
	// 			最重要的, 能一键注入啊...
	@Autowired
	TestRestTemplate restTemplate;





	@Test
	public void getUser() throws Exception {
		// given
		User user = new User().setName("bob").setId(1);

		// when
		ResponseEntity<User> response = restTemplate.getForEntity("/user/1", User.class);


		// then
		ObjectMapper objectMapper=new ObjectMapper();

		Assert.assertEquals(response.getStatusCode(),HttpStatus.OK);
		Assert.assertEquals(
				objectMapper.writeValueAsString(response.getBody()),
				objectMapper.writeValueAsString(user)
		);
	}
	@Test
	public void getUserNotFound() throws Exception {
		// given
		User user = new User().setName("bob").setId(1);

		// when
		ResponseEntity<User> response = restTemplate.getForEntity("/user/999", User.class);


		// then
		ObjectMapper objectMapper=new ObjectMapper();

		Assert.assertEquals(response.getStatusCode(),HttpStatus.NOT_FOUND);

	}

}
