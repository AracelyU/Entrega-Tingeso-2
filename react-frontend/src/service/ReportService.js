import axios from "axios";

const API_URL_CUOTA = "http://gateway-service/examen/";

class ReportService{

    // obtener reporte del estudiante según su id
    getReporte(id){
        return axios.get(API_URL_CUOTA + "generarReporte/" + id);
    }


}

export default new ReportService()