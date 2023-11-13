import React, { Component } from 'react';
import UpFileService from '../service/UpFileService';
import "../style/css/EstiloBotones.css"
import axios from "axios";
import Swal from "sweetalert2";

async function updateDescuento() {
    const response = await axios.put(`http://localhost:8080/examen/descuentoPromedio`, {
    });

    return response.data;
}


class UpFileComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            file: null,
            message: '',

        };
    }


    handleFileChange = (event) => {
        this.setState({
            file: event.target.files[0],
        });
    };

    handleUpload = () => {
        if (!this.state.file) {
            this.setState({ message: 'Selecciona un archivo antes de subirlo.' });
            return;
        }

        const formData = new FormData();
        formData.append('file', this.state.file);

        UpFileService.CargarArchivo(formData)
            .then((response) => {
                this.setState({ message: response.data });
            })
            .catch((error) => {
                console.error('Error uploading Excel:', error);
                this.setState({ message: 'Error al cargar el archivo csv.' });
            });
    };

    render() {

        const aplicarDescuentoPromedio= (event) => {
            Swal.fire({
                title: "¿Desea aplicar el descuento?",
                text: "No podra cambiarse en caso de equivocación",
                icon: "question",
                showDenyButton: true,
                confirmButtonText: "Confirmar",
                confirmButtonColor: "rgb(68, 194, 68)",
                denyButtonText: "Cancelar",
                denyButtonColor: "rgb(190, 54, 54)",

            }).then((result) => {

                updateDescuento().then(r => "")
                Swal.fire({
                    title: "Enviado",
                    timer: 2000,
                    icon: "success",
                    timerProgressBar: true,
                    didOpen: () => {
                        Swal.showLoading()
                    },
                })

            });
        }


        return (
            <div>
                <br></br>
                <a className="botonVolver" href="/"> Volver Al Menú Principal</a>
                <h2 className="titulo text-center">Cargar Archivo Excel</h2>
                <hr></hr>
                <div className='container_subida'>
                    <div className="form-group">
                        <h2 className='entrada titulo'>Selecciona un archivo .csv con formato ';'</h2>
                        <br></br>
                        <br></br>
                        <input
                            className="centrar"
                            type="file"
                            onChange={this.handleFileChange}
                            accept=".csv"
                        />
                    </div>
                    <br></br>
                    <button className="btn ml-2 main-button2 centrarClick" onClick={this.handleUpload}>
                        Subir Excel
                    </button>
                    {this.state.message && <p className="text-success">{this.state.message}</p>}
                    <br></br>
                    <h2>Solo se puede aplicar descuentos mientras no se día de pagos</h2>
                    <button className="btn ml-2 main-button2 centrarClick" onClick={() => aplicarDescuentoPromedio()}>
                        Aplicar Descuento
                    </button>
                </div>
            </div>
        );
    }
}

export default UpFileComponent;