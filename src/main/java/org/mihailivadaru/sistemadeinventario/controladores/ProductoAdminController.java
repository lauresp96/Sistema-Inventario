package org.mihailivadaru.sistemadeinventario.controladores;

import org.mihailivadaru.sistemadeinventario.entidades.Producto;
import org.mihailivadaru.sistemadeinventario.servicios.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/productos")
public class ProductoAdminController {

    @Autowired
    private ProductoServicio productoServicio;

    //1. Listar productos
    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoServicio.listar());
        return "admin/lista-productos";
    }

    //2. Mostrar formulario creación productos
    @GetMapping("/nuevo")
    public String nuevoProducto(Model model) {
        model.addAttribute("producto", new Producto());
        return "admin/formulario-producto";
    }

    //3. Guardar nuevo producto
    @PostMapping
    public String guardarProducto(Model model, @ModelAttribute("producto") Producto producto) {
        productoServicio.guardar(producto);
        return "redirect:/admin/productos";
    }

    //4. Mostrar formulario para editar un producto
    @GetMapping("/editar/{id}")
    public String editarProducto(Model model, @PathVariable Integer id) {
        Producto producto = productoServicio.buscar(id);
        if (producto != null) {
            model.addAttribute("producto", producto);
            return "admin/formulario-producto";
        } else {
            return "redirect:/admin/productos";
        }
    }

    //5. Eliminar un producto
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(Model model, @PathVariable Integer id) {
        productoServicio.eliminar(id);
        return "redirect:/admin/productos";
    }
}
