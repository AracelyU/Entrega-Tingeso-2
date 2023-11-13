import axios from "axios";


const API_URL_FILE = "http://gateway-service/examen/";
class UpFileService{

    CargarArchivo(formData){
            return axios.post(API_URL_FILE + "cargar", formData);
    }



}

export default new UpFileService()