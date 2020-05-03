package ua.nure.soprunov.SummaryTask4.dao.implementation;

import org.junit.*;
import ua.nure.soprunov.SummaryTask4.dao.datasource.DataSourceFactory;
import ua.nure.soprunov.SummaryTask4.dao.datasource.DataSourceType;
import ua.nure.soprunov.SummaryTask4.db.entity.Vehicle;
import ua.nure.soprunov.SummaryTask4.exception.DBException;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.*;


public class VehicleDaoImplTest {
    private static Vehicle vehicle;
    private static VehicleDaoImpl vehicleDaoImpl;
    private static long vehicleId;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("@BeforeClass");
        vehicleDaoImpl = new VehicleDaoImpl(
                DataSourceFactory
                        .getDataSource(DataSourceType.MY_SQL_DATASOURCE_WITH_OUT_JNDI)) {
        };
    }


    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // clean up db
        Vehicle vehicleToDelete = new Vehicle();
        vehicleToDelete.setId(vehicleId);
        vehicleDaoImpl.delete(vehicleToDelete);
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("@Before");
        vehicle = new Vehicle();
        vehicle.setStatus("ready");
        vehicle.setModel("BMV");
        vehicle.setRange(300);
        vehicle.setType("sedan");

        new VehicleDaoImpl(DataSourceFactory.getDataSource(DataSourceType.MY_SQL_DATASOURCE_WITH_OUT_JNDI)).create(vehicle);

        vehicleId = vehicle.getId();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("@After");
        vehicleDaoImpl.delete(vehicle);
        vehicle = null;
    }


    @Test
    public void create() throws DBException {
        vehicleDaoImpl.delete(vehicle);

        vehicle = new Vehicle();

        vehicle.setStatus("ready");
        vehicle.setModel("AUDI");
        vehicle.setRange(500);
        vehicle.setType("sedan");

        vehicleDaoImpl.create(vehicle);

        assertThat(vehicle.getId(), not(equalTo(-1)));
    }

    @Test
    public void update() throws DBException {
        vehicle.setRange(600);
        vehicleDaoImpl.update(vehicle);

        assertThat(vehicle.getRange(), equalTo(vehicleDaoImpl.find(vehicle.getId()).getRange()));
    }

    @Test
    public void delete() throws DBException {
        vehicleDaoImpl.delete(vehicle);
        assertThat(vehicleDaoImpl.find(vehicle.getId()), is(equalTo(null)));

    }

    @Test
    public void find() throws DBException {
        assertNotNull(vehicleDaoImpl.find(vehicleId));
    }

    @Test
    public void findAll() throws DBException {
        List<Vehicle> vehicles = vehicleDaoImpl.findAll();
        assertThat(vehicles.isEmpty(), is(false));


    }
}