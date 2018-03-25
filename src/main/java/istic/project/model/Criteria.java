package istic.project.model;

import java.util.Date;

public class Criteria {

	private Double minPrice;
	private Double maxPrice;
	private String title;
	private Date minCreationDate;
	private Date maxCreationDate;

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public Criteria(Double minPrice, Double maxPrice, String title, Date minCreationDate, Date maxCreationDate) {
		super();
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.title = title;
		this.minCreationDate = minCreationDate;
		this.maxCreationDate = maxCreationDate;
	}

	public Criteria() {

	}

	public Double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getMinCreationDate() {
		return minCreationDate;
	}

	public void setMinCreationDate(Date minCreationDate) {
		this.minCreationDate = minCreationDate;
	}

	public Date getMaxCreationDate() {
		return maxCreationDate;
	}

	public void setMaxCreationDate(Date maxCreationDate) {
		this.maxCreationDate = maxCreationDate;
	}

}
