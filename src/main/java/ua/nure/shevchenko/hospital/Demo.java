package ua.nure.shevchenko.hospital;


import ua.nure.shevchenko.hospital.database.ConnectionPool;
import ua.nure.shevchenko.hospital.database.TransactionManager;
import ua.nure.shevchenko.hospital.model.Doctor;
import ua.nure.shevchenko.hospital.model.Job;
import ua.nure.shevchenko.hospital.model.Role;
import ua.nure.shevchenko.hospital.repository.DoctorRepositoryImpl;
import ua.nure.shevchenko.hospital.repository.UserRepositoryImpl;
import ua.nure.shevchenko.hospital.service.DoctorService;
import ua.nure.shevchenko.hospital.service.UserService;
import ua.nure.shevchenko.hospital.service.implement.DoctorServiceImpl;
import ua.nure.shevchenko.hospital.service.implement.UserServiceImpl;


public class Demo {

    public static void main(String[] args) {
        Context.put("transactionManager", new TransactionManager(ConnectionPool.setUpPool()));
        Context.put("userRepository", new UserRepositoryImpl());
        Context.put("doctorRepository", new DoctorRepositoryImpl());

        UserService service = new UserServiceImpl();
        DoctorService doctorService = new DoctorServiceImpl();

        Doctor doctor = new Doctor();
        doctor.setName("Evgeniy");
        doctor.setSurname("Shevchenko");
        doctor.setEmail("bla@gmail.com");
        doctor.setPassword("password");
        doctor.setRole(Role.WORKER);
        doctor.setJob(Job.ENT);
        doctor.setPatienceCount(5);
        doctorService.addDoctor(doctor);
    }
}
