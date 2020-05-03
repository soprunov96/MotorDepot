package ua.nure.soprunov.SummaryTask4.dao.implementation;

import org.junit.*;
import ua.nure.soprunov.SummaryTask4.dao.datasource.DataSourceFactory;
import ua.nure.soprunov.SummaryTask4.dao.datasource.DataSourceType;
import ua.nure.soprunov.SummaryTask4.db.entity.Request;
import ua.nure.soprunov.SummaryTask4.exception.DBException;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class RequestDaoImplTest {

    private static Request request;
    private static RequestDaoImpl requestDaoImpl;
    private static long requestId;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("@BeforeClass");
        requestDaoImpl = new RequestDaoImpl(
                DataSourceFactory
                        .getDataSource(DataSourceType.MY_SQL_DATASOURCE_WITH_OUT_JNDI)) {
        };
    }


    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // clean up db
        Request requestToDelete = new Request();
        requestToDelete.setId(requestId);
        requestDaoImpl.delete(requestToDelete);
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("@Before");
        request = new Request();
        request.setDriverId(3l);
        request.setCarType("sedan");
        request.setRange(500);

        new RequestDaoImpl(DataSourceFactory.getDataSource(DataSourceType.MY_SQL_DATASOURCE_WITH_OUT_JNDI)).create(request);

        requestId = request.getId();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("@After");
        requestDaoImpl.delete(request);
        request = null;
    }


    @Test
    public void create() throws DBException {
        requestDaoImpl.delete(request);

        request = new Request();

        request.setDriverId(3l);
        request.setCarType("universal");
        request.setRange(700);

       requestDaoImpl.create(request);

        assertThat(request.getId(), not(equalTo(-1)));

    }

    @Test
    public void update() {
    }

    @Test
    public void delete() throws DBException {
        requestDaoImpl.delete(request);
        assertThat(requestDaoImpl.find(request.getId()), is(equalTo(null)));

    }

    @Test
    public void find() throws DBException {
        assertNotNull(requestDaoImpl.find(requestId));
    }

    @Test
    public void findAll() throws DBException {
        List<Request> vehicles = requestDaoImpl.findAll();
        assertThat(vehicles.isEmpty(), is(false));
    }
}