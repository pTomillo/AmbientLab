function listarProyectosUsuario() {
    fetch("GestionProyecto?op=3")
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al cargar los proyectos');
            }
            return response.json();
        })
        .then (data => pintarProyectos(data))
        .catch(error => {
            alert(error.message);
        });
}

function listarProyectosUsuarioOG() {
    fetch("GestionProyecto?op=3")
        .then(response => response.json())
        .then(data => pintarProyectos(data))
}

function listarTareasUsuario() {
    fetch("GestionTarea?op=3")
        .then(response => response.json())
        .then(data => pintarTareas(data))
}

function listarUsuarios() {
    fetch("GestionUsuario?op=0")
        .then(response => response.json())
        .then(data => pintarUsuarios(data))
}

function listarMuestras(){
    fetch("GestionMuestra?op=0")
        .then(response =>response.json())
        .then(data => pintarMuestra(data))
}

function listarAnalisis(){
    fetch("GestionAnalisis?op=0")
        .then(response =>response.json())
        .then(data => pintarAnalisis(data))
}

function listarResultados(){
    fetch("GestionResultado?op=0")
        .then(response =>response.json())
        .then(data => pintarResultados(data))
}




function actualizarEstado(idProyecto, estado){
    fetch(`GestionProyecto?op=1&idProyecto=${idProyecto}&estado=${estado}`, {method: "PUT"})
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al actualizar el estado del proyecto, no dispones de los permisos necesarios para realizar esta accion');
            }
        })
        .catch(error => {
            mostrarError(error.message);
        })
        
}

function actualizarEstadoTarea(idTarea, estado){
    fetch(`GestionTarea?op=1&idTarea=${idTarea}&estado=${estado}`, {method: "PUT"})
}

function actualizarEstadoMuestra(idMuestra, estado){
    fetch(`GestionMuestra?op=1&idMuestra=${idMuestra}&estado=${estado}`, {method: "PUT"})
}

function actualizarEstadoAnalisis(idAnalisis, estado){
    fetch(`GestionAnalisis?op=1&idAnalisis=${idAnalisis}&estado=${estado}`, {method: "PUT"})
}

window.onload = function () {
     listarProyectosUsuario()
     listarTareasUsuario()
     listarUsuarios()
     listarMuestras()
     listarAnalisis()
     listarResultados()
}