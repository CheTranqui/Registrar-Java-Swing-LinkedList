import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class RegistrarTest {
    Registrar reg = new Registrar(new GUI());

    @Test
    void sortByFirstName() {
        reg.sortBy(reg.students,"firstName");
        assertEquals("Andre", reg.students.getFirst().getFirstName());
    }

    @Test
    void sortByLastName() {
        reg.sortBy(reg.students,"lastName");
        assertEquals("Streeter", reg.students.getLast().getLastName());
    }

    @Test
    void sortById() {
        reg.sortBy(reg.students,"Student ID");
        assertEquals(12, reg.students.get(0).getId());
    }

    @Test
    void getStudentById(){
        assertEquals("Kasey",(reg.getStudent(12, reg.students)).getFirstName());
    }
}