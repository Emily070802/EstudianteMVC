
package com.miapp.vista;

import com.miapp.controlador.EstudianteController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JOptionPane;

import javax.swing.JOptionPane;

import javax.swing.JOptionPane;

import javax.swing.JOptionPane;

/**
 * Vista: JFrame principal del módulo Estudiante.
 * Contiene un campo de búsqueda y una tabla de resultados.
 *
 * IMPORTANTE (MVC): esta clase NO conoce ni importa el Modelo (Estudiante).
 * Solo trabaja con tipos genéricos (Object[], List<Object[]>) que el
 * Controlador le entrega ya preparados. Así la Vista queda desacoplada
 * del Modelo y toda la comunicación pasa por el Controlador.
 */
public class EstudianteView extends JFrame {

    // ── Componentes UI ────────────────────────────────────────────────────────
private JTextField txtNombreBuscar;
private JTextField txtNombreAgregar;
private JTextField txtCarrera;
private JTextField txtPromedio;
private JButton btnBuscar;
private JButton btnAgregar;
private JTable tblResultados;
private DefaultTableModel modeloTabla;
private JLabel lblEstado;
private JComboBox<String> comboCriterio;
private JButton btnOrdenar;
private JButton btnMostrarTodos;


    // ── Controlador ───────────────────────────────────────────────────────────
    private EstudianteController controlador;

    // ── Constructor ───────────────────────────────────────────────────────────

    public EstudianteView() {
        initComponentes();
        initEventos();
    }

    // ── Inicialización de componentes ─────────────────────────────────────────

    private void initComponentes() {
        setTitle("Búsqueda de Estudiantes — MVC NetBeans");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel superior — barra de búsqueda
       JPanel panelSuperior = new JPanel(new GridLayout(3, 1, 5, 5));

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Buscar estudiante"));

        JLabel lblNombreBuscar = new JLabel("Nombre:");
        txtNombreBuscar = new JTextField(10);
        btnBuscar = new JButton("Buscar");
        btnMostrarTodos = new JButton("Mostrar todos");

        btnBuscar.setBackground(new Color(59, 139, 212));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);

        panelBusqueda.add(lblNombreBuscar);
        panelBusqueda.add(txtNombreBuscar);
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(btnMostrarTodos);


        JPanel panelAgregar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelAgregar.setBorder(BorderFactory.createTitledBorder("Agregar estudiante"));

        JLabel lblNombreAgregar = new JLabel("Nombre:");
        txtNombreAgregar = new JTextField(10);
        JLabel lblCarrera = new JLabel("Carrera:");
        txtCarrera = new JTextField(10);
        JLabel lblPromedio = new JLabel("Promedio:");
        txtPromedio = new JTextField(5);
        btnAgregar = new JButton("Agregar");

        panelAgregar.add(lblNombreAgregar);
        panelAgregar.add(txtNombreAgregar);
        panelAgregar.add(lblCarrera);
        panelAgregar.add(txtCarrera);
        panelAgregar.add(lblPromedio);
        panelAgregar.add(txtPromedio);
        panelAgregar.add(new JLabel(""));
        panelAgregar.add(btnAgregar);


        JPanel panelOrdenar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelOrdenar.setBorder(BorderFactory.createTitledBorder("Ordenar resultados"));

        comboCriterio = new JComboBox<>(new String[]{"Nombre", "Promedio"});
        btnOrdenar = new JButton("Ordenar");

        panelOrdenar.add(new JLabel("Criterio:"));
        panelOrdenar.add(comboCriterio);
        panelOrdenar.add(btnOrdenar);

        panelSuperior.add(panelBusqueda);
        panelSuperior.add(panelAgregar);
        panelSuperior.add(panelOrdenar);

        // Panel central — tabla de resultados
        String[] columnas = {"ID", "Nombre", "Carrera", "Promedio"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tblResultados = new JTable(modeloTabla);
        tblResultados.setRowHeight(24);
        tblResultados.getTableHeader().setReorderingAllowed(false);
        tblResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tblResultados);
        scroll.setBorder(BorderFactory.createTitledBorder("Resultados"));

        // Panel inferior — estado
        lblEstado = new JLabel("Ingrese un nombre y presione Buscar.");
        lblEstado.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        lblEstado.setForeground(Color.GRAY);

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll,        BorderLayout.CENTER);
        add(lblEstado,     BorderLayout.SOUTH);
    }

    // ── Eventos ───────────────────────────────────────────────────────────────

    private void initEventos() {
        btnBuscar.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                controlador.buscarEstudiante(txtNombreBuscar.getText().trim());
            }
        });

        btnMostrarTodos.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                controlador.mostrarTodos();
            }
        });
        
        btnAgregar.addActionListener((ActionEvent e) -> {

            if (controlador != null) {

                try {
                    double promedio = Double.parseDouble(
                            txtPromedio.getText().trim()
                    );

                    controlador.agregarEstudiante(
                            txtNombreAgregar.getText().trim(),
                            txtCarrera.getText().trim(),
                            promedio
                    );

                } catch (NumberFormatException ex) {
                    mostrarError("El promedio debe ser un número.");
                }
            }
        });

        btnOrdenar.addActionListener((ActionEvent e) -> {
            if (controlador != null) {
                String criterio = (String) comboCriterio.getSelectedItem();
                controlador.ordenarPor(criterio);
            }
        });
        
        // También buscar al presionar Enter en el campo de texto
        txtNombreBuscar.addActionListener((ActionEvent e) -> btnBuscar.doClick());
    }

    // ── Métodos públicos que llama el Controlador ─────────────────────────────
    // ninguno de estos métodos recibe un Estudiante: reciben
    // Object[] / List<Object[]> ya armados, que es lo único que la Vista
    // necesita saber para pintar la tabla.

    /**
     * Muestra una única fila en la tabla.
     * @param fila arreglo con {id, nombre, carrera, promedioFormateado}
     */
    public void mostrarEstudiante(Object[] fila) {
        limpiarTabla();
        agregarFila(fila);
        setEstado("Se encontró 1 estudiante.");
    }

    /**
     * Muestra varias filas en la tabla.
     * @param filas lista de arreglos {id, nombre, carrera, promedioFormateado}
     */
    public void mostrarEstudiantes(List<Object[]> filas) {
        limpiarTabla();
        if (filas == null || filas.isEmpty()) {
            setEstado("No se encontraron estudiantes con ese criterio.");
            return;
        }
        for (Object[] fila : filas) {
            agregarFila(fila);
        }
        setEstado("Se encontraron " + filas.size() + " estudiante(s).");
    }

    /**
     * Muestra un mensaje de error en la barra de estado.
     */
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
        setEstado("Error: " + mensaje);
    }

    /**
     * Devuelve el texto ingresado en el campo de nombre.
     */
    public String getNombreBuscado() {
        return txtNombreBuscar.getText().trim();
    }

    // ── Setter del controlador ────────────────────────────────────────────────

    public void setControlador(EstudianteController controlador) {
        this.controlador = controlador;
    }

    // ── Helpers privados ──────────────────────────────────────────────────────

    private void agregarFila(Object[] fila) {
        modeloTabla.addRow(fila);
    }

    private void limpiarTabla() {
        modeloTabla.setRowCount(0);
    }

    private void setEstado(String texto) {
        lblEstado.setText(texto);
    }

   public void mostrarConfirmacion(String mensaje) {
    JOptionPane.showMessageDialog(
        this,
        mensaje,
        "Confirmación",
        JOptionPane.INFORMATION_MESSAGE
    );
}
}
