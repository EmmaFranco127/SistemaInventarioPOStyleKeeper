-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: stylekeeper
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `actividad_reciente`
--

DROP TABLE IF EXISTS `actividad_reciente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `actividad_reciente` (
  `IdActividad` int NOT NULL AUTO_INCREMENT,
  `Modulo` varchar(50) NOT NULL,
  `Accion` varchar(50) NOT NULL,
  `Descripcion` varchar(100) NOT NULL,
  `FechaHora` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`IdActividad`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actividad_reciente`
--

LOCK TABLES `actividad_reciente` WRITE;
/*!40000 ALTER TABLE `actividad_reciente` DISABLE KEYS */;
INSERT INTO `actividad_reciente` VALUES (1,'Categorias','Registrar','Se registró una nueva categoría: Calzado','2026-05-25 10:38:53'),(2,'Categorias','Registrar','Se registró una nueva categoría: Ropa','2026-05-25 10:40:12'),(3,'Proveedor','Registrar','Se registró un nuevo proveedor: Levís','2026-05-25 10:42:52'),(4,'Proveedor','Registrar','Se registró un nuevo proveedor: Levis','2026-05-25 10:43:37'),(5,'Proveedor','Eliminar','Se eliminó el proveedor: Levis','2026-05-25 10:49:43'),(6,'Empleados','Registrar','Se registró un nuevo empleado: Maria Belen Chacon Almaraz','2026-05-25 10:53:56'),(7,'Empleados','Eliminar','Se eliminó el empleado: Maria Belen Chacon Almaraz','2026-05-25 10:54:30'),(8,'Empleados','Editar','Se actualizó el empleado: Oswaldo Alejandro Murillo Barrera','2026-05-25 10:54:39'),(9,'Categorias','Registrar','Se registró una nueva categoría: Calzado','2026-05-25 11:00:33'),(10,'Categorias','Editar','Se edito la nueva categoría: Calzado','2026-05-25 11:00:47'),(11,'Categoria','Eliminar','Se eliminó la categoria: Calzado','2026-05-25 11:00:54'),(12,'Producto','Registro','Se registró un nuevo producto: Tenis','2026-05-25 11:01:42'),(13,'Producto','Registro','Se registró un nuevo producto: Playera','2026-05-25 11:02:48'),(14,'Producto','Editar','Se editó el producto: Tenis','2026-05-25 11:03:16'),(15,'Productos','Eliminar','Se elimino el producto: Playera','2026-05-25 11:03:49'),(16,'Producto','Registro','Se registró un nuevo producto: Pantalon','2026-05-25 11:05:21'),(17,'Categorias','Registrar','Se registró una nueva categoría: Accesorios','2026-05-25 11:07:30'),(18,'Producto','Registro','Se registró un nuevo producto: Diadema','2026-05-25 11:08:11'),(19,'Salida Mercancia','Registro','Se registró una salida de mercancía','2026-05-25 11:15:14'),(20,'Proveedor','Registrar','Se registró un nuevo proveedor: Palmas Sneakers','2026-05-25 11:25:54'),(21,'Categorias','Editar','Se edito la nueva categoría: Ropa Bebé','2026-05-25 12:37:14'),(22,'Categorias','Registrar','Se registró una nueva categoría: Ropa Mujer','2026-05-25 12:37:38'),(23,'Categorias','Registrar','Se registró una nueva categoría: Ropa Hombre','2026-05-25 12:37:52'),(24,'Empleados','Editar','Se actualizó el empleado: Alexa Yunuen Vazquez Rendon','2026-05-30 16:33:21'),(25,'Empleados','Eliminar','Se eliminó el empleado: Oswaldo Alejandro Murillo Barrera','2026-05-30 16:33:44'),(26,'Productos','Eliminar','Se elimino el producto: Diadema','2026-05-30 16:33:59'),(27,'Producto','Editar','Se editó el producto: Pantalon','2026-05-30 16:36:52'),(28,'Categorias','Editar','Se edito la nueva categoría: Ropa Mujer','2026-05-30 16:38:04'),(29,'Producto','Registro','Se registrÃ³ un nuevo producto: Gucci','2026-05-31 22:03:20'),(30,'Categorias','Registrar','Se registró una nueva categoría: Pantalon deportivo','2026-05-31 23:46:44'),(31,'Producto','Registro','Se registró un nuevo producto: shjsfba','2026-06-01 10:17:44'),(32,'Producto','Registro','Se registró un nuevo producto: Blusa Roja','2026-06-01 11:13:00'),(33,'Producto','Editar','Se editó el producto: Blusa Roja','2026-06-01 11:13:13'),(34,'Producto','Editar','Se editó el producto: Blusa Roja','2026-06-01 11:15:39'),(35,'Proveedor','Registrar','Se registró un nuevo proveedor: Adidas','2026-06-02 10:27:40'),(36,'Categoria','Eliminar','Se eliminó la categoria: Calzado','2026-06-02 10:29:03'),(37,'Categoria','Eliminar','Se eliminó la categoria: Ropa Bebé','2026-06-02 10:29:29'),(38,'Producto','Registro','Se registró un nuevo producto: Blusa azul','2026-06-02 12:49:32'),(39,'Producto','Editar','Se editó el producto: Blusa azul','2026-06-02 12:50:21'),(40,'Productos','Eliminar','Se elimino el producto: Blusa azul','2026-06-02 12:50:29'),(41,'Pago a Proveedores','Registro','Se registró un pago de $ 500.0 al proveedor Adidas','2026-06-02 12:51:25'),(42,'Categorias','Registrar','Se registró una nueva categoría: Calzado de playa','2026-06-02 12:52:21'),(43,'Categorias','Editar','Se edito la nueva categoría: Ropa caballero','2026-06-02 12:53:00'),(44,'Categoria','Eliminar','Se eliminó la categoria: Pantalon deportivo','2026-06-02 12:53:37'),(45,'Pago a Proveedores','Actualización','Se actualizó el pago de $ 500.0 al proveedor Adidas','2026-06-03 15:21:55'),(46,'Pago a Proveedores','Actualización','Se actualizó el pago de $ 12000.0 al proveedor Palmas Sneakers','2026-06-03 15:32:32'),(47,'Pago a Proveedores','Registro','Se registró un pago de $ 5000.0 al proveedor Adidas','2026-06-03 15:33:08'),(48,'Productos','Eliminar','Se elimino el producto: shjsfba','2026-06-03 15:34:57'),(49,'Producto','Editar','Se editó el producto: Tenis','2026-06-03 15:53:09'),(50,'Producto','Editar','Se editó el producto: Gucci','2026-06-03 16:03:34'),(51,'Producto','Editar','Se editó el producto: Gucci','2026-06-03 16:16:03'),(52,'Producto','Editar','Se editó el producto: Pantalon','2026-06-03 16:47:51'),(53,'Producto','Registro','Se registró un nuevo producto: BLUSA','2026-06-04 10:19:38'),(54,'Producto','Editar','Se editó el producto: Gucci','2026-06-04 10:20:08'),(55,'Categorias','Registrar','Se registró una nueva categoría: CALZADO TEENS','2026-06-04 10:20:58'),(56,'Empleados','Registrar','Se registró un nuevo empleado: María de Los Angeles Laces Mancera','2026-06-04 10:22:46'),(57,'Pago a Proveedores','Registro','Se registró un pago de $ 300.0 al proveedor Levis','2026-10-05 09:25:00'),(58,'Pago a Proveedores','Actualización','Se actualizó el pago de $ 300.0 al proveedor Levís','2026-10-05 09:26:18'),(59,'Pago a Proveedores','Registro','Se registró un pago de $ 3584.0 al proveedor Adidas','2026-10-05 09:28:13'),(60,'Producto','Registro','Se registró un nuevo producto: Vestido','2026-10-05 09:29:40'),(61,'Producto','Registro','Se registró un nuevo producto: ARETES','2026-10-05 09:30:39'),(62,'Producto','Editar','Se editó el producto: ARETES','2026-10-05 09:30:46'),(63,'Producto','Editar','Se editó el producto: Tenis','2026-10-05 09:31:23'),(64,'Producto','Editar','Se editó el producto: Tenis','2026-10-05 09:31:30'),(65,'Producto','Editar','Se editó el producto: Vestido','2026-10-05 09:31:44'),(66,'Producto','Registro','Se registró un nuevo producto: OVEROL','2026-10-05 09:32:36'),(67,'Categorias','Registrar','Se registró una nueva categoría: Tenis','2026-10-05 09:33:25'),(68,'Producto','Editar','Se editó el producto: Vestido','2026-10-05 09:35:32'),(69,'Producto','Registro','Se registró un nuevo producto: DSCDSV','2026-10-05 09:35:53'),(70,'Productos','Eliminar','Se elimino el producto: DSCDSV','2026-10-05 09:36:05'),(71,'Proveedor','Registrar','Se registró un nuevo proveedor: Andrea','2026-10-05 09:37:01'),(72,'Proveedor','Registrar','Se registró un nuevo proveedor: Manier MX','2026-10-05 09:40:37'),(73,'Producto','Editar','Se editó el producto: Vestido','2026-10-05 10:06:07'),(74,'Producto','Editar','Se editó el producto: Blusa','2026-10-06 09:43:34'),(75,'Producto','Editar','Se editó el producto: Aretes','2026-10-06 09:44:03'),(76,'Producto','Editar','Se editó el producto: Overol','2026-10-06 09:44:21'),(77,'Empleados','Eliminar','Se eliminó el empleado: Emma Maria Franco Gonzalez','2026-10-06 10:00:31'),(78,'Empleados','Registrar','Se registró un nuevo empleado: Emma Maria Franco Gonzalez','2026-10-06 10:03:59'),(79,'Empleados','Registrar','Se registró un nuevo empleado: Alejandra Marin Gutierrez Lemus','2026-11-04 23:21:05'),(80,'Pago a Proveedores','Registro','Se registró un pago de $ 5000.0 al proveedor Levís','2026-11-05 15:09:08'),(81,'Empleados','Editar','Se actualizó el empleado: María de Los Angeles Laces Mancera','2026-11-05 22:30:32'),(82,'Empleados','Eliminar','Se eliminó el empleado: María de Los Angeles Laces Mancera','2026-11-05 22:30:54'),(83,'Empleados','Eliminar','Se eliminó el empleado: Emma Maria Franco Gonzalez','2026-11-05 22:31:00'),(84,'Productos','Eliminar','Se elimino el producto: Gucci','2026-11-09 09:36:14'),(85,'Proveedor','Eliminar','Se eliminó el proveedor: Adidas','2026-11-09 09:58:09'),(86,'Empleados','Editar','Se actualizó el empleado: Maria Belen Chacon Almaraz','2026-11-09 10:03:11'),(87,'Categorias','Registrar','Se registró una nueva categoría: Ropa deportiva DAMA','2026-11-11 21:22:27'),(88,'Categorias','Editar','Se edito la nueva categoría: Ropa deportiva dama','2026-11-11 21:22:36'),(89,'Categoria','Eliminar','Se eliminó la categoria: Calzado de playa','2026-11-11 21:22:45'),(90,'Producto','Registro','Se registró un nuevo producto: Levis prime','2026-11-11 21:23:54'),(91,'Productos','Eliminar','Se elimino el producto: Aretes','2026-11-11 21:24:04'),(92,'Proveedor','Registrar','Se registró un nuevo proveedor: Stunners','2026-11-11 21:25:22'),(93,'Proveedor','Eliminar','Se eliminó el proveedor: Stunners','2026-11-11 21:25:32'),(94,'Categorias','Registrar','Se registró una nueva categoría: Tenis deportivos','2026-11-11 23:28:18');
/*!40000 ALTER TABLE `actividad_reciente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `alertastock`
--

DROP TABLE IF EXISTS `alertastock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `alertastock` (
  `Id_AlertaS` int NOT NULL AUTO_INCREMENT,
  `Fecha_Alerta` datetime NOT NULL,
  `Existencias_AlMomento` int NOT NULL,
  `Id_Producto` int DEFAULT NULL,
  `Id_Usuario` int DEFAULT NULL,
  PRIMARY KEY (`Id_AlertaS`),
  KEY `FK_AlertaS_Producto` (`Id_Producto`),
  KEY `FK_AlertaS_Usuario` (`Id_Usuario`),
  CONSTRAINT `FK_AlertaS_Producto` FOREIGN KEY (`Id_Producto`) REFERENCES `producto` (`Id_Producto`) ON DELETE SET NULL,
  CONSTRAINT `FK_AlertaS_Usuario` FOREIGN KEY (`Id_Usuario`) REFERENCES `usuario` (`Id_Usuario`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alertastock`
--

LOCK TABLES `alertastock` WRITE;
/*!40000 ALTER TABLE `alertastock` DISABLE KEYS */;
/*!40000 ALTER TABLE `alertastock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria`
--

DROP TABLE IF EXISTS `categoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria` (
  `Id_Categoria` int NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(100) NOT NULL,
  `Descripcion` varchar(250) DEFAULT NULL,
  `activo` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`Id_Categoria`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria`
--

LOCK TABLES `categoria` WRITE;
/*!40000 ALTER TABLE `categoria` DISABLE KEYS */;
INSERT INTO `categoria` VALUES (1,'Calzado','Todo el calzado que te puedas imaginar, al mejor precio y la mejor calidad.',0),(2,'Ropa Bebé','Variedad de ropa.',0),(3,'Calzado','Zapatos.',0),(4,'Accesorios','Variedad de accesorios',1),(5,'Ropa Mujer','Todo para la comodidad que buscas',1),(6,'Ropa caballero','Comoda',1),(7,'Pantalon deportivo','Pantalon para deportes',0),(8,'Calzado de playa','Calzado veraniego para la temporada',0),(9,'CALZADO TEENS','PRODUCTO PARA ADOLESCENTES',1),(10,'Tenis','Comodo',1),(11,'Ropa deportiva dama','Ropa deportiva para dama',1),(12,'Tenis deportivos','Tenis deportivos para futbol',1);
/*!40000 ALTER TABLE `categoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `consolidado`
--

DROP TABLE IF EXISTS `consolidado`;
/*!50001 DROP VIEW IF EXISTS `consolidado`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `consolidado` AS SELECT 
 1 AS `Id_Producto`,
 1 AS `SKU`,
 1 AS `Modelo`,
 1 AS `Talla`,
 1 AS `Total_Entradas`,
 1 AS `Total_Salidas`,
 1 AS `Stock_Actual`,
 1 AS `Precio_Referencia`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `entradamercancia`
--

DROP TABLE IF EXISTS `entradamercancia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entradamercancia` (
  `Id_EntradaMer` int NOT NULL AUTO_INCREMENT,
  `Fecha_Ingreso` datetime NOT NULL,
  `Notas` varchar(500) DEFAULT NULL,
  `Id_Usuario` int DEFAULT NULL,
  PRIMARY KEY (`Id_EntradaMer`),
  KEY `FK_EntradaMer_Usuario` (`Id_Usuario`),
  CONSTRAINT `FK_EntradaMer_Usuario` FOREIGN KEY (`Id_Usuario`) REFERENCES `usuario` (`Id_Usuario`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entradamercancia`
--

LOCK TABLES `entradamercancia` WRITE;
/*!40000 ALTER TABLE `entradamercancia` DISABLE KEYS */;
INSERT INTO `entradamercancia` VALUES (1,'2026-05-25 11:11:51','',1),(2,'2026-05-25 11:21:32','Llegada de diadema',1),(3,'2026-05-30 16:36:04','Entrada de mercancia ',1),(4,'2026-06-04 10:23:26','color roja',1),(5,'2026-10-05 10:06:44','Mercancia',4),(6,'2026-10-12 23:06:40','Mercancia adquirida',4),(7,'2026-11-03 09:50:37','Entro el Producto',4),(8,'2026-11-03 09:51:02','Entrada',4),(9,'2026-11-03 09:52:01','Entrada',4),(10,'2026-11-05 15:06:44','Mercancia',4),(11,'2026-11-09 10:05:04','Llegada de blusas',4);
/*!40000 ALTER TABLE `entradamercancia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `entradamercancia_producto`
--

DROP TABLE IF EXISTS `entradamercancia_producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `entradamercancia_producto` (
  `Id_EntradaMer` int NOT NULL,
  `Id_Producto` int NOT NULL,
  `Cantidad` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`Id_EntradaMer`,`Id_Producto`),
  KEY `FK_EMP_Producto` (`Id_Producto`),
  CONSTRAINT `FK_EMP_EntradaMer` FOREIGN KEY (`Id_EntradaMer`) REFERENCES `entradamercancia` (`Id_EntradaMer`) ON DELETE CASCADE,
  CONSTRAINT `FK_EMP_Producto` FOREIGN KEY (`Id_Producto`) REFERENCES `producto` (`Id_Producto`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entradamercancia_producto`
--

LOCK TABLES `entradamercancia_producto` WRITE;
/*!40000 ALTER TABLE `entradamercancia_producto` DISABLE KEYS */;
INSERT INTO `entradamercancia_producto` VALUES (2,1,0),(2,5,0),(2,6,0),(3,5,5),(4,12,4),(5,13,4),(6,7,4),(7,7,20),(8,12,16),(9,9,17),(10,13,50),(11,12,5);
/*!40000 ALTER TABLE `entradamercancia_producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `historialmovimientos`
--

DROP TABLE IF EXISTS `historialmovimientos`;
/*!50001 DROP VIEW IF EXISTS `historialmovimientos`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `historialmovimientos` AS SELECT 
 1 AS `Tipo_Movimiento`,
 1 AS `Id_Movimiento`,
 1 AS `Fecha`,
 1 AS `Id_Producto`,
 1 AS `SKU`,
 1 AS `Modelo`,
 1 AS `Cantidad`,
 1 AS `Usuario`,
 1 AS `Motivo`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `historialventasdetalle`
--

DROP TABLE IF EXISTS `historialventasdetalle`;
/*!50001 DROP VIEW IF EXISTS `historialventasdetalle`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `historialventasdetalle` AS SELECT 
 1 AS `Id_DetalleVenta`,
 1 AS `Id_Venta`,
 1 AS `Fecha_Hora`,
 1 AS `SKU`,
 1 AS `Modelo`,
 1 AS `Talla`,
 1 AS `Cantidad`,
 1 AS `Precio_Unitario`,
 1 AS `Subtotal`,
 1 AS `Metodo_Pago`,
 1 AS `Estado`,
 1 AS `Vendedor`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `historialventasreportes`
--

DROP TABLE IF EXISTS `historialventasreportes`;
/*!50001 DROP VIEW IF EXISTS `historialventasreportes`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `historialventasreportes` AS SELECT 
 1 AS `Id_Venta`,
 1 AS `Fecha_Hora`,
 1 AS `Productos_Vendidos`,
 1 AS `Unidades_Vendidas`,
 1 AS `Vendedor`,
 1 AS `Total`,
 1 AS `Metodo_Pago`,
 1 AS `Dinero_Recibido`,
 1 AS `Cambio`,
 1 AS `Estado`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `inventarioactual`
--

DROP TABLE IF EXISTS `inventarioactual`;
/*!50001 DROP VIEW IF EXISTS `inventarioactual`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `inventarioactual` AS SELECT 
 1 AS `Id_Producto`,
 1 AS `SKU`,
 1 AS `Modelo`,
 1 AS `Talla`,
 1 AS `Existencias`,
 1 AS `Stock_Minimo`,
 1 AS `Precio_Referencia`,
 1 AS `Categoria`,
 1 AS `Proveedor`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `nivelesstock`
--

DROP TABLE IF EXISTS `nivelesstock`;
/*!50001 DROP VIEW IF EXISTS `nivelesstock`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `nivelesstock` AS SELECT 
 1 AS `Id_Producto`,
 1 AS `SKU`,
 1 AS `Modelo`,
 1 AS `Talla`,
 1 AS `Existencias`,
 1 AS `Stock_Minimo`,
 1 AS `Categoria`,
 1 AS `Proveedor`,
 1 AS `Nivel_Stock`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `pagoproveedor`
--

DROP TABLE IF EXISTS `pagoproveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pagoproveedor` (
  `Id_PagoProveedor` int NOT NULL AUTO_INCREMENT,
  `Monto` decimal(10,2) NOT NULL,
  `Fecha_Pago` datetime NOT NULL,
  `Concepto` varchar(255) NOT NULL,
  `Id_Proveedor` int DEFAULT NULL,
  `Id_Usuario` int DEFAULT NULL,
  `Activo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`Id_PagoProveedor`),
  KEY `FK_PagoProveedor_Proveedor` (`Id_Proveedor`),
  KEY `FK_PagoProveedor_Usuario` (`Id_Usuario`),
  CONSTRAINT `FK_PagoProveedor_Proveedor` FOREIGN KEY (`Id_Proveedor`) REFERENCES `proveedor` (`Id_Proveedor`) ON DELETE SET NULL,
  CONSTRAINT `FK_PagoProveedor_Usuario` FOREIGN KEY (`Id_Usuario`) REFERENCES `usuario` (`Id_Usuario`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pagoproveedor`
--

LOCK TABLES `pagoproveedor` WRITE;
/*!40000 ALTER TABLE `pagoproveedor` DISABLE KEYS */;
INSERT INTO `pagoproveedor` VALUES (1,1500.00,'2024-05-26 00:00:00','Pago de mercancia',1,1,1),(2,12000.00,'2026-06-03 21:32:30','Pago de calzado',4,1,1),(3,5000.00,'2015-05-26 00:00:00','Pago de calzado ',4,1,1),(4,500.00,'2026-06-03 21:21:52','Pago de calzado ',5,1,1),(6,5000.00,'2026-06-03 21:32:47','',5,1,0),(7,300.00,'2025-10-17 06:00:00','4826059',1,1,0),(8,3584.00,'2024-07-18 15:27:55','5612849',5,4,1),(9,5000.00,'2026-11-02 21:08:52','Pago',1,4,1);
/*!40000 ALTER TABLE `pagoproveedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `porproveedor`
--

DROP TABLE IF EXISTS `porproveedor`;
/*!50001 DROP VIEW IF EXISTS `porproveedor`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `porproveedor` AS SELECT 
 1 AS `Id_Proveedor`,
 1 AS `Proveedor`,
 1 AS `Contacto`,
 1 AS `Telefono`,
 1 AS `Correo`,
 1 AS `Total_Productos`,
 1 AS `Existencias_Totales`,
 1 AS `Valor_Inventario`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `producto`
--

DROP TABLE IF EXISTS `producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `producto` (
  `Id_Producto` int NOT NULL AUTO_INCREMENT,
  `SKU` varchar(100) NOT NULL,
  `Modelo` varchar(150) NOT NULL,
  `Talla` varchar(20) DEFAULT NULL,
  `Precio_Referencia` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Existencias` int NOT NULL DEFAULT '0',
  `Stock_Minimo` int NOT NULL DEFAULT '0',
  `Activo` tinyint(1) NOT NULL DEFAULT '1',
  `Id_Categoria` int DEFAULT NULL,
  `Id_Proveedor` int DEFAULT NULL,
  PRIMARY KEY (`Id_Producto`),
  UNIQUE KEY `SKU` (`SKU`),
  KEY `FK_Producto_Categoria` (`Id_Categoria`),
  KEY `FK_Producto_Proveedor` (`Id_Proveedor`),
  CONSTRAINT `FK_Producto_Categoria` FOREIGN KEY (`Id_Categoria`) REFERENCES `categoria` (`Id_Categoria`) ON DELETE SET NULL,
  CONSTRAINT `FK_Producto_Proveedor` FOREIGN KEY (`Id_Proveedor`) REFERENCES `proveedor` (`Id_Proveedor`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `producto`
--

LOCK TABLES `producto` WRITE;
/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` VALUES (1,'TN-002','Tenis','26',255.00,3,3,1,9,5),(3,'TN-003','Playera','L',200.00,19,7,0,2,1),(5,'TN-004','Pantalon','XXL',555.00,8,5,1,6,1),(6,'ZKU-215','Diadema','Seleccionar talla',1000.00,30,5,0,4,1),(7,'TN-005','Gucci','M',4300.00,16,2,0,5,1),(8,'125876','shjsfba','S',5555.00,12,-4,0,5,1),(9,'122255','Blusa Roja','M',120.00,13,2,1,2,1),(10,'TN-006','Blusa azul','',250.00,7,4,0,5,1),(12,'TN-007','Blusa','M',30.00,3,3,1,5,1),(13,'TN-1202','Vestido','M',350.00,48,5,1,5,1),(14,'123356','Aretes','',100.00,6,3,0,4,4),(15,'172022','Overol','XL',599.00,15,11,1,5,1),(16,'CDDD','DSCDSV','',1234.00,18,9,0,4,1),(17,'TN-0001','Levis prime','S',250.00,10,5,1,11,1);
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedor`
--

DROP TABLE IF EXISTS `proveedor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proveedor` (
  `Id_Proveedor` int NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(150) NOT NULL,
  `Contacto` varchar(150) DEFAULT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `Correo` varchar(150) DEFAULT NULL,
  `activo` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`Id_Proveedor`),
  UNIQUE KEY `Correo` (`Correo`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedor`
--

LOCK TABLES `proveedor` WRITE;
/*!40000 ALTER TABLE `proveedor` DISABLE KEYS */;
INSERT INTO `proveedor` VALUES (1,'Levís','Juan Perez','3221456987','juanperez@gmail.com',1),(3,'Levis','Juan Perez','3221456987','juanperezs@gmail.com',0),(4,'Palmas Sneakers','Enrique Cabrera','3222556978','enricabre14@gmail.com',1),(5,'Adidas','Juanes Estrada','3221234567','juanes@adidas.mx',0),(6,'Andrea','Alicia Irene Conriquez Aguirre','3221236787','aica12@gmail.com',1),(7,'Manier MX','Alejandra Maciel Rodriguez Santana','3221456756','alemacrs@manier.com',1),(8,'Stunners','Juan Manuel Cisneros Ocampo','3221786759','juanmcidd@adidas.gdl',0);
/*!40000 ALTER TABLE `proveedor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salidamercancia`
--

DROP TABLE IF EXISTS `salidamercancia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salidamercancia` (
  `Id_SalidaMer` int NOT NULL AUTO_INCREMENT,
  `Fecha` datetime NOT NULL,
  `Motivo` enum('AJUSTE','DANO','PERDIDA','BAJA') NOT NULL,
  `Notas` varchar(500) DEFAULT NULL,
  `Id_Usuario` int DEFAULT NULL,
  PRIMARY KEY (`Id_SalidaMer`),
  KEY `FK_SalidaMer_Usuario` (`Id_Usuario`),
  CONSTRAINT `FK_SalidaMer_Usuario` FOREIGN KEY (`Id_Usuario`) REFERENCES `usuario` (`Id_Usuario`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salidamercancia`
--

LOCK TABLES `salidamercancia` WRITE;
/*!40000 ALTER TABLE `salidamercancia` DISABLE KEYS */;
INSERT INTO `salidamercancia` VALUES (1,'2026-05-25 11:15:12','AJUSTE','Mercancia en venta',1),(2,'2026-06-01 11:17:13','AJUSTE','Se vendieron 7 panalones',1),(3,'2026-06-04 10:25:11','DANO','',1),(4,'2026-06-04 10:31:55','DANO','fgdf',1),(5,'2026-10-05 10:05:12','AJUSTE','Venta ',4),(6,'2026-10-12 23:08:14','AJUSTE','',4),(7,'2026-11-05 15:08:26','DANO','Mercancia dañada ',4),(8,'2026-11-09 10:05:45','DANO','',4);
/*!40000 ALTER TABLE `salidamercancia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salidamercancia_producto`
--

DROP TABLE IF EXISTS `salidamercancia_producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salidamercancia_producto` (
  `Id_SalidaMer` int NOT NULL,
  `Id_Producto` int NOT NULL,
  `Cantidad` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`Id_SalidaMer`,`Id_Producto`),
  KEY `FK_SMP_Producto` (`Id_Producto`),
  CONSTRAINT `FK_SMP_Producto` FOREIGN KEY (`Id_Producto`) REFERENCES `producto` (`Id_Producto`) ON DELETE CASCADE,
  CONSTRAINT `FK_SMP_SalidaMer` FOREIGN KEY (`Id_SalidaMer`) REFERENCES `salidamercancia` (`Id_SalidaMer`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salidamercancia_producto`
--

LOCK TABLES `salidamercancia_producto` WRITE;
/*!40000 ALTER TABLE `salidamercancia_producto` DISABLE KEYS */;
INSERT INTO `salidamercancia_producto` VALUES (1,1,0),(1,5,0),(1,6,0),(2,5,7),(3,12,2),(4,12,3),(5,13,20),(6,7,5),(7,13,18),(8,12,2);
/*!40000 ALTER TABLE `salidamercancia_producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `stockbajo`
--

DROP TABLE IF EXISTS `stockbajo`;
/*!50001 DROP VIEW IF EXISTS `stockbajo`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `stockbajo` AS SELECT 
 1 AS `Id_Producto`,
 1 AS `SKU`,
 1 AS `Modelo`,
 1 AS `Talla`,
 1 AS `Existencias`,
 1 AS `Stock_Minimo`,
 1 AS `Precio_Referencia`,
 1 AS `Categoria`,
 1 AS `Proveedor`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `Id_Usuario` int NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(150) NOT NULL,
  `Correo` varchar(150) NOT NULL,
  `Login` varchar(100) NOT NULL,
  `PasswordHash` varchar(255) NOT NULL,
  `Rol` enum('ADMINISTRADOR','ENCARGADO_INVENTARIO','CAJERO') NOT NULL,
  `Activo` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`Id_Usuario`),
  UNIQUE KEY `Correo` (`Correo`),
  UNIQUE KEY `Login` (`Login`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Emma Maria Franco Gonzalez','emmaaddm@gmail.com','e.franco','Darkmoon','ADMINISTRADOR',0),(2,'Maria Belen Chacon Almaraz','beleneninv@gmail.com','m.chacon','blowsky','CAJERO',1),(4,'Alexa Yunuen Vazquez Rendon','alexaeninv@gmail.com','a.vazquez','poweroff','ADMINISTRADOR',1),(5,'Oswaldo Alejandro Murillo Barrera','oswaeninv@gmail.com','o.murillo','therian','ENCARGADO_INVENTARIO',0),(9,'Maria Belen Chacon Almaraz','beleneinv@gmail.com','m.almaraz','Belen123','ADMINISTRADOR',0),(10,'María de Los Angeles Laces Mancera','anainv@gmail.com','m.laces','1234567','ENCARGADO_INVENTARIO',0),(11,'Emma Maria Franco Gonzalez','emmafranco1@gmail.com','e.francog','Darkmoon','ADMINISTRADOR',0),(12,'Alejandra Marin Gutierrez Lemus','alecaj@gmail.com','a.lemus','12702514','CAJERO',1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `venta`
--

DROP TABLE IF EXISTS `venta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `venta` (
  `Id_Venta` int NOT NULL AUTO_INCREMENT,
  `Fecha_Hora` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Total` decimal(10,2) NOT NULL DEFAULT '0.00',
  `Metodo_Pago` varchar(30) NOT NULL,
  `Estado` varchar(20) NOT NULL DEFAULT 'Confirmada',
  `Id_Usuario` int NOT NULL,
  `Dinero_Recibido` decimal(12,2) DEFAULT NULL,
  `Cambio` decimal(12,2) DEFAULT NULL,
  PRIMARY KEY (`Id_Venta`),
  KEY `Id_Usuario` (`Id_Usuario`),
  CONSTRAINT `venta_ibfk_1` FOREIGN KEY (`Id_Usuario`) REFERENCES `usuario` (`Id_Usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venta`
--

LOCK TABLES `venta` WRITE;
/*!40000 ALTER TABLE `venta` DISABLE KEYS */;
INSERT INTO `venta` VALUES (1,'2026-11-02 00:43:57',240.00,'Efectivo','Completada',4,NULL,NULL),(2,'2026-11-03 10:14:51',700.00,'Efectivo','Completada',4,NULL,NULL),(3,'2026-11-03 10:16:00',2746.00,'Efectivo','Completada',4,NULL,NULL),(4,'2026-11-03 22:15:39',25800.00,'Efectivo','Completada',4,NULL,NULL),(5,'2026-11-04 23:27:00',600.00,'Efectivo','Completada',4,1000.00,400.00),(6,'2026-11-05 14:28:41',100.00,'Efectivo','Completada',4,150.00,50.00),(7,'2026-11-05 14:29:18',1797.00,'Efectivo','Completada',4,2000.00,203.00),(8,'2026-11-05 15:10:26',599.00,'Efectivo','Completada',4,1000.00,401.00),(9,'2026-11-09 10:00:05',2535.00,'Efectivo','Completada',4,3000.00,465.00);
/*!40000 ALTER TABLE `venta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `venta_producto`
--

DROP TABLE IF EXISTS `venta_producto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `venta_producto` (
  `Id_DetalleVenta` int NOT NULL AUTO_INCREMENT,
  `Id_Venta` int NOT NULL,
  `Id_Producto` int NOT NULL,
  `Cantidad` int NOT NULL,
  `Precio_Unitario` decimal(10,2) NOT NULL,
  `Subtotal` decimal(10,2) NOT NULL,
  PRIMARY KEY (`Id_DetalleVenta`),
  KEY `Id_Venta` (`Id_Venta`),
  KEY `Id_Producto` (`Id_Producto`),
  CONSTRAINT `venta_producto_ibfk_1` FOREIGN KEY (`Id_Venta`) REFERENCES `venta` (`Id_Venta`),
  CONSTRAINT `venta_producto_ibfk_2` FOREIGN KEY (`Id_Producto`) REFERENCES `producto` (`Id_Producto`),
  CONSTRAINT `venta_producto_chk_1` CHECK ((`Cantidad` > 0)),
  CONSTRAINT `venta_producto_chk_2` CHECK ((`Precio_Unitario` >= 0)),
  CONSTRAINT `venta_producto_chk_3` CHECK ((`Subtotal` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `venta_producto`
--

LOCK TABLES `venta_producto` WRITE;
/*!40000 ALTER TABLE `venta_producto` DISABLE KEYS */;
INSERT INTO `venta_producto` VALUES (1,1,9,2,120.00,240.00),(2,2,13,2,350.00,700.00),(3,3,13,1,350.00,350.00),(4,3,15,4,599.00,2396.00),(5,4,7,6,4300.00,25800.00),(6,5,9,5,120.00,600.00),(7,6,14,1,100.00,100.00),(8,7,15,3,599.00,1797.00),(9,8,15,1,599.00,599.00),(10,9,1,7,255.00,1785.00),(11,9,12,25,30.00,750.00);
/*!40000 ALTER TABLE `venta_producto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `consolidado`
--

/*!50001 DROP VIEW IF EXISTS `consolidado`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `consolidado` AS select `p`.`Id_Producto` AS `Id_Producto`,`p`.`SKU` AS `SKU`,`p`.`Modelo` AS `Modelo`,`p`.`Talla` AS `Talla`,(select coalesce(sum(`entradamercancia_producto`.`Cantidad`),0) from `entradamercancia_producto` where (`entradamercancia_producto`.`Id_Producto` = `p`.`Id_Producto`)) AS `Total_Entradas`,(select coalesce(sum(`salidamercancia_producto`.`Cantidad`),0) from `salidamercancia_producto` where (`salidamercancia_producto`.`Id_Producto` = `p`.`Id_Producto`)) AS `Total_Salidas`,`p`.`Existencias` AS `Stock_Actual`,`p`.`Precio_Referencia` AS `Precio_Referencia` from `producto` `p` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `historialmovimientos`
--

/*!50001 DROP VIEW IF EXISTS `historialmovimientos`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `historialmovimientos` AS select 'ENTRADA' AS `Tipo_Movimiento`,`em`.`Id_EntradaMer` AS `Id_Movimiento`,`em`.`Fecha_Ingreso` AS `Fecha`,`p`.`Id_Producto` AS `Id_Producto`,`p`.`SKU` AS `SKU`,`p`.`Modelo` AS `Modelo`,`emp`.`Cantidad` AS `Cantidad`,`u`.`Nombre` AS `Usuario`,cast(NULL as char(50) charset utf8mb4) AS `Motivo` from (((`entradamercancia` `em` join `entradamercancia_producto` `emp` on((`emp`.`Id_EntradaMer` = `em`.`Id_EntradaMer`))) join `producto` `p` on((`p`.`Id_Producto` = `emp`.`Id_Producto`))) join `usuario` `u` on((`u`.`Id_Usuario` = `em`.`Id_Usuario`))) union all select 'SALIDA' AS `SALIDA`,`sm`.`Id_SalidaMer` AS `Id_SalidaMer`,`sm`.`Fecha` AS `Fecha`,`p`.`Id_Producto` AS `Id_Producto`,`p`.`SKU` AS `SKU`,`p`.`Modelo` AS `Modelo`,`smp`.`Cantidad` AS `Cantidad`,`u`.`Nombre` AS `Nombre`,cast(`sm`.`Motivo` as char(50) charset utf8mb4) AS `CAST(sm.Motivo AS CHAR(50))` from (((`salidamercancia` `sm` join `salidamercancia_producto` `smp` on((`smp`.`Id_SalidaMer` = `sm`.`Id_SalidaMer`))) join `producto` `p` on((`p`.`Id_Producto` = `smp`.`Id_Producto`))) join `usuario` `u` on((`u`.`Id_Usuario` = `sm`.`Id_Usuario`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `historialventasdetalle`
--

/*!50001 DROP VIEW IF EXISTS `historialventasdetalle`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `historialventasdetalle` AS select `vp`.`Id_DetalleVenta` AS `Id_DetalleVenta`,`v`.`Id_Venta` AS `Id_Venta`,`v`.`Fecha_Hora` AS `Fecha_Hora`,`p`.`SKU` AS `SKU`,`p`.`Modelo` AS `Modelo`,`p`.`Talla` AS `Talla`,`vp`.`Cantidad` AS `Cantidad`,`vp`.`Precio_Unitario` AS `Precio_Unitario`,`vp`.`Subtotal` AS `Subtotal`,`v`.`Metodo_Pago` AS `Metodo_Pago`,`v`.`Estado` AS `Estado`,`u`.`Nombre` AS `Vendedor` from (((`venta` `v` join `venta_producto` `vp` on((`vp`.`Id_Venta` = `v`.`Id_Venta`))) left join `producto` `p` on((`p`.`Id_Producto` = `vp`.`Id_Producto`))) left join `usuario` `u` on((`u`.`Id_Usuario` = `v`.`Id_Usuario`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `historialventasreportes`
--

/*!50001 DROP VIEW IF EXISTS `historialventasreportes`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `historialventasreportes` AS select `v`.`Id_Venta` AS `Id_Venta`,`v`.`Fecha_Hora` AS `Fecha_Hora`,`d`.`Productos_Vendidos` AS `Productos_Vendidos`,`d`.`Unidades_Vendidas` AS `Unidades_Vendidas`,`u`.`Nombre` AS `Vendedor`,`v`.`Total` AS `Total`,`v`.`Metodo_Pago` AS `Metodo_Pago`,`v`.`Dinero_Recibido` AS `Dinero_Recibido`,`v`.`Cambio` AS `Cambio`,`v`.`Estado` AS `Estado` from ((`venta` `v` left join (select `vp`.`Id_Venta` AS `Id_Venta`,sum(`vp`.`Cantidad`) AS `Unidades_Vendidas`,group_concat(concat(`vp`.`Cantidad`,' x ',coalesce(`p`.`Modelo`,'Producto no disponible'),' | SKU: ',coalesce(`p`.`SKU`,'-'),' | Talla: ',coalesce(`p`.`Talla`,'-')) order by `vp`.`Id_DetalleVenta` ASC separator '; ') AS `Productos_Vendidos` from (`venta_producto` `vp` left join `producto` `p` on((`p`.`Id_Producto` = `vp`.`Id_Producto`))) group by `vp`.`Id_Venta`) `d` on((`d`.`Id_Venta` = `v`.`Id_Venta`))) left join `usuario` `u` on((`u`.`Id_Usuario` = `v`.`Id_Usuario`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `inventarioactual`
--

/*!50001 DROP VIEW IF EXISTS `inventarioactual`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `inventarioactual` AS select `p`.`Id_Producto` AS `Id_Producto`,`p`.`SKU` AS `SKU`,`p`.`Modelo` AS `Modelo`,`p`.`Talla` AS `Talla`,`p`.`Existencias` AS `Existencias`,`p`.`Stock_Minimo` AS `Stock_Minimo`,`p`.`Precio_Referencia` AS `Precio_Referencia`,`c`.`Nombre` AS `Categoria`,`pr`.`Nombre` AS `Proveedor` from ((`producto` `p` left join `categoria` `c` on((`p`.`Id_Categoria` = `c`.`Id_Categoria`))) left join `proveedor` `pr` on((`p`.`Id_Proveedor` = `pr`.`Id_Proveedor`))) where (`p`.`Activo` = true) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `nivelesstock`
--

/*!50001 DROP VIEW IF EXISTS `nivelesstock`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `nivelesstock` AS select `p`.`Id_Producto` AS `Id_Producto`,`p`.`SKU` AS `SKU`,`p`.`Modelo` AS `Modelo`,`p`.`Talla` AS `Talla`,`p`.`Existencias` AS `Existencias`,`p`.`Stock_Minimo` AS `Stock_Minimo`,`c`.`Nombre` AS `Categoria`,`pr`.`Nombre` AS `Proveedor`,(case when (`p`.`Existencias` = 0) then 'SIN_STOCK' when (`p`.`Existencias` <= `p`.`Stock_Minimo`) then 'CRITICO' when (`p`.`Existencias` <= (`p`.`Stock_Minimo` * 2)) then 'BAJO' else 'NORMAL' end) AS `Nivel_Stock` from ((`producto` `p` left join `categoria` `c` on((`p`.`Id_Categoria` = `c`.`Id_Categoria`))) left join `proveedor` `pr` on((`p`.`Id_Proveedor` = `pr`.`Id_Proveedor`))) where (`p`.`Activo` = true) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `porproveedor`
--

/*!50001 DROP VIEW IF EXISTS `porproveedor`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `porproveedor` AS select `pr`.`Id_Proveedor` AS `Id_Proveedor`,`pr`.`Nombre` AS `Proveedor`,`pr`.`Contacto` AS `Contacto`,`pr`.`Telefono` AS `Telefono`,`pr`.`Correo` AS `Correo`,count(`p`.`Id_Producto`) AS `Total_Productos`,coalesce(sum(`p`.`Existencias`),0) AS `Existencias_Totales`,coalesce(sum((`p`.`Existencias` * `p`.`Precio_Referencia`)),0) AS `Valor_Inventario` from (`proveedor` `pr` left join `producto` `p` on(((`p`.`Id_Proveedor` = `pr`.`Id_Proveedor`) and (`p`.`Activo` = true)))) group by `pr`.`Id_Proveedor`,`pr`.`Nombre`,`pr`.`Contacto`,`pr`.`Telefono`,`pr`.`Correo` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `stockbajo`
--

/*!50001 DROP VIEW IF EXISTS `stockbajo`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `stockbajo` AS select `p`.`Id_Producto` AS `Id_Producto`,`p`.`SKU` AS `SKU`,`p`.`Modelo` AS `Modelo`,`p`.`Talla` AS `Talla`,`p`.`Existencias` AS `Existencias`,`p`.`Stock_Minimo` AS `Stock_Minimo`,`p`.`Precio_Referencia` AS `Precio_Referencia`,`p`.`Id_Categoria` AS `Categoria`,`p`.`Id_Proveedor` AS `Proveedor` from `producto` `p` where (`p`.`Existencias` <= `p`.`Stock_Minimo`) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-12 15:52:42
