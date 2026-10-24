/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vistas;

import controlador.ControladorPersistencia;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import modelo.Comunidad;
import modelo.Consumo;
import modelo.Vivienda;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pedro
 */
public class PnlVentanaPrincipal extends javax.swing.JPanel {

    /**
     * Métrica 1: Porcentaje de viviendas dentro de los rangos básicos
     * (subsistencia). Evalúa Agua (<= 16 m3) y Energía (<= 173 kWh).
     */
    private double calcularPctRangoBasico(List<Vivienda> viviendas, int mes, int anio) {
        if (viviendas.isEmpty()) {
            return 0.0;
        }
        int viviendasEficientes = 0;

        for (Vivienda viv : viviendas) {
            Consumo agua = viv.obtenerConsumoPorPeriodoYServicio(mes, anio, "AGUA");
            Consumo energia = viv.obtenerConsumoPorPeriodoYServicio(mes, anio, "ENERGIA");

            // Validamos que exista el registro y que su estado sea el óptimo definido en tus clases
            boolean aguaOptima = (agua != null && agua.getValorConsumido() <= 16.0);
            boolean energiaOptima = (energia != null && energia.getValorConsumido() <= 173.0);

            if (aguaOptima && energiaOptima) {
                viviendasEficientes++;
            }
        }
        return ((double) viviendasEficientes / viviendas.size()) * 100;
    }

    /**
     * Métrica 2: Diferencia de Macromedición (Pérdidas técnicas o fugas).
     */
    private double calcularDiferenciaMacromedicion(List<Vivienda> viviendas, int mes, int anio) {
        double sumaMicromedidores = 0;

        for (Vivienda viv : viviendas) {
            Consumo agua = viv.obtenerConsumoPorPeriodoYServicio(mes, anio, "AGUA");
            if (agua != null) {
                sumaMicromedidores += agua.getValorConsumido();
            }
        }

        // En un entorno de producción, la lectura del macromedidor vendría de una base de datos.
        // Aquí simulamos una pérdida técnica (fuga) del 4.2% para observar el comportamiento matemático.
        double lecturaMacromedidorReal = sumaMicromedidores * 1.042;

        if (lecturaMacromedidorReal == 0) {
            return 0.0;
        }

        // Fórmula de desviación: ((Macro - SumaMicro) / Macro) * 100
        return ((lecturaMacromedidorReal - sumaMicromedidores) / lecturaMacromedidorReal) * 100;
    }

    /**
     * Métrica 3: Porcentaje de Reducción de Agua frente a la Línea Base (Mes
     * anterior).
     */
    private double calcularReduccionAgua(List<Vivienda> viviendas, int mesActual, int anioActual) {
        // Lógica para retroceder un mes manteniendo la coherencia del año
        int mesAnterior = (mesActual == 1) ? 12 : mesActual - 1;
        int anioAnterior = (mesActual == 1) ? anioActual - 1 : anioActual;

        double consumoTotalActual = 0;
        double consumoTotalAnterior = 0;

        for (Vivienda viv : viviendas) {
            Consumo aguaAct = viv.obtenerConsumoPorPeriodoYServicio(mesActual, anioActual, "AGUA");
            Consumo aguaAnt = viv.obtenerConsumoPorPeriodoYServicio(mesAnterior, anioAnterior, "AGUA");

            if (aguaAct != null) {
                consumoTotalActual += aguaAct.getValorConsumido();
            }
            if (aguaAnt != null) {
                consumoTotalAnterior += aguaAnt.getValorConsumido();
            }
        }

        if (consumoTotalAnterior == 0) {
            return 0.0; // Evita división por cero si no hay histórico
        }
        return ((consumoTotalAnterior - consumoTotalActual) / consumoTotalAnterior) * 100;
    }

    /**
     * Métrica 4: Intensidad Energética (kWh / día / habitante).
     */
    private double calcularIntensidadEnergetica(List<Vivienda> viviendas, int mes, int anio) {
        double energiaTotalComunidad = 0;

        // Estimación de densidad poblacional (se podría parametrizar en la clase Vivienda a futuro)
        int habitantesPromedioPorVivienda = 3;
        int totalHabitantesEstimados = viviendas.size() * habitantesPromedioPorVivienda;
        int diasFacturacion = 30;

        for (Vivienda viv : viviendas) {
            Consumo energia = viv.obtenerConsumoPorPeriodoYServicio(mes, anio, "ENERGIA");
            if (energia != null) {
                energiaTotalComunidad += energia.getValorConsumido();
            }
        }

        if (totalHabitantesEstimados == 0) {
            return 0.0;
        }

        return energiaTotalComunidad / (totalHabitantesEstimados * diasFacturacion);
    }

