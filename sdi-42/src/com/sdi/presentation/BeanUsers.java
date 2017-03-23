package com.sdi.presentation;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.sdi.business.UserService;
import com.sdi.dto.User;
import com.sdi.infrastructure.Factories;
import com.sdi.presentation.impl.BeanFactoryImp;

@ManagedBean(name = "users")
@SessionScoped
public class BeanUsers implements Serializable {
	private static final long serialVersionUID = 55555L;
	// Se añade este atributo de entidad para recibir el user concreto
	// selecionado de la tabla o de un formulario
	// Es necesario inicializarlo para que al entrar desde el formulario de
	// AltaForm.xml se puedan
	// dejar los avalores en un objeto existente.
	// private User user = new User();

	// uso de inyección de dependencia
	@ManagedProperty(value = "#{user}")
	private BeanUser user;

	public BeanUser getUser() {
		return user;
	}

	public void setUser(BeanUser user) {
		this.user = user;
	}

	private List<User> users = null;

/*	SUPRIMIDO EJERCICIO 21-c
 * public BeanUsers() {
		iniciaUser(null);
	}*/

	public List<User> getUsers() {
		return (users);
	}

	/*
	 * public void setUser(User user) { this.user = user; } public
	 * User getUser() { return user; }
	 */

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void iniciaUser(ActionEvent event) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		// Obtenemos el archivo de propiedades correspondiente al idioma que
		// tengamos seleccionado y que viene envuelto en facesContext
		ResourceBundle bundle = facesContext.getApplication()
				.getResourceBundle(facesContext, "msgs");
		user.setId(null);
		user.setEmail(bundle.getString("valorDefectoCorreo"));
//		user.setIsAdmin(bundle.getString("valorDefectoUserAdmin"));
		user.setLogin(bundle.getString("valorDefectoLogin"));
		user.setPassword(bundle.getString("valorDefectoPassword"));
//		user.setStatus(bundle.getString("valorDefectoStatus"));
	}

	public String listado() {
		UserService service;
		try {
			service = Factories.services.getUserService();
			users = service.findAll();

			return "exito"; // Nos vamos a la vista listado.xhtml

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos la vista de error
		}

	}

//	public String baja(User user) {
//		UserService service;
//		try {
//			// Acceso a la implementacion de la capa de negocio
//			// a trav��s de la factor��a
//			service = 
//			// Aliminamos el user seleccionado en la tabla
//			service.deleteUser(user.getId());
//			// Actualizamos el javabean de users inyectado en la tabla.
//			users = (User[]) service.findAll().toArray(new User[0]);
//			return "exito"; // Nos vamos a la vista de listado.
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "error"; // Nos vamos a la vista de error
//		}
//
//	}

	public String edit() {
		UserService service;
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			service = Factories.services.getUserService();
			// Recargamos el user seleccionado en la tabla de la base de datos
			// por si hubiera cambios.

			// TODO PREGUNTAR SI ESTO ESTÁ CORRECTO
			user = (BeanUser) service.findUserById(user.getId());
			return "exito"; // Nos vamos a la vista de Edición.

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos a la vista de error.
		}

	}

	public String salva() {
		UserService service;
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			service = Factories.services.getUserService();
			// Salvamos o actualizamos el user segun sea una operacion de alta
			// o de edici��n
			if (user.getId() == null) {
				service.registerUser(user);
			} else {
				service.updateUserDetails(user);
			}
			// Actualizamos el javabean de users inyectado en la tabla
			users = service.findAll();
			return "exito"; // Nos vamos a la vista de listado.

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos a la vista de error.
		}

	}

	// Se inicia correctamente el MBean inyectado si JSF lo hubiera crea
	// y en caso contrario se crea. (hay que tener en cuenta que es un Bean de
	// sesión)
	// Se usa @PostConstruct, ya que en el contructor no se sabe todavía si el
	// Managed Bean
	// ya estaba construido y en @PostConstruct SI.
	@PostConstruct
	public void init() {
		System.out.println("BeanUsers - PostConstruct");
		// Buscamos el user en la sesión. Esto es un patrón factoría
		// claramente.
		BeanFactory bFactory = new BeanFactoryImp();
		user = bFactory.createBeanUser();
	}

	@PreDestroy
	public void end() {
		System.out.println("BeanUsers - PreDestroy");
	}
}
