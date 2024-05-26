package uts.edu.java.crud.corte3.controlador;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import uts.edu.java.crud.corte3.model.Orden;
import uts.edu.java.crud.corte3.model.Producto;
import uts.edu.java.crud.corte3.service.IOrdenService;
import uts.edu.java.crud.corte3.service.IUsuarioService;
import uts.edu.java.crud.corte3.service.ProductoService;

@Controller
@RequestMapping("/administrador")
public class AppController {
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IOrdenService ordenService;
	
	private Logger logg= LoggerFactory.getLogger(AppController.class);
	
	@GetMapping("")
	public String home(Model model) {
		List<Producto> productos=productoService.findAll();
		model.addAttribute("productos", productos);
		
		return "administrador/home";
	}
	
	@GetMapping("/usuarios")
	public String usuario(Model model) {
		model.addAttribute("usuarios",usuarioService.findAll());
		
		return "administrador/usuarios";
	}

	@GetMapping("/ordenes")
	public String ordenes(Model model) {
		model.addAttribute("ordenes",ordenService.findAll());
		return "administrador/ordenes";
	}
	
	@GetMapping("/detalle/{id}")
	public String detalle(Model model, @PathVariable Integer id) {
		logg.info("Id de la orden {}",id);
		Orden orden= ordenService.findById(id).get();

		model.addAttribute("detalles", orden.getDetalle());

		return "administrador/detalleorden";
	}
}
