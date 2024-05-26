package uts.edu.java.crud.corte3.service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



import uts.edu.java.crud.corte3.model.Usuario;
import uts.edu.java.crud.corte3.repository.IUsuarioRepository;

@Service
public class UsuarioService implements IUsuarioService {

	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	/*@Autowired
	private BCryptPasswordEncoder passwordEncoder; */

	@Override
	public Optional<Usuario> findById(Integer id) {
		return usuarioRepository.findById(id);
	}

	/*
	@Override
	public Usuario save(Usuario usuario) {
		if (usuario.getPassword() != null) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
		
		return usuarioRepository.save(usuario);
	}
	*/

	@Override
	public Optional<Usuario> findByEmail(String email) {
		
		return usuarioRepository.findByEmail(email);
	}

	@Override
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	@Override
	 public Usuario save(Usuario usuario) {
        // La lógica de encriptación de la contraseña se debe mover a donde se crea el usuario
        return usuarioRepository.save(usuario);
    }
	
	
	 

    
}
