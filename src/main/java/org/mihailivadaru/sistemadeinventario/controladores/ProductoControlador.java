package org.mihailivadaru.sistemadeinventario.controladores;


import org.mihailivadaru.sistemadeinventario.entidades.Producto;
import org.mihailivadaru.sistemadeinventario.servicios.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoControlador {

    @Autowired
    private ProductoServicio productoServicio;

    @GetMapping
    public List<Producto> listar() {
        return productoServicio.listar();
    }

    @GetMapping("/{id}")
    public Producto buscar(@PathVariable Long id) {
        return productoServicio.buscar(Math.toIntExact(id));
    }

    @PostMapping
    public Producto guardarProducto(@RequestBody Producto producto) {
        return productoServicio.guardar(producto);
    }

    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable Long id, @RequestBody Producto detallesProducto) {
        Optional<Producto> producto = Optional.ofNullable(productoServicio.buscar(Math.toIntExact(id)));
        if (producto.isPresent()) {
            producto.get().setNombre(detallesProducto.getNombre());
            producto.get().setDescripcion(detallesProducto.getDescripcion());
            producto.get().setPrecio(detallesProducto.getPrecio());
            producto.get().setStock(detallesProducto.getStock());
            return productoServicio.guardar(producto.get());
        }
        return null;
    }

    @GetMapping("/low-stock")
    public List<Producto> lowStock(@RequestParam int umbral) {
        return productoServicio.findLowStockProducts(umbral);
    }

    // EndPoint para descargar reporte
    @GetMapping("/reportePDF")
    public String generarReportePDF() throws Exception {
        productoServicio.generarReporteInventorio();
        return "Reporte PDF generado con exito!";
    }

}
