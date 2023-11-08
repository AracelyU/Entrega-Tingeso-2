import React, {Component} from "react";
import "../style/css/EstiloTitulos.css"

class ReportCreateComponent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            students: [],
            student_id: "",
            nro_cuotas_pagadas: "",
            nro_cuotas_porpagar: "",
            nro_cuotas_atrasadas: "",
            saldo_pagar: "",
            saldo_pagado: "",
            nro_examenes: "",
            promedio_examenes: "",


        };

        this.handleStudent_idChange = event => {
            this.setState({
                student_id: event.target.value,
            });
        };

    };

    componentDidMount(){
        fetch("http://localhost:8080/student") // obtener los estudiante
            .then((response) => response.json())
            .then((data) => this.setState({ students: data }));



    }


    render(){
        return (
            <div className="container">
                <br></br>
                <a className="botonVolver" href="/"> Volver Al Menú Principal</a>
                <h2>Generar Reporte</h2>
                <hr></hr>





            </div>



        )

    }
}

export default ReportCreateComponent;
