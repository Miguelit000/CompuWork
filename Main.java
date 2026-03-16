public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Gestión CompuWork...\n");

        // 1. Crear un Departamento
        Departamento deptoIT = new Departamento("D01", "Tecnología");

        // 2. Crear Empleados (Demostración de Polimorfismo)
        EmpleadoPermanente emp1 = new EmpleadoPermanente("E001", "Ana Torres", 3000.00, 500.00);
        EmpleadoTemporal emp2 = new EmpleadoTemporal("E002", "Carlos Ruiz", 1500.00, 20);

        // 3. Registrar el desempeño de los empleados
        try {
            emp1.registrarDesempeno(92.5);
            emp2.registrarDesempeno(85.0);
            
            // Ejemplo de validación: Descomentar la siguiente línea lanzaría un IllegalArgumentException
            // emp2.registrarDesempeno(150.0); 
        } catch (IllegalArgumentException e) {
            System.out.println("Error al registrar desempeño: " + e.getMessage());
        }

        // 4. Asignar empleados al departamento (Manejo de Excepciones Personalizadas)
        try {
            deptoIT.agregarEmpleado(emp1);
            deptoIT.agregarEmpleado(emp2);
            
            // Ejemplo de validación: Intentar agregar al mismo empleado dos veces
            // deptoIT.agregarEmpleado(emp1); 
            
        } catch (AsignacionDepartamentoException e) {
            System.out.println("Error de asignación: " + e.getMessage());
        }

        System.out.println("\nGenerando reportes del sistema...\n");

        // 5. Imprimir Reportes Individuales
        System.out.println(ReporteDesempeno.generarReporteIndividual(emp1));
        System.out.println(ReporteDesempeno.generarReporteIndividual(emp2));

        // 6. Imprimir Reporte del Departamento
        System.out.println(ReporteDesempeno.generarReporteDepartamento(deptoIT));
    }
}