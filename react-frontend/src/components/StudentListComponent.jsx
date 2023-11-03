import React, { Component } from "react";
import "../style/css/EstiloTabla.css"
import "../style/css/EstiloHome.css"


class StudentListComponent extends Component{

    constructor(props){
        super(props);
        this.state = {
            students: [],
        };
    }

    componentDidMount(){
        fetch("http://localhost:8080/student")
            .then((response) => response.json())
            .then((data) => this.setState({ students: data }));
    }

    render(){
        return(
            <div className="main-container">
                <a className="botonVolver" href="/"> Volver Al Menú Principal</a>
                <h2>Listado de Estudiantes</h2>
                <hr></hr>
                <table className="table">
                    <thead>
                    <tr>
                        <th>RUT</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Fecha Nacimiento</th>
                        <th>Tipo de Escuela</th>
                        <th>Nombre de la Escuela</th>
                        <th>Año de egreso</th>
                    </tr>
                    </thead>
                    <tbody>

                    {this.state.students.map((student) => (
                        <tr key={student.id}>
                            <td>{student.rut}</td>
                            <td>{student.nombre_estudiante}</td>
                            <td>{student.apellido_estudiante}</td>
                            <td>{student.fecha_nacimiento}</td>
                            <td>{student.tipo_colegio}</td>
                            <td>{student.nombre_colegio}</td>
                            <td>{student.anio_egreso}</td>
                        </tr>
                    ))}

                    </tbody>
                </table>

            </div>
        )
    }
}

export default StudentListComponent;
