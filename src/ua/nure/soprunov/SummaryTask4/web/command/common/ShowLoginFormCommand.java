package ua.nure.soprunov.SummaryTask4.web.command.common;

import org.apache.log4j.Logger;
import ua.nure.soprunov.SummaryTask4.Path;
import ua.nure.soprunov.SummaryTask4.Util.ActionType;
import ua.nure.soprunov.SummaryTask4.exception.AppException;
import ua.nure.soprunov.SummaryTask4.web.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Show login form page. Command allowed  for all users.
 *
 * @author Soprunov Igor
 */


public class ShowLoginFormCommand extends Command {


    private static final Logger LOG = Logger.getLogger(ShowLoginFormCommand .class);

    private static final long serialVersionUID = -3743712855660411050L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType)
            throws IOException, ServletException, AppException {
        LOG.debug("Command execution starts");

        LOG.debug("Command execution finished");
        return Path.PAGE_LOGIN;

    }
}
