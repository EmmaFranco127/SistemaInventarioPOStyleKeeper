/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemainventario;

/**
 *
 * @author franc
 */
public final class Sesion {
    
    private static int idUsuario;
    private static String rol = "";

    private Sesion() {
    }

    public static void iniciar(int id, String rolBD) {

        String rolNormalizado = rolBD == null
                ? ""
                : rolBD.trim().toUpperCase(java.util.Locale.ROOT);

        // Admitir estos dos nombres para administrador.
        if ("ADMIN".equals(rolNormalizado)) {
            rolNormalizado = "ADMINISTRADOR";
        }

        if (id <= 0
                || !("ADMINISTRADOR".equals(rolNormalizado)
                || "ENCARGADO_INVENTARIO".equals(rolNormalizado)
                || "CAJERO".equals(rolNormalizado))) {

            throw new IllegalArgumentException(
                    "Usuario o rol no reconocido: " + rolBD);
        }

        idUsuario = id;
        rol = rolNormalizado;
    }

    public static int getIdUsuario() {
        return idUsuario;
    }

    public static boolean activa() {
        return idUsuario > 0;
    }

    public static boolean esAdministrador() {
        return activa() && "ADMINISTRADOR".equals(rol);
    }

    public static boolean puedeInventario() {
        return activa()
                && (esAdministrador()
                || "ENCARGADO_INVENTARIO".equals(rol));
    }

    public static boolean puedeVentas() {
        return activa()
                && (esAdministrador() || "CAJERO".equals(rol));
    }

    public static boolean puedeEmpleados() {
        return esAdministrador();
    }

    public static void cerrar() {
        idUsuario = 0;
        rol = "";
    }
}
