import React, {useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Swal from 'sweetalert2';
import "../style/css/EstiloFormulario.css"
import "../style/css/EstiloHome.css"
import CuotaService from "../service/CuotaService";

function CuotaCreateComponent(){

    const [students, setStudents] = useState([]);

    useEffect(()=>{
        fetch("http://localhost:8080/student")
            .then(response=>response.json())
            .then(data=>setStudents(data.map(({id, nombre_estudiante, apellido_estudiante})=>({id, nombre_estudiante, apellido_estudiante}))))
    },[])


    const initialState = {
        num_cuotas: "",
        tipo_pago: "",
        id_estudiante: "",
    };

    const [input, setInput] = useState(initialState);

    const navigate = useNavigate();
    const navigateHome = () => {
        navigate("/");
    };

    const changeNum_CuotasHandler = event => {
        setInput({...input, num_cuotas: event.target.value});
    };
    const changeTipo_PagoHandler = event => {
        setInput({...input, tipo_pago: event.target.value});
    };
    const changeId_EstudianteHandler = event => {
        setInput({...input, id_estudiante: event.target.value});
    };

    // hacer función que verifique si un estudiante ya tiene un pago asociado

    // hacer funcion que obtenga el tipo de colegio del id estudiante ingresado

    const crearCuotas = (event) => {

        console.log(input.num_cuotas);
        console.log(input.tipo_pago);
        console.log(input.id_estudiante);

        Swal.fire({
            title: "¿Desea registrar el pago?",
            text: "No podra cambiarse en caso de equivocación",
            icon: "question",
            showDenyButton: true,
            confirmButtonText: "Confirmar",
            confirmButtonColor: "rgb(68, 194, 68)",
            denyButtonText: "Cancelar",
            denyButtonColor: "rgb(190, 54, 54)",

        }).then((result) => {

            // Validar los campos de entrada
            if (
                !input.num_cuotas ||
                !input.tipo_pago ||
                !input.id_estudiante
            ) {
                Swal.fire({
                    title: "Error",
                    text: "Todos los campos son obligatorios",
                    icon: "error",
                });
                return; // Salir de la función si falta algún campo obligatorio.
            }

            if (result.isConfirmed) {

                let newGenerarCuota = {
                    num_cuotas: input.num_cuotas,
                    tipo_pago: input.tipo_pago,
                    id_estudiante: input.id_estudiante,
                };

                console.log(newGenerarCuota);
                CuotaService.createCuota(newGenerarCuota);

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
            <h2>Registrar Pago</h2>
            <hr></hr>
            <div className="form">
                <Form >
                    <Form.Group className="mb-3" controlId="num_cuotas" value = {input.num_cuotas} onChange={changeNum_CuotasHandler}>
                        <Form.Label className="agregar">Nro Cuotas:</Form.Label>
                        <Form.Control className="agregar" type="number" name="num_cuotas"/>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="tipo_pago">
                        <Form.Label className="agregar"> Tipo Pago: </Form.Label>
                        <select className="agregar" name="tipo_pago" required value = {input.tipo_pago} onChange={changeTipo_PagoHandler}>
                            <option value="">Tipo Pago</option>
                            <option value="contado">Contado</option>
                            <option value="cuota">Cuotas</option>
                        </select>
                    </Form.Group>

                    <Form.Group className="mb-3" controlId="id_estudiante">
                        <Form.Label className="agregar">Estudiante</Form.Label>
                        <select className="agregar" name="id_estudiante" required value = {input.id_estudiante} onChange={changeId_EstudianteHandler}>
                            <option value={""}>Selecione Estudiante</option>
                            {students.map((student) => (
                                <option key={student.id} value={student.id}>
                                    {student.nombre_estudiante} {student.apellido_estudiante}
                                </option>
                            ))}
                        </select>
                    </Form.Group>


                    <Button className="botonRegistro" onClick={crearCuotas}>Registrar Pago</Button>

                </Form>
            </div>
        </div>
    )

}

export default CuotaCreateComponent;