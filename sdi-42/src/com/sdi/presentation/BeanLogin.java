package com.sdi.presentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import alb.util.log.Log;

import com.sdi.business.TaskService;
import com.sdi.business.UserService;
import com.sdi.business.exception.BusinessException;
import com.sdi.dto.Task;
import com.sdi.dto.User;
import com.sdi.infrastructure.Factories;

@ManagedBean
@SessionScoped
public class BeanLogin extends User implements Serializable {

	private static final long serialVersionUID = 55556L;

	private String resultado = "";
	
//	@ManagedProperty(value="#{users}")
//	private BeanUsers beanUsers;
//	
//	public BeanUsers getBeanUsers(){
//		return this.beanUsers;
//	}
//	
//	public void setBeanUsers(BeanUsers beanUsers){
//		this.beanUsers = beanUsers;
//	}
	
	@ManagedProperty(value="#{tasks}")
	private BeanTasks beanTasks;
	
	public BeanTasks getBeanTasks(){
		return this.beanTasks;
	}
	
	public void setBeanTasks(BeanTasks beanTasks){
		this.beanTasks = beanTasks;
	}

	private User user = new User();

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@PostConstruct
	public void init() {
		Log.info("BeanLogin - PostConstruct");
		
//		beanUsers = (BeanUsers) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("users");
//		if(beanUsers == null){
//			Log.info("BeanUsers - No existía.");
//			beanUsers = new BeanUsers();
//			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("users", beanUsers);
//		}
		
		
		beanTasks = (BeanTasks) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tasks");
		if(beanTasks == null){
			Log.info("BeanTasks - No existía.");
			beanTasks = new BeanTasks();
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tasks", beanTasks);
		}
	}

	@PreDestroy
	public void end() {
		System.out.println("BeanLogin - PreDestroy");
	}

	public BeanLogin() {
		Log.debug("Bean login no existía.");
	}

	public void inicializarUsuario() {
		user.setId(null);
		user.setLogin("");
		user.setPassword("");
		user.setEmail("");
		// TODO Faltan atributos del usuario, comprobar BBDD.
	}

	public String cerrarSesion() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		removeUserInSession(session);
		return "exito";
	}

	public void putUserInSession() {
		Map<String, Object> sessionmap = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		sessionmap.put("LOGGEDIN_USER", user);
	}

	/**
	 * Borrar usuario en sesión y invalidar sesión
	 */
	public void removeUserInSession(HttpSession session) {
		session.removeAttribute("LOGGEDIN_USER");
	}

	public String verify() {
		FacesContext fc = FacesContext.getCurrentInstance();
		// Si no existe un usuario en sesión
		if (fc.getExternalContext().getSessionMap().get("LOGGEDIN_USER") == null) {
			UserService us = Factories.services.getUserService();
			TaskService ts = Factories.services.getTaskService();
			try {
				User aux = us.findLoggableUser(user.getLogin(),
						user.getPassword());
				if (aux != null) {
					user = aux;
					putUserInSession();
					if(user.getIsAdmin()){
						resultado = "admin";
					}else{
						List<Task> auxTasks = new ArrayList<Task>();
						auxTasks = ts.findInboxTasksByUserId(user.getId());
						beanTasks.setTasks(auxTasks);
						resultado = "client";
					}
					Log.info("El usuario [%s] ha iniciado sesión",
							user.getLogin());
					// TODO Cargar las categorías y las tareas del usuario.
				} else {
					inicializarUsuario();
					resultado = "fracaso";
					Log.info("Alguien ha intentado iniciar sesión con credenciales inválidas");
					fc.addMessage(null, new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Credenciales inválidas", "Credenciales inválidas"));
				}
			} catch (BusinessException e) {
				Log.debug("No se ha encontrado un usuario con las credenciales introducidas.");
			}
		} else {
			resultado = "fracaso";
			Log.info("Ya existe un usuario en sesión");
			fc.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Usuario en sesión",
							"Hay un usuario logeado, para logearte con otra cuenta debes cerrar la sesión con tu cuenta actual."));
		}

		return resultado;
	}
	
	public String register() {
		FacesContext fc = FacesContext.getCurrentInstance();
		// Si no existe un usuario en sesión
		if (fc.getExternalContext().getSessionMap().get("LOGGEDIN_USER") == null) {
			Log.info("Accediendo al registro de usuario");
			resultado = "exito";
			// TODO Cargar las categorías y las tareas del usuario.
		} else {
			resultado = "fracaso";
			Log.info("Ya existe un usuario en sesión");
			fc.addMessage(
					null,
					new FacesMessage(
							FacesMessage.SEVERITY_ERROR,
							"Usuario en sesión",
							"Hay un usuario logeado, para logearte con otra cuenta debes cerrar la sesión con tu cuenta actual."));
		}

		return resultado;
	}

}
