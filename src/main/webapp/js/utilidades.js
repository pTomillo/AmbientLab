function convertirFecha(fecha) {
    // Mapear los nombres de los meses a números de mes
    const meses = {
        "ene": "01", "feb": "02", "mar": "03", "abr": "04",
        "may": "05", "jun": "06", "jul": "07", "ago": "08",
        "sep": "09", "oct": "10", "nov": "11", "dic": "12"
    };

    // Separar la fecha en sus componentes: mes, día, año
    const partesFecha = fecha.split(" ");
    const mes = meses[partesFecha[0].toLowerCase()];
    const dia = partesFecha[1].replace(",", ""); // Eliminar la coma del día
    const año = partesFecha[2];

    // Devolver la fecha en formato "yyyy-mm-dd"
    return `${año}-${mes}-${dia}`;
}

function obtenerRol(rolId) {
    // Mapeamos los roles:
    const idRol = {
        1: "Administrador",
        2: "Supervisor",
        3: "Analista",
        4: "Registro",
        9: "Cliente"
    };

    // Buscar el rol correspondiente al rolId
    return idRol[rolId]
}

function mostrarError(mensaje) {
    // Creamos el div para contener el mensaje y le asignamos un id.
    var divError = document.createElement('div');
    divError.id = "divError";

    // Agregamno el mensaje de error.
    divError.textContent = mensaje;

    // Creamos un salto de línea para separar el mensaje de los botones.
    var salto = document.createElement('br');
    divError.appendChild(salto);

    // Creamos el boton de aceptar el error para que desaparezca el mensaje.
    var botonAceptar = document.createElement('button');
    botonAceptar.textContent = 'Aceptar';

    // Agregamos el evento escuchador al boton.
    botonAceptar.addEventListener('click', function() {
        divError.style.display = 'none';
    });
    // Agregamos el boton
    divError.appendChild(botonAceptar);

    // Agrregamos el elemento a su caja correspondiente.
    document.getElementById('cajaError').appendChild(divError);
}


function mostrarConfirmacion(mensaje) {
    return new Promise((resolve) => {
        // Creamos el div para contener el mensaje y le asignamos un id.
        var divConfirmacion = document.createElement('div');
        divConfirmacion.id = "divConfirmacion";

        // Agregamno el mensaje de error.
        divConfirmacion.textContent = mensaje;

        // Creamos un salto de línea para separar el mensaje de los botones.
        var salto = document.createElement('br');
        divConfirmacion.appendChild(salto);
        
        // Creamos los botones de aceptar y rechazar.
        var botonAceptar = document.createElement('button');
        botonAceptar.textContent = 'Aceptar';

        var botonCancelar = document.createElement('button');
        botonCancelar.textContent = 'Cancelar';
        
        // Agregamos el evento escuchador a los botones, devuelven la promesa correspondiente
        botonAceptar.addEventListener('click', function () {
            document.getElementById('cajaConfirmacion').removeChild(divConfirmacion);
            resolve(true);
        });

        botonCancelar.addEventListener('click', function () {
            document.getElementById('cajaConfirmacion').removeChild(divConfirmacion);
            resolve(false);
        });

        divConfirmacion.appendChild(botonAceptar);
        divConfirmacion.appendChild(botonCancelar);

        document.getElementById('cajaConfirmacion').appendChild(divConfirmacion);
    });
}


// -------------------------------------------------------------------------------------------------
//                 FUNCIONES PINTAR
// -------------------------------------------------------------------------------------------------

// Funcion para pintar Usuarios en una tabla html.
function pintarUsuarios(datos) {
    // Inicializamos la tabla con los títulos de columna.
    let html = "<table>";
    html += `<tr>
                <th>Nombre</th>
                <th>Apellidos</th>
                <th>Rol</th>
                <th>Email</th>
                <th></th>
                <th></th>
            </tr>`;
    // Iteramos sobre cada elemento de los datos.
    for (let i = 0; i < datos.length; i++) {
        html += `<tr>
                    <td>${datos[i].nombre}</td>
                    <td>${datos[i].apellidos}</td>
                    <td>${obtenerRol(datos[i].rol)}</td>
                    <td>${datos[i].email}</td>
                    <td><img src="img/edit.png" alt="Editar" onclick="window.location.href='editarUsuario.html?id=${datos[i].id}&op=0'"/></td>
                    <td><img src="img/iconoborrar.png" alt="Borrar" onclick="borrarUsuario(${datos[i].id}, '${datos[i].nombre}', '${datos[i].apellidos}')"/></td>
                </tr>`;
    }
    // Cerramos la tabla.
    html += "</table>";
    // Agregamos el HTML a la caja correspondiente.
    document.getElementById("cajaUsuario").innerHTML = html;
}

