import React, { Component } from "react";
import "../style/css/EstiloHome.css"


export default function Home(){

    return (
        <div className = "contenedor body">
            <hr></hr>

            <br></br><br></br>
            <div className="h1">
                <h1>TopEducation Web</h1>
            </div>

            <br></br><br></br>

            <a className="boton" href="/lista_estudiantes"> Listar Estudiantes</a>
            <br></br>
            <a className="boton" href="/crear_estudiantes"> Crear Estudiante</a>
            <br></br>
            <a className="boton" href="/subir_archivo"> Cargar Archivo</a>




        </div>
    );
}

