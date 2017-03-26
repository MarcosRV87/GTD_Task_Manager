package com.sdi.dto;

import java.util.Calendar;
import java.util.Date;

import alb.util.date.DateUtil;

public class Task {
	private Long id;

	private String title;
	private String comments;
	private Date created = DateUtil.today();
	private Date planned;
	private Date finished;
	
	private Long categoryId;
	private Long userId;
	
	public Task(String nombreTarea, Long catId, Long userId) {
		this.title = nombreTarea;
		this.categoryId = catId;
		this.comments = null;
		this.created = new Date(Calendar.getInstance().getTimeInMillis());
		this.planned = null;
		this.finished = null;
		this.userId = userId;
	}
	
	public Task(){}

	public Long getId() {
		return id;
	}
	
//	public Task setId(Long id) {
//		this.id = id;
//		return this;
//	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
//	public Task setTitle(String title) {
//		this.title = title;
//		return this;
//	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getComments() {
		return comments;
	}
	
//	public Task setComments(String comments) {
//		this.comments = comments;
//		return this;
//	}
	
	public void setComments(String comments){
		this.comments = comments;
	}
	
	public Date getCreated() {
		return created;
	}
	
//	public Task setCreated(Date created) {
//		this.created = created;
//		return this;
//	}
	
	public void setCreated(Date created){
		this.created = created;
	}
	
	public Date getPlanned() {
		return planned;
	}
	
//	public Task setPlanned(Date planned) {
//		this.planned = planned;
//		return this;
//	}
	
	public void setPlanned(Date planned){
		this.planned = planned;
	}
	
	public Date getFinished() {
		return finished;
	}
	
//	public Task setFinished(Date finished) {
//		this.finished = finished;
//		return this;
//	}
	
	public void setFinished(Date finished){
		this.finished = finished;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}
	
//	public Task setCategoryId(Long category_id) {
//		this.categoryId = category_id;
//		return this;
//	}
	
	public void setCategoryId(Long category_id){
		this.categoryId = category_id;
	}
	
	public Long getUserId() {
		return userId;
	}
	
//	public Task setUserId(Long user_id) {
//		this.userId = user_id;
//		return this;
//	}
	
	public void setUserId(Long user_id){
		this.userId = user_id;
	}
	
	@Override
	public String toString() {
		return "TaskDto [id=" + id 
				+ ", title=" + title 
				+ ", comments=" + comments 
				+ ", created=" + created
				+ ", planned=" + planned 
				+ ", finished=" + finished 
				+ ", categoryId=" + categoryId 
				+ ", userId=" + userId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryId == null) ? 0 : categoryId.hashCode());
		result = prime * result + ((comments == null) ? 0 : comments.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((finished == null) ? 0 : finished.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((planned == null) ? 0 : planned.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (categoryId == null) {
			if (other.categoryId != null)
				return false;
		} else if (!categoryId.equals(other.categoryId))
			return false;
		if (comments == null) {
			if (other.comments != null)
				return false;
		} else if (!comments.equals(other.comments))
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (finished == null) {
			if (other.finished != null)
				return false;
		} else if (!finished.equals(other.finished))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (planned == null) {
			if (other.planned != null)
				return false;
		} else if (!planned.equals(other.planned))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

}
