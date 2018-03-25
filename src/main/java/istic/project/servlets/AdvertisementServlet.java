package istic.project.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import istic.project.Constants;
import istic.project.model.Advertisement;
import istic.project.model.Criteria;
import istic.project.services.IAdvertisementService;
import istic.project.services.impl.AdvertisementService;

@WebServlet(name = "advertisements", urlPatterns = { "/api/advertisements" })
public class AdvertisementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DATE_FORMAT = "yyy-MM-dd";
	private ObjectMapper mapper = new ObjectMapper();

	private IAdvertisementService advService = AdvertisementService.getInstance();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Criteria criteria = new Criteria();
		criteria.setTitle(request.getParameter(Constants.TITLE));
		criteria.setMaxPrice(toDouble(request.getParameter(Constants.MAX_PRICE)));
		criteria.setMinPrice(toDouble(request.getParameter(Constants.MIN_PRICE)));
		criteria.setMaxCreationDate(toDate(request.getParameter(Constants.MAX_DATE)));
		criteria.setMinCreationDate(toDate(request.getParameter(Constants.MIN_DATE)));
		List<Advertisement> advertisements = advService.getByCriteria(criteria);
		request.setAttribute("advertisements", advertisements);
		response.sendRedirect("/home.jsp");
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String content = request.getParameter("content");

		List<Advertisement> advertisements = mapper.readValue(content, new TypeReference<List<Advertisement>>() {
		});
		advertisements.stream().forEach(p -> p.setCreatedAt(new Date()));
		advService.add(advertisements);
		response.sendRedirect("/home.jsp");

	}

	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String content = request.getParameter("content");
		List<Advertisement> advertisement = mapper.readValue(content, new TypeReference<List<Advertisement>>() {
		});
		advService.delete(advertisement);
		response.sendRedirect("/home.jsp");

	}

	private Double toDouble(String s) {
		if (s == null || s.isEmpty()) {
			return null;
		}
		try {
			return Double.parseDouble(s);
		} catch (Exception e) {
			return null;
		}
	}

	private Date toDate(String s) {
		if (s == null || s.isEmpty()) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		try {
			return sdf.parse(s);
		} catch (Exception e) {
			return null;
		}
	}

}