    /**
     * Orquesta los cálculos e inyecta el resumen final en el componente visual.
     */
    public void cargarCuadroResumen(List<Vivienda> viviendas, int mesFiltro, int anioFiltro) {
        // 1. PROCESAMIENTO MATEMÁTICO DE FILTROS NATIVOS
        double pctBasico = calcularPctRangoBasico(viviendas, mesFiltro, anioFiltro);
        double difMacro = calcularDiferenciaMacromedicion(viviendas, mesFiltro, anioFiltro);
        double redAgua = calcularReduccionAgua(viviendas, mesFiltro, anioFiltro);
        double intEnerg = calcularIntensidadEnergetica(viviendas, mesFiltro, anioFiltro);

        // 2. FORMATEO E INYECCIÓN ATÓMICA EN COMPONENTES VISUALES
        // c1: Viviendas en Rango Básico (Meta > 80% según Ley 142 / CRA 750)
        String colorC1 = (pctBasico > 80.0) ? "#2ECC71" : "#E74C3C"; // Verde o Rojo
        c1.setText(String.format("<html><span style='color: %s; font-weight: bold;'>%.1f%%</span></html>", colorC1, pctBasico));

        // c2: Diferencia de Macromedición (Meta < 5% según Decreto 302 de 2000)
        String colorC2 = (difMacro < 5.0) ? "#2ECC71" : "#E74C3C";
        c2.setText(String.format("<html><span style='color: %s; font-weight: bold;'>%.1f%%</span></html>", colorC2, difMacro));

        // c3: Reducción Consumo Agua (Meta >= 15% según Res. 0194 de 2025)
        String colorC3 = (redAgua >= 15.0) ? "#2ECC71" : "#E74C3C";
        c3.setText(String.format("<html><span style='color: %s; font-weight: bold;'>%.1f%%</span></html>", colorC3, redAgua));

        // c4: Intensidad Energética (Meta <= 2.69 kWh/día/hab - Promedio Cartagena)
        String colorC4 = (intEnerg <= 2.69) ? "#2ECC71" : "#E74C3C";
        c4.setText(String.format("<html><span style='color: %s; font-weight: bold;'>%.2f kWh/d</span></html>", colorC4, intEnerg));
    }

    private void cargarTablaRanking(List<Vivienda> viviendas) {
        // 1. OBTENER EL MODELO VISUAL DE LA TABLA
        DefaultTableModel modeloRanking = (DefaultTableModel) tblRanking.getModel();
        modeloRanking.setRowCount(0); // Limpieza preventiva de filas viejas

        // 2. ORDENAR LAS VIVIENDAS POR SU EFICIENCIA USANDO UN COMPARATOR
        // Esto reacomoda la lista en la RAM de mayor a menor porcentaje antes de pintarla
        Collections.sort(viviendas, new Comparator<Vivienda>() {
            @Override
            public int compare(Vivienda v1, Vivienda v2) {
                double ef1 = calcularPorcentajeEficiencia(v1);
                double ef2 = calcularPorcentajeEficiencia(v2);
                return Double.compare(ef2, ef1); // Orden descendente (de mayor a menor)
            }
        });

        // 3. RECORRER LA LISTA YA ORDENADA Y VOLCARLA EN LA INTERFAZ
        for (Vivienda viv : viviendas) {
            double porcentaje = calcularPorcentajeEficiencia(viv);

            // Formateamos el porcentaje para que se muestre estético (Ej: "85.3%")
            String porcentajeFormateado = String.format("%.1f", porcentaje) + "%";

            // Si la vivienda no tiene ningún consumo registrado aún, mostrará "0.0%" o puedes decidir no listarla
            Object[] fila = {
                viv.getIdVivienda(),
                viv.getNombrePropietario(),
                porcentajeFormateado
            };
            modeloRanking.addRow(fila);
        }
    }

