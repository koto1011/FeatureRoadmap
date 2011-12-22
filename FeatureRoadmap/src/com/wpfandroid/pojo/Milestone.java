package com.wpfandroid.pojo;

public class Milestone {

	private int id;
	private String name;
	private String description;
	private String date;
	private Roadmap roadmap;

	public Milestone() {
		// TODO Auto-generated constructor stub
	}

	public Milestone(int id, String name, String description, String date,
			Roadmap roadmap) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.date = date;
		this.roadmap = roadmap;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Roadmap getRoadmap() {
		return roadmap;
	}

	public void setRoadmap(Roadmap roadmap) {
		this.roadmap = roadmap;
	}

	public String toString() {
		return "Milestone [id=" + id + ", name=" + name + ", description="
				+ description + ", date=" + date + ", roadmap=" + roadmap.getId() + "]";
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((roadmap == null) ? 0 : roadmap.hashCode());
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
		Milestone other = (Milestone) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (roadmap == null) {
			if (other.roadmap != null)
				return false;
		} else if (!roadmap.equals(other.roadmap))
			return false;
		return true;
	}

}
