package com.sdi.presentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
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
import alb.util.log.Log;

import com.sdi.business.TaskService;
import com.sdi.business.exception.BusinessException;
import com.sdi.dto.Category;
import com.sdi.dto.Task;
import com.sdi.dto.User;
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

	public List<Task> getTasks() {
		return (tasks);
	}

	/*
	 * public void setTask(Task task) { this.task = task; } public Task
	 * getTask() { return task; }
	 */

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	private Map<String,Long> categories = new HashMap<String,Long>();

	public Map<String,Long> getCategories() {
		return categories;
	}

	public void setCategories(Map<String,Long> categories) {
		this.categories = categories;
	}

	public void iniciaTask(ActionEvent event) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		// Obtenemos el archivo de propiedades correspondiente al idioma que
		// tengamos seleccionado y que viene envuelto en facesContext
		ResourceBundle bundle = facesContext.getApplication()
				.getResourceBundle(facesContext, "msgs");
		task.setId(null);
		task.setTitle(bundle.getString("valorDefectoTitle"));
		// task.setComments(bundle.getString("valorDefectoComments"));
		task.setCreated(DateUtil.today());
		// task.setPlanned(bundle.getString("valorDefectoPlanned"));
		// task.setFinished(bundle.getString("valorDefectoFinished"));
		// task.setCategoryId();
		// task.setUserId();
	}

	public String listado() {
		TaskService service;
		Map<String, Object> sessionmap = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		try {
			User user = (User) sessionmap.get("LOGGEDIN_USER");
			service = Factories.services.getTaskService();
			// Hay que obtener el id del usuario que está en sesión
			tasks = service.findInboxTasksByUserId(user.getId());

			return "exito"; // Nos vamos a la vista listado.xhtml

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos la vista de error
		}

	}

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
		String resultado = "";
		TaskService service;
		Map<String, Object> sessionmap = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			User user = (User) sessionmap.get("LOGGEDIN_USER");
			service = Factories.services.getTaskService();

			// Salvamos o actualizamos el task segun sea una operacion de alta
			// o de edici��n
			if (task.getId() == null) {
				task.setUserId(user.getId());
				service.createTask(task);
				if (task.getCategoryId() == null) {
					tasks = service.findInboxTasksByUserId(user.getId());
					resultado = "inbox";
				}
				if (task.getCategoryId() != null
						&& (task.getPlanned().compareTo(DateUtil.today()) == 0)) {
					tasks = service.findTodayTasksByUserId(user.getId());
					resultado = "today";
				}
				if (task.getCategoryId() != null
						&& (task.getPlanned().compareTo(DateUtil.today()) > 0)) {
					tasks = service.findWeekTasksByUserId(user.getId());
					resultado = "week";
				}
			} else {
				service.updateTask(task);
				if (task.getCategoryId() == null) {
					tasks = service.findInboxTasksByUserId(user.getId());
					resultado = "inbox";
				}
				if (task.getCategoryId() != null
						&& (task.getPlanned().compareTo(DateUtil.today()) == 0)) {
					tasks = service.findTodayTasksByUserId(user.getId());
					resultado = "today";
				}
				if (task.getCategoryId() != null
						&& (task.getPlanned().compareTo(DateUtil.today()) > 0)) {
					tasks = service.findWeekTasksByUserId(user.getId());
					resultado = "week";
				}
			}
			// Actualizamos el javabean de tasks inyectado en la tabla
			// Mismo que antes, hay que obtener el user de la task

			return resultado; // Nos vamos a la vista de listado.

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos a la vista de error.
		}

	}

	//
	public String addTarea() {
		String resultado = "";
		FacesContext fc = FacesContext.getCurrentInstance();

		// Si no existe un usuario en sesión
		if (fc.getExternalContext().getSessionMap().get("LOGGEDIN_USER") != null) {
			Log.info("Accediendo a añadir una tarea");
			resultado = "exito";
		}
		return resultado;
	}

	// TODO Quitar este método de aquí que me ta dando tirria...
	public String atras() {
		String resultado = "";
		FacesContext fc = FacesContext.getCurrentInstance();
		// Si no existe un usuario en sesión
		if (fc.getExternalContext().getSessionMap().get("LOGGEDIN_USER") != null) {
			Log.info("Volviendo a la vista principal de usuario");
			resultado = "exito";
		}
		return resultado;
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
		// task = (BeanTask)
		// FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("task");
		Map<String, Object> session = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		if (session.get("task") == null) {
			System.out.println("BeanTask - No existía.");
			BeanFactory bFactory = new BeanFactoryImp();
			task = bFactory.createBeanTask();
			// FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("task",
			// task);
			session.put("task", task);
		}

	}

	@PreDestroy
	public void end() {
		System.out.println("BeanTasks - PreDestroy");
	}

	public BeanTasks() {
		System.out.println("BeanTasks - No existia.");
	}

	public String listInbox() {
		TaskService service;
		Map<String, Object> sessionmap = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			User user = (User) sessionmap.get("LOGGEDIN_USER");
			service = Factories.services.getTaskService();
			// Actualizamos el javabean de tasks inyectado en la tabla
			// Mismo que antes, hay que obtener el user de la task
			tasks = service.findInboxTasksByUserId(user.getId());
			return "exito";

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos a la vista de error.
		}
	}

	public String listToday() {
		TaskService service;
		Map<String, Object> sessionmap = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			User user = (User) sessionmap.get("LOGGEDIN_USER");
			service = Factories.services.getTaskService();
			// Actualizamos el javabean de tasks inyectado en la tabla
			// Mismo que antes, hay que obtener el user de la task
			tasks = service.findTodayTasksByUserId(user.getId());

			return "exito";

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos a la vista de error.
		}
	}

	public String listWeek() {
		TaskService service;
		Map<String, Object> sessionmap = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		try {
			// Acceso a la implementacion de la capa de negocio
			// a trav��s de la factor��a
			User user = (User) sessionmap.get("LOGGEDIN_USER");
			service = Factories.services.getTaskService();
			// Actualizamos el javabean de tasks inyectado en la tabla
			// Mismo que antes, hay que obtener el user de la task
			tasks = service.findWeekTasksByUserId(user.getId());

			return "exito";

		} catch (Exception e) {
			e.printStackTrace();
			return "error"; // Nos vamos a la vista de error.
		}
	}

	public String selectTask(Task task) {
		this.task.setTask(task);
		return "edit";
	}

	public boolean isDelayed(Task task) {
		// TODO Quitar esto
		if (task.getPlanned() == null)
			return false;
		if (task.getPlanned().compareTo(DateUtil.today()) < 0)
			return true;
		else
			return false;
	}

	public String setTaskAsFinishedInInbox(Task task) {
		TaskService ts;
		ts = Factories.services.getTaskService();

		try {
			User user = (User) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("LOGGEDIN_USER");
			ts.markTaskAsFinished(task.getId());
			Log.info("Tarea marcada como finalizada.");
			tasks = ts.findInboxTasksByUserId(user.getId());
			return "exito";
		} catch (BusinessException e) {
			Log.error("Error: Error marcando tarea como finalizada.");
			return "fracaso";
		}

	}

	public String setTaskAsFinishedInToday(Task task) {
		TaskService ts;
		ts = Factories.services.getTaskService();

		try {
			User user = (User) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("LOGGEDIN_USER");

			ts.markTaskAsFinished(task.getId());
			Log.info("Tarea marcada como finalizada.");
			tasks = ts.findTodayTasksByUserId(user.getId());
			return "exito";
		} catch (BusinessException e) {
			Log.error("Error: Error marcando tarea como finalizada.");
			return "fracaso";
		}
	}

	public String setTaskAsFinishedInWeek(Task task) {
		TaskService ts;
		ts = Factories.services.getTaskService();

		try {
			User user = (User) FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get("LOGGEDIN_USER");
			ts.markTaskAsFinished(task.getId());
			Log.info("Tarea marcada como finalizada.");
			tasks = ts.findWeekTasksByUserId(user.getId());
			return "exito";
		} catch (BusinessException e) {
			Log.error("Error: Error marcando tarea como finalizada.");
			return "fracaso";
		}
	}

	public void obtainCategories() {
		TaskService ts;
		ts = Factories.services.getTaskService();
		User user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("LOGGEDIN_USER");
		try {
			List<Category> aux = new ArrayList<Category>();
			aux = ts.findCategoriesByUserId(user.getId());
			for (Category cat : aux)
				categories.put(cat.getName(), cat.getId());
//				if (!categories.contains(cat.getId()))
//					categories.add(cat.getId()); // String.valueOf()
			Log.info("Obtenidas categorias de usuario " + user.getLogin() + ".");
		} catch (BusinessException e) {
			Log.error("Error: Error obteniendo categorias de un usuario.");
		}
	}

}
