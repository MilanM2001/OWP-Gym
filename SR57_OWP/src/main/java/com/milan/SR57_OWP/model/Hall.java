package com.milan.SR57_OWP.model;

public class Hall {

	private Long id;
	private String hallLabel;
	private int capacity;
	
	public Hall(Long id, String hallLabel, int capacity) {
		super();
		this.id = id;
		this.hallLabel = hallLabel;
		this.capacity = capacity;
	}

	public Hall(String hallLabel, int capacity) {
		super();
		this.hallLabel = hallLabel;
		this.capacity = capacity;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime*result + ((id == null) ? 0 : id.hashCode());
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
		Hall other = (Hall) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHallLabel() {
		return hallLabel;
	}

	public void setHallLabel(String hallLabel) {
		this.hallLabel = hallLabel;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
}
