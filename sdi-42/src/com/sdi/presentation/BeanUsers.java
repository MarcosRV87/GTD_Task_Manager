package com.sdi.presentation;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import alb.util.log.Log;

import com.sdi.business.AdminService;
import com.sdi.business.UserService;
import com.sdi.dto.User;
import com.sdi.dto.types.UserStatus;
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

	public List<User> getUsers() {
		return (users);
	}

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
		// user.setIsAdmin(bundle.getString("valorDefectoUserAdmin"));
		user.setLogin(bundle.getString("valorDefectoLogin"));
		user.setPassword(bundle.getString("valorDefectoPassword"));
		// user.setStatus(bundle.getString("valorDefectoStatus"));
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

	public String edit() {
		UserService service;
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			service = Factories.services.getUserService();
			// Recargamos el user seleccionado en la tabla de la base de datos
			// por si hubiera cambios.

			user = (BeanUser) service.findUserById(user.getId());
			return "exito"; // Nos vamos a la vista de Edición.

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos a la vista de error.
		}

	}

	public String salva() {
		FacesContext fc = FacesContext.getCurrentInstance();
		UserService service;
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			service = Factories.services.getUserService();
			// Salvamos o actualizamos el user segun sea una operacion de alta
			// o de edici��n
			User aux = service.findUserByName(user.getLogin());
			if (user.getId() == null) {
				if ((aux == null)
						&& (user.getPassword().equals(user.getRepassword()))) {
					service.registerUser(user);
					Log.info("Registro satisfactorio.");
					fc.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_INFO,
							"Te has registrado satisfactoriamente.",
							"Te has registrado satisfactoriamente."));
				} else {
					Log.info("Las contraseñas han de ser iguales");
					fc.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_INFO,
									"Las contraseñas han de ser iguales, inténtelo de nuevo.",
									"Las contraseñas han de ser iguales, inténtelo de nuevo."));
					return "fracaso";
				}
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

	public String switchStatus(User user) {
		FacesContext fc = FacesContext.getCurrentInstance();
		
		UserService us;
		AdminService as;
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			us = Factories.services.getUserService();
			as = Factories.services.getAdminService();
			// Eliminamos el user seleccionado en la tabla
			if (user.getStatus() == UserStatus.DISABLED) {
				as.enableUser(user.getId());
				Log.info("Se ha habilitado el usuario con ID [%d]", user.getId());
				fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Se ha habilitado un usuario.",
						"Se ha habilitado un usuario."));
			} else {
				as.disableUser(user.getId());
				Log.info("Se ha deshabilitado el usuario con ID [%d]", user.getId());
				fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Se ha deshabilitado un usuario.",
						"Se ha deshabilitado un usuario."));
			}
			// Actualizamos el javabean de users inyectado en la tabla.
			users = us.findAll();
			return "exito"; // Nos vamos a la vista de listado.

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos a la vista de error
		}

	}

	public String delete(User user) {
		UserService us;
		AdminService as;
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			us = Factories.services.getUserService();
			as = Factories.services.getAdminService();
			// Aliminamos el user seleccionado en la tabla
			as.deepDeleteUser(user.getId());
			FacesContext fc = FacesContext.getCurrentInstance();
			Log.info("Se ha borrado el usuario [%d].",user.getId());
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Se ha borrado el usuario.",
					"Se ha borrado el usuario."));
			// Actualizamos el javabean de users inyectado en la tabla.
			users = us.findAll();
			return "exito"; // Nos vamos a la vista de listado.

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos a la vista de error
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

	public String resetDatabase() {
		FacesContext fc = FacesContext.getCurrentInstance();
		AdminService as;
		as = Factories.services.getAdminService();
		Log.info("La base de datos ha sido reseteada.");
		fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
				"La base de datos ha sido reseteada.",
				"La base de datos ha sido reseteada."));
		as.resetDatabase();
		return "exito";
	}
}
