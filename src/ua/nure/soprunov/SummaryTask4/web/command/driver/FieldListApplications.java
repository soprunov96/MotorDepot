package ua.nure.soprunov.SummaryTask4.web.command.driver;

import org.apache.log4j.Logger;
import ua.nure.soprunov.SummaryTask4.Path;
import ua.nure.soprunov.SummaryTask4.Util.ActionType;
import ua.nure.soprunov.SummaryTask4.dao.datasource.DataSourceFactory;
import ua.nure.soprunov.SummaryTask4.dao.datasource.DataSourceType;
import ua.nure.soprunov.SummaryTask4.dao.implementation.RequestDaoImpl;
import ua.nure.soprunov.SummaryTask4.dao.implementation.FlightDaoImpl;
import ua.nure.soprunov.SummaryTask4.dao.entity.Flight;
import ua.nure.soprunov.SummaryTask4.dao.entity.Request;
import ua.nure.soprunov.SummaryTask4.exception.AppException;
import ua.nure.soprunov.SummaryTask4.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;

/**
 * Command that show all list of driver applications. Command allowed only for drivers.
 *
 * @author Soprunov Igor
 */

public class FieldListApplications  extends Command {

    private static final Logger LOG = Logger.getLogger(FieldListApplications.class);

    private static final long serialVersionUID = -5908815031618218228L;

    DataSource datasource = DataSourceFactory
            .getDataSource(DataSourceType.MY_SQL_DATASOURCE);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType)
            throws IOException, ServletException, AppException {
        LOG.debug("Command starts");

        // get flights list
        List<Flight> listFlights =  new  FlightDaoImpl(datasource).findAll();
        LOG.trace("Found in DB: listFlights --> " + listFlights);

        // put flights list to the request
        request.setAttribute("listFlight", listFlights);
        LOG.trace("Set the request attribute: listFlights --> " + listFlights);

        // get request list
        List<Request> listRequest =  new RequestDaoImpl(datasource).findAll();
        LOG.trace("Found in DB: listFlights --> " + listFlights);

        // put RequestList to the request
        request.setAttribute("listRequest", listRequest);
        LOG.trace("Set the request attribute: listRequest --> " + listRequest);

        LOG.debug("Command finished");
        return Path.FIELD_LIST_APPLICATION;
    }
}