    /**
     * Método auxiliar que calcula matemáticamente la eficiencia de una vivienda
     * específica.
     */
    private double calcularPorcentajeEficiencia(Vivienda viv) {
        List<Consumo> consumosViv = viv.getListaConsumos();

        // Si la vivienda no tiene registros históricos de consumo, su eficiencia base es 0
        if (consumosViv == null || consumosViv.isEmpty()) {
            return 0.0;
        }

        int conteoOptimos = 0;
        for (Consumo con : consumosViv) {
            if (con.getEstadoSostenibilidad().equalsIgnoreCase("Óptimo")) {
                conteoOptimos++;
            }
        }

        // Regla de tres: (Óptimos / Totales) * 100
        return ((double) conteoOptimos / consumosViv.size()) * 100;
    }

    public static void estilizarTablaGenerica(JTable tabla, JScrollPane scrollPane) {
        // 1. Altura de las filas (Le da aire y elegancia al texto)
        tabla.setRowHeight(35);

        // 2. Control de cuadrícula moderna (Líneas horizontales tenues, sin verticales)
        tabla.setShowHorizontalLines(true);
        tabla.setShowVerticalLines(false);
        tabla.setGridColor(new Color(230, 230, 230)); // Gris muy suave

        // 3. Colores de fondo y opacidad de las celdas
        tabla.setBackground(Color.WHITE);
        tabla.setOpaque(true);

        // 4. Estilizar el contenedor Scroll (Importante para eliminar el fondo gris viejo de Swing)
        if (scrollPane != null) {
            scrollPane.getViewport().setBackground(Color.WHITE);
            scrollPane.setBorder(null); // Quita el borde rústico por defecto
        }

        // 5. Fuente y color del texto de los registros
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setForeground(new Color(51, 51, 51)); // Gris oscuro moderno

        // 6. Personalización estética del Encabezado (Header)
        if (tabla.getTableHeader() != null) {
            tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
            tabla.getTableHeader().setBackground(Color.WHITE);
            tabla.getTableHeader().setForeground(new Color(102, 102, 102));
        }
    }

    private void cargarTablaAlertas(List<Consumo> consumos, List<Vivienda> viviendas) {
        DefaultTableModel modeloTabla = (DefaultTableModel) tblAlertas.getModel();
        modeloTabla.setRowCount(0); // Limpieza preventiva de la tabla

        // 1. Obtener la fecha en tiempo real
        LocalDate fechaActual = LocalDate.now();
        int anioActual = fechaActual.getYear();
        int mesActual = fechaActual.getMonthValue();

        for (Consumo con : consumos) {
            // 2. Filtro estricto: Solo mes actual, año actual y estado Extremo
            if (con.getPeriodo().getMes() == mesActual
                    && con.getPeriodo().getAnio() == anioActual
                    && con.getEstadoSostenibilidad().equalsIgnoreCase("Extremo")) {

                // 3. Identificar el tipo de servicio y su umbral límite extremo
                String tipoServicioTXT = con.getTipoServicio().toLowerCase(); // "agua", "gas", "energia"
                double valor = con.getValorConsumido();
                double limiteExtremo = 0;

                // Asignamos los umbrales lógicos del backend
                if (tipoServicioTXT.contains("agua")) {
                    limiteExtremo = 20.0;
                    tipoServicioTXT = "agua";
                } else if (tipoServicioTXT.contains("gas")) {
                    limiteExtremo = 13.75;
                    tipoServicioTXT = "gas";
                } else if (tipoServicioTXT.contains("energia") || tipoServicioTXT.contains("energía")) {
                    limiteExtremo = 216.25;
                    tipoServicioTXT = "energía";
                }

                // 4. Calcular el porcentaje de exceso (Evitando división por cero)
                int porcentajeExceso = 0;
                if (limiteExtremo > 0) {
                    double exceso = ((valor - limiteExtremo) / limiteExtremo) * 100;
                    porcentajeExceso = (int) Math.round(exceso); // Redondeamos a entero limpio
                }

                // 5. Vincular el ID de la vivienda correspondiente
                String idViviendaPropietaria = "Desconocido";
                for (Vivienda viv : viviendas) {
                    if (viv.getListaConsumos().contains(con)) {
                        idViviendaPropietaria = viv.getIdVivienda();
                        break;
                    }
                }

                // 6. Construir el detalle personalizado
                String detalleAlerta = "Consumo de " + tipoServicioTXT + " inusual (+" + porcentajeExceso + "%)";

                // 7. Insertar la fila en el JTable (id Vivienda, detalles de la alerta)
                modeloTabla.addRow(new Object[]{
                    idViviendaPropietaria,
                    detalleAlerta
                });
            }
        }
    }

