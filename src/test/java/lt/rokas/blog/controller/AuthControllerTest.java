package lt.rokas.blog.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import lt.rokas.blog.service.AuthService;
@RunWith(SpringRunner.class)
@WebMvcTest(value = AuthController.class)
class AuthControllerTest {
	@Autowired
	MockMvc mockMvc;
	@MockBean
	private AuthService authService;

	@BeforeEach
	void setUp() throws Exception {
	}

//	@Test
//	void testSignup() {
//		fail("Not yet implemented");
//	}

	@Test
	void testLogin() {
		fail("Not yet implemented");
	}

}
