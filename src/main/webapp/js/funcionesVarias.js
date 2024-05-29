// -------------------------------------------------------------------------------------------------
//                 FUNCIONES VARIAS
// -------------------------------------------------------------------------------------------------

var urlParams = new URLSearchParams(window.location.search);

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
    botonAceptar.addEventListener('click', function () {
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

function cerrarSesion() {
    fetch(`LoginUsuario?op=1`)
        .then(response => {
            if (response.ok) {
                window.location.href = "LoginWeb.html"
            }
        });
}

// ------------------------------------------------------------------------
//                      FUNCIONES CREAR
//-------------------------------------------------------------------------

function crearUsuario() {
    // Obtener los valores actualizados del formulario
    const nombre = document.getElementById("nombre").value;
    const apellidos = document.getElementById("apellidos").value;
    const email = document.getElementById("email").value;
    const rol = document.getElementById("rol").value;
    const contrasena = document.getElementById("contrasena")

    mostrarConfirmacion(`¿Desea crear el perfil de usuario para ${nombre} ${apellidos}?`).then((confirmado) => {
        if (confirmado) {
            fetch(`GestionProyecto?titulo=${titulo}&descripcion=${descripcion}&estado=${estado}&fechaInicio=${fechaInicio}&fechaFin=${fechaFin}&op=0`, { method: "POST" })
                .then(response => {
                    if (response.ok) {
                        window.history.back();;
                    } else {
                        throw new Error('Error al crear el proyecto, no dispone de los permisos necesarios para realziar esta accion.');
                    }
                })
                .catch(error => {
                    mostrarError(error.message);
                });
        }
    });
}

function crearProyecto() {
    // Obtener los valores actualizados del formulario
    const titulo = document.getElementById("titulo").value;
    const descripcion = document.getElementById("descripcion").value;
    const estado = document.getElementById("estado").value;
    const fechaInicio = document.getElementById("fechaInicio").value;
    const fechaFin = document.getElementById("fechaFin").value;

    mostrarConfirmacion(`¿Desea crear el proyecto ${titulo}?`).then((confirmado) => {
        if (confirmado) {
            fetch(`GestionProyecto?titulo=${titulo}&descripcion=${descripcion}&estado=${estado}&fechaInicio=${fechaInicio}&fechaFin=${fechaFin}&op=0`, { method: "POST" })
                .then(response => {
                    if (response.ok) {
                        window.history.back();
                    } else {
                        throw new Error('Error al crear el proyecto, no dispone de los permisos necesarios para realziar esta accion.');
                    }
                })
                .catch(error => {
                    mostrarError(error.message);
                });
        }
    });
}

function registrarMuestra(){
    // Obtener los valores actualizados del formulario
    const referencia = document.getElementById("referencia").value;
    const tipo = document.getElementById("tipo").value;
    const origen = document.getElementById("origen").value;
    const estado = document.getElementById("estado").value;
    const fechaRegistro = document.getElementById("fechaRegistro").value;
    const idProyecto = document.getElementById("idProyecto").value;

    mostrarConfirmacion(`¿Desea registrar la muestra ${referencia}?`).then((confirmado) => {
        if (confirmado) {
            fetch(`GestionMuestra?referencia=${referencia}&tipo=${tipo}&origen=${origen}&estado=${estado}&fechaRegistro=${fechaRegistro}&idProyecto=${idProyecto}`, { method: "POST" })
                .then(response => {
                    if (response.ok) {
                        window.history.back();;
                    } else {
                        throw new Error('Error al registrar la muestra, no dispone de los permisos necesarios para realziar esta accion.');
                    }
                })
                .catch(error => {
                    mostrarError(error.message);
                });
        }
    });
}

function crearAnalisis(){
    // Obtener los valores actualizados del formulario
    const observaciones = document.getElementById("observaciones").value;
    const tipo = document.getElementById("tipo").value;
    const fechaAnalisis = document.getElementById("fechaAnalisis").value;
    const estado = document.getElementById("estado").value;
    const idMuestra = document.getElementById("idMuestra").value;
    const idProyecto = document.getElementById("idProyecto").value;

    mostrarConfirmacion(`¿Desea crear el analisis?`).then((confirmado) => {
        if (confirmado) {
            fetch(`GestionAnalisis?observaciones=${observaciones}&tipo=${tipo}&fechaAnalisis=${fechaAnalisis}&estado=${estado}&idMuestra=${idMuestra}&idProyecto=${idProyecto}`, { method: "POST" })
                .then(response => {
                    if (response.ok) {
                        window.history.back();;
                    } else {
                        throw new Error('Error al crear el analisis, no dispone de los permisos necesarios para realziar esta accion.');
                    }
                })
                .catch(error => {
                    mostrarError(error.message);
                });
        }
    });
}

function crearResultado(){
    // Obtener los valores actualizados del formulario
    const parametro = document.getElementById("parametro").value;
    const valor = document.getElementById("valor").value;
    const unidad = document.getElementById("unidad").value;
    const idAnalisis = document.getElementById("idAnalisis").value;

    mostrarConfirmacion(`¿Desea agregar el resultado?`).then((confirmado) => {
        if (confirmado) {
            fetch(`GestionResultado?parametro=${parametro}&valor=${valor}&unidad=${unidad}&idAnalisis=${idAnalisis}`, { method: "POST" })
                .then(response => {
                    if (response.ok) {
                        window.history.back();;
                    } else {
                        throw new Error('Error al guardar el resultado, no dispone de los permisos necesarios para realziar esta accion.');
                    }
                })
                .catch(error => {
                    mostrarError(error.message);
                });
        }
    });
}

function crearTarea(){
    // Obtener los valores actualizados del formulario
    const titulo = document.getElementById("titulo").value;
    const observaciones = document.getElementById("observaciones").value;
    const estado = document.getElementById("estado").value;
    const idUsuario = document.getElementById("idUsuario").value;
    const idProyecto = document.getElementById("idProyecto").value;

    mostrarConfirmacion(`¿Desea agregar la tarea ${titulo} al proyecto ${idProyecto}?`).then((confirmado) => {
        if (confirmado) {
            fetch(`GestionTarea?titulo=${titulo}&observaciones=${observaciones}&estado=${estado}&idProyecto=${idProyecto}&idUsuario=${idUsuario}`, { method: "POST" })
                .then(response => {
                    if (response.ok) {
                        window.history.back();;
                    } else {
                        throw new Error('Error al guardar la tarea, no dispone de los permisos necesarios para realziar esta accion.');
                    }
                })
                .catch(error => {
                    mostrarError(error.message);
                });
        }
    });
}



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
                        throw new Error(`Error al intentar actualizar la muestra ${referencia}, no dispone de los permisos necesarios para realizar esta acción`);
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
    const idUsuario = document.getElementById("idUsuario").value;
    const idProyecto = document.getElementById("idProyecto").value;

    mostrarConfirmacion(`¿Esta seguro de que quiere modificar este analisis?`).then((confirmado) => {
        if (confirmado) {
            fetch(`GestionAnalisis?observaciones=${observaciones}&tipo=${tipo}&fechaAnalisis=${fechaAnalisis}&estado=${estado}&idMuestra=${idMuestra}&idUsuario=${idUsuario}&idProyecto=${idProyecto}&idAnalisis=${idAnalisis}&op=0`, { method: "PUT" })
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
    const idUsuario = document.getElementById("idUsuario").value;
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
                        return listarMuestrasXProyecto();
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
                        return listarAnalisisXMuestra();
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
                        return listarResultadosXAnalisis();
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

// ------------------------------------------------------------------------
//                      FUNCIONES AGREGAR
//-------------------------------------------------------------------------

// Agregar nuevo resultado.
function agregarNuevoResultado() {
    // Obtenemos los valores necesarios desde la tabla.
    const parametro = document.getElementById("nuevoParametro").value;
    const valor = document.getElementById("nuevoValor").value;
    const unidad = document.getElementById("nuevaUnidad").value;
    const idAnalisis = urlParams.get("idAnalisis");
    // Mostramos el mensaje de confirmacion.
    mostrarConfirmacion(`¿Esta seguro de que quiere añadir este resultado?`).then((confirmado) => {
        if (confirmado) {
            fetch(`GestionResultado?parametro=${parametro}&valor=${valor}&unidad=${unidad}&idAnalisis=${idAnalisis}`, { method: "POST" })
                .then(response => {
                    if (response.ok) {
                        listarResultadosXAnalisis();
                    } else {
                        throw new Error(`Error al intentar añadir el nuevo resultado.`);
                    }
                })
                .catch(error => {
                    mostrarError(error.message);
                });
        }
    });
}

function agregarUsuarioProyecto(idUsuario) {
    // Obtenemos el idProyecto
    const idProyecto = urlParams.get("idProyecto")

    // Llamamos al servlet para actualziar el estado.
    fetch(`GestionProyecto?op=1&idUsuario=${idUsuario}&idProyecto=${idProyecto}`, { method: "POST" })
        .then(response => {
            if (response.ok) {
                window.history.back();
            } else {
                throw new Error(`Error al intentar añadir participante al proyecto.`);
            }
        })
        .catch(error => {
            // Si la respuesta es un error lanzamos el aviso.
            mostrarError(error.message);
        })
}

function quitarUsuarioProyecto(idUsuario) {
    // Obtenemos el idProyecto
    const idProyecto = urlParams.get("idProyecto")

    // Llamamos al servlet para actualziar el estado.
    fetch(`GestionProyecto?op=2&idUsuario=${idUsuario}&idProyecto=${idProyecto}`, { method: "POST" })
        .then(response => {
            if (response.ok) {
                listarUsuariosXProyecto();
            } else {
                throw new Error(`Error al intentar quitar participante al proyecto.`);
            }
        })
        .catch(error => {
            // Si la respuesta es un error lanzamos el aviso.
            mostrarError(error.message);
        })
}


// ------------------------------------------------------------------------
//                      FUNCIONES PINTAR TABLAS
//-------------------------------------------------------------------------

// Funcion para pintar Usuarios en una tabla html.
function pintarUsuarios(datos) {
    // Inicializamos la tabla con los títulos de columna.
    let html = "<table>";
    html += `<tr>
                <th></th>
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
                    <td><img src="img/masInfo.png" alt="Ampliar" onclick="window.location.href='vistaUsuario.html?id=${datos[i].id}&op=1'"/></td>
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

// Funcion para pintar UsuariosAgregar en una tabla html.
function pintarUsuariosAgregar(datos) {
    // Inicializamos la tabla con los títulos de columna.
    let html = "<table>";
    html += `<tr>
                <th></th>
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
                    <td><img src="img/anadir.png" alt="Añadir" onclick="agregarUsuarioProyecto(${datos[i].id})"/></td>
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
    document.getElementById("cajaUsuarios").innerHTML = html;
}

// Funcion para pintar UsuariosAgregar en una tabla html.
function pintarUsuariosProyecto(datos) {
    // Inicializamos la tabla con los títulos de columna.
    let html = "<table>";
    html += `<tr>
                <th></th>
                <th>Nombre</th>
                <th>Apellidos</th>
                <th>Rol</th>
                <th>Email</th>
            </tr>`;
    // Iteramos sobre cada elemento de los datos.
    for (let i = 0; i < datos.length; i++) {
        html += `<tr>
                    <td><img src="img/iconoborrar.png" alt="Quitar" onclick="quitarUsuarioProyecto(${datos[i].id})"/></td>
                    <td>${datos[i].nombre}</td>
                    <td>${datos[i].apellidos}</td>
                    <td>${obtenerRol(datos[i].rol)}</td>
                    <td>${datos[i].email}</td>
                </tr>`;
    }
    // Cerramos la tabla.
    html += "</table>";
    // Agregamos el HTML a la caja correspondiente.
    document.getElementById("cajaUsuarios").innerHTML = html;
}

// Funcion para pintar los proyectos en una tabla html.
function pintarProyectos(datos) {
    // Inicializamos la tabla con los títulos de columna.
    let html = "<table>";
    html += `<tr>
                <th></th>
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
                    <td><img src="img/masInfo.png" alt="Ampliar" onclick="window.location.href='vistaProyecto.html?idProyecto=${datos[i].id}&op=1'"/></td>
                    <td>${datos[i].titulo}</td>
                    <td>
                        <select id="estado_${datos[i].id}" onchange="actualizarEstadoProyecto('${datos[i].id}', this.options[this.selectedIndex].value)">
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
                <th></th>
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
                    <td><img src="img/masInfo.png" alt="Ampliar" onclick="window.location.href='vistaTarea.html?idTarea=${datos[i].id}&op=1'"/></td>
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
                <th></th>
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
                    <td><img src="img/masInfo.png" alt="Ampliar" onclick="window.location.href='vistaMuestra.html?idMuestra=${datos[i].id}&op=1'"/></td>
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
    html += `<br>
    <button onclick="window.location.href = 'registrarMuestra.html?idProyecto=${datos[0].idProyecto}'">Agregar Muestra</button>`
    // Agregamos el HTML a la caja correspondiente.
    document.getElementById("cajaMuestra").innerHTML = html;
}

// Funcion para pintar Analisis en una tabla html.
function pintarAnalisis(datos) {
    // Inicializamos la tabla con los títulos de columna.
    let html = "<table>";
    html += `<tr>
                <th></th>
                <th>ID</th>
                <th>Fecha Analisis</th>
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
                    <td><img src="img/masInfo.png" alt="Ampliar" onclick="window.location.href='vistaAnalisis.html?idAnalisis=${datos[i].id}&op=1'"/></td>
                    <td>${datos[i].id}</td>
                    <td>${datos[i].observaciones}</td>
                    <td>${convertirFecha(datos[i].fechaAnalisis)}</td>
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
    html += "</table>"
    html += `<br>
    <button onclick="window.location.href = 'crearAnalisis.html?idProyecto=${datos[0].idProyecto}&idMuestra=${datos[0].idMuestra}'">Agregar Analisis</button>`
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
    // Creamos una variable para almacenar el ID de análisis.
    let idAnalisis = datos[0].idAnalisis;
    // Agregamos una fila en blanco al final para agregar nuevos resultados.
    html += `<tr>
                <td><input type="text" id="nuevoParametro"></td>
                <td><input type="text" id="nuevoValor"></td>
                <td><input type="text" id="nuevaUnidad"></td>
                <td><spam type="text" id="IdAnalisis">${idAnalisis}</td>
                <td></td>
                <td><button onclick="agregarNuevoResultado()">Agregar</button></td>
            </tr>`;
    // Cerramos la tabla.
    html += "</table>";
    // Agregamos el HTML a la caja correspondiente.
    document.getElementById("cajaResultados").innerHTML = html;
}

// ------------------------------------------------------------------------
//                      FUNCIONES PINTAR FICHAS
//-------------------------------------------------------------------------

// Funcion para pintar la ficha de un Usuario.
function pintarFichaUsuario(datos) {
    let html = `<div class="fichaUsuario">
    <label><b>Id de Usuario:</b></label>
    <span id="id">${datos.id}</span>
    <br><br>
    <label><b>Nombre:</b></label>
    <span id="nombre">${datos.nombre}</span>
    <br><br>
    <label><b>Descripción:</b></label>
    <span id="apellidos">${datos.apellidos}</span>
    <br><br>
    <label><b>Rol:</b></label>
    <span id="rol">${obtenerRol(datos.rol)}</span>
    <br><br>
    <label><b>Email:</b></label>
    <span id="email">${datos.email}</span>
    <br><br>
    <input type="button" value="Editar Usuario" onclick="window.location.href='editarUsuario.html?id=${datos.id}&op=0'"/>
    <input type="button" value="Cambiar Contraseña" onclick="window.location.href='cambiarContrasena.html'"/>
</div>`;

    document.getElementById("cajaFicha").innerHTML = html;
}

// Funcion para pintar la ficha de un Proyecto
function pintarFichaProyecto(datos) {
    let html = `<div class="fichaProyecto">
    <label><b>Id Proyecto:</b></label>
    <span id="idProyecto">${datos.id}</span>
    <br><br>
    <label><b>Título:</b></label>
    <span id="titulo">${datos.titulo}</span>
    <br><br>
    <label><b>Descripción:</b></label>
    <span id="descripcion">${datos.descripcion}</span>
    <br><br>
    <label><b>Estado:</b></label>
    <span id="estado">${datos.estado}</span>
    <br><br>
    <label><b>Fecha de Inicio:</b></label>
    <span id="fechaInicio">${convertirFecha(datos.fechaInicio)}</span>
    <br><br>
    <label><b>Fecha de Fin:</b></label>
    <span id="fechaFin">${convertirFecha(datos.fechaFin)}</span>
    <br><br>
    <input type="button" value="Editar Proyecto" onclick="window.location.href='editarProyecto.html?idProyecto=${datos.id}&op=1'"/>
    <button onclick="window.location.href = 'crearTarea.html?idProyecto=${datos.id}'">Agregar Tarea</button>
    <button onclick="window.location.href = 'agregarUsuarios.html?idProyecto=${datos.id}'">Agregar Usuarios</button>
</div>`;

    document.getElementById("cajaFicha").innerHTML = html;
}

// Funcion para pintar la ficha de una Muestra
function pintarFichaMuestra(datos) {
    let html = `<div class="fichaMuestra">
    <label><b>Referencia:</b></label>
    <span id="referencia">${datos.referencia}</span>
    <br><br>
    <label><b>Tipo:</b></label>
    <span id="tipo">${datos.tipo}</span>
    <br><br>
    <label><b>Origen:</b></label>
    <span id="origen">${datos.origen}</span>
    <br><br>
    <label><b>Estado:</b></label>
    <span id="estado">${datos.estado}</span>
    <br><br>
    <label><b>Fecha de registro:</b></label>
    <span id="fechaRegistro">${convertirFecha(datos.fechaRegistro)}</span>
    <br><br>
    <input type="button" value="Editar Muestra" onclick="window.location.href='editarMuestra.html?idMuestra=${datos.id}&op=0'"/>
</div>`;

    document.getElementById("cajaFicha").innerHTML = html;
}

// Funcion para pintar la ficha de un Analisis
function pintarFichaAnalisis(datos) {
    let html = `<div class="fichaAnalisis">
    <label><b>ID:</b></label>
    <span id="idAnalisis">${datos.id}</span>
    <br><br>
    <label><b>Observaciones:</b></label>
    <span id="observaciones">${datos.observaciones}</span>
    <br><br>
    <label><b>Fecha Analisis:</b></label>
    <span id="fechaAnalisis">${convertirFecha(datos.fechaAnalisis)}</span>
    <br><br>
    <label><b>Tipo:</b></label>
    <span id="tipo">${datos.tipo}</span>
    <br><br>
    <label><b>Estado:</b></label>
    <span id="estado">${datos.estado}</span>
    <br><br>
    <label><b>Muestra:</b></label>
    <span id="idMuestra">${datos.idMuestra}</span>
    <br><br>
    <label><b>Usuario:</b></label>
    <span id="idUsuario">${datos.idUsuario}</span>
    <br><br>
    <label><b>Proyecto:</b></label>
    <span id="idProyecto">${datos.idProyecto}</span>
    <br><br>
    <input type="button" value="Editar Analisis" onclick="window.location.href='editarAnalisis.html?idAnalisis=${datos.id}&op=0'"/>
    <input type="button" value="Agregar Resultado" onclick="window.location.href = 'agregarResultado.html?idAnalisis=${datos.id}'"/>
</div>`;

    document.getElementById("cajaFicha").innerHTML = html;
}

// Funcion para pintar la ficha de una Tarea
function pintarFichaTarea(datos) {
    let html = `<div class="fichaTarea">
    <label for="titulo"><b>Titulo:</b></label>
    <span id="titulo">${datos.titulo}</span>
    <br><br>
    <label for="observaciones"><b>Observaciones:</b></label>
    <span id="observaciones">${datos.observaciones}</span>
    <br><br>
    <label for="estado"><b>Estado:</b></label>
    <span id="estado">${datos.estado}</span>
    <br><br>
    <label for="idUsuario"><b>Usuario:</b></label>
    <span id="idUsuario">${datos.idUsuario}</span>
    <br><br>
    <label for="idProyecto"><b>Proyecto:</b></label>
    <span id="idProyecto">${datos.idProyecto}</span>
    <br><br>
    <input type="button" value="Editar Tarea" onclick="window.location.href='editarTarea.html?idTarea=${datos.id}&op=1'"/>
</div>`;

    document.getElementById("cajaFicha").innerHTML = html;
}

// ------------------------------------------------------------------------
//                      FUNCIONES PINTAR FORMULARIOS
//-------------------------------------------------------------------------

// Pintar Formulario Usuario
function pintarFormularioUsuario(datos) {
    let html = `<form id="formEditarUsuario">
    <label for="nombre">Nombre:</label>
    <input type="text" id="nombre" name="nombre" required value="${datos.nombre}"/>
    <label for="apellidos">Apellidos:</label>
    <input type="text" id="apellidos" name="apellidos" required value="${datos.apellidos}"/>
    <label for="rol">Rol:</label>
    <input type="text" id="rol" name="rol" required value="${obtenerRol(datos.rol)}" disabled/>
    <label for="email">Email</label>
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
    <label for="idProyecto"><b>Proyecto:</b></label>
    <input type="text" id="idProyecto" name="idProyecto" required value="${datos.idProyecto}"/>
    <br><br>
</form>
<br/>
<input type="button" value="Guardar muestra" onclick="editarMuestra(${datos.id})"/>`;

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
    <label for="fechaAnalisis"><b>Fecha de Analisis:</b></label>
    <input type="text" id="fechaAnalisis" name="fechaAnalisis" required value="${convertirFecha(datos.fechaAnalisis)}"/>
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
<input type="button" value="Guardar Analisis" onclick="editarAnalisis(${datos.id})"/>`;

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

function listarUsuariosConSesion() {
    fetch("GestionUsuario?op=2")
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar su usuario');
            }
            return response.json();
        })
        .then(data => pintarFichaUsuario(data))
        .catch(error => {
            mostrarError(error.message);
        });
}

function listarUsuario() {
    const idUsuario = urlParams.get("id")
    fetch(`GestionUsuario?op=1&id=${idUsuario}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar su usuario');
            }
            return response.json();
        })
        .then(data => pintarFichaUsuario(data))
        .catch(error => {
            mostrarError(error.message);
        });
}

function listarUsuariosAsignar() {
    fetch("GestionUsuario?op=0")
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar los usuarios');
            }
            return response.json();
        })
        .then(data => pintarUsuariosAgregar(data))
        .catch(error => {
            mostrarError(error.message);
        });
}

