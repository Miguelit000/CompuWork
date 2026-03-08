import java.util.Collection;;

public class ReporteDesempeno {

    public static String generarReporteIndividual(Empleado empleado) {
         if (empleado == null) {
            throw new IllegalArgumentException("Se requiere un objeto Empleado valido.");
         }

         return "--- Reporte de Desempeño Individual ---\n"  +
                "Nombre: " + empleado.getNombre() + " (ID: " + empleado.getIdEmpleado() + ")\n" +
                "Departamento: " + (empleado.getDepartamento() != null ? empleado.getDepartamento() : "Sin asignar") + "\n" +
                "Puntaje Actual: " + empleado.getPuntajeDesempeno() + "/100\n" +
                "Compensacion Calculada: $" + String.format("%.2f", empleado.calcularCompensacion()) + "\n" +
                "-----------------------------------------";
         
    }

    public static String generarReporteDepartamento(Departamento departamento) {
        Collection<Empleado> empleados = departamento.listarEmpleados();

        if (empleados.isEmpty()) {
            return "El departamento" + departamento.getNombre() + " no tiene empleados asignados.";
        }

        double sumaPuntajes = 0;
        for (Empleado e : empleados) {
            sumaPuntajes += e.getPuntajeDesempeno();
        }

        double promedio = sumaPuntajes / empleados.size();

        return "--- Reporte del Departamento: " + departamento.getNombre() + " ---\n" +
               "Total Empleados: " + empleados.size() + "\n" +
               "Puntaje Promedio del Departamento: " + String.format("%.2f", promedio) + "/100\n" +
               "-----------------------------------------";
    }
}