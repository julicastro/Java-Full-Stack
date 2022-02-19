package com.encuesta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import com.encuesta.models.requests.UserLoginRequestModel;
import com.encuesta.models.requests.UserRegisterRequestModel;
import com.encuesta.repositories.IUserRepository;
import com.encuesta.services.IUserService;
import com.encuesta.services.UserServiceImp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Response;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // puerto
@ActiveProfiles("test") // para q tome el .properties del test
public class LoginTest {

	private static final String API_LOGIN_URL = "/users/login";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    IUserService userService;

    @Autowired
    IUserRepository userRepository;

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }

    @Test
    public void postLogin_sinCredenciales_retornaForbidden() {
        ResponseEntity<Object> response = login(null, Object.class);
        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void postLogin_conCredencialesIncorrectas_retornaForbidden() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        userService.createUser(user);
        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail("asd@gmail.com");
        model.setPassword("12345678");
        ResponseEntity<Object> response = login(model, Object.class);
        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    public void postLogin_conCredencialesCorrectas_retornaOK() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        userService.createUser(user);
        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail(user.getEmail());
        model.setPassword(user.getPassword());
        ResponseEntity<Object> response = login(model, Object.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void postLogin_conCredencialesCorrectas_retornaAuthToken() {
        UserRegisterRequestModel user = TestUtil.createValidUser();
        userService.createUser(user);
        UserLoginRequestModel model = new UserLoginRequestModel();
        model.setEmail(user.getEmail());
        model.setPassword(user.getPassword());
        ResponseEntity<Map<String, String>> response = 
            login(model, new ParameterizedTypeReference<Map<String, String>>(){});

        Map<String, String> body = response.getBody();

        String token = body.get("token");

        assertTrue(token.contains("Bearer"));
    }

    public <T> ResponseEntity<T> login(UserLoginRequestModel data, Class<T> responseType) {
        return testRestTemplate.postForEntity(API_LOGIN_URL, data, responseType);
    }

    public <T> ResponseEntity<T> login(UserLoginRequestModel data, ParameterizedTypeReference<T> responseType) {
        HttpEntity<UserLoginRequestModel> entity = new HttpEntity<UserLoginRequestModel>(data, new HttpHeaders());
        return testRestTemplate.exchange(API_LOGIN_URL, HttpMethod.POST, entity, responseType);
    }



}
