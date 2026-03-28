import java.util.HashMap;
import java.util.Map;
import java.util.Collection;


public class Departamento {
    private String idDepto;
    private String nombre;
    private Map<String, Empleado> empleados;

    public Departamento(String idDepto, String nombre) {
        this.idDepto = idDepto;
        this.nombre = nombre;
        this.empleados = new HashMap<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarEmpleado(Empleado empleado) throws AsignacionDepartamentoException {
        if (empleados.containsKey(empleado.getIdEmpleado())) {
            throw new AsignacionDepartamentoException("El empleado" + empleado.getNombre() + "Ya esta en este departamento.");
        }

        empleados.put(empleado.getIdEmpleado(), empleado);
        empleado.asignarDepartameno(this.nombre);
        System.out.println("Empleado" + empleado.getNombre() + "añadido a" + this.nombre + "exitosamente.");      

    }

    public void eleminarEmpleado(String idEmpleado) throws EmpleadoNoEncontradoException {
        if (empleados.containsKey((idEmpleado))) {
            Empleado emp = empleados.remove(idEmpleado);
            emp.asignarDepartameno(null);
            System.out.println("Empleado" + emp.getNombre() + " removido del departamento " + this.nombre + ".");
        } else {
            throw new EmpleadoNoEncontradoException("No se encontro el ID" + idEmpleado + " en el departamento " + this.nombre + ".");
        }
    }

    public Collection<Empleado> listarEmpleados() {
        return empleados.values();
    }
    
}
