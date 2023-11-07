import React, { Component } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import swal from "sweetalert";
import UpFileService from "../service/UpFileService";
import "../style/css/EstiloCargarArchivo.css";
import "../style/css/EstiloBotones.css";

class UpFileComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
            file: null,
        };
    }

    onFileChange = (event) => {
        const file = event.target.files[0];
        if (!file.name.toLowerCase().includes(".csv")) {
            swal({
                title: "Error",
                text: "Solo se permiten archivos CSV",
                icon: "error",
            });
            return;
        }
        this.setState({ file });
    };

    onFileUpload = () => {
        swal({
            title: "¿Está seguro de que desea cargar el archivo de texto?",
            text: "Tenga en cuenta que el archivo solo será cargado si su extensión es csv y si su formato es correcto.",
            icon: "warning",
            buttons: ["Cancelar", "Cargar"],
            dangerMode: true,
        }).then((respuesta) => {
            if (respuesta) {
                swal("Archivo cargado correctamente!", { icon: "success", timer: "3000" });
                const formData = new FormData();
                formData.append("file", this.state.file);
                UpFileService.CargarArchivo(formData).then((res) => {});
            } else {
                swal({ text: "Archivo no cargado.", icon: "error" });
            }
        });
    };

    render() {
        return (
            <div className="home">
                <br></br>
                <a className="botonVolver" href="/">
                    Volver Al Menú Principal
                </a>
                <h2><b>Cargar el archivo de datos</b></h2>
                <hr></hr>
                <br></br><br></br>

                <div className="container">
                    <Row className="mt-4">
                        <Col col="12">
                            <Form.Group className="mb-3" controlId="formFileLg">
                                <Form.Control type="file" name="file" size="lg" onChange={this.onFileChange} />
                            </Form.Group>
                            <Button variant="primary" className="botonRegistro" onClick={this.onFileUpload}>
                                Cargar el archivo a la Base de Datos</Button>
                        </Col>
                    </Row>
                </div>

                <div className="form1">
                    <h5><b>Recuerde que el nombre del archivo debe ser "Data.csv"!</b></h5>
                </div>

                <hr></hr>

                <Button className="botonVolver"> Aplicar Puntaje</Button>

            </div>
        );
    }
}

export default UpFileComponent;