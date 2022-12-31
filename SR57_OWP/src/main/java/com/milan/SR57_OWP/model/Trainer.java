package com.milan.SR57_OWP.model;

public class Trainer {
	
	private Long id;
	private String trainerName;
	
	public Trainer() {}
	
	public Trainer(Long id, String trainerName) {
		super();
		this.id = id;
		this.trainerName = trainerName;
	}
	
	public Trainer(String trainerName) {
		super();
		this.trainerName = trainerName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTrainerName() {
		return trainerName;
	}

	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
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
		Trainer other = (Trainer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Trainer [id=" + id + ", trainerName=" + trainerName + "]";
	}
	
	

}
