import React, { Component } from "react";
import styled from "styled-components";
import { createGlobalStyle } from 'styled-components'
import { useKeycloak } from '@react-keycloak/web'


export default function Home(){
    const { keycloak } = useKeycloak()

    return (
        <div>
            <h1 className="text-center"> <b>TopEducation Web</b></h1>

            <div class="box-area">

                <div class="single-box">
                    <a href="/lista_estudiantes"></a>

                    <div class="img-text">
                        <span class="header-text"><strong>Lista Estudiantes</strong></span>
                    </div>
                </div>

            </div>

        </div>
    );
}
