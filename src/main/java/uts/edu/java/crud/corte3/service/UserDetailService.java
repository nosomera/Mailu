package uts.edu.java.crud.corte3.service;

import org.slf4j.Logger;

import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import uts.edu.java.crud.corte3.model.Usuario;

@Service
public class UserDetailService implements UserDetailsService{
	
	@Autowired
	private IUsuarioService usuarioService;
	
	/*
	 * @Autowired
	private BCryptPasswordEncoder bCrypt;
	*/

	@Autowired
	HttpSession session;
	
	private Logger log= LoggerFactory.getLogger(IUsuarioService.class);
	
	/*
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Este es el username");
		Optional<Usuario>optionalUser= usuarioService.findByEmail(username);
		if(optionalUser.isPresent()) {
			log.info("Esto es el id del usuario: {}",optionalUser.get().getId());
			session.setAttribute("idusuario", optionalUser.get().getId());
			Usuario usuario= optionalUser.get();
			return User.builder().username(usuario.getNombre()).password(bCrypt.encode(usuario.getPassword())).roles(usuario.getTipo()).build();
		}else {
			throw new UsernameNotFoundException("El usuario no encontrado");
		}
		
	}
   */
	  @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        log.info("Este es el username");
	        Optional<Usuario> optionalUser = usuarioService.findByEmail(username);
	        if (optionalUser.isPresent()) {
	            log.info("Esto es el id del usuario: {}", optionalUser.get().getId());
	            session.setAttribute("idusuario", optionalUser.get().getId());
	            Usuario usuario = optionalUser.get();
	            return User.builder().username(usuario.getNombre()).password(usuario.getPassword()).roles(usuario.getTipo()).build();
	        } else {
	            throw new UsernameNotFoundException("El usuario no encontrado");
	        }
	    }
}
