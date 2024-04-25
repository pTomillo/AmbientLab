function listarProyectosUsuario() {
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




function borrarProyecto(idProyecto) {
    if(confirm("Press a button!\nEither OK or Cancel.")) {
        fetch(`GestionProyecto?idProyecto=${idProyecto}`, {method: "DELETE"})
        .then(response => listarProyectosUsuario())
      } 
}

function borrarTarea(idTarea) {
    if(confirm("Press a button!\nEither OK or Cancel.")) {
        fetch(`GestionTarea?idTarea=${idTarea}`, {method: "DELETE"})
        .then(response => listarTareasUsuario())
      } 
}

function actualizarEstado(idProyecto, estado){
    fetch(`GestionProyecto?op=1&idProyecto=${idProyecto}&estado=${estado}`, {method: "PUT"})
}

window.onload = function () {
     listarProyectosUsuario()
     listarTareasUsuario()
     listarUsuarios()
}