<%@ page import="ModeloNegocio.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>

<!DOCTYPE html>

<html>
<head>
    <title> VIZUAL CONTENDER </title>

    <style>
        .pedido {
            width: 100%;
            background-color: #c6c6c6;
            border: 0;
            border-radius: 10px;
            margin: 3px 0;
        }

        .tipo-info {
            margin: 0 0 10px 0;
            font-size: 23px;
            text-transform: uppercase;
        }

        .imagen-articulo {
            width: 80%;
        }

        .nombre-articulo {
            margin: 10px;
            font-size: 20px;
        }

        .cantidad-articulo {
            font-size: 24px;
        }

        .dato-dentro {
            margin-bottom: 0;
            margin-left: 33px;
        }

        .pedido button {
            color: black !important;
        }
    </style>

    <script>
        $(function() {
            if (getCookie("mosteiroDelPilar") === null) {
                window.location.href = "#!/login";
            }

            if (getCookie("mostrarMensaje") === 'true') {
                $("#modalMensaje").show();
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
                        <p style="display: inline"> Carrito </p>
                    </a>
                </li>
            </ul>
        </div>
    </nav>
    <sql:setDataSource var="BaseDatos" driver="com.mysql.jdbc.Driver" url= "jdbc:mysql://localhost:3306/mosteiroDelPilar"
                       user= "mosteiroDelPilar" password= "1234" />

    <sql:query var="pedidos" dataSource="${BaseDatos}">
        SELECT pedido.id, pedido.fecha, pedido.total, direccionEnvio.calle, direccionEnvio.num, direccionEnvio.piso, direccionEnvio.ciudad, direccionEnvio.provincia, direccionEnvio.codigoPostal, metodoPago.facturacionIgualEnvio, metodoPago.metodoPago, metodoPago.tarjeta, metodoPago.caducidadTarjeta, metodoPago.codSeguridadTarjeta
        FROM pedido
        left join direccionEnvio on pedido.direccionEnvio = direccionEnvio.id
        left join metodoPago on pedido.metodoPago = metodoPago.id
        where pedido.usuario = <%= u.getId() %>
        order by pedido.fecha desc, pedido.id desc
    </sql:query>

    <div class="container" style="padding: 85px 0">
        <div class="row" id="acordeon">
            <c:choose>
                <c:when test="${pedidos.rowCount !=0}">
                    <c:forEach var="pedido" items="${pedidos.rows}">
                        <div class="card pedido">
                            <div class="card-header" id="${pedido.id}">
                                <h5>
                                    <button class="btn btn-link" data-toggle="collapse" data-target="#collapse${pedido.id}" aria-expanded="false" aria-controls="${pedido.id}">
                                        Nº de pedido <b> ${pedido.id} </b>
                                        Realizado el <b> ${pedido.fecha} </b>
                                    </button>
                                </h5>
                            </div>

                            <div id="collapse${pedido.id}" class="collapse" aria-labelledby="${pedido.id}" data-parent="#acordeon">
                                <div class="card-body">
                                    <p class="tipo-info"> Dirección de envío </p>

                                        <p class="dato-dentro"> ${pedido.calle}, ${pedido.num} </p>
                                        <p class="dato-dentro"> ${pedido.piso} </p>
                                        <p class="dato-dentro"> ${pedido.ciudad}, ${pedido.provincia} </p>
                                        <p class="dato-dentro"> ${pedido.codigoPostal} </p>

                                    <hr>

                                    <p class="tipo-info"> Método de pago </p>

                                        <p class="dato-dentro"> Facturación igual que envío: ${pedido.facturacionIgualEnvio} </p>
                                        <p class="dato-dentro"> Tipo: ${pedido.metodoPago} </p>
                                        <c:if test="${pedido.metodoPago == 'tarjeta'}">
                                            <p class="dato-dentro"> Núm.: ${pedido.tarjeta} </p>
                                            <p class="dato-dentro"> CCV: ${pedido.codSeguridadTarjeta} </p>
                                            <p class="dato-dentro"> Caducidad: ${pedido.caducidadTarjeta} </p>
                                        </c:if>

                                    <hr>

                                    <p class="tipo-info"> Artículos </p>

                                    <sql:query var="articulos" dataSource="${BaseDatos}">
                                        SELECT articulo.id, articulo.imagen, articulo.nombre, articulo.precio, articuloEnPedido.cantidad
                                        FROM pedido, articuloEnPedido, articulo
                                        WHERE pedido.id = articuloEnPedido.pedido
                                        AND articuloEnPedido.articulo = articulo.id
                                        AND pedido.usuario = <%= u.getId() %>
                                        AND pedido.id = ${pedido.id}
                                    </sql:query>

                                    <c:forEach var="articulo" items="${articulos.rows}">
                                        <div class="row">
                                            <div class="col-lg-2" style="text-align: center">
                                                <img class="imagen-articulo" src="tienda/_imagenes/${articulo.imagen}" alt="${articulo.imagen}">
                                            </div>

                                            <div class="col-lg-6">
                                                <p class="nombre-articulo"> ${articulo.nombre} </p>
                                            </div>

                                            <div class="col-lg-2" style="text-align: center">
                                                <p class="cantidad-articulo"> ${articulo.cantidad}x </p>
                                            </div>

                                            <div class="col-lg-2" style="text-align: center">
                                                <p class="cantidad-articulo"> ${articulo.precio} € </p>
                                            </div>
                                        </div>
                                    </c:forEach>

                                    <hr>

                                    <p class="tipo-info"> Total </p>
                                    <p class="dato-dentro" style="font-size: 35px;font-weight: bolder;"> ${pedido.total} € </p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p style="margin: 10px auto; font-size: 50px; text-align: center; text-transform: uppercase;">
                        No hay pedidos registrados
                    </p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<div class="modal" id="modalMensaje" style="z-index: 98">
    <span class="close" onclick="closeModal()" >&times;</span>

    <div class="row" style="position: relative; top: 50%; transform: translateY(-50%);">
        <div class="col-sm-10 col-md-8 col-lg-6 mx-auto">
            <div class="card card-signin my-5">
                <div class="card-body">
                    <p style="font-size: 60px;text-align: center;line-height: 64px; margin-bottom: 35px"> Pedido realizado con éxito! </p>

                    <p style="font-size: 16px;text-align: center;"> No dudes en contactárnos por los siguientes medios: </p>

                    <p style="margin-bottom: 0"> <b> ${initParam.nombre} </b> </p>
                    <p style="margin-bottom: 0"> ${initParam. telefono} </p>
                    <p style="margin-bottom: 0"> ${initParam.direccion} </p>
                    <p style="margin-bottom: 0"> ${initParam.email} </p>

                </div>
            </div>
        </div>
    </div>
</div>

<script>
    function openModal() {
        document.getElementById("modalMensaje").style.display = "block";
    }

    function closeModal() {
        document.getElementById("modalMensaje").style.display = "none";
    }
</script>

</body>
</html>