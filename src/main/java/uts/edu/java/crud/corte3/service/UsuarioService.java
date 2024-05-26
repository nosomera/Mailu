package uts.edu.java.crud.corte3.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



import uts.edu.java.crud.corte3.model.Usuario;
import uts.edu.java.crud.corte3.repository.IUsuarioRepository;

@Service
public class UsuarioService implements IUsuarioService {

	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public Optional<Usuario> findById(Integer id) {
		return usuarioRepository.findById(id);
	}

	@Override
	public Usuario save(Usuario usuario) {
		if (usuario.getPassword() != null) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
		
		return usuarioRepository.save(usuario);
	}

	@Override
	public Optional<Usuario> findByEmail(String email) {
		
		return usuarioRepository.findByEmail(email);
	}

	@Override
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}
	
	 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        Usuario usuario = usuarioRepository.findByEmail(username)
	                .orElseThrow(() -> new UsernameNotFoundException("Usuario o password inválidos"));	        	        
	        return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getPassword(), new ArrayList<>());
	    }

    
}
