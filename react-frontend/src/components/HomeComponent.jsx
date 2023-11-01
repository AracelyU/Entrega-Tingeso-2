import React, { Component } from "react";


export default function Home(){

    return (
        <div>
            <h1 className="text-center"> <b>TopEducation Web</b></h1>

            <div className="box-area">

                <div class="single-box">
                    <a href="/lista_estudiantes">
                        <div className="img-text">
                            <span className="header-text"><strong>Listar Estudiantes</strong></span>
                        </div>
                    </a>
                </div>

                <div className="single-box">
                    <a href="/crear_estudiantes">
                        <div className="img-text">
                            <span className="header-text"><strong>Agregar Estudiante</strong></span>
                        </div>
                    </a>
                </div>




            </div>

        </div>
    );
}

