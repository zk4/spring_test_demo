package com.zk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zk.controller.UserController;
import com.zk.controller.UserExceptionAdvice;
import com.zk.entity.User;
import com.zk.exception.UserNotFound;
import com.zk.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//由 Junit 4 启动 Mockito
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureRestDocs
public class MyController_1_Test {



	private MockMvc mvc;

	@Mock
	// 要 Mock 的类
	UserService  userService;

	@InjectMocks
	// Mock 要注入的类
			UserController userController;


	@Before
	public void setUp()   {
		mvc = MockMvcBuilders.standaloneSetup(userController)
				//指定 Exception 处理器
				.setControllerAdvice(new UserExceptionAdvice())
				//.addFilters(new UserFilter())  //你也可以指定 filter , interceptor 之类的, 看 StandaloneMockMvcBuilder 源码
				.build();
	}

	@Test
	public void getUserTest() throws Exception {
		// given
		User bob = new User().setName("bob").setId(1);
		given(userService.getUser(1))
				.willReturn(bob);

		//  when
		MockHttpServletResponse response = mvc.perform(
				get("/user/1")
						.accept(MediaType.APPLICATION_JSON)

		)
				.andReturn()
				.getResponse();

		// then
		ObjectMapper objectMapper = new ObjectMapper();
		Assert.assertEquals(response.getStatus(), HttpStatus.OK.value());
		Assert.assertEquals(response.getContentAsString(), objectMapper.writeValueAsString(bob));
	}


	@Test
	public void getUserNotFound() throws Exception {
		 //given
		given(userService.getUser(999))
				.willThrow(new UserNotFound());

		// when
		MockHttpServletResponse response = mvc.perform(
				get("/user/999")
						.accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// then
		Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		Assertions.assertThat(response.getContentAsString()).isEmpty();
	}
}
