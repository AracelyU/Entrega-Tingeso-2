import React, { Component } from "react";


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
            <div className="home">
                <h1>Listado de Estudiantes</h1>
                <table className="table">
                    <thead className="thead-dark">
                    <tr>
                        <th>ID</th>
                        <th>RUT</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Fecha Nacimiento</th>
                        <th>Tipo de Escuela</th>
                        <th>Nombre de la Escuela</th>
                        <th>Año de egreso</th>
                        <th>Acciones</th>
                    </tr>
                    </thead>
                    <tbody>

                    {this.state.students.map((student) => (
                        <tr key={student.id}>
                            <td>{student.id}</td>
                            <td>{student.rut}</td>
                            <td>{student.nombre_estudiante}</td>
                            <td>{student.apellido_estudiante}</td>
                            <td>{student.fecha_nacimiento}</td>
                            <td>{student.tipo_escuela}</td>
                            <td>{student.nombre_escuela}</td>
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
