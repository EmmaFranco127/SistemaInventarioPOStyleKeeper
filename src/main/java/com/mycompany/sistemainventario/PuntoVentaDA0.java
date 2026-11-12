/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemainventario;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.math.RoundingMode;
/**
 *
 * @author franc
 */
public class PuntoVentaDA0 {
    
    public static class Producto {

        public final int id;
        public final String sku;
        public final String modelo;
        public final String talla;
        public final BigDecimal precio;
        public final int stock;

        public Producto(int id, String sku, String modelo,
                        String talla, BigDecimal precio, int stock) {
            this.id = id;
            this.sku = sku;
            this.modelo = modelo;
            this.talla = talla;
            this.precio = precio;
            this.stock = stock;
        }

        @Override
        public String toString() {
            return sku + " | " + modelo
                    + " | Talla: " + talla
                    + " | $" + precio.toPlainString()
                    + " | Stock: " + stock;
        }
    }
    
     public static class ItemVenta {

        public final Producto producto;
        public int cantidad;

        public ItemVenta(Producto producto, int cantidad) {
            this.producto = producto;
            this.cantidad = cantidad;
        }

        public BigDecimal subtotal() {
            return producto.precio.multiply(BigDecimal.valueOf(cantidad));
        }
    }
     
     private Connection abrirConexion() throws SQLException {
        Connection con = new Conexion().getConnection();

        if (con == null) {
            throw new SQLException(
                    "No se pudo conectar con la base de datos.");
        }

        return con;
    }

