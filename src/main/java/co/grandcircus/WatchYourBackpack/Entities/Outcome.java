package co.grandcircus.WatchYourBackpack.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Outcome {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	private Boolean survived;
	private String description;
	@ManyToOne 
	private WeatherEvent weatherEvent;
	@ManyToOne 
	private BeastEvent beastEvent;
	private Integer choice;
	private Integer healthChange = 0;
	
	
	
	public Outcome(Boolean survived, String description) {
		super();
		this.survived = survived;
		this.description = description;
	}
	public Outcome() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Boolean getSurvived() {
		return survived;
	}
	public void setSurvived(Boolean survived) {
		this.survived = survived;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Outcome [id=" + id + ", survived=" + survived + ", description=" + description + "]";
	}
	public Integer getChoice() {
		return choice;
	}
	public void setChoice(Integer choice) {
		this.choice = choice;
	}
	public Integer getHealthChange() {
		return healthChange;
	}
	public void setHealthChange(Integer healthChange) {
		this.healthChange = healthChange;
	}
	
	
}
