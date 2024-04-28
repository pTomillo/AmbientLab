// ------------------------------------------------------------------------
//                      FUNCIONES PINTAR FICHAS
//-------------------------------------------------------------------------





// ------------------------------------------------------------------------
//                      FUNCIONES PINTAR FORMULARIOS
//-------------------------------------------------------------------------

// Pintar Formulario Usuario
function pintarFormularioUsuario(datos) {
    let html= `<form id="formEditarUsuario">
    <label for="nombre">Nombre:</label>
    <input type="text" id="nombre" name="nombre" required value="${datos.nombre}"/>
    <label for="apellidos">Descripción:</label>
    <input type="text" id="apellidos" name="apellidos" required value="${datos.apellidos}"/>
    <label for="rol">Rol:</label>
    <input type="text" id="rol" name="rol" required value="${obtenerRol(datos.rol)}"/>
    <label for="rol>Email</label>
    <input type="text" id="email" name="email" required value="${datos.email}"/>
</form>
<br/>
<input type="button" value="Guardar Usuario" onclick="editarUsuario(${datos.id})"/>`;

     document.getElementById("cajaFormulario").innerHTML = html;

}


// Pintar Formulario Proyecto
function pintarFormulario(datos) {
    let html= `<form id="formEditarProyecto">
    <label for="titulo">Título:</label>
    <input type="text" id="titulo" name="titulo" required value="${datos.titulo}"/>
    <label for="descripcion">Descripción:</label>
    <textarea id="descripcion" name="descripcion" rows="4" required>${datos.descripcion}</textarea>
    <label for="estado">Estado:</label>
    <select id="estado" name="estado" required>
        <option value="A la espera" ${datos.estado === 'A la espera' ? 'selected' : ''}>A la espera</option>
        <option value="En proceso" ${datos.estado === 'En proceso' ? 'selected' : ''}>En proceso</option>
        <option value="Finalizado" ${datos.estado === 'Finalizado' ? 'selected' : ''}>Finalizado</option>
    </select>
    <label for="fechaInicio">Fecha de Inicio:</label>
    <input type="text" id="fechaInicio" name="fechaInicio" required value="${convertirFecha(datos.fechaInicio)}"/>
    <label for="fechaFin">Fecha de Fin:</label>
    <input type="text" id="fechaFin" name="fechaFin" required value="${convertirFecha(datos.fechaFin)}"/>
</form>
<br/>
<input type="button" value="Guardar Proyecto" onclick="editarProyecto(${datos.id})"/>`;

     document.getElementById("cajaFormulario").innerHTML = html;

}


// ------------------------------------------------------------------------
//                      FUNCIONES LISTAR
//-------------------------------------------------------------------------



// ------------------------------------------------------------------------
//                      FUNCIONES EDITAR
//-------------------------------------------------------------------------



// ------------------------------------------------------------------------
//                      FUNCIONES BORRAR
//-------------------------------------------------------------------------

function borrarUsuario(idUsuario, nombre, apellidos) {
    mostrarConfirmacion(`¿Esta seguro de que quiere borrar la cuenta de usuario de ${nombre} ${apellidos}?`).then((confirmado) => {
        if (confirmado) {
            fetch(`GestionUsuario?id=${idUsuario}`, { method: "DELETE" })
                .then(response => {
                    if (response.ok) {
                        return listarUsuarios();
                    } else {
                        throw new Error('Error al intentar borrar la cuenta de usuario, no dispone de los permisos necesarios para realizar esta acción');
                    }
                })
                .catch(error => {
                    mostrarError(error.message);
                });
        }
    });
}

function borrarProyecto(idProyecto, titulo) {
    mostrarConfirmacion(`¿Está seguro de que quiere borrar el proyecto ${titulo}? Aceptar conlleva el borrado de todas las muestras y análisis que forman parte de este proyecto.`).then((confirmado) => {
        if (confirmado) {
            fetch(`GestionProyecto?idProyecto=${idProyecto}`, { method: "DELETE" })
                .then(response => {
                    if (response.ok) {
                        return listarProyectosUsuario();
                    } else {
                        throw new Error('Error al intentar borrar el proyecto, no dispone de los permisos necesarios para realizar esta acción.');
                    }
                })
                .catch(error => {
                    mostrarError(error.message);
                });
        }
    });
}


function borrarMuestra(idMuestra, referencia) {
    mostrarConfirmacion(`¿Está seguro de que quiere borrar la muestra ${referencia}?`).then((confirmado) => {
        if (confirmado) {
            fetch(`GestionMuestra?id=${idMuestra}`, {method: "DELETE"})
                .then(response => {
                    if (response.ok) {
                        return listarMuestras();
                    } else {
                        throw new Error(`Error al intentar borrar la muestra ${referencia} , no dispone de los permisos necesarios para realizar esta acción.`);
                    }
                })
                .catch(error => {
                    mostrarError(error.message);
                });
        }
    });
}

