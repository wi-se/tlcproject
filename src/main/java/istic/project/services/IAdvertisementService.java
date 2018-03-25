package istic.project.services;

import java.util.List;

import istic.project.model.Advertisement;
import istic.project.model.Criteria;

public interface IAdvertisementService {
	List<Advertisement> getByCriteria(Criteria criteria);

	void add(List<Advertisement> advertisement);

	public void delete(List<Advertisement> advertisement);

}
