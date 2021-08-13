import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class RegistrarTest {
    Registrar registrar = new Registrar();

    @Test
    void sortByFirstName() {
        Controller.sortBy(registrar.students,"firstName");
        assertEquals("Andre", registrar.students.getFirst().getFirstName());
    }

    @Test
    void sortByLastName() {
        Controller.sortBy(registrar.students,"lastName");
        assertEquals("Streeter", registrar.students.getLast().getLastName());
    }

    @Test
    void sortById() {
        Controller.sortBy(registrar.students,"Student ID");
        assertEquals(12, registrar.students.get(0).getId());
    }

    @Test
    void getStudentById(){
        assertEquals("Kasey",
                (Controller.getStudent(12, registrar.students)).getFirstName());
    }
}