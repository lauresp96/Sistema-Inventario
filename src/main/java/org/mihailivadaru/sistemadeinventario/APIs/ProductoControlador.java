package org.mihailivadaru.sistemadeinventario.APIs;


import org.mihailivadaru.sistemadeinventario.entidades.Producto;
import org.mihailivadaru.sistemadeinventario.servicios.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
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
    public ResponseEntity<byte[]> generarReportePDF() {
        try {
            // Ruta donde se guarda el archivo generado en el servidor (esto lo puedes cambiar si lo deseas)
            String archivoPath = "reportes/reporte_inventario.pdf";

            // Llamada al servicio que genera el reporte PDF
            productoServicio.generarReporteInventorio();

            // Leer el archivo generado
            File archivo = new File(archivoPath);

            // Verificar que el archivo se haya generado
            if (!archivo.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Archivo no encontrado".getBytes());
            }

            // Leer los bytes del archivo PDF
            byte[] archivoBytes = Files.readAllBytes(archivo.toPath());

            // Devolver el archivo PDF como respuesta para descarga
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_inventario.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(archivoBytes);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error al generar el reporte: " + e.getMessage()).getBytes());
        }
    }
}

