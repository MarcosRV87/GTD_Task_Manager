package com.sdi.presentation;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import alb.util.date.DateUtil;

import com.sdi.business.TaskService;
import com.sdi.dto.Task;
import com.sdi.infrastructure.Factories;
import com.sdi.presentation.impl.BeanFactoryImp;

@ManagedBean(name = "tasks")
@SessionScoped
public class BeanTasks implements Serializable {
	private static final long serialVersionUID = 55555L;
	// Se añade este atributo de entidad para recibir el task concreto
	// selecionado de la tabla o de un formulario
	// Es necesario inicializarlo para que al entrar desde el formulario de
	// AltaForm.xml se puedan
	// dejar los avalores en un objeto existente.
	// private Task task = new Task();

	// uso de inyección de dependencia
	@ManagedProperty(value = "#{task}")
	private BeanTask task;

	public BeanTask getTask() {
		return task;
	}

	public void setTask(BeanTask task) {
		this.task = task;
	}

	private List<Task> tasks = null;

/*	SUPRIMIDO EJERCICIO 21-c
 * public BeanTasks() {
		iniciaTask(null);
	}*/

	public List<Task> getTasks() {
		return (tasks);
	}

	/*
	 * public void setTask(Task task) { this.task = task; } public
	 * Task getTask() { return task; }
	 */

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public void iniciaTask(ActionEvent event) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		// Obtenemos el archivo de propiedades correspondiente al idioma que
		// tengamos seleccionado y que viene envuelto en facesContext
		ResourceBundle bundle = facesContext.getApplication()
				.getResourceBundle(facesContext, "msgs");
		task.setId(null);
		task.setTitle(bundle.getString("valorDefectoTitle"));
		task.setComments(bundle.getString("valorDefectoComments"));
		task.setCreated(DateUtil.today());
//		task.setPlanned(bundle.getString("valorDefectoPlanned"));
//		task.setFinished(bundle.getString("valorDefectoFinished"));
//		task.setCategoryId();
//		task.setUserId();
	}

	public String listado() {
		TaskService service;
		Map<String, Object> sessionmap = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		try {
			BeanUser user = (BeanUser) sessionmap.get("LOGGEDIN_USER");
			service = Factories.services.getTaskService();
			//Hay que obtener el id del usuario que está en sesión
			tasks = service.findInboxTasksByUserId(user.getId());
					
			return "exito"; // Nos vamos a la vista listado.xhtml

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos la vista de error
		}

	}

//	public String baja(Task task) {
//		TaskService service;
//		try {
//			// Acceso a la implementacion de la capa de negocio
//			// a trav��s de la factor��a
//			service = 
//			// Aliminamos el task seleccionado en la tabla
//			service.deleteTask(task.getId());
//			// Actualizamos el javabean de tasks inyectado en la tabla.
//			tasks = (Task[]) service.findAll().toArray(new Task[0]);
//			return "exito"; // Nos vamos a la vista de listado.
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "error"; // Nos vamos a la vista de error
//		}
//
//	}

	public String edit() {
		TaskService service;
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			service = Factories.services.getTaskService();
			// Recargamos el task seleccionado en la tabla de la base de datos
			// por si hubiera cambios.

			// TODO PREGUNTAR SI ESTO ESTÁ CORRECTO
			task = (BeanTask) service.findTaskById(task.getId());
			return "exito"; // Nos vamos a la vista de Edición.

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos a la vista de error.
		}

	}

	public String salva() {
		TaskService service;
		Map<String, Object> sessionmap = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			BeanUser user = (BeanUser) sessionmap.get("LOGEDIN_USER");
			service = Factories.services.getTaskService();
			// Salvamos o actualizamos el task segun sea una operacion de alta
			// o de edici��n
			if (task.getId() == null) {
				service.createTask(task);
			} else {
				service.updateTask(task);
			}
			// Actualizamos el javabean de tasks inyectado en la tabla
			//Mismo que antes, hay que obtener el user de la task
			tasks = service.findInboxTasksByUserId(user.getId());
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
		System.out.println("BeanTasks - PostConstruct");
		// Buscamos el task en la sesión. Esto es un patrón factoría
		// claramente.
		BeanFactory bFactory = new BeanFactoryImp();
		task = bFactory.createBeanTask();
	}

	@PreDestroy
	public void end() {
		System.out.println("BeanTasks - PreDestroy");
	}
}
