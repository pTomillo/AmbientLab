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

// Funcion para pintar Usuarios en una tabla html.
function pintarUsuarios(datos) {
    // Inicializamos la tabla.
    let html = "<table>";
    // Iteramos sobre cada elemento de los datos de
    for (let i = 0; i < datos.length; i++) {
        html += `<tr><td>${datos[i].nombre}</td>
        <td>${datos[i].apellidos}</td>
        <td>${obtenerRol(datos[i].rol)}</td>
        <td>${datos[i].email}</td>
        <td><button onclick="window.location.href='editarUsuario.html?id=${datos[i].id}&op=0'">Editar</button></td>
        <td><input  type="button" value="Borrar" onclick="borrarUsuario(${datos[i].id})"/></td>
        </tr>`    }
    // Cerramos tabla
    html += "</table>";
    // Agregamos el html a la caja correspondiente.
    document.getElementById("cajaUsuario").innerHTML = html;
}

// Funcion para pintar los proyectos en una tabla html.
function pintarProyectos(datos) {
    // Inicializamos la tabla.
    let html = "<table>";
    // Itemamos sobre cada elemento de los datos de proyectos.
    for (let i = 0; i < datos.length; i++) {
        html += `<tr><td>${datos[i].titulo}</td>
        <td>${datos[i].descripcion} </td>
        <td> <select id="estado_${datos[i].id}" onchange="actualizarEstado('${datos[i].id}', this.options[this.selectedIndex].value)">
            <option value="A la espera" ${datos[i].estado === 'A la espera' ? 'selected' : ''}>A la espera</option>
            <option value="En proceso" ${datos[i].estado === 'En proceso' ? 'selected' : ''}>En proceso</option>
            <option value="Finalizado" ${datos[i].estado === 'Finalizado' ? 'selected' : ''}>Finalizado</option>
            </select>
        </td>
        <td>${convertirFecha(datos[i].fechaInicio)}</td>
        <td>${convertirFecha(datos[i].fechaFin)}</td>
        <td><button onclick="window.location.href='editarProyecto.html?idProyecto=${datos[i].id}&op=1'">Editar</button></td>
        <td><input  type="button" value="Borrar" onclick="borrarProyecto(${datos[i].id})"/></td>
        </tr>`
    }
    // Cerramos tabla.
    html += "</table>";
    // Agregamos el html a la caja correspondiente.
    document.getElementById("cajaProyectos").innerHTML = html;
}

// Funcion para pintar las tareas en una tabla html.
function pintarTareas(datos) {
    // Inicializamos la tabla.
    let html = "<table>";
    // Iteramos sobre cada elemento de los datos de tareas.
    for (let i = 0; i < datos.length; i++) {
        html += `<tr><td>${datos[i].titulo}</td>
        <td>${datos[i].observaciones}</td>
        <td>${datos[i].estado} </td>
        <td>${datos[i].idProyecto}</td>
        <td>${datos[i].idUsuario}</td>
        <td><button onclick="window.location.href='editarTarea.html?idTarea=${datos[i].id}&op=1'">Editar</button></td>
        <td><input  type="button" value="Borrar" onclick="borrarTarea(${datos[i].id})"/></td>
        </tr>`
    }
    // Cerramos tabla.
    html += "</table>";
    // Agregamos el html a la caja correspondiente.
    document.getElementById("cajaTarea").innerHTML = html;
}


// Funcion para pintar X en una tabla html.
function pintarUsuario(datos) {
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



