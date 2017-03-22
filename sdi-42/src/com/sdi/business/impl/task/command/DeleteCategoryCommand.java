package com.sdi.business.impl.task.command;

import com.sdi.business.exception.BusinessException;
import com.sdi.business.impl.command.Command;
import com.sdi.infrastructure.Factories;

public class DeleteCategoryCommand implements Command<Void> {

	private Long catId;

	public DeleteCategoryCommand(Long catId) {
		this.catId = catId;
	}

	@Override
	public Void execute() throws BusinessException {
		Factories.persistence.getTaskDao().deleteAllFromCategory( catId );
		Factories.persistence.getCategoryDao().delete( catId );
		return null;
	}

}
