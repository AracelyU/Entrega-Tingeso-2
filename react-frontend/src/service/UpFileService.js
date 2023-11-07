import axios from "axios";



const API_URL_FILE = "http://localhost:8080/examen/";
class UpFileService{

    CargarArchivo(file){
        const formData = new FormData();
        formData.append("file", file);
        return axios.post(API_URL_FILE, formData);
    }


    CargarArchivo2(file) {
        const config = {
            headers: {
                "Content-Type": "multipart/form-data",
            },
        };
        const formData = new FormData();
        formData.append("file", file);
        return axios.post(API_URL_FILE, formData, config);
    }


}

export default new UpFileService()