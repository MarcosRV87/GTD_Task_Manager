package com.sdi.business.impl;

import com.sdi.business.AdminService;
import com.sdi.business.ServicesFactory;
import com.sdi.business.TaskService;
import com.sdi.business.UserService;
import com.sdi.business.impl.admin.AdminServiceImpl;
import com.sdi.business.impl.task.TaskServiceImpl;
import com.sdi.business.impl.user.UserServiceImpl;

public class SimpleServicesFactory implements ServicesFactory {

	public AdminService getAdminService() {
		return new AdminServiceImpl();
	}

	public UserService getUserService() {
		return new UserServiceImpl();
	}

	public TaskService getTaskService() {
		return new TaskServiceImpl();
	}

}
