package com.sdi.presentation;

import java.io.Serializable;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import alb.util.date.DateUtil;

import com.sdi.dto.Task;
import com.sdi.dto.User;

@ManagedBean(name = "task")
@SessionScoped
public class BeanTask extends Task implements Serializable {
	private static final long serialVersionUID = 55556L;

	public BeanTask() {
		iniciaTask(null);
	}

	// Este método es necesario para copiar la tarea a editar cuando
	// se pincha el enlace Editar en la vista listado.xhtml. Podría sustituirse
	// por un método editar en BeanTasks.
	public void setTask(Task task) {
		setId(task.getId());
		setTitle(task.getTitle());
		setComments(task.getComments());
		setCreated(task.getCreated());
		setPlanned(task.getPlanned());
		setFinished(task.getFinished());
		setCategoryId(task.getCategoryId());
		setUserId(task.getUserId());
	}

	// Iniciamos los datos de la tarea con los valores por defecto
	// extraídos del archivo de propiedades correspondiente
	public void iniciaTask(ActionEvent event) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Map<String, Object> sessionmap = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		User user = (User) sessionmap.get("LOGGEDIN_USER");
		ResourceBundle bundle = facesContext.getApplication()
				.getResourceBundle(facesContext, "msgs");
		setId(null);
		setTitle(bundle.getString("valorDefectoTitle"));
//		setComments(bundle.getString("valorDefectoComments"));
		setCreated(DateUtil.today());
//		setPlanned(bundle.getString("valorDefectoPlanned"));
//		setFinished(bundle.getString("valorDefectoFinished"));
//		setCategoryId();
		setUserId(user.getId());
	}

}