function listarProyectosUsuario() {
    fetch("GestionProyecto?op=3")
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar los proyectos');
            }
            return response.json();
        })
        .then(data => pintarProyectos(data))
        .catch(error => {
            mostrarError(error.message);
        });
}

function listarUsuariosXProyecto() {
    idProyecto = urlParams.get("idProyecto")
    fetch(`GestionProyecto?idProyecto=${idProyecto}&op=4`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar los usuarios');
            }
            return response.json();
        })
        .then(data => pintarUsuariosProyecto(data))
        .catch(error => {
            mostrarError(error.message);
        });
}

function listarProyecto() {
    const idProyecto = urlParams.get("idProyecto")
    fetch(`GestionProyecto?idProyecto=${idProyecto}&op=1`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar el proyecto');
            }
            return response.json();
        })
        .then(data => pintarFichaProyecto(data))
        .catch(error => {
            mostrarError(error.message);
        });
}

function listarMuestrasXProyecto() {
    const idProyecto = urlParams.get("idProyecto")
    fetch(`GestionMuestra?idProyecto=${idProyecto}&op=2`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar las muestras');
            }
            return response.json();
        })
        .then(data => pintarMuestra(data))
        .catch(error => {
            mostrarError(error.message);
        });
}

function listarMuestra() {
    const idMuestra = urlParams.get("idMuestra")
    fetch(`GestionMuestra?op=1&idMuestra=${idMuestra}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar la muestra');
            }
            return response.json();
        })
        .then(data => pintarFichaMuestra(data))
        .catch(error => {
            mostrarError(error.message);
        });
}

function listarAnalisisXMuestra() {
    const idMuestra = urlParams.get("idMuestra")
    fetch(`GestionAnalisis?op=3&idMuestra=${idMuestra}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar los analisis de la muestra');
            }
            return response.json();
        })
        .then(data => pintarAnalisis(data))
        .catch(error => {
            mostrarError(error.message);
        });
}

