package ua.nure.soprunov.SummaryTask4.web.command.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.nure.soprunov.SummaryTask4.Path;
import ua.nure.soprunov.SummaryTask4.Util.ActionType;
import ua.nure.soprunov.SummaryTask4.dao.datasource.DataSourceFactory;
import ua.nure.soprunov.SummaryTask4.dao.datasource.DataSourceType;
import ua.nure.soprunov.SummaryTask4.dao.implementation.VehicleDaoImpl;
import ua.nure.soprunov.SummaryTask4.db.entity.Vehicle;
import ua.nure.soprunov.SummaryTask4.exception.AppException;
import ua.nure.soprunov.SummaryTask4.web.command.Command;

/**
 * Command that delete vehicles. Command allowed only for admins.
 *d
 * @author Soprunov Igor
 *
 */

public class DeleteVehicleCommand extends Command {

	private static final Logger LOG = Logger.getLogger(DeleteVehicleCommand.class);

	private static final long serialVersionUID = -823581367656691239L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType)
			throws IOException, ServletException, AppException {
		LOG.debug("Commands starts");

		String id = request.getParameter("id");
		LOG.trace("Fetch request parameter: 'id' = " + id);

		Vehicle vehicle= new Vehicle();
		vehicle.setId(Long.parseLong(id));
		new VehicleDaoImpl(DataSourceFactory
				.getDataSource(DataSourceType.MY_SQL_DATASOURCE)).delete(vehicle);

		List<Vehicle> vehiclesList =  new VehicleDaoImpl(DataSourceFactory
				.getDataSource(DataSourceType.MY_SQL_DATASOURCE)).findAll();
		LOG.trace("Found in DB: vehiclesList --> " + vehiclesList);

		request.setAttribute("listVechicles", vehiclesList);
		LOG.trace("Set the request attribute: vehiclesList --> " + vehiclesList);

		LOG.debug("Command finished");
		return Path.LIST_OF_VECHICLES;
	}

}
