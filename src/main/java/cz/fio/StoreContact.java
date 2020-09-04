package cz.fio;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StoreContact extends HttpServlet {

	CsvRepository csvRepository = new CsvRepository();

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		Customer c = new Customer(req.getParameter("firstName"), req.getParameter("lastName"),
				req.getParameter("email"));
		csvRepository.addCustomer(c);
	}




}
