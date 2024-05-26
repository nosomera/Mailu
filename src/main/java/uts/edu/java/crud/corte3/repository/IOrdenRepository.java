package uts.edu.java.crud.corte3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uts.edu.java.crud.corte3.model.Orden;
import uts.edu.java.crud.corte3.model.Usuario;

import java.util.List;



@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Integer>{
	List<Orden> findByUsuario(Usuario usuario);
	
	

}
