import axios from "axios";

const API_URL_CUOTA = "http://gateway-service/cuota/";

class CuotaService{

    // crear pago
    createCuota(GenerarCuota){
        return axios.post(API_URL_CUOTA + "generatepago", GenerarCuota);
    }


    // si tienes cuotas pendientes aún tienes un pago pendiente
    verificarCuotas(id){
        return axios.get(API_URL_CUOTA + "bystudent/"+ id);
    }

    // obtener estudiante según su id
    getEstudiante(id){
        return axios.get(API_URL_CUOTA + "student/" + id);
    }






}

export default new CuotaService()