function listarUnAnalisis() {
    const idAnalisis = urlParams.get("idAnalisis")
    fetch(`GestionAnalisis?op=1&idAnalisis=${idAnalisis}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar el analisis');
            }
            return response.json();
        })
        .then(data => pintarFichaAnalisis(data))
        .catch(error => {
            mostrarError(error.message);
        });
}

function listarResultadosXAnalisis() {
    const idAnalisis = urlParams.get("idAnalisis")
    fetch(`GestionResultado?idAnalisis=${idAnalisis}&op=2`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar los analisis de los resultados');
            }
            return response.json();
        })
        .then(data => pintarResultados(data))
        .catch(error => {
            mostrarError(error.message);
        });
}


function listarTareasUsuario() {
    fetch("GestionTarea?op=3")
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar las tareas');
            }
            return response.json();
        })
        .then(data => pintarTareas(data))
        .catch(error => {
            mostrarError(error.message);
        });
}

function listarUnaTarea() {
    const idTarea = urlParams.get("idTarea")
    fetch(`GestionTarea?op=1&idTarea=${idTarea}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar la tarea');
            }
            return response.json();
        })
        .then(data => pintarFichaTarea(data))
        .catch(error => {
            mostrarError(error.message);
        });
}

function listarTareasXProyecto() {
    const idProyecto = urlParams.get("idProyecto")
    fetch(`GestionTarea?op=2&idProyecto=${idProyecto}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar las tareas');
            }
            return response.json();
        })
        .then(data => pintarTareas(data))
        .catch(error => {
            mostrarError(error.message);
        });
}



// ------------------------------------------------------------------------
//                      FUNCIONES LISTAR PARA EDITAR
//-------------------------------------------------------------------------

function editarUsuarioConSesion() {
    fetch(`GestionUsuario?op=2`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar su Usuario');
            }
            return response.json();
        })
        .then(data => pintarFormularioUsuario(data))
        .catch(error => {
            mostrarError(error.message);
        });
}

function editarUnProyecto() {
    const idProyecto = urlParams.get("idProyecto")
    fetch(`GestionProyecto?idProyecto=${idProyecto}&op=1`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar el proyecto');
            }
            return response.json();
        })
        .then(data => pintarFormularioProyecto(data))
        .catch(error => {
            mostrarError(error.message);
        });
}


function editarUnaMuestra() {
    const idMuestra = urlParams.get("idMuestra")
    fetch(`GestionMuestra?op=1&idMuestra=${idMuestra}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar la muestra');
            }
            return response.json();
        })
        .then(data => pintarFormularioMuestra(data))
        .catch(error => {
            mostrarError(error.message);
        });
}

