<%@ page import="ModeloNegocio.Usuario" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <% Usuario c = (Usuario) request.getSession().getAttribute("loggedClient"); %>

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
                            <a class="dropdown-item" href=""> Opción 1 </a>
                            <a class="dropdown-item" href=""> Opción 2 </a>
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

        <div class="row" style="padding: 80px">
            <div class="col-lg-12" style="font-size: 80px; text-transform: uppercase; font-weight: bolder; text-align: center">
                ${bienvenidaTienda}
                <br>
                Nuestra tienda...
                <br>
                Proximamente!
            </div>
        </div>

    </div>
</body>
</html>