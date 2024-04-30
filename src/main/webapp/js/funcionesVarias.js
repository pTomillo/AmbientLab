// ------------------------------------------------------------------------
//                      FUNCIONES PINTAR FICHAS
//-------------------------------------------------------------------------





// ------------------------------------------------------------------------
//                      FUNCIONES PINTAR FORMULARIOS
//-------------------------------------------------------------------------

// Pintar Formulario Usuario
function pintarFormularioUsuario(datos) {
    let html = `<form id="formEditarUsuario">
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
function pintarFormularioProyecto(datos) {
    let html = `<form id="formEditarProyecto">
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

// Pintar Formulario Muestra
function pintarFormularioMuestra(datos) {
    let html = `<form id="formEditarMuestra">
    <label for="Referencia"><b>Referencia:</b></label>
    <input type="text" id="referencia" name="referencia" required value="${datos.referencia}"/>
    <br><br>
    <label for="tipo"><b>Tipo</b></label>
    <select id="tipo" name="tipo" required>
        <option value="Biologica" ${datos.estado === 'Biologica' ? 'selected' : ''}>Biologica</option>
        <option value="Geologica" ${datos.estado === 'Geologica' ? 'selected' : ''}>Geologica</option>
        <option value="Suelo" ${datos.estado === 'Suelo' ? 'selected' : ''}>Suelo</option>
    </select>
    <br><br>
    <label for="origen"><b>Origen:</b></label>
    <input type="text" id="origen" name="origen" required value="${datos.origen}"/>
    <br><br>
    <label for="estado"><b>Estado:</b></label>
    <select id="estado" name="estado" required>
        <option value="Registrada" ${datos.estado === 'Registrada' ? 'selected' : ''}>Registrada</option>
        <option value="En Analisis" ${datos.estado === 'En Analisis' ? 'selected' : ''}>En Analisis</option>
        <option value="Analizada" ${datos.estado === 'Analizada' ? 'selected' : ''}>Analizada</option>
    </select>
    <br><br>
    <label for="fechaRegistro"><b>Fecha de registro:</b></label>
    <input type="text" id="fechaRegistro" name="fechaRegistro" required value="${convertirFecha(datos.fechaRegistro)}"/>
    <br><br>
</form>
<br/>
<input type="button" value="Guardar Proyecto" onclick="editarProyecto(${datos.id})"/>`;

    document.getElementById("cajaFormulario").innerHTML = html;

}

// Pintar Formulario Muestra
function pintarFormularioMuestra(datos) {
    let html = `<form id="formEditarMuestra">
    <label for="Referencia"><b>Referencia:</b></label>
    <input type="text" id="referencia" name="referencia" required value="${datos.referencia}"/>
    <br><br>
    <label for="tipo"><b>Tipo</b></label>
    <select id="tipo" name="tipo" required>
        <option value="Biologica" ${datos.estado === 'Biologica' ? 'selected' : ''}>Biologica</option>
        <option value="Geologica" ${datos.estado === 'Geologica' ? 'selected' : ''}>Geologica</option>
        <option value="Suelo" ${datos.estado === 'Suelo' ? 'selected' : ''}>Suelo</option>
    </select>
    <br><br>
    <label for="origen"><b>Origen:</b></label>
    <input type="text" id="origen" name="origen" required value="${datos.origen}"/>
    <br><br>
    <label for="estado"><b>Estado:</b></label>
    <select id="estado" name="estado" required>
        <option value="Registrada" ${datos.estado === 'Registrada' ? 'selected' : ''}>Registrada</option>
        <option value="En Analisis" ${datos.estado === 'En Analisis' ? 'selected' : ''}>En Analisis</option>
        <option value="Analizada" ${datos.estado === 'Analizada' ? 'selected' : ''}>Analizada</option>
    </select>
    <br><br>
    <label for="fechaRegistro"><b>Fecha de registro:</b></label>
    <input type="text" id="fechaRegistro" name="fechaRegistro" required value="${convertirFecha(datos.fechaRegistro)}"/>
    <br><br>
</form>
<br/>
<input type="button" value="Guardar Proyecto" onclick="editarMuestra(${datos.id})"/>`;

    document.getElementById("cajaFormulario").innerHTML = html;
}

// Pintar Formulario Analisis
function pintarFormularioAnalisis(datos) {
    let html = `<form id="formEditarAnalisis">
    <label for="idAnalisis"><b>ID:</b></label>
    <span id="idAnalisis">${datos.id}</span>
    <br><br>
    <label for="observaciones"><b>Observaciones:</b></label>
    <textarea id="observaciones" name="observaciones" rows="4" required>${datos.observaciones}</textarea>
    <br><br>
    <label for="tipo"><b>Tipo</b></label>
    <select id="tipo" name="tipo" required>
        <option value="ICP-MS" ${datos.estado === 'ICP-MS' ? 'selected' : ''}>ICP-MS</option>
        <option value="GC-MS" ${datos.estado === 'GC-MS' ? 'selected' : ''}>GC-MS</option>
        <option value="HgTotal" ${datos.estado === 'HgTotal' ? 'selected' : ''}>HgTotal</option>
    </select>
    <br><br>
    <label for="estado"><b>Estado:</b></label>
    <select id="estado" name="estado" required>
        <option value="A la espera" ${datos.estado === 'A la espera' ? 'selected' : ''}>A la espera</option>
        <option value="En realizacion" ${datos.estado === 'En realizacion' ? 'selected' : ''}>En realizacion</option>
        <option value="Completado" ${datos.estado === 'Completado' ? 'selected' : ''}>Completado</option>
    </select>
    <br><br>
    <label for="idMuestra"><b>Muestra:</b></label>
    <input type="text" id="idMuestra" name="idMuestra" required value="${datos.idMuestra}"/>
    <br><br>
    <label for="idUsuario"><b>Usuario:</b></label>
    <input type="text" id="idUsuario" name="idUsuario" required value="${datos.idUsuario}"/>
    <br><br>
    <label for="idProyecto"><b>Proyecto:</b></label>
    <input type="text" id="idProyecto" name="idProyecto" required value="${datos.idProyecto}"/>
    <br><br>
</form>
<br/>
<input type="button" value="Guardar Muestra" onclick="editarAnalisis(${datos.id})"/>`;

    document.getElementById("cajaFormulario").innerHTML = html;
}

// Pintar Formulario Tarea
function pintarFormularioTarea(datos) {
    let html = `<form id="formEditarTarea">
    <label for="titulo"><b>Titulo:</b></label>
    <input type="text" id="titulo" name="titulo" required value="${datos.titulo}"/>
    <br><br>
    <label for="observaciones"><b>Observaciones:</b></label>
    <textarea id="observaciones" name="observaciones" rows="4" required>${datos.observaciones}</textarea>
    <br><br>
    <label for="estado"><b>Estado:</b></label>
    <select id="estado" name="estado" required>
        <option value="A la espera" ${datos.estado === 'A la espera' ? 'selected' : ''}>A la espera</option>
        <option value="En proceso" ${datos.estado === 'En proceso' ? 'selected' : ''}>En proceso</option>
        <option value="Finalizado" ${datos.estado === 'Finalizado' ? 'selected' : ''}>Finalizado</option>
    </select>
    <br><br>
    <label for="idUsuario"><b>Usuario:</b></label>
    <input type="text" id="idUsuario" name="idUsuario" required value="${datos.idUsuario}"/>
    <br><br>
    <label for="idProyecto"><b>Proyecto:</b></label>
    <input type="text" id="idProyecto" name="idProyecto" required value="${datos.idProyecto}"/>
    <br><br>
</form>
<br/>
<input type="button" value="Guardar Tarea" onclick="editarTarea(${datos.id})"/>`;

    document.getElementById("cajaFormulario").innerHTML = html;
}

// Pintar Formulario Tarea
function pintarFormularioResultado(datos) {
    let html = `<form id="formEditarResultado">
    <label for="parametro"><b>Parametro:</b></label>
    <input type="text" id="parametro" name="parametro" required value="${datos.parametro}"/>
    <br><br>
    <label for="valor"><b>Valor:</b></label>
    <input type="text" id="valor" name="valor" required value="${datos.valor}"/>
    <br><br>
    <label for="unidad"><b>Unidad:</b></label>
    <input type="text" id="unidad" name="unidad" required value="${datos.unidad}"/>
    <br><br>
    <label for="idAnalisis"><b>Id de Analisis:</b></label>
    <input type="text" id="idAnalisis" name="idAnalisis" required value="${datos.idAnalisis}"/>
    <br><br>
</form>
<br/>
<input type="button" value="Guardar Resultado" onclick="editarResultado(${datos.id})"/>`;

    document.getElementById("cajaFormulario").innerHTML = html;
}


// ------------------------------------------------------------------------
//                      FUNCIONES LISTAR
//-------------------------------------------------------------------------



// ------------------------------------------------------------------------
//                      FUNCIONES EDITAR
//-------------------------------------------------------------------------



function editarUsuario(idUsuario) {
    // Obtener los valores actualizados del formulario
    const nombre = document.getElementById("nombre").value;
    const apellidos = document.getElementById("apellidos").value;
    const email = document.getElementById("email").value;

    mostrarConfirmacion(`¿Esta seguro de que quiere modificar su cuenta de usuario?`).then((confirmado) => {
        if (confirmado) {
            fetch(`GestionUsuario?id=${idUsuario}&nombre=${nombre}&apellidos=${apellidos}&email=${email}&op=0`, { method: "PUT" })
                .then(response => {
                    if (response.ok) {
                        window.history.back();;
                    } else {
                        throw new Error('Error al intentar actualizar la cuenta de usuario, no dispone de los permisos necesarios para realizar esta acción');
                    }
                })
                .catch(error => {
                    mostrarError(error.message);
                });
        }
    });
}

function editarUsuarioByAdmin(idUsuario) {
    // Obtener los valores actualizados del formulario
    const nombre = document.getElementById("nombre").value;
    const apellidos = document.getElementById("apellidos").value;
    const email = document.getElementById("email").value;
    const rol = document.getElementById("rol").value;

    mostrarConfirmacion(`¿Esta seguro de que quiere modificar esta cuenta de usuario?`).then((confirmado) => {
        if (confirmado) {
            fetch(`GestionUsuario?id=${idUsuario}&nombre=${nombre}&apellidos=${apellidos}&email=${email}&op=0&rol=${rol}`, { method: "PUT" })
                .then(response => {
                    if (response.ok) {
                        window.history.back();;
                    } else {
                        throw new Error('Error al intentar actualizar la cuenta de usuario');
                    }
                })
                .catch(error => {
                    mostrarError(error.message);
                });
        }
    });
}

function asignarRolByAdmin(idUsuario, rol) {
    // Llamamos al servlet para actualziar el estado.
    fetch(`GestionUsuario?id=${idUsuario}&op=3&rol=${rol}`, { method: "PUT" })
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

function cambiarContrasena() {
    // Obtener los valores actualizados del formulario
    const con1 = document.getElementById("con1").value;
    const con2 = document.getElementById("con2").value;
    const ncon = document.getElementById("ncon").value;

    mostrarConfirmacion(`¿Esta seguro de que quiere modificar su cuenta de usuario?`).then((confirmado) => {
        if (confirmado) {
            fetch(`GestionUsuario?op=2&con1=${con1}&con2=${con2}&nCon=${ncon}`, { method: "PUT" })
                .then(response => {
                    if (response.ok) {
                        window.history.back();;
                    } else {
                        throw new Error('Error al intentar actualizar la contraaseña, intentelo de nuevo.');
                    }
                })
                .catch(error => {
                    mostrarError(error.message);
                });
        }
    });
}

function resetearContrasenaByAdmin(idUsuario, nombre, apellidos) {
    // Obtener los valores actualizados del formulario
    const ncon = document.getElementById("ncon").value;
    mostrarConfirmacion(`¿Estás seguro de que cambiar la contraseña del usuario ${nombre} ${apellidos}`).then((confirmado) => {
        if (confirmado) {
            fetch(`GestionUsuario?op=4&id=${idUsuario}&nCon=${ncon}`, { method: "PUT" })
                .then(response => {
                    if (response.ok) {
                        window.history.back();;
                    } else {
                        throw new Error('Error al intentar actualizar la contraaseña, intentelo de nuevo.');
                    }
                })
                .catch(error => {
                    mostrarError(error.message);
                });
        }
    });
}

function editarProyecto(idProyecto) {
    // Obtener los valores actualizados del formulario
    const titulo = document.getElementById("titulo").value;
    const descripcion = document.getElementById("descripcion").value;
    const estado = document.getElementById("estado").value;
    const fechaInicio = document.getElementById("fechaInicio").value;
    const fechaFin = document.getElementById("fechaFin").value;

    mostrarConfirmacion(`¿Estás seguro de actualizar el proyecto ${titulo}?`).then((confirmado) => {
        if (confirmado) {
            fetch(`GestionProyecto?op=0&idProyecto=${idProyecto}&titulo=${titulo}&descripcion=${descripcion}&estado=${estado}&fechaInicio=${fechaInicio}&fechaFin=${fechaFin}`, { method: "PUT" })
                .then(response => {
                    if (response.ok) {
                        window.history.back();;
                    } else {
                        throw new Error('Error al editar el proyecto, no dispone de los permisos necesarios para realziar esta accion.');
                    }
                })
                .catch(error => {
                    mostrarError(error.message);
                });
        }
    });
}

function editarMuestra(idMuestra) {
    // Obtener los valores actualizados del formulario
    const referencia = document.getElementById("referencia").value;
    const tipo = document.getElementById("tipo").value;
    const origen = document.getElementById("origen").value;
    const estado = document.getElementById("estado").value;
    const fechaRegistro = document.getElementById("fechaRegistro").value;
    const idProyecto = document.getElementById("idProyecto").value;

    mostrarConfirmacion(`¿Esta seguro de que quiere modificar la muestra ${referencia}?`).then((confirmado) => {
        if (confirmado) {
            fetch(`GestionMuestra?op=0&idMuestra=${idMuestra}&referencia=${referencia}&tipo=${tipo}&origen=${origen}&estado=${estado}&fechaRegistro=${fechaRegistro}&idProyecto=${idProyecto}`, { method: "PUT" })
                .then(response => {
                    if (response.ok) {
                        window.history.back();
                    } else {
                        throw new Error(`Error al intentar actualizar la muestras ${referencia}, no dispone de los permisos necesarios para realizar esta acción`);
                    }
                })
                .catch(error => {
                    mostrarError(error.message);
                });
        }
    });
}

function editarAnalisis(idAnalisis) {
    // Obtener los valores actualizados del formulario
    const observaciones = document.getElementById("observaciones").value;
    const tipo = document.getElementById("tipo").value;
    const fechaAnalisis = document.getElementById("fechaAnalisis").value;
    const estado = document.getElementById("estado").value;
    const idMuestra = document.getElementById("idMuestra").value;
    const idUsuario = document.getElementById("idMuestra").value;
    const idProyecto = document.getElementById("idProyecto").value;

    mostrarConfirmacion(`¿Esta seguro de que quiere modificar este analisis?`).then((confirmado) => {
        if (confirmado) {
            fetch(`GestionAnalisis?observaciones=${observaciones}&tipo=${tipo}&fechaAnalisis=${fechaAnalisis}&estado=${estado}&idMuestra=${idMuestra}&idUsuario=${idUsuario}&idProyecto=${idProyecto}&idAnalisis${idAnalisis}&op=0`, { method: "PUT" })
                .then(response => {
                    if (response.ok) {
                        window.history.back();
                    } else {
                        throw new Error(`Error al intentar actualizar el analisis, no dispone de los permisos necesarios para realizar esta acción`);
                    }
                })
                .catch(error => {
                    mostrarError(error.message);
                });
        }
    });
}

function editarResultado(idResultado) {
    // Obtener los valores actualizados del formulario
    const parametro = document.getElementById("parametro").value;
    const valor = document.getElementById("valor").value;
    const unidad = document.getElementById("unidad").value;
    const idAnalisis = document.getElementById("idAnalisis").value;


    mostrarConfirmacion(`¿Esta seguro de que quiere modificar este resultado?`).then((confirmado) => {
        if (confirmado) {
            fetch(`GestionResultado?parametro=${parametro}&valor=${valor}&unidad=${unidad}&idAnalisis=${idAnalisis}&idResultado=${idResultado}`, { method: "PUT" })
                .then(response => {
                    if (response.ok) {
                        window.history.back();
                    } else {
                        throw new Error(`Error al intentar actualizar el resultado, no dispone de los permisos necesarios.`);
                    }
                })
                .catch(error => {
                    mostrarError(error.message);
                });
        }
    });
}

function editarTarea(idTarea) {
    // Obtener los valores actualizados del formulario
    const titulo = document.getElementById("titulo").value;
    const observaciones = document.getElementById("observaciones").value;
    const estado = document.getElementById("estado").value;
    const idUsuario = document.getElementById("idMuestra").value;
    const idProyecto = document.getElementById("idProyecto").value;

    mostrarConfirmacion(`¿Esta seguro de que quiere modificar este analisis?`).then((confirmado) => {
        if (confirmado) {
            fetch(`GestionTarea?titulo=${titulo}&observaciones=${observaciones}&estado=${estado}&idProyecto=${idProyecto}&idUsuario=${idUsuario}&idTarea=${idTarea}&op=0`, { method: "PUT" })
                .then(response => {
                    if (response.ok) {
                        window.history.back();
                    } else {
                        throw new Error(`Error al intentar editar la tarea, no dispone de los permisos necesarios para realizar esta acción`);
                    }
                })
                .catch(error => {
                    mostrarError(error.message);
                });
        }
    });
}

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
            fetch(`GestionMuestra?id=${idMuestra}`, { method: "DELETE" })
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
            fetch(`GestionAnalisis?idAnalisis=${idAnalisis}`, { method: "DELETE" })
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
            fetch(`GestionResultado?idResultado=${idResultado}`, { method: "DELETE" })
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
            fetch(`GestionTarea?idTarea=${idTarea}`, { method: "DELETE" })
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
function actualizarEstadoProyecto(idProyecto, estado) {
    // Llamamos al servlet para actualziar el estado.
    fetch(`GestionProyecto?op=1&idProyecto=${idProyecto}&estado=${estado}`, { method: "PUT" })
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
function actualizarEstadoTarea(idTarea, estado) {
    // Llamamos al servlet para actualziar el estado.
    fetch(`GestionTarea?op=1&idTarea=${idTarea}&estado=${estado}`, { method: "PUT" })
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
function actualizarEstadoMuestra(idMuestra, estado) {
    // Llamamos al servlet para actualziar el estado.
    fetch(`GestionMuestra?op=1&idMuestra=${idMuestra}&estado=${estado}`, { method: "PUT" })
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
function actualizarEstadoAnalisis(idAnalisis, estado) {
    // Llamamos al servlet para actualziar el estado.
    fetch(`GestionAnalisis?op=1&idAnalisis=${idAnalisis}&estado=${estado}`, { method: "PUT" })
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