// Funcion para pintar los proyectos en una tabla html.
function pintarProyectos(datos) {
    // Inicializamos la tabla con los títulos de columna.
    let html = "<table>";
    html += `<tr>
                <th>Título</th>
                <th>Estado</th>
                <th>Fecha de Inicio</th>
                <th>Fecha de Fin</th>
                <th></th>
                <th></th>
            </tr>`;
    // Iteramos sobre cada elemento de los datos de proyectos.
    for (let i = 0; i < datos.length; i++) {
        html += `<tr>
                    <td>${datos[i].titulo}</td>
                    <td>
                        <select id="estado_${datos[i].id}" onchange="actualizarEstado('${datos[i].id}', this.options[this.selectedIndex].value)">
                            <option value="A la espera" ${datos[i].estado === 'A la espera' ? 'selected' : ''}>A la espera</option>
                            <option value="En proceso" ${datos[i].estado === 'En proceso' ? 'selected' : ''}>En proceso</option>
                            <option value="Finalizado" ${datos[i].estado === 'Finalizado' ? 'selected' : ''}>Finalizado</option>
                        </select>
                    </td>
                    <td>${convertirFecha(datos[i].fechaInicio)}</td>
                    <td>${convertirFecha(datos[i].fechaFin)}</td> 
                    <td><img src="img/edit.png" alt="Editar" onclick="window.location.href='editarProyecto.html?idProyecto=${datos[i].id}&op=1'"/></td>
                    <td><img src="img/iconoborrar.png" alt="Borrar" onclick="borrarProyecto(${datos[i].id}, '${datos[i].titulo}')"/></td>
                </tr>`;
    }
    // Cerramos la tabla.
    html += "</table>";
    // Agregamos el HTML a la caja correspondiente.
    document.getElementById("cajaProyectos").innerHTML = html;
}


// Funcion para pintar las tareas en una tabla html.
function pintarTareas(datos) {
    // Inicializamos la tabla con los títulos de columna.
    let html = "<table>";
    html += `<tr>
                <th>Título</th>
                <th>Observaciones</th>
                <th>Estado</th>
                <th>ID de Proyecto</th>
                <th>ID de Usuario</th>
                <th></th>
                <th></th>
            </tr>`;
    // Iteramos sobre cada elemento de los datos de tareas.
    for (let i = 0; i < datos.length; i++) {
        html += `<tr>
                    <td>${datos[i].titulo}</td>
                    <td>${datos[i].observaciones}</td>
                    <td>
                        <select id="estado_${datos[i].id}" onchange="actualizarEstadoTarea('${datos[i].id}', this.options[this.selectedIndex].value)">
                            <option value="A la espera" ${datos[i].estado === 'A la espera' ? 'selected' : ''}>A la espera</option>
                            <option value="En proceso" ${datos[i].estado === 'En proceso' ? 'selected' : ''}>En proceso</option>
                            <option value="Finalizado" ${datos[i].estado === 'Finalizado' ? 'selected' : ''}>Finalizado</option>
                        </select>
                    </td>
                    <td>${datos[i].idProyecto}</td>
                    <td>${datos[i].idUsuario}</td>
                    <td><img src="img/edit.png" alt="Editar" onclick="window.location.href='editarTarea.html?idTarea=${datos[i].id}&op=0'"/></td>
                    <td><img src="img/iconoborrar.png" alt="Borrar" onclick="borrarTarea(${datos[i].id})"/></td>
                </tr>`;
    }
    // Cerramos la tabla.
    html += "</table>";
    // Agregamos el HTML a la caja correspondiente.
    document.getElementById("cajaTarea").innerHTML = html;
}


// Funcion para pintar Muestras en una tabla html.
function pintarMuestra(datos) {
    // Inicializamos la tabla con los títulos de columna.
    let html = "<table>";
    html += `<tr>
                <th>Referencia</th>
                <th>Tipo</th>
                <th>Estado</th>
                <th>Fecha de Registro</th>
                <th>ID de Proyecto</th>
                <th></th>
                <th></th>
            </tr>`;
    // Iteramos sobre cada elemento de los datos de muestra.
    for (let i = 0; i < datos.length; i++) {
        html += `<tr>
                    <td>${datos[i].referencia}</td>
                    <td>${datos[i].tipo}</td>
                    <td>
                        <select id="estado_${datos[i].id}" onchange="actualizarEstadoMuestra('${datos[i].id}', this.options[this.selectedIndex].value)">
                            <option value="Registrada" ${datos[i].estado === 'Registrada' ? 'selected' : ''}>Registrada</option>
                            <option value="En Analisis" ${datos[i].estado === 'En Analisis' ? 'selected' : ''}>En Analisis</option>
                            <option value="Analizada" ${datos[i].estado === 'Analizada' ? 'selected' : ''}>Analizada</option>
                        </select>
                    </td>
                    <td>${convertirFecha(datos[i].fechaRegistro)}</td>
                    <td>${datos[i].idProyecto}</td>
                    <td><img src="img/edit.png" alt="Editar" onclick="window.location.href='editarMuestra.html?idMuestra=${datos[i].id}&op=0'"/></td>
                    <td><img src="img/iconoborrar.png" alt="Borrar" onclick="borrarMuestra(${datos[i].id}, '${datos[i].referencia}')"/></td>
                </tr>`;
    }
    // Cerramos la tabla.
    html += "</table>";
    // Agregamos el HTML a la caja correspondiente.
    document.getElementById("cajaMuestra").innerHTML = html;
}


