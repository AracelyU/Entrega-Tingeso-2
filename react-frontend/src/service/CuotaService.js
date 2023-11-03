import axios from "axios";

const API_URL_CUOTA = "http://localhost:8080/cuota/";

class CuotaService{

    // crear pago
    createCuota(GenerarCuota){
        return axios.post("http://localhost:8080/cuota/generatepago", GenerarCuota);
    }





}

export default new CuotaService()