<%@ page import="ModeloNegocio.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>

<html>
<head>
    <title> VIZUAL CONTENDER </title>

    <style>
        .info {
            background-color: #c4c4c4;
            margin: 29px auto;
            padding: 20px;
            border-radius: 7px;
        }

        .titulo-info {
            text-align: center;
            font-weight: bolder;
            text-transform: uppercase;
            font-size: 37px !important;
        }

        .info p {
            margin: 10px 20px;
            display: block;
            font-size: 29px;
            white-space: pre-line;
            width: 100%;
        }

        .metodoPago, .direccion {
            background-color: #cecece;
            margin: 14px auto;
            border-radius: 6px;
            padding: 9px;
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
    </script>
</head>

<body>
<% Usuario u = (Usuario) request.getSession().getAttribute("loggedUsuario"); %>

<div class="container-fluid main-content">
    <nav class="navbar navbar-expand-lg navbar-dark" id="storeBar">
        <button class="navbar-toggler" id="compressed-navbar" type="button" data-toggle="collapse" data-target="#storeBarDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon" style="color: black"></span>
        </button>

        <div class="collapse navbar-collapse" id="storeBarDropdown" style="height: 100%">
            <ul class="navbar-nav">
                <li class="nav-item dropdown   mr-auto">
                    <a class="nav-link dropdown-toggle" data-target=""  target="_blank" href="" data-toggle="dropdown">
                        <%= u.getNombre() %>
                    </a>
                    <div class="dropdown-menu animate slideIn">
                        <a class="dropdown-item" href="#!perfil"> Mi perfil </a>
                        <a class="dropdown-item" href="#!pedidos"> Mis pedidos </a>
                        <a class="dropdown-item" id="logOutButton" href=""> Log out </a>
                    </div>
                </li>

                <li class="nav-item   ml-auto">
                    <a class="nav-link" href="#!carrito">
                        <img id="cartIcon" src="tienda/_imagenes/cart-symbol.svg" style="height: 65px;"/>
                        Carrito
                    </a>
                </li>
            </ul>
        </div>
    </nav>

    <sql:setDataSource var="BaseDatos" driver="com.mysql.jdbc.Driver" url= "jdbc:mysql://localhost:3306/mosteiroDelPilar"
                       user= "mosteiroDelPilar" password= "1234" />

    <sql:query var="datosUsuario" dataSource="${BaseDatos}">
        SELECT *
        FROM usuario
        WHERE id = <%= u.getId() %>
    </sql:query>

    <sql:query var="direcciones" dataSource="${BaseDatos}">
        SELECT *
        FROM direccionEnvio
        WHERE usuario = <%= u.getId() %>
    </sql:query>

    <sql:query var="metodosPago" dataSource="${BaseDatos}">
        SELECT *
        FROM metodoPago
        WHERE id = <%= u.getId() %>
    </sql:query>


    <div class="container" style="padding: 85px 0">
        <div class="row info">
            <p class="titulo-info"> Datos personales </p>

            <c:forEach var="dato" items="${datosUsuario.rows}">
                <p id=""> Nombre: ${dato.nombre} ${dato.apellidos} </p>

                <p id=""> Correo electrónico: ${dato.email} </p>

                <p id=""> DNI: ${dato.dni} </p>
            </c:forEach>
        </div>

        <div class="row">
            <div class="col-lg-6 info">
                <p class="titulo-info"> Direcciones de envío </p>

                <c:forEach var="direccion" items="${direcciones.rows}">
                    <div class="row direccion">
                        <p id=""> ${direccion.calle}, ${direccion.num} </p>

                        <p id=""> ${direccion.piso} </p>

                        <p id=""> ${direccion.ciudad}, ${direccion.provincia} </p>

                        <p id=""> ${direccion.codigoPostal} </p>
                    </div>
                </c:forEach>
            </div>

            <div class="col-lg-6 info">
                <p class="titulo-info"> Métodos de pago </p>

                <c:forEach var="metodoPago" items="${metodosPago.rows}">
                    <div class="row metodoPago">
                        <p id=""> Facturación igual que envío: ${metodoPago.facturacionIgualEnvio} </p>

                        <p id=""> Tipo: ${metodoPago.metodoPago} </p>

                        <c:if test="${metodoPago.metodoPago == 'tarjeta'}">
                            <p id=""> Núm.: ${metodoPago.tarjeta} </p>

                            <p id=""> CCV: ${metodoPago.codSeguridadTarjeta} </p>

                            <p id=""> Caducidad: ${metodoPago.caducidadTarjeta} </p>
                        </c:if>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>