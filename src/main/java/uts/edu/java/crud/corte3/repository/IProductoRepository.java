package uts.edu.java.crud.corte3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uts.edu.java.crud.corte3.model.Producto;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Integer>{

}
