-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 13-06-2024 a las 21:54:55
-- Versión del servidor: 8.0.33
-- Versión de PHP: 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyectofinal`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contacto`
--

DROP TABLE IF EXISTS `contacto`;
CREATE TABLE IF NOT EXISTS `contacto` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) COLLATE utf16_unicode_ci DEFAULT NULL,
  `fecha_creada` datetime(6) DEFAULT NULL,
  `mensaje` varchar(255) COLLATE utf16_unicode_ci DEFAULT NULL,
  `nombre` varchar(255) COLLATE utf16_unicode_ci DEFAULT NULL,
  `numero` varchar(255) COLLATE utf16_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalles`
--

DROP TABLE IF EXISTS `detalles`;
CREATE TABLE IF NOT EXISTS `detalles` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad` double NOT NULL,
  `nombre` varchar(255) COLLATE utf16_unicode_ci DEFAULT NULL,
  `precio` double NOT NULL,
  `total` double NOT NULL,
  `orden_id` bigint DEFAULT NULL,
  `producto_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdurdo71oa161lmmal7oeaor74` (`orden_id`),
  KEY `FKio4oyl8qt5jclekxp7bwws2iy` (`producto_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Volcado de datos para la tabla `detalles`
--

INSERT INTO `detalles` (`id`, `cantidad`, `nombre`, `precio`, `total`, `orden_id`, `producto_id`) VALUES
(4, 1, 'Marvel Spider Man 2', 64.84, 64.84, 5, 20),
(5, 1, 'Red Dead Redemption 2', 18.15, 18.15, 5, 23),
(6, 1, 'Hellblade II', 36.99, 36.99, 5, 18);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ordenes`
--

DROP TABLE IF EXISTS `ordenes`;
CREATE TABLE IF NOT EXISTS `ordenes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `fecha_creada` datetime(6) DEFAULT NULL,
  `fecha_recibida` datetime(6) DEFAULT NULL,
  `numero` varchar(255) COLLATE utf16_unicode_ci DEFAULT NULL,
  `total` double NOT NULL,
  `usuario_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsqu43gsd6mtx7b1siww96324` (`usuario_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Volcado de datos para la tabla `ordenes`
--

INSERT INTO `ordenes` (`id`, `fecha_creada`, `fecha_recibida`, `numero`, `total`, `usuario_id`) VALUES
(1, '2024-06-10 11:07:46.000000', NULL, '0000000001', 5, 1),
(2, '2024-06-10 11:13:17.000000', NULL, '0000000002', 15, 2),
(3, '2024-06-10 11:16:54.000000', NULL, '0000000003', 5, 2),
(4, '2024-06-10 11:16:55.000000', NULL, '0000000004', 0, 2),
(5, '2024-06-13 17:24:11.726000', NULL, '0000000005', 119.98, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

DROP TABLE IF EXISTS `productos`;
CREATE TABLE IF NOT EXISTS `productos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cantidad` int NOT NULL,
  `categoria` varchar(255) COLLATE utf16_unicode_ci DEFAULT NULL,
  `descripcion` varchar(700) CHARACTER SET utf16 COLLATE utf16_unicode_ci DEFAULT NULL,
  `imagen` varchar(255) COLLATE utf16_unicode_ci DEFAULT NULL,
  `nombre` varchar(255) COLLATE utf16_unicode_ci DEFAULT NULL,
  `precio` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id`, `cantidad`, `categoria`, `descripcion`, `imagen`, `nombre`, `precio`) VALUES
(15, 10, 'xbox', ' Adéntrate en el peligroso mundo abierto de Skull and Bones, una experiencia cooperativa de rol y acción, para convertirte en un temible cabecilla pirata. Participa en emocionantes combates navales, fabrica barcos únicos y forja alianzas inverosímiles mientras superas retos y desatas el caos en los mares.', 'xbox-skull-and-bones.jpg', 'Skull and Bones', 39.99),
(16, 10, 'xbox', 'Forza Motorsport para PC es el equivalente a Forza Motorsport 8, pero es más un reinicio del juego original que una continuación: de ahí que el nombre no lleve número. Es un videojuego de simulación de carreras de coches.', 'xbox-forza-motorsport.jpg', 'Forza Motor', 34.29),
(17, 10, 'xbox', 'Starfield es el primer universo nuevo en más de 25 años de Bethesda Game Studios, los galardonados creadores de The Elder Scrolls V: Skyrim y Fallout 4.', 'xbox-starfield.jpg', 'Starfield', 58.99),
(18, 9, 'xbox', 'En esta secuela del galardonado Hellblade: Senua\'s Sacrifice, Senua regresa en un brutal viaje de supervivencia a través de los mitos y tormentos de la Islandia vikinga.', 'xbox-hellblade-II.jpg', 'Hellblade II', 36.99),
(19, 10, 'playstation', 'ADAPTARSE O MORIR EN LA LUCHA CONTRA LA AMENAZA DEFINITIVA En la secuela directa del exitoso juego Call of Duty®: Modern Warfare® II, el capitán Price y la Fuerza operativa 141 se enfrentan a la amenaza definitiva.', 'playstation-modern-warfare-iii.jpg', 'Modern Warfare III', 69.09),
(20, 9, 'playstation', 'Marvel Spider-Man 2 para PlayStation es un juego de acción y aventuras para un jugador que presenta a Miles Morales y Peter Parker como el superhéroe del mismo nombre. ', 'playstation-spider-man-2.jpg', 'Marvel Spider Man 2', 64.84),
(21, 10, 'playstation', 'THE NORSE SAGA CONTINUES From Santa Monica Studio and brought to PC in partnership with Jetpack Interactive comes God of War Ragnarök, an epic and heartfelt journey that follows Kratos and Atreus as they struggle with holding on and letting go.', 'playstation-god-of-war-ragnarok.jpg', 'God Of War Ragnarok', 48.41),
(22, 10, 'playstation', 'Llega a nuevas cotas Disfruta del majestuoso mundo de Horizon como nunca antes en esta nueva aventura para PlayStation®VR2.', 'playstation-horizon-call-of-the-mountain.jpg', 'Horizon Call Of The Mountain', 43.99),
(23, 29, 'inicio', 'Red Dead Redemption 2 para PC es un juego de acción y aventuras de mundo abierto en el que el jugador puede vagar libremente. Cuenta algunos elementos en tercera persona, además de otras modalidades de juego en primera persona.', 'red-dead-redemption-2.jpg', 'Red Dead Redemption 2', 18.15),
(24, 10, 'inicio', 'El paquete prémium incluye el siguiente contenido: Expansión ELDEN RING - Shadow of the Erdtree Libro de arte y banda sonora de ELDEN RING - Shadow of the Erdtree ELDEN RING', 'inicio-elden-ring.jpg', 'Elden Ring', 38.69),
(25, 10, 'inicio', 'La edición incluye: Conjunto de armadura \"Salvador de los libres DP-53\". Capa \"La voluntad del Pueblo\". Arma \"Caballero MP-98\". Condición de Superciudadano. Juego de nave de héroe de las estratagemas. Bono de guerra prémium \"Veteranos avezados\".', 'inicio-helldivers-2.jpg', 'Helldivers II', 42.89);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `direccion` varchar(255) COLLATE utf16_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf16_unicode_ci DEFAULT NULL,
  `nombre` varchar(255) COLLATE utf16_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf16_unicode_ci DEFAULT NULL,
  `tipo` varchar(255) COLLATE utf16_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf16 COLLATE=utf16_unicode_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `direccion`, `email`, `nombre`, `password`, `tipo`) VALUES
(1, 'narnia', 'jordycamacho225@gmail.com', 'Jordy', '1234', 'ADMIN'),
(2, 'narnia', 'prueba@gmail.com', 'Prueba', '$2a$10$nEyLxO.WtvCpM8.F0NxRLOV4xll83ZQs8M9m1Ozlmdzn35nreWtKG', 'USER');

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalles`
--
ALTER TABLE `detalles`
  ADD CONSTRAINT `FKdurdo71oa161lmmal7oeaor74` FOREIGN KEY (`orden_id`) REFERENCES `ordenes` (`id`),
  ADD CONSTRAINT `FKio4oyl8qt5jclekxp7bwws2iy` FOREIGN KEY (`producto_id`) REFERENCES `productos` (`id`);

--
-- Filtros para la tabla `ordenes`
--
ALTER TABLE `ordenes`
  ADD CONSTRAINT `FKsqu43gsd6mtx7b1siww96324` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