function editarUnAnalisis() {
    const idAnalisis = urlParams.get("idAnalisis")
    fetch(`GestionAnalisis?op=1&idAnalisis=${idAnalisis}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar el analisis');
            }
            return response.json();
        })
        .then(data => pintarFormularioAnalisis(data))
        .catch(error => {
            mostrarError(error.message);
        });
}

function editarUnResultado() {
    const idResultado = urlParams.get("idResultado")
    fetch(`GestionResultado?idResultado=${idResultado}&op=1`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar el resultado');
            }
            return response.json();
        })
        .then(data => pintarFormularioResultado(data))
        .catch(error => {
            mostrarError(error.message);
        });
}

function editarUnaTarea() {
    const idTarea = urlParams.get("idTarea")
    fetch(`GestionTarea?op=1&idTarea=${idTarea}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar la tarea');
            }
            return response.json();
        })
        .then(data => pintarFormularioTarea(data))
        .catch(error => {
            mostrarError(error.message);
        });
}



// ------------------------------------------------------------------------
//                      FUNCIONES LISTAR ADMIN
//-------------------------------------------------------------------------

function listarUsuarios() {
    fetch("GestionUsuario?op=0")
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar los Usuarios');
            }
            return response.json();
        })
        .then(data => pintarUsuarios(data))
        .catch(error => {
            mostrarError(error.message);
        });
}

