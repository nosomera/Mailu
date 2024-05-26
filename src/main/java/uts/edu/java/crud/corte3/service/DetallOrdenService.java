
package uts.edu.java.crud.corte3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uts.edu.java.crud.corte3.model.DetalleOrden;
import uts.edu.java.crud.corte3.repository.IDetalleOrdenRepository;

@Service
public class DetallOrdenService implements IDetalleOrdenService{

	@Autowired
	private IDetalleOrdenRepository detalleOrdenRepository;
	
	@Override
	public DetalleOrden save(DetalleOrden detalleOrden) {
		return detalleOrdenRepository.save(detalleOrden);
	}

}
