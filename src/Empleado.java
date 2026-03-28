public abstract class Empleado {
    protected String idEmpleado;
    protected String nombre;
    protected double salarioBase;
    protected String departamento;
    protected double puntajeDesempeno;

    public Empleado(String idEmpleado, String nombre, double salarioBase) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.salarioBase = salarioBase;
        this.puntajeDesempeno = 0.0;
    }

    public String getIdEmpleado() {return idEmpleado; }
    public String getNombre() {return nombre; }
    public String getDepartamento() {return departamento; }

    public void asignarDepartameno(String departamentoNombre) {
        this.departamento = departamentoNombre;
    }

    public void registrarDesempeno(double puntaje) {
        if (puntaje >= 0 && puntaje <= 100) {
            this.puntajeDesempeno = puntaje;
        } else {
            throw new IllegalArgumentException("El puntaj debe estar entre 0 y 100.");
        }
    }

    public double getPuntajeDesempeno() {
        return puntajeDesempeno;
    }

    public abstract double calcularCompensacion();

    @Override
    public String toString() {
        return "[" + idEmpleado + "] " + nombre + " - Depto: " + (departamento != null ? departamento : "Ninguno");
    }
    
}