function listarProyectos() {
    fetch("GestionProyecto?op=0")
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar los proyectos, no posee los permisos necesarios para realizar esta accion.');
            }
            return response.json();
        })
        .then(data => pintarProyectos(data))
        .catch(error => {
            mostrarError(error.message);
        });
}

function listarMuestras() {
    fetch("GestionMuestra?op=0")
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar las Muestras');
            }
            return response.json();
        })
        .then(data => pintarMuestra(data))
        .catch(error => {
            mostrarError(error.message);
        });
}

function listarAnalisis() {
    fetch("GestionAnalisis?op=0")
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar los Analisis');
            }
            return response.json();
        })
        .then(data => pintarAnalisis(data))
        .catch(error => {
            mostrarError(error.message);
        });
}

function listarResultados() {
    fetch("GestionResultado?op=0")
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar los Analisis');
            }
            return response.json();
        })
        .then(data => pintarResultados(data))
        .catch(error => {
            mostrarError(error.message);
        });
}


// ------------------------------------------------------------------------
//                      FUNCIONES INICIAR SESION
//-------------------------------------------------------------------------

function iniciarSesion() {
    const email = document.getElementById("email").value;
    const contrasena = document.getElementById("contrasena").value;
    fetch(`LoginUsuario?email=${email}&contrasena=${contrasena}&op=0`, { method: "POST" })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al iniciar sesión, email o contraseña incorrectos');
            }
            return response.text(); // Obtener la URL de redirección desde el servlet
        })
        .then(redireccion => {
            // Redirigir al usuario a la URL obtenida
            window.location.href = redireccion;
        })
        .catch(error => {
            mostrarError(error.message);
        });
}