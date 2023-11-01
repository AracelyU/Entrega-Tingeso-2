import axios from "axios";

const API_URL_STUDENT = "http://localhost:8080/student/";

class StudentService{

    // crear un estudiante
    createEstudiante(estudiante){
        return axios.post(API_URL_STUDENT, estudiante);
    }


    verificarRut(rut){
        return axios.post(API_URL_STUDENT, rut);
    }



}

export default new StudentService()