    public List<Producto> buscarProductos(String texto) throws SQLException {
        List<Producto> productos = new ArrayList<>();

        String sql =
                "SELECT Id_Producto, SKU, Modelo, Talla, "
                + "Precio_Referencia, Existencias "
                + "FROM producto "
                + "WHERE Activo = 1 "
                + "AND (SKU LIKE ? OR Modelo LIKE ? "
                + "OR CAST(Id_Producto AS CHAR) = ?) "
                + "ORDER BY Modelo, Talla LIMIT 100";

        try (Connection con = abrirConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, "%" + texto + "%");
            ps.setString(2, "%" + texto + "%");
            ps.setString(3, texto);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BigDecimal precio =
                            rs.getBigDecimal("Precio_Referencia");

                    if (precio == null || precio.signum() < 0) {
                        continue;
                    }

                    productos.add(new Producto(
                            rs.getInt("Id_Producto"),
                            rs.getString("SKU"),
                            rs.getString("Modelo"),
                            rs.getString("Talla"),
                            precio,
                            rs.getInt("Existencias")
                    ));
                }
            }
        }

        return productos;
    }
    
    public List<Object[]> consultarHistorial(String texto)
        throws SQLException {

    List<Object[]> filas = new ArrayList<>();

    String sql =
            "SELECT v.Id_Venta, v.Fecha_Hora, v.Total, "
            + "v.Estado, u.Nombre AS Vendedor "
            + "FROM venta v "
            + "LEFT JOIN usuario u ON u.Id_Usuario = v.Id_Usuario "
            + "WHERE CAST(v.Id_Venta AS CHAR) LIKE ? "
            + "OR COALESCE(u.Nombre, '') LIKE ? "
            + "OR v.Estado LIKE ? "
            + "OR CAST(v.Fecha_Hora AS CHAR) LIKE ? "
            + "ORDER BY v.Fecha_Hora DESC, v.Id_Venta DESC "
            + "LIMIT 300";

    try (Connection con = abrirConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        String filtro = "%" + texto + "%";

        for (int i = 1; i <= 4; i++) {
            ps.setString(i, filtro);
        }

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                filas.add(new Object[]{
                    rs.getInt("Id_Venta"),
                    rs.getTimestamp("Fecha_Hora"),
                    rs.getBigDecimal("Total"),
                    rs.getString("Estado"),
                    rs.getString("Vendedor")
                });
            }
        }
    }

    return filas;
}
    
    public int registrarVenta(int idUsuario,
                          List<ItemVenta> carrito,
                          BigDecimal recibido) throws SQLException {

        if (!Sesion.puedeVentas()
            || Sesion.getIdUsuario() != idUsuario) {

            throw new SQLException(
                "La sesión no está autorizada para registrar ventas.");
    }

        if (carrito == null || carrito.isEmpty()) {
            throw new SQLException("La venta no tiene artículos.");
    }

        if (recibido == null || recibido.signum() < 0) {
            throw new SQLException("El efectivo recibido no es válido.");
    }

    BigDecimal total = BigDecimal.ZERO;

        for (ItemVenta item : carrito) {

            if (item == null
                || item.producto == null
                || item.producto.precio == null
                || item.cantidad <= 0
                || item.producto.precio.signum() < 0) {

                throw new SQLException(
                    "Hay un artículo con datos no válidos.");
            }

            total = total.add(item.subtotal());
        }

    try {
        total = total.setScale(2, RoundingMode.UNNECESSARY);
        recibido = recibido.setScale(2, RoundingMode.UNNECESSARY);

    } catch (ArithmeticException ex) {
        throw new SQLException(
                "Los importes deben tener como máximo dos decimales.",
                ex);
    }

        if (recibido.compareTo(total) < 0) {
            throw new SQLException(
                "El efectivo recibido no cubre el total.");
    }

    BigDecimal cambio = recibido.subtract(total);

        try (Connection con = abrirConexion()) {

            con.setAutoCommit(false);

        try {
            String sqlUsuario =
                    "SELECT Rol FROM usuario "
                    + "WHERE Id_Usuario = ? AND Activo = 1";

            try (PreparedStatement ps =
                    con.prepareStatement(sqlUsuario)) {

                ps.setInt(1, idUsuario);

                try (ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {
                        throw new SQLException(
                                "El usuario no existe o está inactivo.");
                    }

                    String rolBD = rs.getString("Rol");

                    String rol = rolBD == null
                            ? ""
                            : rolBD.trim().toUpperCase(
                                    java.util.Locale.ROOT);

                    if (!"ADMINISTRADOR".equals(rol)
                            && !"ADMIN".equals(rol)
                            && !"CAJERO".equals(rol)) {

                        throw new SQLException(
                                "El rol del usuario no puede vender.");
                    }
                }
            }

            String sqlStock =
                    "UPDATE producto "
                    + "SET Existencias = Existencias - ? "
                    + "WHERE Id_Producto = ? "
                    + "AND Activo = 1 "
                    + "AND Existencias >= ? "
                    + "AND Precio_Referencia = ?";

            try (PreparedStatement ps =
                    con.prepareStatement(sqlStock)) {

                for (ItemVenta item : carrito) {

                    ps.setInt(1, item.cantidad);
                    ps.setInt(2, item.producto.id);
                    ps.setInt(3, item.cantidad);
                    ps.setBigDecimal(4, item.producto.precio);

                    if (ps.executeUpdate() != 1) {
                        throw new SQLException(
                                "No se pudo vender: "
                                + item.producto.modelo
                                + ". Revisa existencias, estado o precio.");
                    }
                }
            }

            int idVenta;

            String sqlVenta =
                    "INSERT INTO venta "
                    + "(Fecha_Hora, Total, Metodo_Pago, Estado, "
                    + "Id_Usuario, Dinero_Recibido, Cambio) "
                    + "VALUES "
                    + "(NOW(), ?, 'Efectivo', 'Completada', ?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(
                    sqlVenta,
                    Statement.RETURN_GENERATED_KEYS)) {

                ps.setBigDecimal(1, total);
                ps.setInt(2, idUsuario);
                ps.setBigDecimal(3, recibido);
                ps.setBigDecimal(4, cambio);

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (!rs.next()) {
                        throw new SQLException(
                                "No se obtuvo el folio de la venta.");
                    }

                    idVenta = rs.getInt(1);
                }
            }

            String sqlDetalle =
                    "INSERT INTO venta_producto "
                    + "(Id_Venta, Id_Producto, Cantidad, "
                    + "Precio_Unitario, Subtotal) "
                    + "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement ps =
                    con.prepareStatement(sqlDetalle)) {

                for (ItemVenta item : carrito) {

                    ps.setInt(1, idVenta);
                    ps.setInt(2, item.producto.id);
                    ps.setInt(3, item.cantidad);
                    ps.setBigDecimal(4, item.producto.precio);
                    ps.setBigDecimal(5, item.subtotal());

                    ps.executeUpdate();
                }
            }

            con.commit();
            return idVenta;

        } catch (SQLException | RuntimeException ex) {

            try {
                con.rollback();

            } catch (SQLException rollbackError) {
                ex.addSuppressed(rollbackError);
            }

            throw ex;
        }
    }
  }
}
