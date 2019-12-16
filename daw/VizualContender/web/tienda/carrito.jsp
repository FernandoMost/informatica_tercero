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
            margin: 15px 0 !important;
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
        }

        .articulo button,
        #total button,
        #realizarPedido {
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
        #total button:hover,
        #realizarPedido:hover {
            color: black;
            background-color: white;
        }

        .cambiarCantidad {
            height: 35px;
            width: 35px;
        }

        .cantidad {
            height: 35px;
            width: 60px;
            margin: 0 7px;
            text-align: center;
        }

        .eliminarArticulo {
            margin: 0 15px;
            min-height: 35px;
            padding: 5px 23px !important;
            font-size: 15px !important;
            text-transform: uppercase;
        }

        .precioArticulo {
            font-size: 28px;
        }

        #total .col-lg-6,
        #total p {
            padding: 0;
            margin: 0;
        }

        #total p {
            font-size: 21px;
            font-weight: bolder;
        }

        #total .col-lg-8,
        #total .col-lg-4 {
            padding: 0 !important;
        }

        #total > .row {
            background-color: #a2a2a2;
            color: black;
            border-radius: 7px;
            margin: 15px 6px !important;
            padding: 14px 10px;
        }

        #total .row {
            margin: 0 auto;
        }

        @media screen and (max-width: 992px) {
            #total p {
                text-align: center !important;
            }
        }

        #realizarPedido {
            padding: 10px 15px !important;
            min-width: 80%;
        }

        #realizarPedido:disabled {
            opacity: 0.1;
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
<% Usuario u = (Usuario) request.getSession().getAttribute("loggedUsuario"); %>

<script>
    $(function () {
        var tamanoCarrito = <%= u.getCarrito().getNumArticulos() %>;

        if (tamanoCarrito === 0)
            document.getElementById("realizarPedido").disabled = true;
    });
</script>

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

    <sql:query var="precioTotalCarrito" dataSource="${BaseDatos}">
        select SUM(carrito.cantidad*articulo.precio) as total
        from carrito, articulo
        where carrito.articulo = articulo.id
        and usuario = <%= u.getId() %>
    </sql:query>

    <div class="container" style="padding: 85px 0">
        <div class="row">
            <div class="col-lg-8" style="padding: 5px !important;">
                <c:choose>
                    <c:when test="${articulosCarrito.rowCount !=0}">
                        <c:forEach var="articulo" items="${articulosCarrito.rows}">
                            <div class="row articulo">
                                <div class="col-lg-3" style="padding: 5px !important;">
                                    <img class="imagenArticulo" src="tienda/_imagenes/${articulo.imagen}" alt="${articulo.imagen}">
                                </div>

                                <div class="col-lg-7">
                                    <div class="row">
                                        <p class="nombreArticulo"> ${articulo.nombre} <p>
                                    </div>

                                    <div class="row" style="text-align: center; padding: 10px">
                                        <form action="${pageContext.request.contextPath}/CarritoServlet" method="post">
                                            <input class="idProducto" name="idArticulo" type="hidden" value="${articulo.id}">
                                            <input name="tipo" type="hidden" value="cambiarUnaUnidad">

                                            <button class="cambiarCantidad menos" type="submit"> ─ </button>
                                            <input class="cantidad" name="cantidad" type="text" value="${articulo.cantidad}">
                                            <button class="cambiarCantidad mas" type="submit"> + </button>
                                        </form>

                                        <form action="${pageContext.request.contextPath}/CarritoServlet" method="post">
                                            <input class="idProducto" name="idArticulo" type="hidden" value="${articulo.id}">
                                            <input name="tipo" type="hidden" value="eliminarArticulo">
                                            <input name="cantidad" type="hidden" value="0">

                                            <button class="eliminarArticulo" type="submit"> Eliminar </button>
                                        </form>
                                    </div>
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

            <div class="col-lg-4" id="total" style="padding: 5px !important;">
                <div class="row">
                    <div class="row" style="width: 100%">
                        <div class="col-lg-8">
                            <p style="text-align: left"> TOTAL SIN IVA (21%) </p>
                        </div>

                        <div class="col-lg-4">
                            <p style="text-align: right"> <%= String.format("%10.2f €", u.getCarrito().getPrecioTotalSinIva()) %> </p>
                        </div>
                    </div>

                    <hr>

                    <div class="row" style="width: 100%">
                        <div class="col-lg-8">
                            <p style="text-align: left"> TOTAL </p>
                        </div>

                        <div class="col-lg-4">
                            <p style="text-align: right"> <%= String.format("%10.2f €", u.getCarrito().getPrecioTotal()) %> </p>
                        </div>
                    </div>
                </div>

                <form action="#!resumenCompra" style="text-align: center">
                    <button id="realizarPedido" type="submit"> Realizar pedido </button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>