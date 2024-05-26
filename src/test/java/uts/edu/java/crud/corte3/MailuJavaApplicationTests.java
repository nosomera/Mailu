package uts.edu.java.crud.corte3;

import static org.assertj.core.api.Assertions.assertThatRuntimeException;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import uts.edu.java.crud.corte3.model.UserLogin;
import uts.edu.java.crud.corte3.repository.IUserLoginRepository;

@SpringBootTest
class MailuJavaApplicationTests {

	@Autowired
	private IUserLoginRepository repo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Test
	void crearUsuarioTest() {
		UserLogin us= new UserLogin();
		us.setId(100);
		us.setEmail("Prueba@gmail.com");
		us.setPassword(encoder.encode("12345"));
		
		UserLogin retorno= repo.save(us);
		
		assertTrue(retorno.getPassword().equalsIgnoreCase(us.getPassword()));
	}

}
