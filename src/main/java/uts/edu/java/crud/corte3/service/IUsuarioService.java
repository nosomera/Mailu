package uts.edu.java.crud.corte3.service;


import java.util.List;
import java.util.Optional;

import uts.edu.java.crud.corte3.model.Usuario;


public interface IUsuarioService {
	
	List<Usuario> findAll();
	
	Optional<Usuario> findById(Integer id);
	
	Usuario save(Usuario usuario);
	
	Optional<Usuario> findByEmail(String email);
	
	
}
