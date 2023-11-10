import React, {useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Swal from 'sweetalert2';
import "../style/css/EstiloFormulario.css"
import "../style/css/EstiloHome.css"
import CuotaService from "../service/CuotaService";
import studentService from "../service/StudentService";
import cuotaService from "../service/CuotaService";
import axios from "axios";
import reportService from "../service/ReportService";

function ReportCreateComponent(){

    const navigate = useNavigate();
    const navigateHome = () => {
        navigate("/");
    };

    const [students, setStudents] = useState([]);

    const initialState = {
        id_estudiante: "",
        rut: "",
        nombre: "",
        apellido: "",
        nro_examenes:"",
        promedio: "",
        monto_total: "",
        tipo_pago:"",
        nro_cuotas: "",
        nro_cuotas_pagadas:"",
        pagado:"",  // el monto pagado
        ultimo_pago:"",  // la fecha
        por_pagar:"", // el monto por pagar
    };

    const initialStateReporte = {
            rut: "",
            nombre: "",
            apellido: "",
            nro_examenes:"",
            promedio: "",
            monto_total: "",
            tipo_pago:"",
            nro_cuotas: "",
            nro_cuotas_pagadas:"",
            pagado:"",  // el monto pagado
            ultimo_pago:"",  // la fecha
            por_pagar:"", // el monto por pagar
    };

    const [input, setInput] = useState(initialState);
    const [reporte, setReporte] = useState(initialStateReporte);


    // obtenermos los estudiantes
    useEffect(()=>{
        fetch("http://localhost:8080/student")
            .then(response=>response.json())
            .then(data=>setStudents(data.map(({id, nombre_estudiante, apellido_estudiante})=>({id, nombre_estudiante, apellido_estudiante}))))
    },[])


    const changeId_EstudianteHandler = event => {
        setInput({...input, id_estudiante: event.target.value});
    };


    const crearReporte = (event) => {

        // Obtener el id del estudiante seleccionado
        const student_id = input.id_estudiante;

        axios.get(`http://localhost:8080/examen/generarReporte/${student_id}`)
            .then((response) => {
                // Asigna el objeto reporte al estado reporte
                setReporte(response.data);
            })
            .catch((error) => {
                console.log(error);
            });

        input.rut = reporte.rut;
        input.nombre = reporte.nombre;
        input.apellido = reporte.apellido;
        input.nro_examenes = reporte.nro_examenes;
        input.promedio = reporte.promedio;
        input.monto_total = reporte.monto_total;
        input.tipo_pago = reporte.tipo_pago;
        input.nro_cuotas = reporte.nro_cuotas;
        input.nro_cuotas_pagadas = reporte.nro_cuotas_pagadas;
        input.pagado = reporte.pagado;
        input.ultimo_pago = reporte.ultimo_pago;
        input.por_pagar = reporte.por_pagar;
        input.nro_cuotas_atraso = reporte.nro_cuotas_atraso;

        console.log(input.id_estudiante);
        console.log(input.rut);
        console.log(input.nombre);
        console.log(input.apellido)
        console.log(input.nro_examenes);
        console.log(input.promedio);
        console.log(input.monto_total);
        console.log(input.tipo_pago);
        console.log(input.nro_cuotas);
        console.log(input.nro_cuotas_pagadas);
        console.log(input.pagado);
        console.log(input.ultimo_pago);
        console.log(input.por_pagar);
        console.log(input.nro_cuotas_atraso);

    };




    return(
        <div className="general">
            <br></br>
            <a className="botonVolver" href="/"> Volver Al Menú Principal</a>
            <h2>Registrar Pago</h2>
            <hr></hr>
            <div className="form">
                <Form >
                    <div style={{ display: "flex", justifyContent: "space-between" }}>
                        <Form.Group className="mb-3" controlId="id_estudiante">
                            <Form.Label className="agregar">Estudiante</Form.Label>
                            <select className="agregar" name="id_estudiante" required value = {input.id_estudiante} onChange={changeId_EstudianteHandler}>
                                <option value={""} disabled>Selecione Estudiante</option>
                                {students.map((student) => (
                                    <option key={student.id} value={student.id}>
                                        {student.nombre_estudiante} {student.apellido_estudiante}
                                    </option>
                                ))}
                            </select>
                        </Form.Group>
                        <Button className="botonRegistro" onClick={crearReporte}>Generar Reporte</Button>
                    </div>
                </Form>


                <h2>Información del estudiante</h2>

                <p>Rut: {input.rut}</p>
                <p>Nombre: {input.nombre}</p>
                <p>Apellido: {input.apellido}</p>
                <p>Número de exámenes rendidos: {input.nro_examenes}</p>
                <p>Promedio: {input.promedio}</p>

                <h2>Información de las cuotas</h2>

                <p>Monto total: {input.monto_total}</p>
                <p>Número de cuotas: {input.nro_cuotas}</p>
                <p>Número de cuotas pagadas: {input.nro_cuotas_pagadas}</p>
                <p>Monto pagado: {input.pagado}</p>
                <p>Monto por pagar: {input.por_pagar}</p>

                <hr></hr>

            </div>
            <br></br>
        </div>
    )

}

export default ReportCreateComponent;