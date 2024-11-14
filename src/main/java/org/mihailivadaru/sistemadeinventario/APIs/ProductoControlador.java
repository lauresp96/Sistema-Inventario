package org.mihailivadaru.sistemadeinventario.APIs;


import org.mihailivadaru.sistemadeinventario.entidades.Producto;
import org.mihailivadaru.sistemadeinventario.servicios.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Producto> buscar(@PathVariable Long id) {
        Producto producto = productoServicio.buscar(Math.toIntExact(id));
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Producto> guardarProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoServicio.guardar(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto detallesProducto) {
        Optional<Producto> productoOptional = Optional.ofNullable(productoServicio.buscar(Math.toIntExact(id)));
        if (productoOptional.isPresent()) {
            Producto producto = productoOptional.get();
            producto.setNombre(detallesProducto.getNombre());
            producto.setDescripcion(detallesProducto.getDescripcion());
            producto.setPrecio(detallesProducto.getPrecio());
            producto.setStock(detallesProducto.getStock());
            productoServicio.guardar(producto);
            return ResponseEntity.ok(producto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 5. Eliminar un producto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (productoServicio.buscar(Math.toIntExact(id)) != null) {
            productoServicio.eliminar(Math.toIntExact(id));
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 6. Obtener productos con stock bajo
    @GetMapping("/low-stock")
    public List<Producto> lowStock(@RequestParam int umbral) {
        return productoServicio.findLowStockProducts(umbral);
    }

    // 7. Generar un reporte de inventario en PDF
    @GetMapping("/reportePDF")
    public ResponseEntity<String> generarReportePDF() {
        try {
            productoServicio.generarReporteInventorio();
            return ResponseEntity.ok("Reporte PDF generado con éxito!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al generar el reporte");
        }
    }
}

