import axios from "axios";


//const API_URL = "http://localhost:8080/marcas-reloj/";

const API_URL_FILE = "http://localhost:8080/examen/";
class UpFileService{

    CargarArchivo(file){
        return axios.post(API_URL_FILE, file);
    }
}

export default new UpFileService()