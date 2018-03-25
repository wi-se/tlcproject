package istic.project.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import istic.project.model.Advertisement;
import istic.project.model.Criteria;
import istic.project.services.IAdvertisementService;

public class AdvertisementService implements IAdvertisementService {

	private static AdvertisementService instance;
	static {
		try {
			ObjectifyService.register(Advertisement.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}

	private AdvertisementService() {

	}

	public static AdvertisementService getInstance() {
		if (instance == null) {
			instance = new AdvertisementService();
		}
		return instance;
	}

	private List<Advertisement> filterByTitle(List<Advertisement> advertisements,
 String title) {
 if (advertisements != null) {
 return advertisements.stream().filter(a -> {
 if (title != null && !title.isEmpty()) {
 return a.getTitle().startsWith(title);

 }
 return true;
 }).collect(Collectors.toList());
 } else if (title != null && !title.isEmpty()) {
 return ObjectifyService.ofy().load().type(Advertisement.class).filter("title
 >=", title)
 .filter("title <", title + "\uFFFD").list();
 }
 return advertisements;
 }

	private List<Advertisement> filterByPrice(List<Advertisement> advertisements, Double minPrice, Double maxPrice) {
		if (advertisements != null) {

			return advertisements.stream().filter(a -> {
				boolean isMatch = true;
				if (minPrice != null) {
					isMatch = a.getPrice() >= minPrice;
				}
				if (maxPrice != null) {
					return isMatch && a.getPrice() <= maxPrice;
				}
				return isMatch;
			}).collect(Collectors.toList());

		} else {
			Query<Advertisement> query = ObjectifyService.ofy().load().type(Advertisement.class);
			if (minPrice != null) {
				query = query.filter("price >=", minPrice);
			}
			if (maxPrice != null) {
				query = query.filter("price <=", maxPrice);
			}
			return query.list();
		}
	}

	private List<Advertisement> filterByDate(List<Advertisement> advertisements, Date minCreatedAt, Date maxCreatedAt) {
		if (advertisements != null) {
			return advertisements.stream().filter(a -> {
				return ((minCreatedAt != null) ? a.getCreatedAt().after(minCreatedAt) : true)
						&& ((maxCreatedAt != null) ? a.getCreatedAt().before(maxCreatedAt) : true);
			}).collect(Collectors.toList());

		} else {
			Query<Advertisement> queryAdvs = ObjectifyService.ofy().load().type(Advertisement.class);
			if (minCreatedAt != null) {
				queryAdvs = queryAdvs.filter("createdAt >=", minCreatedAt);
			}
			if (maxCreatedAt != null) {
				queryAdvs = queryAdvs.filter("createdAt <=", maxCreatedAt);
			}
			return queryAdvs.list();
		}
	}

	@Override
	public List<Advertisement> getByCriteria(Criteria criteria) {
		List<Advertisement> result = null;
		result = filterByTitle(result, criteria.getTitle());
		result = filterByPrice(result, criteria.getMinPrice(), criteria.getMaxPrice());
		result = filterByDate(result, criteria.getMinCreationDate(), criteria.getMaxCreationDate());
		return result;
	}

	public void add(List<Advertisement> advertisement) {
		ObjectifyService.ofy().save().entities(advertisement).now();
	}

	public void delete(List<Advertisement> advertisement) {
		ObjectifyService.ofy().delete().entities(advertisement).now();
	}

}
