public class EmpleadoPermanente extends Empleado {
    private double bonoAnual;

    public EmpleadoPermanente(String idEmpleado, String nombre, double salarioBase, double bonoAnual) {
        super(idEmpleado, nombre, salarioBase);
        this.bonoAnual = bonoAnual;
    }

    @Override
    public double calcularCompensacion() {
        return this.salarioBase + this.bonoAnual;
    }
    
}
