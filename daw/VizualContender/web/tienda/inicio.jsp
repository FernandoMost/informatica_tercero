<%@ page import="ModeloNegocio.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<%@page isELIgnored="false"%>

<!DOCTYPE html>

<html>
<head>
    <title> VIZUAL CONTENDER </title>

    <style>
        #storeBar {
            position: fixed;
            width: 100%;
            padding: 0 !important;
            z-index: 89;
        }

        #storeBar,
        #storeBar * {
            background-color: gray;
            transition-duration: 0.3s;
        }

        @media screen and (max-width: 767px) { #storeBar li { width: 100% } }

        #storeBar a:hover,
        #storeBar a:hover #cartIcon {
            background-color: #a6a6a6;
        }

        #storeBar a:hover #cartIcon {
            filter: invert(0%);
        }

        #storeBar .navbar-toggler-icon {
            background-image: url(./_imagenes/toggler.png);
            filter: saturate(0%) contrast(1000%);
        }

        #cartIcon {
            filter: invert(100%);
        }

        .articulo {
            margin: 40px 0 40px 0;
            background-color: #b9b9b9;
            padding: 25px;
        }

        .imagenArticulo {
            width: 100%;
        }

        .nombreArticulo {
            font-size: 40px;
            line-height: 35px;
            text-transform: uppercase;
            font-weight: bolder;
            margin: 20px 0;
        }

        .descripcionArticulo { }
        .precioArticulo {
            text-align: center;
            font-size: 45px;
        }

        .stockArticulo { }

        .cambiarCantidad {
            height: 50px;
            width: 50px;
            color: white;
            background-color: black;
            padding: 2px 2px;
            font-size: 30px;
            font-weight: bolder;
            transition-duration: 0.5s;
            border: 0;
        }

        .cambiarCantidad:hover {
            color: black;
            background-color: white;
        }

        .cantidad {
            height: 50px;
            width: 50px;
            font-size: 30px;
            border: 0;
            margin: 0 12px;
            text-align: center;
        }

        .añadirCesta {
            color: white;
            background-color: black;

            min-width: 85%;

            margin: 20px auto;
            padding: 10px 20px;
            font-size: 18px;

            text-transform: uppercase;
            font-weight: bolder;
            transition-duration: 0.5s;

            border: 0;
        }

        .añadirCesta:hover {
            color: black;
            background-color: white;
        }
    </style>

    <script>
        $(function() {
            if (getCookie("mosteiroDelPilar") === null) {
                window.location.href = "#!/login";
            }
        });

        $("#logOutButton").on('click', function () {
            document.cookie = 'mosteiroDelPilar=;expires=Thu, 01 Jan 1970 00:00:01 GMT;';
            window.location.href = "#!/login";
        });

        $(".cambiarCantidad.menos").on('click', function () {
            var textField = $(this).closest('form').find('input.cantidad');
            var cantidad = parseInt(textField.val());

            if (cantidad > 1)
                cantidad--;

            textField.val(cantidad);
        });

        $(".cambiarCantidad.mas").on('click', function () {
            var textField = $(this).closest('form').find('input.cantidad');
            var cantidad = parseInt(textField.val());
            cantidad++;
            textField.val(cantidad);
        });
    </script>
</head>

<body>
    <% Usuario c = (Usuario) request.getSession().getAttribute("loggedClient"); %>
    <sql:setDataSource var="BaseDatos" driver="com.mysql.jdbc.Driver" url= "jdbc:mysql://localhost:3306/mosteiroDelPilar"
                       user= "mosteiroDelPilar" password= "1234" />

    <sql:query var="articulosBD" dataSource="${BaseDatos}"> SELECT * FROM articulo </sql:query>

    <div class="container-fluid main-content">
        <nav class="navbar navbar-expand-lg navbar-dark" id="storeBar">
            <button class="navbar-toggler" id="compressed-navbar" type="button" data-toggle="collapse" data-target="#storeBarDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon" style="color: black"></span>
            </button>

            <div class="collapse navbar-collapse" id="storeBarDropdown" style="height: 100%">
                <ul class="navbar-nav">
                    <li class="nav-item dropdown   mr-auto">
                        <a class="nav-link dropdown-toggle" data-target=""  target="_blank" href="" data-toggle="dropdown">
                            <%= c.getNombre() %>
                        </a>
                        <div class="dropdown-menu animate slideIn">
                            <a class="dropdown-item" href=""> Mi perfil </a>
                            <a class="dropdown-item" href=""> Mis pedidos </a>
                            <a class="dropdown-item" id="logOutButton" href=""> Log out </a>
                        </div>
                    </li>

                    <li class="nav-item   ml-auto">
                        <a class="nav-link" href="">
                            <img id="cartIcon" src="tienda/_imagenes/cart-symbol.svg" style="height: 65px;"/>
                            Carrito
                        </a>
                    </li>
                </ul>
            </div>
        </nav>

        <div class="container" style="padding: 65px 0">
        <%--${bienvenidaTienda}--%>
        <%--<br>--%>
            <c:choose>
                <c:when test="${articulosBD.rowCount !=0}">
                    <c:forEach var="articulo" items="${articulosBD.rows}">
                        <div class="row articulo">
                            <div class="col-lg-4" style="padding: 0 !important;">
                                <img class="imagenArticulo" src="tienda/_imagenes/${articulo.imagen}" alt="${articulo.imagen}">
                            </div>

                            <div class="col-lg-5">
                                <p class="nombreArticulo"> ${articulo.nombre} <p>
                                <p class="descripcionArticulo"> ${articulo.descripcion} <p>
                                <p class="precioArticulo"> ${articulo.precio} € <p>
                            </div>

                            <div class="col-lg-3" style="text-align: center">
                                <c:choose>
                                    <c:when test="${articulo.stock <= 10}">
                                        <p class="stockArticulo" style="color: red; font-weight: bolder"> Unidades disponibles: ${articulo.stock} <p>
                                    </c:when>
                                    <c:otherwise>
                                        <p class="stockArticulo"> Unidades disponibles: ${articulo.stock} <p>
                                    </c:otherwise>
                                </c:choose>

                                <form>
                                    <input class="idProducto" name="idProducto" type="hidden" value="${articulo.id}">

                                    <button class="cambiarCantidad menos"> ─ </button>
                                    <input class="cantidad" type="text" value="1">
                                    <button class="cambiarCantidad mas"> + </button>

                                    <button class="añadirCesta"> Añadir a la cesta </button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    La base de datos está vacía.
                </c:otherwise>
            </c:choose>
        </div>

    </div>
</body>
</html>