package ua.nure.soprunov.SummaryTask4.dao;

import ua.nure.soprunov.SummaryTask4.db.entity.User;
import ua.nure.soprunov.SummaryTask4.exception.DBException;


/**
 * Interface for all  UserDao  objects extends Dao with basic crud operation.
 * In this class we can add some specific operations for UserDao implementation
 *
 * @see ua.nure.soprunov.SummaryTask4.dao.Dao
 * @author Soprunov Igor
 */

public interface UserDao extends Dao<User> {

    /**
     * Finds User in database by specified login, it can be done because login
     * should be unique.
     *
     * @param login - user login
     * @return User instance with such login
     */

    public User findUserByLogin(String login) throws DBException;

}
