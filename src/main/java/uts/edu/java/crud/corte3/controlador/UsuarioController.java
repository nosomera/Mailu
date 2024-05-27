package uts.edu.java.crud.corte3.controlador;



import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import uts.edu.java.crud.corte3.model.Orden;
import uts.edu.java.crud.corte3.model.Usuario;
import uts.edu.java.crud.corte3.service.IOrdenService;
import uts.edu.java.crud.corte3.service.IUsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	private final Logger logger=LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IOrdenService ordenService;
	
	BCryptPasswordEncoder passEncode= new BCryptPasswordEncoder();
	
	
	@GetMapping("/registro")
	public String create() {
		return "usuario/registro";
	}
	
	@PostMapping("/save")
	public String save(Usuario usuario) {
		logger.info("Usuario registro: {}",usuario);
		usuario.setTipo("USER");
		usuario.setPassword( passEncode.encode(usuario.getPassword()));
		usuarioService.save(usuario);
		
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return "usuario/login";
	}
	
	@PostMapping("/acceder")
	public String acceder(Usuario usuario, HttpSession session, Model model) {
	    logger.info("Accesos : {}", usuario);

	    Optional<Usuario> user = usuarioService.findByEmail(usuario.getEmail());

	    if (user.isPresent()) {
	        Usuario usuarioDB = user.get();

	        if (passEncode.matches(usuario.getPassword(), usuarioDB.getPassword())) {
	            session.setAttribute("idusuario", usuarioDB.getId());
	            session.setAttribute("usuarioTipo", usuarioDB.getTipo()); // Agregar tipo de usuario a la sesión
	            if (usuarioDB.getTipo().equals("ADMIN")) {
	                return "redirect:/administrador";
	            } else {
	                return "redirect:/";
	            }
	        } else {
	            logger.info("Contraseña incorrecta");
	            model.addAttribute("error", "Contraseña incorrecta");
	            return "usuario/login";
	        }
	    } else {
	        logger.info("Usuario no existe");
	        model.addAttribute("error", "Usuario no existe");
	        return "usuario/login";
	    }
	}

	@GetMapping("/compras")
	public String obtenerCompras(HttpSession session, Model model) {
		model.addAttribute("session",session.getAttribute("idusuario"));
		
		Usuario usuario= usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		List<Orden> ordenes= ordenService.findByUsuario(usuario);
		
		model.addAttribute("ordenes",ordenes);
		
		return "usuario/compras";
	}
	
	@GetMapping("/detalle/{id}")
	public String detalleCompra (@PathVariable Integer id, HttpSession session, Model model) {
		logger.info("Id de la orden: {}", id);
		Optional<Orden> orden= ordenService.findById(id);
		
		model.addAttribute("session",session.getAttribute("idusuario"));
		
		
		model.addAttribute("detalles",orden.get().getDetalle());
		return "usuario/detallecompra";
	}

	@GetMapping("/cerrar")
	public String cerrarSesion( HttpSession session, RedirectAttributes redirectAttributes ) {
		session.removeAttribute("idusuario");
		redirectAttributes.addFlashAttribute("mensaje", "Sesión cerrada exitosamente");

		return "redirect:/";
	}
	
	@GetMapping("/cambiarClave")
	public String cambiarClave() {
		return "usuario/cambiarClave";
	}
	
	 @PostMapping("/actualizarClave")
	    public String actualizarClave(@RequestParam("claveActual") String claveActual,
	                                  @RequestParam("nuevaClave") String nuevaClave,
	                                  @RequestParam("confirmarClave") String confirmarClave,
	                                  HttpSession session, RedirectAttributes redirectAttributes) {
	        Integer idUsuario = (Integer) session.getAttribute("idusuario");
	        if (idUsuario == null) {
	            return "redirect:/usuario/login";
	        }

	        Usuario usuario = usuarioService.findById(idUsuario).get();

	        if (!passEncode.matches(claveActual, usuario.getPassword())) {
	            redirectAttributes.addFlashAttribute("error", "La contraseña actual es incorrecta");
	            return "redirect:/usuario/cambiarClave";
	        }

	        if (!nuevaClave.equals(confirmarClave)) {
	            redirectAttributes.addFlashAttribute("error", "Las nuevas contraseñas no coinciden");
	            return "redirect:/usuario/cambiarClave";
	        }

	        usuario.setPassword(passEncode.encode(nuevaClave));
	        usuarioService.save(usuario);
	        redirectAttributes.addFlashAttribute("mensaje", "Contraseña actualizada exitosamente");
	        return "redirect:/usuario/cambiarClave";
	    }
	
}
