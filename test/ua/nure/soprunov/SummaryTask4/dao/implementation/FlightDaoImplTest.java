package ua.nure.soprunov.SummaryTask4.dao.implementation;

import org.junit.*;
import ua.nure.soprunov.SummaryTask4.dao.datasource.DataSourceFactory;
import ua.nure.soprunov.SummaryTask4.dao.datasource.DataSourceType;
import ua.nure.soprunov.SummaryTask4.dao.entity.Flight;
import ua.nure.soprunov.SummaryTask4.exception.DBException;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class FlightDaoImplTest {
    private static Flight flight;
    private static FlightDaoImpl flightDaoImpl;
    private static long flightId;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("@BeforeClass");
        flightDaoImpl = new FlightDaoImpl(
                DataSourceFactory
                        .getDataSource(DataSourceType.MY_SQL_DATASOURCE_WITH_OUT_JNDI)) {
        };
    }


    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // clean up db
        Flight flightToDelete = new Flight();
        flightToDelete.setId(flightId);
        flightDaoImpl.delete(flightToDelete);
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("@Before");
        flight = new Flight();
        flight.setName("Test flight");
        flight.setDate("22.02.2010");
        flight.setArrival("New York");
        flight.setDepart("Kiev");
        flight.setStatus("open");
        new FlightDaoImpl(DataSourceFactory.getDataSource(DataSourceType.MY_SQL_DATASOURCE_WITH_OUT_JNDI)).create(flight);

        flightId = flight.getId();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("@After");
        flightDaoImpl.delete(flight);
        flight = null;
    }


    @Test
    public void create() throws DBException {
        flightDaoImpl.delete(flight);

        flight = new Flight();

        flight.setName("Second flight");
        flight.setDate("22.02.2018");
        flight.setArrival("Nikolaev");
        flight.setDepart("Kiev");
        flight.setStatus("open");

        flightDaoImpl.create(flight);

        assertThat(flight.getId(), not(equalTo(-1)));

    }

    @Test
    public void update() throws DBException {
        flight.setName("new name");

        flightDaoImpl.update(flight);

        assertThat(flight.getName(), equalTo(flightDaoImpl.find(flight.getId()).getName()));
    }

    @Test
    public void delete() throws DBException {
        flightDaoImpl.delete(flight);
        assertThat(flightDaoImpl.find(flight.getId()), is(equalTo(null)));

    }

    @Test
    public void find() throws DBException {
        assertNotNull(flightDaoImpl.find(flightId));

    }

    @Test
    public void findAll() throws DBException {
        List<Flight> flights = flightDaoImpl.findAll();
        assertThat(flights.isEmpty(), is(false));

    }
}