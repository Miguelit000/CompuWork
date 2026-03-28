import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CompuWorkTests {

    private Departamento departamento;
    private EmpleadoPermanente empPermanente;
    private EmpleadoTemporal empTemporal;

    @BeforeEach
    public void setUp() {
        // Se ejecuta para tener un entorno limpio
        departamento = new Departamento("IT", "Tecnología");
        empPermanente = new EmpleadoPermanente("E1", "Juan Perez", 2000.0, 500.0);
        empTemporal = new EmpleadoTemporal("E2", "Maria Gomez", 1600.0, 10);
    }

    @Test
    public void testCalculoCompensacionPermanente() {
        // Salario (2000) + Bono (500) = 2500
        assertEquals(2500.0, empPermanente.calcularCompensacion(), "El cálculo del permanente falló");
    }

    @Test
    public void testCalculoCompensacionTemporal() {
        // Tarifa extra: (1600 / 160) * 1.5 = 15.0 por hora
        // Horas extras: 10 * 15.0 = 150.0
        // Total: 1600 + 150 = 1750.0
        assertEquals(1750.0, empTemporal.calcularCompensacion(), "El cálculo del temporal falló");
    }

    @Test
    public void testAsignacionDuplicadaLanzaExcepcion() throws AsignacionDepartamentoException {
        departamento.agregarEmpleado(empPermanente);
        
        // Validar que intentar añadir el mismo empleado lance la excepción personalizada
        assertThrows(AsignacionDepartamentoException.class, () -> {
            departamento.agregarEmpleado(empPermanente);
        });
    }

    @Test
    public void testRegistroDesempenoInvalidoLanzaExcepcion() {
        // Validar que un puntaje mayor a 100 lance excepción
        assertThrows(IllegalArgumentException.class, () -> {
            empPermanente.registrarDesempeno(150.0);
        });
    }
}