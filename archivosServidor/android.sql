-- phpMyAdmin SQL Dump
-- version 4.4.14
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 06-12-2015 a las 21:22:19
-- Versión del servidor: 5.6.26
-- Versión de PHP: 5.6.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `android`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensaje`
--

CREATE TABLE IF NOT EXISTS `mensaje` (
  `id_mensaje` int(11) NOT NULL,
  `id_usuario1` int(11) NOT NULL,
  `id_usuario2` int(11) NOT NULL,
  `mensaje` varchar(1000) NOT NULL,
  `fecha_mensaje` date NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `mensaje`
--

INSERT INTO `mensaje` (`id_mensaje`, `id_usuario1`, `id_usuario2`, `mensaje`, `fecha_mensaje`) VALUES
(1, 1, 2, 'hola javi', '0000-00-00'),
(2, 1, 2, 'hola Javier!', '0000-00-00'),
(3, 2, 1, 'zjbzjzzbzj', '0000-00-00'),
(4, 1, 2, 'que pasa', '0000-00-00'),
(5, 1, 2, 'funciona?', '0000-00-00'),
(6, 2, 1, 'si', '0000-00-00'),
(7, 2, 1, '', '0000-00-00'),
(8, 2, 1, 'si', '0000-00-00'),
(9, 2, 1, 'si', '0000-00-00'),
(10, 1, 2, 'jajajaja', '0000-00-00'),
(11, 2, 1, '', '0000-00-00'),
(12, 2, 1, 'BUAHH ya lo tenemos ', '0000-00-00'),
(13, 1, 2, 'esto es nitido xD', '0000-00-00'),
(14, 2, 1, 'ahora solo queda lo mÃ¡s fÃ¡cil ', '0000-00-00'),
(15, 2, 1, 'ahora solo queda lo mÃ¡s fÃ¡cil ', '0000-00-00'),
(16, 1, 2, 'esto podriamos meterlo como un chat tambien', '0000-00-00'),
(17, 2, 1, 'si tambiÃ©n, junto con el tres en raya ', '0000-00-00'),
(18, 1, 2, 'claro, y luego se lo vendemos a google', '0000-00-00'),
(19, 2, 1, 'jaja,  nos vamos a hacer de oro ', '0000-00-00'),
(20, 2, 1, 'el profesor seguro que lo flipa cuando se lo enseÃ±emos ', '0000-00-00'),
(21, 2, 1, 'el profesor seguro que lo flipa cuando se lo enseÃ±emos ', '0000-00-00'),
(22, 2, 1, ' y que pasa si salgo ahora? ', '0000-00-00'),
(23, 1, 2, 'puedes volver a entrsr', '0000-00-00'),
(24, 2, 1, 'se te joroba a ti?', '0000-00-00'),
(25, 1, 2, 'no', '0000-00-00'),
(26, 2, 1, 'ahh vale', '0000-00-00'),
(27, 1, 2, 'cuando vuelves se el ultimo.mensaje', '0000-00-00'),
(28, 1, 2, 'se ve*', '0000-00-00'),
(29, 1, 2, 'habria que enseÃ±ar el listado de mensajes tambien', '0000-00-00'),
(30, 2, 1, 'vale vale. pues nada, voy a ver cÃ³mo hacer el tres en raya. si, tener el listado estarÃ­a bien ', '0000-00-00'),
(31, 1, 2, 'hola', '0000-00-00'),
(32, 1, 2, 'holaaaaa', '0000-00-00'),
(33, 1, 2, 'holaaaaa', '0000-00-00'),
(34, 1, 2, 'jasma', '0000-00-00'),
(35, 1, 2, 'pam!', '0000-00-00'),
(36, 1, 2, 'pam, pam!', '0000-00-00'),
(37, 1, 2, 'misaRIndo', '0000-00-00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `id_usuario` int(10) NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `imagen_usuario` varchar(50) DEFAULT NULL,
  `fecha_alta` date NOT NULL,
  `ELO` int(6) NOT NULL,
  `conectado` tinyint(1) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `nombre`, `imagen_usuario`, `fecha_alta`, `ELO`, `conectado`) VALUES
(1, 'luis', NULL, '2015-11-24', 0, 1),
(2, 'javi', NULL, '2015-11-24', 0, 1),
(3, 'pepe', NULL, '2015-11-25', 0, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `mensaje`
--
ALTER TABLE `mensaje`
  ADD PRIMARY KEY (`id_mensaje`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `mensaje`
--
ALTER TABLE `mensaje`
  MODIFY `id_mensaje` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=38;
--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id_usuario` int(10) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
