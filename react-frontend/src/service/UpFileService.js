import axios from "axios";


const API_URL_FILE = "http://localhost:8080/examen/";
class UpFileService{

    CargarArchivo(formData){
            return axios.post(API_URL_FILE + "cargar", formData);
    }



}

export default new UpFileService()