    private void cargarMetricaCobertura(List<Vivienda> viviendas, List<Consumo> consumos) {
        // 1. OBTENER EL TIEMPO REAL DEL SISTEMA
        LocalDate fechaActual = LocalDate.now();
        int anioActual = fechaActual.getYear();
        int mesActual = fechaActual.getMonthValue(); // Nos devuelve un entero del 1 al 12

        int consumosMesActual = 0;

        // 2. CONTAR LOS CONSUMOS REGISTRADOS EN ESTE MES Y AÑO
        for (Consumo con : consumos) {
            // Accedemos a través de nuestra clase Periodo
            if (con.getPeriodo().getMes() == mesActual && con.getPeriodo().getAnio() == anioActual) {
                consumosMesActual++;
            }
        }

        // 3. CALCULAR EL TOTAL TEÓRICO ESPERADO (Viviendas registradas * 3 servicios básicos)
        int totalEsperado = viviendas.size() * 3;

        // 4. CONSTRUIR E INYECTAR LA CADENA VISUAL EN TU ETIQUETA (lblValorRegistroC)
        // Mostrará un formato idéntico a tu requerimiento: "15/60"
        String textoProporcion = consumosMesActual + " / " + totalEsperado;
        lblValorRegistroC.setText(textoProporcion);
    }

    private void cargarTarjetasMetricas(List<Vivienda> viviendas, List<Consumo> consumos) {
        // Métrica 1: Total de viviendas en el TXT
        lblValorViviendasR.setText(String.valueOf(viviendas.size()));

        // Métrica 2: Total de registros de consumos medidos
        lblValorConsumosR.setText(String.valueOf(consumos.size()));

        // Métrica 3: Proporción de cobertura en tiempo real
        cargarMetricaCobertura(viviendas, consumos);

    }

    private void metodoCargarDatos() {
        ControladorPersistencia persistencia = new ControladorPersistencia();

        // 1. Levantamos los archivos planos a la memoria RAM de manera relacional
        List<Comunidad> comunidades = persistencia.cargarComunidades();
        List<Vivienda> viviendas = persistencia.cargarViviendas(comunidades);
        List<Consumo> consumos = persistencia.cargarConsumos(viviendas);
        LocalDate fechaActual = LocalDate.now();
        int anioActual = fechaActual.getYear();
        int mesActual = fechaActual.getMonthValue();

        // 2. Despachamos de forma modular a las subfunciones específicas
        cargarTarjetasMetricas(viviendas, consumos);

        cargarTablaAlertas(consumos, viviendas);
        cargarTablaRanking(viviendas);
        cargarCuadroResumen(viviendas, mesActual, anioActual);

        //cargarModuloSostenibilidad(consumos);
    }