// Funcion para pintar Analisis en una tabla html.
function pintarAnalisis(datos) {
    // Inicializamos la tabla con los títulos de columna.
    let html = "<table>";
    html += `<tr>
                <th>ID</th>
                <th>Observaciones</th>
                <th>Tipo</th>
                <th>Estado</th>
                <th>ID de Muestra</th>
                <th>ID de Usuario</th>
                <th>ID de Proyecto</th>
                <th></th>
                <th></th>
            </tr>`;
    // Iteramos sobre cada elemento de los datos de análisis.
    for (let i = 0; i < datos.length; i++) {
        html += `<tr>
                    <td>${datos[i].id}</td>
                    <td>${datos[i].observaciones}</td>
                    <td>${datos[i].tipo}</td>
                    <td>
                        <select id="estado_${datos[i].id}" onchange="actualizarEstadoAnalisis('${datos[i].id}', this.options[this.selectedIndex].value)">
                            <option value="A la espera" ${datos[i].estado === 'A la espera' ? 'selected' : ''}>A la espera</option>
                            <option value="En realizacion" ${datos[i].estado === 'En realizacion' ? 'selected' : ''}>En realizacion</option>
                            <option value="Completado" ${datos[i].estado === 'Completado' ? 'selected' : ''}>Completado</option>
                        </select>
                    </td>
                    <td>${datos[i].idMuestra}</td>
                    <td>${datos[i].idUsuario}</td>
                    <td>${datos[i].idProyecto}</td>
                    <td><img src="img/edit.png" alt="Editar" onclick="window.location.href='editarAnalisis.html?idAnalisis=${datos[i].id}&op=0'"/></td>
                    <td><img src="img/iconoborrar.png" alt="Borrar" onclick="borrarAnalisis(${datos[i].id})"/></td>
                </tr>`;
    }
    // Cerramos la tabla.
    html += "</table>";
    // Agregamos el HTML a la caja correspondiente.
    document.getElementById("cajaAnalisis").innerHTML = html;
}


// Funcion para pintar Resultados en una tabla html.
function pintarResultados(datos) {
    // Inicializamos la tabla con los títulos de columna.
    let html = "<table>";
    html += `<tr>
                <th>Parámetro</th>
                <th>Valor</th>
                <th>Unidad</th>
                <th>ID de Análisis</th>
                <th>Editar</th>
                <th>Borrar</th>
            </tr>`;
    // Iteramos sobre cada elemento de los datos de resultados.
    for (let i = 0; i < datos.length; i++) {
        html += `<tr>
                    <td>${datos[i].parametro}</td>
                    <td>${datos[i].valor}</td>
                    <td>${datos[i].unidad}</td>
                    <td>${datos[i].idAnalisis}</td>
                    <td><img src="img/edit.png" alt="Editar" onclick="window.location.href='editarResultado.html?idResultado=${datos[i].id}'"/></td>
                    <td><img src="img/iconoborrar.png" alt="Borrar" onclick="borrarResultado(${datos[i].id})"/></td>
                </tr>`;
    }
    // Agregamos una fila en blanco al final para agregar nuevos resultados.
    html += `<tr>
                <td><input type="text" id="nuevoParametro"></td>
                <td><input type="text" id="nuevoValor"></td>
                <td><input type="text" id="nuevaUnidad"></td>
                <td><spam type="text" id="IdAnalisis">${datos[i].id}</td>
                <td></td>
                <td><button onclick="agregarNuevoResultado()">Agregar</button></td>
            </tr>`;
    // Cerramos la tabla.
    html += "</table>";
    // Agregamos el HTML a la caja correspondiente.
    document.getElementById("cajaResultado").innerHTML = html;
}



// Funcion para pintar X en una tabla html.
function pintarX(datos) {
    // Inicializamos la tabla.
    let html = "<table>";
    // Iteramos sobre cada elemento de los datos de
    for (let i = 0; i < datos.length; i++) {

    }
    // Cerramos tabla
    html += "</table>";
    // Agregamos el html a la caja correspondiente.
    document.getElementById("cajaX").innerHTML = html;
}



