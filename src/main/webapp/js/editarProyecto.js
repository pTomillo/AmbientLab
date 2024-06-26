var urlParams = new URLSearchParams(window.location.search);
var idProyecto = urlParams.get('idProyecto');
var op = urlParams.get('op');

function listarProyecto() {
    fetch("GestionProyecto?idProyecto="+idProyecto+"&op="+op)
        .then(response => response.json())
        .then(data => pintarFormulario(data))
}


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

window.onload = function() {
    listarProyecto();
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