function borrarAnalisis(idAnalisis) {
    mostrarConfirmacion(`¿Está seguro de que quiere borrar el analisis ${idAnalisis}?`).then((confirmado) => {
        if (confirmado) {
            fetch(`GestionAnalisis?idAnalisis=${idAnalisis}`, {method: "DELETE"})
                .then(response => {
                    if (response.ok) {
                        return listarAnalisis();
                    } else {
                        throw new Error(`Error al intentar borrar el analisis ${idAnalisis} , no dispone de los permisos necesarios para realizar esta acción`);
                    }
                })
                .catch(error => {
                    mostrarError(error.message);
                });
        }
    });
}

function borrarResultado(idResultado) {
    mostrarConfirmacion(`¿Está seguro de que quiere borrar este resultado?`).then((confirmado) => {
        if (confirmado) {
            fetch(`GestionResultado?idResultado=${idResultado}`, {method: "DELETE"})
                .then(response => {
                    if (response.ok) {
                        return listarResultados();
                    } else {
                        throw new Error(`Error al intentar borrar el resultado, no dispone de los permisos necesarios para realizar esta acción`);
                    }
                })
                .catch(error => {
                    mostrarError(error.message);
                });
        }
    });
}


function borrarTarea(idTarea) {
    mostrarConfirmacion("¿Está seguro de que quiere borrar esta tarea?").then((confirmado) => {
        if (confirmado) {
            fetch(`GestionTarea?idTarea=${idTarea}`, {method: "DELETE"})
                .then(response => {
                    if (response.ok) {
                        return listarTareasUsuario();
                    } else {
                        throw new Error('Error al intentar borrar la tarea, no dispone de los permisos necesarios para realizar esta acción');
                    }
                })
                .catch(error => {
                    mostrarError(error.message);
                });
        }
    });
}


// ------------------------------------------------------------------------
//                      FUNCIONES ACTUALIZAR ESTADO
//-------------------------------------------------------------------------

// Actualizar el estado de un Proyecto
function actualizarEstadoProyecto(idProyecto, estado){
    // Llamamos al servlet para actualziar el estado.
    fetch(`GestionProyecto?op=1&idProyecto=${idProyecto}&estado=${estado}`, {method: "PUT"})
        .then(response => {
            if (!response.ok) {
                // Si la respuesta es un error lanzamos el aviso.
                throw new Error('Error al actualizar el estado del proyecto, no dispones de los permisos necesarios para realizar esta accion');
            }
        })
        .catch(error => {
            // Si la respuesta es un error lanzamos el aviso.
            mostrarError(error.message);
        })
}
// Actualizar el estado de una Tarea
function actualizarEstadoTarea(idTarea, estado){
    // Llamamos al servlet para actualziar el estado.
    fetch(`GestionTarea?op=1&idTarea=${idTarea}&estado=${estado}`, {method: "PUT"})
    .then(response => {
        if (!response.ok) {
            // Si la respuesta es un error lanzamos el aviso.
            throw new Error('Error al actualizar el estado de la tarea, no dispones de los permisos necesarios para realizar esta accion');
        }
    })
    .catch(error => {
        // Si la respuesta es un error lanzamos el aviso.
        mostrarError(error.message);
    })
}
// Actualizar el estado de una Muestra
function actualizarEstadoMuestra(idMuestra, estado){
    // Llamamos al servlet para actualziar el estado.
    fetch(`GestionMuestra?op=1&idMuestra=${idMuestra}&estado=${estado}`, {method: "PUT"})
    .then(response => {
        if (!response.ok) {
            // Si la respuesta es un error lanzamos el aviso.
            throw new Error('Error al actualizar el estado de la muestra, no dispones de los permisos necesarios para realizar esta accion');
        }
    })
    .catch(error => {
        // Si la respuesta es un error lanzamos el aviso.
        mostrarError(error.message);
    })
}
// Actualizar el estado de un Analisis
function actualizarEstadoAnalisis(idAnalisis, estado){
    // Llamamos al servlet para actualziar el estado.
    fetch(`GestionAnalisis?op=1&idAnalisis=${idAnalisis}&estado=${estado}`, {method: "PUT"})
    .then(response => {
        if (!response.ok) {
            // Si la respuesta es un error lanzamos el aviso.
            throw new Error('Error al actualizar el estado del analisis, no dispones de los permisos necesarios para realizar esta accion');
        }
    })
    .catch(error => {
        // Si la respuesta es un error lanzamos el aviso.
        mostrarError(error.message);
    })
}


