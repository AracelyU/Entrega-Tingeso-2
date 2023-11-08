package modules.service;

import modules.entity.AdminEntity;
import modules.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    RestTemplate restTemplate;


    // obtener todos los examenes
    public List<AdminEntity> obtenerExamenes(){
        return adminRepository.findAll();
    }

    // guardar un examen
    public AdminEntity guardarExamen(AdminEntity examen){
        return adminRepository.save(examen);
    }



}
