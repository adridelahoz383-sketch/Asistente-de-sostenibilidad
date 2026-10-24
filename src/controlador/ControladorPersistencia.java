package controlador;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import modelo.*;

public class ControladorPersistencia {

    private final String ARCHIVO_USUARIOS = "usuarios.txt";
    private final String ARCHIVO_COMUNIDADES = "comunidades.txt";
    private final String ARCHIVO_VIVIENDAS = "viviendas.txt";
    private final String ARCHIVO_CONSUMOS = "consumos.txt";

    public void guardarUsuarios(List<Usuario> listaUsuarios) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_USUARIOS))) {

            for (Usuario user : listaUsuarios) {

                bw.write(user.toTextoPlano());
                bw.newLine();

            }
            System.out.println(">>> Persistencia: Usuarios guardados exitosamente en el archivo plano.");

        } catch (IOException e) {
            System.err.println("Error crítico al guardar usuarios: " + e.getMessage());
        }
    }

    public List<Usuario> cargarUsuarios() {
        List<Usuario> listaCargada = new ArrayList<>();
        File archivo = new File(ARCHIVO_USUARIOS);

        if (!archivo.exists()) {
            System.out.println("Aviso: El archivo " + ARCHIVO_USUARIOS + " no existe. Se creará automáticamente al guardar.");
            return listaCargada;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {

                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] tokens = linea.split(";");

                if (tokens.length == 5) {

                    Usuario user = new Usuario(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]);
                    listaCargada.add(user);
                } else {
                    System.err.println("Advertencia: Línea corrompida u omitida en el archivo de usuarios: " + linea);
                }
            }
            System.out.println(">>> Persistencia: Se cargaron " + listaCargada.size() + " usuarios desde el TXT.");

        } catch (IOException e) {
            System.err.println("Error crítico al cargar usuarios: " + e.getMessage());
        }

        return listaCargada;
    }

    public void guardarComunidades(List<Comunidad> listaComunidades) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_COMUNIDADES))) {

            for (Comunidad com : listaComunidades) {

                bw.write(com.toTextoPlano());
                bw.newLine();
            }
            System.out.println(">>> Persistencia: Comunidades guardadas exitosamente.");

        } catch (IOException e) {
            System.err.println("Error crítico al guardar comunidades: " + e.getMessage());
        }
    }

    public List<Comunidad> cargarComunidades() {
        List<Comunidad> listaCargada = new ArrayList<>();
        File archivo = new File(ARCHIVO_COMUNIDADES);

        if (!archivo.exists()) {
            System.out.println("Aviso: El archivo " + ARCHIVO_COMUNIDADES + " no existe. Se creará al guardar.");
            return listaCargada;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] tokens = linea.split(";");

                if (tokens.length == 4) {
                    String idComunidad = tokens[0];
                    String nombre = tokens[1];
                    String ubicacion = tokens[2];

                    int estrato = Integer.parseInt(tokens[3]);

                    Comunidad com = new Comunidad(nombre, idComunidad, ubicacion, estrato);
                    listaCargada.add(com);
                } else {
                    System.err.println("Advertencia: Línea corrupta omitida en comunidades.txt: " + linea);
                }
            }
            System.out.println(">>> Persistencia: Se cargaron " + listaCargada.size() + " comunidades desde el TXT.");

        } catch (IOException | NumberFormatException e) {
            System.err.println("Error crítico al cargar comunidades: " + e.getMessage());
        }

        return listaCargada;
    }

    public void guardarViviendas(List<Vivienda> listaViviendas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_VIVIENDAS))) {
            for (Vivienda viv : listaViviendas) {
                // Usa el método toTextoPlano() que definimos en la clase Vivienda
                bw.write(viv.toTextoPlano());
                bw.newLine();
            }
            System.out.println(">>> Persistencia: Viviendas guardadas exitosamente en el archivo plano.");
        } catch (IOException e) {
            System.err.println("Error crítico al guardar viviendas: " + e.getMessage());
        }
    }

    public List<Vivienda> cargarViviendas(List<Comunidad> listaComunidadesTotales) {
        List<Vivienda> listaCargadaViviendas = new ArrayList<>();
        File archivo = new File(ARCHIVO_VIVIENDAS);

        if (!archivo.exists()) {
            System.out.println("Aviso: El archivo " + ARCHIVO_VIVIENDAS + " no existe. Se creará automáticamente al guardar.");
            return listaCargadaViviendas;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] tokens = linea.split(";");
                if (tokens.length >= 6) {
                    String idVivienda = tokens[0];
                    String idComunidadTXT = tokens[1];
                    double area = Double.parseDouble(tokens[2]);
                    String nombreProp = tokens[3];
                    String cedulaProp = tokens[4];
                    String direccion = tokens[5];

                    // Sincronización en memoria: Buscamos el objeto Comunidad real al que pertenece
                    Comunidad comunidadAsociada = null;
                    for (Comunidad com : listaComunidadesTotales) {
                        if (com.getIdComunidad().equals(idComunidadTXT)) {
                            comunidadAsociada = com;
                            break;
                        }
                    }

                    if (comunidadAsociada != null) {
                        // Instanciamos la vivienda pasándole su comunidad real
                        Vivienda nuevaVivienda = new Vivienda(comunidadAsociada, idVivienda, area, nombreProp, cedulaProp, direccion);

                        // Vinculación bidireccional en memoria RAM
                        comunidadAsociada.agregarVivienda(nuevaVivienda);
                        listaCargadaViviendas.add(nuevaVivienda);
                    } else {
                        System.err.println("Error de integridad: No se encontró la Comunidad " + idComunidadTXT + " para la vivienda " + idVivienda);
                    }
                }
            }
            System.out.println(">>> Persistencia: Se cargaron " + listaCargadaViviendas.size() + " viviendas desde el archivo plano.");

        } catch (IOException | NumberFormatException e) {
            System.err.println("Error crítico al cargar viviendas: " + e.getMessage());
        }

        return listaCargadaViviendas;
    }

    public void guardarConsumos(List<Vivienda> listaViviendas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_CONSUMOS))) {
            for (Vivienda viv : listaViviendas) {
                // Recorremos la lista de consumos interna de cada vivienda
                for (Consumo con : viv.getListaConsumos()) {
                    // Escribimos anteponiendo el ID de la vivienda para mantener el enlace relacional
                    bw.write(viv.getIdVivienda() + ";" + con.toTextoPlano());
                    bw.newLine();
                }
            }
            System.out.println(">>> Persistencia: Consumos guardados exitosamente en el archivo plano.");
        } catch (IOException e) {
            System.err.println("Error crítico al guardar consumos: " + e.getMessage());
        }
    }

    public List<Consumo> cargarConsumos(List<Vivienda> listaViviendasTotales) {
        List<Consumo> listaGeneralConsumos = new ArrayList<>();
        File archivo = new File(ARCHIVO_CONSUMOS);

        if (!archivo.exists()) {
            System.out.println("Aviso: El archivo " + ARCHIVO_CONSUMOS + " no existe. Se creará automáticamente al guardar.");
            return listaGeneralConsumos;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) {
                    continue;
                }

                String[] tokens = linea.split(";");
                if (tokens.length >= 6) {
                    String idViviendaTXT = tokens[0];
                    String tipoServicio = tokens[1];
                    double valor = Double.parseDouble(tokens[2]);
                    int mes = Integer.parseInt(tokens[3]);
                    int anio = Integer.parseInt(tokens[4]);
                    String estadoSost = tokens[5];

                    // Sincronización: Buscamos el objeto Vivienda real en la memoria
                    Vivienda viviendaEncontrada = null;
                    for (Vivienda viv : listaViviendasTotales) {
                        if (viv.getIdVivienda().equals(idViviendaTXT)) {
                            viviendaEncontrada = viv;
                            break;
                        }
                    }

                    if (viviendaEncontrada != null) {
                        // 1. Instanciamos el objeto Periodo encapsulando mes y año
                        Periodo periodoReconstruido = new Periodo(mes, anio);

                        // 2. Instanciamos la subclase polimórfica inyectando el Periodo
                        Consumo consumoReconstruido;
                        switch (tipoServicio.toUpperCase()) {
                            case "AGUA":
                                consumoReconstruido = new ConsumoAgua(valor, periodoReconstruido);
                                break;
                            case "GAS":
                                consumoReconstruido = new ConsumoGas(valor, periodoReconstruido);
                                break;
                            case "ENERGIA":
                            case "ENERGÍA":
                                consumoReconstruido = new ConsumoLuz(valor, periodoReconstruido);
                                break;
                            default:
                                System.err.println("Advertencia: Tipo de servicio desconocido omitido: " + tipoServicio);
                                continue;
                        }

                        // Restauramos el estado calculado previamente
                        consumoReconstruido.setEstadoSostenibilidad(estadoSost);

                        // 3. Vinculación bidireccional en caliente
                        viviendaEncontrada.agregarConsumo(consumoReconstruido);
                        listaGeneralConsumos.add(consumoReconstruido);
                    } else {
                        System.err.println("Error de integridad: No se encontró la Vivienda " + idViviendaTXT + " para el consumo.");
                    }
                }
            }
            System.out.println(">>> Persistencia: Se cargaron " + listaGeneralConsumos.size() + " consumos vinculados.");

        } catch (IOException | NumberFormatException e) {
            System.err.println("Error crítico al cargar consumos: " + e.getMessage());
        }

        return listaGeneralConsumos;
    }

}
