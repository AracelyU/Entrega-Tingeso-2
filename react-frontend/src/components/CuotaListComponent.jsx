import React, {Component, useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import Swal from 'sweetalert2';
import "../style/css/EstiloFormulario.css"
import "../style/css/EstiloHome.css"
import CuotaService from "../service/CuotaService";
import "../style/css/EstiloTabla.css"
import * as students from "react-bootstrap/ElementChildren";
import axios from "axios";

class CuotaListComponent extends Component {

    constructor(props) {
        super(props);

        this.state = {
            id_estudiante: "",
            cuotas: [],
            students: [],
        };

        this.changeId_EstudianteHandler = event => {
            this.setState({ id_estudiante: event.target.value });
        };

        this.handleSubmit = event => {
            event.preventDefault();
            const { id_estudiante } = this.state;
            // Obtenemos las cuotas del estudiante
            axios.get(`http://localhost:8080/cuota/bystudent/${id_estudiante}`)
                .then((response) => {
                    this.setState({ cuotas: response.data });
                });
        };


    }

    componentDidMount() {
        fetch("http://localhost:8080/student")
            .then((response) => response.json())
            .then((data) => this.setState({ students: data }));

    }


    render() {

        const { cuotas } = this.state;
        const { students } = this.state;


        if(students.length === 0){
            return(
                <div>
                    <br></br>
                    <a className="botonVolver" href="/"> Volver Al Menú Principal</a>
                    <h2>Mostrar Cuotas</h2>
                    <hr></hr>
                    <h2>No hay Alumnos registrados</h2>
                </div>
            )
        }

        if(cuotas.length === 0){
            return(
                <div>
                    <br></br>
                    <a className="botonVolver" href="/"> Volver Al Menú Principal</a>
                    <h2>Mostrar Cuotas</h2>
                    <hr></hr>

                    <div style={{ display: "flex", justifyContent: "space-between" }}>
                        <Form.Group className="mb-3" controlId="id_estudiante">
                            <Form.Label className="agregar">Estudiante</Form.Label>
                            <select className="agregar form-select" name="id_estudiante" required value = {this.state.id_estudiante} onChange={this.changeId_EstudianteHandler}>
                                <option value={""} disabled>Selecione Estudiante</option>
                                {students.map((student, index) => (
                                    <option key={index} value={student.id}>
                                        {student.nombre_estudiante} {student.apellido_estudiante}
                                    </option>
                                ))}
                            </select>
                            <i></i>
                        </Form.Group>
                        <button type="button" className="botonVolver" onClick={this.handleSubmit}>
                            Consultar Cuotas
                        </button>
                    </div>

                    <hr></hr>
                    <h2>El Alumno no tiene un pago registrado</h2>

                </div>
            )
        }

        return (
            <div>
                <br></br>
                <a className="botonVolver" href="/"> Volver Al Menú Principal</a>
                <h2>Mostrar Cuotas</h2>
                <hr></hr>
                <div style={{ display: "flex", justifyContent: "space-between" }}>
                    <Form.Group className="mb-3" controlId="id_estudiante">
                        <Form.Label className="agregar">Estudiante</Form.Label>
                        <select className="agregar form-select" name="id_estudiante" required value = {this.state.id_estudiante} onChange={this.changeId_EstudianteHandler}>
                            <option value={""} disabled>Selecione Estudiante</option>
                            {students.map((student, index) => (
                                <option key={index} value={student.id}>
                                    {student.nombre_estudiante} {student.apellido_estudiante}
                                </option>
                            ))}
                        </select>
                        <i></i>
                    </Form.Group>
                    <button type="button" className="botonVolver" onClick={this.handleSubmit}>
                        Consultar Cuotas
                    </button>
                </div>


                <hr></hr>
                <table className="table">
                    <thead>
                    <tr>
                        <th>Nro Cuota</th>
                        <th>Monto</th>
                        <th>Tipo</th>
                        <th>Fecha Vencimiento</th>
                        <th>Fecha Pago</th>
                        <th>Estado Pago</th>
                    </tr>
                    </thead>
                    <tbody>

                            {this.state.cuotas.map((cuota, index) => (
                                <tr key={index}>
                                    <td>{cuota.num_cuota}</td>
                                    <td>{cuota.monto}</td>
                                    <td>{cuota.tipo_pago}</td>
                                    <td>{cuota.fecha_vencimiento}</td>
                                    <td>{cuota.fecha_pago}</td>
                                    <td>{cuota.estado_pago}</td>
                                </tr>

                            ))}
                    </tbody>
                </table>
            </div>
        );
    }
}

export default CuotaListComponent;