    public PnlVentanaPrincipal() {
        initComponents();
        estilizarTablaGenerica(tblRanking, jScrollPane2);
        estilizarTablaGenerica(tblAlertas, jScrollPane1);
        metodoCargarDatos();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pnlColumnaIzquierda = new javax.swing.JPanel();
        pnlFilaMetricas = new javax.swing.JPanel();
        pnlTarjetaMetrica1 = new javax.swing.JPanel();
        lblViviendasRegistradas = new javax.swing.JLabel();
        lblValorViviendasR = new javax.swing.JLabel();
        pnlTarjetaMetrica2 = new javax.swing.JPanel();
        lblConsumosRegistrados = new javax.swing.JLabel();
        lblValorConsumosR = new javax.swing.JLabel();
        pnlTarjetaMetrica3 = new javax.swing.JPanel();
        lblRegistroConsumos = new javax.swing.JLabel();
        lblValorRegistroC = new javax.swing.JLabel();
        pnlTarjetaAlertas = new javax.swing.JPanel();
        lblTituloAlertas = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAlertas = new javax.swing.JTable();
        pnlTarjetaRanking = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRanking = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        pnlColumnaDerecha = new javax.swing.JPanel();
        pnlResumenSostenibilidad = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblCuadroResumen = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        c1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        c2 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        c3 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        c4 = new javax.swing.JLabel();
        pnlReportes = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        pblBotonesReportes = new javax.swing.JPanel();
        btnReporteIndividual = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnReporteGeneral = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        pnlColumnaIzquierda.setPreferredSize(new java.awt.Dimension(1000, 900));
        pnlColumnaIzquierda.setLayout(new java.awt.GridBagLayout());

        pnlFilaMetricas.setBackground(new java.awt.Color(255, 255, 255));
        pnlFilaMetricas.setAlignmentX(0.0F);
        pnlFilaMetricas.setAlignmentY(0.0F);
        pnlFilaMetricas.setOpaque(false);
        pnlFilaMetricas.setLayout(new java.awt.GridLayout(1, 3, 20, 0));

        pnlTarjetaMetrica1.setBackground(new java.awt.Color(255, 255, 255));
        pnlTarjetaMetrica1.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 220, 220), 1, true), javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        pnlTarjetaMetrica1.setLayout(new java.awt.GridBagLayout());

        lblViviendasRegistradas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblViviendasRegistradas.setForeground(new java.awt.Color(0, 102, 102));
        lblViviendasRegistradas.setText("VIVIENDAS REGISTRADAS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        pnlTarjetaMetrica1.add(lblViviendasRegistradas, gridBagConstraints);

        lblValorViviendasR.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblValorViviendasR.setForeground(new java.awt.Color(0, 102, 102));
        lblValorViviendasR.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        pnlTarjetaMetrica1.add(lblValorViviendasR, gridBagConstraints);

        pnlFilaMetricas.add(pnlTarjetaMetrica1);

        pnlTarjetaMetrica2.setBackground(new java.awt.Color(255, 255, 255));
        pnlTarjetaMetrica2.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 220, 220), 1, true), javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        pnlTarjetaMetrica2.setLayout(new java.awt.GridBagLayout());

        lblConsumosRegistrados.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblConsumosRegistrados.setForeground(new java.awt.Color(0, 102, 102));
        lblConsumosRegistrados.setText("CONSUMOS REGISTRADOS");
        pnlTarjetaMetrica2.add(lblConsumosRegistrados, new java.awt.GridBagConstraints());

        lblValorConsumosR.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblValorConsumosR.setForeground(new java.awt.Color(0, 102, 102));
        lblValorConsumosR.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        pnlTarjetaMetrica2.add(lblValorConsumosR, gridBagConstraints);

        pnlFilaMetricas.add(pnlTarjetaMetrica2);

        pnlTarjetaMetrica3.setBackground(new java.awt.Color(255, 255, 255));
        pnlTarjetaMetrica3.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 220, 220), 1, true), javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        pnlTarjetaMetrica3.setLayout(new java.awt.GridBagLayout());

        lblRegistroConsumos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblRegistroConsumos.setForeground(new java.awt.Color(0, 102, 102));
        lblRegistroConsumos.setText("REGISTRO DE CONSUMO MENSUAL");
        pnlTarjetaMetrica3.add(lblRegistroConsumos, new java.awt.GridBagConstraints());

        lblValorRegistroC.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        lblValorRegistroC.setForeground(new java.awt.Color(0, 102, 102));
        lblValorRegistroC.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        pnlTarjetaMetrica3.add(lblValorRegistroC, gridBagConstraints);

        pnlFilaMetricas.add(pnlTarjetaMetrica3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 232;
        gridBagConstraints.ipady = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 10, 20);
        pnlColumnaIzquierda.add(pnlFilaMetricas, gridBagConstraints);

        pnlTarjetaAlertas.setBackground(new java.awt.Color(255, 255, 255));
        pnlTarjetaAlertas.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 220, 220), 1, true), javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        pnlTarjetaAlertas.setLayout(new java.awt.GridBagLayout());

        lblTituloAlertas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTituloAlertas.setForeground(new java.awt.Color(0, 102, 102));
        lblTituloAlertas.setText("ALERTAS CRÍTICAS RECIENTES");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 10, 0);
        pnlTarjetaAlertas.add(lblTituloAlertas, gridBagConstraints);

        tblAlertas.setForeground(new java.awt.Color(0, 102, 102));
        tblAlertas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Vivienda", "Detalle de la Alerta"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblAlertas);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        pnlTarjetaAlertas.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 10, 20);
        pnlColumnaIzquierda.add(pnlTarjetaAlertas, gridBagConstraints);

        pnlTarjetaRanking.setBackground(new java.awt.Color(255, 255, 255));
        pnlTarjetaRanking.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 220, 220), 1, true), javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        pnlTarjetaRanking.setLayout(new java.awt.GridBagLayout());

        tblRanking.setForeground(new java.awt.Color(0, 102, 102));
        tblRanking.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Vivienda", "Propietario", "% Eficiencia"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblRanking);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        pnlTarjetaRanking.add(jScrollPane2, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("RANKING DE VIVIENDAS MAS EFICIENTES");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 10, 0);
        pnlTarjetaRanking.add(jLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(10, 20, 10, 20);
        pnlColumnaIzquierda.add(pnlTarjetaRanking, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.6;
        gridBagConstraints.weighty = 1.0;
        add(pnlColumnaIzquierda, gridBagConstraints);

        pnlColumnaDerecha.setBackground(new java.awt.Color(204, 204, 204));
        pnlColumnaDerecha.setOpaque(false);
        pnlColumnaDerecha.setPreferredSize(new java.awt.Dimension(600, 500));
        pnlColumnaDerecha.setLayout(new java.awt.GridBagLayout());

        pnlResumenSostenibilidad.setBackground(new java.awt.Color(255, 255, 255));
        pnlResumenSostenibilidad.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        pnlResumenSostenibilidad.setPreferredSize(new java.awt.Dimension(400, 600));
        pnlResumenSostenibilidad.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setText("RESUMEN SOSTENIBLE DE LA COMUNIDAD");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 10, 0);
        pnlResumenSostenibilidad.add(jLabel2, gridBagConstraints);

        lblCuadroResumen.setBackground(new java.awt.Color(204, 204, 204));
        lblCuadroResumen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        lblCuadroResumen.setAlignmentX(0.0F);
        lblCuadroResumen.setAlignmentY(0.0F);
        lblCuadroResumen.setLayout(new java.awt.GridLayout(5, 3));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Métrica");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel3.setAlignmentY(0.0F);
        jLabel3.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), javax.swing.BorderFactory.createEmptyBorder(15, 10, 5, 10)));
        jLabel3.setOpaque(true);
        jLabel3.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        lblCuadroResumen.add(jLabel3);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Estado Óptimo");
        jLabel4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel4.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), javax.swing.BorderFactory.createEmptyBorder(15, 10, 5, 10)));
        jLabel4.setOpaque(true);
        jLabel4.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        lblCuadroResumen.add(jLabel4);

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel6.setText("<html>Viviendas en rango básico");
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel6.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), javax.swing.BorderFactory.createEmptyBorder(10, 10, 5, 10)));
        jLabel6.setOpaque(true);
        jLabel6.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        lblCuadroResumen.add(jLabel6);

        c1.setBackground(new java.awt.Color(255, 255, 255));
        c1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        c1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        c1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), javax.swing.BorderFactory.createEmptyBorder(10, 10, 5, 10)));
        c1.setOpaque(true);
        lblCuadroResumen.add(c1);

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel9.setText("<html>Diferencia de Macromedición");
        jLabel9.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel9.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), javax.swing.BorderFactory.createEmptyBorder(10, 10, 5, 10)));
        jLabel9.setOpaque(true);
        jLabel9.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        lblCuadroResumen.add(jLabel9);

        c2.setBackground(new java.awt.Color(255, 255, 255));
        c2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        c2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        c2.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), javax.swing.BorderFactory.createEmptyBorder(10, 10, 5, 10)));
        c2.setOpaque(true);
        lblCuadroResumen.add(c2);

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel12.setText("<html>Reducción de Consumo de Agua");
        jLabel12.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel12.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), javax.swing.BorderFactory.createEmptyBorder(10, 10, 5, 10)));
        jLabel12.setOpaque(true);
        lblCuadroResumen.add(jLabel12);

        c3.setBackground(new java.awt.Color(255, 255, 255));
        c3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        c3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        c3.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), javax.swing.BorderFactory.createEmptyBorder(10, 10, 5, 10)));
        c3.setOpaque(true);
        lblCuadroResumen.add(c3);

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel15.setText("<html>Intensidad Energética");
        jLabel15.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel15.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), javax.swing.BorderFactory.createEmptyBorder(10, 10, 5, 10)));
        jLabel15.setOpaque(true);
        lblCuadroResumen.add(jLabel15);

        c4.setBackground(new java.awt.Color(255, 255, 255));
        c4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        c4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        c4.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)), javax.swing.BorderFactory.createEmptyBorder(10, 10, 5, 10)));
        c4.setOpaque(true);
        lblCuadroResumen.add(c4);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        pnlResumenSostenibilidad.add(lblCuadroResumen, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.6;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 0);
        pnlColumnaDerecha.add(pnlResumenSostenibilidad, gridBagConstraints);

        pnlReportes.setBackground(new java.awt.Color(255, 255, 255));
        pnlReportes.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        pnlReportes.setPreferredSize(new java.awt.Dimension(710, 500));
        pnlReportes.setLayout(new java.awt.GridBagLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 102));
        jLabel7.setText("GENERAR REPORTES");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 10, 0);
        pnlReportes.add(jLabel7, gridBagConstraints);

        pblBotonesReportes.setBackground(new java.awt.Color(255, 255, 255));
        pblBotonesReportes.setLayout(new java.awt.GridLayout(1, 0, 50, 0));

        btnReporteIndividual.setBackground(new java.awt.Color(255, 255, 255));
        btnReporteIndividual.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        btnReporteIndividual.setPreferredSize(new java.awt.Dimension(100, 86));
        btnReporteIndividual.setLayout(new java.awt.GridBagLayout());

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 102, 102));
        jLabel8.setText("REPORTE VIVIENDAS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        btnReporteIndividual.add(jLabel8, gridBagConstraints);

        jLabel10.setText("LOGO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        btnReporteIndividual.add(jLabel10, gridBagConstraints);

        pblBotonesReportes.add(btnReporteIndividual);

        btnReporteGeneral.setBackground(new java.awt.Color(255, 255, 255));
        btnReporteGeneral.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true), javax.swing.BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        btnReporteGeneral.setPreferredSize(new java.awt.Dimension(100, 86));
        btnReporteGeneral.setLayout(new java.awt.GridBagLayout());

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 102, 102));
        jLabel11.setText("REPORTE COMUNITARIO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        btnReporteGeneral.add(jLabel11, gridBagConstraints);

        jLabel13.setText("LOGO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        btnReporteGeneral.add(jLabel13, gridBagConstraints);

        pblBotonesReportes.add(btnReporteGeneral);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 15, 20, 15);
        pnlReportes.add(pblBotonesReportes, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.4;
        pnlColumnaDerecha.add(pnlReportes, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 10, 20);
        add(pnlColumnaDerecha, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnReporteGeneral;
    private javax.swing.JPanel btnReporteIndividual;
    private javax.swing.JLabel c1;
    private javax.swing.JLabel c2;
    private javax.swing.JLabel c3;
    private javax.swing.JLabel c4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblConsumosRegistrados;
    private javax.swing.JPanel lblCuadroResumen;
    private javax.swing.JLabel lblRegistroConsumos;
    private javax.swing.JLabel lblTituloAlertas;
    private javax.swing.JLabel lblValorConsumosR;
    private javax.swing.JLabel lblValorRegistroC;
    private javax.swing.JLabel lblValorViviendasR;
    private javax.swing.JLabel lblViviendasRegistradas;
    private javax.swing.JPanel pblBotonesReportes;
    private javax.swing.JPanel pnlColumnaDerecha;
    private javax.swing.JPanel pnlColumnaIzquierda;
    private javax.swing.JPanel pnlFilaMetricas;
    private javax.swing.JPanel pnlReportes;
    private javax.swing.JPanel pnlResumenSostenibilidad;
    private javax.swing.JPanel pnlTarjetaAlertas;
    private javax.swing.JPanel pnlTarjetaMetrica1;
    private javax.swing.JPanel pnlTarjetaMetrica2;
    private javax.swing.JPanel pnlTarjetaMetrica3;
    private javax.swing.JPanel pnlTarjetaRanking;
    private javax.swing.JTable tblAlertas;
    private javax.swing.JTable tblRanking;
    // End of variables declaration//GEN-END:variables
}
