package com.carlousuga.prueba_crud.controller;

import com.carlousuga.prueba_crud.dto.Mensaje;
import com.carlousuga.prueba_crud.dto.ProductoDto;
import com.carlousuga.prueba_crud.entity.Producto;
import com.carlousuga.prueba_crud.service.ProductoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @GetMapping("/lista")
    public ResponseEntity<List<Producto>> list(){
        List<Producto> list = productoService.list();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<List<Producto>> getById(@PathVariable("id") int id){
        if (!productoService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Producto producto = productoService.getOne(id).get();
            return new ResponseEntity(producto, HttpStatus.OK);
    }

    @GetMapping("/detail/{nombre}")
    public ResponseEntity<List<Producto>> getByNomre(@PathVariable("nombre") String nombre){
        if (!productoService.existsByNombre(nombre))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Producto producto = productoService.getByNombre(nombre).get();
        return new ResponseEntity(producto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ProductoDto productoDto){
        if(StringUtils.isBlank(productoDto.getNombre()))
            return new ResponseEntity(new Mensaje("El nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(productoDto.getPrecio() == null || productoDto.getPrecio()<0)
            return new ResponseEntity(new Mensaje("EL precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);
        if(productoService.existsByNombre(productoDto.getNombre()))
            return new ResponseEntity(new Mensaje("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        Producto producto = new Producto(productoDto.getNombre(), productoDto.getPrecio());
        productoService.save(producto);
        return new ResponseEntity(new Mensaje("Producto Creado"), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody ProductoDto productoDto){
        if (!productoService.existsById(id))
            return new ResponseEntity(new Mensaje("No existe"), HttpStatus.NOT_FOUND);
        if (productoService.existsByNombre(productoDto.getNombre()) && productoService.getByNombre(productoDto.getNombre()).get().getId() != id)
            return new ResponseEntity(new Mensaje("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if (StringUtils.isBlank(productoDto.getNombre()))
            return new ResponseEntity(new Mensaje("El nombre debe ser obligatorio"), HttpStatus.BAD_REQUEST);
        if (productoDto.getPrecio()<0)
            return new ResponseEntity<>(new Mensaje("EL precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);
        Producto producto = productoService.getOne(id).get();
        producto.setNombre(productoDto.getNombre());
        producto.setPrecio(productoDto.getPrecio());
        productoService.save(producto);
        return new ResponseEntity(new Mensaje("Producto Actualizado"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id){
        if (!productoService.existsById(id))
            return new ResponseEntity(new Mensaje("No Existe"), HttpStatus.NOT_FOUND);
        productoService.delete(id);
        return new ResponseEntity(new Mensaje("Producto eliminado"), HttpStatus.OK);
    }
}
