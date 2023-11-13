import axios from "axios";

const API_URL_STUDENT = "http://gateway-service/student/";

class StudentService{

    // crear un estudiante
    createEstudiante(estudiante){
        return axios.post(API_URL_STUDENT, estudiante);
    }

    verificarRut(rut){
        return axios.get(API_URL_STUDENT + "rut/"+ rut);
    }



}

export default new StudentService()