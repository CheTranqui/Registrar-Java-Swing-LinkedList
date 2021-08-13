import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

class RegistrarTest {
    LinkedList<Student> students = loadStudentList();
    Registrar registrar = new Registrar(students);

    private static LinkedList<Student> loadStudentList(){
        LinkedList<Student> s = new LinkedList<>();
        s.add(new Student("Andre", "Calderon",25));
        s.add(new Student("Micah", "Chapagai", 63));
        s.add(new Student("Chris", "Cutler", 22));
        s.add(new Student("James", "Donahue", 63));
        s.add(new Student("Ryan", "Lambert", 82));
        s.add(new Student("Stephen", "Mays", 49));
        s.add(new Student("Kasey", "Moore", 12));
        s.add(new Student("Timothy", "Moore", 74));
        s.add(new Student("Chaz", "Peterson", 23));
        s.add(new Student("Katrina", "Schaeffer", 31));
        s.add(new Student("Silvia", "Streeter", 13));
        return s;
    }

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