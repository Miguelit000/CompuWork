import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CompuWorkGUI extends JFrame {
    
    // Almacenamiento en memoria
    private Map<String, Departamento> mapaDepartamentos;
    private Map<String, Empleado> mapaEmpleados;

    // Modelos de tabla para actualizar la vista dinámicamente
    private DefaultTableModel modeloDepartamentos;
    private DefaultTableModel modeloEmpleados;
    
    // Área exclusiva para los reportes
    private JTextArea areaReportes;

    public CompuWorkGUI() {
        mapaDepartamentos = new HashMap<>();
        mapaEmpleados = new HashMap<>();

        setTitle("CompuWork Dashboard - By Miguelit000");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel principal con pestañas
        JTabbedPane tabbedPane = new JTabbedPane();

        // pestañas
        tabbedPane.addTab("Gestión de Departamentos", crearPanelDepartamentos());
        tabbedPane.addTab("Gestión de Empleados", crearPanelEmpleados());
        tabbedPane.addTab("Reportes de Desempeño", crearPanelReportes());

        add(tabbedPane, BorderLayout.CENTER);
        
        inicializarDatosDePrueba();
    }

    // Metodo para actualzar las tablas 
    private void actualizarTablas() {
        // Limpiar tabla de departamentos
        modeloDepartamentos.setRowCount(0);
        for (Map.Entry<String, Departamento> entry : mapaDepartamentos.entrySet()) {
            Departamento d = entry.getValue();
            modeloDepartamentos.addRow(new Object[]{entry.getKey(), d.getNombre(), d.listarEmpleados().size()});
        }

        // Limpiar tabla de empleados
        modeloEmpleados.setRowCount(0);
        for (Map.Entry<String, Empleado> entry : mapaEmpleados.entrySet()) {
            Empleado e = entry.getValue();
            String tipo = (e instanceof EmpleadoPermanente) ? "Permanente" : "Temporal";
            String depto = (e.getDepartamento() != null) ? e.getDepartamento() : "Sin asignar";
            modeloEmpleados.addRow(new Object[]{e.getIdEmpleado(), e.getNombre(), tipo, depto, String.format("%.1f", e.getPuntajeDesempeno())});
        }
    }

    // Gestion de departamentos
    private JPanel crearPanelDepartamentos() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Botones en la parte superior
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnCrear = new JButton("Crear Departamento");
        JButton btnModificar = new JButton("Modificar Nombre");
        JButton btnEliminar = new JButton("Eliminar Departamento");

        // Tabla en el centro
        String[] columnas = {"ID", "Nombre del Departamento", "Total Empleados"};
        modeloDepartamentos = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modeloDepartamentos);
        JScrollPane scrollTabla = new JScrollPane(tabla);

        btnCrear.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "ID del nuevo departamento:");
            if (id == null || id.trim().isEmpty()) return;
            if (mapaDepartamentos.containsKey(id)) {
                mostrarError("Ya existe un departamento con el ID " + id);
                return;
            }
            String nombre = JOptionPane.showInputDialog(this, "Nombre del departamento:");
            if (nombre != null && !nombre.trim().isEmpty()) {
                mapaDepartamentos.put(id, new Departamento(id, nombre));
                mostrarExito("Departamento creado exitosamente.");
                actualizarTablas();
            }
        });

        btnModificar.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "ID del departamento a modificar:");
            if (mapaDepartamentos.containsKey(id)) {
                String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo nombre:");
                if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                    Departamento viejo = mapaDepartamentos.get(id);
                    Departamento nuevo = new Departamento(id, nuevoNombre);
                    // Trasladar empleados al nuevo objeto departamento
                    for(Empleado emp : viejo.listarEmpleados()) {
                        try { nuevo.agregarEmpleado(emp); } catch(Exception ex){}
                    }
                    mapaDepartamentos.put(id, nuevo);
                    mostrarExito("Nombre actualizado.");
                    actualizarTablas();
                }
            } else {
                mostrarError("Departamento no encontrado.");
            }
        });

        btnEliminar.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "ID del departamento a eliminar:");
            if (mapaDepartamentos.containsKey(id)) {
                mapaDepartamentos.remove(id);
                mostrarExito("Departamento eliminado.");
                actualizarTablas();
            } else {
                mostrarError("Departamento no encontrado.");
            }
        });

        panelBotones.add(btnCrear);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);

        panel.add(panelBotones, BorderLayout.NORTH);
        panel.add(scrollTabla, BorderLayout.CENTER);
        return panel;
    }

    //  Gestion de empleados
    private JPanel crearPanelEmpleados() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Botones en la parte superior
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnCrearPerm = new JButton("Añadir Permanente");
        JButton btnCrearTemp = new JButton("Añadir Temporal");
        JButton btnAsignarDepto = new JButton("Asignar Departamento");
        JButton btnActualizar = new JButton("Calificar Desempeño");
        JButton btnEliminar = new JButton("Eliminar Empleado");

        // Tabla en el centro
        String[] columnas = {"ID", "Nombre", "Tipo Contrato", "Departamento", "Puntaje Desempeño"};
        modeloEmpleados = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modeloEmpleados);
        JScrollPane scrollTabla = new JScrollPane(tabla);

        btnCrearPerm.addActionListener(e -> crearEmpleado(true));
        btnCrearTemp.addActionListener(e -> crearEmpleado(false));
        
        btnActualizar.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "ID del Empleado a calificar:");
            if (mapaEmpleados.containsKey(id)) {
                try {
                    String input = JOptionPane.showInputDialog(this, "Nuevo puntaje de desempeño (0-100):");
                    if (input == null) return;
                    double puntaje = Double.parseDouble(input);
                    mapaEmpleados.get(id).registrarDesempeno(puntaje);
                    mostrarExito("Desempeño actualizado.");
                    actualizarTablas();
                } catch (Exception ex) {
                    mostrarError("Valor inválido: " + ex.getMessage());
                }
            } else {
                mostrarError("Empleado no encontrado.");
            }
        });

        btnEliminar.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "ID del Empleado a eliminar:");
            if (mapaEmpleados.containsKey(id)) {
                mapaEmpleados.remove(id);
                for (Departamento d : mapaDepartamentos.values()) {
                    try { d.eleminarEmpleado(id); } catch(Exception ex){}
                }
                mostrarExito("Empleado eliminado.");
                actualizarTablas();
            } else {
                mostrarError("Empleado no encontrado.");
            }
        });

        btnAsignarDepto.addActionListener(e -> reasignarDepartamento());

        panelBotones.add(btnCrearPerm);
        panelBotones.add(btnCrearTemp);
        panelBotones.add(btnAsignarDepto);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        panel.add(panelBotones, BorderLayout.NORTH);
        panel.add(scrollTabla, BorderLayout.CENTER);
        return panel;
    }

    private void crearEmpleado(boolean esPermanente) {
        try {
            String id = JOptionPane.showInputDialog(this, "ID del Empleado:");
            if (id == null || id.trim().isEmpty() || mapaEmpleados.containsKey(id)) {
                mostrarError("ID inválido o ya existente."); return;
            }
            String nombre = JOptionPane.showInputDialog(this, "Nombre:");
            if (nombre == null) return;
            
            double salario = Double.parseDouble(JOptionPane.showInputDialog(this, "Salario Base:"));
            
            Empleado emp;
            if (esPermanente) {
                double bono = Double.parseDouble(JOptionPane.showInputDialog(this, "Bono Anual:"));
                emp = new EmpleadoPermanente(id, nombre, salario, bono);
            } else {
                int horas = Integer.parseInt(JOptionPane.showInputDialog(this, "Horas Extras estimadas:"));
                emp = new EmpleadoTemporal(id, nombre, salario, horas);
            }
            
            mapaEmpleados.put(id, emp);
            mostrarExito("Empleado registrado en el sistema.");
            actualizarTablas();
            
        } catch (NumberFormatException ex) {
            mostrarError("Formato numérico incorrecto.");
        }
    }

    private void reasignarDepartamento() {
        String idEmp = JOptionPane.showInputDialog(this, "ID del Empleado:");
        if (idEmp == null || !mapaEmpleados.containsKey(idEmp)) {
            mostrarError("Empleado no encontrado."); return;
        }
        
        String idDepto = JOptionPane.showInputDialog(this, "ID del Departamento destino:");
        if (idDepto == null || !mapaDepartamentos.containsKey(idDepto)) {
            mostrarError("Departamento no encontrado."); return;
        }

        Empleado emp = mapaEmpleados.get(idEmp);
        Departamento nuevoDepto = mapaDepartamentos.get(idDepto);

        for (Departamento d : mapaDepartamentos.values()) {
            try { d.eleminarEmpleado(idEmp); } catch(Exception ex){}
        }

        try {
            nuevoDepto.agregarEmpleado(emp);
            mostrarExito("Empleado asignado al departamento " + nuevoDepto.getNombre());
            actualizarTablas();
        } catch (AsignacionDepartamentoException ex) {
            mostrarError(ex.getMessage());
        }
    }

    // Generacion de reportes
    private JPanel crearPanelReportes() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnReporteIndiv = new JButton("Reporte por Empleado");
        JButton btnReporteDepto = new JButton("Reporte por Departamento");

        areaReportes = new JTextArea();
        areaReportes.setEditable(false);
        areaReportes.setFont(new Font("Monospaced", Font.PLAIN, 14));
        areaReportes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollReportes = new JScrollPane(areaReportes);

        btnReporteIndiv.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "Ingrese el ID del Empleado:");
            if (mapaEmpleados.containsKey(id)) {
                areaReportes.setText(ReporteDesempeno.generarReporteIndividual(mapaEmpleados.get(id)));
            } else if (id != null) {
                mostrarError("Empleado no encontrado.");
            }
        });

        btnReporteDepto.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(this, "Ingrese el ID del Departamento:");
            if (mapaDepartamentos.containsKey(id)) {
                areaReportes.setText(ReporteDesempeno.generarReporteDepartamento(mapaDepartamentos.get(id)));
            } else if (id != null) {
                mostrarError("Departamento no encontrado.");
            }
        });

        panelBotones.add(btnReporteIndiv);
        panelBotones.add(btnReporteDepto);

        panel.add(panelBotones, BorderLayout.NORTH);
        panel.add(scrollReportes, BorderLayout.CENTER);
        return panel;
    }


    private void mostrarExito(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Operación Exitosa", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void inicializarDatosDePrueba() {
        Departamento d1 = new Departamento("D01", "Recursos Humanos");
        Departamento d2 = new Departamento("D02", "Desarrollo de Software");
        mapaDepartamentos.put("D01", d1);
        mapaDepartamentos.put("D02", d2);

        EmpleadoPermanente emp1 = new EmpleadoPermanente("E001", "Laura Gomez", 3500, 500);
        emp1.registrarDesempeno(95.0);
        mapaEmpleados.put("E001", emp1);
        
        try { d1.agregarEmpleado(emp1); } catch (Exception e) {}
        
        actualizarTablas();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new CompuWorkGUI().setVisible(true);
        });
    }
}