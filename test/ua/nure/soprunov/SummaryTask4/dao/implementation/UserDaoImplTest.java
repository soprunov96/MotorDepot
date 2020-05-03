package ua.nure.soprunov.SummaryTask4.dao.implementation;

import org.junit.*;
import ua.nure.soprunov.SummaryTask4.dao.datasource.DataSourceFactory;
import ua.nure.soprunov.SummaryTask4.dao.datasource.DataSourceType;
import ua.nure.soprunov.SummaryTask4.dao.entity.User;
import ua.nure.soprunov.SummaryTask4.exception.DBException;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.*;

public class UserDaoImplTest {
    private static User user;
    private static UserDaoImpl userDaoImpl;
    private static long userId;
    private static String userLogin;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("@BeforeClass");
        userDaoImpl = new UserDaoImpl(
                DataSourceFactory
                        .getDataSource(DataSourceType.MY_SQL_DATASOURCE_WITH_OUT_JNDI)) {
        };
    }


    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // clean up db
        User userToDelete = new User();
        userToDelete.setId(userId);
        userDaoImpl.delete(userToDelete);
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("@Before");
        user = new User();
        user.setLogin("Don3");
        user.setFirstName("Al");
        user.setLastName("Pacino");
        user.setPassword("1234");
        user.setRoleId(0);

        new UserDaoImpl(DataSourceFactory.getDataSource(DataSourceType.MY_SQL_DATASOURCE_WITH_OUT_JNDI)).create(user);

        // userLogin = user.getLogin();

//    user = userDao.findUserByLogin(userLogin);
        userId = user.getId();


    }

    @After
    public void tearDown() throws Exception {
        userDaoImpl.delete(user);
        user = null;
    }


    @Test
    public void findTest() throws DBException {
        assertNotNull(userDaoImpl.find(userId));
    }

    @Test
    public void update() throws DBException {
        user.setFirstName("newLogin");
        userDaoImpl.update(user);

        assertThat(user.getFirstName(), equalTo(userDaoImpl.find(user.getId())
                .getFirstName()));
    }

    @Test
    public void extractUser() {

    }

    @Test
    public void findUserByLogin() throws DBException {
        assertNotNull(userDaoImpl.findUserByLogin(user.getLogin()));

    }

    @Test
    public void findAllTest() throws DBException {
        List<User> users = userDaoImpl.findAll();
        assertThat(users.isEmpty(), is(false));
    }

//    @Test
//    public void deleteById() throws DBException {
//        userDaoImpl.deleteById(user.getId().toString());
//        assertThat(userDaoImpl.find(user.getId()), is(equalTo(null)));
//    }

    @Test
    public void createTest() throws DBException {
        userDaoImpl.delete(user);

        user = new User();
        user.setLogin("Ivan");
        user.setFirstName("Ivanov");
        user.setLastName("Ivanovich");
        user.setPassword("1234");
        user.setRoleId(0);

        userDaoImpl.create(user);

        assertThat(user.getId(), not(equalTo(-1)));

        userDaoImpl.delete(user);

    }

    @Test
    public void deleteTest() throws DBException {
        userDaoImpl.delete(user);
        assertThat(userDaoImpl.find(user.getId()), is(equalTo(null)));
    }


//    @Test
//    public void changeAtributesUser() throws DBException {
////        user.setLogin("newLogin");
////        user.setPassword("newPassword");
////        user.setFirstName("newFirstName");
////        user.setLastName("newlastname");
////        user.setRoleId(1);
////        userDao.changeAtributesUser(userId + "",user.getLogin(),user.getPassword(),user.getFirstName(),user.getLastName(),user.getRoleId()+"");
////        userDao.updateUser(user);
////
////        assertThat(user.getLogin(), equalTo(UserDao.findUser(userId)
////                .getLogin()));
//    }
}