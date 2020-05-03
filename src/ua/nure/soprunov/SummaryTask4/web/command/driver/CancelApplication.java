package ua.nure.soprunov.SummaryTask4.web.command.driver;

import org.apache.log4j.Logger;
import ua.nure.soprunov.SummaryTask4.Path;
import ua.nure.soprunov.SummaryTask4.Util.ActionType;
import ua.nure.soprunov.SummaryTask4.dao.datasource.DataSourceFactory;
import ua.nure.soprunov.SummaryTask4.dao.datasource.DataSourceType;
import ua.nure.soprunov.SummaryTask4.dao.implementation.RequestDaoImpl;
import ua.nure.soprunov.SummaryTask4.dao.implementation.FlightDaoImpl;
import ua.nure.soprunov.SummaryTask4.db.Fields;
import ua.nure.soprunov.SummaryTask4.db.entity.Flight;
import ua.nure.soprunov.SummaryTask4.db.entity.Request;
import ua.nure.soprunov.SummaryTask4.exception.AppException;
import ua.nure.soprunov.SummaryTask4.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Command that cancel previous application. Command allowed  for drivers.
 *
 * @author Soprunov Igor
 *
 */


public class CancelApplication extends Command {
    private static final Logger LOG = Logger.getLogger(CancelApplication.class);

    private static final long serialVersionUID = -823581367656691239L;

    DataSource datasource = DataSourceFactory
            .getDataSource(DataSourceType.MY_SQL_DATASOURCE);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType)
            throws IOException, ServletException, AppException {
        LOG.debug("Commands starts");

        String id = request.getParameter(Fields.ENTITY_ID);
        LOG.trace("Get request parameter: 'id' = " + id);

        Request newRequest = new Request();
        newRequest.setId(Long.parseLong(id));
        new RequestDaoImpl(datasource).delete(newRequest);

        // get menu items list
        List<Flight> listFlights = new  FlightDaoImpl(datasource).findAll();
        LOG.trace("Found in DB: listFlights --> " + listFlights);


        // put menu items list to the request
        request.setAttribute("listFlight", listFlights);
        LOG.trace("Set the request attribute: listFlights --> " + listFlights);

        List<Request> listRequest =  new RequestDaoImpl(datasource).findAll();
        LOG.trace("Found in DB: listRequest --> " + listRequest);

        // put menu items list to the request
        request.setAttribute("listRequest", listRequest);
        LOG.trace("Set the request attribute: listRequest --> " + listRequest);

        LOG.debug("Command finished");
        return Path.FIELD_LIST_APPLICATION ;
    }
}
