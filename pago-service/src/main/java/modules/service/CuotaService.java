package modules.service;


import modules.entity.CuotaEntity;
import modules.repository.CuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class CuotaService{

    @Autowired
    CuotaRepository cuotaRepository;

    @Autowired
    RestTemplate restTemplate;


    /*  PENDIENTE
    Crear función que verifique que la cuota esta correcta

     */


    // mostrar todas las cuotas registradas
    public List<CuotaEntity> obtenerCuotas(){
        return cuotaRepository.findAll();
    }


    // obtener cuotas por id estudiante
    public List<CuotaEntity> obtenerCuotasDeIdEstudiante(Long id_estudiante) {
        return cuotaRepository.findCuotaEntitiesByEstudiante_id(id_estudiante);
    }

    // guardar cuota
    public CuotaEntity guardarCuota(CuotaEntity cuota){
        return cuotaRepository.save(cuota);
    }





}
