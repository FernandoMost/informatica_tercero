<%@ page import="ModeloNegocio.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>

<html>
<head>
    <title> VIZUAL CONTENDER </title>

    <style>
        .articulo {
            margin: 25px 0 !important;
            background-color: #b3b3b3;
            padding: 20px;
            border-radius: 5px;
        }

        @media screen and (max-width: 992px) {
            .nombreArticulo { margin: 10px auto; text-align: center }
            form { margin: 10px auto !important; }
        }

        .imagenArticulo {
            width: 100%;
            padding: 0;
            margin: 0;
        }

        .nombreArticulo {
            font-size: 25px;
            font-weight: bolder;
            text-transform: uppercase;
            margin: 10px 0;
        }

        .articulo button,
        #total button {
            color: white;
            background-color: black;
            padding: 0;
            font-size: 20px;
            font-weight: bolder;
            text-transform: uppercase;
            transition-duration: 0.5s;
            border: 0;
        }

        .articulo button:hover,
        #total button:hover {
            color: black;
            background-color: white;
        }

        .cantidad, .precioArticulo {
            font-size: 28px;
            text-align: center;
        }

        #total p {
            padding: 0;
            margin: 0;
        }

        #total p {
            font-size: 21px;
            font-weight: bolder;
        }

        @media screen and (max-width: 992px) {
            #total p {
                text-align: center !important;
            }
        }

        .titulo-info {
            width: 100%;
            text-transform: uppercase;
            font-size: 23px;
            font-weight: bolder;
        }

        .botones {
            text-align: center;
        }

        .botones button {
            width: 90%;
            padding: 10px 0;
            font-size: 28px;
            text-transform: uppercase;
            font-weight: bolder;
            border: 0;
            transition-duration: 1s;
        }

        #volver {
            background-color: red;
            color: white;
        }

        #volver:hover {
            color: red;
            background-color: white;
        }

        #confirmar {
            background-color: forestgreen;
            color: white;
        }

        #confirmar:hover {
            color: forestgreen;
            background-color: white;
        }
    </style>

    <script>
        $(function() {
            if (getCookie("mosteiroDelPilar") === null) {
                window.location.href = "#!/login";
            }

            $("input:radio[name=direccionEnvio]:first").attr('checked', true);
            $("input:radio[name=metodoPago]:first").attr('checked', true);
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

    <sql:query var="articulosCarrito" dataSource="${BaseDatos}">
        select articulo.*, carrito.cantidad
        from carrito, articulo
        where carrito.articulo = articulo.id
        and usuario = <%= u.getId() %>
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
        <div class="row articulo">
            <c:choose>
                <c:when test="${articulosCarrito.rowCount !=0}">
                    <c:forEach var="articulo" items="${articulosCarrito.rows}">
                        <div class="row" style="margin: 10px 0">
                            <div class="col-lg-2" style="text-align: center">
                                <img class="imagenArticulo" src="tienda/_imagenes/${articulo.imagen}" alt="${articulo.imagen}">
                            </div>

                            <div class="col-lg-6">
                                <p class="nombreArticulo"> ${articulo.nombre} <p>
                            </div>

                            <div class="col-lg-2">
                                <p class="cantidad"> ${articulo.cantidad}x <p>
                            </div>

                            <div class="col-lg-2" style="text-align: center; padding: 0 !important;">
                                <p class="precioArticulo"> ${articulo.precio} € <p>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p style="margin: 10px auto; font-size: 50px; text-align: center; text-transform: uppercase;">
                        El carrito está vacío
                    </p>
                </c:otherwise>
            </c:choose>
        </div>

        <form id="pedido" action="${pageContext.request.contextPath}/PedidoServlet" method="post">
        </form>

        <div class="row articulo" id="direcciones">
            <c:choose>
                <c:when test="${direcciones.rowCount !=0}">
                    <p class="titulo-info"> Direcciones de envío </p>

                    <c:forEach var="direccion" items="${direcciones.rows}">
                        <input type="radio" name="direccionEnvio" value="${direccion.id}" form="pedido" style="margin-left: 35px">
                            <p style="margin: 0 24px">

                                ${direccion.calle}, ${direccion.num} <br>
                                ${direccion.piso} <br>
                                ${direccion.ciudad}, ${direccion.provincia} <br>
                                ${direccion.codigoPostal} <br>
                            </p>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p style="margin: 10px auto; font-size: 50px; text-align: center; text-transform: uppercase;">
                        El carrito está vacío
                    </p>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="row articulo" id="metodosPago">
            <c:choose>
                <c:when test="${metodosPago.rowCount !=0}">
                    <p class="titulo-info"> Métodos de pago </p>

                    <c:forEach var="metodoPago" items="${metodosPago.rows}">
                        <input type="radio" name="metodoPago" value="${metodoPago.id}" form="pedido" style="margin-left: 35px">
                        <p style="margin: 0 24px">
                            Facturación igual que envío: ${metodoPago.facturacionIgualEnvio} <br>
                            Tipo: ${metodoPago.metodoPago} <br>
                            <c:if test="${metodoPago.metodoPago == 'tarjeta'}">
                                Núm.: ${metodoPago.tarjeta} <br>
                                CCV: ${metodoPago.codSeguridadTarjeta} <br>
                                Caducidad: ${metodoPago.caducidadTarjeta} <br>
                            </c:if>
                        </p>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p style="margin: 10px auto; font-size: 50px; text-align: center; text-transform: uppercase;">
                        El carrito está vacío
                    </p>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="row articulo">
            <div class="row" style="width: 100%">
                <div class="col-lg-8">
                    <p class="titulo-info" style="text-align: left"> TOTAL SIN IVA (21%) </p>
                </div>

                <div class="col-lg-4">
                    <p class="titulo-info" style="text-align: right"> <%= String.format("%10.2f €", u.getCarrito().getPrecioTotalSinIva()) %> </p>
                </div>
            </div>

            <hr>

            <div class="row" style="width: 100%">
                <div class="col-lg-8">
                    <p class="titulo-info" style="text-align: left"> TOTAL </p>
                </div>

                <div class="col-lg-4">
                    <p class="titulo-info" style="text-align: right"> <%= String.format("%10.2f €", u.getCarrito().getPrecioTotal()) %> </p>
                </div>
            </div>
        </div>

        <div class="row botones">
            <div class="col-lg-6">
                <form action="#!carrito">
                    <button type="submit" id="volver"> Volver </button>
                </form>
            </div>

            <div class="col-lg-6">
                <button type="submit" form="pedido" id="confirmar"> Confirmar pedido </button>
            </div>
        </div>
    </div>

</div>
</body>
</html>