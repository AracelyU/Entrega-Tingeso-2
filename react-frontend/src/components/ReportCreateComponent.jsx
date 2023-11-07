import React, {Component} from "react";
import "../style/css/EstiloTitulos.css"
import StudentListComponent from "./StudentListComponent";

class ReportCreateComponent extends Component {


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
