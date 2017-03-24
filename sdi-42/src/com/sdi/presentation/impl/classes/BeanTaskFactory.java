package com.sdi.presentation.impl.classes;

import javax.faces.context.FacesContext;

import com.sdi.presentation.BeanTask;
import com.sdi.presentation.BeanUser;

public class BeanTaskFactory {
	
	BeanTask task;
	
	public BeanTask createBeanTask(){
		task = (BeanTask) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get(new String("task"));
		// si no existe lo creamos e inicializamos
		if (task == null) {
			System.out.println("BeanTask - No existia");
			task = new BeanTask();
			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("task", task);
		}
		return task;
	}

}
