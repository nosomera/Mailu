package uts.edu.java.crud.corte3.controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import uts.edu.java.crud.corte3.model.DetalleOrden;
import uts.edu.java.crud.corte3.model.Orden;
import uts.edu.java.crud.corte3.model.Producto;
import uts.edu.java.crud.corte3.model.Usuario;
import uts.edu.java.crud.corte3.service.IDetalleOrdenService;
import uts.edu.java.crud.corte3.service.IOrdenService;
import uts.edu.java.crud.corte3.service.IUsuarioService;
import uts.edu.java.crud.corte3.service.ProductoService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	private final Logger log=LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IOrdenService ordenService;
	
	@Autowired
	private IDetalleOrdenService detalleOrdenService;
	
	
	//para almacenar la orden
	List<DetalleOrden> detalles= new ArrayList<DetalleOrden>();
	
	//datos de la orden
	Orden orden=new Orden();
	
	@GetMapping("")
	public String home(Model model, HttpSession session) {
		log.info("El id del usuario: {}",session.getAttribute("idusuario"));
		
		model.addAttribute("productos", productoService.findAll());
		
		//session
		model.addAttribute("session",session.getAttribute("idusuario"));
		
		return "usuario/home";
	}
	
	@GetMapping("productohome/{id}")
	public String productoHome(@PathVariable int id, Model model) {
		log.info("Id producto enviado como paametro {}",id);
		Producto producto= new Producto();
		Optional<Producto>productoOptional=productoService.get(id);
		producto =productoOptional.get();
		
		model.addAttribute("producto", producto);
		
		return "usuario/productohome";
	}
	
	@PostMapping("/carrito")
	public String añadirAlCarrito(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
	    DetalleOrden detalleOrden = new DetalleOrden();
	    Producto producto;
	    double sumaTotal=0;

	    Optional<Producto> optionalProducto = productoService.get(id);
	   
	        
	        log.info("Producto añadido al carrito: {}", optionalProducto.get());
	        log.info("Cantidad: {}", cantidad);
	        producto = optionalProducto.get();
	        
	        detalleOrden.setCantidad(cantidad);
	        detalleOrden.setNombre(producto.getNombre());
	        detalleOrden.setPrecio(producto.getPrecio());
	        detalleOrden.setTotal(producto.getPrecio() * cantidad);
	        detalleOrden.setProducto(producto);
	        
	        //valida que el producto no se añada dos veces
	        Integer idProducto=producto.getId();
	        boolean ingresado=detalles.stream().anyMatch(p ->p.getProducto().getId()==idProducto);
	        if(!ingresado) {
	        	detalles.add(detalleOrden);
	        }
	        
	        sumaTotal=detalles.stream().mapToDouble(dt->dt.getTotal()).sum();

	      orden.setTotal(sumaTotal);
	      model.addAttribute("carrito", detalles);
	      model.addAttribute("orden", orden);

	    return "usuario/carrito";
	}
	
	@GetMapping("/delete/carrito/{id}")
	public String borrarDelCarrito(@PathVariable int id, Model model) {
		//lista nueva de productos
		List<DetalleOrden> ordenesNueva=new ArrayList<DetalleOrden>();
		
		for(DetalleOrden detalleorden: detalles) {
			if(detalleorden.getProducto().getId() !=id) {
				ordenesNueva.add(detalleorden);
			}
		}
		//poner lanueva lista conlos productos restantes
		detalles=ordenesNueva;
		
		double sumaTotal=0;
		
		sumaTotal=detalles.stream().mapToDouble(dt->dt.getTotal()).sum();

	     orden.setTotal(sumaTotal);
	     model.addAttribute("carrito", detalles);
	     model.addAttribute("orden", orden);
		
		return "usuario/carrito";
	}
	
	@GetMapping("/carrito")
	public String getCarro(Model model, HttpSession session) {
	    model.addAttribute("carrito", detalles);
	    model.addAttribute("orden", orden);
	    model.addAttribute("session", session.getAttribute("idusuario"));
	    return "/usuario/carrito";
	}

	
	@GetMapping("/orden")
	public String order(Model model, HttpSession session) {
	
		Usuario usuario=usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();	
		
		
		model.addAttribute("carrito", detalles);
	    model.addAttribute("orden", orden);
	    model.addAttribute("usuario", usuario);
	    
		return "usuario/resumenorden";
	}
	
	//guardar la orden en la BD
	@GetMapping("/saveOrder")
	public String saveOrder(HttpSession session) {
		Date fechaCreacion = new Date();
		orden.setFechaCreacion(fechaCreacion);
		orden.setNumero(ordenService.generarNumeroOrden());
		
		//usuario
		Usuario usuario=usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		
		orden.setUsuario(usuario);
		
		ordenService.save(orden);
		
		//guardar detalles
		for(DetalleOrden dt: detalles) {
			dt.setOrden(orden);
			detalleOrdenService.save(dt);
		}
		//limpiar lisrta y orden
		orden = new Orden();
		detalles.clear();
		
		return "redirect:/";
	}
	
	@PostMapping("/search")
	public String searchProduct(@RequestParam String nombre, Model model) {
		log.info("/nombre del producto: {}",nombre);
		List<Producto>productos= productoService.findAll().stream().filter(p -> p.getNombre().contains(nombre)).collect(Collectors.toList());
		model.addAttribute("productos",productos);
		
		return "/usuario/home";
	}
	

}
