package org.mihailivadaru.sistemadeinventario.repositorios;

import org.mihailivadaru.sistemadeinventario.entidades.Producto;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
public interface RepoProducto extends JpaRepository<Producto, Long> {

    //Método para buscar productos con stock bajo
    List<Producto> findByStockLessThan(int threshold);

}


