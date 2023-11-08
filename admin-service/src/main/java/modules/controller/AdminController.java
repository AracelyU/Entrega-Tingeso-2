package modules.controller;

import modules.entity.AdminEntity;
import modules.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    // mostrar todos los examenes
    @GetMapping
    public ResponseEntity<List<AdminEntity>> getAll() {
        List<AdminEntity> examenes = adminService.obtenerExamenes();
        if(examenes.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(examenes);
    }

}
