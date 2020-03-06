package com.zk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zk.entity.User;
import com.zk.exception.UserNotFound;
import com.zk.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MyController_3_Test {

	@Autowired
	MockMvc mvc;



	@MockBean
	UserService userService;




	@Test
	public void getUser() throws Exception {
		// given
		User bob = new User().setName("bob").setId(3);
		given(userService.getUser(1))
				.willReturn(bob);


		// when
		MockHttpServletResponse response = mvc.perform(
				get("/user/1")
						.accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// then
		ObjectMapper objectMapper = new ObjectMapper();
		Assert.assertEquals(response.getStatus(), HttpStatus.OK.value());
		Assert.assertEquals(response.getContentAsString(), objectMapper.writeValueAsString(bob));
	}

	@Test
	public void getUserNotFound() throws Exception {
		// given
		given(userService.getUser(999))
				.willThrow(new UserNotFound());

		// when
		MockHttpServletResponse response = mvc.perform(
				get("/users/1")
						.accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		assertThat(response.getContentAsString()).isEmpty();
	}
}
