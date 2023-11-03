import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import HeaderComponent from "../Headers/HeaderComponent";
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Swal from 'sweetalert2';
import StudentService from "../service/StudentService";
import studentService from "../service/StudentService";
import "../style/css/EstiloFormulario.css"
import "../style/css/EstiloHome.css"

function StudentCreateComponent(props) {

    const initialState = {
        rut: "",
        nombre_estudiante: "",
        apellido_estudiante: "",
        fecha_nacimiento: "",
        tipo_colegio: "",
        nombre_colegio: "",
        anio_egreso: "",
    };

    const [input, setInput] = useState(initialState);

    const navigate = useNavigate();
    const navigateHome = () => {
        navigate("/");
    };

    const changeRutHandler = event => {
        setInput({...input, rut: event.target.value});
    };
    const changeNombreHandler = event => {
        setInput({...input, nombre_estudiante: event.target.value});
    };
    const changeApellidoHandler = event => {
        setInput({...input, apellido_estudiante: event.target.value});
    };
    const changeFechaNacimientoHandler = event => {
        setInput({...input, fecha_nacimiento: event.target.value});
    };
    const changeAnioEgresoIDHandler = event => {
        setInput({...input, anio_egreso: event.target.value});
    };
    const changeTipoColegioHandler = event => {
        setInput({...input, tipo_colegio: event.target.value});
    };
    const changeNombreColegioHandler = event => {
        setInput({...input, nombre_colegio: event.target.value});
    };

    // 1. En el servicio (StudentService), crea una función para verificar la existencia de un Rut.
    const checkRutExistence = (rut) => {
        if(studentService.verificarRut(rut) == null){ // si no hay contenido el rut no es repetido
            return false;
        }
        return true;
    };

    const ingresarEstudiante = (event) => {

        console.log(input.rut);
        console.log(input.nombre_estudiante);
        console.log(input.apellido_estudiante);
        console.log(input.fecha_nacimiento);
        console.log(input.tipo_colegio);
        console.log(input.nombre_colegio);
        console.log(input.anio_egreso);

        Swal.fire({
            title: "¿Desea registrar el estudiante?",
            text: "No podra cambiarse en caso de equivocación",
            icon: "question",
            showDenyButton: true,
            confirmButtonText: "Confirmar",
            confirmButtonColor: "rgb(68, 194, 68)",
            denyButtonText: "Cancelar",
            denyButtonColor: "rgb(190, 54, 54)",

        }).then((result) => {

            // Validar el tipo de dato para fecha_nacimiento
            if (typeof input.fecha_nacimiento !== "string" || isNaN(Date.parse(input.fecha_nacimiento))) {
                Swal.fire({
                    title: "Error",
                    text: "Fecha de nacimiento no es válida",
                    icon: "error",
                });
                return;
            }

            // Validar el tipo de dato para anio_egreso no sea negativo
            if (input.anio_egreso < 0) {
                Swal.fire({
                    title: "Error",
                    text: "El año debe de ser positivo",
                    icon: "error",
                });
                return;
            }

            /*
            // Verifica si el Rut ya existe antes de agregar el estudiante.
            if (checkRutExistence(input.rut)) {
                Swal.fire({
                    title: "Error",
                    text: "El Rut ya está registrado",
                    icon: "error",
                });
                return;
            }

             */

            // Validar los campos de entrada
            if (
                !input.rut ||
                !input.nombre_estudiante ||
                !input.apellido_estudiante ||
                !input.fecha_nacimiento ||
                !input.tipo_colegio ||
                !input.nombre_colegio ||
                !input.anio_egreso
            ) {
                Swal.fire({
                    title: "Error",
                    text: "Todos los campos son obligatorios",
                    icon: "error",
                });
                return; // Salir de la función si falta algún campo obligatorio.
            }

            if (result.isConfirmed) {

                // Se forma al estudiante
                let newEstudiante = {
                    rut: input.rut,
                    nombre_estudiante: input.nombre_estudiante,
                    apellido_estudiante: input.apellido_estudiante,
                    fecha_nacimiento: input.fecha_nacimiento,
                    tipo_colegio: input.tipo_colegio,
                    nombre_colegio: input.nombre_colegio,
                    anio_egreso: input.anio_egreso,
                    pago: 0,
                    tipo_pago: "",
                    matricula: 70000,

                };

                console.log(newEstudiante);
                StudentService.createEstudiante(newEstudiante);

                Swal.fire({
                    title: "Enviado",
                    timer: 2000,
                    icon: "success",
                    timerProgressBar: true,
                    didOpen: () => {
                        Swal.showLoading()
                    },
                })

                navigateHome();
            }
        });
    };

    return(
        <div className="general">
            <br></br>
            <a className="botonVolver" href="/"> Volver Al Menú Principal</a>
            <h2>Registro Estudiante</h2>
            <hr></hr>

            <div className="form">
                <Form >
                    <Form.Group className="mb-3" controlId="rut" value = {input.rut} onChange={changeRutHandler}>
                        <Form.Label className="agregar">Rut:</Form.Label>
                        <Form.Control className="agregar" type="text" name="rut"/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="nombre_estudiante" value = {input.nombre_estudiante} onChange={changeNombreHandler}>
                        <Form.Label className="agregar">Nombre:</Form.Label>
                        <Form.Control className="agregar" type="text" name="nombre_estudiante"/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="apellido_estudiante" value = {input.apellido_estudiante} onChange={changeApellidoHandler}>
                        <Form.Label className="agregar">Apellido:</Form.Label>
                        <Form.Control className="agregar" type="text" name="apellido_estudiante"/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="fecha_nacimiento" value = {input.fecha_nacimiento} onChange={changeFechaNacimientoHandler}>
                        <Form.Label className="agregar">Fecha de Nacimiento:</Form.Label>
                        <Form.Control className="agregar" type="date" name="fecha_nacimiento"/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="anio_egreso" value = {input.anio_egreso} onChange={changeAnioEgresoIDHandler}>
                        <Form.Label className="agregar">Año Egreso Del Colegio:</Form.Label>
                        <Form.Control className="agregar" type="number" name="anio_egreso"/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="tipo_colegio">
                        <Form.Label className="agregar"> Tipo Colegio: </Form.Label>
                        <select className="agregar" name="tipo_colegio" required value = {input.tipo_colegio} onChange={changeTipoColegioHandler}>
                            <option value="">Tipo Colegio</option>
                            <option value="municipal">Municipal</option>
                            <option value="subvencionado">Subvencionado</option>
                            <option value="privado">Privado</option>
                        </select>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="nombre_colegio" value = {input.nombre_colegio} onChange={changeNombreColegioHandler}>
                        <Form.Label className="agregar">Nombre Colegio:</Form.Label>
                        <Form.Control className="agregar" type="text" name="nombre_colegio"/>
                    </Form.Group>

                    <Button className="botonRegistro" onClick={ingresarEstudiante}>Registrar Estudiante</Button>

                </Form>
            </div>
        </div>
    )

}

export default StudentCreateComponent;