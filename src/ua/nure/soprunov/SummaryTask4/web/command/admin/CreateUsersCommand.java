package ua.nure.soprunov.SummaryTask4.web.command.admin;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import ua.nure.soprunov.SummaryTask4.Path;
import ua.nure.soprunov.SummaryTask4.Util.ActionType;
import ua.nure.soprunov.SummaryTask4.Util.SecureHashing;
import ua.nure.soprunov.SummaryTask4.dao.datasource.DataSourceFactory;
import ua.nure.soprunov.SummaryTask4.dao.datasource.DataSourceType;
import ua.nure.soprunov.SummaryTask4.Util.Fields;
import ua.nure.soprunov.SummaryTask4.dao.implementation.UserDaoImpl;
import ua.nure.soprunov.SummaryTask4.dao.entity.User;
import ua.nure.soprunov.SummaryTask4.exception.AppException;
import ua.nure.soprunov.SummaryTask4.exception.DBException;
import ua.nure.soprunov.SummaryTask4.web.command.Command;


/**
 * Command that create new users. Command allowed only for admins.
 *
 * @author Soprunov Igor
 *
 */

public class CreateUsersCommand extends Command {

    private static final Logger LOG = Logger.getLogger(CreateUsersCommand.class);

    private static final long serialVersionUID = 7951182384151312104L;


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, ActionType actionType)
            throws IOException, ServletException, AppException {
        LOG.debug("Command execution starts");

        String result = null;

        if (actionType == ActionType.GET) {
            result = doGet(request, response);
        } else if (actionType == ActionType.POST) {
            result = doPost(request, response);
        }

        LOG.debug("Finished executing Command");
        return result;
    }

    private String doGet(HttpServletRequest request, HttpServletResponse response) throws DBException {
        LOG.debug("Commands starts");
        LOG.debug("return" + Path.PAGE_CREATE_USER);
        LOG.debug("Commands finished");
        return Path.PAGE_CREATE_USER;
    }

    /**
     * Create new user.
     *
     * @return path to the view all users.
     * @throws DBException
     */
    private String doPost(HttpServletRequest request, HttpServletResponse response) throws DBException{
        // get parameters from page
        LOG.debug("Commands starts");

        String login = request.getParameter(Fields.USER_LOGIN);
        String password = request.getParameter(Fields.USER_PASSWORD);
        String firstName = request.getParameter(Fields.USER_FIRST_NAME);
        String lastName = request.getParameter(Fields.USER_LAST_NAME);
        String roleId = request.getParameter(Fields.USER_ROLE_ID);

        String strongPassword = null;
        try {
             strongPassword =  SecureHashing.generateStrongPasswordHash(password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        
        User user = new User(login, strongPassword , firstName, lastName, Integer.parseInt(roleId));

        LOG.info("Create new: user --> " + user);
         new UserDaoImpl( DataSourceFactory
                 .getDataSource(DataSourceType.MY_SQL_DATASOURCE)).create(user);

        LOG.debug("Commands finished");
        LOG.debug("return " + Path.COMMAND_LIST_USERS);
        return Path.COMMAND_LIST_USERS;
    }

}
