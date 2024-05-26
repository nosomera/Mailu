package uts.edu.java.crud.corte3.service;

import java.util.List;
import java.util.Optional;

import uts.edu.java.crud.corte3.model.Orden;
import uts.edu.java.crud.corte3.model.Usuario;

public interface IOrdenService {
	
	List<Orden>findAll();
	
	Optional<Orden> findById(Integer id);
	
	Orden save(Orden orden);

	String generarNumeroOrden();
	
	List<Orden> findByUsuario(Usuario usuario);
	
	
	
}
