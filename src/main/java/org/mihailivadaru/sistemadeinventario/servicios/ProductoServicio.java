package org.mihailivadaru.sistemadeinventario.servicios;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.mihailivadaru.sistemadeinventario.entidades.Producto;
import org.mihailivadaru.sistemadeinventario.repositorios.RepoProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ProductoServicio {

    @Autowired
    private RepoProducto repoProducto;

    public List<Producto> listar() {
        return repoProducto.findAll();
    }

    public Producto buscar(int id) {
        return repoProducto.findById((long) id).orElse(null);
    }

    public Producto guardar(Producto producto) {
        return repoProducto.save(producto);
    }

    public void eliminar(int id) {
        repoProducto.deleteById((long) id);
    }

    public List<Producto> findLowStockProducts(int threshold) {
        return repoProducto.findByStockLessThan(threshold);
    }

    /**
     * GENERAR PDF DE REPORTES
     */
    public void generarReporteInventorio() throws Exception {
        // Ruta con directorio especificado
        String dest = "reportes/reporte_inventario.pdf";  // Ruta con subdirectorio "reportes"

        // Verificar si el directorio de destino existe y crearla si no existe
        File archivo = new File(dest);
        File directorio = archivo.getParentFile();
        if (directorio != null && !directorio.exists()) {
            directorio.mkdirs();  // Crea las carpetas necesarias
        }

        // Crear el escritor y documento PDF
        try (PdfWriter writer = new PdfWriter(dest);
             PdfDocument pdfDoc = new PdfDocument(writer);
             Document document = new Document(pdfDoc)) {

            // Obtener los productos (esto depende de tu implementación)
            List<Producto> productos = listar();

            // Agregar un título al reporte
            document.add(new Paragraph("Lista de productos por stock").setBold());

            // Agregar detalles de cada producto
            for (Producto producto : productos) {
                document.add(new Paragraph("Producto: " + producto.getNombre()));
                document.add(new Paragraph("Descripción: " + producto.getDescripcion()));
                document.add(new Paragraph("Stock: " + producto.getStock()));
                document.add(new Paragraph("Precio: €" + producto.getPrecio()));
                document.add(new Paragraph("------------------------------------------------"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Error al generar el reporte PDF", e);
        }
    }
}
