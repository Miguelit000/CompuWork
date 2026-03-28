public class EmpleadoTemporal extends Empleado {
    private int horasExtras;

    public EmpleadoTemporal(String idEmpleado, String nombre, double salarioBase, int horasExtras) {
        super(idEmpleado, nombre, salarioBase);
        this.horasExtras = horasExtras;
    }

    @Override
    public double calcularCompensacion() {
        double tarifaExtra = (this.salarioBase / 160) * 1.5;
        return this.salarioBase + (this.horasExtras * tarifaExtra);
    }
    
}
