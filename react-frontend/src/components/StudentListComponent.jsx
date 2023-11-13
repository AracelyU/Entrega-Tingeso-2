import React, { Component } from "react";
import "../style/css/EstiloTabla.css"
import "../style/css/EstiloBotones.css"


class StudentListComponent extends Component{

    constructor(props){
        super(props);
        this.state = {
            students: [],
            student_id: "",
        };

        this.handleStudent_idChange = event => {
            this.setState({
                student_id: event.target.value,
            });
        };
    }





    componentDidMount(){
        fetch("http://gateway-service/student")
            .then((response) => response.json())
            .then((data) => this.setState({ students: data }));
    }

    render(){

        const { students } = this.state;

        if(students.length === 0){
            return(
                <div>
                    <a className="botonVolver" href="/"> Volver Al Menú Principal</a>
                    <h2>Listado de Estudiantes</h2>
                    <hr></hr>
                    <h2>No hay Estudiantes Registrados</h2>

                </div>
            )
        }

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
                        <th>Acciones</th>
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
