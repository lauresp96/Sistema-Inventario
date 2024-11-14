package org.mihailivadaru.sistemadeinventario.servicios;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.mihailivadaru.sistemadeinventario.entidades.Producto;
import org.mihailivadaru.sistemadeinventario.repositorios.RepoProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /** GENERAR PDF DE REPORTES*/
    public void generarReporteInventorio()throws Exception{
        String dest = "reporte_inventario.pdf";
        PdfWriter writer = new PdfWriter(dest);
        Document document = new Document (new PdfDocument(writer));

        List<Producto> productos = listar();
        document.add(new Paragraph("Lista de productos por stock"));

        for (Producto producto : productos) {
            document.add(new Paragraph("Producto: " + producto.getNombre()));
            document.add(new Paragraph("Descripción: "+ producto.getDescripcion()));
            document.add(new Paragraph("Stock " + String.valueOf(producto.getStock())));
            document.add(new Paragraph("Precio: €" +String.valueOf(producto.getPrecio())));
        }
        document.close();
    }
}
