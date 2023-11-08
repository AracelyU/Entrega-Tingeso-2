package module.estudiante.service;

import module.estudiante.entity.StudentEntity;
import module.estudiante.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    RestTemplate restTemplate;

    /*
        PENDIENTE
        Crear función que verifique se el estudiante no tiene ingresado un rut repetido

     */

    // obtener todos los estudiantes
    public List<StudentEntity> obtenerEstudiantes() {
        return studentRepository.findAll();
    }


    // guardar un estudiante
    public StudentEntity guardarEstudiante(StudentEntity student) {
        return studentRepository.save(student);
    }


    // obtener estudiante por rut
    public StudentEntity obtenerEstudiantePorRut(String rut){
        return studentRepository.findByRut(rut);
    }


}
