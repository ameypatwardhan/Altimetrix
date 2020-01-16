package com.altimetrix.altimetrixjpacrudexample;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.altimetrix.altimetrixjpacrudexample.controller.UserController;
import com.altimetrix.altimetrixjpacrudexample.model.User;
import com.altimetrix.altimetrixjpacrudexample.repository.UserRepository;
import com.altimetrix.altimetrixjpacrudexample.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userServiceTest;
	
	@MockBean
	private UserRepository mockRepository;
	
	@Test
	public void getUserBygivenId() throws Exception
	{
		this.mockMvc.perform(get("/edit/15")).andExpect(status().isOk()).andExpect(view().name("add-edit-user"));
	}

	@Test
	public void getUserByIdTest() throws Exception
	{
		Optional<User> user = Optional.empty();
		user.get().setId(15L);
		user.get().setFirstName("mi");
		user.get().setLastName("aaaa");
		user.get().setEmailId("abc@xyz.com");
		user.get().setPhoneNumber("12345");
		user.get().setStatus("InActive");
		when(mockRepository.findById(15L)).thenReturn(user);
		assertNotNull(user.get());
	}
	
	// simillarly test cases can be written for other methods
		
}
