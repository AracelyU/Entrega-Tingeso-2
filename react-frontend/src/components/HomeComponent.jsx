import React, { Component } from "react";
import "../style/css/EstiloHome.css"
import "../style/css/EstiloBotones.css"
import "../style/css/EstiloTitulos.css"


export default function Home(){

    return (
        <div className = "contenedor body">
            <hr></hr>

            <br></br><br></br>
            <div className="h1">
                <h1>TopEducation Web</h1>
            </div>

            <br></br><br></br>

            <a className="boton" href="/lista-estudiantes"> Listar Estudiantes</a>
            <br></br>
            <a className="boton" href="/crear-estudiante"> Crear Estudiante</a>
            <br></br>
            <a className="boton" href="/crear-pago"> Generar Pago</a>
            <br></br>
            <a className="boton" href="/mostrar-pago"> Mostrar Pago</a>
            <br></br>
            <a className="boton" href="/pagar-pago"> Pagar Pago</a>
            <br></br>
            <a className="boton" href="/cargar-archivo"> Cargar Archivo</a>





        </div>
    );
}

