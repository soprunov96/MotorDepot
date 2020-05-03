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
 * Command that show all list of vehicles. Command allowed only for admins.
 *
 * @author Soprunov Igor
 *
 */

public class ShowListVechicalCommand extends Command {
	
	private static final Logger LOG = Logger.getLogger(ShowListVechicalCommand.class);
	
	private static final long serialVersionUID = -2591004293216132814L;

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType)
			throws IOException, ServletException, AppException {
		
		List<Vehicle> vehiclesList = new VehicleDaoImpl(DataSourceFactory
				.getDataSource(DataSourceType.MY_SQL_DATASOURCE)).findAll();
		LOG.trace("Found in DB: listVechicles --> " + vehiclesList);

		request.setAttribute("listVechicles", vehiclesList);
		LOG.trace("Set the request attribute: listFlights --> " + vehiclesList);
		
		LOG.debug("Command finished");
		return Path.LIST_OF_VECHICLES;
	}

}
