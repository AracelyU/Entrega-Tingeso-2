import React from 'react'
import { useNavigate } from 'react-router-dom';

function HeaderComponent() {
    const navigate = useNavigate();
    const handleClick = () => {
        navigate("/");
    }
    return (
        <div>


            <header className="header">
                <div className="logo">
                    <h1 onClick={handleClick}>TopEducation</h1>
                </div>
                <nav></nav>
                <a className="btn" href="/crear_estudiante"><button>Ingresar Estudiante</button></a>



            </header>
        </div>
    )
}
export default HeaderComponent